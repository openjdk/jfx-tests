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
package javafx.scene.control.test.treeview;

import java.util.HashSet;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.control.test.util.MultipleSelectionHelper.Range;
import javafx.scene.control.test.util.TableListCommonTests;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Alexander Kirov
 */
public class TreeViewSelectionTest extends TableListCommonTests {

    protected static Wrap<? extends Scene> scene;

    @BeforeClass
    public static void setUpClass() throws Exception {
        TreeViewNewApp.main(null);
    }

    @Before
    public void setUp() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        testedControl = (Wrap<? extends TreeView>) parent.lookup(TreeView.class, new ByID<TreeView>(TreeViewNewApp.TESTED_TREEVIEW_ID)).wrap();
        setContentSize(1, 21);
        selectionHelper.setPageWidth(1);
        selectionHelper.setPageHeight(9);//9 for windows
        Environment.getEnvironment().setTimeout("wait.state", 2000);
        Environment.getEnvironment().setTimeout("wait.control", 1000);
        scene.mouse().move(new Point(0, 0));
    }

    @Override
    protected void checkSelection() {
        TreeViewCommonFunctionality.checkSelection(testedControl, selectionHelper, DATA_ITEMS_NUM);
    }

    @Override
    protected Point getSelectedItem() {
        return TreeViewCommonFunctionality.getSelectedItem(testedControl);
    }

    @Override
    protected HashSet<Point> getSelected() {
        return TreeViewCommonFunctionality.getSelected(testedControl);
    }

    @Override
    protected void scrollTo(int inXCoord, int inYCoord) {
        TreeViewCommonFunctionality.scrollTo(testedControl, inXCoord, inYCoord);
    }

    @Override
    protected void switchOnMultiple() {
        TreeViewCommonFunctionality.switchOnMultiple();
    }

    @Override
    protected void adjustControl() {
        TreeViewCommonFunctionality.adjustControl(DATA_ITEMS_NUM);
    }

    @Override
    protected void clickOnFirstCell() {
        TreeViewCommonFunctionality.clickOnFirstCell(testedControl);
    }

    @Override
    protected void setOrientation(Orientation orientation) {
        throw new UnsupportedOperationException("Not supported for TreeView.");
    }

    @Override
    protected Wrap getCellWrap(int column, int row) {
        return TreeViewCommonFunctionality.getCellWrap(testedControl, column, row);
    }

    @Override
    protected Range getVisibleRange() {
        return TreeViewCommonFunctionality.getVisibleRange(testedControl);
    }
}