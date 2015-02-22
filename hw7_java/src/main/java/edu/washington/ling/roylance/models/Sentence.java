package edu.washington.ling.roylance.models;

import edu.washington.ling.roylance.enums.FeatureNames;
import edu.washington.ling.roylance.enums.TagNames;
import edu.washington.ling.roylance.models.feature.PreviousTag;
import edu.washington.ling.roylance.models.feature.PreviousTwoTags;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Sentence {
    private HashSet<Word> words;

    private int length;

    public Sentence() {
        this.words = new HashSet<>();
    }

    public HashSet<Word> getWords() {
        return this.words;
    }

    public int getLength() {
        return this.length;
    }

    public Sentence addWord(@NotNull Word word) {
        this.words.add(word);
        return this;
    }

    public Sentence setLength(int value) {
        this.length = value;
        return this;
    }

    public static List<Sentence> factory(@NotNull String boundaryFileName,
                                  @NotNull List<Word> words) {
        List<Sentence> returnList = new ArrayList<>();

        int currentWordsIndex = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(boundaryFileName));
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] splitLine = ObjectUtilities.splitByWhiteSpace(line.trim());

                    if (splitLine.length == 1) {
                        int traversedIndexes = 0;
                        int sentenceLength = Integer.parseInt(splitLine[0]);

                        Sentence newSentence = new Sentence()
                                .setLength(sentenceLength);

                        String previousTag = TagNames.BeginningOfSentence;
                        String previousTwoTags = TagNames.BeginningOfSentence;

                        while (traversedIndexes < sentenceLength) {
                            Word currentWord = words.get(currentWordsIndex);

                            currentWord.addFeatureItem(
                                    PreviousTag.factory(previousTag)
                            );

                            currentWord.addFeatureItem(
                                    PreviousTwoTags.factory(previousTwoTags)
                            );

                            previousTwoTags = currentWord.getGoldClass() + ObjectUtilities.PlusSign + previousTag;
                            previousTag = currentWord.getGoldClass();

                            newSentence.addWord(currentWord);

                            currentWordsIndex++;
                            traversedIndexes++;
                        }

                        returnList.add(newSentence);
                    }
                }
            }
            finally {
                if (br != null) {
                    br.close();
                }
            }
        }
        catch (Exception e) {
            // report the error to the console
            System.out.println(e);
        }

        return returnList;
    }
}
