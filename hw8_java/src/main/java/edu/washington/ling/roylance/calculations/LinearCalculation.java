package edu.washington.ling.roylance.calculations;

import edu.washington.ling.roylance.utilities.ObjectUtilities;
import libsvm.svm_node;
import libsvm.svm_parameter;
import org.jetbrains.annotations.NotNull;

public class LinearCalculation implements IKernelCalculation {
    @Override
    public double perform(
            @NotNull svm_node[] x,
            @NotNull svm_node[] y,
            @NotNull svm_parameter parameter) {
        return ObjectUtilities.calculateDotProduct(x, y);
    }
}
