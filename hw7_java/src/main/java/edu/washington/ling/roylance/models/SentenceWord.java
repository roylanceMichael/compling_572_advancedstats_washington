package edu.washington.ling.roylance.models;

import edu.washington.ling.roylance.enums.TagNames;
import edu.washington.ling.roylance.models.feature.PreviousTag;
import edu.washington.ling.roylance.models.feature.PreviousTwoTags;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SentenceWord {
    private SentenceWord previousTwoWord;

    private SentenceWord previousWord;

    private String wordName;

    private String tagName;

    private double probability;

    private HashMap<String, SentenceWord> nextSentenceWords;

    public SentenceWord() {
        this.previousTwoWord = null;
        this.previousWord = null;
        this.wordName = TagNames.BeginningOfSentence;
        this.tagName = TagNames.BeginningOfSentence;
        this.nextSentenceWords = new HashMap<>();
    }

    public SentenceWord(
            @NotNull String wordName,
            @NotNull String tagName) {
        this(new SentenceWord(),
                new SentenceWord(),
                wordName,
                tagName);
    }

    public SentenceWord(
            @NotNull SentenceWord previousWord,
            @NotNull String wordName,
            @NotNull String tagName) {
        this(new SentenceWord(),
                previousWord,
                wordName,
                tagName);
    }

    public SentenceWord(
            @NotNull SentenceWord previousTwoWord,
            @NotNull SentenceWord previousWord,
            @NotNull String wordName,
            @NotNull String tagName) {
        this.previousTwoWord = previousTwoWord;
        this.previousWord = previousWord;
        this.wordName = wordName;
        this.tagName = tagName;
        this.nextSentenceWords = new HashMap<>();
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
        StringBuilder workspace = new StringBuilder(this.wordName + " " + this.tagName);

        this
                .nextSentenceWords
                .keySet()
                .forEach(key -> {
                    workspace.append("\n" + this.nextSentenceWords.get(key).toString());
                });

        return workspace.toString();
    }

    public void printSelf() {
        System.out.println(this.wordName + " " + this.tagName);

        this.nextSentenceWords
                .keySet()
                .forEach(key -> this.nextSentenceWords.get(key).printSelf());
    }
}
