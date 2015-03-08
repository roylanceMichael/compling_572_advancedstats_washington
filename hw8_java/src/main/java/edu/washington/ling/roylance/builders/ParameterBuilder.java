package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.enums.KernelTypes;
import libsvm.svm_node;
import libsvm.svm_parameter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

public class ParameterBuilder implements IBuilder<svm_parameter> {

    private final String kernelType;

    private final String gamma;

    private final String coef0;

    private final String degree;

    private final Map<Integer, svm_node[]> trainingInstances;

    public ParameterBuilder(
            @NotNull String kernelType,
            String gamma,
            String coef0,
            String degree,
            Map<Integer, svm_node[]> trainingInstances) {
        this.kernelType = kernelType;
        this.gamma = gamma;
        this.coef0 = coef0;
        this.degree = degree;
        this.trainingInstances = trainingInstances;
    }

    @Override
    public svm_parameter build() {
        svm_parameter parameter = new svm_parameter();
        // default values
        parameter.svm_type = svm_parameter.C_SVC;
        parameter.kernel_type = svm_parameter.RBF;
        parameter.degree = 3;
        parameter.gamma = 1;
        parameter.coef0 = 0;
        parameter.nu = 0.5;
        parameter.cache_size = 100;
        parameter.C = 1;
        parameter.eps = 1e-3;
        parameter.p = 0.1;
        parameter.shrinking = 1;
        parameter.probability = 0;
        parameter.nr_weight = 0;
        parameter.weight_label = new int[0];
        parameter.weight = new double[0];

        switch (this.kernelType.toLowerCase()) {
            case KernelTypes.Linear:
                parameter.kernel_type = svm_parameter.LINEAR;
                break;
            case KernelTypes.Polynomial:
                parameter.kernel_type = svm_parameter.POLY;
                break;
            case KernelTypes.RBF:
                parameter.kernel_type = svm_parameter.RBF;
                break;
            default:
                parameter.kernel_type = svm_parameter.SIGMOID;
                break;
        }

        if (this.gamma != null && this.gamma.length() > 0) {
            parameter.gamma = Double.parseDouble(this.gamma);
        }

        if (this.coef0 != null && this.coef0.length() > 0) {
            parameter.coef0 = Double.parseDouble(this.coef0);
        }

        if (this.degree != null && this.degree.length() > 0) {
            parameter.degree = Integer.parseInt(this.degree);
        }

        if (parameter.gamma == 0) {
            int maxIndex = this.trainingInstances
                    .values()
                    .stream()
                    .map(trainingInstance -> Arrays.stream(trainingInstance).max((a, b) -> Integer.compare(a.index, b.index)).get().index)
                    .max((a, b) -> a.compareTo(b))
                    .get();
            parameter.gamma = 1.0 / maxIndex;
        }

        return parameter;
    }
}
