package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class LabelBuilder implements IBuilder<Map<Integer, Integer>> {
    private final String fileName;

    public LabelBuilder(
            @NotNull String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Map<Integer, Integer> build() {
        final Map<Integer,Integer> returnHashMap = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(this.fileName));
            try {
                int id = 0;
                String line;
                while ((line = br.readLine()) != null) {
                    String[] splitLine = ObjectUtilities.splitByWhiteSpace(line.trim());

                    if (splitLine.length > 0) {
                        int classLabel = Integer.parseInt(splitLine[0]);
                        returnHashMap.put(id, classLabel);
                        id++;
                    }
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
