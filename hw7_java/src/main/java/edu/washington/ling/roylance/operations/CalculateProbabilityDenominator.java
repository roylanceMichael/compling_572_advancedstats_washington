package edu.washington.ling.roylance.operations;

import edu.washington.ling.roylance.builders.IBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class CalculateProbabilityDenominator implements IBuilder<Double> {
    private final Collection<Double> numerators;

    public CalculateProbabilityDenominator(
            @NotNull Collection<Double> numerators) {
        this.numerators = numerators;
    }

    @Override
    public Double build() {
        return this.numerators.stream().reduce(0.0, (a, b) -> a + b);
    }
}