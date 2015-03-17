package edu.washington.ling.roylance.models;

import edu.washington.ling.roylance.utils.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

public class TransformationResult {
    private final int number;

    private final Transformation transformation;

    private final double gain;

    private final double currentProbability;

    public TransformationResult(
            int number,
            @NotNull Transformation transformation,
            double gain,
            double currentProbability) {
        this.number = number;
        this.transformation = transformation;
        this.gain = gain;
        this.currentProbability = currentProbability;
    }

    public double getCurrentProbability() {
        return this.currentProbability;
    }

    @Override
    public String toString() {
        return this.transformation.getFeature() +
                ObjectUtilities.Tab +
                this.transformation.getFromClassification() +
                ObjectUtilities.Tab +
                this.transformation.getToClassification() +
                ObjectUtilities.Tab +
                this.gain;
    }
}
