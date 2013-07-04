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

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author alexander_kirov
 */
public class ModalityWindow {
    private static final int WND_HEIGHT_IN_SEQUENTIAL_MODE = 135;

    protected CheckBox cb = new CheckBox();

    public static enum TestCase {

        NONE, WIN, APP, WINWIN, APPAPP, WINAPP, APPWIN, FILECH
    };

    abstract class SubstageCreator {

        protected TestCase name;
        protected boolean parentIsSelected;

        public SubstageCreator(TestCase name) {
            this.name = name;
        }

        public abstract void run();

        @Override
        public String toString() {
            return name.name();
        }
    }

    class SingleSubstageCreator extends SubstageCreator {

        protected Modality modality;

        public SingleSubstageCreator(TestCase name, Modality modality) {
            super(name);
            this.modality = modality;
        }

        public void run() {
            createSubwindow(modality, stage, cb.isSelected(), propIsShowAndWait.get());
        }
    }

    class DoubleSubstageCreator extends SingleSubstageCreator {

        protected Modality modality2;

        public DoubleSubstageCreator(TestCase name, Modality modality1, Modality modality2) {
            super(name, modality1);
            this.modality2 = modality2;
        }

        @Override
        public void run() {
            createSubwindow(modality, stage, cb.isSelected(), propIsShowAndWait.get());
            createSubwindow(modality2, stage, cb.isSelected(), propIsShowAndWait.get());
        }
    }
    List<SubstageCreator> substageCreatorTestCases = FXCollections.observableArrayList(new SingleSubstageCreator(TestCase.NONE, Modality.NONE),
            new SingleSubstageCreator(TestCase.WIN, Modality.WINDOW_MODAL),
            new SingleSubstageCreator(TestCase.APP, Modality.APPLICATION_MODAL),
            new DoubleSubstageCreator(TestCase.WINWIN, Modality.WINDOW_MODAL, Modality.WINDOW_MODAL),
            new DoubleSubstageCreator(TestCase.APPAPP, Modality.APPLICATION_MODAL, Modality.APPLICATION_MODAL),
            new DoubleSubstageCreator(TestCase.WINAPP, Modality.WINDOW_MODAL, Modality.APPLICATION_MODAL),
            new DoubleSubstageCreator(TestCase.APPWIN, Modality.APPLICATION_MODAL, Modality.WINDOW_MODAL),
            new SubstageCreator(TestCase.FILECH) {

                @Override
                public void run() {
                    createSubFileChooser(stage, cb.isSelected(), FileChooserType.OPEN);
                }
            });

    public enum WindowsRenderType {

        LIST, HIERARHICAL
    };

    public enum FileChooserType {

        OPEN, SAVE
    };
    public static int counter = 0;
    private int mouseClickingCounter = 0;
    private int keyBoardEventsCounter = 0;
    //Sizes of window that makes sence on order of windows showing
    public static int initialX;
    public static int initialY;
    public static int width;
    public static int height;
    public static int hierarhicalOffset = 83;
    private static WindowsRenderType renderType = WindowsRenderType.LIST;
    private Stage stage;
    private String uniqueID;
    public static Stack<Stage> allStages = new Stack<Stage>();
    public static boolean isApplet = false;
    private SimpleBooleanProperty propIsShowAndWait = new SimpleBooleanProperty();

