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

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.ptables.AbstractPropertyValueSetter.BindingType;
import javafx.scene.control.test.utils.ptables.WeakPropertyValueController;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * Application for :
 *
 * http://javafx-jira.kenai.com/browse/RT-28540
 * http://javafx-jira.kenai.com/browse/RT-13294
 *
 * There is a titledPane, which is used for checking animation of arrow.
 *
 * @author Alexander Kirov
 */
public class TitledPaneApp extends InteroperabilityApp {

    public static void main(String[] args) {
        Utils.launch(TitledPaneApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "TitledPaneApp");
        return new TitledPaneScene();
    }

    public class TitledPaneScene extends CommonPropertiesScene {

        TitledPane pane;
        VBox properties;

        public TitledPaneScene() {
            super("TitledPaneScene", 700, 300);
            prepareScene();
        }

        @Override
        final protected void prepareScene() {
            pane = new TitledPane("Titled pane", new Label("This is content"));
            //pane.setMinSize(200, 200);

            properties = new VBox(10);
            properties.setPadding(new Insets(10));

            addController(new WeakPropertyValueController(pane.animatedProperty(), pane));

            setTestedControlContainerSize(250, 250);
            setTestedControl(pane);
            setPropertiesContent(properties);
        }

        private void addController(WeakPropertyValueController controller) {
            properties.getChildren().addAll(new Separator(Orientation.HORIZONTAL), controller);
        }
    }
}