package edu.washington.ling.roylance.report;

import edu.washington.ling.roylance.instances.TestInstanceResult;
import edu.washington.ling.roylance.interfaces.IBuilder;
import edu.washington.ling.roylance.utilities.ObjectUtilities;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by mroylance on 2/14/15.
 */
public class PrintQ2SysFile implements IBuilder<Boolean> {
    
    private final String sysFile;
    
    private final List<TestInstanceResult> testResults;
    
    public PrintQ2SysFile(
            @NotNull String sysFile,
            @NotNull List<TestInstanceResult> testResults) {
        this.sysFile = sysFile;
        this.testResults = testResults;
    }

    @Override
    public Boolean build() {
        try {
            PrintWriter writer = new PrintWriter(sysFile, ObjectUtilities.Utf8Encoding);
            
            this.testResults
                    .forEach(testResult -> writer.println(testResult.toString()));
            
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
