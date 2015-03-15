package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.models.Transformation;
import edu.washington.ling.roylance.services.Store;
import org.jetbrains.annotations.NotNull;

public class ApplyHighestTransformationBuilder
    implements IBuilder<Double> {

    private final Store store;

    private final Transformation transformation;

    public ApplyHighestTransformationBuilder(
            @NotNull Store store,
            @NotNull Transformation transformation) {
        this.store = store;
        this.transformation = transformation;
    }

    @Override
    public Double build() {
        this.store
        .getInstances()
        .forEach(instance -> instance.applyTransformation(this.transformation));

        double correct = this.store
                .getInstances()
                .stream()
                .map(instance -> instance.isCorrect())
                .filter(result -> result)
                .count();

        return correct;
    }
}
