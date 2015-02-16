package edu.washington.ling.roylance.instances;

import edu.washington.ling.roylance.interfaces.IPofYGivenX;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by royla_000 on 2/15/2015.
 */
public class ExpectationInstance {
    private String className;

    private String featureName;

    private double expectation;

    private double featureCount;

    public String getClassName() {
        return this.className;
    }

    public String getFeatureName() {
        return this.featureName;
    }

    public double getExpectation() {
        return this.expectation;
    }

    public double getFeatureCount() {
        return this.featureCount;
    }

    @Override
    public String toString() {
        return this.className +
                ObjectUtilities.Tab +
                this.featureName +
                ObjectUtilities.Tab +
                this.expectation +
                ObjectUtilities.Tab +
                this.featureCount;
    }
    
    public static List<ExpectationInstance> modelFactory(
            @NotNull List<VectorInstance> vectorInstances,
            @NotNull IPofYGivenX pofYGivenX) {
        List<ExpectationInstance> returnList = new ArrayList<>();

        HashMap<String, Integer> featureCount = new HashMap<>();
        HashMap<String, HashMap<String, Double>> modelExpectation
                = new HashMap<>();
        
        pofYGivenX
                .getClassNames()
                .forEach(className -> {
                    modelExpectation.put(className, new HashMap<>());
                });
        
        double totalCount = vectorInstances.size();
        double totalCountProbability = 1.0 / totalCount;
        
        vectorInstances
                .forEach(vectorInstance -> {
                    vectorInstance
                        .getFeatures()
                        .forEach(feature -> {
                                    int existingCount = featureCount.containsKey(feature) ?
                                            featureCount.get(feature) :
                                            0;
                                    
                                    featureCount.put(feature, existingCount + 1);
                                    
                                    pofYGivenX
                                            .getClassNames()
                                            .forEach(className -> {
                                        
                                                double existingValue = modelExpectation.get(className)
                                                        .containsKey(feature) ?
                                                        modelExpectation.get(className).get(feature) :
                                                        0.0;
                                                
                                                // we're caching, so this is effective
                                                double newCalculation = pofYGivenX.calculate(className, vectorInstance);
                                                
                                                modelExpectation.get(className)
                                                        .put(feature, existingValue + (newCalculation * totalCountProbability));
                                    });
                                }
                        );
        });

        modelExpectation
                .keySet()
                .forEach(className -> {
                    modelExpectation.get(className)
                            .keySet()
                            .forEach(featureName -> {
                                ExpectationInstance expectationInstance = new ExpectationInstance();
                                expectationInstance.className = className;
                                expectationInstance.featureName = featureName;
                                expectationInstance.expectation = modelExpectation.get(className).get(featureName);
                                expectationInstance.featureCount = expectationInstance.expectation * totalCount;
                                
                                returnList.add(expectationInstance);
                            });
                });
        


        return sortExpectationInstances(returnList);
    }

    public static List<ExpectationInstance> empiricalFactory(
            @NotNull List<VectorInstance> vectors) {
        List<ExpectationInstance> returnList = new ArrayList<>();

        HashMap<String, HashMap<String, Integer>> countDictionary
                = new HashMap<>();

        vectors
                .forEach(vector ->
                        vector
                        .getFeatures()
                        .forEach(feature -> {
                            if (countDictionary.containsKey(vector.getClassification())) {

                                int existingValue = countDictionary.get(vector.getClassification())
                                        .containsKey(feature) ?
                                        countDictionary.get(vector.getClassification()).get(feature) :
                                        0;

                                countDictionary.get(vector.getClassification())
                                        .put(feature, existingValue + 1);
                            } else {
                                HashMap<String, Integer> newMap = new HashMap<>();

                                newMap.put(feature, 1);

                                countDictionary.put(vector.getClassification(), newMap);
                            }
                        }));

        double totalCount = countDictionary
                .values()
                .stream()
                .map(dictionary ->
                        dictionary
                                .values()
                                .stream()
                                .reduce(0, (a, b) -> a + b))
                .reduce(0, (a, b) -> a + b);

        countDictionary
                .keySet()
                .forEach(key -> {
                    countDictionary
                            .get(key)
                            .keySet()
                            .forEach(subKey -> {
                                ExpectationInstance newInstance =
                                        new ExpectationInstance();
                                newInstance.className = key;
                                newInstance.featureName = subKey;
                                newInstance.expectation =  newInstance.featureCount / totalCount;
                                newInstance.featureCount = countDictionary.get(key).get(subKey);

                                returnList.add(newInstance);
                            });
                });

        return sortExpectationInstances(returnList);
    }
    
    private static List<ExpectationInstance> sortExpectationInstances(List<ExpectationInstance> expectationInstances) {
        Comparator<ExpectationInstance> byClassName = (a, b) ->
                a.className.compareTo(b.className);

        Comparator<ExpectationInstance> byFeatureName = (a, b) ->
                a.featureName.compareTo(b.featureName);

        expectationInstances.sort(byClassName.thenComparing(byFeatureName));
        
        return expectationInstances;
    }
}
