package edu.washington.ling.roylance.models.feature;

import edu.washington.ling.roylance.enums.FeatureNames;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

public class PreviousTag extends Feature {
    public PreviousTag() {
        this.setName(FeatureNames.PreviousTag);
    }

    public static String factory(@NotNull String tagName) {
        return FeatureNames.PreviousTag + ObjectUtilities.EqualsSign + tagName;
    }
}
