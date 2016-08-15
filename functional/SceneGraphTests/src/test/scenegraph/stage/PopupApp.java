/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 */
package test.scenegraph.stage;

import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import test.embedded.helpers.AbstractButton;
import test.embedded.helpers.AbstractCheckBox;
import test.embedded.helpers.ButtonBuilderFactory;
import test.embedded.helpers.CheckBoxBuilderFactory;
import test.embedded.helpers.OnClickHandler;
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
    public  AbstractCheckBox chAutoHide;
    private AbstractCheckBox chConsumeAutoHidingEvents;
    private AbstractCheckBox chHideOnEscape;
    private EventCounter evcntKeyOnPopup;
    private EventCounter evcntKeyOnScene;
    private EventCounter evcntMouseOnPopup;
    private EventCounter evcntMouseOnScene;

    private static final class EventCounter implements EventHandler<Event> {

        private int counter = 0;
        StringProperty strProp;
        Text label;

        private EventCounter(Text label) {
            this.label = label;
        }

        private void reset() {
            counter = 0;
            label.setText(Integer.toString(counter));
        }

        public void handle(final Event event) {
            ++counter;
            label.setText(Integer.toString(counter));
        }
    }

    private static void log(String s) {
        System.err.println(s);
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
        AbstractButton btnShowPopup = ButtonBuilderFactory.newButtonBuilder()
                .text("Show Popup")
                .id(ID_BTN_SWOW_POPUP)
                .setOnClickHandler(new OnClickHandler() {

                    @Override
                    public void onClick() {

                        log("=>btnShowPopup clicked");

                        initPopup();
                        boolean autoHide = chAutoHide.isChecked();
                        log("autoHide=" + autoHide);
                        popup.setAutoHide(autoHide);



                        popup.setHideOnEscape(chHideOnEscape.isChecked());
                        popup.setConsumeAutoHidingEvents(chConsumeAutoHidingEvents.isChecked());

                        popup.show(stage,
                                stage.getX() + stage.getWidth() / 2 - popup.getWidth() / 2,
                                stage.getY() + stage.getHeight() - popup.getHeight());
                            }

                })
                .build();

        chAutoHide = CheckBoxBuilderFactory.newCheckboxBuilder()
                .text("AutoHide")
                .id(ID_CHBOX_AUTO_HIDE)
                .build();
        chConsumeAutoHidingEvents = CheckBoxBuilderFactory.newCheckboxBuilder()
                .text("ConsumeAutoHidingEvents")
                .id(ID_CHBOX_CONSUME_AUTO_HIDING_EVENTS)
                .build();

        chHideOnEscape = CheckBoxBuilderFactory.newCheckboxBuilder()
                .text("HideOnEscape")
                .id(ID_CHBOX_HIDE_ON_ESCAPE)
                .build();

        hbox.getChildren().addAll(btnShowPopup.node(), chAutoHide.node(), chConsumeAutoHidingEvents.node(), chHideOnEscape.node());

        HBox hboxEventsOnPopup = new HBox();
        Text lblMousePressOnPopup = new Text("0");
        lblMousePressOnPopup.setId(ID_LABEL_MOUSE_PRESS_COUNT_ON_POPUP);

        Text lblKbdPressOnPopup = new Text("0");
        lblKbdPressOnPopup.setId(ID_LABEL_KEY_PRESS_COUNT_ON_POPUP);

        Text lblTitle1 = new Text("Mouse press on popup: ");
        Text lblTitle2 = new Text("    Key press on popup: ");


        hboxEventsOnPopup.getChildren().addAll(lblTitle1, lblMousePressOnPopup, lblTitle2, lblKbdPressOnPopup);

        HBox hboxEventsOnScene = new HBox();
        Text lblMousePressOnScene = new Text("0");
        lblMousePressOnScene.setId(ID_LABEL_MOUSE_PRESS_COUNT_ON_SCENE);

        Text lblKbdPressOnScene = new Text("0");
        lblKbdPressOnScene.setId(ID_LABEL_KEY_PRESS_COUNT_ON_SCENE);


        Text lblTitle3 = new Text("Mouse press on scene: ");
        Text lblTitle4 = new Text("    Key press on scene: ");

        hboxEventsOnScene.getChildren().addAll(lblTitle3, lblMousePressOnScene, lblTitle4, lblKbdPressOnScene);

        root.getChildren().addAll(hbox, hboxEventsOnPopup, hboxEventsOnScene);

        evcntMouseOnScene = new EventCounter(lblMousePressOnScene);
        evcntKeyOnScene = new EventCounter(lblKbdPressOnScene);
        evcntMouseOnPopup = new EventCounter(lblMousePressOnPopup);
        evcntKeyOnPopup = new EventCounter(lblKbdPressOnPopup);

        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, evcntMouseOnScene);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, evcntKeyOnScene);

        Utils.addBrowser(scene);
        return scene;
    }

    private void initPopup() {
        if (popup != null) {
            log("hiding popup");
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
