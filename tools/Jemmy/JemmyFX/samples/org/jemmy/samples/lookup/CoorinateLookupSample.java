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
 * questions.
 */
package org.jemmy.samples.lookup;

import javafx.scene.control.Label;
import org.jemmy.fx.FXRelativeCoordinateLookup;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.junit.Before;
import org.junit.Test;

/**
 * This sample demonstrates how to find a node by it's location related to
 * another node.
 *
 * @author shura
 */
public class CoorinateLookupSample extends LookupSampleBase {

    SceneDock scene;
    NodeDock center;

    @Before
    public void findCenter() {
        //find the scene
        scene = new SceneDock();

        //let's find the central label (1,1)and then some others which placed
        //this or that way related to the center one
        center = new NodeDock(scene.asParent(), Label.class, cntrl -> cntrl.getText().equals("(1,1)"));
        before();
    }

    /**
     * Left-top.
     */
    @Test
    public void lt() {

        new NodeDock(scene.asParent(), Label.class, new FXRelativeCoordinateLookup<Label>(center.wrap(), false, -1, -1)).mouse().move();

        assureMouseOver("(0,0)");
    }

    /**
     * Left-bottom.
     */
    @Test
    public void lb() {
        new NodeDock(scene.asParent(), Label.class, new FXRelativeCoordinateLookup<Label>(center.wrap(), false, -1, 1)).mouse().move();

        assureMouseOver("(0,2)");
    }

    /**
     * Right-bottom including the anchor node (so it could be the node itself or
     * anything within its bounds).
     */
    @Test
    public void rbe() {
        new NodeDock(scene.asParent(), Label.class, new FXRelativeCoordinateLookup<Label>(center.wrap(), true, 1, 1)).mouse().move();

        assureMouseOver("(1,1)");
    }

    /**
     * Anything to the right.
     */
    @Test
    public void r() {
        new NodeDock(scene.asParent(), Label.class, new FXRelativeCoordinateLookup<Label>(center.wrap(), false, 1, 0)).mouse().move();

        assureMouseOver("(2,0)");
    }
}
