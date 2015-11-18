/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
 * questions.
 */
package javafx.scene.control.test.dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Vorobyev
 */
public class DialogApp extends InteroperabilityApp {

    public static void main(String[] args) {
        Utils.launch(DialogApp.class, args);
    }

    @Override
    public Scene getScene() {
        return new DialogScene();
    }

    public class DialogScene extends Scene {

        private ButtonType lastResult;
        private SimpleStringProperty buttonText = new SimpleStringProperty("empty");
        public static final String BUTTON_SHOW_INFO_TEXT = "Show Information Dialog";
        public static final String BUTTON_SHOW_CONFIRM_TEXT = "Show Confirmation Dialog";
        public static final String BUTTON_SHOW_WARNING_TEXT = "Show Warning Dialog";
        public static final String BUTTON_SHOW_ERROR_TEXT = "Show Error Dialog";
        public static final String BUTTON_SHOW_NONE_TEXT = "Show NONE Type Dialog";
        public static final String BUTTON_SHOW_TEXT_FIELD_TEXT = "Show Text Field Dialog";
        public static final String BUTTON_SHOW_INITIAL_VALUE_TEXT = "Show Initial Value Set Dialog";
        public static final String BUTTON_SHOW_CHOICE_LT_TEN_TEXT = "Show Set Choices Dialog (<10)";
        public static final String BUTTON_SHOW_CHOICE_GT_TEN_TEXT = "Show Set Choices Dialog (>=10)";
        public static final String BUTTON_SHOW_EXP_TEXT = "Show Dialog With Expandable Content";
        public static final String BUTTON_SHOW_CUSTOM_TEXT = "Show Dialog With Custom Content";
        public static final String TOGGLE_BUTTON_TEXT = "Button";
        public static final String TOGGLE_LABEL_TEXT = "Label";
        public static final String TOGGLE_INPUT_TEXT = "Text Field";
        public static final String TOGGLE_NOTHING_TEXT = "Nothing";
        public static final String RESULT_FIELD_ID = "RESULT_FIELD";
        public static final String CHK_SHOW_MASTHEAD_ID = "CHK_SHOW_MASTHEAD";
        public static final String CHECKBOX_SHOW_CANCEL_TEXT = "Show Cancel Button";
        public static final String BUTTON_SHOW_INFO_TWO_BUTTONS_TEXT = "Show Information Dialog With Two Buttons";
        private final String OPTIONAL_MASTERHEAD_TEXT = "Wouldn't this be nice?";
        private final String CONTENT_TEXT = "A collection of pre-built JavaFX dialogs?\nSeems like a great idea to me...";
        private final ToggleGroup styleGroup = new ToggleGroup();
        private final ToggleGroup expContentGroup = new ToggleGroup();
        private final ToggleGroup buttonsGroup = new ToggleGroup();
        private final ComboBox<Modality> modalityCombobox = new ComboBox<Modality>();
        private final CheckBox cbUseBlocking = new CheckBox();
        private final CheckBox cbCloseDialogAutomatically = new CheckBox();
        private final CheckBox cbShowMasthead = new CheckBox();
        private final CheckBox cbSetOwner = new CheckBox();
        private final CheckBox cbCustomGraphic = new CheckBox();
        public static final double TS_SPACING = 20.0f;
        TextField resultField = new TextField();
        Text isButtonPushed;
        Text button_press_counter;
        final Text checkBoxSelection = new Text();
        int counter, last_counter;
        ArrayList<ToggleButton> selectedButtons = new ArrayList<>();

        public DialogScene() {
            super(new Pane(), 1200, 500);
            initialize();
        }

