package edu.washington.ling.roylance.models;

import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Word {
    private int id;

    private String temporaryFeature;

    private String instanceName;

    private String goldClass;

    private HashMap<String, String> features;

    public Word() {
        this.features = new HashMap<>();
    }

    public int getId() {
        return this.id;
    }

    public String getInstanceName() {
        return this.instanceName;
    }

    public String getGoldClass() {
        return this.goldClass;
    }

    public HashMap<String, String> getFeatures() {
        return this.features;
    }

    public Word setId(int value) {
        this.id = value;
        return this;
    }

    public Word setInstanceName(String value) {
        this.instanceName = value;
        return this;
    }

    public Word setGoldClass(String value) {
        this.goldClass = value;
        return this;
    }

    public Word addFeatureItem(String item) {
        if (this.temporaryFeature == null) {
            this.temporaryFeature = item;
            return this;
        }

        this.addFeature(this.temporaryFeature, item);
        this.temporaryFeature = null;
        return this;
    }

    public Word addFeature(String key, String value) {
        this.features.put(key, value);
        return this;
    }

    public static List<Word> factory(@NotNull String fileName) {
        List<Word> returnList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            try {
                String line;
                int id = 0;
                while ((line = br.readLine()) != null) {
                    String[] splitLine = ObjectUtilities.splitByWhiteSpace(line.trim());

                    if (splitLine.length > 3) {
                        Word currentTestInstance =
                                new Word()
                                .setId(id)
                                .setInstanceName(splitLine[0])
                                .setGoldClass(splitLine[1]);

                        Arrays.stream(splitLine)
                                .skip(2)
                                .forEach(currentTestInstance::addFeatureItem);

                        returnList.add(currentTestInstance);
                        id++;
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

        return returnList;
    }
}
