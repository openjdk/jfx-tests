/*
 * Copyright (c) 2012-2013, Oracle and/or its affiliates. All rights reserved.
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

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTablePosition;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.As;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.dock.Shortcut;
import org.jemmy.env.Environment;
import org.jemmy.fx.control.Scrollable2DImpl.ScrollsLookupCriteria;
import org.jemmy.interfaces.Caret;
import org.jemmy.interfaces.EditableCellOwner;
import org.jemmy.interfaces.Focusable;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Scroll;
import org.jemmy.interfaces.Scrollable2D;
import org.jemmy.interfaces.Scroller;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;
import org.jemmy.interfaces.Table;
import org.jemmy.interfaces.Tree;

/**
 * TreeTable support in JemmyFX is provided through a few different control
 * interfaces. Namely, these are
 * <code>Table</code>,
 * <code>Tree</code>,
 * <code>Parent</code> and
 * <code>Selectable</code>. A tree could be considered a parent/selectable for
 * two types of objects:
 * <code>javafx.scene.control.TreeItem</code> in which case TreeItems are
 * themselves the elements of the hierarchy/list or the underlying data held
 * within the tree items.
 *
 * @see #asTreeTableItemParent()
 * @see #asTreeTableItemSelectable()
 * @see #asSelectable(java.lang.Class)
 * @see #asItemParent(java.lang.Class)
 * @author Alexander Kirov
 * @param <CONTROL>
 */
@ControlType({TreeTableView.class})
@ControlInterfaces(value = {Selectable.class, EditableCellOwner.class, EditableCellOwner.class, Tree.class, Table.class, Scroll.class, Scrollable2D.class},
encapsulates = {TreeItem.class, Object.class, Object.class, Object.class, Object.class},
name = {"asTreeItemSelectable", "asTableItemParent", "asTreeItemParent"})
public class TreeTableViewWrap<CONTROL extends TreeTableView> extends ControlWrap<CONTROL> implements Scroll, Selectable<TreeItem>, Focusable {

    public static final String SELECTED_INDEX_PROP_NAME = "tree.selected.index";
    public static final String SELECTED_ITEM_PROP_NAME = "tree.selected.item";
    public static final String SHOW_ROOT_PROP_NAME = "show.root";
    public static final String ROOT_PROP_NAME = "root.item";
    public static final String SELECTED_ITEMS_PROP_NAME = "selected.items";
    public final static String SELECTED_CELLS_PROP_NAME = "selected.cells";
    public static final String SELECTED_INDICES_PROP_NAME = "selected.indices";
    public static final String ITEMS_COUNT_PROP_NAME = "item.count";
    public static final String ITEMS_PROP_NAME = "items";
    public static final String DATA_COLUMNS_PROP_NAME = "data.columns";
    public static final String COLUMNS_PROP_NAME = "columns";
    private Tree tree;
    private Table table;
    private TreeTableCellParent cellParent;
    private TreeTableItemParent treeTableItemParent;
    private TreeTableNodeParent nodeParent;
    private Selectable selectable;
    private Selectable<TreeItem> itemSelectable;
    private TableTreeScroll scroll;
    private Scrollable2D scrollable2D;

    public TreeTableViewWrap(Environment env, CONTROL nd) {
        super(env, nd);
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
        if (table == null || !table.getType().equals(type)) {
            table = asTreeTableCellItemParent(type);
        }
        return table;
    }

    /**
     * Allows selections of paths. Notice that in the tree implementation, tree
     * root is not looked up. That is, fist lookup criterion of the criteria
     * passed for tree selection will be used for finding a child of the root
     * node whether or not the root node is shown.
     *
     * @param <T>
     * @param type
     * @return
     */
    @As(Object.class)
    public <T> Tree<T> asTree(Class<T> type) {
        if (tree == null || !tree.getType().equals(type)) {
            asTreeItemParent(TreeItem.class);
            tree = new TreeTableImpl<T>(type, this, treeTableItemParent);
        }
        return tree;
    }

