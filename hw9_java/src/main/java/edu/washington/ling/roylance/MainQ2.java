package edu.washington.ling.roylance;

import edu.washington.ling.roylance.builders.ApplyAllTransformationsBuilder;
import edu.washington.ling.roylance.models.Instance;
import edu.washington.ling.roylance.models.ModelFile;
import edu.washington.ling.roylance.reports.ReportSysFile;
import edu.washington.ling.roylance.services.Store;

public class MainQ2 {
    public static void main(String[] args) {
        if (args == null || args.length < 3) {
            System.out.println("There must be 3 arguments!");
            return;
        }

        String testFile = args[0];
        String modelFile = args[1];
        String sysFile = args[2];
        String n = args[3];

        Store store = Instance.factory(testFile);
        ModelFile convertedModelFile = ModelFile.factory(modelFile);

        new ApplyAllTransformationsBuilder(
                store,
                convertedModelFile.getTransformations(),
                convertedModelFile.getInitialClass()).build();

        new ReportSysFile(store.getInstances(), sysFile).build();

        double correct = store.getInstances().stream().filter(instance -> instance.isCorrect()).count();

        System.out.println(correct / store.getInstanceSize());
    }
}
