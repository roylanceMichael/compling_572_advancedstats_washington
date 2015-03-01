package edu.washington.ling.roylance.report;

import edu.washington.ling.roylance.builders.IBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ReportQ1 implements IBuilder<Double> {

    private HashMap<Integer, Integer> actualResults;

    private final HashMap<Integer, Integer> labels;

    public ReportQ1(
            @NotNull HashMap<Integer, Integer> actualResults,
            @NotNull HashMap<Integer, Integer> labels) {
        this.actualResults = actualResults;
        this.labels = labels;
    }

    @Override
    public Double build() {
        return ((double) this
                .actualResults
                .entrySet()
                .stream()
                .filter(kvp -> {
                            return this.labels.get(kvp.getKey()).equals(kvp.getValue());
                })
                .count()) / (double)(this.actualResults.size());
    }
}