        protected void initialize() {
            Utils.addBrowser(this);

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setHgap(10);
            grid.setVgap(10);

            int row = 0;

            // *******************************************************************
            // Information Dialog
            // *******************************************************************
            grid.add(createLabel("Information Dialog: "), 0, row);

            final Button showInformationDialogButton = new Button(BUTTON_SHOW_INFO_TEXT);
            showInformationDialogButton.setOnAction((ActionEvent e) -> {
                Alert dlg = createAlert(Alert.AlertType.INFORMATION);
                dlg.setTitle("Information Dialog");
                dlg.getDialogPane().setContentText(CONTENT_TEXT);
                configureSampleDialog(dlg, OPTIONAL_MASTERHEAD_TEXT, null);

                // lets get some output when events happen
                dlg.setOnShowing(evt -> System.out.println(evt));
                dlg.setOnShown(evt -> System.out.println(evt));
                dlg.setOnHiding(evt -> System.out.println(evt));
                dlg.setOnHidden(evt -> System.out.println(evt));
                showDialog(dlg);
            });

            final Button showInformationDialogTwoButtonsButton = new Button(BUTTON_SHOW_INFO_TWO_BUTTONS_TEXT);
            showInformationDialogTwoButtonsButton.setOnAction((ActionEvent e) -> {
                Alert dlg = createAlert(Alert.AlertType.INFORMATION);
                dlg.setTitle("Information Dialog With Two Buttons");
                dlg.getDialogPane().setContentText(CONTENT_TEXT);
                configureSampleDialog(dlg, OPTIONAL_MASTERHEAD_TEXT, null);
                dlg.getButtonTypes().add(ButtonType.NEXT);
                showDialog(dlg);
            });

            grid.add(new HBox(10, showInformationDialogButton, showInformationDialogTwoButtonsButton), 1, row);

            row++;

            // *******************************************************************
            // Confirmation Dialog
            // *******************************************************************
            grid.add(createLabel("Confirmation Dialog: "), 0, row);

            final CheckBox cbShowCancel = new CheckBox(CHECKBOX_SHOW_CANCEL_TEXT);
            cbShowCancel.setSelected(true);

            final Button showConfirmationDialogButton = new Button(BUTTON_SHOW_CONFIRM_TEXT);
            showConfirmationDialogButton.setOnAction(e -> {
                Alert dlg = createAlert(Alert.AlertType.CONFIRMATION);
                dlg.setTitle("You do want dialogs right?");
                String optionalMasthead = "Just Checkin'";
                dlg.getDialogPane().setContentText("I was a bit worried that you might not want them, so I wanted to double check.");

                if (!cbShowCancel.isSelected()) {
                    dlg.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);
                }

                configureSampleDialog(dlg, optionalMasthead, null);
                showDialog(dlg);
            });
            grid.add(new HBox(10, showConfirmationDialogButton, cbShowCancel), 1, row);

            row++;

            // *******************************************************************
            // Warning Dialog
            // *******************************************************************
            grid.add(createLabel("Warning Dialog: "), 0, row);

            final Button showWarningDialogButton = new Button(BUTTON_SHOW_WARNING_TEXT);
            showWarningDialogButton.setOnAction(e -> {
                Alert dlg = createAlert(Alert.AlertType.WARNING);
                dlg.setTitle("I'm warning you!");
                String optionalMasthead = "This is a warning";
                dlg.getDialogPane().setContentText("I'm glad I didn't need to use this...");
                configureSampleDialog(dlg, optionalMasthead, null);
                showDialog(dlg);
            });
            grid.add(new HBox(10, showWarningDialogButton), 1, row);

            row++;

            // *******************************************************************
            // Error Dialog
            // *******************************************************************
            grid.add(createLabel("Error Dialog: "), 0, row);

            final Button showErrorDialogButton = new Button(BUTTON_SHOW_ERROR_TEXT);
            showErrorDialogButton.setOnAction(e -> {
                Alert dlg = createAlert(Alert.AlertType.ERROR);
                dlg.setTitle("It looks like you're making a bad decision");
                String optionalMasthead = "Exception Encountered";
                dlg.getDialogPane().setContentText("Better change your mind - this is really your last chance! (Even longer text that should probably wrap)");
                configureSampleDialog(dlg, optionalMasthead, null);
                showDialog(dlg);
            });
            grid.add(new HBox(10, showErrorDialogButton), 1, row);

            row++;

            // *******************************************************************
            // NONE Dialog
            // *******************************************************************
            grid.add(createLabel("NONE Type Dialog: "), 0, row);

            final Button showNoneDialogButton = new Button(BUTTON_SHOW_NONE_TEXT);
            showNoneDialogButton.setOnAction(e -> {
                Alert dlg = createAlert(Alert.AlertType.NONE);
                dlg.setTitle("NONE");
                String optionalMasthead = "NONE";
                dlg.getDialogPane().setContentText("NONE");
                configureSampleDialog(dlg, optionalMasthead, null);
                showDialog(dlg);
            });
            grid.add(new HBox(10, showNoneDialogButton), 1, row);

            row++;

            // *******************************************************************
            // Input Dialog (with masthead)
            // *******************************************************************
            grid.add(createLabel("Text Field Dialog: "), 0, row);

            final Button showTextFieldDialogButton = new Button(BUTTON_SHOW_TEXT_FIELD_TEXT);
            showTextFieldDialogButton.setOnAction(e -> {
                TextInputDialog dlg = new TextInputDialog("");
                dlg.setTitle("Name Check");
                String optionalMasthead = "Please type in your name";
                dlg.getDialogPane().setContentText("What is your name?");
                configureSampleDialog(dlg, optionalMasthead, null);
                showDialog(dlg);
            });
            grid.add(new HBox(10, showTextFieldDialogButton), 1, row);
            row++;

