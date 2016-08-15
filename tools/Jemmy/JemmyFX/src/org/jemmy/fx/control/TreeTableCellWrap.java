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
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import org.jemmy.JemmyException;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.As;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
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
 * A tree table could be used as a parent for objects, which are contained in
 * the tree table.
 *
 * @param <DATA>
 * @author Alexander Kirov
 * @see ItemWrap
 * @see TreeTableCellItemDock
 */
@ControlType(Object.class)
@ControlInterfaces(value = {WindowElement.class, EditableCell.class, Showable.class},
encapsulates = {TreeTableView.class})
@DockInfo(name = "org.jemmy.fx.control.TreeTableCellDock", generateSubtypeLookups = true, multipleCriteria = false)
public class TreeTableCellWrap<DATA extends Object> extends ItemWrap<DATA> implements Showable {

    private TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap;
    private int row;
    private TreeTableColumn column;
    private final WindowElement<TreeTableView> windowElement;

    public TreeTableCellWrap(TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap, int row, TreeTableColumn column, CellEditor<? super DATA> editor) {
        this(treeTableViewWrap, null, row, column, (DATA) column.getCellData(row), editor);
    }

    public TreeTableCellWrap(TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap, int row, TreeTableColumn column, DATA cellItem, CellEditor<? super DATA> editor) {
        this(treeTableViewWrap, null, row, column, cellItem, editor);
    }

    public TreeTableCellWrap(TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap, Class<DATA> dataClass, int row, TreeTableColumn column, DATA cellItem, CellEditor<? super DATA> editor) {
        super(dataClass, new Point(treeTableViewWrap.getDataColumns().indexOf(column), row), cellItem, treeTableViewWrap, editor);
        this.treeTableViewWrap = treeTableViewWrap;
        this.row = row;
        this.column = column;
        windowElement = new ViewElement<TreeTableView>(TreeTableView.class, treeTableViewWrap.getControl());
    }

    @ObjectLookup("cell coordinates")
    public static <T> LookupCriteria<T> byCoords(Class<T> type, int column, int row) {
        return new TableCellItemParent.ByPoint<T>(column, row);
    }

    @Property(ITEM_PROP_NAME)
    @Override
    public Point getItem() {
        return (Point) super.getItem();
    }

    @Override
    public Wrap<? extends TreeTableCell> cellWrap() {
        return treeTableViewWrap.as(Parent.class, Node.class).lookup(TreeTableCell.class, new TreeTableCellLookup(row, column)).wrap(0);
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
        treeTableViewWrap.scrollTo(row, treeTableViewWrap.getDataColumns().indexOf(column));
    }

    @Override
    public void edit(DATA newValue) {
        boolean isEditable = new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(column.isEditable());
            }
        }.dispatch(getEnvironment());

        if (!isEditable) {
            String title = new GetAction<String>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(column.getText());
                }
            }.dispatch(getEnvironment());
            throw new JemmyException("Attempt of editing of non editable column. Column <" + column + ">, title <" + title + ">.");
        }
        super.edit(newValue);
    }

    /**
     * To get the tree table view where the item resides.
     */
    @As
    public WindowElement<TreeTableView> asWindowElement() {
        return windowElement;
    }

    /**
     * Deprecated
     *
     * @param <ITEM>
     */
    public static class TreeTableCellLookup implements LookupCriteria<TreeTableCell> {

        private final int row;
        private final TreeTableColumn column;

        public TreeTableCellLookup(int row, TreeTableColumn column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean check(TreeTableCell control) {
            if (control.isVisible() && control.getOpacity() == 1.0) {
                return control.getTableColumn().equals(column) && control.getTreeTableRow().getIndex() == row;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Looking for a (" + row + "," + column + ") tree table cell.";
        }
    }
}