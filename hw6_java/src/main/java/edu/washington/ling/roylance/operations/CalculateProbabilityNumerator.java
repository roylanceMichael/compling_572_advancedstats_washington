package edu.washington.ling.roylance.operations;

import edu.washington.ling.roylance.instances.ClassInstance;
import edu.washington.ling.roylance.instances.VectorInstance;
import edu.washington.ling.roylance.interfaces.IBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Created by mroylance on 2/14/15.
 */
public class CalculateProbabilityNumerator implements IBuilder<Double> {
    private final ClassInstance classInstance;
    
    private final VectorInstance vectorInstance;
    
    public CalculateProbabilityNumerator(
            @NotNull ClassInstance classInstance,
            @NotNull VectorInstance vectorInstance) {
        this.classInstance = classInstance;
        this.vectorInstance = vectorInstance;
    }
    
    @Override
    public Double build() {
        double addDefaultAndXResult = 
                this.addDefaultAndX();

        return Math.pow(
                Math.E,
                addDefaultAndXResult
        );
    }
    
    private double addDefaultAndX() {
        double returnResult = this.classInstance.getDefaultProbability()
                +
            this.vectorInstance
                    .getFeatures()
                    .stream()
                    .map(feature -> {
                        if (this.classInstance.getFeatureClassInstances().containsKey(feature)) {
                                return this.classInstance.getFeatureClassInstances().get(feature);
                            }
                            return 0.0;
                        })
                    .reduce(0.0, (a, b) -> a + b);
        
        return returnResult;
    }
}
