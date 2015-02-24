package edu.washington.ling.roylance.models;

import com.google.common.base.Functions;
import com.google.common.collect.Ordering;
import edu.washington.ling.roylance.builders.FeatureFactory;
import edu.washington.ling.roylance.enums.FeatureNames;
import edu.washington.ling.roylance.models.feature.Feature;
import edu.washington.ling.roylance.models.feature.PreviousTag;
import edu.washington.ling.roylance.models.feature.PreviousTwoTags;
import edu.washington.ling.roylance.operations.CalculateProbabilityDenominator;
import edu.washington.ling.roylance.operations.CalculateProbabilityNumerator;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Word {
    private int id;

    private Feature lastFeature;

    private String instanceName;

    private String goldTag;

    private List<TagResult> potentialTags;

    private HashMap<String, Feature> instanceFeatures;

    public Word() {
        this.instanceFeatures = new HashMap<>();
        this.potentialTags = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public String getInstanceName() {
        return this.instanceName;
    }

    public String getGoldTag() {
        return this.goldTag;
    }

    public HashMap<String, Feature> getInstanceFeatures() {
        return this.instanceFeatures;
    }

    public List<TagResult> getPotentialTags() {
        return this.potentialTags;
    }

    public Word setId(int value) {
        this.id = value;
        return this;
    }

    public Word setInstanceName(String value) {
        this.instanceName = value;
        return this;
    }

    public Word setGoldTag(String value) {
        this.goldTag = value;
        return this;
    }

    public Word addFeatureItem(@NotNull String item) {
        if (ObjectUtilities.isInteger(item)) {

            if (this.lastFeature != null) {
                this.lastFeature
                        .setCount(Integer.parseInt(item));
            }

            return this;
        }

        FeatureFactory
                .getInstance()
                .create(item)
                .ifPresent(feature -> {
                    this.lastFeature = feature;
                    this.instanceFeatures.put(this.lastFeature.getName(), this.lastFeature);
                });

        return this;
    }

    public Set<String> getTopTags(int topN) {
        return this
                .potentialTags
                .stream()
                .limit(topN)
                .map(item -> item.getTag())
                .collect(Collectors.toCollection(HashSet::new));
    }

    public Word calculatePotentialTags(
            @NotNull PreviousTwoTags prevTwoTags,
            @NotNull PreviousTag prevTag,
            @NotNull HashMap<String, Tag> allTags){

        this.instanceFeatures.put(FeatureNames.PreviousTwoTags, prevTwoTags);
        this.instanceFeatures.put(FeatureNames.PreviousTag, prevTag);

        this.potentialTags.clear();

        HashMap<String, Double> tagResults = new HashMap<>();

        allTags
                .keySet()
                .forEach(tagKey ->
                        tagResults.put(
                            tagKey,
                            new CalculateProbabilityNumerator(allTags.get(tagKey), this).build()
                ));

        double denominator = new CalculateProbabilityDenominator(tagResults.values()).build();

        tagResults
                .entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .forEach(kvp ->
                        this.potentialTags.add(
                                new TagResult()
                                .setTag(kvp.getKey())
                                .setProbability(kvp.getValue() / denominator)
                        ));

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
                                .setGoldTag(splitLine[1]);

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
