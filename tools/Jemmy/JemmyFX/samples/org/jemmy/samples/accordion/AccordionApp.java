/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jemmy.samples.accordion;

import java.awt.AWTException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccordionApp extends Application {
    Accordion accordion = new Accordion();

    public static void main(String[] args) throws AWTException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox box = new VBox();
        Scene scene = new Scene(box);


        accordion.getPanes().add(new TitledPane("First pane", new Label("First pane's content")));
        accordion.getPanes().add(new TitledPane("Second pane", new Label("Second pane's content")));

        box.getChildren().add(accordion);

        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                reset();
            }
        });
        box.getChildren().add(reset);

        stage.setScene(scene);

        stage.setWidth(300);
        stage.setHeight(300);

        stage.show();

        reset();
    }

    private void reset() {
        TitledPane pane = accordion.getExpandedPane();
        if (pane != null) {
            pane.setExpanded(false);
        }
        accordion.getPanes().get(1).setExpanded(true);
    }
}
