package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.model.Transformation;
import edu.washington.ling.roylance.services.Store;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class HistoryBuilder
        implements IBuilder<HashMap<Transformation, Double>> {

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
    public HashMap<Transformation, Double> build() {
        HashMap<Transformation, Double> history = new HashMap<>();
        double lastProbability = this.initialProbability;
        double currentProbability = this.initialProbability;

        do {
            lastProbability = currentProbability;

            Transformation bestTransformation = new HighestTransformationBuilder(this.store).build();
            currentProbability = new ApplyHighestTransformationBuilder(this.store, bestTransformation).build();

            System.out.println(
                    "found best transformation " +
                            bestTransformation.toString() +
                            " = " +
                            currentProbability);

            history.put(bestTransformation, currentProbability);
            System.out.println(currentProbability - lastProbability);
            System.out.println(this.minimumGain);
        } while (currentProbability - lastProbability >= this.minimumGain);

        return history;
    }
}
