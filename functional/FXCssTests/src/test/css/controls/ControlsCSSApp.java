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
/**
 *
 * @author Andrey Nazarov
 */
package test.css.controls;

import java.net.URL;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import java.util.Set;
import javafx.application.Platform;
import javafx.scene.text.Text;
import test.css.controls.styles.Style;

import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.ScrollablePageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class ControlsCSSApp extends BasicButtonChooserApp {

    private static int width = 1500;
    private static int heigth = 900;
    private static boolean isSetterMode = false;
    public static final String SETTER_MODE = "SetterMode";
    TestNode rootTestNode = new TestNode();

    private static void setSetterMode(boolean isSetterMode) {
        ControlsCSSApp.isSetterMode = isSetterMode;
    }

    public ControlsCSSApp() {
        super(width, heigth, "CssControls", false);
    }

    public static void main(String args[]) {
        if (args != null && args.length > 0) {
            if (args[0].equals(SETTER_MODE)) {
                ControlsCSSApp.setSetterMode(true); //Now there is no way to init app in Launcher
            }
        }

        Utils.launch(ControlsCSSApp.class, args);
    }

    private void createPages(ControlPage page, TestNode rootTestNode) {
        createPages(page, rootTestNode, null);
    }


    private void createPages(ControlPage page, TestNode rootTestNode, String styleName) {
        ScrollablePageWithSlots pageWithSlot = new ScrollablePageWithSlots(page.name(), width, height);
        rootTestNode.add(pageWithSlot);
        Set<Style> styles = ControlsCssStylesFactory.getStyles(page, isSetterMode);
        for (final Style style : styles) {
            if (styleName == null) {
                createPage(pageWithSlot, page, style);
            }
            else if (style.name().equals(styleName)) {
                createPage(pageWithSlot, page, style);
            }
        }
    }

    private void createPage(ScrollablePageWithSlots pageWithSlot, ControlPage page, Style style) {
        Pane slot = new Pane();
        slot.setPrefSize(page.slotWidth, page.slotHeight);
        final Node control = page.factory.createControl();
        Pane innerPane = new Pane();
        innerPane.setLayoutX(ControlPage.INNER_PANE_SHIFT);
        innerPane.setLayoutY(ControlPage.INNER_PANE_SHIFT);
        innerPane.setPrefSize(page.slotWidth - ControlPage.INNER_PANE_SHIFT, page.slotHeight - ControlPage.INNER_PANE_SHIFT);
        innerPane.setMaxSize(page.slotWidth - ControlPage.INNER_PANE_SHIFT, page.slotHeight - ControlPage.INNER_PANE_SHIFT);
        slot.getChildren().add(innerPane);
        style.decorate(control, innerPane);
        if (control instanceof Text) {
            control.relocate(0, 0); // workaround RT-24670
        }
        style.setStyle(control);
        pageWithSlot.add(new StyleTestNode(style, control, innerPane));
    }

    public interface ControlFactory {

        /**
         * @return current control instance
         */
        public Node createControl();
    }

    @Override
    protected TestNode setup() {
        URL css = ControlsCSSApp.class.getResource("/test/css/resources/style.css");
        combinedTestChooserPresenter.getScene().getStylesheets().add(css.toExternalForm());
        if (showButtons) {
            for (ControlPage page : ControlPage.filteredValues()) {
                createPages(page, rootTestNode);
            }
        }
        return rootTestNode;
    }

    public void open(final ControlPage page) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createPages(page, rootTestNode);
            }
        });
    }

    public static class Person {

        Person(String firstName, String lastName) {
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
        }
        public StringProperty firstName;

        public void setFirstName(String value) {
            firstName.set(value);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public StringProperty firstNameProperty() {
            if (firstName == null) {
                firstName = new SimpleStringProperty();
            }
            return firstName;
        }
        public StringProperty lastName;

        public void setLastName(String value) {
            lastName.set(value);
        }

        public String getLastName() {
            return lastName.get();
        }

        public StringProperty lastNameProperty() {
            if (lastName == null) {
                lastName = new SimpleStringProperty();
            }
            return lastName;
        }
    }
}
