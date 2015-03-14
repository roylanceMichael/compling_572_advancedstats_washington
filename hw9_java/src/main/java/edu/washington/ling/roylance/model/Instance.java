package edu.washington.ling.roylance.model;

import edu.washington.ling.roylance.services.Store;
import edu.washington.ling.roylance.utils.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;

public class Instance {
    private static final String FeatureDelimiter = ":";

    private static int idSequence = 0;

    private int id;

    private String goldClassification;

    private String currentClassification;

    private final HashSet<String> features = new HashSet<>();

    public int getId() {
        return this.id;
    }

    public String getGoldClassification() {
        return this.goldClassification;
    }

    public String getCurrentClassification() {
        return this.currentClassification;
    }

    public HashSet<String> getFeatures() {
        return this.features;
    }

    public Instance setId(int value) {
        this.id = value;
        return this;
    }

    public Instance setGoldClassification(@NotNull String value) {
        this.goldClassification = value;
        return this;
    }

    public Instance addFeature(@NotNull String value) {
        String[] splitItems = value.split(FeatureDelimiter);
        this.features.add(splitItems[0]);
        return this;
    }

    public Instance setCurrentClassification(@NotNull String value) {
        this.currentClassification = value;

        return this;
    }

    public boolean isCorrect() {
        return this.goldClassification.equals(this.currentClassification);
    }

    public boolean getTestTransformation(@NotNull Transformation transformation) {
        if (this.features.contains(transformation.getFeature()) &&
                this.currentClassification.equals(transformation.getFromClassification())) {
            return this.goldClassification.equals(transformation.getToClassification());
        }
        return this.isCorrect();
    }

    public boolean applyTransformation(@NotNull Transformation transformation) {
        if (this.features.contains(transformation.getFeature()) &&
                this.currentClassification.equals(transformation.getFromClassification())) {
            this.currentClassification = transformation.getToClassification();
        }
        return this.isCorrect();
    }

    public static Store factory(@NotNull String testFileName) {
        Store store = new Store();

        try {
            BufferedReader br = new BufferedReader(new FileReader(testFileName));
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] splitLine = ObjectUtilities.splitByWhiteSpace(line.trim());

                    if (splitLine.length > 1) {
                        Instance newInstance = new Instance()
                                .setId(idSequence)
                                .setGoldClassification(splitLine[0].intern());

                        Arrays.stream(splitLine)
                                .skip(1)
                                .forEach(item -> newInstance.addFeature(item.intern()));

                        store.addInstance(newInstance);
                    }

                    idSequence++;
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

        return store;
    }
}
