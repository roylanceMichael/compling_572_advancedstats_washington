package edu.washington.ling.roylance.models;

import org.jetbrains.annotations.NotNull;

public class TagResult implements Comparable<TagResult> {
    private String tag;

    private Double probability;

    public String getTag() {
        return this.tag;
    }

    public Double getProbability() {
        return this.probability;
    }

    public TagResult setTag(String value) {
        this.tag = value;
        return this;
    }

    public TagResult setProbability(Double value) {
        this.probability = value;
        return this;
    }

    @Override
    public int compareTo(@NotNull TagResult o) {
        return this.probability < o.probability ? -1 : 1;
    }
}
