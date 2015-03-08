package edu.washington.ling.roylance.builders;

import libsvm.svm;
import libsvm.svm_model;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LoadModelFromFileBuilder implements IBuilder<svm_model> {
    private final String modelFile;

    public LoadModelFromFileBuilder(
            @NotNull String modelFile) {
        this.modelFile = modelFile;
    }

    @Override
    public svm_model build() {
        try {
            return svm.svm_load_model(this.modelFile);
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
}
