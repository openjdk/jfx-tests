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

import java.util.Collection;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Andrey Nazarov
 */
public class ScrollablePageWithSlots extends TestNode {

    private static final int DEFAULT_VGAP = 10;
    private static final int DEFAULT_HGAP = 10;
    private int VGAP = DEFAULT_VGAP;
    private int HGAP = DEFAULT_HGAP;

    public ScrollablePageWithSlots(String name, int a_width, int a_height) {
        super(name);
        setSize(a_height, a_width);
    }

    public void setVGap(int a_vgap) {
        VGAP = a_vgap;
    }

    public void setHGap(int a_hgap) {
        HGAP = a_hgap;
    }

    @Override
    void drawTo(Pane paneTo) {

        TilePane tilePane = new TilePane();
        tilePane.setHgap(HGAP);
        tilePane.setVgap(VGAP);

        if (0 == getActionHolderList().size()) {
            return;
        } else {
            for (ActionHolder ah : getActionHolderList()) {
                Collection<? extends Node> childnodes = ah.draw();
                if (null != childnodes && 0 != childnodes.size()) {
                    createSlot(((TestNode) ah).getName(), childnodes, tilePane);
                } else {
                    //  TODO
                }
            }
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(getWidth(), getHeight());
        scrollPane.setMaxSize(getWidth(), getHeight());
        scrollPane.setContent(tilePane);
        scrollPane.setStyle("-fx-padding: 10;-fx-background: white;-fx-border-color: gray;");

        paneTo.getChildren().add(scrollPane);
    }

    /**
     * This method adds a conponent to next free slot in the page. Slot is named box.
     * <p>Usage is optional - inheritors can add components directly to {@link AbstractApp#pageContent}
     * @param node component to add
     * @param name slot name
     */
    private Node createSlot(String name, Collection<? extends Node> nodes, Pane paneTo) {

        Pane slot = new VBox(0);
        slot.getChildren().add(new Label(name));
        slot.getChildren().addAll(nodes);
        slot.setStyle("-fx-border-color:lightblue;-fx-border-style: dashed;");
        paneTo.getChildren().add(slot);
        return slot;

    }
}
