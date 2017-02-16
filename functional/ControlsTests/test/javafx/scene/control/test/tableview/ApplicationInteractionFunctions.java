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

import javafx.scene.control.skin.NestedTableColumnHeader;
import javafx.scene.control.skin.TableColumnHeader;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.commons.Consts.*;
import static javafx.commons.Consts.Cell.EDITING_CHOICEBOX_CELL_ID;
import javafx.commons.Consts.CellEditorType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableView;
import static javafx.scene.control.test.treetable.ResetButtonNames.HARD_RESET_BUTTON_ID;
import static javafx.scene.control.test.treetable.ResetButtonNames.SOFT_RESET_BUTTON_ID;
import javafx.scene.control.test.treetable.TreeTableNewApp;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.stage.PopupWindow;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Alexander Kirov
 */
public class ApplicationInteractionFunctions extends TestBaseCommon {

    protected static Wrap<? extends Scene> scene;
    protected static Wrap<? extends Control> testedControl;
    protected static boolean isTableTests = true;

    protected boolean resetHardByDefault = true;//switcher of hard and soft reset mode.
    protected boolean doNextResetHard = resetHardByDefault;

    protected static enum Properties {

        prefWidth, prefHeight, width, sortNode, comparator, sortOrder, graphic,
        resizable, showRoot, expanded, editable, value, sortable, fixedCellSize, reorderable};

    @BeforeClass
    public static void setUpClass() throws Exception {
        if (isTableTests) {
            System.out.println("Starting a TableView application with properties control.");
            NewTableViewApp.main(null);
        } else {
            System.out.println("Starting a TreeTable application with properties control.");
            TreeTableNewApp.main(null);
        }
    }

    @Before
    public void setUp() {
        initWrappers();
        Environment.getEnvironment().setTimeout("wait.state", 2000);
        Environment.getEnvironment().setTimeout("wait.control", 1000);
        scene.mouse().move(new Point(0, 0));
    }

    @After
    public void tearDown() {
        if (doNextResetHard) {
            resetSceneHard();
        } else {
            resetSceneSoft();
        }

        doNextResetHard = resetHardByDefault;
        currentSettingOption = SettingOption.PROGRAM;
    }

