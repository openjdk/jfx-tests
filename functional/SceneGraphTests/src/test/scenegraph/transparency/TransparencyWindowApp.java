/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.transparency;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
public class TransparencyWindowApp extends InteroperabilityApp {
//public class MouseEventApp extends Application {

    public static final int smallRectSize = 10;
    Scene scene;
    Pane root;
    Rectangle rectGreen;

//    @Override
//    public void start(Stage stage) throws Exception {
//        stage.setTitle(this.getClass().getSimpleName());
//        scene = getScene();
//        stage.setScene(scene);
//        stage.show();
//    }
    @Override
    protected Scene getScene() {

        root = new Pane();
        scene = new Scene(root, 320, 200, Color.WHITE);

        HBox hbox = new HBox();

        rectGreen = new Rectangle(50, 50, 100, 100);
        rectGreen.setFill(Color.GREEN);
        rectGreen.setId("RectGreen");
        root.getChildren().add(rectGreen);

        Button btnShowPopup = new Button("Show Popup");
        btnShowPopup.setId("BtnShowPopup");


        CheckBox chIsSupportedTransparentWindow = new CheckBox("Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW)");
        chIsSupportedTransparentWindow.setId("TRANSPARENT_WINDOW");
        chIsSupportedTransparentWindow.setSelected(Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW));

        hbox.getChildren().addAll(btnShowPopup, chIsSupportedTransparentWindow);
        root.getChildren().add(hbox);

        btnShowPopup.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                Popup popup = new Popup();
                popup.setWidth(stage.getWidth());
                popup.setHeight(stage.getHeight());
                popup.setOpacity(0.5);

                Pane rootPopup = new Pane();
                Rectangle rectBg = new Rectangle(0, 0, rectGreen.getWidth() + smallRectSize * 2, rectGreen.getHeight() + smallRectSize * 2);
                rectBg.setId("RectPopupBg");
                rectBg.setFill(Color.BLUE);
                rectBg.setOpacity(0.5);

                rootPopup.getChildren().add(rectBg);
                rootPopup.getChildren().add(new Rectangle(0, 0, smallRectSize, smallRectSize));
                rootPopup.getChildren().add(new Rectangle(rectBg.getWidth() - smallRectSize, rectBg.getHeight() - smallRectSize, smallRectSize, smallRectSize));
                rootPopup.getChildren().add(new Rectangle(0, rectBg.getHeight() - smallRectSize, smallRectSize, smallRectSize));
                rootPopup.getChildren().add(new Rectangle(rectBg.getWidth() - smallRectSize, 0, smallRectSize, smallRectSize));

                popup.getContent().add(rootPopup);

                popup.show(stage, 
                        stage.getX() + scene.getX() + rectGreen.getX() - smallRectSize, 
                        stage.getY() + scene.getY() + rectGreen.getY() - smallRectSize);
            }
        });

        Utils.addBrowser(scene);
        return scene;
    }

    public static void main(String[] args) {
        Utils.launch(TransparencyWindowApp.class, args);
        //Application.launch(MouseEventApp.class, args);
    }
}
