/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.stage;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
public class PopupApp extends InteroperabilityApp {
//public class PopupApp extends Application {

    public static String ID_BTN_SWOW_POPUP = "ID_BTN_SWOW_POPUP";
    public static String ID_LABEL_MOUSE_PRESS_COUNT_ON_SCENE = "ID_LABEL_MOUSE_PRESS_COUNT_ON_SCENE";
    public static String ID_LABEL_KEY_PRESS_COUNT_ON_SCENE = "ID_LABEL_KEY_PRESS_COUNT_ON_SCENE";
    public static String ID_LABEL_MOUSE_PRESS_COUNT_ON_POPUP = "ID_LABEL_MOUSE_PRESS_COUNT_ON_POPUP";
    public static String ID_LABEL_KEY_PRESS_COUNT_ON_POPUP = "ID_LABEL_KEY_PRESS_COUNT_ON_POPUP";
    public static String ID_CHBOX_AUTO_HIDE = "ID_CHBOX_AUTO_HIDE";
    public static String ID_CHBOX_CONSUME_AUTO_HIDING_EVENTS = "ID_CHBOX_CONSUME_AUTO_HIDING_EVENTS";
    public static String ID_CHBOX_HIDE_ON_ESCAPE = "ID_CHBOX_HIDE_ON_ESCAPE";
    private Scene scene;
    private VBox root;
    
    private static Popup popup;
    private CheckBox chAutoHide;
    private CheckBox chConsumeAutoHidingEvents;
    private CheckBox chHideOnEscape;
    private EventCounter evcntKeyOnPopup;
    private EventCounter evcntKeyOnScene;
    private EventCounter evcntMouseOnPopup;
    private EventCounter evcntMouseOnScene;

    private static final class EventCounter implements EventHandler<Event> {

        private int counter = 0;
        StringProperty strProp;

        private EventCounter(StringProperty strProp) {
            this.strProp = strProp;
        }

        private void reset() {
            counter = 0;
            strProp.setValue(Integer.toString(counter));
        }

        public void handle(final Event event) {
            ++counter;
            strProp.setValue(Integer.toString(counter));
        }
    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        stage.setTitle(this.getClass().getSimpleName());
//        scene = getScene();
//        stage.setScene(scene);
//        stage.show();
//    }
    @Override
    protected Scene getScene() {
        System.out.println(com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());

        root = new VBox();
        scene = new Scene(root, 320, 200, Color.WHITE);

        FlowPane hbox = new FlowPane();
        Button btnShowPopup = new Button("Show Popup");
        btnShowPopup.setId(ID_BTN_SWOW_POPUP);

        chAutoHide = new CheckBox("AutoHide");
        chAutoHide.setId(ID_CHBOX_AUTO_HIDE);
        chConsumeAutoHidingEvents = new CheckBox("ConsumeAutoHidingEvents");
        chConsumeAutoHidingEvents.setId(ID_CHBOX_CONSUME_AUTO_HIDING_EVENTS);
        chHideOnEscape = new CheckBox("HideOnEscape");
        chHideOnEscape.setId(ID_CHBOX_HIDE_ON_ESCAPE);

        hbox.getChildren().addAll(btnShowPopup, chAutoHide, chConsumeAutoHidingEvents, chHideOnEscape);

        HBox hboxEventsOnPopup = new HBox();
        Label lblMousePressOnPopup = new Label("0");
        lblMousePressOnPopup.setId(ID_LABEL_MOUSE_PRESS_COUNT_ON_POPUP);
        Label lblKbdPressOnPopup = new Label("0");
        lblKbdPressOnPopup.setId(ID_LABEL_KEY_PRESS_COUNT_ON_POPUP);
        hboxEventsOnPopup.getChildren().addAll(new Label("Mouse press on popup: "), lblMousePressOnPopup, new Label("    Key press on popup: "), lblKbdPressOnPopup);

        HBox hboxEventsOnScene = new HBox();
        Label lblMousePressOnScene = new Label("0");
        lblMousePressOnScene.setId(ID_LABEL_MOUSE_PRESS_COUNT_ON_SCENE);
        Label lblKbdPressOnScene = new Label("0");
        lblKbdPressOnScene.setId(ID_LABEL_KEY_PRESS_COUNT_ON_SCENE);
        hboxEventsOnScene.getChildren().addAll(new Label("Mouse press on scene: "), lblMousePressOnScene, new Label("    Key press on scene: "), lblKbdPressOnScene);

        root.getChildren().addAll(hbox, hboxEventsOnPopup, hboxEventsOnScene);

        evcntMouseOnScene = new EventCounter(lblMousePressOnScene.textProperty());
        evcntKeyOnScene = new EventCounter(lblKbdPressOnScene.textProperty());
        evcntMouseOnPopup = new EventCounter(lblMousePressOnPopup.textProperty());
        evcntKeyOnPopup = new EventCounter(lblKbdPressOnPopup.textProperty());

        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, evcntMouseOnScene);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, evcntKeyOnScene);

        btnShowPopup.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                initPopup();

                popup.setAutoHide(chAutoHide.isSelected());
                popup.setHideOnEscape(chHideOnEscape.isSelected());
                popup.setConsumeAutoHidingEvents(chConsumeAutoHidingEvents.isSelected());

                popup.show(stage,
                        stage.getX() + stage.getWidth() / 2 - popup.getWidth() / 2,
                        stage.getY() + stage.getHeight() - popup.getHeight());
            }
        });

        Utils.addBrowser(scene);
        return scene;
    }

    private void initPopup() {
        if (popup != null) {
            popup.hide();
        }

        popup = new Popup();
        popup.isShowing();
        popup.setWidth(100);
        popup.setHeight(100);
        Pane rootPopup = new Pane();
        Rectangle rectBg = new Rectangle(0, 0, popup.getWidth(), popup.getHeight());
        rectBg.setId("RectPopupBg");
        rectBg.setFill(Color.BLUE);

        rootPopup.getChildren().add(rectBg);
        popup.getContent().add(rootPopup);
        popup.addEventHandler(MouseEvent.MOUSE_PRESSED, evcntMouseOnPopup);
        popup.addEventHandler(KeyEvent.KEY_PRESSED, evcntKeyOnPopup);
        
        evcntMouseOnScene.reset();
        evcntKeyOnScene.reset();
        evcntMouseOnPopup.reset();
        evcntKeyOnPopup.reset();
    }

    public static boolean isPopupShowing() {
        return popup.isShowing();
    }

    public static void main(String[] args) {
        Utils.launch(PopupApp.class, args);
        //Application.launch(MouseEventApp.class, args);
    }
}