    protected void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        if (isTableTests) {
            testedControl = parent.lookup(TableView.class, new ByID<TableView>(NewTableViewApp.TESTED_TABLE_VIEW_ID)).wrap();
        } else {
            testedControl = parent.lookup(TreeTableView.class, new ByID<TreeTableView>(TreeTableNewApp.TESTED_TREETABLEVIEW_ID)).wrap();
        }
    }

    /**
     * Set size if tested control using bidirectional bindings.
     */
    protected void setSize(double width, double height) throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, height);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, width);
    }

    protected void setNewDataSize(int newSize) {
        setText(findTextField(NEW_DATA_SIZE_TEXTFIELD_ID), String.valueOf(newSize));
        clickButtonForTestPurpose(NEW_DATA_SIZE_BUTTON_ID);
    }

    public static void addColumn(String name, int index) {
        addColumn(name, index, false);
    }

    public static void addColumn(String name, int index, boolean createPropertiesTab) {
        setText(findTextField(NEW_COLUMN_NAME_TEXTFIELD_ID), name);
        setText(findTextField(NEW_COLUMN_INDEX_TEXTFIELD_UD), String.valueOf(index));
        setCheckBoxState(NEW_COLUMN_GET_COLUMN_PROPERTIES_TAB_ID, createPropertiesTab);
        clickButtonForTestPurpose(NEW_COLUMN_ADD_BUTTON_ID);
    }

    protected void createNestedColumn(String newColumnName, int index, int... indicesOfColumns) {
        setText(findTextField(NESTED_COLUMN_NAME_TEXT_FIELD_ID), newColumnName);
        setText(findTextField(NESTED_COLUMN_INDEX_TEXT_FIELD_ID), String.valueOf(index));
        setText(findTextField(CREATE_NESTED_COLUMN_MULTIPLE_TEXTFIELD_ID), convertMultiIndicesToString(indicesOfColumns));
        clickButtonForTestPurpose(CREATE_NESTED_COLUMN_MULTIPLE_ACTION_BUTTON_ID);
    }

    protected void removeColumns(int... indicesOfColumns) {
        setText(findTextField(REMOVE_MULTIPLE_COLUMNS_TEXT_FIELD_ID), convertMultiIndicesToString(indicesOfColumns));
        clickButtonForTestPurpose(REMOVE_MULTIPLE_COLUMNS_ACTION_BUTTON_ID);
    }

    protected void removeDataItems(int... indicesOfColumns) {
        setText(findTextField(REMOVE_DATA_ITEMS_MULTIPLE_TEXT_FIELD_ID), convertMultiIndicesToString(indicesOfColumns));
        clickButtonForTestPurpose(REMOVE_DATA_ITEMS_MULTIPLE_ACTION_BUTTON_ID);
    }

    private void resetSceneHard() {
        clickButtonForTestPurpose(HARD_RESET_BUTTON_ID);
        initWrappers();
    }

    private void resetSceneSoft() {
        clickButtonForTestPurpose(SOFT_RESET_BUTTON_ID);
        initWrappers();
    }

    protected void scrollTo(final int column, final int row) {
        scrollTo(testedControl, column, row);
    }

    protected Wrap<? extends TableCell> getCellWrap(final int column, final int row) {
        return getCellWrap(testedControl, column, row);
    }

    protected Point getResizingPointOfColumn(String columnName) {
        Rectangle rec = getTableColumnHeaderWrap(columnName).getScreenBounds();
        return new Point(rec.x + rec.width, rec.y + rec.height / 2);
    }

    protected double getScrollBarValue(final Wrap<? extends ScrollBar> scroll) {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(scroll.getControl().getValue());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected Wrap<? extends TableColumnHeader> getTableColumnHeaderWrap(final String name) {
        return testedControl.as(Parent.class, Node.class).lookup(TableColumnHeader.class, new LookupCriteria<TableColumnHeader>() {
            public boolean check(TableColumnHeader cntrl) {
                if ((cntrl.getBoundsInLocal().getMaxY() > 15) && (!(cntrl instanceof NestedTableColumnHeader))) {
                    return ((Label) (cntrl.getChildrenUnmodifiable().get(0))).getText().equals(name);
                } else {
                    return false;
                }
            }
        }).wrap();
    }

    /*
     * Clears all the content and then
     * creates COLS columns and respective numver of rows
     * with different string literals;
     * @param COLS number of columns
     */
    void createRowsForSortPurposes(final int COLS) {
        Integer colsCount = new GetAction<Integer>() {
            @Override
            public void run(Object... parameters) throws Exception {
                if (isTableTests) {
                    TableView tv = (TableView) testedControl.getControl();
                    setResult(Integer.valueOf(tv.getColumns().size()));
                } else {
                    TreeTableView ttv = (TreeTableView) testedControl.getControl();
                    setResult(Integer.valueOf(ttv.getColumns().size()));
                    ttv.setShowRoot(false);
                }
            }
        }.dispatch(testedControl.getEnvironment());

        //Remove columns if exist
        if (colsCount.intValue() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < colsCount.intValue(); i++) {
                if (0 != i) {
                    sb.append(";");
                }
                sb.append(i);
            }

            Wrap wrap = parent.lookup(TextField.class, new ByID(REMOVE_MULTIPLE_COLUMNS_TEXT_FIELD_ID)).wrap();
            setText(wrap, sb.toString());
            clickButtonForTestPurpose(REMOVE_MULTIPLE_COLUMNS_ACTION_BUTTON_ID);
        }

        Wrap wrap = parent.lookup(CheckBox.class, new ByID(NEW_COLUMN_GET_COLUMN_PROPERTIES_TAB_ID)).wrap();
        wrap.mouse().click();

        for (int i = 0; i < COLS; i++) {
            wrap = parent.lookup(TextField.class, new ByID(NEW_COLUMN_NAME_TEXTFIELD_ID)).wrap();

            String tabName = String.format("column %d", i);
            setText(wrap, tabName);

            wrap = parent.lookup(TextField.class, new ByID(NEW_COLUMN_INDEX_TEXTFIELD_UD)).wrap();
            setText(wrap, String.valueOf(i));

            clickButtonForTestPurpose(NEW_COLUMN_ADD_BUTTON_ID);
            switchToPropertiesTab(tabName);
            try {
                setPropertyBySlider(SettingType.SETTER, Properties.prefWidth, 50.0);
            } catch (InterruptedException ex) {
                Logger.getLogger(ApplicationInteractionFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        clickButtonForTestPurpose(BTN_CREATE_SORTABLE_ROWS_ID);
    }

    protected void setCellEditor(final CellEditorType type, final boolean isCustom) {

        new GetAction<Object>() {
            @Override
            public void run(Object... parameters) throws Exception {
                Wrap<? extends Node> cmbEditors
                        = parent.lookup(new ByID(CMB_EDITORS_ID)).wrap();

                ((ComboBox) cmbEditors.getControl()).setValue(type);
            }
        }.dispatch(testedControl.getEnvironment());

        new GetAction<Object>() {
            @Override
            public void run(Object... parameters) throws Exception {
                Wrap<? extends Node> chbCustom = parent.lookup(new ByID(CHB_IS_CUSTOM_ID)).wrap();
                ((CheckBox)chbCustom.getControl()).setSelected(isCustom);
            }
        }.dispatch(testedControl.getEnvironment());

        clickButtonForTestPurpose(BTN_SET_CELLS_EDITOR_ID);

        if (isCustom) {
            testedControl.waitState(new State<Object>() {
                String id = null;
                {
                   if (type == CellEditorType.CHOICEBOX) {
                        id = Cell.EDITING_CHOICEBOX_CELL_ID;
                    } else if (type == CellEditorType.COMBOBOX) {
                        id = Cell.EDITING_COMBOBOX_CELL_ID;
                    } else if (type == CellEditorType.TEXT_FIELD) {
                        id = Cell.EDITING_TEXTFIELD_CELL_ID;
                    } else {
                        throw new RuntimeException("Something went wrong");
                    }
                }
                @Override
                public Object reached() {
                    return (testedControl.as(Parent.class, Node.class).lookup(Node.class, new ByID(id)).size() > 0)
                           ? 42 : null;
                    }
            });

        }
    }

    protected String getCellText(final Wrap<? extends Labeled> wrap) {
        return new GetAction<String>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(((Labeled)wrap.getControl()).getText());
            }
        }.dispatch(testedControl.getEnvironment());
    }

    protected void checkPopup() throws Exception {
        Lookup<Scene> lookup = Root.ROOT.lookup(new ByWindowType(PopupWindow.class))
                                         .lookup(Scene.class);
        if (lookup.size() != 1) {
            throw new Exception("Popup is expected to appear.");
        }
    }

    /*
     * Doesn't take nested columns into consideration.
     */
    protected Collection<TableColumnBase> getTopLevelColumns() {
        return new GetAction<Collection<TableColumnBase>>() {

            @Override
            public void run(Object... parameters) throws Exception {
                Collection<TableColumnBase> cols;
                Control control = testedControl.getControl();

                if (isTableTests) {
                    cols = ((TableView) control).getColumns();
                } else {
                    cols = ((TreeTableView) control).getColumns();
                }
                setResult(cols);
            }
        }.dispatch(testedControl.getEnvironment());
    }

    protected void setShowRoot(final boolean value) {
        if (!isTableTests) {
            new GetAction<Object>() {

                @Override
                public void run(Object... os) throws Exception {
                    ((TreeTableView) testedControl.getControl()).setShowRoot(value);
                }
            }.dispatch(testedControl.getEnvironment());

            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (Exception e) {
            }
        }
    }

    protected void sortColumnsByName(final boolean isAscending) {
        new GetAction<Object>() {
            private Comparator<TableColumnBase> comparator = new Comparator<TableColumnBase>() {
                public int compare(TableColumnBase o1, TableColumnBase o2) {
                    int result = o1.getText().compareTo(o2.getText());
                    return isAscending ? result : -result;
                }
            };

            @Override
            public void run(Object... os) throws Exception {
                if (isTableTests) {
                    FXCollections.sort(((TableView) testedControl.getControl()).getColumns(), comparator);
                } else {
                    FXCollections.sort(((TreeTableView) testedControl.getControl()).getColumns(), comparator);
                }
            }
        }.dispatch(testedControl.getEnvironment());

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (Exception e) {
        }
    }
}