            grid.add(createLabel("Initial Value Set Dialog: "), 0, row);
            final Button showInitialValueSetDialogButton = new Button(BUTTON_SHOW_INITIAL_VALUE_TEXT);
            showInitialValueSetDialogButton.setOnAction(e -> {
                TextInputDialog dlg = new TextInputDialog("Jonathan");
                dlg.setTitle("Name Guess");
                String optionalMasthead = "Name Guess";
                dlg.getDialogPane().setContentText("Pick a name?");
                configureSampleDialog(dlg, optionalMasthead, null);
                showDialog(dlg);
            });
            grid.add(new HBox(10, showInitialValueSetDialogButton), 1, row);
            row++;

            grid.add(createLabel("Set Choices Dialog (<10): "), 0, row);
            final Button showChoicesLessTenDialogButton = new Button(BUTTON_SHOW_CHOICE_LT_TEN_TEXT);
            showChoicesLessTenDialogButton.setOnAction(e -> {
                ChoiceDialog<String> dlg = new ChoiceDialog<String>("Jonathan",
                        "Matthew", "Jonathan", "Ian", "Sue", "Hannah");
                dlg.setTitle("Name Guess");
                List<ButtonType> existing = dlg.getDialogPane().getButtonTypes();
                existing.addAll(getSelectedTypes(existing));
                String optionalMasthead = "Name Guess";
                dlg.getDialogPane().setContentText("Pick a name?");
                configureSampleDialog(dlg, optionalMasthead, null);
                showDialog(dlg);
            });
            grid.add(new HBox(10, showChoicesLessTenDialogButton), 1, row);
            row++;

            grid.add(createLabel("Set Choices (>=10) Dialog: "), 0, row);
            final Button showChoicesGreaterTenDialogButton = new Button(BUTTON_SHOW_CHOICE_GT_TEN_TEXT);
            showChoicesGreaterTenDialogButton.setOnAction(e -> {
                ChoiceDialog<String> dlg = new ChoiceDialog<String>("Jonathan",
                        "Matthew", "Jonathan", "Ian", "Sue",
                        "Hannah", "Julia", "Denise", "Stephan",
                        "Sarah", "Ron", "Ingrid");
                dlg.setTitle("Name Guess");
                List<ButtonType> existing = dlg.getDialogPane().getButtonTypes();
                existing.addAll(getSelectedTypes(existing));
                String optionalMasthead = "Name Guess";
                dlg.getDialogPane().setContentText("Pick a name?");
                configureSampleDialog(dlg, optionalMasthead, null);
                showDialog(dlg);
            });
            grid.add(new HBox(10, showChoicesGreaterTenDialogButton), 1, row);
            row++;

