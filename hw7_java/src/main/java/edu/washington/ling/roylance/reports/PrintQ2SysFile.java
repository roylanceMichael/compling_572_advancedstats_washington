package edu.washington.ling.roylance.reports;

import edu.washington.ling.roylance.builders.IBuilder;
import edu.washington.ling.roylance.models.Sentence;
import edu.washington.ling.roylance.models.SentenceWord;
import edu.washington.ling.roylance.models.Tag;
import edu.washington.ling.roylance.operations.CalculateSentenceTree;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrintQ2SysFile implements IBuilder<Double> {

    private final int topN;

    private final int topK;

    private final double beamSize;

    private final HashMap<String, Tag> allTags;

    private final String sysFile;

    private final List<Sentence> sentences;

    public PrintQ2SysFile(
            int topN,
            int topK,
            double beamSize,
            @NotNull HashMap<String, Tag> allTags,
            @NotNull String sysFile,
            @NotNull List<Sentence> sentences) {
        this.topN = topN;
        this.topK = topK;
        this.beamSize = beamSize;
        this.allTags = allTags;
        this.sysFile = sysFile;
        this.sentences = sentences;
    }

    @Override
    public Double build() {
        List<SentenceWord> sentenceWords = new ArrayList<>();

        this
                .sentences
                .parallelStream()
                .forEach(sentence ->
                        sentenceWords.add(
                            new CalculateSentenceTree(
                                    this.topN,
                                    topK,
                                    beamSize,
                                    sentence,
                                    allTags).build()
                ));

        List<Double> accuracies = new ArrayList<>();

        try {
            PrintWriter writer = new PrintWriter(sysFile, ObjectUtilities.Utf8Encoding);

            sentenceWords
                    .forEach(beginningOfSentence -> {
                        writer.println(beginningOfSentence.toString());
                        accuracies.add(beginningOfSentence.getAccuracy());
                    });

            writer.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
        catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }

        return accuracies.stream().reduce(0.0, (a, b) -> a + b) / (double) accuracies.size();
    }
}
