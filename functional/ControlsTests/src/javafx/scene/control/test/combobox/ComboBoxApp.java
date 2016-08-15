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
package javafx.scene.control.test.combobox;

import java.text.FieldPosition;
import java.text.Format;
import java.text.MessageFormat;
import java.text.ParsePosition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.CustomStringConverter;
import javafx.scene.control.test.utils.SingleSelectionModelImpl;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class ComboBoxApp extends InteroperabilityApp {

    public final static String TESTED_COMBOBOX_ID = "TESTED_COMBOBOX_ID";
    public final static String HARD_RESET_BUTTON_ID = "HARD_RESET_COMBOBOX_BUTTON_ID";
    public final static String SOFT_RESET_BUTTON_ID = "SOFT_RESET_COMBOBOX_BUTTON_ID";
    public final static String ADD_ITEM_BUTTON_ID = "ADD_ITEM_BUTTON_ID";
    public final static String ADD_ITEM_POSITION_TEXT_FIELD_ID = "ADD_ITEM_POSITION_TEXT_FIELD_ID";
    public final static String ADD_ITEM_TEXT_FIELD_ID = "ADD_ITEM_TEXT_FIELD_ID";
    public final static String REMOVE_BUTTON_ID = "REMOVE_BUTTON_ID";
    public final static String REMOVE_ITEM_POS_TEXT_FIELD_ID = "REMOVE_ITEM_POS_TEXT_FIELD_ID";
    public final static String ON_ACTION_EVENT_COUNTER_ID = "ON_ACTION_EVENT_COUNTER";
    public final static String APPLY_CUSTOM_SELECTION_MODEL_BUTTON_ID = "APPLY_CUSTOM_SELECTION_MODEL_BUTTON_ID";
    public final static String APPLY_CUSTOM_STRING_CONVERTER_BUTTON_ID = "APPLY_CUSTOM_STRING_CONVERTER_BUTTON_ID";
    public final static String CUSTOM_CELL_START_EDIT_COUNTER = "CELL START EDIT";
    public final static String CUSTOM_CELL_CANCEL_EDIT_COUNTER = "CELL CANCEL EDIT";
    public final static String CUSTOM_CELL_UPDATE_EDIT_COUNTER = "CELL UPDATE EDIT";
    public final static String APPLY_CUSTOM_CELL_FACTORY_BUTTON_ID = "APPLIED CUSTOM CELL FACTORY";
    public final static String CALLBACK_CALLED_ID = "CALLBACK CALLED";
    public final static String CUSTOM_CELL_UPDATE_EDIT_FOR_EDITABLE_CELL_COUNTER = "UPDATE EDIT FOR EDITABLE CELL";
    public final static String CUSTOM_CELL_FACTORY_ITEM_SUFFIX = "Custom";
    public final static String INITIAL_VALUE = "Initial value";
    public final static String SET_ON_SHOWING_COUNTER = "SET_ON_SHOWING_COUNTER";
    public final static String SET_ON_SHOWN_COUNTER = "SET_ON_SHOWN_COUNTER";
    public final static String SET_ON_HIDING_COUNTER = "SET_ON_HIDING_COUNTER";
    public final static String SET_ON_HIDEN_COUNTER = "SET_ON_HIDEN_COUNTER";
    public final static String SET_ADDING_ELEMENTS_ON_SHOWING = "SET_ADDING_ELEMENTS_ON_SHOWING";
    public final static String RESTORE_ON_SHOWING_EVENT_HANDLER = "RESTORE_ON_SHOWING_EVENT_HANDLER";
    public final static String POPULATE_COMBOBOX_WITH_FONT_SIZES = "POPULATE_COMBOBOX_WITH_FONT_SIZES";
    public final static String SET_ITEMS_NULL_BUTTON_ID = "SET_ITEMS_NULL_BUTTON_ID";
    public final static int INITIAL_FONT_SIZE = 8;
    public final static int MAX_FONT_SIZE = 36;

    public static void main(String[] args) {
        Utils.launch(ComboBoxApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "ComboBoxTestApp");
        return new ComboBoxScene();
    }

    class ComboBoxScene extends CommonPropertiesScene {

        PropertiesTable tb;
        //ComboBox to be tested.
        ComboBox<String> testedComboBox;

        public ComboBoxScene() {
            super("ComboBox", 800, 600);

            prepareScene();
        }

        @Override
        final protected void prepareScene() {
            Utils.addBrowser(this);
            testedComboBox = new ComboBox<String>();
            testedComboBox.setId(TESTED_COMBOBOX_ID);

            tb = new PropertiesTable(testedComboBox);
            tb.addSimpleListener(testedComboBox.getSelectionModel().selectedIndexProperty(), testedComboBox.getSelectionModel());
            tb.addSimpleListener(testedComboBox.getSelectionModel().selectedItemProperty(), testedComboBox.getSelectionModel());
            tb.addSimpleListener(testedComboBox.getEditor().parentProperty(), testedComboBox.getEditor());
            tb.addStringLine(testedComboBox.getEditor().textProperty(), "", testedComboBox.getEditor());
            tb.addStringLine(testedComboBox.valueProperty(), INITIAL_VALUE);
            PropertyTablesFactory.explorePropertiesList(testedComboBox, tb);

            tb.addCounter(SET_ON_SHOWING_COUNTER);
            testedComboBox.setOnShowing(new EventHandler<Event>() {
                public void handle(Event t) {
                    tb.incrementCounter(SET_ON_SHOWING_COUNTER);
                }
            });

            tb.addCounter(SET_ON_SHOWN_COUNTER);
            testedComboBox.setOnShown(new EventHandler<Event>() {
                public void handle(Event t) {
                    tb.incrementCounter(SET_ON_SHOWN_COUNTER);
                }
            });

            tb.addCounter(SET_ON_HIDING_COUNTER);
            testedComboBox.setOnHiding(new EventHandler<Event>() {
                public void handle(Event t) {
                    tb.incrementCounter(SET_ON_HIDING_COUNTER);
                }
            });

            tb.addCounter(SET_ON_HIDEN_COUNTER);
            testedComboBox.setOnHidden(new EventHandler<Event>() {
                public void handle(Event t) {
                    tb.incrementCounter(SET_ON_HIDEN_COUNTER);
                }
            });

            tb.addCounter(ON_ACTION_EVENT_COUNTER_ID);
            testedComboBox.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    tb.incrementCounter(ON_ACTION_EVENT_COUNTER_ID);
                }
            });

            tb.addCounter(CUSTOM_CELL_CANCEL_EDIT_COUNTER);
            tb.addCounter(CUSTOM_CELL_START_EDIT_COUNTER);
            tb.addCounter(CUSTOM_CELL_UPDATE_EDIT_COUNTER);
            tb.addCounter(CALLBACK_CALLED_ID);

            final SingleSelectionModel<String> selectionModel = new SingleSelectionModelImpl<String>(testedComboBox.getItems());
            Button flushCalledMethodsInSelectionModel = new Button("Flush called methods");
