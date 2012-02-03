/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jemmy.fx.control;

import java.awt.AWTException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//import org.jemmy.fx.Browser;

public class ComboBoxApp  extends Application {
    public static void main(String[] args) throws AWTException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox box = new VBox();
        Scene scene = new Scene(box);

//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            boolean browserStarted = false;
//            public void handle(KeyEvent ke) {
//                if (!browserStarted && ke.isControlDown() && ke.isShiftDown() && ke.getCode() == KeyCode.B) {
//                    browserStarted = true;
//                    javafx.application.Platform.runLater(new Runnable() {
//                        public void run() {
//                            try {
//                                Browser.runBrowser();
//                            } catch (AWTException ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            }
//        });

        ComboBox combo = new ComboBox();
        for (int i = 0; i < 20; i++) {
            combo.getItems().add("Item " + i);
        }

        combo.setEditable(true);

        box.getChildren().add(combo);

        stage.setScene(scene);

        stage.setWidth(300);
        stage.setHeight(300);

        stage.show();
    }
}