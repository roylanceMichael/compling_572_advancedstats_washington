package edu.washington.ling.roylance.models;

import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Tag {
    private static final String DefaultName = "<default>";

    private String tagName;

    private double defaultProbability;

    private HashMap<String, Double> featureClassInstances;

    public Tag() {
        this.featureClassInstances = new HashMap<>();
    }

    public String getTagName() {
        return this.tagName;
    }

    public double getDefaultProbability() {
        return this.defaultProbability;
    }

    public HashMap<String, Double> getFeatureClassInstances() {
        return this.featureClassInstances;
    }

    public Tag setTagName(@NotNull String value) {
        this.tagName = value;
        return this;
    }

    public Tag setDefaultProbability(double value) {
        this.defaultProbability = value;
        return this;
    }

    public Tag addFeatureClassInstance(@NotNull String feature, double count) {
        this.featureClassInstances.put(feature, count);
        return this;
    }

    public static HashMap<String, Tag> factory(@NotNull String fileName) {
        HashMap<String, Tag> returnHashMap = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            try {
                Tag currentTag = null;

                String line;
                while ((line = br.readLine()) != null) {
                    String[] splitLine = ObjectUtilities.splitByWhiteSpace(line.trim());

                    // this is where we'll get the class name
                    if (splitLine.length > 2) {
                        if (currentTag != null) {
                            returnHashMap.put(currentTag.getTagName(), currentTag);
                        }

                        currentTag = new Tag()
                                .setTagName(splitLine[splitLine.length - 1]);
                    }
                    else if (splitLine.length == 2) {
                        if (currentTag == null) {
                            continue;
                        }

                        if (DefaultName.equals(splitLine[0])) {
                            currentTag.setDefaultProbability(
                                    Double.parseDouble(splitLine[1])
                            );
                        }
                        else {
                            currentTag
                                    .addFeatureClassInstance(
                                            splitLine[0],
                                            Double.parseDouble(splitLine[1])
                                    );
                        }
                    }
                }

                returnHashMap.put(currentTag.getTagName(), currentTag);
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
