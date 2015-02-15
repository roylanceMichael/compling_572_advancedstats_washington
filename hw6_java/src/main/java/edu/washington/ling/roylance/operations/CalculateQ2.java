package edu.washington.ling.roylance.operations;

import edu.washington.ling.roylance.instances.ClassInstance;
import edu.washington.ling.roylance.instances.TestInstanceResult;
import edu.washington.ling.roylance.instances.VectorInstance;
import edu.washington.ling.roylance.interfaces.IBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mroylance on 2/14/15.
 */
public class CalculateQ2 implements IBuilder<List<TestInstanceResult>> {
    
    private final List<VectorInstance> testInstances;
    
    private final HashMap<String, ClassInstance> model;
    
    private final HashSet<String> allClassNames;
    
    public CalculateQ2(
            @NotNull List<VectorInstance> testInstances,
            @NotNull HashMap<String, ClassInstance> model) {
        this.testInstances = testInstances;
        this.model = model;
        this.allClassNames = new HashSet<>();
        
        // add in the class names
        this.testInstances
                .stream()
                .map(testInstance -> testInstance.getClassification())
                .distinct()
                .forEach(className -> this.allClassNames.add(className));
    }
    
    @Override
    public List<TestInstanceResult> build() {
        List<TestInstanceResult> returnList = new ArrayList<>();
        
        this.testInstances
                .forEach(testInstance -> {
                    TestInstanceResult testInstanceResult = TestInstanceResult.factory();
                    
                    HashMap<String, Double> classNumerators = new HashMap<>();
                    
                    this.model.values()
                            .forEach(classInstance -> {
                                classNumerators.put(
                                        classInstance.getClassName(),
                                        new CalculateProbabilityNumerator(classInstance, testInstance).build()
                                );
                            });
                    
                    double denominator = new CalculateProbabilityDenominator(classNumerators.values()).build();
                    
                    this.allClassNames
                            .forEach(key -> {
                                        if (classNumerators.containsKey(key)) {
                                            testInstanceResult.addClassResult(key, classNumerators.get(key) / denominator);
                                        }
                                        else {
                                            testInstanceResult.addClassResult(key, 0);
                                        }
                                    }
                            );
                    
                    returnList.add(testInstanceResult);
                });

        return returnList;
    }
}
