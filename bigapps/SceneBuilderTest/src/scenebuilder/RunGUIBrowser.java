package scenebuilder;


import com.oracle.javafx.scenebuilder.app.SceneBuilderApp;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Browser;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.SceneDock;

import java.awt.AWTException;

/**
 *
 * @author andrey
 */
public class RunGUIBrowser {

    public static void main(String[] args) throws AWTException {
        new Thread(() -> Application.launch(SceneBuilderApp.class)).start();
        SceneDock mainScene = new SceneDock(new ByWindowType(Stage.class));
        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                mainScene.wrap().getControl().setOnKeyPressed(new EventHandler<KeyEvent>() {
                    boolean browserStarted = false;
                    @Override
                    public void handle(KeyEvent ke) {
                        if (!browserStarted && ke.isControlDown() && ke.isShiftDown() && ke.getCode() == KeyCode.B) {
                            browserStarted = true;
                            javafx.application.Platform.runLater(() -> Browser.runBrowser());
                        }
                    }
                });
            }
        }.dispatch(mainScene.wrap().getEnvironment());
        System.err.println("Click Ctrl-Shift-B to run FX Browser.");
    }
}
