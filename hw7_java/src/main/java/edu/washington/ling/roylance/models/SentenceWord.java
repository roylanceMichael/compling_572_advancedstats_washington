package edu.washington.ling.roylance.models;

import edu.washington.ling.roylance.enums.TagNames;
import edu.washington.ling.roylance.models.feature.PreviousTag;
import edu.washington.ling.roylance.models.feature.PreviousTwoTags;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SentenceWord {
    private SentenceWord previousTwoWord;

    private SentenceWord previousWord;

    private String wordName;

    private String tagName;

    private double probability;

    private String goldTagName;

    private HashMap<String, SentenceWord> nextSentenceWords;

    public SentenceWord() {
        this.previousTwoWord = null;
        this.previousWord = null;
        this.wordName = TagNames.BeginningOfSentence;
        this.tagName = TagNames.BeginningOfSentence;
        this.nextSentenceWords = new HashMap<>();
        this.probability = 0.0;
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
        return this.previousTwoWord;
    }

    public SentenceWord getPreviousWord() {
        return this.previousWord;
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
            previousTwoTags.setValue(this.previousTwoWord.getTagName());
        }

        return previousTwoTags;
    }

    public PreviousTag getPreviousTag() {
        PreviousTag previousTag = new PreviousTag();
        if (this.previousWord == null) {
            previousTag.setValue(TagNames.BeginningOfSentence);
        }
        else {
            previousTag.setValue(this.previousWord.getTagName());
        }

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

    @Override
    public String toString() {
        StringBuilder workspace = new StringBuilder(
                this.wordName +
                ObjectUtilities.Tab +
                this.goldTagName +
                ObjectUtilities.Tab +
                this.tagName +
                ObjectUtilities.Tab +
                this.probability);

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
