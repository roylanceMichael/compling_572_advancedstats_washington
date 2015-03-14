package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.services.Store;
import org.jetbrains.annotations.NotNull;

public class InitialTransformationBuilder
        implements IBuilder<Double> {

    private final Store store;

    public InitialTransformationBuilder(@NotNull Store store) {
        this.store = store;
    }

    @Override
    public Double build() {
        this
                .store
                .getInstances()
                .forEach(instance ->
                        instance
                                .setCurrentClassification(
                                        this.store.getFirstClass()));

        double correct = this.store
                .getInstances()
                .stream()
                .filter(instance -> instance.isCorrect())
                .count();

        double total = this.store.getInstanceSize();

        return correct / total;
    }
}
