package edu.washington.ling.roylance.calculations;

import edu.washington.ling.roylance.utilities.ObjectUtilities;
import libsvm.svm_node;
import libsvm.svm_parameter;

public class SigmoidCalculation implements IKernelCalculation {
    @Override
    public double perform(svm_node[] x, svm_node[] y, svm_parameter parameter) {
        double dotProduct = ObjectUtilities.calculateDotProduct(x, y);

        return Math.tanh(parameter.gamma * dotProduct + parameter.coef0);
    }
}
