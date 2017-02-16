/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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

import javafx.scene.control.skin.VirtualFlow;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.jemmy.Point;
import org.jemmy.action.FutureAction;
import org.jemmy.control.As;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.env.Environment;
import org.jemmy.fx.control.Scrollable2DImpl.ScrollsLookupCriteria;
import org.jemmy.interfaces.EditableCellOwner;
import org.jemmy.interfaces.Focusable;
import org.jemmy.interfaces.Scrollable2D;
import org.jemmy.interfaces.Table;

import java.util.ArrayList;

/**
 * Table support in JemmyFX is provided through
 * <code>Table</code> and
 * <code>Parent</code> control interfaces.
 *
 * @param <CONTROL>
 * @author shura
 * @see #asItemParent(java.lang.Class)
 * @see #asTable(java.lang.Class)
 * @see #asTableCellItemParent(java.lang.Class)
 * @see TableViewDock
 */
@ControlType({TableView.class})
@ControlInterfaces(value = {Table.class, Scrollable2D.class},
        encapsulates = {Object.class})
public class TableViewWrap<CONTROL extends TableView> extends ControlWrap<CONTROL> implements Focusable {

    public static final String DATA_COLUMNS_PROP_NAME = "data.columns";
    public static final String COLUMNS_PROP_NAME = "columns";
    public static final String ITEMS_PROP_NAME = "items";
    public static final String SELECTION_PROP_NAME = "selection";
    public static final String ITEMS_COUNT_PROP_NAME = "item.count";
    private TableTreeScroll scroll;
    private Scrollable2D scrollable2D;
    private TableCellItemParent parent;

    /**
     * TableViewWrap is a wrap for TableView control of JavaFX
     *
     * @param env
     * @param nd
     */
    public TableViewWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    /**
     * Gives a size of a list of objects (rows) displayed in the table.
     *
     * @return
     */
    @Property(ITEMS_COUNT_PROP_NAME)
    public int getSize() {
        return new FutureAction<>(getEnvironment(), () -> getControl().getItems().size()).get();
    }

    /**
     * Gives a list of objects (rows) displayed in the table.
     *
     * @return
     */
    @Property(ITEMS_PROP_NAME)
    public ObservableList getItems() {
        return new FutureAction<>(getEnvironment(), () -> getControl().getItems()).get();
    }

    /**
     * Gives a selection.
     *
     * @return
     */
    @Property(SELECTION_PROP_NAME)
    public java.util.List<Point> selection() {
        return new FutureAction<>(getEnvironment(), () -> {
            java.util.List<Point> res = new ArrayList<>();
            for (TablePosition tp : (java.util.List<TablePosition>) getControl().getSelectionModel().getSelectedCells()) {
                res.add(new Point(tp.getColumn(), tp.getRow()));
            }
            return res;
        }).get();
    }

    Object getRow(final int index) {
        return new FutureAction<>(getEnvironment(), () -> getControl().getItems().get(index)).get();
    }

    TableColumn getColumn(final int index) {
        return new FutureAction<>(getEnvironment(), () -> (TableColumn) getControl().getColumns().get(index)).get();

    }

    /**
     * @return list of columns.
     */
    @Property(COLUMNS_PROP_NAME)
    public java.util.List<TableColumn> getColumns() {
        return new FutureAction<>(getEnvironment(), () -> getControl().getColumns()).get();
    }

    /**
     * @return List of columns, which are on the last level (leaf columns) of
     *         table header. In the case of nested columns, some columns are parents,
     *         and some are children. This method returns the list of columns, which
     *         have no children. Those columns correspond to the realy shown columns of
     *         data in TableView.
     */
    @Property(DATA_COLUMNS_PROP_NAME)
    public java.util.List<TableColumn> getDataColumns() {
        return new FutureAction<>(getEnvironment(), () -> {
            ArrayList fillList = new ArrayList<TableColumn>();
            getLastLevelColumns((java.util.List<? extends TableColumnBase>) getControl().getColumns(), fillList);
            return (java.util.List<TableColumn>) fillList;
        }).get();
    }

    protected static void getLastLevelColumns(java.util.List<? extends TableColumnBase> columnsToSearchIn, java.util.List fillingList) {
        for (TableColumnBase column : columnsToSearchIn) {
            if (column.getColumns().size() > 0) {
                getLastLevelColumns(column.getColumns(), fillingList);
            } else {
                fillingList.add(column);
            }
        }
    }

    /**
     * Jemmy table control interface introduces multiple selections mechanism.
     *
     * @param <T>
     * @param type
     * @return
     */
    @As(Object.class)
    public <T> Table<T> asTable(Class<T> type) {
        return asTableCellItemParent(type);
    }

    @As
    public Scrollable2D asScrollable2D() {
        if (scrollable2D == null) {
            scrollable2D = new Scrollable2DImpl(this, new ScrollsLookupCriteria() {
                @Override
                public boolean checkFor(ScrollBar scrollBar) {
                    return ((scrollBar.getParent() instanceof VirtualFlow)
                            && (scrollBar.getParent().getParent() instanceof TableView));
                }
            });
        }
        return scrollable2D;
    }

    /**
     * You could find items within table and operate with them just like with
     * any other UI elements.
     *
     * @param <T>
     * @param type
     * @return
     * @see TableCellItemWrap
     */
    @As(Object.class)
    public <T> EditableCellOwner<T> asItemParent(Class<T> type) {
        return asTableCellItemParent(type);
    }

    void scrollTo(int row, int column) {
        if (scroll == null) {
            scroll = new TableTreeScroll(this);
        }
        scroll.checkScrolls();
        TableUtils.<TableCell>scrollTo(getEnvironment(), this, scroll.hScroll,
                scroll.vScroll, row, column,
                new TableUtils.TableViewIndexInfoProvider(this), TableCell.class);
    }

    private <T> TableCellItemParent<T> asTableCellItemParent(Class<T> type) {
        if (parent == null || !parent.getType().equals(type)) {
            parent = new TableCellItemParent<>(this, type);
        }
        return parent;
    }

    protected int getRowIndex(IndexedCell tableCell) {
        return ((TableCell) tableCell).getTableRow().getIndex();
    }

    protected int getColumnIndex(IndexedCell tableCell) {
        return ((TableCell) tableCell).getTableView().getVisibleLeafIndex(((TableCell) tableCell).getTableColumn());
    }
}