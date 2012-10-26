package scenebuilder;


import com.oracle.javafx.authoring.Main;
import java.awt.AWTException;
import javafx.application.Application;
import org.jemmy.fx.Browser;

/**
 *
 * @author andrey
 */
public class RunGUIBrowser {

    public static void main(String[] args) throws AWTException {
        new Thread(new Runnable() {

            public void run() {
                Application.launch(Main.class);
            }
        }).start();

        Browser.runBrowser();
    }
}
