package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.enums.FeatureNames;
import edu.washington.ling.roylance.models.feature.Feature;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FeatureFactory implements IFactory<String, Optional<Feature>> {
    public static FeatureFactory instance;

    public static FeatureFactory getInstance() {
        if (instance == null) {
            instance = new FeatureFactory();
        }

        return instance;
    }

    @Override
    public Optional<Feature> create(@NotNull String input) {
        String featureName = null;
        String value = ObjectUtilities.EmptyString;

        if (!input.contains(ObjectUtilities.EqualsSign)) {
            featureName = input;
        }
        else {
            String[] items = input.split(ObjectUtilities.EqualsSign);

            if (items.length > 0) {
                featureName = items[0];
            }

            if (items.length > 1) {
                value = items[1];
            }
        }

        if (featureName == null) {
            return Optional.empty();
        }

        try {
            Feature newFeature = ((Feature)
                    Class.forName(FeatureNames.getClassName(featureName)).newInstance())
                    .setValue(value);

            return Optional.of(newFeature);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (InstantiationException e) {
            System.out.println(e);
        } catch (IllegalAccessException e) {
            System.out.println(e);
        }

        return Optional.empty();
    }
}
