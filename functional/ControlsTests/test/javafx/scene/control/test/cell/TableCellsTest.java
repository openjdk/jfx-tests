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
import com.sun.javafx.scene.control.skin.LabeledText;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.test.cellapps.CellsApp.CellType;
import javafx.scene.control.test.cellapps.CellsApp.DataItem;
import javafx.scene.control.test.cellapps.TableCellsApp;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

@RunWith(FilteredTestRunner.class)
public class TableCellsTest extends TreeTableCellsTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        TableCellsApp.main(null);
    }

    @Before
    @Override
    public void tableSetUpClass() throws Exception {
        updateWraps();
        testedControl = parent.lookup(TableView.class, new ByID<TableView>(TableCellsApp.TABLE_VIEW_ID)).wrap();
        cellID = TableCellsApp.TABLE_EDIT_ID;
        choiceID = TableCellsApp.TABLE_FACTORY_CHOICE_ID;
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

    /**
     * Content of tableView can be stored
     */
    @Smoke
    @Test(timeout = 300000)
    public void MapCellFactoryTest() {
        doFactoryChange(CellType.MapValue);
        testedControl = parent.lookup(TableView.class, new ByID<TableView>(TableCellsApp.TABLE_VIEW_ID)).wrap();
        Lookup lookup = testedControl.as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
            public boolean check(Node cntrl) {
                if ((cntrl instanceof LabeledText) && (((LabeledText) cntrl).getText().equals(TableCellsApp.ContentOfMaps.get(0)))) {
                    return true;
                }
                return false;
            }
        });
        lookup.wrap(0).mouse().click();
        lookup.wrap(0).mouse().click();
        Lookup lookupOfTextField = testedControl.as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
            public boolean check(Node cntrl) {
                if (cntrl instanceof TextField) {
                    return true;
                }
                return false;
            }
        });
        Wrap<? extends TextField> wrap = ((Wrap<? extends TextField>) lookupOfTextField.wrap(0));
        wrap.keyboard().pushKey(Keyboard.KeyboardButtons.HOME);
        wrap.keyboard().pushKey(Keyboard.KeyboardButtons.END, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        wrap.keyboard().pushKey(Keyboard.KeyboardButtons.DELETE);

        final String newValue = "NewValue";
        wrap.as(Text.class).type(newValue);
        wrap.keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);
        new Waiter(new Timeout("", 2000)).ensureState(new State() {
            public Object reached() {
                if (new GetAction<String>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(((Map<String, String>) ((TableView) testedControl.getControl()).getItems().get(0)).get(TableCellsApp.Column1MapKey));
                    }
                }.dispatch(Root.ROOT.getEnvironment()).equals(newValue)) {
                    return true;
                } else {
                    return null;
                }
            }
        });

        new Waiter(new Timeout("", 2000)).ensureState(new State() {
            public Object reached() {
                final Wrap<? extends TableCell> tableCell = javafx.scene.control.test.tableview.TestBaseCommon.getCellWrap(testedControl, 0, 0);
                if (new GetAction<String>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        setResult((String) tableCell.getControl().getItem());
                    }
                }.dispatch(Root.ROOT.getEnvironment()).equals(newValue)) {
                    return true;
                } else {
                    return null;
                }
            }
        });
    }

    @Override
    protected ObservableList<DataItem> getCurrentProgramValues() {
        return new GetAction<ObservableList<DataItem>>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(FXCollections.observableArrayList(((TableView) testedControl.getControl()).getItems()));
            }
        }.dispatch(testedControl.getEnvironment());
    }
}