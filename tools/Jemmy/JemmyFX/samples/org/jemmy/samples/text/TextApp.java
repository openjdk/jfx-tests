/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.samples.text;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author shura
 */
public class TextApp extends Application {

    TextField singleLine;
    TextArea multiLine;
        
    @Override
    public void start(Stage stage) throws Exception {
        singleLine = new TextField();
        singleLine.setId("single");
        multiLine = new TextArea();
        reset();
        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                reset();
            }
        });
        VBox content = new VBox();
        content.getChildren().addAll(singleLine, multiLine);
        content.getChildren().add(reset);
        stage.setScene(new Scene(content));
        stage.show();
    }
    
    private void reset() {
        singleLine.setText("single line text");
        multiLine.setText("multi\nline\ntext\n");
    }
    
    public static void main(String[] args) {
        launch();
    }
    
}
