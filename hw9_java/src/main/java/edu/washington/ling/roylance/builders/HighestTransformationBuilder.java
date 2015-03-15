package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.models.Transformation;
import edu.washington.ling.roylance.services.Store;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class HighestTransformationBuilder
    implements IBuilder<Transformation> {

    private final Store store;

    public HighestTransformationBuilder(
            @NotNull Store store) {
        this.store = store;
    }

    @Override
    public Transformation build() {
        HashMap<Transformation, Long> transformations = new HashMap<>();
        
        this.store
                .getPossibleTransformations()
                .stream()
                .forEach(transformation -> {
                    long correctTotal = this.store
                            .getInstances()
                            .stream()
                            .map(instance -> instance.getTestTransformation(transformation))
                            .filter(result -> result)
                            .count();

                    transformations.put(transformation, correctTotal);
                });

        return transformations
                .entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(1)
                .findFirst()
                .get()
                .getKey();
    }
}