//            flushCalledMethodsInSelectionModel.setOnAction(new EventHandler<ActionEvent>() {
//
//                public void handle(ActionEvent t) {
//                    CMSM.setLogBuf(PropertyValueController.getLogger().textProperty());
//                    CMSM.flushCalledMethods();
//                    testedComboBox.setSelectionModel(selectionModel);
//                }
//            });

            Button applyCustomSelectionModel = ButtonBuilder.create().text("Apply custom selection model").id(APPLY_CUSTOM_SELECTION_MODEL_BUTTON_ID).build();
            applyCustomSelectionModel.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedComboBox.selectionModelProperty().setValue(selectionModel);
                }
            });

            Button applyCustomStringConverter = ButtonBuilder.create().text("Apply custom string converter").id(APPLY_CUSTOM_STRING_CONVERTER_BUTTON_ID).build();
            applyCustomStringConverter.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedComboBox.setConverter(new CustomStringConverter());
                }
            });

            Button applyCustomCellFactory = ButtonBuilder.create().text("Apply custom cell factory").id(APPLY_CUSTOM_CELL_FACTORY_BUTTON_ID).build();
            applyCustomCellFactory.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedComboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                        public ListCell<String> call(ListView<String> p) {
                            tb.incrementCounter(CALLBACK_CALLED_ID);
                            return new TextFieldListCell();
                        }
                    });
                }
            });

            Button hardResetButton = ButtonBuilder.create().id(HARD_RESET_BUTTON_ID).text("Hard reset").build();
            hardResetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareMainSceneStructure();
                    prepareScene();
                }
            });

            Button softResetButton = ButtonBuilder.create().id(SOFT_RESET_BUTTON_ID).text("Soft reset").build();
            softResetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    tb.refresh();
                    ComboBox<String> newOne = new ComboBox<String>();
                    //testedComboBox.setItems(FXCollections.observableArrayList());//RT-18945
                    while (testedComboBox.getItems().size() != 0) {
                        testedComboBox.getItems().remove(0);
                    }
                    testedComboBox.setPrefHeight(newOne.getPrefHeight());
                    testedComboBox.setPrefWidth(newOne.getPrefWidth());
                    testedComboBox.setVisibleRowCount(newOne.getVisibleRowCount());
                    testedComboBox.setPromptText(newOne.getPromptText());
                    testedComboBox.setValue(null);
                    testedComboBox.setEditable(newOne.isEditable());
                    testedComboBox.setVisible(newOne.isVisible());
                    //testedComboBox.showingProperty().setValue(newOne.isShowing());//Showing is readonly from 2.1.0b10.
                    testedComboBox.setConverter(newOne.getConverter());
                    testedComboBox.setCellFactory(newOne.getCellFactory());
                }
            });

            HBox resetButtonsHBox = new HBox();
            resetButtonsHBox.getChildren().addAll(hardResetButton, softResetButton);

            Button setAddingElementsOnShowing = ButtonBuilder.create()
                    .id(SET_ADDING_ELEMENTS_ON_SHOWING)
                    .text("Set adding elements on showing")
                    .build();

            setAddingElementsOnShowing.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedComboBox.setOnShowing(new EventHandler<Event>() {
                        public void handle(Event t) {
                            int index = ((ComboBox) testedComboBox).getItems().size();
                            ((ComboBox) testedComboBox).getItems().add(index, INITIAL_VALUE);

                            tb.incrementCounter(SET_ON_SHOWING_COUNTER);
                        }
                    });
                }
            });

            Button restoreDefaultOnShowingHandler = ButtonBuilder.create()
                    .id(RESTORE_ON_SHOWING_EVENT_HANDLER)
                    .text("Restore default onShowing handler")
                    .build();

            restoreDefaultOnShowingHandler.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedComboBox.setOnShowing(new EventHandler<Event>() {
                        public void handle(Event t) {
                            tb.incrementCounter(SET_ON_SHOWING_COUNTER);
                        }
                    });
                }
            });

            Button populateComboBoxWithFontSizes = ButtonBuilder.create()
                    .id(POPULATE_COMBOBOX_WITH_FONT_SIZES)
                    .text("Populate ComboBox with font sizes")
                    .build();

            populateComboBoxWithFontSizes.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {

                    testedComboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                        @Override
                        public ListCell<String> call(ListView<String> p) {
                            return new ListCell<String>() {
                                {
                                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                                }

                                @Override
                                protected void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);

                                    if (item == null || empty) {
                                        setText(null);
                                    } else {
                                        setText(item);
                                        setFont(new Font(Double.valueOf(item)));
                                    }
                                }
                            };
                        }
                    });

                    for (int i = INITIAL_FONT_SIZE; i <= MAX_FONT_SIZE; i += 2) {
                        testedComboBox.getItems().add(String.valueOf(i));
                    }
                }
            });

            setTestedControl(testedComboBox);

            VBox vb = new VBox();
            vb.setSpacing(5);
            vb.getChildren().addAll(resetButtonsHBox, getAddItemHBox(), getRemoveItemHBox(),
                    applyCustomSelectionModel, applyCustomStringConverter,
                    flushCalledMethodsInSelectionModel, applyCustomCellFactory,
                    setAddingElementsOnShowing,
                    restoreDefaultOnShowingHandler,
                    populateComboBoxWithFontSizes,
                    getSetItemsNullHBox());
            setControllersContent(vb);

            setPropertiesContent(tb);
        }

        private HBox getAddItemHBox() {
            HBox hb = new HBox();
            Label lb = new Label("Add item");
            final TextField tf = TextFieldBuilder.create().prefWidth(50).id(ADD_ITEM_TEXT_FIELD_ID).build();
            Label atLb = new Label("at pos");
            final TextField tfPos = TextFieldBuilder.create().prefWidth(50).id(ADD_ITEM_POSITION_TEXT_FIELD_ID).build();
            Button bt = ButtonBuilder.create().text("Add!").id(ADD_ITEM_BUTTON_ID).build();
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int index = Integer.parseInt(tfPos.getText());
                    ((ComboBox) testedComboBox).getItems().add(index, tf.getText());
                }
            });
            hb.getChildren().addAll(lb, tf, atLb, tfPos, bt);
            return hb;
        }

        private HBox getRemoveItemHBox() {
            HBox hb = new HBox();
            Label lb = new Label("From position");
            final TextField tf = TextFieldBuilder.create().text("0").prefWidth(50).id(REMOVE_ITEM_POS_TEXT_FIELD_ID).build();
            Button bt = ButtonBuilder.create().text("Remove!").id(REMOVE_BUTTON_ID).build();
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int index = Integer.parseInt(tf.getText());
                    ((ComboBox) testedComboBox).getItems().remove(index);
                }
            });
            hb.getChildren().addAll(lb, tf, bt);
            return hb;
        }

        private HBox getSetItemsNullHBox() {
            HBox hb = new HBox();

            Button bt = ButtonBuilder.create().text("Set items null").id(SET_ITEMS_NULL_BUTTON_ID).build();
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    ((ComboBox) testedComboBox).setItems(null);
                }
            });
            hb.getChildren().addAll(bt);
            return hb;
        }

        class TextFieldListCell extends ListCell<String> {

            public TextFieldListCell() {
                setEditable(true);
            }

            @Override
            public void startEdit() {
                tb.incrementCounter(CUSTOM_CELL_START_EDIT_COUNTER);
                super.startEdit();

                setText(null);
                setGraphic(null);
            }

            @Override
            public void cancelEdit() {
                tb.incrementCounter(CUSTOM_CELL_CANCEL_EDIT_COUNTER);
                super.cancelEdit();

                setText(null);
                setGraphic(null);
            }

            @Override
            public void updateItem(String item, boolean empty) {
                tb.incrementCounter(CUSTOM_CELL_UPDATE_EDIT_COUNTER);
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (isEditing()) {
                        tb.incrementCounter(CUSTOM_CELL_UPDATE_EDIT_FOR_EDITABLE_CELL_COUNTER);
                    } else {
                        setText(CUSTOM_CELL_FACTORY_ITEM_SUFFIX + item);
                        setGraphic(null);
                    }
                }
            }
        }
    }

    /**
     * MyMessageFormat wraps java.text.MessageFormat to provide a possibility to
     * format only one object, in order to be used by
     * javafx.util.converter.FormatStringConverter, because
     * java.text.MessageFormat works only with array of objects.
     *
     * @see java.text.MessageFormat
     * @see javafx.util.converter.FormatStringConverter
     */
    public static class MyMessageFormat extends Format {

        private MessageFormat fmt;

        public MyMessageFormat(MessageFormat fmt) {
            this.fmt = fmt;
        }

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            if (!obj.getClass().isAssignableFrom(Object[].class)) {
                return fmt.format(new Object[]{obj}, toAppendTo, pos);
            } else {
                return fmt.format(obj, toAppendTo, pos);
            }
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            Object parseObject = fmt.parseObject(source, pos);
            if (parseObject.getClass().isAssignableFrom(Object[].class)) {
                int length = ((Object[]) parseObject).length;
                if (length == 1) {
                    return ((Object[]) parseObject)[0];
                }
            }
            return parseObject;
        }
    }
}
