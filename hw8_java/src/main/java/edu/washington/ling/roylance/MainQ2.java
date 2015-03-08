package edu.washington.ling.roylance;

import edu.washington.ling.roylance.builders.*;
import edu.washington.ling.roylance.calculations.*;
import edu.washington.ling.roylance.models.PredictModel;
import edu.washington.ling.roylance.report.ReportAccuracy;
import edu.washington.ling.roylance.report.ReportSysFile;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;

import java.util.HashMap;
import java.util.Map;

public class MainQ2 {
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("There must be several arguments!");
            return;
        }

        String testData = args[0];
        String modelFile = args[1];
        String sysFile = args[2];

        // read in files
        Map<Integer, Integer> testLabels = new LabelBuilder(testData).build();
        Map<Integer, svm_node[]> testInstances = new InstanceBuilder(testData).build();
        svm_model model = new LoadModelFromFileBuilder(modelFile).build();

        IKernelCalculation kernelCalculation = null;

        if (model.param.kernel_type == svm_parameter.LINEAR) {
            kernelCalculation = new LinearCalculation();
        }
        else if (model.param.kernel_type == svm_parameter.POLY) {
            kernelCalculation = new PolynomialCalculation();
        }
        else if (model.param.kernel_type == svm_parameter.RBF) {
            kernelCalculation = new RbfCalculation();
        }
        else if (model.param.kernel_type == svm_parameter.SIGMOID) {
            kernelCalculation = new SigmoidCalculation();
        }

        Map<Integer, PredictModel> testResults = new AlternatePredictBuilder(testInstances, model, kernelCalculation).build();
        Map<Integer, Integer> otherTestResults = new PredictBuilder(testInstances, model).build();

        Map<Integer, Integer> convertedTestResults = new HashMap<>();
        testResults
                .entrySet()
                .forEach(entrySet -> {
                    convertedTestResults.put(entrySet.getKey(), entrySet.getValue().getClassification());
                });

        // report sys file
        new ReportSysFile(sysFile, testResults, testLabels).build();

        double testAccuracy = new ReportAccuracy(convertedTestResults, testLabels).build();
        double otherTestAccuracy = new ReportAccuracy(otherTestResults, testLabels).build();

        StringBuilder resultOutput = new StringBuilder();
        resultOutput.append(testAccuracy + ObjectUtilities.Tab);
        resultOutput.append(otherTestAccuracy + ObjectUtilities.Tab);

        System.out.println(resultOutput.toString());

    }
}
