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
package test.scenegraph.modality;

import java.awt.AWTException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jemmy.fx.Browser;
import test.javaclient.shared.AppLauncher;
import test.javaclient.shared.Utils;

/**
 *
 * @author alexander_kirov
 */
public class ModalityApp extends Application{
    public static Stage primaryStage = null;

    public static void main(String[] args) {
        AppLauncher.getInstance().launch(ModalityApp.class, args);
    }

    @Override
    public void start(final Stage primaryStage){
        InitScene scene = new InitScene();
        scene.startApp(primaryStage);
    }

    public class InitScene {

        private ModalityWindow mainwin_scene0 = null;


        public void startApp(final Stage primaryStageMain) {
            Button initButton = new Button(INIT_BUTTON_ID);
            initButton.setId(INIT_BUTTON_ID);
            initButton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent t) {
                    initApp();
                }
            });

            Button setStyleButton = new Button(HIERARHUCAL_BUTTON_ID);
            setStyleButton.setId(HIERARHUCAL_BUTTON_ID);
            setStyleButton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent t) {
                    ModalityWindow.setWindowsRenderType(ModalityWindow.WindowsRenderType.HIERARHICAL);
                }
            });

            primaryStageMain.setTitle(PRIMATY_STAGE_ID);
            primaryStageMain.setWidth(640);
            primaryStageMain.setHeight(200);
            primaryStageMain.setX(50);
            primaryStageMain.setY(0);

            Rectangle r = new Rectangle(100,100);
            r.setId(ModalityWindow.RECTANGLE_ID);
            r.setFill(Color.GREEN);

            HBox hb = new HBox();
            hb.getChildren().addAll(initButton, setStyleButton, r);

            VBox vb = new VBox();
            vb.getChildren().addAll(new Label("This is the primary window"), hb);

            Scene scene = new Scene(vb);
            addBrowser(scene);
            primaryStageMain.setScene(scene);
            primaryStageMain.show();
            primaryStage = primaryStageMain;

            //Modality stages setup: applet mode or not.
            if (AppLauncher.getInstance().getMode() == AppLauncher.Mode.REMOTE)
                ModalityWindow.isApplet = true;
        }

        public void initApp(){
            if (mainwin_scene0 != null) mainwin_scene0.getStage().hide();
            ModalityWindow.counter = 0;
            mainwin_scene0 = new ModalityWindow(Modality.NONE, primaryStage, false);
        }
    }

    public static void addBrowser(Scene scene) {
                scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            boolean browserStarted = false;

            public void handle(KeyEvent ke) {
                if (!browserStarted && ke.isControlDown() && ke.isShiftDown() && ke.getCode() == KeyCode.B) {
                    browserStarted = true;
                    Utils.deferAction(new Runnable() {
                        public void run() {
                            try {
                                Browser.runBrowser();
                            } catch (AWTException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    public static void tryToFront(){
        primaryStage.toFront();
    }

    public static final String PRIMATY_STAGE_ID = "primaryStage";
    public static final String INIT_BUTTON_ID = "Init";
    public static final String HIERARHUCAL_BUTTON_ID = "Hierarhical order";
}

