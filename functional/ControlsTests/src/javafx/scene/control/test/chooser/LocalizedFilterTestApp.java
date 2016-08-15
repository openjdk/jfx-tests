package javafx.scene.control.test.chooser;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Using file chooser with localized file filter on 32-bit linux crashes jvm.
 * That file is executed by FileChooserTest.testLocalizedFilter.
 *
 * @author andrey.rusakov@oracle.com
 */
public class LocalizedFilterTestApp extends Application {

    private static final long KILL_WAIT = 5000;

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                System.exit(0);
            }
        }, KILL_WAIT);
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("\u0424\u0430\u0439\u043B XML", "*.xml"));
        fileChooser.showOpenDialog(primaryStage);
    }
}