    public ModalityWindow(Modality modality, Stage parentStage, boolean isWait) {
        uniqueID = "stage" + counter++;

        stage = new Stage();
        stage.setTitle(uniqueID);
        stage.initModality(modality);
        stage.initOwner(parentStage);
        allStages.add(stage);

        setWindowsRenderType(renderType);
        changeStageSizes();

        final Label labelMouseCounter = new Label("0");
        labelMouseCounter.setId(uniqueID + MOUSE_COUNTER_ID);

        final Label labelKeyEventsCounter = new Label("0");
        labelKeyEventsCounter.setId(uniqueID + KEY_COUNTER_ID);

        Button dismissButton = new Button(DISMISS_BUTTON_ID);
        dismissButton.setId(DISMISS_BUTTON_ID);
        dismissButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                stage.hide();
            }
        });

        cb.setId(CHECKBOX_ID);
        cb.setText("Create new with this as parent.");
        cb.setSelected(true);

        Pane creationButtons = null;
        if (renderType == WindowsRenderType.LIST) {
            creationButtons = new HBox();
        } else {
            creationButtons = new VBox();
        }

        ArrayList<Button> creationButtonsArray = new ArrayList();
        for (final SubstageCreator runner : substageCreatorTestCases) {
            Button button = new Button(runner.name.name());
            button.setId(runner.name.name());
            button.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    runner.run();
                }
            });
            creationButtonsArray.add(button);
        }
        creationButtons.getChildren().addAll(creationButtonsArray);

        final ChoiceBox<SubstageCreator> choiceBox = new ChoiceBox<SubstageCreator>();
        choiceBox.setId("choiceBox");
        choiceBox.setItems(FXCollections.observableArrayList(substageCreatorTestCases));
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            public void changed(ObservableValue ov, Object t, Object t1) {
                ((SubstageCreator) t1).run();
            }
        });

        final Label selection = new Label("null");
        selection.setId(SELECTION_LABEL_ID);
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            public void changed(ObservableValue ov, Object t, Object t1) {
                Object obj = choiceBox.getSelectionModel().getSelectedItem();
                if (obj != null) {
                    selection.setText(obj.toString());
                } else {
                    selection.setText("");
                }
            }
        });

        Rectangle r = new Rectangle(100, 100);
        r.setId(RECTANGLE_ID);
        r.setFill(Color.GREEN);

        CheckBox cbWaitAfterShow = new CheckBox("Wait After Show Modal Window");
        cbWaitAfterShow.setId(SHOWANDWAIT_CHECKBOX_ID);
        cbWaitAfterShow.selectedProperty().bindBidirectional(propIsShowAndWait);

        ProgressIndicator progressWait = new ProgressIndicator();
        progressWait.setId(WAIT_PROGRESS_ID);

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(dismissButton, choiceBox, cb, cbWaitAfterShow, progressWait);

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(new Label("MouseClicked : "), labelMouseCounter,
                new Label(" and keyboard input : "), labelKeyEventsCounter);

        HBox hb3 = new HBox();
        hb3.getChildren().addAll(new Label("Modality: " + modality.name()));

        HBox hb4 = new HBox();
        hb4.getChildren().addAll(creationButtons, r);

        VBox sceneGroup = new VBox();
        sceneGroup.getChildren().addAll(hb1, hb2, hb3, hb4);

        Scene scene = new Scene(sceneGroup);
        scene.setFill(Color.LIGHTGREEN);
        ModalityApp.addBrowser(scene);
        stage.setScene(scene);

        //Events for mouse and for keyboard
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent t) {
                mouseClickingCounter++;
                labelMouseCounter.setText(((Integer) mouseClickingCounter).toString());
            }
        });
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            public void handle(KeyEvent t) {
                keyBoardEventsCounter++;
                labelKeyEventsCounter.setText(((Integer) keyBoardEventsCounter).toString());
            }
        });

        if (isWait) {
            stage.showAndWait();
        } else {
            stage.show();
        }
        progressWait.setProgress(1.0);
    }

    public static void setWindowsRenderType(WindowsRenderType type) {
        renderType = type;

        switch (type) {
            case LIST:
                initialX = 200 * (isApplet ? 1 : 0);
                initialY = 0;
                width = 640;
                height = WND_HEIGHT_IN_SEQUENTIAL_MODE;
                break;
            case HIERARHICAL:
                initialX = 200 * (isApplet ? 1 : 0);
                initialY = 0;
                width = 640;
                height = 300;
                break;
        }
    }

    private void changeStageSizes() {
        switch (renderType) {
            case LIST:
                stage.setWidth(width);
                stage.setHeight(height);
                stage.setX(initialX + (counter - 1));
                stage.setY(initialY + (counter - 1) * height);
                break;
            case HIERARHICAL:
                stage.setWidth(width);
                stage.setHeight(height);
                stage.setX(initialX + (counter - 1) * (hierarhicalOffset - 5));
                stage.setY(initialY + (counter - 1) * hierarhicalOffset);
                break;
        }
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public Stage getStage() {
        return stage;
    }

    private static ModalityWindow createSubwindow(Modality modalityType, Stage stage, boolean isThisParent, boolean isShowAndWait) {
        Stage parentStage = null;
        if (isThisParent) {
            parentStage = stage;
        }
        return new ModalityWindow(modalityType, parentStage, isShowAndWait);
    }

    private static void createSubFileChooser(Stage stage, boolean isThisParent, FileChooserType type) {
        FileChooser fc = new FileChooser();
        try {
            switch (type) {
                case OPEN:
                    fc.showOpenDialog(isThisParent ? stage : null);
                    break;
                case SAVE:
                    fc.showSaveDialog(isThisParent ? stage : null);
                    break;
            }
        } catch (Exception e) {
        }
    }
    public static final String SELECTION_LABEL_ID = "selection.id";
    public static final String CHECKBOX_ID = "checkbox.id";
    public static final String RECTANGLE_ID = "emptyRectangle";
    public static final String MOUSE_COUNTER_ID = "mouseCounter";
    public static final String KEY_COUNTER_ID = "keyEventsCounter";
    public static final String DISMISS_BUTTON_ID = "Dismiss";
    public static final String WAIT_PROGRESS_ID = "WaitProgress";
    public static final String SHOWANDWAIT_CHECKBOX_ID = "ShowAndWaitCheckBox";
}
