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

package javafx.scene.control.test.choicebox;

import com.oracle.jdk.sqe.cc.markup.Covers;
import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;
import javafx.util.StringConverter;

/**
 *
 * @author shura
 */
public class ChoiceBoxApp extends InteroperabilityApp {

    public static final String ADD_FIELD = "add.field";
    public static final String SELECTION_LABEL = "selection.label";
    public static final String ADD_BUTTON_ID = "add.button";
    public static final String SHOW_BUTTON_ID = "show.button";
    public static final String HIDE_BUTTON_ID = "hide.button";
    public static final String GET_BUTTON_ID = "get.button";
    public static final String CONVERTER_BUTTON_ID = "converter.button";
    public static final String REMOVE_CONVERTER_BUTTON_ID = "remove.converter.button";
    public static final String GET_FIELD_ID = "get.field";
    public static final String CLEAR_BUTTON_ID = "clear.button";
    public static final String RESET_BUTTON_ID = "reset.button";
    public static final String SHOW_BOX_ID = "hide.button";
    public static final String IS_SHOWING_CHECK_ID = "is.showing.check";
    public static final String VALUES_LIST_ID = "values.list";
    public static final String SHOWING_PROPERTY_VAL_CHECK_ID = "showing.prop.val.check";
    public static final String SHOWING_PROPERTY_ARG1_CHECK_ID = "showing.prop.arg1.check";
    public static final String SHOWING_PROPERTY_ARG2_CHECK_ID = "showing.prop.arg2.check";
    public static final String TEST_PANE_ID = "test.pane";

    ChoiceBox cb;
    ListView objectList;

