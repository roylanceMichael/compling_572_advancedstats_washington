package edu.washington.ling.roylance.models.feature;

import edu.washington.ling.roylance.utilities.ObjectUtilities;

public class Feature {
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

    public String getRepresentation() {
        return this.name + ObjectUtilities.EqualsSign + this.value;
    }

    public Feature setValue(String value) {
        this.value = value;
        return this;
    }

    public Feature setCount (int value) {
        this.count = value;
        return this;
    }

    protected Feature setName(String value) {
        this.name = value;
        return this;
    }

    @Override
    public String toString() {
        if (ObjectUtilities.EmptyString.equals(this.value)) {
            return "<" + this.name + ">";
        }
        return "<" + this.name + ":" + this.value + ">";
    }
}
