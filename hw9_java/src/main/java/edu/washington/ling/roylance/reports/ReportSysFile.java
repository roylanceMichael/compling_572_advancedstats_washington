package edu.washington.ling.roylance.reports;

import edu.washington.ling.roylance.builders.IBuilder;
import edu.washington.ling.roylance.models.Instance;
import edu.washington.ling.roylance.utils.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;

public class ReportSysFile
    implements IBuilder<Boolean> {

    private final HashSet<Instance> instances;

    private final String sysFile;

    public ReportSysFile(
            @NotNull HashSet<Instance> instances,
            @NotNull String sysFile) {
        this.instances = instances;
        this.sysFile = sysFile;
    }

    @Override
    public Boolean build() {
        try {
            PrintWriter writer = new PrintWriter(this.sysFile, ObjectUtilities.Utf8Encoding);

            this
                    .instances
                    .stream()
                    .sorted((a, b) -> Integer.compare(a.getId(), b.getId()))
                    .forEach(instance -> {
                        writer.println(instance.toString());
                    });

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
