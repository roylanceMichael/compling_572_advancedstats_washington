package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.enums.KernelTypes;
import libsvm.svm_parameter;
import org.jetbrains.annotations.NotNull;

import java.util.DoubleSummaryStatistics;

public class ParameterBuilder implements IBuilder<svm_parameter> {

    private final String kernelType;

    private final String gamma;

    private final String coef0;

    private final String degree;

    public ParameterBuilder(
            @NotNull String kernelType,
            String gamma,
            String coef0,
            String degree) {
        this.kernelType = kernelType;
        this.gamma = gamma;
        this.coef0 = coef0;
        this.degree = degree;
    }

    @Override
    public svm_parameter build() {
        svm_parameter parameter = new svm_parameter();
        parameter.svm_type = svm_parameter.ONE_CLASS;

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

        return parameter;
    }
}
