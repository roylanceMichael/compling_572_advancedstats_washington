package edu.washington.ling.roylance.utilities;

import org.jetbrains.annotations.NotNull;

/**
 * Created by mroylance on 2/13/15.
 */
public class ObjectUtilities {
    public static String[] splitByWhiteSpace(@NotNull String line) {
        return line.split("\\s+");
    }
}
