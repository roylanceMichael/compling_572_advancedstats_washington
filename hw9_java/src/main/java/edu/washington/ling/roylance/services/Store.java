package edu.washington.ling.roylance.services;

import edu.washington.ling.roylance.models.Instance;
import edu.washington.ling.roylance.models.Transformation;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class Store {
    private final HashSet<String> uniqueFeatures;

    private final HashSet<String> classifications;

    private final HashSet<Instance> instances;

    private final HashSet<Transformation> possibleTransformations;

    private double instanceSize;

    private String firstClass;

    public Store() {
        this.uniqueFeatures = new HashSet<>();
        this.classifications = new HashSet<>();
        this.instances = new HashSet<>();
        this.possibleTransformations = new HashSet<>();
    }

    public String getFirstClass() {
        return this.firstClass;
    }

    public HashSet<Instance> getInstances() {
        return this.instances;
    }

    public double getInstanceSize() {
        if (this.instanceSize == 0.0) {
            this.instanceSize = this.instances.size();
        }
        return this.instanceSize;
    }

    public Store addFeature(@NotNull String feature) {
        if (!this.uniqueFeatures.contains(feature)) {
            this.uniqueFeatures.add(feature);
        }
        return this;
    }

    public Store addClassification(@NotNull String classification) {
        if (!this.classifications.contains(classification)) {
            this.classifications.add(classification);
        }
        return this;
    }

    public Store addInstance(@NotNull Instance instance) {
        if (this.instances.size() == 0) {
            this.setFirstClass(instance.getGoldClassification());
        }

        this.instances.add(instance);

        this.addClassification(instance.getGoldClassification());

        instance
                .getFeatures()
                .forEach(feature ->
                    this.addFeature(feature)
                );

        return this;
    }

    public Store setFirstClass(@NotNull String value) {
        this.firstClass = value;
        return this;
    }

    public HashSet<Transformation> getPossibleTransformations() {
        if (this.possibleTransformations.size() == 0) {

            this.classifications
                    .forEach(fromClassification -> {
                        this.classifications
                                .forEach(toClassification -> {

                                    if (fromClassification.equals(toClassification)) {
                                        return;
                                    }

                                    this.uniqueFeatures
                                            .forEach(feature -> {
                                                this.possibleTransformations
                                                        .add(new Transformation(feature, fromClassification, toClassification));
                                            });
                        });
                    });
        }

        return this.possibleTransformations;
    }
}
