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
import javafx.scene.shape.Line;
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

        GridPane buttonsPane = new GridPane();
        buttonsPane.setPadding(new Insets(10));
        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.getColumnConstraints().add(new ColumnConstraints(200));
        buttonsPane.getColumnConstraints().add(new ColumnConstraints(200));
        buttonsPane.getRowConstraints().add(new RowConstraints(30));
        buttonsPane.getRowConstraints().add(new RowConstraints(30));
        buttonsPane.getRowConstraints().add(new RowConstraints(30));
        buttonsPane.getRowConstraints().add(new RowConstraints(30));
        buttonsPane.getRowConstraints().add(new RowConstraints(30));

        final TextArea logTextArea = new TextArea();
        logTextArea.setEditable(false);

        //Timer for update logTextArea
        Timeline updateLogTimeline = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        logTextArea.insertText(logTextArea.getLength(), log.toString());
                        log.delete(0, log.length());

                    }
                }, ( KeyValue[])null));  // cast null to suppress compiler warning
        updateLogTimeline.setCycleCount(-1);

        //<editor-fold defaultstate="collapsed" desc="Menu">
        Menu menu1 = new Menu("_Menu1");
        menu1.setOnShown(new EventHandler<Event>() {
                        public void handle(Event t) {
                            log.append("Menu 1 Shown\n");
                        }
        });
        menu1.setOnHidden(new EventHandler<Event>() {
                        public void handle(Event t) {
                            log.append("Menu 1 Hidden\n");
                        }
        });
        menu1.setMnemonicParsing(true);
        MenuItem mi11 = new MenuItem("Menu _Item 1");
        mi11.setAccelerator(KeyCombination.keyCombination("ctrl+m"));
        mi11.setMnemonicParsing(true);
        mi11.setOnAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent t) {
                                    log.append("Menu Item 1 Action\n");
                                }
        });
        MenuItem mi12 = new MenuItem("Menu I_tem 2");
        mi12.setMnemonicParsing(true);
        menu1.getItems().addAll(mi11, mi12);

        Menu menu2 = new Menu("M_enu2");
        menu2.setMnemonicParsing(true);
        menu2.setOnShown(new EventHandler<Event>() {
                        public void handle(Event t) {
                            log.append("Menu 2 Shown\n");
                        }
        });
        menu2.setOnHidden(new EventHandler<Event>() {
                        public void handle(Event t) {
                            log.append("Menu 2 Hidden\n");
                        }
        });
        MenuItem mi21 = new MenuItem("Menu _Item 1");
        mi21.setMnemonicParsing(true);
        MenuItem mi22 = new MenuItem("Menu I_tem 2");
        mi22.setMnemonicParsing(true);
        MenuItem mi23 = new MenuItem("Menu Ite_m 3");
        mi23.setMnemonicParsing(true);
        menu1.getItems().addAll(mi21, mi22, mi23);

        MenuBar menu = new MenuBar(menu1, menu2);
        menu.setUseSystemMenuBar(true);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Fullscreen indicator">
        final Circle fullscreenIndicator = new Circle(6);
        fullscreenIndicator.setFill(Color.RED);
        fullscreenIndicator.setEffect(new InnerShadow());

        primaryStage.fullScreenProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                fullscreenIndicator.setFill(newValue.booleanValue() ? Color.GREEN : Color.RED);
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Fullscreen false Button">
        Button setFSFalseButton = new Button("Set fullscreen false");
        setFSFalseButton.setAlignment(Pos.CENTER);
        setFSFalseButton.setPrefWidth(180);
        setFSFalseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent arg0) {
                        primaryStage.setFullScreen(false);
                    }
        });
        buttonsPane.add(setFSFalseButton, 0, 0);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Fullscreen true Button">
        Button setFSTrueButton = new Button("Set fullscreen true");
        setFSTrueButton.setAlignment(Pos.CENTER);
        setFSTrueButton.setPrefWidth(180);
        setFSTrueButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent arg0) {
                        primaryStage.setFullScreen(true);
                    }
        });
        buttonsPane.add(setFSTrueButton, 1, 0);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Center Button">
        Button centerButton = new Button("Center");
        centerButton.setAlignment(Pos.CENTER);
        centerButton.setPrefWidth(180);
        centerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent arg0) {
                        primaryStage.centerOnScreen();
                    }
        });
        buttonsPane.add(centerButton, 0, 1);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Close Button">
        Button closeButton = new Button("Close");
        closeButton.setAlignment(Pos.CENTER);
        closeButton.setPrefWidth(180);
        closeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent arg0) {
                        primaryStage.close();
                    }
        });
        buttonsPane.add(closeButton, 1, 1);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Size to scene Button">
        Button sizeToSceneButton = new Button("Size to scene");
        sizeToSceneButton.setAlignment(Pos.CENTER);
        sizeToSceneButton.setPrefWidth(180);
        sizeToSceneButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent arg0) {
                        primaryStage.sizeToScene();
                    }
        });
        buttonsPane.add(sizeToSceneButton, 0, 2);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Show modality window Button">
        Button showModalityWindowButton = new Button("Show modality window");
        showModalityWindowButton.setAlignment(Pos.CENTER);
        showModalityWindowButton.setPrefWidth(180);
        showModalityWindowButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent arg0) {
                        Stage modalityStage = new Stage();
                        modalityStage.initModality(Modality.APPLICATION_MODAL);
                        modalityStage.setScene(createTestGridScene());
                        modalityStage.setResizable(resizable);
                        modalityStage.show();
                    }
        });
        buttonsPane.add(showModalityWindowButton, 1, 2);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Resizable Button">
        Button resizableButton = new Button();
        resizableButton.setText(resizable ? "Not resizable" : "Resizable");
        resizableButton.setAlignment(Pos.CENTER);
        resizableButton.setPrefWidth(180);
        resizableButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent arg0) {
                        runJVMProcess(FSTestApp.this.getClass().getName(), String.valueOf(!resizable));
                        primaryStage.close();
                    }
        });
        buttonsPane.add(resizableButton, 0, 3);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Opacity Slider">
        Slider opacitySlider = new Slider(0, 1, 1);
        opacitySlider.setMaxWidth(180);
        primaryStage.opacityProperty().bindBidirectional(opacitySlider.valueProperty());
        buttonsPane.add(opacitySlider, 1, 3);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="System menu ToggleButton">
        ToggleButton useSystemMenuToggleButton = new ToggleButton("Use system menu");
        useSystemMenuToggleButton.setAlignment(Pos.CENTER);
        useSystemMenuToggleButton.setPrefWidth(180);
        menu.useSystemMenuBarProperty().bindBidirectional(useSystemMenuToggleButton.selectedProperty());

        buttonsPane.add(useSystemMenuToggleButton, 0, 4);
        //</editor-fold>

        HBox temp = new HBox(fullscreenIndicator, new Text("Fullscreen"));
        temp.setAlignment(Pos.CENTER);
        temp.setPadding(new Insets(5));
        VBox root = new VBox(menu, temp, buttonsPane, logTextArea);
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


        Pane root  = new Pane();
        root.setMinHeight(height);
        root.setMinWidth(width);

        for (int x = 0; x < width; x+=10) {
            root.getChildren().add(new Line(x, x, 0, height));
        }

        for (int y = 0; y < height; y+=10) {
            root.getChildren().add(new Line(0, width, y, y));
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
