package edu.washington.ling.roylance.builders;

import libsvm.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ModelBuilder implements IBuilder<svm_model> {

    private final HashMap<Integer, HashMap<Integer, Integer>> trainingInstances;

    private final HashMap<Integer, Integer> trainingLabels;

    private final svm_parameter parameter;

    public ModelBuilder(
            @NotNull HashMap<Integer, HashMap<Integer, Integer>> trainingInstances,
            @NotNull HashMap<Integer, Integer> trainingLabels,
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
            problem.x[i] = new svm_node[this.trainingInstances.get(i).size()];

            int index = 0;
            for (Integer id: this.trainingInstances.get(i).keySet()) {
                svm_node node = new svm_node();
                node.index = id;
                node.value = 1;
                problem.x[i][index] = node;
                index++;
            }

            problem.y[i] = this.trainingLabels.get(i);
        }

        return svm.svm_train(problem, this.parameter);
    }
}
