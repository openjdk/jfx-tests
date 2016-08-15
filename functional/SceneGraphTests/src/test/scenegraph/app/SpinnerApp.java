/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
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
package test.scenegraph.app;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
//import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class SpinnerApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override public void start(Stage stage) {
        final Spinner spinner = new Spinner();

        spinner.setId("spinner");

        // debug: spinner as drag source
        /*
        spinner.getEditor().setOnDragDetected(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            Dragboard db = spinner.startDragAndDrop(TransferMode.COPY);//sourceModes.toArray(new TransferMode[sourceModes.size()]));
                            if (db != null) {
                                final ClipboardContent preparedClipboardContent = new ClipboardContent();
                                preparedClipboardContent.putString(spinner.getEditor().textProperty().get());
                                System.out.println("prepareClipboardContent " + preparedClipboardContent);
                                db.setContent(preparedClipboardContent);
                                event.consume();
                            }
                        }
                    });
        */

        // debug output to console
        spinner.valueProperty().addListener((o, oldValue, newValue) ->
                System.out.println("value changed: '" + oldValue + "' -> '" + newValue + "'"));
        spinner.getEditor().textProperty().addListener((o, oldValue, newValue) ->
                System.out.println("text changed: '" + oldValue + "' -> '" + newValue + "'"));

        // this lets us switch between the spinner value factories
        ComboBox<String> spinnerValueFactoryOptions =
                new ComboBox<>(FXCollections.observableArrayList("Integer", "Double", "List<String>"));

        // "date/time/calendar spinner" temporary removed. pls see
        // https://javafx-jira.kenai.com/browse/RT-37968          //   , "Calendar"));


        spinnerValueFactoryOptions.setId("valuetype");

        // debug output
        spinnerValueFactoryOptions.armedProperty().addListener(
            new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> o, Boolean oldValue, Boolean newValue) {
                    System.out.println("debug: armed:" + oldValue + "/" + newValue);
            }
        });

        // debug output
        spinnerValueFactoryOptions.setOnHiding(new EventHandler<Event>() {
            public void handle(Event t) {
                System.out.println("debug: hiding");
            }
        });

        // press this "reset" button to avoid undefined values when another test starts
        Button resetBtn = new Button("reset factory");
        resetBtn.setId("reset");
        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.out.println("debug: reset");
                setSpinnerValueFactory(spinner, spinnerValueFactoryOptions.getSelectionModel().selectedItemProperty().get());
            }
        });

        spinnerValueFactoryOptions.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
            setSpinnerValueFactory(spinner, newValue);
        });

        spinnerValueFactoryOptions.getSelectionModel().select(0);

        ComboBox<String> spinnerStyleClassOptions =
                new ComboBox<>(FXCollections.observableArrayList(
                        "Default (Arrows on right (Vertical))",
                        "Arrows on right (Horizontal)",
                        "Arrows on left (Vertical)",
                        "Arrows on left (Horizontal)",
                        "Split (Vertical)",
                        "Split (Horizontal)"));
        spinnerStyleClassOptions.setId("styleclass");
        spinnerStyleClassOptions.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
        spinner.getStyleClass().removeAll(
                    Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL,
                    Spinner.STYLE_CLASS_ARROWS_ON_LEFT_VERTICAL,
                    Spinner.STYLE_CLASS_ARROWS_ON_LEFT_HORIZONTAL,
                    Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL,
                    Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);

            switch (newValue) {
                case "Default (Arrows on right (Vertical))":
                    break;

                case "Arrows on right (Horizontal)": {
                    spinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
                    break;
                }

                case "Arrows on left (Vertical)": {
                    spinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_LEFT_VERTICAL);
                    break;
                }

                case "Arrows on left (Horizontal)": {
                    spinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_LEFT_HORIZONTAL);
                    break;
                }

                case "Split (Vertical)": {
                    spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
                    break;
                }

                case "Split (Horizontal)": {
                    spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
                    break;
                }
            }
        });
        spinnerStyleClassOptions.getSelectionModel().select(0);

        final CheckBox wrapAroundCheckBox = new CheckBox();
        wrapAroundCheckBox.selectedProperty().addListener((o, oldValue, newValue) ->
                spinner.getValueFactory().setWrapAround(newValue)
        );
        wrapAroundCheckBox.setId("wrapAroundCheckBox");

        final CheckBox editableCheckBox = new CheckBox();
        spinner.editableProperty().bind(editableCheckBox.selectedProperty());
        editableCheckBox.setId("editableCheckBox");

        final CheckBox rtlCheckBox = new CheckBox();
        rtlCheckBox.setId("righttoleft");
        rtlCheckBox.selectedProperty().addListener((o, oldValue, newValue) ->
                spinner.setNodeOrientation(newValue ? NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.INHERIT));

        spinner.setTooltip(new Tooltip("tooltip for spinner"));
        Label label = new Label("Effect:");


        final CheckBox transfCheckBox = new CheckBox();
        transfCheckBox.selectedProperty().addListener(new ChangeListener<Boolean> (){
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if (t1) {
                        spinner.getTransforms().add(new Scale(3, 3, -25, -25));
                                //new Scale()_//Shear(2,2));
                    } else {
                        spinner.getTransforms().clear();
                    }
                }
            });

        final CheckBox effectCheckBox = new CheckBox();
        effectCheckBox.selectedProperty().addListener(new ChangeListener<Boolean> (){
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if (t1) {
                        Effect badEffect = new  DropShadow(10,0,0,Color.GREEN) {{ setWidth(40);}};
                        Effect badEffect2 = new DropShadow(10., 0., 0., Color.BLACK) {{ setWidth(40);}};
                        Effect goodEffect = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 5., 0.3, 0., 0.);
                        Effect actualEffect = badEffect;

                        spinner.setEffect(actualEffect);
                        effectCheckBox.setEffect(actualEffect);
                        label.setEffect(actualEffect);
                    } else {
                        spinner.setEffect(null);
                        label.setEffect(null);
                        effectCheckBox.setEffect(null);
                    }
                }
            });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        int row = 0;

        Text txtBindedToValue = new Text();
        txtBindedToValue.textProperty().bind(spinner.valueProperty().asString());
        Text txtBindedToEditor = new Text();
        txtBindedToEditor.textProperty().bind(spinner.getEditor().textProperty());

        grid.add(new Label("Value Factory:"), 0, row);
        grid.add(new Text("text binded to value:"), 2, row);
        grid.add(txtBindedToValue, 3, row);
        grid.add(spinnerValueFactoryOptions, 1, row++);

        grid.add(new Label("Style Class:"), 0, row);
        grid.add(new Text("text binded to editor:"), 2, row);
        grid.add(txtBindedToEditor, 3, row);
        grid.add(spinnerStyleClassOptions, 1, row++);

        grid.add(new Label("Wrap around:"), 0, row);
        grid.add(wrapAroundCheckBox, 1, row++);

        grid.add(new Label("Editable:"), 0, row);
        grid.add(editableCheckBox, 1, row++);

        grid.add(new Label("Right-to-left:"), 0, row);
        grid.add(rtlCheckBox, 1, row++);

        final TextField tf = new TextField();
        tf.setId("textfield");
        grid.add(new Label("TextField:"), 0, row);
        grid.add(tf, 1, row++);

        grid.add(new Label("Transfromation:"), 0, row);
        grid.add(transfCheckBox, 1, row++);

        grid.add(resetBtn, 1, row++);


        grid.add(label, 0, row);
        grid.add(effectCheckBox, 1, row++);

        grid.add(new Label("Spinner:"), 0, row);
        grid.add(spinner, 1, row++);


        //  this TexField would be used as drop taregt
        tf.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasContent(DataFormat.PLAIN_TEXT)) {
                    event.acceptTransferModes(TransferMode.COPY);
                }
            }
        });

        tf.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean gotData = false;
                if (db.hasString()) {
                    gotData = true;
                    System.out.println("DragDropped:" + db.getString());
                    tf.setText(db.getString());
                }
                event.setDropCompleted(gotData);
            }
        });

        Scene scene = new Scene(grid, 650, 500);

        stage.setTitle("Hello Spinner");
        stage.setScene(scene);
        stage.show();


    }

    public void setSpinnerValueFactory(final Spinner _spinner, final String _newValue) {
        ObservableList<String> items = FXCollections.observableArrayList("Jonathan", "Julia", "Henry");
        switch (_newValue) {
            case "Integer": {
                _spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 10));
                break;
            }

            case "List<String>": {
                _spinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(items));
                break;
            }
            /*    postponed.  see https://javafx-jira.kenai.com/browse/RT-37968
            case "Calendar": {
                _spinner.setValueFactory(new SpinnerValueFactory.LocalDateSpinnerValueFactory());
                break;
            }
            */
            case "Double": {
                _spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 1.0, 0.5, 0.05));
                break;
            }
            default: {
                _spinner.setValueFactory(null);
                break;
            }

        }

    }
}