    /**
     * You could find items within treetable and operate with them just like with
     * any other UI elements.
     *
     * @param <T>
     * @param type
     * @return
     * @see TableCellItemWrap
     */
    @As(Object.class)
    public <T> EditableCellOwner<T> asTableItemParent(Class<T> type) {
        return asTreeTableCellItemParent(type);
    }

    /**
     * Allows to perform lookup for data objects - the objects which are
     * accessible through
     * <code>javafx.scene.control.TreeItem.getValue()</code>. All objects within
     * the treeTable are elements of this hierarchy: whether visible or not. Notice
     * though that the implementation does not expand nodes, so if a tree is
     * loaded dynamically on node expand, those dynamically added nodes will not
     * be a part of hierarchy.
     *
     * @param <T> type of data supported by the tree. If should be consistent
     * with the data present in the tree, because the tree data may get casted
     * to this type parameter during lookup operations. That, this must be a
     * super type for all types present in the tree.
     * @param type
     * @return
     * @see #asTableItemParent()
     */
    @As(Object.class)
    public <T> EditableCellOwner<T> asTreeItemParent(Class<T> type) {
        if (treeTableItemParent == null || !treeTableItemParent.getType().equals(type)) {
            treeTableItemParent = new TreeTableItemParent<T>(this, getRoot(), type);
        }
        return treeTableItemParent;
    }

    /**
     * Allows to perform lookup for
     * <code>javafx.scene.control.TreeItem</code>s. All objects within the tree
     * are elements of this hierarchy: whether visible or not. Notice though
     * that the implementation does not expand nodes, so if a tree is loaded
     * dynamically on node expand, those dynamically added nodes will not be a
     * part of hierarchy.
     *
     * @param <T>
     * @param type
     * @return
     * @see #asTreeItemParent()
     */
    @As(TreeItem.class)
    public <T extends TreeItem> EditableCellOwner<T> asItemParent(Class<T> type) {
        if (nodeParent == null) {
            nodeParent = new TreeTableNodeParent<T>(this, type);
        }
        return nodeParent;
    }

    /**
     * Allows to work with treeTable as with a list on selectable data objects - the
     * objects which are accessible through
     * <code>javafx.scene.control.TreeItem.getValue()</code>. Notice that only
     * expanded tree items get into the list. A set of items in the hierarchy is
     * also limited by the type parameter - objects of other types do not make
     * it to the list.
     *
     * @param <T>
     * @param type
     * @return
     * @see #asTreeItemSelectable()
     */
    @As(Object.class)
    public <T> Selectable<T> asSelectable(Class<T> type) {
        if (selectable == null || !selectable.getType().equals(type)) {
            selectable = new TreeTableViewSelectable<T>(type);
        }
        return selectable;
    }

    /**
     * Allows to work with treeTable as with a list on selectable items. Notice
     * that only expanded tree items get into the list.
     *
     * @return
     * @see #asSelectable(java.lang.Class)
     */
    @As(TreeItem.class)
    public Selectable<TreeItem> asTreeItemSelectable() {
        if (itemSelectable == null) {
            itemSelectable = new TreeTableViewSelectable();
        }
        return itemSelectable;
    }

    /**
     * Returns implementation of Scrollable2D interface.
     */
    @As
    public Scrollable2D asScrollable2D() {
        if (scrollable2D == null) {
            scrollable2D = new Scrollable2DImpl(this, new ScrollsLookupCriteria() {
                @Override
                public boolean checkFor(ScrollBar scrollBar) {
                    return ((scrollBar.getParent() instanceof VirtualFlow)
                            && (scrollBar.getParent().getParent() instanceof TreeTableView));
                }
            });
        }
        return scrollable2D;
    }

    /**
     * Returns implementation of vertical scroll.
     */
    @As
    public Scroll asScroll() {
        checkScroll();
        return scroll.asScroll();
    }

