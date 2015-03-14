package edu.washington.ling.roylance.models;

import org.jetbrains.annotations.NotNull;

public class Transformation {
    private static int IdSequence = 0;

    private final int id;

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
        this.id = ++IdSequence;
    }

    public int getId() {
        return this.id;
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

    @Override
    public String toString() {
        return this.feature + " : " + this.fromClassification + " -> " + this.toClassification;
    }
}
