package edu.washington.ling.roylance.instances;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Created by mroylance on 2/14/15.
 */
public class TestInstanceResult {
    private static int idSequence = 0;
    
    private int id;
    
    private HashMap<String, Double> classResults;
    
    public TestInstanceResult() {
        this.classResults = new HashMap<>();
    }

    public int getId() {
        return this.id;
    }
    
    public TestInstanceResult addClassResult(@NotNull String className, double probability) {
        this.classResults.put(className, probability);
        return this;
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("array:" + Integer.toString(this.id) + " ");
        
        this.classResults
                .keySet()
                .forEach(key -> stringBuilder.append(key + " " + Double.toString(this.classResults.get(key)) + " "));
        
        return stringBuilder.toString();
    }

    public static TestInstanceResult factory() {
        TestInstanceResult testInstanceResult = new TestInstanceResult();
        testInstanceResult.id = idSequence;
        
        idSequence++;
        return testInstanceResult;
    }
}
