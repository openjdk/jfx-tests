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
package javafx.scene.control.test.focus;

import javafx.factory.ControlsFactory;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;

/**
 *
 * @author Andrey Glushchenko
 */
public class FocusTestApp extends BasicButtonChooserApp {

    public final static String TITLE = "FocusTest";
    public final static String LEFT_BUTTON_ID = "LEFT_BUTTON_ID";
    public final static String RIGHT_BUTTON_ID = "RIGHT_BUTTON_ID";
    public final static String CONTROL_ID = "CONTROL_ID";
    public final static String TRAVERSABLE_CHECK_ID = "TRAVERSABLE_CHECK_ID";
    public final static String DISABLE_CHECK_ID = "DISABLE_CHECK_ID";
    public final static String DISABLED_CHECK_ID = "DISABLED_CHECK_ID";

    public FocusTestApp() {
        super(800, 400, TITLE, false);
    }
    @Override
    protected TestNode setup() {
        TestNode rootTestNode = new TestNode();
        for(ControlsFactory factory:ControlsFactory.filteredValues()){
            rootTestNode.add(new Page(factory), factory.name());
        }
        return rootTestNode;
    }
    private class Page extends TestNode {

        private Button bLeft = new Button("left");
        private Button bRight = new Button("right");
        private Node control = null;
        private VBox root = new VBox();
        private CheckBox traversable = new CheckBox("Focus Traversable");
        private CheckBox disable = new CheckBox("Control Disable");
        private CheckBox disabled = new CheckBox("Control DisableD");

        public Page(ControlsFactory factory) {
            HBox testArea = new HBox();
            control = factory.createNode();
            bLeft.setId(LEFT_BUTTON_ID);
            control.setId(CONTROL_ID);
            bRight.setId(RIGHT_BUTTON_ID);
            testArea.getChildren().addAll(bLeft,control,bRight);
            testArea.setSpacing(30);
            traversable.setSelected(control.isFocusTraversable());
            traversable.setId(TRAVERSABLE_CHECK_ID);
            traversable.selectedProperty().bindBidirectional(control.focusTraversableProperty());
            disable.setSelected(control.isDisable());
            disable.selectedProperty().bindBidirectional(control.disableProperty());
            disable.setId(DISABLE_CHECK_ID);
            disabled.setSelected(control.isDisabled());
            disabled.selectedProperty().bind(control.disabledProperty());
            disabled.setDisable(true);
            disabled.setId(DISABLED_CHECK_ID);
            root.getChildren().addAll(testArea,traversable,disable,disabled);
            root.setSpacing(50);
        }
        @Override
        public Node drawNode() {
            return root;
        }
    }

    public static void main(String args[]) {
        test.javaclient.shared.Utils.launch(FocusTestApp.class, args);
    }
}
