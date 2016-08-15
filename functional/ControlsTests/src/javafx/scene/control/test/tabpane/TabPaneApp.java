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

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.test.chart.BaseApp;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class TabPaneApp extends BaseApp {
    public static int TABS_NUM = 5;
    public enum Pages {
        RotateGraphic, Selection, Side, TabMaxHeight, TabMaxWidth, TabMinHeight, TabMinWidth, TabClosingPolicy
    }

    public TabPaneApp() {
        super(1100, 600, "TabPane", false);
    }

    protected class SelectionNode extends TestNode {
        protected int index;
        public SelectionNode(int index) {
            this.index = index;
        }

        @Override
        public Node drawNode() {
            TabPane node = (TabPane)createObject();
            node.getSelectionModel().select(index);
            return node;
        }
    }

    @Override
    protected TestNode setup() {

        Boolean rotate[] = {false, true};
        TestNode rotate_nodes[] = new TestNode[rotate.length];
        for (int i = 0; i < rotate.length; i++) {
            rotate_nodes[i] = new StandardBooleanSetterNode(Pages.RotateGraphic.name(), rotate[i]) {
                @Override
                public Object create() {
                    Object ret = super.create();
                    ((TabPane)ret).setSide(Side.LEFT);
                    return ret;
                }
            };
        }
        pageSetup(Pages.RotateGraphic.name(), rotate_nodes, rotate);

        final PageWithSlots selection_page = new PageWithSlots(Pages.Selection.name(), height, width);
        selection_page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        for (int i = 0; i < TABS_NUM; i++) {
            selection_page.add(new SelectionNode(i), Pages.Selection.name() + " " + i);
        }
        rootTestNode.add(selection_page);

        setupObject(Pages.Side.name(), Side.values());

        sizeSetup(Pages.TabMaxHeight.name(), null, new Double(65));

        sizeSetup(Pages.TabMaxWidth.name(), new Double(65), null);

        sizeSetup(Pages.TabMinHeight.name(), null, null);

        sizeSetup(Pages.TabMinWidth.name(), null, null);

        setupObject(Pages.TabClosingPolicy.name(), TabPane.TabClosingPolicy.values());
        return rootTestNode;
    }

    protected void sizeSetup(String name, final Double width, final Double height) {
        Double sizes[] = {0.0, 5.0, 15.0, 25.0, 65.0};
        TestNode size_nodes[] = new TestNode[sizes.length];
        for (int i = 0; i < sizes.length; i++) {
            size_nodes[i] = new StandardDoubleSetterNode(name, sizes[i]) {
                @Override
                public Node create() {
                    Node ret = (Node)createObject(SLOT_WIDTH, SLOT_HEIGHT, width, height);
                    return ret;
                }
            };
        }
        pageSetup(name, size_nodes, sizes);
    }

    @Override
    protected Object createObject() {
        return createObject(SLOT_WIDTH, SLOT_HEIGHT);
    }

    protected Object createObject(double width, double height) {
        return createObject(width, height, null, null);
    }

    protected Object createObject(double width, double height, Double tab_width, Double tab_height) {
        TabPane tab_pane = new TabPane();
        for (int i = 0; i < TABS_NUM; i++) {
            Tab tab = new Tab("Tab " + i);
            Label label = new Label("Tab's " + i + " content");
            tab.setContent(label);
            VBox box  = new VBox();
            box.getChildren().add(createRect(tab_width, tab_height, new Color(0.0, 1.0, 0.0, 1.0)));
            box.getChildren().add(createRect(tab_width, tab_height, new Color(0.0, 0.0, 1.0, 1.0)));
            tab.setGraphic(box);
            tab_pane.getTabs().add(tab);
        }
        tab_pane.setMaxSize(width, height);
        tab_pane.setPrefSize(width, height);
        tab_pane.setStyle("-fx-border-color: darkgray;");
        return tab_pane;
    }

    protected Rectangle createRect(Double tab_width, Double tab_height, Color color) {
        return new Rectangle(tab_width == null ? 10 : tab_width,
                             tab_height == null ? 5 : tab_height/2,
                             color);
    }

    public static void main(String[] args) {
        Utils.launch(TabPaneApp.class, args);
    }
}