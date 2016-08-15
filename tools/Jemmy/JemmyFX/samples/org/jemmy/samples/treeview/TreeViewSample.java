/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
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
package org.jemmy.samples.treeview;

import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.TextFieldCellEditor;
import org.jemmy.fx.control.TreeItemDock;
import org.jemmy.fx.control.TreeViewDock;
import org.jemmy.lookup.LookupCriteria;
import static org.jemmy.resources.StringComparePolicy.*;
import org.jemmy.samples.SampleBase;
import org.jemmy.samples.treeview.TreeViewApp.Data;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Alexander Kirov
 */
public class TreeViewSample extends SampleBase {

    private static SceneDock scene;
    private static TreeViewDock treeView;

    @BeforeClass
    public static void launch() throws InterruptedException {
        startApp(TreeViewApp.class);
        scene = new SceneDock();

        // find by id
        treeView = new TreeViewDock(scene.asParent(), TreeViewApp.TREE_VIEW_ID);
        treeView.asItemParent().setEditor(new TextFieldCellEditor<Object>());
    }

    @Test
    /**
     * How to find an item in a tree. The tree itself has no specific lookup attributes -
     * please check the LookupSample for generic lookup functionality.
     */
    public void itemLookup() {
        //you could find an item by content of the path elements.(comparison is done by equals(Object))
        new TreeItemDock(treeView.asItemParent(), new Data("0"), new Data("0-1")).mouse().click();
        //you could use strings (toString() is compared to the reference data)
        new TreeItemDock(treeView.asItemParent(), EXACT, "0", "0-1", "0-1-2", "0-1-2-3").mouse().click();
        //you could also find an item by simply its content (no path)
        new TreeItemDock(treeView.asItemParent(), new Data("0-3-2-1")).mouse().click();
        //or find a second node by substring
        new TreeItemDock(treeView.asItemParent(), 2, "-3-2-", SUBSTRING).mouse().click();
        //or a 5th by a custom criteria
        new TreeItemDock(treeView.asItemParent(), Data.class, 4, new LookupCriteria<Data>() {

            @Override
            public boolean check(Data cntrl) {
                return cntrl.getValue().length() == 5;
            }
        }).mouse().click();
    }

    /**
     * How to scroll
     */
    @Test
    public void scrolling() {
        //scrolling to an item is done automatically.
        //should you need to scroll explicitely, just call show():
        TreeItemDock item = new TreeItemDock(treeView.asItemParent(), EXACT, "0", "0-3", "0-3-0", "0-3-0-1");
        item.shower().show();
        item.mouse().move();
        //or scroll explicitely
        treeView.asScrollable2D().asVerticalScroll().to(.3);
    }

    /**
     * How to use as a tree
     */
    @Test
    public void expandAndCollapse() {
        //collapse
        new TreeItemDock(treeView.asItemParent(), "0-2-0", EXACT).collapse();
        new TreeItemDock(treeView.asItemParent(), "0-2", EXACT).collapse();
        new TreeItemDock(treeView.asItemParent(), "0-3-3", EXACT).collapse();
        //expand happens automatically
        new TreeItemDock(treeView.asItemParent(), "0-2-0-2", EXACT).mouse().click();
        //but there's, of course, an expand:
        new TreeItemDock(treeView.asItemParent(), "0-3-3", EXACT).expand();
    }

    /**
     * How to edit
     */
    //@Test//https://javafx-jira.kenai.com/browse/RT-31165
    public void edit() {
        //make sure you have set up an editor (see launch())
        //and then
        new TreeItemDock(treeView.asItemParent(), "0-0-0-1", EXACT).asEditableCell().edit("new value");
    }
}
