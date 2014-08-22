/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
 */

package test.javaclient.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

/**
 *
 * @author shubov
 */

public class TestNode implements ActionHolder {

    private List<TestNode> actionHolderList = new ArrayList<TestNode>();
    private String name;
    private int height = 300;
    private int width = 300;

    public void additionalAction() {
        for (ActionHolder ah : actionHolderList) {
            ah.additionalAction();
        }
    }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }

    public void setSize(int _height, int _width) {
        height = _height;
        width = _width;
    }

    public TestNode search(String top_level, String inner_level) {
        TestNode found = null;
        found = search(top_level);
        if (null != found) {
            found = found.search(inner_level);
        }

        return found;
    }

    public TestNode search(String name_to_find) {
        TestNode found = null;
        if(name.equals(name_to_find)) {
                found = this;
        } else {
            for (TestNode tn : actionHolderList) {
                found = tn.search(name_to_find);
                if (null != found)
                    break;
            }
        }
        return found;

    }

    public List<TestNode> getActionHolderList() {
        return actionHolderList;
    }

    public TestNode() {
        this("unnamedTestNode");
    }

    public TestNode(String nodeName) {
        this.name = nodeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(TestNode tn) {
        add(tn, tn.getName());
    }

    public void add(TestNode tn, String nodeName) {
        tn.setName(nodeName);
        actionHolderList.add(tn);
    }

    // default method to show group of tests: draw TilePane with slots.
    public Node drawNode() {
        if (0 == actionHolderList.size()) {
            return null;
        }
        TilePane tilePane = new TilePane();
        for (ActionHolder ah : actionHolderList) {
            tilePane.getChildren().add(ah.drawNode());
        }
        return tilePane;

    }

    public Collection<? extends Node> draw() {
        List<Node> resultList = new ArrayList<Node>();
        Node node;
        if (0 == actionHolderList.size()) {
            node = drawNode();
        } else {
            TilePane tilePane = new TilePane();
            for (ActionHolder ah : actionHolderList) {
                Collection<? extends Node> childnodes = ah.draw(); //was: //            tilePane.getChildren().add(ah.drawNode());
                if (null != childnodes && 0 != childnodes.size()) {
                    tilePane.getChildren().addAll(ah.draw());
                } else {
                    tilePane.getChildren().add(ah.drawNode());
                }
            }
            node = tilePane;
        }
        resultList.add(node);
        return resultList;
    }

    void drawTo(Pane pane) {
        pane.getChildren().addAll(draw());
    }
}
