package javafx.scene.control.test.chooser;

import java.io.IOException;
import java.nio.file.Paths;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test for RT-38103. Using file chooser with localized file filter on 32-bit linux crashes jvm.
 * That file executes LocalizedFilterFileChooserApp and detect its exit code.
 *
 * @author andrey.rusakov@oracle.com
 */
public class FileChooserTest {

    @Test
    public void localizedFilterCrashTest() throws IOException, InterruptedException {
        String classpath = System.getProperty("java.class.path");
        String path = Paths.get(System.getProperty("java.home"), "bin", "java").toString();
        ProcessBuilder processBuilder = new ProcessBuilder(path, "-cp", classpath,
                        LocalizedFilterTestApp.class.getCanonicalName());
        Process process = processBuilder.start();
        assertEquals("Process should exit, not crash.", 0, process.waitFor());
    }

}
