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
package org.jemmy.fx.control.caspian;

import javafx.scene.Node;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import org.jemmy.control.Wrap;
import org.jemmy.fx.control.TreeNodeWrap;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Scrollable2D;
import org.jemmy.timing.DescriptiveState;
import org.jemmy.timing.State;

/**
 * @author shura
 */
public class TreeItem implements org.jemmy.interfaces.TreeItem<javafx.scene.control.TreeItem> {

    private TreeNodeWrap<? extends javafx.scene.control.TreeItem> wrap;
    private Wrap<? extends TreeView> treeViewWrap;
    State<Boolean> expandedState = new DescriptiveState<>(()->wrap.isExpanded(), () -> "Wait a node <" + wrap + "> to be expanded/collapsed");


    public TreeItem(Wrap<? extends javafx.scene.control.TreeItem> wrap, Wrap<? extends TreeView> treeViewWrap) {
        if (!(wrap instanceof TreeNodeWrap)) {
            throw new IllegalArgumentException("Class " + wrap.getClass().getName()
                    + " is not supported by " + TreeItem.class.getName());
        }
        this.wrap = (TreeNodeWrap<? extends javafx.scene.control.TreeItem>) wrap;
        this.treeViewWrap = treeViewWrap;
    }

    private Wrap<? extends Node> findPointer(Wrap<?> skin) {
        final Parent<Node> parent = skin.as(Parent.class, Node.class);
        return parent.lookup(StackPane.class, cntrl -> cntrl.getChildren().size() == 0).wrap();
    }

    public void expand() {
        wrap.show();
        if (!wrap.isExpanded()) {
            if (treeViewWrap.as(Scrollable2D.class).isHorizontalScrollable()) {
                treeViewWrap.as(Scrollable2D.class).hto(treeViewWrap.as(Scrollable2D.class).hmin());
            }
            findPointer(wrap.getNode()).mouse().click();
            wrap.waitState(expandedState, true);
        }
    }

    public void collapse() {
        wrap.show();
        if (wrap.isExpanded()) {
            if (treeViewWrap.as(Scrollable2D.class).isHorizontalScrollable()) {
                treeViewWrap.as(Scrollable2D.class).hto(treeViewWrap.as(Scrollable2D.class).hmin());
            }
            findPointer(wrap.getNode()).mouse().click();
            wrap.waitState(expandedState, false);
        }
    }

    public Class<javafx.scene.control.TreeItem> getType() {
        return javafx.scene.control.TreeItem.class;
    }
}
