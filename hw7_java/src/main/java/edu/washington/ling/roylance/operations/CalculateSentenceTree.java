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

    private final Sentence sentence;

    private final HashMap<String, Tag> allTags;

    public CalculateSentenceTree(
            int topN,
            @NotNull Sentence sentence,
            @NotNull HashMap<String, Tag> allTags) {
        this.topN = topN;
        this.sentence = sentence;
        this.allTags = allTags;
    }

    @Override
    public SentenceWord build() {
        List<SentenceWord> currentLevel = new ArrayList<>();
        SentenceWord previousWord = new SentenceWord();

        currentLevel.add(previousWord);

        this
                .sentence
                .getWords()
                .forEach(word -> {
                    List<SentenceWord> newLevel = new ArrayList<>();

                    currentLevel
                            .forEach(wordOnLevel -> {
                                System.out.println("----");
                                System.out.println(wordOnLevel.getPreviousTwoTags());
                                System.out.println(wordOnLevel.getPreviousTag());

                                word
                                        .calculatePotentialTags(
                                                wordOnLevel.getPreviousTwoTags(),
                                                wordOnLevel.getPreviousTag(),
                                                allTags)
                                        .getTopTags(this.topN)
                                        .forEach(tag -> {
                                            System.out.println(tag);

                                            SentenceWord newWord = new SentenceWord(
                                                    wordOnLevel,
                                                    word.getInstanceName(),
                                                    tag);
                                            wordOnLevel.addNextSentenceWord(tag, newWord);
                                            newLevel.add(newWord);
                                        });
                            });

                    currentLevel.clear();
                    currentLevel.addAll(newLevel);
                });

        return previousWord;
    }
}
