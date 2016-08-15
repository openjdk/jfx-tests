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

import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.control.*;
import org.jemmy.dock.DockInfo;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.fx.WindowElement;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.EditableCellOwner.EditableCell;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Show;
import org.jemmy.interfaces.Showable;
import org.jemmy.lookup.LookupCriteria;


/**
 * A table could be used as a parent for objects, which are contained in the tree.
 *
 * @param DATA
 * @author KAM, shura
 * @see ItemWrap
 * @see TableCellItemDock
 */

@ControlType(Object.class)
@ControlInterfaces(value={WindowElement.class, EditableCell.class, Showable.class},
        encapsulates={TableView.class})
@DockInfo(name="org.jemmy.fx.control.TableCellItemDock", generateSubtypeLookups=true, multipleCriteria=false)
public class TableCellItemWrap<DATA extends Object> extends ItemWrap<DATA> implements Showable {

    @ObjectLookup("cell coordinates")
    public static <T> LookupCriteria<T> byCoords(Class<T> type, int row, int column) {
        return new TableCellItemParent.ByPoint<>(column, row);
    }

    private TableViewWrap<? extends TableView> tableViewWrap;
    private int row;
    private TableColumn column;
    private final WindowElement<TableView> wElement;

    /**
     *
     * @param env
     * @param cellItem
     * @param tableViewWrap
     */
    public TableCellItemWrap(TableViewWrap<? extends TableView> tableViewWrap,
            int row, TableColumn column, DATA cellItem, CellEditor<? super DATA> editor) {
        this(tableViewWrap, null, row, column, cellItem, editor);
    }

    public TableCellItemWrap(TableViewWrap<? extends TableView> tableViewWrap, Class<DATA> dataClass,
            int row, TableColumn column, DATA cellItem, CellEditor<? super DATA> editor) {
        super(dataClass, new Point(tableViewWrap.getDataColumns().indexOf(column), row), cellItem, tableViewWrap, editor);
        this.tableViewWrap = tableViewWrap;
        this.row = row;
        this.column = column;
        wElement = new ViewElement<>(TableView.class, tableViewWrap.getControl());
    }

    @Property(ITEM_PROP_NAME)
    @Override
    public Point getItem() {
        return (Point) super.getItem();
    }

    @Override
    public Wrap<? extends TableCell> cellWrap() {
        return tableViewWrap.as(Parent.class, Node.class).lookup(TableCell.class,
          new TableCellLookup(row, column)).wrap(0);
    }

    @Override
    public Rectangle getScreenBounds() {
        return cellWrap().getScreenBounds();
    }

    @Override
    public Show shower() {
        return this;
    }

    @Override
    public void show() {
        tableViewWrap.scrollTo(row, tableViewWrap.getDataColumns().indexOf(column));
    }

    /**
     * To get the tree view where the item resides.
     * @return
     */
    @As
    public WindowElement<TableView> asWindowElement() {
        return wElement;
    }

    /**
     * Deprecated
     * @param <ITEM>
     */
    public static class TableCellLookup implements LookupCriteria<TableCell> {

        private final int row;
        private final TableColumn column;

        public TableCellLookup(int row, TableColumn column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean check(TableCell control) {
            if (control.isVisible() && control.getOpacity() == 1.0) {
                return control.getTableColumn().equals(column)
                        && control.getTableRow().getIndex() == row;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Looking for a (" + row + "," + column + ") cell";
        }
    }
}