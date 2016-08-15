/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jemmy.client;

import org.jemmy.fx.Browser;
import java.awt.AWTException;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author shura
 */
public class TestBrowser extends Application {
    public static void main(final String[] args) throws AWTException {
        new Thread(new Runnable() {

            public void run() {
                launch(TestBrowser.class, args);
            }
        }).start();
        Browser.runBrowser();
    }

    protected Label lbl = new Label();

    @Override
    public void start(Stage stg) throws Exception {
        Scene scene = new Scene(new Group());
        VBox root = new VBox();
        scene.setRoot(root);

        MenuButton menu_button = new MenuButton("MenuButton");
        addMenus(menu_button.getItems());
        root.getChildren().add(menu_button);

        MenuBar bar = new MenuBar();
        addMenus(bar.getMenus());
        root.getChildren().add(bar);

        Group grp = new Group();
        grp.setLayoutX(0);
        grp.setLayoutY(0);
        HBox hBox = new HBox();
        grp.getChildren().add(hBox);
        Button btn = new Button();
        btn.setText("push me");
        lbl.setText("not yet pushed");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                lbl.setText("button pushed!");
            }
        });
        hBox.getChildren().add(btn);
        hBox.getChildren().add(lbl);
        root.getChildren().add(grp);


        stg.setScene(scene);
        stg.setWidth(300);
        stg.setHeight(300);
        stg.show();
    }
    protected void addMenus(ObservableList list) {
        Menu menu0 = new Menu("menu0");
        MenuItem item0 = new MenuItem("item0");
        item0.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                lbl.setText("item0 pushed!");
            }
        });
        menu0.getItems().add(item0);
        list.add(menu0);

        Menu menu1 = new Menu("menu1");
        Menu sub_menu1 = new Menu("sub-menu1");
        Menu sub_sub_menu1 = new Menu("sub-sub-menu1");
        MenuItem item1 = new MenuItem("item1");
        item1.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                lbl.setText("item1 pushed!");
            }
        });
        sub_menu1.getItems().add(sub_sub_menu1);
        sub_sub_menu1.getItems().add(item1);
        menu1.getItems().add(sub_menu1);
        list.add(menu1);

        Menu menu2 = new Menu("menu2");
        menu2.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                lbl.setText("menu2 pushed!");
            }
        });
        list.add(menu2);
    }
}
