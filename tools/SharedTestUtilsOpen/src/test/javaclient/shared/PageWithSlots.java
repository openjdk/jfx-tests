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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author shubov
 */
public class PageWithSlots extends TestNode{

    private static final int DEFAULT_SLOTSIZE = 90;
    private static final int DEFAULT_YGAP = 20;
    /**
     * Slot side size
     */
    protected int SLOTSIZEX = DEFAULT_SLOTSIZE;
    protected int SLOTSIZEY = DEFAULT_SLOTSIZE;
    private int YGAP = DEFAULT_YGAP;
    /**
     * Next free slot horizontal position
     */
    private int shiftX = 0;
    /**
     * Next free slot vertical position
     */
    private int shiftY = 0;

    /**
     * Actual height
     */
    private int actualY = 0;

    public int getActualY() {
        return actualY;
    }

    //public static String debug_lastPageName;

    public PageWithSlots(String name, int a_height, int a_width) {
        super(name);
        setSize(a_height, a_width);
        //debug_lastPageName = name;
    }

    public void setSlotSize(int a_height, int a_width) {
        SLOTSIZEX = a_width;
        SLOTSIZEY = a_height;
    }

    public void setYGap(int a_ygap) {
        YGAP = a_ygap;
    }

    @Override
    void drawTo(Pane paneTo) {
        shiftX = 0;
        shiftY = 0;

        if (0 == getActionHolderList().size()) {
            return;
        } else {
            for (ActionHolder ah : getActionHolderList()) {
                Collection<? extends Node> childnodes = ah.draw();
                if (null != childnodes && 0 != childnodes.size()) {
                    createSlot(((TestNode)ah).getName(),childnodes, YGAP, paneTo);
                } else {
                  //  TODO
                }
            }
        }
    }


    /**
     * This method adds a conponent to next free slot in the page. Slot is named box with size controlled by {@link AbstractApp#SLOTSIZE}.
     * <p>Usage is optional - inheritors can add components directly to {@link AbstractApp#pageContent}
     * @param node component to add
     * @param name slot name
     * @param Ygap vertival gap between slots
     * @throws IndexOutOfBoundsException if there is no more slots left in the application screen
     */
    private Node createSlot(String name, Collection<? extends Node> nodes, int Ygap, Pane paneTo) {

        // check space avaiable for this slot

        if ((shiftY + SLOTSIZEY) > getHeight()) { // (height - 10)
            throw new IndexOutOfBoundsException("Slot is outside of the applicatin borders");
        }
        //System.err.println("Slot name: " + name);
        VBox slot = new VBox();

        slot.getChildren().add(new Text(name));
        //node.setTranslateY(3);
        slot.getChildren().addAll(nodes);
        slot.setTranslateX(shiftX);
        slot.setTranslateY(shiftY);
        paneTo.getChildren().add(slot);
        actualY = shiftY + SLOTSIZEY;

        int stepX = SLOTSIZEX + 1;
        int stepY = SLOTSIZEY + Ygap;
        shiftX += stepX;     // SLOTSIZE + 1;
        if ((shiftX + SLOTSIZEX) > getWidth()) {
            shiftX = 0;
            shiftY += stepY; //SLOTSIZE + 20;
        }
        return slot;

    }
}
