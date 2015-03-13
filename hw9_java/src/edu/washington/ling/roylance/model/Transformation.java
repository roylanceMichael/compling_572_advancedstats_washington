package edu.washington.ling.roylance.model;

import org.jetbrains.annotations.NotNull;

public class Transformation {
    private final String feature;

    private final String fromClassification;

    private final String toClassification;

    public Transformation(
        @NotNull String feature,
        @NotNull String fromClassification,
        @NotNull String toClassification) {
        this.feature = feature;
        this.fromClassification = fromClassification;
        this.toClassification = toClassification;
    }

    public String getFeature() {
        return this.feature;
    }

    public String getFromClassification() {
        return this.fromClassification;
    }

    public String getToClassification() {
        return this.toClassification;
    }
}
