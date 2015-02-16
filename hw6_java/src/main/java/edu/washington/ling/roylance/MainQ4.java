package edu.washington.ling.roylance;

import edu.washington.ling.roylance.instances.ClassInstance;
import edu.washington.ling.roylance.instances.ExpectationInstance;
import edu.washington.ling.roylance.instances.VectorInstance;
import edu.washington.ling.roylance.interfaces.IPofYGivenX;
import edu.washington.ling.roylance.operations.ModelFileAbsentCalculation;
import edu.washington.ling.roylance.operations.ModelFileCalculation;
import edu.washington.ling.roylance.utilities.ObjectUtilities;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mroylance on 2/15/15.
 */
public class MainQ4 {
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("There must be 4 arguments!");
            return;
        }
        
        String trainFile = args[0];
        
        List<VectorInstance> trainingData = VectorInstance.factory(trainFile);
        IPofYGivenX pofYGivenX = new ModelFileAbsentCalculation(trainingData);

        if (args.length > 1 && args[1].trim().length() > 0) {
            HashMap<String, ClassInstance> classInstances 
                    = ClassInstance.factory(args[1]);
            
            pofYGivenX = new ModelFileCalculation(classInstances);
        }

        ExpectationInstance
                .modelFactory(trainingData, pofYGivenX)
                .forEach(expectation -> System.out.println(expectation.toString()));
    }
}
