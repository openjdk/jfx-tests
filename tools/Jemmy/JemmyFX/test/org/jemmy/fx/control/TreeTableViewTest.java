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

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.text.Text;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.TreeTableViewApp.Data;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Alexander Kirov
 */
public class TreeTableViewTest {

    private static SceneDock scene;
    private static TreeTableViewDock treeTableView;
    private static Wrap<? extends TreeTableView> treeTableViewWrap;
    private static Selectable<TreeItem> treeAsMultiSelectable;
    private static Parent<Object> treeAsParent;

    @BeforeClass
    public static void launch() throws InterruptedException {
        AppExecutor.executeNoBlock(TreeTableViewApp.class);
        scene = new SceneDock();
        treeTableView = new TreeTableViewDock(scene.asParent());
        treeTableViewWrap = treeTableView.wrap();
        treeAsMultiSelectable = treeTableViewWrap.as(Selectable.class, TreeItem.class);
        treeAsParent = treeTableViewWrap.as(Parent.class, TreeItem.class);
    }

    @Test(timeout = 300000)
    public void treeItemSelectorTest() {
        expandAll();

        final List<TreeItem> states = treeTableView.asTreeItemSelectable().getStates();

        assertEquals(states.size(), 40);

        treeTableView.asTreeItemSelectable().selector().select(states.get(1));
        treeTableView.asTreeItemSelectable().selector().select(states.get(5));
        treeTableView.asTreeItemSelectable().selector().select(states.get(38));

        assertEquals(treeTableView.getSelectedIndices().size(), 1);
        assertEquals(treeTableView.getSelectedIndices().get(0), 38);
    }

    @Test(timeout = 300000)
    public void tableImplementationTest() {
        expandAll();//https://javafx-jira.kenai.com/browse/RT-31123
        //Supposing, after expansion, we have 40 lines with data.
        List<Point> points = java.util.Arrays.asList(new Point[]{new Point(1, 3), new Point(0, 39), new Point(0, 2), new Point(1, 38)});
        treeTableView.asTable().select(new Point(1, 3), new Point(0, 39), new Point(0, 2), new Point(1, 38));
        assertTrue(treeTableView.getSelectedCells().equals(points));

        assertTrue(treeTableView.asTable(String.class).select(new Point(1, 1)).get(0) instanceof TreeTableCellWrap);
        assertTrue(treeTableView.asTable(String.class).select(new Point(0, 0)).get(0).getControl().equals("Root"));
    }

    @Test(timeout = 300000)
    public void treeImplementationTest() {
        //scroll to the top.
        treeTableView.vto(0.0);
        //We expect this code to select the fourth line in the treeTableView, index == 3.
        treeTableView.asTree(Data.class).selector().select(new OfMainData("data-0-0"), new OfMainData("data-1-0"), new OfMainData("data-2-0"));
        assertTrue(treeTableView.getSelectedIndices().contains(3));

        expandAll();
        //Now, with need of scrolling down.
        treeTableView.asTree(Data.class).selector().select(new OfMainData("data-0-2"), new OfMainData("data-1-2"), new OfMainData("data-2-2"));
        assertTrue(treeTableView.getSelectedIndices().contains(39));
    }

    @Test(timeout = 300000)
    public void sanityTest() {
        assertTrue(treeTableView.control() instanceof TreeTableView);
        assertTrue(treeTableView.getShowRoot());
        assertTrue(treeTableView.getColumns().get(0) instanceof TreeTableColumn);
        assertTrue(treeTableView.getItems().size() > 0);
        assertTrue(treeTableView.getRootItem().getParent() == null);
        assertTrue(treeTableView.wrap() instanceof TreeTableViewWrap);
    }

    @Test(timeout = 300000)
    public void parentOfNodeTest() {
        Parent<Node> as = treeTableView.wrap().as(Parent.class, Node.class);
        assertEquals(getFirstText(as), "Main data");
        as = treeTableView.asParent();
        assertEquals(getFirstText(as), "Main data");
    }

    @Test(timeout = 30000)
    public void expansionTest() {
        Lookup<TreeItem> lookup = expandRootItems();
        assertEquals(4, lookup.size());
    }

    @Test(timeout = 300000)
    public void asteriskExpansionTest() throws InterruptedException {
        final TreeItem root = getRoot();
        treeAsMultiSelectable.selector().select(root);
        treeTableViewWrap.waitState(new State<Integer>() {
            @Override
            public Integer reached() {
                return new GetAction<Integer>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(treeTableViewWrap.getControl().getSelectionModel().getSelectedItems().size());
                    }
                }.dispatch(treeTableViewWrap.getEnvironment());
            }
        }, 1);
        treeTableViewWrap.keyboard().pushKey(KeyboardButtons.MULTIPLY);
        treeTableViewWrap.waitState(new State() {
            @Override
            public Object reached() {
                return isExpanded(root) ? true : null;
            }
        });
    }

    private String getFirstText(Parent<Node> as) {
        final Wrap<? extends Text> wrap = as.lookup(Text.class).wrap();
        return new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(wrap.getControl().getText());
            }
        }.dispatch(treeTableView.wrap().getEnvironment());
    }

    private TreeItem getRoot() {
        return new GetAction<TreeItem>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(treeTableViewWrap.getControl().getRoot());
            }
        }.dispatch(treeTableViewWrap.getEnvironment());
    }

    private void expandAll() {
        final Lookup<TreeItem> lookup = treeTableView.wrap().as(Parent.class, TreeItem.class).lookup();
        boolean all_expanded = false;
        while (!all_expanded) {
            all_expanded = true;
            for (int i = 0; i < lookup.size(); i++) {
                final TreeTableNodeWrap<? extends javafx.scene.control.TreeItem> wrap = (TreeTableNodeWrap<? extends javafx.scene.control.TreeItem>) lookup.wrap(i);
                if (!wrap.isExpanded() && !wrap.isLeaf()) {
                    all_expanded = false;
                    wrap.as(org.jemmy.interfaces.TreeItem.class).expand();
                }
            }
        }
    }

    private boolean isExpanded(TreeItem item) {
        for (TreeItem child : (List<TreeItem>) item.getChildren()) {
            if ((!child.isLeaf() && !child.isExpanded()) || !isExpanded(child)) {
                return false;
            }
        }
        return true;
    }

    protected Lookup expandRootItems() {
        Lookup<TreeItem> lookup = treeAsParent.lookup(TreeItem.class, new LookupCriteria<TreeItem>() {
            @Override
            public boolean check(TreeItem item) {
                TreeItem root = getRoot();
                return root == item || root == item.getParent();
            }
        });
        lookup.wrap().as(org.jemmy.interfaces.TreeItem.class).expand();
        return lookup;
    }

    private class OfMainData implements LookupCriteria<Data> {

        private String mainData;

        public OfMainData(String mainData) {
            this.mainData = mainData;
        }

        @Override
        public boolean check(Data cntrl) {
            return cntrl.mainData.getValue().equals(mainData);
        }
    }
}