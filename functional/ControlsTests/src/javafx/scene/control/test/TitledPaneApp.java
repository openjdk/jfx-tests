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
package javafx.scene.control.test;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.test.chart.BaseApp;
import javafx.scene.control.test.tabpane.TabApp.TitleLabel;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class TitledPaneApp extends BaseApp {

    public static int TABS_NUM = 5;

    public enum Pages {

        Constructors, Animated, Collapsible, Content, Expanded, ExpandedForNotCollapsible, Oversize
    }

    public TitledPaneApp() {
        super(800, 300, "TitledPane", false);
    }

    protected class Constructor2Node extends TestNode {

        public Constructor2Node() {
        }

        @Override
        public Node drawNode() {
            String title = "TitledPane";
            Label content = new Label("Content");
            TitledPane titledPane = new TitledPane(title, content);
            if (titledPane.getText() != title) {
                reportGetterFailure("getTitle()");
            }
            if (titledPane.getContent() != content) {
                reportGetterFailure("getTitle()");
            }
            titledPane.setMinSize(SLOT_WIDTH, SLOT_HEIGHT);
            titledPane.setMaxSize(SLOT_WIDTH, SLOT_HEIGHT);
            titledPane.setPrefSize(SLOT_WIDTH, SLOT_HEIGHT);
            titledPane.setStyle("-fx-border-color: darkgray;");
            return titledPane;
        }
    }

    protected class TitledSizingNode extends TestNode {

        double width;
        double height;
        Double content_width;
        Double content_height;

        public TitledSizingNode(double width, double height, Double content_width, Double content_height) {
            this.width = width;
            this.height = height;
            this.content_width = content_width;
            this.content_height = content_height;
        }

        @Override
        public Node drawNode() {
            TitledPane node = (TitledPane) createObject(width, height, content_width, content_height);
            return node;
        }
    }

    protected class ExpandedForNotCollapsibleNode extends TestNode {

        boolean expanded;

        public ExpandedForNotCollapsibleNode(boolean expanded) {
            this.expanded = expanded;
        }

        @Override
        public Node drawNode() {
            TitledPane titled_pane = (TitledPane) createObject();
            titled_pane.setCollapsible(false);
            return titled_pane;
        }
    }

    @Override
    protected TestNode setup() {

        pageSetup(Pages.Constructors.name(), new EmptyNode(), new Constructor2Node(), "TitledPane()", "TitledPane(title, content)");

        setupBoolean(Pages.Animated.name());

        setupBoolean(Pages.Collapsible.name());

        setupBoolean(Pages.Expanded.name());

        Node[] contents = {new TitleLabel("New Content 1"), new TitleLabel("New Content 2")};
        setupObject(Pages.Content.name(), contents);

        pageSetup(Pages.ExpandedForNotCollapsible.name(),
                new ExpandedForNotCollapsibleNode(true),
                new ExpandedForNotCollapsibleNode(false),
                "true",
                "false");

        pageSetup(Pages.Oversize.name(),
                new TitledSizingNode(SLOT_WIDTH, SLOT_HEIGHT, null, null),
                new TitledSizingNode(SLOT_WIDTH, SLOT_HEIGHT, SLOT_HEIGHT * 1.5, SLOT_WIDTH * 1.5),
                "Default Size",
                "Panes Oversize");

        return rootTestNode;
    }

    protected Object createObject() {
        return createObject(SLOT_WIDTH, SLOT_HEIGHT);
    }

    protected Object createObject(double width, double height) {
        return createObject(width, height, null, null);
    }

    protected Object createObject(double width, double height, Double tab_width, Double tab_height) {
        Label content = new Label("Content");
        if (tab_width != null && tab_height != null) {
            content.setMinSize(tab_width, tab_height);
            content.setMaxSize(tab_width, tab_height);
            content.setPrefSize(tab_width, tab_height);
        }
        TitledPane titled_pane = new TitledPane();
        titled_pane.setText("Title");
        titled_pane.setContent(content);
        titled_pane.setMinSize(width, height);
        titled_pane.setMaxSize(width, height);
        titled_pane.setPrefSize(width, height);
        titled_pane.setStyle("-fx-border-color: darkgray;");
        return titled_pane;
    }

    public static void main(String[] args) {
        Utils.launch(TitledPaneApp.class, args);
    }
}