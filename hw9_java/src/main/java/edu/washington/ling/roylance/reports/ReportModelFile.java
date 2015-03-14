package edu.washington.ling.roylance.reports;

import edu.washington.ling.roylance.builders.IBuilder;
import edu.washington.ling.roylance.models.TransformationResult;
import edu.washington.ling.roylance.utils.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ReportModelFile
        implements IBuilder<Boolean> {

    private final String modelFile;

    private final String initialClass;

    private final List<TransformationResult> transformationHistory;

    public ReportModelFile(
            @NotNull String modelFile,
            @NotNull String initialClass,
            @NotNull List<TransformationResult> transformationHistory) {
        this.modelFile = modelFile;
        this.initialClass = initialClass;
        this.transformationHistory = transformationHistory;
    }

    @Override
    public Boolean build() {
        try {
            PrintWriter writer = new PrintWriter(this.modelFile, ObjectUtilities.Utf8Encoding);

            writer.println(this.initialClass);

            this.transformationHistory
                    .forEach(transformation -> writer.println(transformation.toString()));

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
