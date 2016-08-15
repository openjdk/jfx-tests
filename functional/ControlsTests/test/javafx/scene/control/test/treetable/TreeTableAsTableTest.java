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

import client.test.Smoke;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.commons.SortValidator;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.test.tableview.TableViewTest;
import javafx.scene.control.test.tableview.TestBase;
import javafx.util.StringConverter;
import org.jemmy.action.GetAction;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import static org.junit.Assert.*;
import org.junit.Test;
import static javafx.scene.control.test.treetable.TreeTableAsOldTableApp.*;

/**
 * @author Alexander Kirov
 */
public class TreeTableAsTableTest extends TableViewTest {

    static {
        isTableTests = false;
        TestBase.isTableTests = false;
    }

    /**
     * Checks that when the sorting is applied to the underlying data collection
     * the cells are rendered in the right order.
     */
    @Smoke
    @Test(timeout = 30000)
    @Override
    public void renderingAfterSortingTest() throws Throwable {
        final int ITEMS_COUNT = 7;
        final int COL_IDX = 0;

        StringConverter<TreeItem<Data>> conv = new StringConverter<TreeItem<Data>>() {
            @Override
            public String toString(TreeItem<Data> t) {
                return String.format("item %s field 0", t.getValue().getName());
            }

            @Override
            public TreeItem<Data> fromString(String s) {
                return new TreeItem<Data>(new Data(s, DATA_FIELDS_NUM, 0));
            }
        };

        final Comparator<TreeItem<Data>> comp = new Comparator<TreeItem<Data>>() {
            public int compare(TreeItem<Data> t1, TreeItem<Data> t2) {
                return t1.getValue().getName().compareTo(t2.getValue().getName());
            }
        };

        SortValidator<TreeItem<Data>, TreeTableCell> validator = new SortValidator<TreeItem<Data>, TreeTableCell>(ITEMS_COUNT, conv, comp) {
            @Override
            protected void setControlData(final ObservableList<TreeItem<Data>> ls) {
                new GetAction<Object>() {
                    @Override
                    public void run(Object... parameters) throws Exception {
                        TreeTableView tv = (TreeTableView) testedControl.getControl();
                        tv.getRoot().getChildren().setAll(ls);
                    }
                }.dispatch(testedControl.getEnvironment());
            }

            @Override
            protected Lookup<? extends TreeTableCell> getCellsLookup() {
                return testedControl.as(Parent.class, Node.class).lookup(TreeTableCell.class, new LookupCriteria<TreeTableCell>() {

                    final String rootName = "item fictive field 0 loooooooooong";

                    public boolean check(TreeTableCell cell) {
                        TreeTableColumn col = cell.getTableColumn();
                        return COL_IDX == cell.getTreeTableView().getColumns().indexOf(col)
                               && cell.isVisible() && !rootName.equals(cell.getText());
                    }
                });
            }

            @Override
            protected String getTextFromCell(TreeTableCell cell) {
                return cell.getText();
            }

            @Override
            protected void sort() {
                new GetAction<Object>() {
                    @Override
                    public void run(Object... parameters) throws Exception {
                        TreeTableView tv = (TreeTableView) testedControl.getControl();
                        FXCollections.sort(tv.getRoot().getChildren(), comp);
                    }
                }.dispatch(testedControl.getEnvironment());
            }
        };

        boolean result = validator.check();
        String msg = validator.getFailureReason();
        assertTrue(msg, result);
    }


    /**
     * RT-38739 create test for RT-38172
     * RT-38172: ClassCastException after TreeTableColumn setText
     */
    @Test(timeout = 300000)
    public void testSetTextException() {
        AtomicBoolean exceptionThrown = new AtomicBoolean(false);
        Thread.UncaughtExceptionHandler h = (Thread th, Throwable ex) -> {
            exceptionThrown.set(exceptionThrown.get() | (ex instanceof ClassCastException));
        };
        Thread.UncaughtExceptionHandler prevHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(h);
        String originalText = getColumnText(0);
        setColumnTextSync(0, "Whatever");
        setColumnTextSync(0, "Nevermind");
        setColumnTextSync(0, originalText);
        Thread.setDefaultUncaughtExceptionHandler(prevHandler);
        assertFalse("There should be no exception during setText", exceptionThrown.get());
    }
}
