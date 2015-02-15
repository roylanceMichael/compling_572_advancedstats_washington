package edu.washington.ling.roylance.instances;

import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mroylance on 2/13/15.
 */
public class VectorInstance {
    private int id;
    
    private String classification;
    
    private final HashSet<String> features = new HashSet<>();
    
    public int getId() {
        return this.id;
    }
    
    public String getClassification() {
        return this.classification;
    }
    
    public HashSet<String> getFeatures() {
        return this.features;
    }
    
    public VectorInstance setId(int value) {
        this.id = value;
        return this;
    }
    
    public VectorInstance setClassification(@NotNull String value) {
        this.classification = value;
        return this;
    }
    
    public VectorInstance addFeature(@NotNull String value) {
        this.features.add(value);
        return this;
    }
    
    public static List<VectorInstance> factory(@NotNull String testFileName) {
        List<VectorInstance> returnVectors = new ArrayList<>();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(testFileName));
            try {
                int id = 0;
                String line;
                while ((line = br.readLine()) != null) {
                    String[] splitLine = ObjectUtilities.splitByWhiteSpace(line.trim());
                    
                    if (splitLine.length > 1) {
                        VectorInstance newInstance = new VectorInstance()
                                .setId(id)
                                .setClassification(splitLine[0]);

                        Arrays.stream(splitLine)
                                .skip(1)
                                .forEach(item -> newInstance.addFeature(item));
                        
                        returnVectors.add(newInstance);
                    }
                    
                    id++;
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
        
        return returnVectors;
    }
}
