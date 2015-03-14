package edu.washington.ling.roylance;

import edu.washington.ling.roylance.builders.HistoryBuilder;
import edu.washington.ling.roylance.builders.InitialTransformationBuilder;
import edu.washington.ling.roylance.models.Instance;
import edu.washington.ling.roylance.models.TransformationResult;
import edu.washington.ling.roylance.reports.ReportModelFile;
import edu.washington.ling.roylance.services.Store;

import java.util.List;

public class MainQ1 {
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

        List<TransformationResult> history = new HistoryBuilder(
                store,
                initialCorrectProbability,
                minGain).build();

        new ReportModelFile(modelFile, store.getFirstClass(), history).build();

        System.out.println(history.get(history.size()-1).getCurrentProbability());
    }
}
