package edu.washington.ling.roylance.models;

import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

public class Feature {
    private static final String EqualsSign = "=";

    private String name;

    private String value;

    private int count;

    public String getName(){
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public int getCount() {
        return this.count;
    }

    public Feature setName(String value) {
        this.name = value;
        return this;
    }

    public Feature setValue(String value) {
        this.value = value;
        return this;
    }

    public Feature setCount (int value) {
        this.count = value;
        return this;
    }

    public static Feature factory(@NotNull String line) {
        String[] splitItems = line.split(EqualsSign);

        Feature returnFeature = new Feature();

        if (splitItems.length == 2) {
            return returnFeature
                    .setName(splitItems[0])
                    .setValue(splitItems[1]);
        }
        else if(splitItems.length == 1) {
            return returnFeature
                    .setName(splitItems[0])
                    .setValue(ObjectUtilities.EmptyString);
        }

        throw new UnsupportedOperationException("improperly formed string for feature: " + line);
    }
}
