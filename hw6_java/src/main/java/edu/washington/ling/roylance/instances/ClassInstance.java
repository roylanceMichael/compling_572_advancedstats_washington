package edu.washington.ling.roylance.instances;

import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Created by mroylance on 2/14/15.
 */
public class ClassInstance {
    private static final String DefaultName = "<default>";
    
    private String className;
    
    private double defaultProbability;
    
    private HashMap<String, Double> featureClassInstances;
    
    public ClassInstance() {
        this.featureClassInstances = new HashMap<>();
    }
    
    public String getClassName() {
        return this.className;
    }
    
    public double getDefaultProbability() {
        return this.defaultProbability;
    }
    
    public HashMap<String, Double> getFeatureClassInstances() {
        return this.featureClassInstances;
    }
    
    public ClassInstance setClassName(@NotNull String value) {
        this.className = value;
        return this;
    }
    
    public ClassInstance setDefaultProbability(double value) {
        this.defaultProbability = value;
        return this;
    }
    
    public ClassInstance addFeatureClassInstance(@NotNull String feature, double count) {
        this.featureClassInstances.put(feature, count);
        return this;
    }
    
    public static HashMap<String, ClassInstance> factory(@NotNull String fileName) {
        HashMap<String, ClassInstance> returnHashMap = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            try {
                ClassInstance currentClassInstance = null;

                String line;
                while ((line = br.readLine()) != null) {
                    String[] splitLine = ObjectUtilities.splitByWhiteSpace(line.trim());

                    // this is where we'll get the class name
                    if (splitLine.length > 2) {
                        if (currentClassInstance != null) {
                            returnHashMap.put(currentClassInstance.getClassName(), currentClassInstance);
                        }
                        
                        currentClassInstance = new ClassInstance()
                                .setClassName(splitLine[splitLine.length - 1]);
                    }
                    else if (splitLine.length == 2) {
                        if (currentClassInstance == null) {
                            continue;
                        }
                        
                        if (DefaultName.equals(splitLine[0])) {
                            currentClassInstance.setDefaultProbability(
                                    Double.parseDouble(splitLine[1])
                            );
                        } 
                        else {
                            currentClassInstance
                                    .addFeatureClassInstance(
                                            splitLine[0],
                                            Double.parseDouble(splitLine[1])
                                    );
                        }
                    }
                }
            }
            finally {
                if (br != null) {
                    br.close();
                }
            }
        }
        catch (Exception e) {
            // report the error to the console
            System.out.println(e);
        }

        return returnHashMap;
    }
}
