package edu.washington.ling.roylance.report;

import edu.washington.ling.roylance.instances.TestInstanceResult;
import edu.washington.ling.roylance.interfaces.IBuilder;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by mroylance on 2/14/15.
 */
public class PrintQ2StdOut implements IBuilder<Boolean> {
    
    private final List<TestInstanceResult> testResults;
    
    public PrintQ2StdOut(
            @NotNull List<TestInstanceResult> testResults) {
        this.testResults = testResults;
    }
    
    @Override
    public Boolean build() {
        HashMap<String, HashMap<String, Integer>> reportingDictionary = 
                this.prepareHashMap();
        
        this.testResults.forEach(result -> {
            int existingValue = reportingDictionary
                    .get(result.getExpected())
                    .get(result.getActual());
            
            reportingDictionary
                    .get(result.getExpected())
                    .put(result.getActual(), existingValue + 1);
        });
        
        // print accuracy
        double totalAmount = this.testResults.size();
        double correctAmount = this.testResults.stream()
                .map(testResult -> testResult.getAccuracyResult())
                .reduce(0.0, (a, b) -> a + b);
        
        System.out.println("Accuracy: " + 
                correctAmount + 
                " / " + 
                totalAmount + 
                " : " + 
                (correctAmount / totalAmount));
        
        // print header
        this.printHeader(reportingDictionary);
        
        // print data
        reportingDictionary
                .keySet()
                .forEach(key -> {
                    StringBuilder stringBuilder = new StringBuilder(key);
                    
                    reportingDictionary.get(key)
                            .keySet()
                            .forEach(subKey -> stringBuilder.append(ObjectUtilities.Tab + reportingDictionary.get(key).get(subKey)));
                    
                    System.out.println(stringBuilder.toString());
                });

        return true;
    }
    
    private void printHeader(HashMap<String, HashMap<String, Integer>> reportingDictionary) {
        StringBuilder stringBuilder = new StringBuilder(ObjectUtilities.Tab);
        
        reportingDictionary
                .keySet()
                .forEach(key -> stringBuilder.append(key + ObjectUtilities.Tab));

        System.out.println(stringBuilder.toString());
    }
    
    private HashMap<String, HashMap<String, Integer>> prepareHashMap() {
        List<String> classNamesToCycle = new ArrayList<>();
        
        HashMap<String, HashMap<String, Integer>> returnHashMap = 
                new HashMap<>();

        this.testResults
                .stream()
                .map(testResult -> testResult.getExpected())
                .distinct()
                .forEach(className -> classNamesToCycle.add(className));

        classNamesToCycle
                .forEach(className -> {
                    HashMap<String, Integer> subHashMap
                            = new HashMap<>();

                    classNamesToCycle.forEach(subClassName -> subHashMap.put(subClassName, 0));

                    returnHashMap.put(className, subHashMap);
                });
        
        return returnHashMap;
    }
}
