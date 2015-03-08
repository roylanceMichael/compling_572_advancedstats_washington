package edu.washington.ling.roylance.report;

import edu.washington.ling.roylance.builders.IBuilder;
import edu.washington.ling.roylance.models.PredictModel;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ReportSysFile implements IBuilder<Boolean>{

    private final String fileName;

    private final Map<Integer, PredictModel> results;

    private final Map<Integer, Integer> classLabels;

    public ReportSysFile(
            @NotNull String fileName,
            @NotNull Map<Integer, PredictModel> results,
            @NotNull Map<Integer, Integer> classLabels) {
        this.fileName = fileName;
        this.results = results;
        this.classLabels = classLabels;
    }

    @Override
    public Boolean build() {
        try {
            PrintWriter writer = new PrintWriter(this.fileName, ObjectUtilities.Utf8Encoding);

            for (int i = 0; i < this.results.size(); i++) {
                int trueLabel = this.classLabels.get(i);
                int sysLabel = this.results.get(i).getClassification();
                double fx = this.results.get(i).getFx();
                writer.println(trueLabel + ObjectUtilities.Tab + sysLabel + ObjectUtilities.Tab + fx);
            }

            writer.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
        catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }

        return true;
    }
}
