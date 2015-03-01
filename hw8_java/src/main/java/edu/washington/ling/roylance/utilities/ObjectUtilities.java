package edu.washington.ling.roylance.utilities;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ObjectUtilities {
    public static final String Utf8Encoding = "utf8";

    public static final String EmptyString = "";

    public static final String Tab = "\t";

    public static final String NewLine = "\n";

    public static final String EqualsSign = "=";

    public static final String PlusSign = "+";

    public static final String Colon = ":";

    public static String[] splitByWhiteSpace(@NotNull String line) {
        return line.split("\\s+");
    }

    public static <T extends Comparable<T>> int ascOrder(T first, T second) {
        return first.compareTo(second);
    }

    public static <T extends Comparable<T>> int descOrder(T first, T second) {
        return second.compareTo(first);
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

    // http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java - thank you!
    public static <K, V extends Comparable<? super V>> HashMap<K, V> sortByValue(HashMap<K, V> map)
    {
        HashMap<K,V> result = new HashMap<>();
        Stream<Map.Entry<K,V>> st = map.entrySet().stream();

        st.sorted(Comparator.comparing(e -> e.getValue()))
                .forEach(e ->result.put(e.getKey(),e.getValue()));

        return result;
    }
}
