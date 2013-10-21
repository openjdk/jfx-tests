/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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

import javafx.scene.control.TreeItem;
import org.jemmy.fx.SceneDock;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.EqualsLookup;
import org.jemmy.resources.StringComparePolicy;
import org.junit.*;

/**
 * @author shura
 */
public class TreeItemTest {

    private TreeViewDock tree;
//    private LabeledDock selection;


    @BeforeClass
    public static void setUpClass() throws Exception {
        TreeTest.setUpClass();
    }

    @Before
    public void setUp() {
        tree = new TreeViewDock(new SceneDock().asParent());
//        selection = new LabeledDock(new SceneDock().asParent(), "selection");
    }

    @Test
    public void byCriteria() {
        TreeItemDock ti00 = new TreeItemDock(tree.asItemParent(), new EqualsLookup<Object>("00"));
        ti00.asTreeItem().expand();
        new TreeItemDock(tree.asItemParent(), new EqualsLookup<Object>("001")).asEditableCell().select();
    }

    @Test
    public void byObject() {
        TreeItemDock ti01 = new TreeItemDock(tree.asItemParent(), "01");
        ti01.asTreeItem().collapse();
        new TreeItemDock(
                ti01.asItemParent(), String.class, "012").asEditableCell().select();
    }

    @Test
    public void byToString() {
        new TreeItemDock(
                new TreeItemDock(tree.asItemParent(), "02").asItemParent(),
                "23", StringComparePolicy.SUBSTRING).asEditableCell().select();
    }

    @Test
    public void byToStringPath() {
        new TreeItemDock(tree.asItemParent(), StringComparePolicy.EXACT, "0", "02", "023").asEditableCell().select();
    }

    @Test
    public void byPath() {
        new TreeItemDock(tree.asItemParent(), "0", "02", "024").asEditableCell().select();
    }

    @Test
    public void byCriteriaPath() {
        new TreeItemDock(tree.asItemParent(),
                new EqualsLookup<Object>("0"),
                new EqualsLookup<Object>("02"),
                new EqualsLookup<Object>("025")).asEditableCell().select();
    }
    //@Test//https://javafx-jira.kenai.com/browse/RT-30658
    public void autoExpand() {
        new TreeItemDock(tree.asItemParent(), "00").asTreeItem().collapse();
        new TreeItemDock(tree.asItemParent(), "01").asTreeItem().collapse();
        new TreeItemDock(tree.asItemParent(), "029").asTreeItem().collapse();
        new TreeItemDock(tree.asItemParent(), "02").asTreeItem().collapse();
        new TreeItemDock(tree.asItemParent(), "0").asTreeItem().collapse();
        new TreeItemDock(tree.asItemParent(), "0290").asEditableCell().select();
        new TreeItemDock(tree.asItemParent(), "0looooooooooooooooooooooooooooooooooo00000000000000000000000000000000000000oooooooooooooooooooooooooong").asEditableCell().select();
    }

    @Test
    public void lookupItem() {
        tree.wrap().as(Parent.class, TreeItem.class).lookup(TreeApp.MyTreeItem.class).wrap().mouse().click();
    }
}