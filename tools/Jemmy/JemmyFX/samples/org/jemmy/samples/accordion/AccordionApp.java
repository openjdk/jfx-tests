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
    public static void main(String[] args) throws AWTException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox box = new VBox();
        Scene scene = new Scene(box);

        final Accordion accordion = new Accordion();

        accordion.getPanes().add(new TitledPane("First pane", new Label("First pane's content")));
        accordion.getPanes().add(new TitledPane("Second pane", new Label("Second pane's content")));

        box.getChildren().add(accordion);

        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                TitledPane pane = accordion.getExpandedPane();
                if (pane != null) {
                    accordion.getExpandedPane().setExpanded(false);
                }
            }
        });
        box.getChildren().add(reset);

        stage.setScene(scene);

        stage.setWidth(300);
        stage.setHeight(300);

        stage.show();
    }
}
