package org.jemmy.samples;

import javafx.application.Application;
import org.jemmy.fx.AppExecutor;
import org.jemmy.input.AWTRobotInputFactory;


/**
 *
 * @author shura
 */
public class SampleBase {

    private static final String osName = System.getProperty("os.name").toLowerCase();;

    static {
        if(osName.contains("mac os")) {
            AWTRobotInputFactory.runInOtherJVM(true);
        }
    }

    protected static void startApp(Class<? extends Application> app) throws InterruptedException {
        AppExecutor.executeNoBlock(app);
        if(osName.contains("mac os")) {
            Thread.sleep(1000);
        }
    }

}
