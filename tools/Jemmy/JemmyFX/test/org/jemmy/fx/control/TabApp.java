/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import java.awt.AWTException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author shura
 */
public class TabApp  extends Application {
    public static void main(String[] args) throws AWTException {
//        org.jemmy.client.Browser.runBrowser();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox box = new VBox();
        Scene scene = new Scene(box);

        TabPane tp = new TabPane();
        Tab tab0 = new Tab("0");
        Control focusHolder = new TextField("lala");
        tab0.setContent(focusHolder);
        tp.getTabs().add(tab0);
        tp.getTabs().add(new Tab("1"));
        tp.getTabs().add(new Tab("2"));
        tp.getTabs().add(new Tab("3"));
        tp.getTabs().add(new Tab("4"));
        tp.getTabs().add(new Tab("5"));
        
        box.getChildren().add(tp);
        
        focusHolder.requestFocus();
        
        stage.setScene(scene);
        
        stage.setWidth(300);
        stage.setHeight(300);
        
        stage.show();
    }
}
