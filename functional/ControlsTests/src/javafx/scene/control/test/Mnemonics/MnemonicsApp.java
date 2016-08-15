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
package javafx.scene.control.test.Mnemonics;

import java.lang.reflect.Method;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.factory.ControlsFactory;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.test.utils.PropertyCheckingGrid;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

public class MnemonicsApp extends InteroperabilityApp {
    public static final String LABELED_ID = "labaeled.id";

    public static void main(String[] args) {
        Utils.launch(MnemonicsApp.class, args); // args go to /dev/null/ for the moment
    }

    @Override
    public Scene getScene() {
        return new MnemonicsScene();
    }

    protected HBox box = new HBox(10);
    PropertyCheckingGrid grid = null;

    public class MnemonicsScene extends Scene {
        public MnemonicsScene() {
            super(box, 700, 400);
            Utils.addBrowser(this);

            final VBox object_box = new VBox(5);
            object_box.setMinWidth(200);
            final VBox control_box = new VBox(5);
            control_box.setMinWidth(300);
            ChoiceBox<ControlsFactory> choice = new ChoiceBox<ControlsFactory>();
            choice.setConverter(new StringConverter<ControlsFactory>() {
                @Override
                public String toString(ControlsFactory cl) {
                    //return cl.getControlClass().getSimpleName();
                    return cl.toString();
                }
                @Override
                public ControlsFactory fromString(String string) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            });
            for (ControlsFactory factory : ControlsFactory.filteredValues()) {
                final Class controlClass = factory.getControlClass();
                if (Labeled.class.isAssignableFrom(controlClass)) {
                    choice.getItems().add(factory);
                }
            }
            choice.valueProperty().addListener(new ChangeListener<ControlsFactory>() {
                public void changed(ObservableValue<? extends ControlsFactory> ov, ControlsFactory t, ControlsFactory t1) {
                    object_box.getChildren().clear();
                    final Labeled node = (Labeled)t1.createNode();
                    node.setId(LABELED_ID);
                    node.setText("_" + node.getText());
                    object_box.getChildren().add(node);
                    if (grid != null) {
                        control_box.getChildren().remove(grid);
                    }
                    grid = new PropertyCheckingGrid(node) {
                        @Override
                        protected boolean checkMethod(Object obj, Method m) {
                            return m.getDeclaringClass().equals(Labeled.class) && m.getName().contains("mnemonic");
                        }
                    };
                    control_box.getChildren().add(grid);
                }
            });

            control_box.getChildren().add(choice);

            box.getChildren().addAll(object_box, control_box);
        }
    }
}
