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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.test.cellapps.CellsApp;
import javafx.scene.control.test.cellapps.CellsApp.CellType;
import javafx.scene.control.test.cellapps.CellsApp.DataItem;
import javafx.scene.control.test.cellapps.TableCellsApp;
import static javafx.scene.control.test.cellapps.TableCellsApp.*;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Table;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Alexander Kirov
 */
public class TreeTableCellsTest extends CellsTestBase {

    @BeforeClass
    public static void setUpClass() throws Exception {
        TableCellsApp.main(null);
    }

    @Before
    public void tableSetUpClass() throws Exception {
        updateWraps();
        testedControl = parent.lookup(TreeTableView.class, new ByID<TreeTableView>(TableCellsApp.TREE_TABLE_VIEW_ID)).wrap();
        cellID = TableCellsApp.TREE_TABLE_EDIT_ID;
        choiceID = TableCellsApp.TREE_TABLE_FACTORY_CHOICE_ID;
    }

    @Test(timeout = 30000)
    public void progressBarCellFactoryTest() {
        doFactoryChange(CellType.ProgressBar);
        final Lookup lookup = testedControl.as(Parent.class, Node.class).lookup(new LookupCriteria() {
            public boolean check(Object cntrl) {
                if (cntrl instanceof ProgressBar) {
                    //Some cells are created for caching purposes, ignore them.
                    if (!((Control) cntrl).getParent().getParent().getParent().isVisible()
                            || !((Control) cntrl).getParent().getParent().isVisible()
                            || !((Control) cntrl).getParent().isVisible()) {
                        return false;
                    }
                    return true;
                }
                return false;
            }
        });
        Wrap<? extends ProgressBar> progressBar = lookup.wrap();
        assertEquals("Initial value is incorrect", getProgressBarValue(progressBar), 0.5, 0.0001);
        changeProgressValues(0.75);
        assertEquals("Initial value is incorrect", getProgressBarValue(progressBar), 0.75, 0.0001);
        assertEquals("Amount of progress bars", lookup.size(), DATA_ITEMS_SIZE);
    }

    @Override
    protected Wrap select(final String data) {
        Wrap wrap = testedControl.as(Table.class, DataItem.class).lookup(new LookupCriteria<DataItem>() {
            public boolean check(DataItem tableData) {
                return tableData.toString().contains(data);
            }
        }).wrap();
        wrap.mouse().click();
        return wrap;
    }

    @Override
    protected ObservableList<DataItem> getCurrentProgramValues() {
        return new GetAction<ObservableList<DataItem>>() {
            @Override
            public void run(Object... os) throws Exception {
                final ObservableList<TreeItem<DataItem>> observableArrayList = FXCollections.observableArrayList(((TreeTableView) testedControl.getControl()).getRoot().getChildren());
                ObservableList<DataItem> dataItems = FXCollections.observableArrayList();
                for (TreeItem<DataItem> treeItem : observableArrayList) {
                    dataItems.add(treeItem.getValue());
                }
                setResult(dataItems);
            }
        }.dispatch(testedControl.getEnvironment());
    }

    private double getProgressBarValue(final Wrap<? extends ProgressBar> progressBarWrap) {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(progressBarWrap.getControl().getProgress());
            }
        }.dispatch(progressBarWrap.getEnvironment());
    }

    private void changeProgressValues(final double newValue) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                CellsApp.DataItem.progressBarValue.setValue(newValue);
            }
        }.dispatch(testedControl.getEnvironment());
    }
}