package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.calculations.IKernelCalculation;
import edu.washington.ling.roylance.models.PredictModel;
import libsvm.svm_model;
import libsvm.svm_node;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AlternatePredictBuilder implements IBuilder<Map<Integer, PredictModel>> {
    private final Map<Integer, svm_node[]> testInstances;

    private final svm_model trainingModel;

    private final IKernelCalculation kernelCalculation;

    public AlternatePredictBuilder(
            @NotNull Map<Integer, svm_node[]> instances,
            @NotNull svm_model model,
            @NotNull IKernelCalculation kernelCalculation) {
        this.testInstances = instances;
        this.trainingModel = model;
        this.kernelCalculation = kernelCalculation;
    }

    @Override
    public Map<Integer, PredictModel> build() {
        Map<Integer, PredictModel> returnMap = new HashMap<>();

        this
                .testInstances
                .entrySet()
                .forEach(instance -> {
                    returnMap.put(
                            instance.getKey(),
                            new ManualPredictBuilder(instance.getValue(),
                                    this.trainingModel,
                                    this.kernelCalculation)
                                    .build());
                });

        return returnMap;
    }
}
