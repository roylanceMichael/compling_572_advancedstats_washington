package edu.washington.ling.roylance.report;

import edu.washington.ling.roylance.builders.IBuilder;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_parameter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SaveModelFile
        implements IBuilder<Boolean> {
    private static final String DirectoryName = "q1/";

    private final svm_parameter parameters;

    private final svm_model model;

    public SaveModelFile(
            @NotNull svm_parameter parameters,
            @NotNull svm_model model) {
        this.parameters = parameters;
        this.model = model;
    }

    @Override
    public Boolean build() {
        String modelFileName = DirectoryName + "model.1";
        if (this.parameters.kernel_type == svm_parameter.POLY &&
                this.parameters.gamma == 1) {
            modelFileName = DirectoryName +  "model.2";
        }
        else if (this.parameters.kernel_type == svm_parameter.POLY &&
                this.parameters.gamma == 0.1) {
            modelFileName = DirectoryName + "model.3";
        }
        else if (this.parameters.kernel_type == svm_parameter.RBF) {
            modelFileName = DirectoryName + "model.4";
        }
        else if (this.parameters.kernel_type == svm_parameter.SIGMOID) {
            modelFileName = DirectoryName + "model.5";
        }

        try {
            System.out.println("printing " + modelFileName);
            svm.svm_save_model(modelFileName, this.model);
        }
        catch (IOException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }
}
