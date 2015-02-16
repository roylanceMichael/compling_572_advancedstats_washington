package edu.washington.ling.roylance;

import edu.washington.ling.roylance.instances.ExpectationInstance;
import edu.washington.ling.roylance.instances.VectorInstance;

import java.util.List;

/**
 * Created by royla_000 on 2/15/2015.
 */
public class MainQ3 {
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("There must be 1 argument!");
            return;
        }

        String trainingFile = args[0];

        List<VectorInstance> vectorInstances = VectorInstance.factory(trainingFile);

        ExpectationInstance.empiricalFactory(vectorInstances)
                .forEach(instance -> System.out.println(instance.toString()));
    }
}
