package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.model.Instance;
import edu.washington.ling.roylance.services.Store;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InitialTransformationBuilder
        implements IBuilder<Double> {

    private final List<Instance> instances;

    private final Store store;

    public InitialTransformationBuilder(
            @NotNull List<Instance> instances,
            @NotNull Store store) {
        this.instances = instances;
        this.store = store;
    }

    @Override
    public Double build() {
        this
                .instances
                .forEach(instance ->
                        instance
                                .setCurrentClassification(
                                        this.store.getFirstClass()));

        double correct = this.instances
                .stream()
                .filter(instance -> instance.isCorrect())
                .count();

        double total = (double)this.instances.size();

        return correct / total;
    }
}
