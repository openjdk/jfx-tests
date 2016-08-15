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
package javafx.scene.control.test.manual;

import java.time.chrono.Chronology;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class DatePickerAppManual extends InteroperabilityApp {

    public static void main(String[] args) {
        Utils.launch(DatePickerAppManual.class, args);
    }

    @Override
    protected Scene getScene() {
        return new DatePickerScene();
    }

    private class DatePickerScene extends Scene {

        public DatePickerScene() {
            super(new VBox(10), 300, 300);

            final DatePicker datePicker = new DatePicker();

            ComboBox comboBox = new ComboBox();
            comboBox.getItems().addAll(Chronology.getAvailableChronologies());
            comboBox.valueProperty().addListener(new ChangeListener<Chronology>() {
                public void changed(ObservableValue<? extends Chronology> ov, Chronology t, Chronology t1) {
                    datePicker.setChronology(t1);
                }
            });

            ((VBox) getRoot()).getChildren().addAll(datePicker, comboBox);
        }
    }
}