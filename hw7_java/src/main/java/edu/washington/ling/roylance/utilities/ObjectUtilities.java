package edu.washington.ling.roylance.utilities;

import org.jetbrains.annotations.NotNull;

public class ObjectUtilities {
    public static final String Utf8Encoding = "utf8";

    public static final String EmptyString = "";

    public static final String Tab = "\t";

    public static final String EqualsSign = "=";

    public static final String PlusSign = "+";

    public static String[] splitByWhiteSpace(@NotNull String line) {
        return line.split("\\s+");
    }

    public static int ascOrder(int first, int second) {
        return first < second ? -1 : 1;
    }

    public static int descOrder(int first, int second) {
        return first < second ? 1 : -1;
    }

    // http://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java - thank you!
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
