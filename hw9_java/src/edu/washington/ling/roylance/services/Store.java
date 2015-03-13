package edu.washington.ling.roylance.services;

import edu.washington.ling.roylance.model.Instance;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class Store {
    private final HashSet<String> uniqueFeatures;

    private final HashSet<String> classifications;

    private final HashSet<Instance> instances;

    private double instanceSize;

    private String firstClass;

    public Store() {
        this.uniqueFeatures = new HashSet<>();
        this.classifications = new HashSet<>();
        this.instances = new HashSet<>();
    }

    public String getFirstClass() {
        return this.firstClass;
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
}
