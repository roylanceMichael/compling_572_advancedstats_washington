package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.utilities.ObjectUtilities;
import libsvm.svm_node;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InstanceBuilder implements IBuilder<Map<Integer, svm_node[]>> {
    private final String fileName;

    public InstanceBuilder(
            @NotNull String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Map<Integer, svm_node[]> build() {
        final Map<Integer, svm_node[]> returnHashMap = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(this.fileName));
            try {
                int id = 0;
                String line;
                while ((line = br.readLine()) != null) {
                    String[] splitLine = ObjectUtilities.splitByWhiteSpace(line.trim());

                    svm_node[] nodes = new svm_node[splitLine.length - 1];

                    for (int i = 1; i < splitLine.length; i++) {
                        int index = i-1;
                        String[] splitItems = splitLine[i].split(ObjectUtilities.Colon);

                        nodes[index] = new svm_node();
                        nodes[index].index = Integer.parseInt(splitItems[0]);
                        nodes[index].value = Integer.parseInt(splitItems[1]);
                    }

                    returnHashMap.put(id, nodes);
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
