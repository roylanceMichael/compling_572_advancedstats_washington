package edu.washington.ling.roylance.calculations;

import libsvm.svm_node;
import libsvm.svm_parameter;

public interface IKernelCalculation {
    double perform(svm_node[] x, svm_node[] y, svm_parameter parameter);
}
