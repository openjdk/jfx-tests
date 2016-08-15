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
package javafx.scene.control.test.datepicker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class DatePickerApp extends InteroperabilityApp {

    public final static String TESTED_DATEPICKER_ID = "TESTED_DATEPICKER_ID";
    public final static String HARD_RESET_BUTTON_ID = "HARD_RESET_DATEPICKER_BUTTON_ID";
    public final static String SOFT_RESET_BUTTON_ID = "SOFT_RESET_DATEPICKER_BUTTON_ID";

    public static void main(String[] args) {
        Utils.launch(DatePickerApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "DatePickerTestApp");
        return new DatePickerApp.DatePickerScene();
    }

    class DatePickerScene extends CommonPropertiesScene {

        //VBox which contain tested date picker.
        Pane pane;
        PropertiesTable propertiesTable;
        //Date picker to be tested.
        DatePicker testedDatePicker;

        public DatePickerScene() {
            super("DatePicker", 800, 600);

            prepareScene();
        }

        @Override
        final protected void prepareScene() {
            Utils.addBrowser(this);
            pane = new Pane();
            testedDatePicker = new DatePicker();
            testedDatePicker.setId(TESTED_DATEPICKER_ID);

            propertiesTable = new PropertiesTable(testedDatePicker);
            propertiesTable.addObjectEnumPropertyLine((ObjectProperty) testedDatePicker.dayCellFactoryProperty(), Arrays.asList(new Callback[]{null, new Callback<DatePicker, DateCell>() {
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        {
                            setId("ID");
                        }
                    };
                }
                @Override public String toString() { return "Cells with ID"; }
            },
            new WorkingDays()}), testedDatePicker);

            propertiesTable.addObjectEnumPropertyLine((ObjectProperty) testedDatePicker.converterProperty(),
                    Arrays.asList(new StringConverter[]{null, new LocalDateConverter(testedDatePicker)}), testedDatePicker);

            PropertyTablesFactory.explorePropertiesList(testedDatePicker, propertiesTable);
            SpecialTablePropertiesProvider.provideForControl(testedDatePicker, propertiesTable);

//            propertiesTable.addObjectEnumPropertyLine(testedDatePicker.chronologyProperty(), Arrays.asList(
//                    Chronology.of("ISO"), Chronology.of("Minguo"), Chronology.of("ThaiBuddhist"), Chronology.of("Japanese"), Chronology.of("Hijrah-umalqura")));

            pane.setMinSize(240, 240);
            pane.setPrefSize(240, 240);
            pane.setStyle("-fx-border-color : red;");
            pane.getChildren().add(testedDatePicker);

            VBox vb = new VBox();
            vb.setSpacing(5);

            HBox hb = (HBox) getRoot();
            hb.setPadding(new Insets(5, 5, 5, 5));
            hb.setStyle("-fx-border-color : green;");

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
                    propertiesTable.refresh();
                    DatePicker newOne = new DatePicker();
                    testedDatePicker.setValue(newOne.getValue());
                    testedDatePicker.setConverter(newOne.getConverter());
                    testedDatePicker.setDayCellFactory(newOne.getDayCellFactory());
                    testedDatePicker.setShowWeekNumbers(newOne.isShowWeekNumbers());
                }
            });

            HBox resetButtonsHBox = new HBox();
            resetButtonsHBox.getChildren().addAll(hardResetButton, softResetButton);
            resetButtonsHBox.setAlignment(Pos.CENTER);

            VBox vb1 = new VBox(5);
            vb1.getChildren().addAll(resetButtonsHBox);

            setTestedControl(testedDatePicker);
            setControllersContent(vb1);
            setPropertiesContent(propertiesTable);
        }
    }

    public static class LocalDateConverter extends StringConverter<LocalDate> {

        public LocalDateConverter(DatePicker testedDatePicker) {
            testedDatePicker.setPromptText(pattern.toLowerCase());
        }

        String pattern = "yyyy-MM-dd";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

        @Override
        public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }

        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }

        @Override public String toString() { return pattern; }
    }

    public static class DummyConverter extends StringConverter<LocalDate> {
        @Override public String toString(LocalDate date) { return "Dummy"; }

        @Override public LocalDate fromString(String string) {
            return LocalDate.of(2000, Month.JANUARY, 1);
        }
    }

    /**
     * Class implements a DateCell factory. It disables days which are sundays.
     */
    public static class WorkingDays implements Callback<DatePicker, DateCell> {

        public DateCell call(DatePicker param) {
            return new DateCell() {

                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (isRestricted(item)) {
                        setStyle("-fx-background-color: #ff4444;");
                        setDisable(true);
                    }
                }
            };
        }

        protected static boolean isRestricted(LocalDate item) {
            return DayOfWeek.SUNDAY == item.getDayOfWeek();
        }

        @Override public String toString() { return "Except sundays"; }
    }
}