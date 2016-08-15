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
package javafx.scene.control.test.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public abstract class CommonPropertiesScene extends Scene {

    private Pane testedControlContainer = new Pane();
    private VBox leftVBox = new VBox();
    private HBox hb = (HBox) getRoot();
    private VBox controllersVb = new VBox(5);
    private Pane propertiesPane = new Pane();
    private int defaultTestedControlPaneWidth = 220;
    private int defaultTestedControlPaneHeight = 220;
    private BooleanProperty nonTestedContentVisibility = new SimpleBooleanProperty(true);

    public CommonPropertiesScene(String controlName, int width, int height) {
        super(new HBox(), width, height);

        Utils.addBrowser(this);
        hb.setPadding(new Insets(5, 5, 5, 5));
        hb.setStyle("-fx-border-color : green;");

        setTestedControlContainerSize(defaultTestedControlPaneWidth, defaultTestedControlPaneHeight);
        testedControlContainer.setStyle("-fx-border-color : red;");

        ToggleButton visibility = new ToggleButton("V");
        visibility.setSelected(true);
        nonTestedContentVisibility.bindBidirectional(visibility.selectedProperty());
        visibility.setMinSize(10, 10);
        visibility.setPrefSize(10, 10);
        visibility.setMaxSize(10, 10);

        HBox headerHb = new HBox(5);
        headerHb.getChildren().addAll(visibility, new Label("Pane with tested " + controlName));

        leftVBox.getChildren().addAll(headerHb, testedControlContainer, controllersVb);

        leftVBox.setSpacing(5);
        //Main scene layout.
        prepareMainSceneStructure();

        nonTestedContentVisibility.addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                controllersVb.setVisible(t1);
                propertiesPane.setVisible(t1);
            }
        });

        prepareScene();
    }

    final protected void prepareMainSceneStructure() {
        hb.getChildren().addAll(leftVBox, propertiesPane);
    }

    abstract protected void prepareScene();

    public final void setPropertiesContent(Parent content) {
        propertiesPane.getChildren().clear();
        propertiesPane.getChildren().add(content);
    }

    public final void setControllersContent(Parent content) {
        controllersVb.getChildren().clear();
        controllersVb.getChildren().add(content);
    }

    public final void setTestedControl(Node testedControl) {
        testedControlContainer.getChildren().clear();
        testedControlContainer.getChildren().add(testedControl);
    }

    public final Node getTestedControl() {
        return testedControlContainer.getChildren().get(0);
    }

    public final void setTestedControlContainerSize(double width, double height) {
        testedControlContainer.setPrefSize(width, height);
        testedControlContainer.setMinSize(width, height);
    }

    public final void setNonTestedContentVisibility(Boolean visibility) {
        nonTestedContentVisibility.setValue(visibility);
    }
}
