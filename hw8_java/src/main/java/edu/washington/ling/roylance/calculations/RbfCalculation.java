package edu.washington.ling.roylance.calculations;

import libsvm.svm_node;
import libsvm.svm_parameter;

public class RbfCalculation implements IKernelCalculation {
    @Override
    public double perform(svm_node[] x, svm_node[] y, svm_parameter parameter) {
        double total = 0.0;

        int xLength = x.length;
        int yLength = y.length;

        int xIndex = 0;
        int yIndex = 0;

        while (xIndex < xLength &&
                yIndex < yLength) {
            if (x[xIndex].index == y[yIndex].index) {
                double equalTotal = x[xIndex].value - y[yIndex].value;
                total += equalTotal * equalTotal;
                xIndex++;
                yIndex++;
            }
            else if (x[xIndex].index > y[yIndex].index) {
                total += y[yIndex].value * y[yIndex].value;
                yIndex++;
            }
            else if (x[xIndex].index < y[yIndex].index) {
                total += x[xIndex].value * x[xIndex].value;
                xIndex++;
            }
        }

        // get remaining y
        while (yIndex < yLength) {
            total += y[yIndex].value * y[yIndex].value;
            yIndex++;
        }

        while (xIndex < xLength) {
            total += x[xIndex].value * x[xIndex].value;
            xIndex++;
        }

        return Math.exp(-parameter.gamma * total);
    }
}
