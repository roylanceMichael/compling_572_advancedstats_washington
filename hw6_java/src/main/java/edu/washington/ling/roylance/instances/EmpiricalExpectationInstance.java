package edu.washington.ling.roylance.instances;

import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by royla_000 on 2/15/2015.
 */
public class EmpiricalExpectationInstance {
    private String className;

    private String featureName;

    private double empiricalExpectation;

    private int featureCount;

    public String getClassName() {
        return this.className;
    }

    public String getFeatureName() {
        return this.featureName;
    }

    public double getEmpiricalExpectation() {
        return this.empiricalExpectation;
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
                this.empiricalExpectation +
                ObjectUtilities.Tab +
                this.featureCount;
    }

    public static List<EmpiricalExpectationInstance> factory(
            @NotNull List<VectorInstance> vectors) {
        List<EmpiricalExpectationInstance> returnList = new ArrayList<>();

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
                                EmpiricalExpectationInstance newInstance =
                                        new EmpiricalExpectationInstance();
                                newInstance.featureCount = countDictionary.get(key).get(subKey);
                                newInstance.className = key;
                                newInstance.featureName = subKey;
                                newInstance.empiricalExpectation =  newInstance.featureCount / totalCount;

                                returnList.add(newInstance);
                            });
                });

        return returnList;
    }
}
