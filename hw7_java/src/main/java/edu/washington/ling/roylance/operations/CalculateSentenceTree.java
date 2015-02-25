package edu.washington.ling.roylance.operations;

import edu.washington.ling.roylance.builders.IBuilder;
import edu.washington.ling.roylance.models.Sentence;
import edu.washington.ling.roylance.models.SentenceWord;
import edu.washington.ling.roylance.models.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CalculateSentenceTree implements IBuilder<SentenceWord> {

    private final int topN;

    private final int topK;

    private final double beamSize;

    private final Sentence sentence;

    private final HashMap<String, Tag> allTags;

    public CalculateSentenceTree(
            int topN,
            int topK,
            double beamSize,
            @NotNull Sentence sentence,
            @NotNull HashMap<String, Tag> allTags) {
        this.topN = topN;
        this.topK = topK;
        this.beamSize = beamSize;
        this.sentence = sentence;
        this.allTags = allTags;
    }

    @Override
    public SentenceWord build() {
        List<SentenceWord> currentLevel = new ArrayList<>();
        SentenceWord beginningOfSentence = new SentenceWord();

        currentLevel.add(beginningOfSentence);

        this
                .sentence
                .getWords()
                .stream()
                .sorted((a, b) -> Integer.compare(a.getId(), b.getId()))
                .forEach(word -> {
                    List<SentenceWord> newLevel = new ArrayList<>();

                    currentLevel
                            .forEach(previousWord ->
                                    word
                                    .calculatePotentialTags(
                                            this.topK,
                                            this.beamSize,
                                            previousWord.getPreviousTwoTags(),
                                            previousWord.getPreviousTag(),
                                            this.allTags)
                                    .getTopTags(this.topN)
                                    .forEach(tag -> {

                                        SentenceWord newWord = new SentenceWord(
                                                previousWord,
                                                word.getInstanceName(),
                                                tag.getTag(),
                                                word.getGoldTag(),
                                                tag.getProbability());

                                        previousWord.addNextSentenceWord(tag.getTag(), newWord);
                                        newLevel.add(newWord);
                                    }));

                    currentLevel.clear();
                    currentLevel.addAll(newLevel);
                });

        return beginningOfSentence;
    }
}
