package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.models.Transformation;
import edu.washington.ling.roylance.services.Store;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ApplyAllTransformationsBuilder
        implements IBuilder<Boolean> {

    private final Store store;

    private final List<Transformation> transformations;

    private final String initialClass;

    private final int n;

    public ApplyAllTransformationsBuilder(
            @NotNull Store store,
            @NotNull List<Transformation> transformations,
            @NotNull String initialClass,
            int n) {
        this.store = store;
        this.transformations = transformations;
        this.initialClass = initialClass;
        this.n = n;
    }

    @Override
    public Boolean build() {

        this
                .store
                .getInstances()
                .forEach(instance -> {
                    instance.setCurrentClassification(this.initialClass);

                    this
                            .transformations
                            .stream()
                            .limit(this.n)
                            .forEach(transformation -> instance.applyTransformation(transformation));
                });



        return null;
    }
}
