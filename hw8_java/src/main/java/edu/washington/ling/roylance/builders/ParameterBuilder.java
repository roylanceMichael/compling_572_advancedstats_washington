package edu.washington.ling.roylance.builders;

import libsvm.svm_parameter;
import org.jetbrains.annotations.NotNull;

public class ParameterBuilder implements IBuilder<svm_parameter> {

    private final String svmType;

    private final String kernelType;

    private final String gamma;

    private final String nu;

    private final String cacheSize;

    private final String c;

    private final String epsilon;

    private final String p;

    public ParameterBuilder(
            @NotNull String svmType,
            @NotNull String kernelType,
            @NotNull String gamma,
            @NotNull String nu,
            @NotNull String cacheSize,
            @NotNull String c,
            @NotNull String epsilon,
            @NotNull String p) {
        this.svmType = svmType;
        this.kernelType = kernelType;
        this.gamma = gamma;
        this.nu = nu;
        this.cacheSize = cacheSize;
        this.c = c;
        this.epsilon = epsilon;
        this.p = p;
    }

    @Override
    public svm_parameter build() {
        svm_parameter parameter = new svm_parameter();

        parameter.svm_type = Integer.parseInt(this.svmType);
        parameter.kernel_type = Integer.parseInt(this.kernelType);
        parameter.gamma = Double.parseDouble(this.gamma);
        parameter.nu = Double.parseDouble(this.nu);
        parameter.cache_size = Double.parseDouble(this.cacheSize);
        parameter.C = Double.parseDouble(this.c);
        parameter.eps = Double.parseDouble(this.epsilon);
        parameter.p = Double.parseDouble(this.p);

        return parameter;
    }
}
