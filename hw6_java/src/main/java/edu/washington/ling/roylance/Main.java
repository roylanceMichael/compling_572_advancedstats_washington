package edu.washington.ling.roylance;

import edu.washington.ling.roylance.instances.ClassInstance;
import edu.washington.ling.roylance.instances.TestInstanceResult;
import edu.washington.ling.roylance.instances.VectorInstance;
import edu.washington.ling.roylance.operations.CalculateQ2;
import edu.washington.ling.roylance.report.PrintQ2StdOut;
import edu.washington.ling.roylance.report.PrintQ2SysFile;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mroylance on 2/13/15.
 */
public class Main {
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("There must be 4 arguments!");
            return;
        }
        
        String testDataFile = args[0];
        String modelFile = args[1];
        String sysOutput = args[2];
        
        List<VectorInstance> testInstances = VectorInstance.factory(testDataFile);
        HashMap<String, ClassInstance> model = ClassInstance.factory(modelFile);
        
        List<TestInstanceResult> testResults =
                new CalculateQ2(testInstances, model).build();
        
        // print the sys output file
        new PrintQ2SysFile(
                sysOutput,
                testResults
        ).build();
        
        new PrintQ2StdOut(testResults)
                .build();
    }
}
