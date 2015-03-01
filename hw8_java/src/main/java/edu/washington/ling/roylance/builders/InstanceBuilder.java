package edu.washington.ling.roylance.builders;

import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;

public class InstanceBuilder implements IBuilder<Double[]> {
    private final String fileName;

    public InstanceBuilder(
            @NotNull String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Double[] build() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.fileName));
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] splitLine = ObjectUtilities.splitByWhiteSpace(line.trim());


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
        return new Double[0];
    }
}
