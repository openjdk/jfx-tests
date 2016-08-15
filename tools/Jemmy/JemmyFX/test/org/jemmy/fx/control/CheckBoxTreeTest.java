/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
package org.jemmy.fx.control;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.samples.SampleBase;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexander Kirov
 */
public class CheckBoxTreeTest extends SampleBase {

    static Wrap<? extends TreeView> tree;
    static Parent<TreeItem> treeAsParent;
    static Wrap<? extends Scene> scene;
    static Selectable<TreeItem> treeAsSelectable;
    static Parent<Node> parent;

    @BeforeClass
    public static void setUpClass() throws Exception {
        startApp(CheckBoxTreeApp.class);
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        tree = (Wrap<? extends TreeView>) parent.lookup(TreeView.class).wrap();
        treeAsParent = tree.as(Parent.class, TreeItem.class);
        treeAsSelectable = tree.as(Selectable.class, TreeItem.class);
    }

    @Test
    public void setUp() {
        parent.lookup(Button.class, new ByID<Button>(CheckBoxTreeApp.RESET_SCENE_BTN_ID)).wrap().mouse().click();
        final Lookup<TreeItem> lookup = treeAsParent.lookup();
        boolean all_expanded = false;
        while (!all_expanded) {
            all_expanded = true;
            for (int i = 0; i < lookup.size(); i++) {
                final TreeNodeWrap<? extends javafx.scene.control.TreeItem> wrap = (TreeNodeWrap<? extends javafx.scene.control.TreeItem>) lookup.wrap(i);
                if (!wrap.isExpanded() && !wrap.isLeaf()) {
                    all_expanded = false;
                    wrap.as(org.jemmy.interfaces.TreeItem.class).expand();
                }
            }
        }

        treeAsSelectable.selector().select(treeAsSelectable.getStates().get(0));
        final Wrap<? extends TreeItem> item = treeAsParent.lookup(new LookupCriteria<TreeItem>() {
            @Override
            public boolean check(TreeItem cntrl) {
                return treeAsSelectable.getStates().get(0).equals(cntrl);
            }
        }).wrap();

        assertEquals(new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult((String) item.getControl().getValue());
            }
        }.dispatch(tree.getEnvironment()), "Root");
    }
}