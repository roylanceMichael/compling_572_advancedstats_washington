package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.models.Transformation;
import edu.washington.ling.roylance.models.TransformationResult;
import edu.washington.ling.roylance.services.Store;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HistoryBuilder
        implements IBuilder<List<TransformationResult>> {

    private final Store store;

    private final double initialProbability;

    private final double minimumGain;

    private final boolean log;

    public HistoryBuilder(
            @NotNull Store store,
            double initialProbability,
            double minimumGain,
            boolean log) {
        this.store = store;
        this.initialProbability = initialProbability;
        this.minimumGain = minimumGain;
        this.log = log;
    }

    @Override
    public List<TransformationResult> build() {
        List<TransformationResult> history = new ArrayList<>();
        double lastCorrectAmount;
        double currentCorrectAmount = this.initialProbability;

        int stepNumber = 0;

        do {
            lastCorrectAmount = currentCorrectAmount;

            Transformation bestTransformation = new HighestTransformationBuilder(this.store).build();
            currentCorrectAmount = new ApplyHighestTransformationBuilder(this.store, bestTransformation).build();

            double gain = currentCorrectAmount - lastCorrectAmount;

            TransformationResult result = new TransformationResult(
                    ++stepNumber,
                    bestTransformation,
                    gain,
                    currentCorrectAmount / this.store.getInstanceSize());

            if (this.log) {
                System.out.println(result.toString());
            }

            history.add(result);
        } while (currentCorrectAmount - lastCorrectAmount >= this.minimumGain);

        return history;
    }
}
