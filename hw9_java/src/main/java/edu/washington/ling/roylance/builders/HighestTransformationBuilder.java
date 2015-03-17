package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.models.Transformation;
import edu.washington.ling.roylance.services.Store;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HighestTransformationBuilder
    implements IBuilder<Transformation> {

    private final Store store;

    public HighestTransformationBuilder(
            @NotNull Store store) {
        this.store = store;
    }

    @Override
    public Transformation build() {
        Map<Transformation, Long> transformations = new ConcurrentHashMap<>();

        this.store
                .getPossibleTransformations()
                .parallelStream()
                .forEach(transformation -> {
                    List<Boolean> results = new ArrayList<>();
                    this.store
                            .getInstances()
                            .forEach(instance -> {
                                if (instance.getTestTransformation(transformation)) {
                                    results.add(true);
                                }
                            });

                    transformations.put(transformation, (long)results.size());
                });

        Long largest = 0L;
        Transformation largestTransformation = null;

        for(Transformation key : transformations.keySet()) {
            if (transformations.get(key) > largest) {
                largestTransformation = key;
                largest = transformations.get(key);
            }
        }

        return largestTransformation;
    }
}
