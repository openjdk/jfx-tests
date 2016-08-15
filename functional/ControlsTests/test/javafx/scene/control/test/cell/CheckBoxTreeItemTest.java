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

package javafx.scene.control.test.cell;

import client.test.Smoke;
import java.util.Arrays;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.test.mix.TreeViewTest;
import javafx.scene.control.test.util.BooleanPropertyChanger;
import javafx.scene.control.test.util.PropertyHelper;
import javafx.scene.control.test.cellapps.CheckBoxTreeItemApp;
import javafx.scene.control.test.utils.PropertyCheckingGrid;
import javafx.scene.control.test.util.PropertyGridHelper;
import javafx.scene.control.test.util.UtilTestFunctions;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

@RunWith(FilteredTestRunner.class)
public class CheckBoxTreeItemTest extends UtilTestFunctions {

    static protected enum Properties {

        independent, indeterminate, selected
    };
    static Wrap<? extends TreeView> tree;
    static Parent<TreeItem> treeAsParent;
    static Wrap<? extends Scene> scene;
    static Selectable<TreeItem> treeAsSelectable;
    PropertyGridHelper<CheckBoxTreeItem> propertyTableHelper;

    @BeforeClass
    public static void setUpClass() throws Exception {
        CheckBoxTreeItemApp.main(null);
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        tree = (Wrap<? extends TreeView>) parent.lookup(TreeView.class).wrap();
        treeAsParent = tree.as(Parent.class, TreeItem.class);
        treeAsSelectable = tree.as(Selectable.class, TreeItem.class);

        PropertyGridHelper.addChanger(CheckBoxTreeItem.class, "selected", new BooleanPropertyChanger(true, Boolean.FALSE, Arrays.asList(Boolean.TRUE, Boolean.FALSE)) {
            public void changeByUI(Wrap<?> obj, Boolean value) {
                Wrap<? extends CheckBox> check = obj.as(Parent.class, Node.class).lookup(CheckBox.class).wrap();
                check.as(Selectable.class, Boolean.class).selector().select(value);
            }
        });
        PropertyGridHelper.addChanger(CheckBoxTreeItem.class, "independent", new BooleanPropertyChanger(false, Boolean.FALSE, Arrays.asList(Boolean.TRUE, Boolean.FALSE)) {
            public void changeByUI(Wrap<?> obj, Boolean value) {
                throw new UnsupportedOperationException("Not supported.");
            }
        });
        PropertyGridHelper.addChanger(CheckBoxTreeItem.class, "indeterminate", new BooleanPropertyChanger(false, Boolean.FALSE, Arrays.asList(Boolean.TRUE, Boolean.FALSE)) {
            public void changeByUI(Wrap<?> obj, Boolean value) {
                throw new UnsupportedOperationException("Not supported.");
            }
        });
    }

    @Before
    public void setUp() {
        parent.lookup(Button.class, new ByID<Button>(CheckBoxTreeItemApp.RESET_SCENE_BTN_ID)).wrap().mouse().click();
        new TreeViewTest().expand(treeAsParent.lookup());

        treeAsSelectable.selector().select(treeAsSelectable.getStates().get(0));
        Wrap<? extends TreeItem> item = treeAsParent.lookup(new LookupCriteria<TreeItem>() {
            public boolean check(TreeItem cntrl) {
                return treeAsSelectable.getStates().get(0).equals(cntrl);
            }
        }).wrap();
        propertyTableHelper = new PropertyGridHelper<CheckBoxTreeItem>((Wrap<? extends CheckBoxTreeItem>) item, parent.lookup(PropertyCheckingGrid.class).wrap());
    }

    @Smoke
    @Test
    public void selectedPropertyTest() throws Throwable {
        PropertyHelper selectedHelper = propertyTableHelper.getPropertyHelper(Boolean.class, "selected");
        selectedHelper.checkProperty(true);
    }

    @Smoke
    @Test
    public void indeterminatePropertyTest() throws Throwable {
        PropertyHelper indeterminateHelper = propertyTableHelper.getPropertyHelper(Boolean.class, "indeterminate");
        indeterminateHelper.checkProperty(false);
    }

    @Smoke
    @Test
    public void independentPropertyTest() throws Throwable {
        PropertyHelper independentHelper = propertyTableHelper.getPropertyHelper(Boolean.class, "independent");
        independentHelper.checkProperty(false);

        PropertyHelper selectedHelper = propertyTableHelper.getPropertyHelper(Boolean.class, "selected");
        selectedHelper.setValue(Boolean.TRUE);
        selectionChecker(Boolean.TRUE);
        selectedHelper.setValue(Boolean.FALSE);
        selectionChecker(Boolean.FALSE);

        independentHelper.setValue(Boolean.TRUE);
        selectedHelper.setValue(Boolean.TRUE);
        selectionChecker(Boolean.FALSE);

        independentHelper.setValue(Boolean.FALSE);
        checkChildDependency(true);
        checkChildDependency(false);
    }

    protected void checkChildDependency(boolean select) {
        Lookup<CheckBoxTreeItem> lookup = levelLookup(CheckBoxTreeItemApp.DATA_ITEMS_DEPTH);
        for (int i = 0; i < lookup.size(); i++) {
            Wrap<? extends CheckBox> check = lookup.wrap(i).as(Parent.class, Node.class).lookup(CheckBox.class).wrap();
            check.as(Selectable.class, Boolean.class).selector().select(select);
        }
        selectionChecker(select);
    }

    protected void selectionChecker(final Boolean selected) {
        tree.waitState(new State<Boolean>() {
            public Boolean reached() {
                final Lookup<CheckBoxTreeItem> lookup = treeAsParent.lookup(CheckBoxTreeItem.class, new LookupCriteria<CheckBoxTreeItem>() {
                    public boolean check(CheckBoxTreeItem item) {
                        return getLevel(item) > 0;
                    }
                });
                for (int i = 0; i < lookup.size(); i++) {
                    final int _i = i;
                    if (selected != new GetAction<Boolean>() {
                        @Override
                        public void run(Object... os) throws Exception {
                            setResult(lookup.get(_i).isSelected());
                        }
                    }.dispatch(tree.getEnvironment())) {
                        return Boolean.FALSE;
                    }
                }
                return Boolean.TRUE;
            }
        }, Boolean.TRUE);
    }

    protected Lookup<CheckBoxTreeItem> levelLookup(final int level) {
        return treeAsParent.lookup(CheckBoxTreeItem.class, new LookupCriteria<CheckBoxTreeItem>() {
            public boolean check(CheckBoxTreeItem item) {
                return getLevel(item) == level;
            }
        });
    }

    protected int getLevel(TreeItem item) {
        TreeItem parent = item.getParent();
        int level = 0;
        while (parent != null) {
            parent = parent.getParent();
            level++;
        }
        return level;
    }
}
