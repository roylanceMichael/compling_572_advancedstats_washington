package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.models.Transformation;
import edu.washington.ling.roylance.services.Store;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ApplyAllTransformationsBuilder
        implements IBuilder<Boolean> {

    private final Store store;

    private final List<Transformation> transformations;

    private String initialClass;

    public ApplyAllTransformationsBuilder(
            @NotNull Store store,
            @NotNull List<Transformation> transformations,
            @NotNull String initialClass) {
        this.store = store;
        this.transformations = transformations;
        this.initialClass = initialClass;
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
                            .forEach(transformation -> instance.applyTransformation(transformation));
                });



        return null;
    }
}
