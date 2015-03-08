package edu.washington.ling.roylance.builders;

import libsvm.*;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ModelBuilder implements IBuilder<svm_model> {

    private final Map<Integer, svm_node[]> trainingInstances;

    private final Map<Integer, Integer> trainingLabels;

    private final svm_parameter parameter;

    public ModelBuilder(
            @NotNull Map<Integer, svm_node[]> trainingInstances,
            @NotNull Map<Integer, Integer> trainingLabels,
            @NotNull svm_parameter parameter) {
        this.trainingInstances = trainingInstances;
        this.trainingLabels = trainingLabels;
        this.parameter = parameter;
    }

    @Override
    public svm_model build() {
        svm_problem problem = new svm_problem();
        problem.l = this.trainingLabels.keySet().size();
        problem.y = new double[problem.l];
        problem.x = new svm_node[problem.l][];

        for (int i = 0; i < problem.l; i++) {
            problem.x[i] = this.trainingInstances.get(i);
            problem.y[i] = this.trainingLabels.get(i);
        }

        return svm.svm_train(problem, this.parameter);
    }
}
