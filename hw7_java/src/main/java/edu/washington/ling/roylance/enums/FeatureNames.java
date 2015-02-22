package edu.washington.ling.roylance.enums;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class FeatureNames {
    public static final String CurrentWord = "curW";

    public static final String NextTwoWords = "next2W";

    public static final String NextWord = "nextW";

    public static final String Prefix = "pref";

    public static final String Suffix = "suf";

    public static final String PreviousTag = "prevT";

    public static final String PreviousTwoTags = "prev2T";

    public static final String PreviousWord = "prevW";

    public static final String PreviousTwoWords = "prev2W";

    public static final String ContainsUpperCase = "containUC";

    public static final String ContainsNumber = "containNum";

    private static final HashMap<String, String> FeatureNamesToClassNames = new HashMap<>();

    public static String getClassName(@NotNull String featureName) {
        populateFeatureNamesIfEmpty();
        return FeatureNamesToClassNames.get(featureName);
    }

    private static void populateFeatureNamesIfEmpty() {
        if (FeatureNamesToClassNames.isEmpty()) {
            FeatureNamesToClassNames.put(CurrentWord,
                    edu.washington.ling.roylance.models.feature.CurrentWord.class.getName());

            FeatureNamesToClassNames.put(NextTwoWords,
                    edu.washington.ling.roylance.models.feature.NextTwoWords.class.getName());

            FeatureNamesToClassNames.put(NextWord,
                    edu.washington.ling.roylance.models.feature.NextWord.class.getName());

            FeatureNamesToClassNames.put(Prefix,
                    edu.washington.ling.roylance.models.feature.Prefix.class.getName());

            FeatureNamesToClassNames.put(Suffix,
                    edu.washington.ling.roylance.models.feature.Suffix.class.getName());

            FeatureNamesToClassNames.put(PreviousTag,
                    edu.washington.ling.roylance.models.feature.PreviousTag.class.getName());

            FeatureNamesToClassNames.put(PreviousTwoTags,
                    edu.washington.ling.roylance.models.feature.PreviousTwoTags.class.getName());

            FeatureNamesToClassNames.put(PreviousWord,
                    edu.washington.ling.roylance.models.feature.PreviousWord.class.getName());

            FeatureNamesToClassNames.put(PreviousTwoWords,
                    edu.washington.ling.roylance.models.feature.PreviousTwoWords.class.getName());

            FeatureNamesToClassNames.put(ContainsUpperCase,
                    edu.washington.ling.roylance.models.feature.ContainsUpperCase.class.getName());

            FeatureNamesToClassNames.put(ContainsNumber,
                    edu.washington.ling.roylance.models.feature.ContainsNumber.class.getName());
        }
    }
}
