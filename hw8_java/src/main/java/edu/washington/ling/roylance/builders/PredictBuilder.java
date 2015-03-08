package edu.washington.ling.roylance.builders;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PredictBuilder implements IBuilder<Map<Integer, Integer>> {

    private final Map<Integer, svm_node[]> testInstances;

    private final svm_model trainingModel;

    public PredictBuilder(
            @NotNull Map<Integer, svm_node[]> instances,
            @NotNull svm_model model) {
        this.testInstances = instances;
        this.trainingModel = model;
    }

    @Override
    public Map<Integer, Integer> build() {
        Map<Integer, Integer> returnMap = new HashMap<>();

        this
                .testInstances
                .entrySet()
                .forEach(instance ->
                        returnMap.put(instance.getKey(),
                                        (int) svm.svm_predict(
                                                this.trainingModel,
                                                instance.getValue()
                )));

        return returnMap;
    }
}