            grid.add(createLabel("Dialog With Expandable Content:"), 0, row);
            final Button showExpandable = new Button(BUTTON_SHOW_EXP_TEXT);
            showExpandable.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Alert dlg = createAlert(Alert.AlertType.INFORMATION);
                    dlg.setTitle("Dialog With Expandable Conent");
                    dlg.getDialogPane().setContentText(CONTENT_TEXT);
                    Toggle selected = expContentGroup.getSelectedToggle();
                    dlg.getDialogPane().setExpandableContent(
                            selected == null ? null : (Node) selected.getUserData());
                    configureSampleDialog(dlg, OPTIONAL_MASTERHEAD_TEXT, null);
                    showDialog(dlg);
                }
            });
            grid.add(new HBox(10, showExpandable), 1, row);
            row++;

            grid.add(createLabel("Dialog With Custom Content:"), 0, row);
            final Button showCustom = new Button(BUTTON_SHOW_CUSTOM_TEXT);
            showCustom.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Alert dlg = createAlert(Alert.AlertType.INFORMATION);
                    dlg.setTitle("Dialog With Custom Content");
                    dlg.getDialogPane().setContentText(CONTENT_TEXT);
                    Toggle selected = expContentGroup.getSelectedToggle();
                    dlg.getDialogPane().setContent(selected == null ? null : (Node) selected.getUserData());
                    configureSampleDialog(dlg, OPTIONAL_MASTERHEAD_TEXT, null);
                    showDialog(dlg);
                }
            });
            grid.add(new HBox(10, showCustom), 1, row);
            row++;
            grid.add(createLabel("Result:"), 0, row);
            resultField.setId(RESULT_FIELD_ID);
            resultField.textProperty().bind(buttonText);
            grid.add(new HBox(10, resultField), 1, row);
            row++;
            //grid.add(
            //        new HBox(10, showTextFieldDialogButton, showInitialValueSetDialogButton, showChoicesLessTenDialogButton, showChoicesGreaterTenDialogButton),
            //        1, row);
            //row++;

            SplitPane splitPane = new SplitPane();
            splitPane.setPrefSize(1200, 1200);
            splitPane.getItems().addAll(grid, getControlPanel());
            splitPane.setDividerPositions(0.6);
            splitPane.setId("SPLIT");
            splitPane.prefWidthProperty().bind(stage.widthProperty());
            Pane list = (Pane) getRoot();
            list.getChildren().clear();
            list.setPrefSize(splitPane.getPrefWidth(), splitPane.getPrefHeight());
            list.getChildren().add(splitPane);
        }

        private Alert createAlert(Alert.AlertType type) {
            Alert dlg;
            Window owner = cbSetOwner.isSelected() ? stage : null;
            List<ButtonType> types = getSelectedTypes(Collections.emptyList());
            dlg = types.isEmpty() ? new Alert(type, "") : new Alert(type, "", types.toArray(new ButtonType[types.size()]));
            dlg.initModality(modalityCombobox.getValue());
            dlg.initOwner(owner);
            return dlg;
        }

        private List<ButtonType> getSelectedTypes(List<ButtonType> alreadySelected) {
            List<ButtonType> types = new ArrayList<>();
            for (ToggleButton tb : selectedButtons) {
                if (tb.isSelected()) {
                    types.add((ButtonType) tb.getUserData());
                }
            }
            types.removeAll(alreadySelected);
            return types;
        }

        private void configureSampleDialog(Dialog<?> dlg, String masthead, StageStyle style) {
            dlg.getDialogPane().setHeaderText(cbShowMasthead.isSelected() ? masthead : null);
            if (cbCustomGraphic.isSelected()) {
                dlg.getDialogPane().setGraphic(new ImageView(new Image(getClass().getResource("tick.png").toExternalForm())));
            }
            if (style != null) {
                dlg.initStyle(style);
            } else {
                dlg.initStyle((StageStyle) styleGroup.getSelectedToggle().getUserData());
            }
        }

        private void showDialog(Dialog<?> dlg) {
            if (cbCloseDialogAutomatically.isSelected()) {
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Attempting to close dialog now...");
                    Platform.runLater(() -> dlg.close());
                }).start();
            }

            if (cbUseBlocking.isSelected()) {
                dlg.showAndWait().ifPresent(new Consumer<Object>() {

                    public void accept(Object result) {
                        System.out.println("Result is " + result);
                        lastResult = (ButtonType) result;
                        resultField.setUserData(lastResult);
                        buttonText.set(lastResult.getText());
                    }
                });
            } else {
                dlg.show();
                dlg.resultProperty().addListener(new InvalidationListener() {

                    public void invalidated(Observable o) {
                        System.out.println("Result is: " + dlg.getResult());
                        lastResult = (ButtonType) dlg.getResult();
                        resultField.setUserData(lastResult);
                        buttonText.set(lastResult.getText());
                    }
                });
                System.out.println("This println is _after_ the show method - we're non-blocking!");
            }
        }

        private Node getControlPanel() {
            GridPane grid = new GridPane();
            grid.setVgap(10);
            grid.setHgap(10);
            grid.setPadding(new Insets(30, 30, 0, 30));

            int row = 0;

            // stage style
            HBox styleHBox = new HBox();
            for (StageStyle s : StageStyle.values()) {
                ToggleButton tb = new ToggleButton(s.toString());;
                //if (s == StageStyle.UNDECORATED || s == StageStyle.TRANSPARENT) {
                //    tb.setUserData(StageStyle.DECORATED);
                //} else {

                tb.setUserData(s);
                //}
                tb.setToggleGroup(styleGroup);

                styleHBox.getChildren().add(tb);
            }
            grid.add(createLabel("Style: ", "property"), 0, row);
            grid.add(styleHBox, 1, row);
            row++;

            // expandable content
            ToggleButton buttonToggleButton = new ToggleButton(TOGGLE_BUTTON_TEXT);
            buttonToggleButton.setUserData(new Button("Button"));
            buttonToggleButton.setToggleGroup(expContentGroup);
            ToggleButton labelToggleButton = new ToggleButton(TOGGLE_LABEL_TEXT);
            labelToggleButton.setUserData(new Label("Label"));
            labelToggleButton.setToggleGroup(expContentGroup);
            ToggleButton inputToggleButton = new ToggleButton(TOGGLE_INPUT_TEXT);
            inputToggleButton.setUserData(new TextField("Text Field"));
            inputToggleButton.setToggleGroup(expContentGroup);
            ToggleButton nothingToggleButton = new ToggleButton(TOGGLE_NOTHING_TEXT);
            nothingToggleButton.setToggleGroup(expContentGroup);
            grid.add(createLabel("Style: ", "property"), 0, row);
            grid.add(new HBox(buttonToggleButton, labelToggleButton, inputToggleButton, nothingToggleButton), 1, row);
            row++;
            // dialog buttons
            ToggleButton okToggleButton = new ToggleButton(ButtonType.OK.getText());
            selectedButtons.add(okToggleButton);
            okToggleButton.setUserData(ButtonType.OK);
            ToggleButton applyToggleButton = new ToggleButton(ButtonType.APPLY.getText());
            selectedButtons.add(applyToggleButton);
            applyToggleButton.setUserData(ButtonType.APPLY);
            ToggleButton cancelToggleButton = new ToggleButton(ButtonType.CANCEL.getText());
            selectedButtons.add(cancelToggleButton);
            cancelToggleButton.setUserData(ButtonType.CANCEL);
            ToggleButton closeToggleButton = new ToggleButton(ButtonType.CLOSE.getText());
            selectedButtons.add(closeToggleButton);
            closeToggleButton.setUserData(ButtonType.CLOSE);
            ToggleButton finishToggleButton = new ToggleButton(ButtonType.FINISH.getText());
            selectedButtons.add(finishToggleButton);
            finishToggleButton.setUserData(ButtonType.FINISH);
            ToggleButton nextToggleButton = new ToggleButton(ButtonType.NEXT.getText());
            selectedButtons.add(nextToggleButton);
            nextToggleButton.setUserData(ButtonType.NEXT);
            ToggleButton noToggleButton = new ToggleButton(ButtonType.NO.getText());
            selectedButtons.add(noToggleButton);
            noToggleButton.setUserData(ButtonType.NO);
            ToggleButton previousToggleButton = new ToggleButton(ButtonType.PREVIOUS.getText());
            selectedButtons.add(previousToggleButton);
            previousToggleButton.setUserData(ButtonType.PREVIOUS);
            ToggleButton yesToggleButton = new ToggleButton(ButtonType.YES.getText());
            selectedButtons.add(yesToggleButton);
            yesToggleButton.setUserData(ButtonType.YES);
            grid.add(createLabel("Buttons", "property"), 0, row);
            grid.add(new HBox(okToggleButton, applyToggleButton, cancelToggleButton, closeToggleButton, finishToggleButton, nextToggleButton, noToggleButton, previousToggleButton, yesToggleButton), 1, row);
            row++;
            // modality
            grid.add(createLabel("Modality: ", "property"), 0, row);
            modalityCombobox.getItems().setAll(Modality.values());
            modalityCombobox.setValue(modalityCombobox.getItems().get(Modality.values().length - 1));
            grid.add(modalityCombobox, 1, row);
            row++;

            // use blocking
            cbUseBlocking.setSelected(true);
            grid.add(createLabel("Use blocking: ", "property"), 0, row);
            grid.add(cbUseBlocking, 1, row);
            row++;

            // close dialog automatically
            grid.add(createLabel("Close dialog after 2000ms: ", "property"), 0, row);
            grid.add(cbCloseDialogAutomatically, 1, row);
            row++;

            // show masthead
            grid.add(createLabel("Show masthead: ", "property"), 0, row);
            cbShowMasthead.setId(CHK_SHOW_MASTHEAD_ID);
            grid.add(cbShowMasthead, 1, row);
            row++;

            // set owner
            grid.add(createLabel("Set dialog owner: ", "property"), 0, row);
            grid.add(cbSetOwner, 1, row);
            row++;

            // custom graphic
            grid.add(createLabel("Use custom graphic: ", "property"), 0, row);
            grid.add(cbCustomGraphic, 1, row);
            row++;

            return grid;
        }

        private Node createLabel(String text, String... styleclass) {
            Label label = new Label(text);

            if (styleclass == null || styleclass.length == 0) {
                label.setFont(Font.font(13));
            } else {
                label.getStyleClass().addAll(styleclass);
            }
            return label;
        }

        private TextField createTextField(String id) {
            TextField textField = new TextField();
            textField.setId(id);
            GridPane.setHgrow(textField, Priority.ALWAYS);
            return textField;
        }

    }
}
