package edu.washington.ling.roylance.builders;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class PredictBuilder implements IBuilder<HashMap<Integer, Integer>> {

    private final HashMap<Integer, HashMap<Integer, Integer>> testInstances;

    private final svm_model trainingModel;

    public PredictBuilder(
            @NotNull HashMap<Integer, HashMap<Integer, Integer>> instances,
            @NotNull svm_model model) {
        this.testInstances = instances;
        this.trainingModel = model;
    }

    @Override
    public HashMap<Integer, Integer> build() {
        HashMap<Integer, Integer> returnMap = new HashMap<>();

        this
                .testInstances
                .entrySet()
                .forEach(testInstance -> {
                    svm_node[] x = new svm_node[testInstance.getValue().size()];

                    int featureIndex = 0;
                    for (Integer feature: testInstance.getValue().keySet()) {
                        x[featureIndex] = new svm_node();
                        x[featureIndex].index = feature;
                        x[featureIndex].value = 1;
                        featureIndex++;
                    }

                    returnMap.put(testInstance.getKey(), (int)svm.svm_predict(this.trainingModel, x));
                });

        return returnMap;
    }
}
