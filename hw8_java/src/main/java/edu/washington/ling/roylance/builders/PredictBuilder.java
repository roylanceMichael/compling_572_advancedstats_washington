package edu.washington.ling.roylance.builders;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class PredictBuilder implements IBuilder<HashMap<Integer, Double>> {

    private final HashMap<Integer, HashMap<Integer, Double>> testInstances;

    private final svm_model trainingModel;

    public PredictBuilder(
            @NotNull HashMap<Integer, HashMap<Integer, Double>> testInstances,
            @NotNull svm_model trainingModel) {
        this.testInstances = testInstances;
        this.trainingModel = trainingModel;
    }

    @Override
    public HashMap<Integer, Double> build() {
        HashMap<Integer, Double> returnMap = new HashMap<>();

        this
                .testInstances
                .entrySet()
                .forEach(testInstance -> {
                    svm_node[] x = new svm_node[testInstance.getValue().size()];

                    int featureIndex = 0;

                    for (Integer feature: testInstance.getValue().keySet()) {
                        x[featureIndex] = new svm_node();
                        x[featureIndex].index = feature;
                        x[featureIndex].value = testInstance.getValue().get(feature);
                        featureIndex++;
                    }

                    returnMap.put(testInstance.getKey(), svm.svm_predict(this.trainingModel, x));
                });

        return returnMap;
    }
}
