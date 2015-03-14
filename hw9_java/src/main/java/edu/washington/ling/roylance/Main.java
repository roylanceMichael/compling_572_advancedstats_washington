package edu.washington.ling.roylance;

import edu.washington.ling.roylance.builders.HistoryBuilder;
import edu.washington.ling.roylance.builders.InitialTransformationBuilder;
import edu.washington.ling.roylance.model.Instance;
import edu.washington.ling.roylance.model.Transformation;
import edu.washington.ling.roylance.services.Store;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length < 3) {
            System.out.println("There must be 3 arguments!");
            return;
        }

        String trainFile = args[0];
        String modelFile = args[1];
        Double minGain = Double.parseDouble(args[2]);

        Store store = Instance.factory(trainFile);

        double initialCorrectProbability = new InitialTransformationBuilder(store).build();
        Transformation transformation = new Transformation("<INIT>", "<INIT>", store.getFirstClass());
        System.out.println(
                "found best transformation " +
                        transformation.toString() +
                        " = " +
                        initialCorrectProbability);

        HashMap<Transformation, Double> history = new HistoryBuilder(
                store,
                initialCorrectProbability,
                minGain).build();
    }
}
