package edu.washington.ling.roylance.models.feature;

import edu.washington.ling.roylance.enums.FeatureNames;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

public class PreviousTwoTags extends Feature {
    public PreviousTwoTags() {
        this.setName(FeatureNames.PreviousTwoTags);
    }

    public static String factory(@NotNull String tagName) {
        return FeatureNames.PreviousTwoTags + ObjectUtilities.EqualsSign + tagName;
    }
}