    @Property(ROOT_PROP_NAME)
    public TreeItem getRoot() {
        return new GetAction<TreeItem>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().getRoot());
            }
        }.dispatch(getEnvironment());
    }

    /**
     * Returns selected row index.
     *
     * @return
     */
    @Property(SELECTED_INDEX_PROP_NAME)
    public int getSelectedIndex() {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... parameters) {
                setResult(Integer.valueOf(getControl().getSelectionModel().getSelectedIndex()));
            }
        }.dispatch(getEnvironment());
    }

    @Property(SELECTED_ITEM_PROP_NAME)
    public TreeItem getSelectedItem() {
        return new GetAction<TreeItem>() {
            @Override
            public void run(Object... parameters) {
                setResult((TreeItem) getControl().getSelectionModel().getSelectedItem());
            }
        }.dispatch(getEnvironment());
    }

    @Property(SHOW_ROOT_PROP_NAME)
    public boolean isShowRoot() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getControl().isShowRoot());
            }
        }.dispatch(getEnvironment());
    }

    @Override
    public TreeItem getState() {
        return asTreeItemSelectable().getState();
    }

    @Override
    public List<TreeItem> getStates() {
        return asTreeItemSelectable().getStates();
    }

    @Override
    public Class<TreeItem> getType() {
        return TreeItem.class;
    }

    @Override
    public Selector<TreeItem> selector() {
        return asTreeItemSelectable().selector();
    }

    @Property(VALUE_PROP_NAME)
    @Override
    public double position() {
        checkScroll();
        return scroll.position();
    }

    @Property(MINIMUM_PROP_NAME)
    @Override
    public double minimum() {
        checkScroll();
        return scroll.minimum();
    }

    @Property(MAXIMUM_PROP_NAME)
    @Override
    public double maximum() {
        checkScroll();
        return scroll.maximum();
    }

    @Override
    @Deprecated
    public double value() {
        checkScroll();
        return position();
    }

    @Deprecated
    @Override
    public Scroller scroller() {
        checkScroll();
        return scroll.scroller();
    }

    @Override
    public Caret caret() {
        checkScroll();
        return scroll.caret();
    }

    @Override
    public void to(double position) {
        checkScroll();
        scroll.to(position);
    }

    private void checkScroll() {
        if (scroll == null) {
            scroll = new TableTreeScroll(this);
        }
    }

    @Property(COLUMNS_PROP_NAME)
    public List<TreeTableColumn> getColumns() {
        return new GetAction<java.util.List<TreeTableColumn>>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getControl().getColumns());
            }
        }.dispatch(getEnvironment());
    }

    /**
     * @return List of columns, which are on the last level (leaf columns) of
     * tree table header. In the case of nested columns, some columns are
     * parents, and some are children. This method returns the list of columns,
     * which have no children. Those columns correspond to the realy shown
     * columns of data in TreeTableView.
     */
    @Property(DATA_COLUMNS_PROP_NAME)
    public List<TreeTableColumn> getDataColumns() {
        return new GetAction<java.util.List<TreeTableColumn>>() {
            @Override
            public void run(Object... parameters) throws Exception {
                ArrayList fillList = new ArrayList<TableColumnBase>();
                TableViewWrap.getLastLevelColumns((java.util.List<? extends TableColumnBase>) getControl().getColumns(), fillList);
                setResult((java.util.List) fillList);
            }
        }.dispatch(getEnvironment());
    }

    /**
     * Gives a size of a list of objects (rows) displayed in the tree table.
     *
     * @return
     */
    @Property(ITEMS_PROP_NAME)
    public ObservableList getItems() {
        return new GetAction<ObservableList>() {
            @Override
            public void run(Object... parameters) throws Exception {
                ObservableList listForItems = FXCollections.observableArrayList();
                if (getControl().isShowRoot()) {
                    if (getControl().getRoot() != null) {
                        listForItems.add(getControl().getRoot());
                    }
                }
                getItemsRec(getControl().getRoot(), listForItems);
                setResult(listForItems);
            }

            private void getItemsRec(TreeItem rootItem, ObservableList listForItems) {
                if (rootItem.isExpanded()) {
                    for (Object item : rootItem.getChildren()) {
                        listForItems.add(item);
                        getItemsRec((TreeItem) item, listForItems);
                    }
                }
            }
        }.dispatch(getEnvironment());
    }

    public TreeTableItemWrap getRootWrap() {
        TreeItem root = getRoot();
        if (root == null) {
            return null;
        } else {
            return new TreeTableItemWrap(Object.class, root, this, null);
        }
    }

    /**
     * Gives a list of objects (rows) displayed in the tree table.
     *
     * @return
     */
    @Property(ITEMS_COUNT_PROP_NAME)
    public int getSize() {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getControl().getExpandedItemCount());
            }
        }.dispatch(getEnvironment());
    }

    /**
     * @return List<Integer>, containing list of selected indices.
     */
    @Property(SELECTED_INDICES_PROP_NAME)
    public java.util.List<Integer> getSelectedIndices() {
        return new GetAction<java.util.List<Integer>>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult((java.util.List<Integer>) getControl().getSelectionModel().getSelectedIndices());
            }
        }.dispatch(getEnvironment());
    }

    @Property(SELECTED_ITEMS_PROP_NAME)
    public List getSelectedItems() {
        return new GetAction<List>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getControl().getSelectionModel().getSelectedItems());
            }
        }.dispatch(getEnvironment());
    }

    @Property(SELECTED_CELLS_PROP_NAME)
    public List<Point> getSelectedCells() {
        return new GetAction<java.util.List<Point>>() {
            @Override
            public void run(Object... parameters) throws Exception {
                java.util.List<Point> res = new ArrayList<Point>();
                for (TreeTablePosition tp : (java.util.List<TreeTablePosition>) getControl().getSelectionModel().getSelectedCells()) {
                    res.add(new Point(tp.getColumn(), tp.getRow()));
                }
                setResult(res);
            }
        }.dispatch(getEnvironment());
    }

    @Shortcut
    public void scrollTo(int row, int column) {
        if (scroll == null) {
            scroll = new TableTreeScroll(this);
        }
        scroll.checkScrolls();
        TableUtils.<TreeTableCell>scrollTo(getEnvironment(), this,
                scroll.hScroll, scroll.vScroll,
                row, column,
                new TableUtils.TreeTableViewIndexInfoProvider(this), TreeTableCell.class);
    }

    Object getRow(final int index) {
        return getItems().get(index);
    }

    TreeTableColumn getColumn(final int index) {
        return (TreeTableColumn) getDataColumns().get(index);
        }

    protected int getRowIndex(IndexedCell tableCell) {
        return ((TreeTableCell) tableCell).getTreeTableRow().getIndex();
    }

    protected int getColumnIndex(IndexedCell tableCell) {
        return ((TreeTableCell) tableCell).getTreeTableView().getVisibleLeafIndex(((TreeTableCell) tableCell).getTableColumn());
    }

    public int getRow(TreeItem item) {
        return getItems().indexOf(item);
    }

    public IndexedCell getTreeCell(TreeItem item) {
        return (TreeTableRow) new TreeTableNodeWrap<>(item, new TreeTableItemWrap<>(Object.class, item, this, null), this, null).getNode().getControl();
    }

    protected class TreeTableViewSelectable<T> extends TreeSelectable<T> {

        TreeTableViewSelectable(Class<T> type) {
            super(type);
        }

        TreeTableViewSelectable() {
            super();
        }

        @Override
        protected TreeItem getRoot() {
            return TreeTableViewWrap.this.getRoot();
        }

        @Override
        protected boolean isShowRoot() {
            return TreeTableViewWrap.this.isShowRoot();
        }

        @Override
        protected TreeItem getSelectedItem() {
            return TreeTableViewWrap.this.getSelectedItem();
        }

        @Override
        protected Parent asTreeItemParent() {
            return TreeTableViewWrap.this.asItemParent(TreeItem.class);
        }

        @Override
        protected Environment getEnvironment() {
            return TreeTableViewWrap.this.getEnvironment();
        }
    }

    private <T> TreeTableCellParent<T> asTreeTableCellItemParent(Class<T> type) {
        if (cellParent == null || !cellParent.getType().equals(type)) {
            cellParent = new TreeTableCellParent<T>(this, type);
        }
        return cellParent;
    }
}