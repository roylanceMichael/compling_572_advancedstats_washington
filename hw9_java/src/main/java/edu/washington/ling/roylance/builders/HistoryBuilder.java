package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.models.Transformation;
import edu.washington.ling.roylance.models.TransformationResult;
import edu.washington.ling.roylance.services.Store;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryBuilder
        implements IBuilder<List<TransformationResult>> {

    private final Store store;

    private final double initialProbability;

    private final double minimumGain;

    public HistoryBuilder(
            @NotNull Store store,
            double initialProbability,
            double minimumGain) {
        this.store = store;
        this.initialProbability = initialProbability;
        this.minimumGain = minimumGain;
    }

    @Override
    public List<TransformationResult> build() {
        List<TransformationResult> history = new ArrayList<>();
        double lastProbability;
        double currentProbability = this.initialProbability;

        int stepNumber = 0;

        do {
            lastProbability = currentProbability;

            Transformation bestTransformation = new HighestTransformationBuilder(this.store).build();
            currentProbability = new ApplyHighestTransformationBuilder(this.store, bestTransformation).build();
            double gain = currentProbability - lastProbability;
            TransformationResult result = new TransformationResult(
                    ++stepNumber,
                    bestTransformation,
                    gain,
                    currentProbability
            );
            history.add(result);
        } while (currentProbability - lastProbability >= this.minimumGain);

        return history;
    }
}
