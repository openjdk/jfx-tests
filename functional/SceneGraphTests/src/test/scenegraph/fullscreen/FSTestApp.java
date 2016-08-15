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
package test.scenegraph.fullscreen;



import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.LineBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import test.javaclient.shared.Utils;

/**
*
* @author alexander
*/
public class FSTestApp extends Application {

    //if stage resizable
    private static boolean resizable = true;


    private final StringBuilder log = new StringBuilder();

    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) {
        if((args != null) && (args.length != 0))
            resizable = Boolean.valueOf(args[0]);

        Utils.launch(FSTestApp.class, args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("FSTestApp");
        primaryStage.setResizable(resizable);

        GridPane buttonsPane = GridPaneBuilder.create()
                .padding(new Insets(10))
                .alignment(Pos.CENTER)
                .columnConstraints(new ColumnConstraints(200), new ColumnConstraints(200))
                .rowConstraints(new RowConstraints(30), new RowConstraints(30),
                        new RowConstraints(30), new RowConstraints(30),
                        new RowConstraints(30))
                .build();

        final TextArea logTextArea = TextAreaBuilder.create()
                .editable(false)
                .build();

        //Timer for update logTextArea
        Timeline updateLogTimeline = TimelineBuilder.create()
                .keyFrames(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent t) {
                        logTextArea.insertText(logTextArea.getLength(), log.toString());
                        log.delete(0, log.length());

                    }
                }, ( KeyValue[])null))  // cast null to suppress compiler warning
                .cycleCount(-1)
                .build();

        //<editor-fold defaultstate="collapsed" desc="Menu">
        MenuBar menu = MenuBarBuilder.create()
                .menus(
                MenuBuilder.create()
                    .text("_Menu1")
                    .onShown(new EventHandler<Event>() {

                        public void handle(Event t) {
                            log.append("Menu 1 Shown\n");
                        }
                    })
                    .onHidden(new EventHandler<Event>() {

                        public void handle(Event t) {
                            log.append("Menu 1 Hidden\n");
                        }
                    })
                    .mnemonicParsing(true)
                    .items(
                        MenuItemBuilder.create()
                            .mnemonicParsing(true)
                            .onAction(new EventHandler<ActionEvent>() {

                                public void handle(ActionEvent t) {
                                    log.append("Menu Item 1 Action\n");
                                }
                            })
                            .accelerator(KeyCombination.keyCombination("ctrl+m"))
                            .text("Menu _Item 1")
                            .build(),
                        MenuItemBuilder.create()
                            .mnemonicParsing(true)
                            .text("Menu I_tem 2")
                            .build())
                    .build(),
                MenuBuilder.create()
                    .mnemonicParsing(true)
                    .text("M_enu2")
                    .onShown(new EventHandler<Event>() {

                        public void handle(Event t) {
                            log.append("Menu 2 Shown\n");
                        }
                    })
                    .onHidden(new EventHandler<Event>() {

                        public void handle(Event t) {
                            log.append("Menu 2 Hidden\n");
                        }
                    })
                    .items(
                        MenuItemBuilder.create()
                            .mnemonicParsing(true)
                            .text("Menu _Item 1")
                            .build(),
                        MenuItemBuilder.create()
                            .mnemonicParsing(true)
                            .text("Menu I_tem 2")
                            .build(),
                        MenuItemBuilder.create()
                            .mnemonicParsing(true)
                            .text("Menu Ite_m 3")
                            .build())
                    .build())
                .useSystemMenuBar(true)
                .build();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Fullscreen indicator">
        final Circle fullscreenIndicator =
                CircleBuilder.create()
                .radius(6)
                .fill(Color.RED)
                .effect(new InnerShadow())
                .build();

        primaryStage.fullScreenProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                fullscreenIndicator.setFill(newValue.booleanValue() ? Color.GREEN : Color.RED);
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Fullscreen false Button">
        Button setFSFalseButton =  ButtonBuilder.create()
                .text("Set fullscreen false")
                .alignment(Pos.CENTER)
                .prefWidth(180)
                .onMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent arg0) {
                        primaryStage.setFullScreen(false);
                    }
                })
                .build();

        buttonsPane.add(setFSFalseButton, 0, 0);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Fullscreen true Button">
        Button setFSTrueButton =  ButtonBuilder.create()
                .text("Set fullscreen true")
                .alignment(Pos.CENTER)
                .prefWidth(180)
                .onMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent arg0) {
                        primaryStage.setFullScreen(true);
                    }
                })
                .build();

        buttonsPane.add(setFSTrueButton, 1, 0);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Center Button">
        Button centerButton =  ButtonBuilder.create()
                .text("Center")
                .alignment(Pos.CENTER)
                .prefWidth(180)
                .onMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent arg0) {
                        primaryStage.centerOnScreen();
                    }
                })
                .build();

        buttonsPane.add(centerButton, 0, 1);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Close Button">
        Button closeButton =  ButtonBuilder.create()
                .text("Close")
                .alignment(Pos.CENTER)
                .prefWidth(180)
                .onMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent arg0) {
                        primaryStage.close();
                    }
                })
                .build();

        buttonsPane.add(closeButton, 1, 1);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Size to scene Button">
        Button sizeToSceneButton =  ButtonBuilder.create()
                .text("Size to scene")
                .alignment(Pos.CENTER)
                .prefWidth(180)
                .onMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent arg0) {
                        primaryStage.sizeToScene();
                    }
                })
                .build();

        buttonsPane.add(sizeToSceneButton, 0, 2);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Show modality window Button">
        Button showModalityWindowButton =  ButtonBuilder.create()
                .text("Show modality window")
                .alignment(Pos.CENTER)
                .prefWidth(180)
                .onMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent arg0) {
                        Stage modalityStage = new Stage();
                        modalityStage.initModality(Modality.APPLICATION_MODAL);
                        modalityStage.setScene(createTestGridScene());
                        modalityStage.setResizable(resizable);
                        modalityStage.show();
                    }
                })
                .build();

        buttonsPane.add(showModalityWindowButton, 1, 2);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Resizable Button">
        Button resizableButton = ButtonBuilder.create()
                .text(resizable ? "Not resizable" : "Resizable")
                .alignment(Pos.CENTER)
                .prefWidth(180)
                .onMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent arg0) {
                        runJVMProcess(FSTestApp.this.getClass().getName(), String.valueOf(!resizable));
                        primaryStage.close();
                    }
                }).build();

        buttonsPane.add(resizableButton, 0, 3);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Opacity Slider">
        Slider opacitySlider =  SliderBuilder.create()
                .maxWidth(180)
                .value(1)
                .min(0)
                .max(1)
                .build();
        primaryStage.opacityProperty().bindBidirectional(opacitySlider.valueProperty());


        buttonsPane.add(opacitySlider, 1, 3);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="System menu ToggleButton">
        ToggleButton useSystemMenuToggleButton =  ToggleButtonBuilder.create()
                .text("Use system menu")
                .alignment(Pos.CENTER)
                .prefWidth(180)
                .build();

        menu.useSystemMenuBarProperty().bindBidirectional(useSystemMenuToggleButton.selectedProperty());

        buttonsPane.add(useSystemMenuToggleButton, 0, 4);
        //</editor-fold>

        VBox root = VBoxBuilder.create()
                .children(
                    menu,
                    HBoxBuilder.create()
                        .alignment(Pos.CENTER)
                        .padding(new Insets(5))
                        .children(
                            fullscreenIndicator,
                            new Text("Fullscreen"))
                        .build(),
                    buttonsPane,
                    logTextArea)
                .build();


        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
        primaryStage.show();

        updateLogTimeline.play();
    }

    /**
     * Run class in new process
     * @param className name of class
     * @param arg run argument
     */
    private void runJVMProcess(String className, String arg) {
        try {
            String cp = System.getProperty("java.class.path");
            String pathToJava = System.getProperty("java.home") + "/bin/java";

            Runtime.getRuntime().exec(new String[]{pathToJava, "-cp", cp, className, arg});
        } catch (Exception ex) {
            Logger.getLogger(FSTestApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Create scene with grid.
     * @return new scene
     */
    private Scene createTestGridScene(){
        double height = getMaxScreenHeight();
        double width = getMaxScreenWidth();


        Pane root  = PaneBuilder.create()
                .minHeight(height)
                .minWidth(width)
                .build();

        for (int x = 0; x < width; x+=10) {
            root.getChildren().add(
                    LineBuilder.create()
                        .startX(x)
                        .endX(x)
                        .startY(0)
                        .endY(height)
                        .build());
        }

        for (int y = 0; y < height; y+=10) {
            root.getChildren().add(
                    LineBuilder.create()
                        .startX(0)
                        .endX(width)
                        .startY(y)
                        .endY(y)
                        .build());
        }

        return new Scene(root, 200, 200);
    }

    /**
     * Get maximum height of all monitors.
     * @return maximum height
     */
    private double getMaxScreenHeight(){
        double result = 0;

        for(Screen screen : Screen.getScreens()){
            if(screen.getBounds().getHeight() > result){
                result = screen.getBounds().getHeight();
            }
        }

        return result;
    }

    /**
     * Get maximum width of all monitors.
     * @return maximum width
     */
    private double getMaxScreenWidth(){
        double result = 0;

        for(Screen screen : Screen.getScreens()){
            if(screen.getBounds().getWidth() > result){
                result = screen.getBounds().getWidth();
            }
        }

        return result;
    }


}
