package edu.washington.ling.roylance.models;

import edu.washington.ling.roylance.enums.TagNames;
import edu.washington.ling.roylance.models.feature.PreviousTag;
import edu.washington.ling.roylance.models.feature.PreviousTwoTags;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SentenceWord {
    private SentenceWord previousTwoWord;

    private SentenceWord previousWord;

    private String wordName;

    private String tagName;

    private String goldTagName;

    private double probability;

    private HashMap<String, SentenceWord> nextSentenceWords;

    public SentenceWord() {
        this.previousTwoWord = null;
        this.previousWord = null;
        this.tagName = TagNames.BeginningOfSentence;
        this.wordName = TagNames.BeginningOfSentence;
        this.goldTagName = TagNames.BeginningOfSentence;
        this.probability = 1.0;
        this.nextSentenceWords = new HashMap<>();
    }

    public SentenceWord(
            @NotNull String wordName,
            @NotNull String tagName,
            @NotNull String goldTagName,
            double probability) {
        this(new SentenceWord(),
            new SentenceWord(),
            wordName,
            tagName,
            goldTagName,
            probability);
    }

    public SentenceWord(
            @NotNull SentenceWord previousWord,
            @NotNull String wordName,
            @NotNull String tagName,
            @NotNull String goldTagName,
            double probability) {
        this(new SentenceWord(),
            previousWord,
            wordName,
            tagName,
            goldTagName,
            probability);
    }

    public SentenceWord(
            @NotNull SentenceWord previousTwoWord,
            @NotNull SentenceWord previousWord,
            @NotNull String wordName,
            @NotNull String tagName,
            @NotNull String goldTagName,
            double probability) {
        this.previousTwoWord = previousTwoWord;
        this.previousWord = previousWord;
        this.wordName = wordName;
        this.tagName = tagName;
        this.nextSentenceWords = new HashMap<>();
        this.goldTagName = goldTagName;
        this.probability = probability;
    }

    public SentenceWord getPreviousTwoWord() {
        return this.getPreviousWord();
    }

    public SentenceWord getPreviousWord() {
        return this;
    }

    public String getWordName() {
        return this.wordName;
    }

    public String getTagName() {
        return this.tagName;
    }

    public String getGoldTagName() {
        return this.goldTagName;
    }

    public double getProbability() {
        return this.probability;
    }

    public PreviousTwoTags getPreviousTwoTags() {
        PreviousTwoTags previousTwoTags = new PreviousTwoTags();
        if (this.previousTwoWord == null) {
            previousTwoTags.setValue(TagNames.BeginningOfSentence);
        }
        else {
            previousTwoTags.setValue(this.previousWord.getTagName());
        }

        return previousTwoTags;
    }

    public PreviousTag getPreviousTag() {
        PreviousTag previousTag = new PreviousTag();
        previousTag.setValue(this.tagName);
        return previousTag;
    }

    public HashMap<String, SentenceWord> getNextSentenceWords() {
        return this.nextSentenceWords;
    }

    public SentenceWord addNextSentenceWord(
            @NotNull String tag,
            @NotNull SentenceWord sentenceWord) {
        this.nextSentenceWords.put(tag, sentenceWord);
        return this;
    }

    public double getAccuracy() {
        List<Double> correctAmounts = new ArrayList<>();

        correctAmounts.add(
                this.tagName.equals(this.goldTagName) ? 1.0 : 0.0
        );

        this
                .nextSentenceWords
                .entrySet()
                .forEach(kvp -> {
                    correctAmounts.add(kvp.getValue().getAccuracy());
                });

        return correctAmounts.stream().reduce(0.0, (a, b) -> a + b) / (double)correctAmounts.size();
    }

    @Override
    public String toString() {
        StringBuilder workspace;
        if (this.tagName == TagNames.BeginningOfSentence) {
            workspace = new StringBuilder();
        }
        else {
            workspace = new StringBuilder(
                    this.wordName +
                            ObjectUtilities.Tab +
                            this.goldTagName +
                            ObjectUtilities.Tab +
                            this.tagName +
                            ObjectUtilities.Tab +
                            this.probability);
        }

        this
                .nextSentenceWords
                .keySet()
                .forEach(key -> workspace.append(
                        ObjectUtilities.NewLine + this.nextSentenceWords.get(key).toString()));

        return workspace.toString();
    }

    public void printSelfForSysFile() {
        System.out.println(this.wordName +
                ObjectUtilities.Tab +
                this.goldTagName +
                ObjectUtilities.Tab +
                this.tagName +
                ObjectUtilities.Tab +
                this.probability);

        this.nextSentenceWords
                .keySet()
                .forEach(key -> this.nextSentenceWords.get(key).printSelfForSysFile());
    }
}
