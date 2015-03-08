package edu.washington.ling.roylance;

import edu.washington.ling.roylance.builders.*;
import edu.washington.ling.roylance.report.ReportAccuracy;
import edu.washington.ling.roylance.report.SaveModelFile;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;

import java.util.Map;

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
        Map<Integer, Integer> trainingLabels = new LabelBuilder(trainFile).build();
        Map<Integer, Integer> testLabels = new LabelBuilder(testFile).build();

        Map<Integer, svm_node[]> trainingInstances = new InstanceBuilder(trainFile).build();
        Map<Integer, svm_node[]> testInstances = new InstanceBuilder(testFile).build();

        svm_parameter parameter = new ParameterBuilder(kernel, gamma, coef0, degree, trainingInstances).build();

        svm_model model = new ModelBuilder(trainingInstances, trainingLabels, parameter).build();

        // run classification
        Map<Integer, Integer> trainResults = new PredictBuilder(trainingInstances, model).build();
        double trainAccuracy = new ReportAccuracy(trainResults, trainingLabels).build();

        Map<Integer, Integer> testResults = new PredictBuilder(testInstances, model).build();
        double testAccuracy = new ReportAccuracy(testResults, testLabels).build();

        // output
        new SaveModelFile(parameter, model).build();

        StringBuilder resultOutput = new StringBuilder();
        resultOutput.append(trainingLabels.size() + ObjectUtilities.Tab);
        resultOutput.append(trainAccuracy + ObjectUtilities.Tab);
        resultOutput.append(testAccuracy + ObjectUtilities.Tab);

        System.out.println(resultOutput.toString());
    }
}
