package edu.washington.ling.roylance.report;

import edu.washington.ling.roylance.builders.IBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ReportAccuracy implements IBuilder<Double> {

    private final Map<Integer, Integer> actualResults;

    private final Map<Integer, Integer> labels;

    public ReportAccuracy(
            @NotNull Map<Integer, Integer> actualResults,
            @NotNull Map<Integer, Integer> labels) {
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
