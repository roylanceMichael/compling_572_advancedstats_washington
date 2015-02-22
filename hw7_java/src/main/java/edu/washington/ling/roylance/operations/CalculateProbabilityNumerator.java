package edu.washington.ling.roylance.operations;

import edu.washington.ling.roylance.builders.IBuilder;
import edu.washington.ling.roylance.models.Tag;
import edu.washington.ling.roylance.models.Word;
import org.jetbrains.annotations.NotNull;

public class CalculateProbabilityNumerator implements IBuilder<Double> {
    private final Tag tag;

    private final Word word;

    public CalculateProbabilityNumerator(
            @NotNull Tag tag,
            @NotNull Word word) {
        this.tag = tag;
        this.word = word;
    }

    @Override
    public Double build() {
        double addDefaultAndXResult =
                this.addDefaultAndX();

        return Math.pow(
                Math.E,
                addDefaultAndXResult
        );
    }

    private double addDefaultAndX() {
        double returnResult = this.tag.getDefaultProbability()
                +
                this.word
                        .getInstanceFeatures()
                        .keySet()
                        .stream()
                        .map(key -> {
                            if (this.tag.getFeatureClassInstances().containsKey(key)) {
                                return this.tag.getFeatureClassInstances().get(key);
                            }
                            return 0.0;
                        })
                        .reduce(0.0, (a, b) -> a + b);

        return returnResult;
    }
}
