package edu.washington.ling.roylance.operations;

import edu.washington.ling.roylance.instances.ClassInstance;
import edu.washington.ling.roylance.instances.VectorInstance;
import edu.washington.ling.roylance.interfaces.IPofYGivenX;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by mroylance on 2/15/15.
 */
public class ModelFileCalculation implements IPofYGivenX {
    private final HashMap<String, ClassInstance> internalStructure;
    
    private final HashMap<String, HashMap<VectorInstance, Double>> cachedProbabilities;
    
    public ModelFileCalculation(
            @NotNull HashMap<String, ClassInstance> classInstances) {
        this.internalStructure = classInstances;
        this.cachedProbabilities = new HashMap<>();
    }

    @Override
    public double calculate(
            @NotNull String className,
            @NotNull VectorInstance vectorInstance) {
        
        if (this.cachedProbabilities.containsKey(className) && 
                this.cachedProbabilities.get(className).containsKey(vectorInstance)) {
            return this.cachedProbabilities.get(className).get(vectorInstance);
        }
        
        HashMap<String, Double> classNumerators = new HashMap<>();
        
        this.internalStructure
                .keySet()
                .forEach(key -> {
                    ClassInstance classInstance = this.internalStructure.get(key);
                    double numerator = new CalculateProbabilityNumerator(classInstance, vectorInstance)
                            .build();
                    classNumerators.put(key, numerator);
                });
        
        double denominator = new CalculateProbabilityDenominator(classNumerators.values())
                .build();
        
        classNumerators
                .keySet()
                .forEach(key -> {
                    double probability = classNumerators.get(key) / denominator;
                    
                    if (this.cachedProbabilities.containsKey(key)) {
                        this.cachedProbabilities.get(key).put(vectorInstance, probability);
                    }
                    else {
                        HashMap<VectorInstance, Double> subDictionary = new HashMap<>();
                        subDictionary.put(vectorInstance, probability);
                        this.cachedProbabilities.put(key, subDictionary);
                    }
                });

        return this.cachedProbabilities.get(className).get(vectorInstance);
    }

    @Override
    public Set<String> getClassNames() {
        return this.internalStructure.keySet();
    }
}
