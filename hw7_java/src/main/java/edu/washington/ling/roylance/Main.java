package edu.washington.ling.roylance;

import edu.washington.ling.roylance.models.Sentence;
import edu.washington.ling.roylance.models.Tag;
import edu.washington.ling.roylance.models.Word;
import edu.washington.ling.roylance.reports.PrintQ2SysFile;

import java.util.HashMap;
import java.util.List;

public class Main {
    private static final int ArgCount = 7;

    public static void main(String[] args) {
        if (args == null || args.length < ArgCount) {
            System.out.println("There must be " + ArgCount + " arguments!");
            return;
        }

        // get all the file names and variables
        String testDataFile = args[0];
        String boundaryFile = args[1];
        String modelFile = args[2];
        String sysOutput = args[3];
        String beamSize = args[4];
        String topN = args[5];
        String topK = args[6];

        HashMap<String, Tag> allTags = Tag.factory(modelFile);
        List<Word> words = Word.factory(testDataFile);
        List<Sentence> sentences = Sentence.factory(boundaryFile, words);

        double accuracyScore =
            new PrintQ2SysFile(
                    Integer.parseInt(topN),
                    Integer.parseInt(topK),
                    Double.parseDouble(beamSize),
                    allTags,
                    sysOutput,
                    sentences
            ).build();

        System.out.println("overall accuracy: " + accuracyScore);
    }
}
