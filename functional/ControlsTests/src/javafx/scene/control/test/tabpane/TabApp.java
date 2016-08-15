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

package javafx.scene.control.test.tabpane;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.test.chart.BaseApp;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class TabApp extends BaseApp {

    public static String TITLE_ID = "TITLE_ID";
    protected static final String[] BOOL = {"true", "false"};

    public enum Pages {
        Disable, Closable, Content, Graphic
    }

    public TabApp() {
        super(1100, 600, "Tab", false);
    }

    @Override
    protected TestNode setup() {

        setupBoolean(Pages.Closable.name());

        setupBoolean(Pages.Disable.name());

        Label contents[] = {new TitleLabel("New Content 1"), new TitleLabel("New Content 2")};
        setupObject(Pages.Content.name(), contents);

        Rectangle graphics[] = {new TitleRectangle(10, 10, new Color(0, 1, 0, 1)), new TitleRectangle(10, 10, new Color(0, 0, 1, 1))};
        setupObject(Pages.Graphic.name(), graphics);

        return rootTestNode;
    }

    @Override
    protected Node createNode(Object obj) {
        TabPane tab_pane = new TabPane();
        tab_pane.getTabs().add((Tab)obj);
        tab_pane.setMinSize(SLOT_WIDTH, SLOT_HEIGHT);
        tab_pane.setMaxSize(SLOT_WIDTH, SLOT_HEIGHT);
        tab_pane.setPrefSize(SLOT_WIDTH, SLOT_HEIGHT);
        tab_pane.setStyle("-fx-border-color: darkgray;");
        return tab_pane;
    }

    protected Object createObject() {
        return createObject(new Double(SLOT_WIDTH), new Double(SLOT_HEIGHT));
    }

    protected Object createObject(Double width, Double height) {
        Tab tab = new Tab("Tab");
        Label content = new Label("Content");
        content.setAlignment(Pos.TOP_LEFT);
        if (width != null && height != null) {
            content.setMinSize(width, height);
            content.setMaxSize(width, height);
            content.setPrefSize(width, height);
        }
        tab.setContent(content);
        return tab;
    }

    public static class TitleLabel extends Label {
        public TitleLabel(String text) {
            super(text);
            setId(TITLE_ID);
        }
        @Override
        public String toString() {
            return getText();
        }
    }

    public static class TitleRectangle extends Rectangle {
        public TitleRectangle(double width, double height, Paint paint) {
            super(width, height, paint);
        }
        @Override
        public String toString() {
            return "Rectangle " + getFill().toString();
        }
    }

    public static void main(String[] args) {
        Utils.launch(TabApp.class, args);
    }
}