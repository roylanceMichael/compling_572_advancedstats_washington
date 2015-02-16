package edu.washington.ling.roylance.operations;

import edu.washington.ling.roylance.instances.ClassInstance;
import edu.washington.ling.roylance.interfaces.IPofYGivenX;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Created by mroylance on 2/15/15.
 */
public class ModelFileCalculation implements IPofYGivenX {
    private final HashMap<String, ClassInstance> internalStructure;
    
    public ModelFileCalculation(
            @NotNull HashMap<String, ClassInstance> classInstances) {
        this.internalStructure = classInstances;
    }

    @Override
    public double calculate(
            @NotNull String className, 
            @NotNull String featureName) {
        
        if (this.internalStructure
                .containsKey(className) && 
                this.internalStructure
                        .get(className)
                        .getFeatureClassInstances()
                        .containsKey(featureName)) {
            return 
                    this.internalStructure
                        .get(className)
                            .getFeatureClassInstances()
                            .get(featureName);
        }
        
        return 0.0;
    }
}
