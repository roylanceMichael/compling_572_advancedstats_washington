package edu.washington.ling.roylance.models;

import edu.washington.ling.roylance.utils.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ModelFile {
    private String initialClass;

    private List<Transformation> transformations;

    public ModelFile(
            @NotNull String initialClass,
            @NotNull List<Transformation> transformations) {
        this.initialClass = initialClass;
        this.transformations = transformations;
    }

    public String getInitialClass() {
        return this.initialClass;
    }

    public List<Transformation> getTransformations() {
        return this.transformations;
    }

    public static ModelFile factory(@NotNull String fileName) {
        List<Transformation> transformations = new ArrayList<>();
        String initialClass = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] splitLine = ObjectUtilities.splitByWhiteSpace(line.trim());

                    if (splitLine.length == 1) {
                        initialClass = splitLine[0];
                    }

                    if (splitLine.length > 3) {
                        Transformation transformation = new Transformation(
                                splitLine[0],
                                splitLine[1],
                                splitLine[2]
                        );
                        transformations.add(transformation);
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

        return new ModelFile(initialClass, transformations);
    }
}