    @Override
    @Covers(value={"javafx.scene.control.ChoiceBox.showing.SET","javafx.scene.control.ChoiceBox.showing.GET","javafx.scene.control.ChoiceBox.showing.BIND","javafx.scene.control.ChoiceBox.showing.DEFAULT","javafx.scene.control.SingleSelectionModel.selectedItem.GET","javafx.scene.control.SingleSelectionModel.selectedItem.SET","javafx.scene.control.SingleSelectionModel.selectedItem.BIND"}, level=Level.FULL)
    protected Scene getScene() {
        cb = new ChoiceBox();

        HBox content = new HBox(5);
        Pane test_pane = new Pane();
        test_pane.setId(TEST_PANE_ID);

        test_pane.setPrefSize(300, 400);
        test_pane.setMinSize(300, 400);
        test_pane.setMaxSize(300, 400);
        content.getChildren().add(test_pane);

        test_pane.getChildren().add(cb);

        VBox controls = new VBox();

        final SimpleObjectProperty value = new SimpleObjectProperty();
        cb.valueProperty().bindBidirectional(value);

        value.addListener(new ChangeListener() {
            public void changed(ObservableValue ov, Object t, Object t1) {
                objectList.getSelectionModel().select(t1);
            }
        });

        objectList = new ListView();
        objectList.setId(VALUES_LIST_ID);
        objectList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue ov, Object t, Object t1) {
                value.setValue(t1);
            }
        });

        HBox selectionBox = new HBox();
        final Label selection = new Label("null");
        selection.setId(SELECTION_LABEL);
        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue ov, Object t, Object t1) {
                Object obj = cb.getSelectionModel().getSelectedItem();
                if (obj != null) {
                    selection.setText(obj.toString());
                } else {
                    selection.setText("");
                }
            }
        });

        final CheckBox checkShowing = new CheckBox("isShowing()");
        checkShowing.setId(IS_SHOWING_CHECK_ID);

        HBox showingPropertyBox = new HBox();
        final CheckBox checkShowingPropertyValue = new CheckBox("");
        checkShowingPropertyValue.setId(SHOWING_PROPERTY_VAL_CHECK_ID);
        showingPropertyBox.getChildren().add(checkShowingPropertyValue);

        final CheckBox checkShowingProperty1 = new CheckBox("");
        checkShowingProperty1.setId(SHOWING_PROPERTY_ARG1_CHECK_ID);
        showingPropertyBox.getChildren().add(checkShowingProperty1);

        final CheckBox checkShowingProperty2 = new CheckBox("showingProperty()");
        checkShowingProperty2.setId(SHOWING_PROPERTY_ARG2_CHECK_ID);
        showingPropertyBox.getChildren().add(checkShowingProperty2);

        cb.showingProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                checkShowingPropertyValue.setSelected(ov.getValue());
                checkShowingProperty1.setSelected(t);
                checkShowingProperty2.setSelected(t1);
                checkShowing.setSelected(cb.isShowing());
            }
        });

        selectionBox.getChildren().add(new Label("Selection: "));
        selectionBox.getChildren().add(selection);
        controls.getChildren().add(selectionBox);

        HBox addBox = new HBox();
        final TextField toAdd = new TextField();
        toAdd.setId(ADD_FIELD);
        Button addBtn = new Button("Add");
        addBtn.setId(ADD_BUTTON_ID);
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                cb.getItems().add(toAdd.getText());
                objectList.getItems().add(toAdd.getText());
            }
        });

        Button addLaterBtn = new Button("Add later");
        addLaterBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                        }
                        Platform.runLater(new Runnable() {
                            public void run() {
                                cb.getItems().add(toAdd.getText());
                            }
                        });
                    }
                }).start();
            }
        });

        HBox showBox = new HBox();

        Button showBtn = new Button("Show");
        showBtn.setId(SHOW_BUTTON_ID);
        showBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                cb.show();
            }
        });
        showBox.getChildren().add(showBtn);

        Button hideBtn = new Button("Hide");
        hideBtn.setId(HIDE_BUTTON_ID);
        hideBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                cb.hide();
            }
        });
        showBox.getChildren().add(hideBtn);

        HBox getterBox = new HBox();
        final TextField getField = new TextField();
        getField.setId(GET_FIELD_ID);
        getField.setEditable(false);

        HBox converterBox = new HBox();
        Button converterBtn = new Button("Set converter");
        converterBtn.setId(CONVERTER_BUTTON_ID);
        converterBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                cb.setConverter(new StringConverter() {
                    @Override
                    public String toString(Object t) {
                        return "Converted " + t.toString();
                    }
                    @Override
                    public Object fromString(String string) {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                });
            }
        });
        Button removeConverterBtn = new Button("Remove converter");
        removeConverterBtn.setId(REMOVE_CONVERTER_BUTTON_ID);
        removeConverterBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                cb.setConverter(null);
            }
        });
        converterBox.getChildren().add(converterBtn);
        converterBox.getChildren().add(removeConverterBtn);

        Button getBtn = new Button("Get");
        getBtn.setId(GET_BUTTON_ID);
        getBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if (cb.getValue() != null) {
                    getField.setText(cb.getValue().toString());
                }
            }
        });
        getterBox.getChildren().add(getBtn);
        getterBox.getChildren().add(getField);

        HBox clearBox = new HBox();
        Button clearBtn = new Button("Clear");
        clearBtn.setId(CLEAR_BUTTON_ID);
        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                cb.getItems().clear();
            }
        });
        clearBox.getChildren().add(clearBtn);
        Button resetBtn = new Button("Reset");
        resetBtn.setId(RESET_BUTTON_ID);
        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                reset();
            }
        });
        clearBox.getChildren().add(resetBtn);

        addBox.getChildren().add(toAdd);
        addBox.getChildren().add(addBtn);
        addBox.getChildren().add(addLaterBtn);
        controls.getChildren().add(getterBox);
        controls.getChildren().add(converterBox);
        controls.getChildren().add(clearBox);
        controls.getChildren().add(addBox);
        controls.getChildren().add(showBox);
        controls.getChildren().add(checkShowing);
        controls.getChildren().add(showingPropertyBox);
        controls.getChildren().add(objectList);
        content.getChildren().add(new Label("Values:"));
        content.getChildren().add(controls);
        Scene res = new Scene(content);

        reset();

        Utils.addBrowser(res);

        return res;
    }

    protected void reset() {
        cb.getItems().clear();
        cb.getItems().add("1");
        cb.getItems().add(3);
        cb.getItems().add(false);
        cb.setConverter(null);
        objectList.getItems().clear();
        objectList.getItems().addAll(cb.getItems());
        objectList.getItems().add("Unlisted");
    }

    public static void main(String args[]) {
        Utils.launch(ChoiceBoxApp.class, args);
    }
}
