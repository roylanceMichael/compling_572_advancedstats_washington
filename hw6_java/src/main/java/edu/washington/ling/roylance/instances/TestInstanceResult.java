package edu.washington.ling.roylance.instances;

import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Created by mroylance on 2/14/15.
 */
public class TestInstanceResult {
    private static int idSequence = 0;
    
    private int id;
    
    private String expected;
    
    private HashMap<String, Double> classResults;
    
    public TestInstanceResult() {
        this.classResults = new HashMap<>();
    }

    public int getId() {
        return this.id;
    }
    
    public String getExpected() {
        return this.expected;
    }
    
    public double getAccuracyResult() {
        if (this.expected == null || this.getActual() == null) {
            return 0.0;
        }
        
        return this.expected.equals(this.getActual()) ? 1.0 : 0.0;
    }
    
    public TestInstanceResult setExpected(@NotNull String value) {
        this.expected = value;
        return this;
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
    
    public String getActual() {
        double maxDouble = 0.0;
        String maxKey = ObjectUtilities.EmptyString;
        
        for (String key: this.classResults.keySet()) {
            if (this.classResults.get(key) > maxDouble) {
                maxDouble = this.classResults.get(key);
                maxKey = key;
            }
        }
        
        return maxKey;
    }

    public static TestInstanceResult factory() {
        TestInstanceResult testInstanceResult = new TestInstanceResult();
        testInstanceResult.id = idSequence;
        
        idSequence++;
        return testInstanceResult;
    }
}
