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
package javafx.scene.control.test.tableview;

import java.util.HashSet;
import static javafx.commons.Consts.*;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.test.treetable.TreeTableAsOldTableApp;
import javafx.scene.control.test.util.MultipleSelectionHelper.Range;
import javafx.scene.control.test.util.TableListCommonTests;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Alexander Kirov
 */
public class TableViewSelectionTest extends TableListCommonTests {

    protected static Parent<Node> sceneParent;
    protected static Wrap<? extends Scene> sceneWrap;
    protected static Wrap<? extends TableView> tableViewWrap;
    protected static Wrap<? extends CheckBox> multipleSelectionWrap;
    protected static boolean isTableTests = true;

    @BeforeClass
    public static void setUpClass() throws Exception {
        if (isTableTests) {
            System.out.println("Starting an old TableView application with simple controllers.");
            TableViewApp.main(null);
        } else {
            System.out.println("Starting a new TreeTable application created as an old TableView application.");
            TreeTableAsOldTableApp.main(null);
        }
        setContentSize(TableViewApp.DATA_FIELDS_NUM, TableViewApp.DATA_ITEMS_NUM);
    }

    @Before
    public void setUp() throws Exception {
        sceneWrap = Root.ROOT.lookup().wrap();
        sceneParent = sceneWrap.as(Parent.class, Node.class);
        if (isTableTests) {
            testedControl = sceneParent.lookup(TableView.class).wrap();
        } else {
            testedControl = sceneParent.lookup(TreeTableView.class).wrap();
        }
        multipleSelectionWrap = sceneParent.lookup(CheckBox.class, new ByID<CheckBox>(ENABLE_MULTIPLE_SELECTION_ID)).wrap();
    }

    @Override
    protected void scrollTo(int inXCoord, int inYCoord) {
        TestBaseCommon.scrollTo(testedControl, inXCoord, inYCoord);
    }

    @Override
    protected void switchOnMultiple() {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                multipleSelectionWrap.getControl().setSelected(true);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    protected void adjustControl() {
    }

    @Override
    protected void clickOnFirstCell() {
        TestBaseCommon.clickOnFirstCell(testedControl);
    }

    @Override
    protected void setOrientation(Orientation orientation) {
        throw new UnsupportedOperationException("Not supported for TableView.");
    }

    @Override
    protected Wrap getCellWrap(int column, int row) {
        return TestBaseCommon.getCellWrap(testedControl, column, row);
    }

    @Override
    protected Range getVisibleRange() {
        return TestBaseCommon.getVisibleRange(testedControl);
    }

    @Override
    protected void checkSelection() {
        TestBaseCommon.checkSelection(testedControl, selectionHelper);
    }

    @Override
    protected HashSet<Point> getSelected() {
        return TestBaseCommon.getSelected(testedControl);
    }

    @Override
    protected Point getSelectedItem() {
        return TestBaseCommon.getSelectedItem(testedControl);
    }

    @Override
    public void keyboardShiftSequentialMultipleSelectionTest() {

    }
}