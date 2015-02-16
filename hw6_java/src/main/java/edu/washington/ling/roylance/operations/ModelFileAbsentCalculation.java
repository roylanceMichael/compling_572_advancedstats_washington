package edu.washington.ling.roylance.operations;

import edu.washington.ling.roylance.instances.VectorInstance;
import edu.washington.ling.roylance.interfaces.IPofYGivenX;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by mroylance on 2/15/15.
 */
public class ModelFileAbsentCalculation implements IPofYGivenX {
    private final double classCountProbability;
    
    public ModelFileAbsentCalculation(
            @NotNull List<VectorInstance> vectorInstances) {
        this.classCountProbability =
                1 /
                        (vectorInstances
                .stream()
                .map(vector -> vector.getClassification())
                .distinct()
                .count());
    } 

    @Override
    public double calculate(
            String className, 
            String featureName) {
        return this.classCountProbability;
    }
}
