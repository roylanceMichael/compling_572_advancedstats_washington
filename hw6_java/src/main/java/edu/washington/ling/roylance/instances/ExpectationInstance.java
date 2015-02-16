package edu.washington.ling.roylance.instances;

import edu.washington.ling.roylance.interfaces.IPofYGivenX;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by royla_000 on 2/15/2015.
 */
public class ExpectationInstance {
    private String className;

    private String featureName;

    private double expectation;

    private int featureCount;

    public String getClassName() {
        return this.className;
    }

    public String getFeatureName() {
        return this.featureName;
    }

    public double getExpectation() {
        return this.expectation;
    }

    public int getFeatureCount() {
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
        
        List<String> allClasses = new ArrayList<>();
        
        HashMap<String, Integer> featureCountDictionary
                = new HashMap<>();
        
        vectorInstances
                .stream()
                .map(vectorInstance -> vectorInstance.getClassification())
                .distinct()
                .forEach(className -> allClasses.add(className));
        
        int totalCountProbability = 1 / vectorInstances.size();
        
        vectorInstances
                .forEach(vectorInstance -> {
                    vectorInstance
                            .getFeatures()
                            .forEach(feature -> {
                                int currentCount = 
                                        featureCountDictionary.containsKey(feature) ?
                                        featureCountDictionary.get(feature) :
                                        0;
                                
                                featureCountDictionary.put(feature, currentCount + 1);
                            });
                });
        
        allClasses
                .forEach(className -> 
                        featureCountDictionary
                            .keySet()
                            .forEach(feature -> {
                                ExpectationInstance newInstance = new ExpectationInstance();
                                newInstance.className = className;
                                newInstance.featureName = feature;
                                newInstance.expectation =
                                        pofYGivenX.calculate(className, feature) *
                                        totalCountProbability *
                                        featureCountDictionary.get(feature);
                                newInstance.featureCount = featureCountDictionary.get(feature);

                                returnList.add(newInstance);
                            }));
        
        return returnList;
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
                                newInstance.featureCount = countDictionary.get(key).get(subKey);
                                newInstance.className = key;
                                newInstance.featureName = subKey;
                                newInstance.expectation =  newInstance.featureCount / totalCount;

                                returnList.add(newInstance);
                            });
                });

        return returnList;
    }
}
