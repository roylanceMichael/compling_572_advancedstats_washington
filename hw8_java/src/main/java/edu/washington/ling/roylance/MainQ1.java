package edu.washington.ling.roylance;

import libsvm.svm_parameter;

public class MainQ1 {
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("There must be 4 arguments!");
            return;
        }

        String trainFile = args[0];
        String testFile = args[1];
        String exceptId = args[2];
        String kernel = args[3];
        String gamma = args[4];
        String coef0 = args[5];
        String degree = args[6];

        // read in files
        // run classification
        // output results

        new svm_parameter();
    }
}
