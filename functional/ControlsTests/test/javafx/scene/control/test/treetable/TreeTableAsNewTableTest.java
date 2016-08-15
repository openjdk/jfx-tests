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
package javafx.scene.control.test.treetable;

import static javafx.commons.Consts.StyleClass.*;
import javafx.scene.Node;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.test.tableview.TableViewNewTest;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.scene.text.Text;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.ByText;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.Test;

/**
 * @author Alexander Kirov
 */
public class TreeTableAsNewTableTest extends TableViewNewTest {

    static {
        isTableTests = false;
    }

    /**
     * Tests that tree column property works according to the spec. see
     * <a href="http://javafx-jira.kenai.com/browse/RT-26505">RT-26505</a>
     */
    @Test(timeout = 300000)
    public void treeColumnPropertyTest() throws InterruptedException {
        setSize(250, 250);
        final int COLS = 2;
        final int ROWS = 5;
        for (int i = 0; i < COLS; i++) {
            addColumn("items" + String.valueOf(i), i, true);
            switchToPropertiesTab("items" + String.valueOf(i));
            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 100.0);
        }
        setNewDataSize(ROWS);

        //Check that disclosure node is in the first column
        checkRectanglesRelation(getParentWrap(getRootAsWrap("items0-0")).as(Parent.class, Node.class).lookup(new ByStyleClass(TREE_DISCLOSURE_NODE)).wrap().getScreenBounds(),
                RectanglesRelations.ISCONTAINED,
                getCellWrap(0, 0).getScreenBounds());

        switchToPropertiesTab("ROOT");
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);

        checkTextAlignment(ROWS, 0, 1);

        //Change property value
        new GetAction<Object>() {
            @Override
            public void run(Object... parameters) throws Exception {
                TreeTableView treeTable = (TreeTableView) testedControl.getControl();
                treeTable.setTreeColumn((TreeTableColumn) treeTable.getColumns().get(treeTable.getColumns().size() - 1));
            }
        }.dispatch(testedControl.getEnvironment());

        //Check that disclosure node is in the second column
        checkRectanglesRelation(getParentWrap(getRootAsWrap("items1-0")).as(Parent.class, Node.class).lookup(new ByStyleClass(TREE_DISCLOSURE_NODE)).wrap().getScreenBounds(),
                RectanglesRelations.ISCONTAINED,
                getCellWrap(1, 0).getScreenBounds());

        checkTextAlignment(ROWS, 1, 0);
    }

    private Parent getRootAsParent(final String rootText) {
        return getRootAsWrap(rootText).as(Parent.class, Node.class);
    }

    private Wrap<? extends Node> getRootAsWrap(final String rootText) {
        return parent.lookup(IndexedCell.class, new LookupCriteria<IndexedCell>() {
            public boolean check(IndexedCell cell) {
//                System.out.println("rootText = " + rootText);
//                System.out.println("cell.getText() = " + cell.getText());
//                System.out.println("cell.getStyleClass() = " + cell.getStyleClass());
//                System.out.println("cell.getStyleClass().contains(TREE_TABLE_ROW_CELL) = " + cell.getStyleClass().contains(TREE_TABLE_CELL));
                return rootText.equals(cell.getText()) && cell.getStyleClass().contains(TREE_TABLE_CELL);
            }
        }).wrap();
    }

    /*
     * Checks 'text' nodes alignment after changing tree table 'treeColumn' property.
     * @param treeColIdx - index of the column which is set as a 'treeColumn' in tree table.
     * @param listColIdx - index of the colum which was 'treeColumn' before.
     */
    private void checkTextAlignment(final int ROWS, int treeColIdx, int listColIdx) {
        final Timeout timeout = new Timeout("", 1000L);
        final Rectangle rootTreeText = getRootAsParent("items" + treeColIdx + "-0").lookup(Text.class, new ByText("items" + treeColIdx + "-0")).wrap().getScreenBounds();
        for (int i = 1; i < ROWS; i++) {
            final Rectangle cellText = parent.lookup(Text.class, new ByText("items" + treeColIdx + "-" + i)).wrap().getScreenBounds();
            new Waiter(timeout).ensureValue(Boolean.TRUE, new State<Boolean>() {
                public Boolean reached() {
                    return Boolean.valueOf(rootTreeText.x < cellText.x);
                }

                @Override
                public String toString() {
                    return "[Tree column text alignment]";
                }
            });
        }

        final Rectangle topText = getRootAsParent("items" + listColIdx + "-0").lookup(Text.class, new ByText("items" + listColIdx + "-0")).wrap().getScreenBounds();
        for (int i = 1; i < ROWS; i++) {
            final Rectangle cellText = parent.lookup(Text.class, new ByText("items" + listColIdx + "-" + i)).wrap().getScreenBounds();
            new Waiter(timeout).ensureValue(Boolean.TRUE, new State<Boolean>() {
                public Boolean reached() {
                    return Boolean.valueOf(topText.x == cellText.x);
                }

                @Override
                public String toString() {
                    return "[Simple column text alignment]";
                }
            });
        }
    }
}
