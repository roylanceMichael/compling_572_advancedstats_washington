package edu.washington.ling.roylance.operations;

import edu.washington.ling.roylance.instances.VectorInstance;
import edu.washington.ling.roylance.interfaces.IPofYGivenX;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mroylance on 2/15/15.
 */
public class ModelFileAbsentCalculation implements IPofYGivenX {
    private final double classCountProbability;
    
    private final Set<String> classNames;
    
    public ModelFileAbsentCalculation(
            @NotNull List<VectorInstance> vectorInstances) {
        this.classNames = new HashSet<>();
        vectorInstances
                .stream()
                .map(vector -> vector.getClassification())
                .distinct()
                .forEach(className -> this.classNames.add(className));

        this.classCountProbability =
                1.0 / (this.classNames.size());
    } 

    @Override
    public double calculate(String className, VectorInstance vectorInstance) {
        return this.classCountProbability;
    }

    @Override
    public Set<String> getClassNames() {
        return this.classNames;
    }
}
