package edu.washington.ling.roylance;

import edu.washington.ling.roylance.builders.*;
import edu.washington.ling.roylance.report.ReportQ1;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import libsvm.svm_model;
import libsvm.svm_parameter;

import java.util.HashMap;

public class MainQ1 {
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("There must be several arguments!");
            return;
        }

        String trainFile = args[0];
        String testFile = args[1];
        String kernel = args[2];
        String gamma = null;
        String coef0 = null;
        String degree = null;

        if (args.length > 3) {
            gamma = args[3];
        }

        if (args.length > 4) {
            coef0 = args[4];
        }

        if (args.length > 5) {
            degree = args[5];
        }

        // read in files
        svm_parameter parameter = new ParameterBuilder(kernel, gamma, coef0, degree).build();
        HashMap<Integer, Integer> trainingLabels = new LabelBuilder(trainFile).build();
        HashMap<Integer, Integer> testLabels = new LabelBuilder(testFile).build();

        HashMap<Integer, HashMap<Integer, Integer>> trainingInstances = new InstanceBuilder(trainFile).build();
        HashMap<Integer, HashMap<Integer, Integer>> testInstances = new InstanceBuilder(testFile).build();
        svm_model model = new ModelBuilder(trainingInstances, trainingLabels, parameter).build();

        // run classification
        HashMap<Integer, Integer> trainResults = new PredictBuilder(trainingInstances, model).build();
        double trainAccuracy = new ReportQ1(trainResults, trainingLabels).build();

        HashMap<Integer, Integer> testResults = new PredictBuilder(testInstances, model).build();
        double testAccuracy = new ReportQ1(testResults, testLabels).build();

        // output
        StringBuilder resultOutput = new StringBuilder();
        resultOutput.append(trainingLabels.size() + ObjectUtilities.Tab);
        resultOutput.append(trainAccuracy + ObjectUtilities.Tab);
        resultOutput.append(testAccuracy + ObjectUtilities.Tab);

        System.out.println(resultOutput.toString());
    }
}
