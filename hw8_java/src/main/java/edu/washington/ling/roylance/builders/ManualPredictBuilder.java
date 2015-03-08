package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.calculations.IKernelCalculation;
import edu.washington.ling.roylance.models.PredictModel;
import libsvm.svm_model;
import libsvm.svm_node;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ManualPredictBuilder implements IBuilder<PredictModel> {
    private final svm_node[] testInstances;

    private final svm_model trainingModel;

    private final IKernelCalculation kernelCalculation;

    public ManualPredictBuilder(
            @NotNull svm_node[] instances,
            @NotNull svm_model model,
            @NotNull IKernelCalculation kernelCalculation) {
        this.testInstances = instances;
        this.trainingModel = model;
        this.kernelCalculation = kernelCalculation;
    }

    @Override
    public PredictModel build() {
        double[] kernelValues = new double[this.trainingModel.l];
        for (int i = 0; i < this.trainingModel.l; i++) {
            kernelValues[i] = this.kernelCalculation.perform(
                    this.testInstances,
                    this.trainingModel.SV[i],
                    this.trainingModel.param);
        }

        // calculate all the starting values
        double[] fxStorage = new double[this.trainingModel.nr_class];
        for (int i =0; i < fxStorage.length; i++) {
            fxStorage[i] = 0.0D;
        }

        int[] start = new int[this.trainingModel.nr_class];
        start[0] = 0;
        for(int i = 1; i < this.trainingModel.nr_class; i++) {
            start[i] = start[i - 1] + this.trainingModel.nSV[i - 1];
        }

        // initialize all the voting items
        int[] vote = new int[this.trainingModel.nr_class];
        for(int i = 0; i < this.trainingModel.nr_class; i++) {
            vote[i] = 0;
        }

        // p corresponds to the training rho
        int p = 0;
        for(int keyClass = 0; keyClass < this.trainingModel.nr_class; keyClass++) {
            for(int altClass = keyClass + 1; altClass < this.trainingModel.nr_class; altClass++) {
                double fx = 0.0D;

                int currentValue = start[keyClass];
                int largestIndexValue = start[altClass];

                int currentTrainingValue = this.trainingModel.nSV[keyClass];
                int altTrainingValue = this.trainingModel.nSV[altClass];

                double[] currentCoefValue = this.trainingModel.sv_coef[keyClass];
                double[] altCoefValue = this.trainingModel.sv_coef[altClass - 1];

                // first coef
                for(int k = 0; k < currentTrainingValue; k++) {
                    fx += altCoefValue[currentValue + k] * kernelValues[currentValue + k];
                }

                // "max" coef
                for(int k = 0; k < altTrainingValue; k++) {
                    fx += currentCoefValue[largestIndexValue + k] * kernelValues[largestIndexValue + k];
                }

                // subtract the rho from the model
                fx -= this.trainingModel.rho[p];

                fxStorage[keyClass] = fx;

                // if the sum is greater than the training rho, then we vote for the new index
                if(fx > 0.0D) {
                    vote[keyClass]++;
                } else {
                    vote[altClass]++;
                }

                // increment p
                p++;
            }
        }

        int largestVoteIndex = 0;

        for(int i = 1; i < this.trainingModel.nr_class; ++i) {
            if(vote[i] > vote[largestVoteIndex]) {
                largestVoteIndex = i;
            }
        }

        return new PredictModel()
                .setClassification(this.trainingModel.label[largestVoteIndex])
                .setFx(Arrays.stream(fxStorage).sum());
    }
}
