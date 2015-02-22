package edu.washington.ling.roylance.utilities;

import org.jetbrains.annotations.NotNull;

public class ObjectUtilities {
    public static final String Utf8Encoding = "utf8";

    public static final String EmptyString = "";

    public static final String Tab = "\t";

    public static String[] splitByWhiteSpace(@NotNull String line) {
        return line.split("\\s+");
    }

    public static int ascOrder(int first, int second) {
        return first < second ? -1 : 1;
    }

    public static int descOrder(int first, int second) {
        return first < second ? 1 : -1;
    }
}
