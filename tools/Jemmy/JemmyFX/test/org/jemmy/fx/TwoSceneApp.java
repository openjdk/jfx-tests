/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author shura
 */
public class TwoSceneApp extends Application {

    @Override
    public void start(Stage noFocus) throws Exception {
        createScene(new Stage(), 2);
        createScene(new Stage(), 1);
        final Text txt = new Text("not clicked");
        Rectangle r = new Rectangle(100, 100);
        r.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {txt.setText("clicked");});
        VBox content = new VBox();
        content.getChildren().addAll(r, txt);
        noFocus.setScene(new Scene(content));
        noFocus.setTitle("no focus");
        position(noFocus);
        noFocus.show();
    }

    private void position(Stage stage) {
        stage.setX(100);
        stage.setY(100);
        stage.setWidth(300);
        stage.setHeight(300);
    }

    private void createScene(Stage stage, int index) {
        final Label lbl = new Label("not pushed");
        Button btn = new Button("button");
        btn.setOnAction(t -> lbl.setText("pushed"));
        //btn
        VBox content = new VBox();
        content.getChildren().addAll(btn, lbl);
        stage.setScene(new Scene(content));
        stage.setTitle("stage" + index);
        position(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
