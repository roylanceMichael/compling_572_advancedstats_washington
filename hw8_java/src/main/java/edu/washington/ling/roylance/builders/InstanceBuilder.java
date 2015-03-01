package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;

public class InstanceBuilder implements IBuilder<HashMap<Integer, HashMap<Integer, Integer>>> {
    private final String fileName;

    public InstanceBuilder(
            @NotNull String fileName) {
        this.fileName = fileName;
    }

    @Override
    public HashMap<Integer, HashMap<Integer, Integer>> build() {
        final HashMap<Integer, HashMap<Integer, Integer>> returnHashMap = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(this.fileName));
            try {
                int id = 0;
                String line;
                while ((line = br.readLine()) != null) {
                    HashMap<Integer, Integer> featureHashMap = new HashMap<>();
                    String[] splitLine = ObjectUtilities.splitByWhiteSpace(line.trim());

                    Arrays.stream(splitLine)
                            .skip(1)
                            .forEach(lineItem -> {
                                String[] splitItems = lineItem.split(ObjectUtilities.Colon);

                                if (splitItems.length == 2) {
                                    featureHashMap
                                            .put(
                                                    Integer.parseInt(splitItems[0]),
                                                    Integer.parseInt(splitItems[1]));
                                }
                            });

                    returnHashMap.put(id, featureHashMap);
                    id++;
                }
            }
            finally {
                if (br != null) {
                    br.close();
                }
            }
        }
        catch (Exception e) {
            // report the error to the console
            System.out.println(e);
        }

        return returnHashMap;
    }
}
