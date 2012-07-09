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

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.jemmy.JemmyException;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.*;
import org.jemmy.env.Environment;
import org.jemmy.fx.FXClickFocus;
import org.jemmy.fx.NodeWrap;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;

/**
 * Table support in JemmyFX is provided through <code>Table</code> and 
 * <code>Parent</code> control interfaces.
 * @see #asItemParent(java.lang.Class) 
 * @see #asTable(java.lang.Class) 
 * @see #asTableCellItemParent(java.lang.Class)  
 * @author shura
 * @param <CONTROL> 
 * @see TableViewDock
 */
@ControlType({TableView.class})
@ControlInterfaces(value = {Table.class}, encapsulates = {Object.class})
public class TableViewWrap<CONTROL extends TableView> extends NodeWrap<CONTROL>
        implements Focusable {

    public static final String SELECTION_PROP_NAME = "selection";
    private AbstractScroll hScroll, vScroll;
    private TableCellItemParent parent;

    /**
     * 
     * @param env
     * @param nd 
     */
    public TableViewWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    /**
     * Gives a size of a list of objects (rows) displayed in the table. 
     * @return 
     */
    @Property("itemCount")
    public int getSize() {
        return new GetAction<Integer>() {

            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getControl().getItems().size());
            }
        }.dispatch(getEnvironment());
    }

    /**
     * Gives a list of objects (rows) displayed in the table. 
     * @return 
     */
    @Property("items")
    public ObservableList getItems() {
        return new GetAction<ObservableList>() {

            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getControl().getItems());
            }
        }.dispatch(getEnvironment());
    }

    /**
     * Gives a selection. 
     * @return 
     */
    @Property("selection")
    public java.util.List<Point> selection() {
        return new GetAction<java.util.List<Point>>() {

            @Override
            public void run(Object... parameters) throws Exception {
                java.util.List<Point> res = new ArrayList<Point>();
                for (TablePosition tp : (java.util.List<TablePosition>) getControl().getSelectionModel().getSelectedCells()) {
                    res.add(new Point(tp.getColumn(), tp.getRow()));
                }
                setResult(res);
            }
        }.dispatch(getEnvironment());
    }

    Object getRow(final int index) {
        return new GetAction() {

            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getControl().getItems().get(index));
            }
        }.dispatch(getEnvironment());
    }

    TableColumn getColumn(final int index) {
        return new GetAction<TableColumn>() {

            @Override
            public void run(Object... parameters) throws Exception {
                setResult((TableColumn) getControl().getColumns().get(index));
            }
        }.dispatch(getEnvironment());
    }

    @Property("getColumns")
    public java.util.List<TableColumn> getColumns() {
        return new GetAction<java.util.List<TableColumn>>() {

            @Override
            public void run(Object... parameters) throws Exception {
                setResult((java.util.List<TableColumn>) getControl().getColumns());
            }
        }.dispatch(getEnvironment());
    }

    /**
     * Jemmy table control interface introduces multiple selections mechanism.
     * @param <T>
     * @param type
     * @return 
     */
    @As(Object.class)
    public <T> Table<T> asTable(Class<T> type) {
        return asTableCellItemParent(type);
    }

    /**
     * You could find items within table and operate with them just like with
     * any other UI elements.
     * @param <T>
     * @param type
     * @return 
     * @see TableCellItemWrap
     */
    @As(Object.class)
    public <T> EditableCellOwner<T> asItemParent(Class<T> type) {
        return asTableCellItemParent(type);
    }

    private <T> TableCellItemParent<T> asTableCellItemParent(Class<T> type) {
        if (parent == null || !parent.getType().equals(type)) {
            parent = new TableCellItemParent<T>(this, type);
        }
        return parent;
    }

    /**
     * Initialize wraps for ScrollBars if they're not yet initialized
     */
    private void checkScrolls() {
        if (vScroll == null) {
            vScroll = getScroll(true);
        }
        if (hScroll == null) {
            hScroll = getScroll(false);
        }
    }

    /**
     * @return wrap of parent container that contains TableCells
     */
    private Wrap<? extends javafx.scene.Parent> getClippedContainerWrap() {
        return ((Parent<Node>) as(Parent.class, Node.class)).lookup(javafx.scene.Parent.class, new LookupCriteria<javafx.scene.Parent>() {

            public boolean check(javafx.scene.Parent control) {
                return control.getClass().getName().endsWith("VirtualFlow$ClippedContainer");
            }
        }).wrap();
    }

    /**
     * Obtains wrap for scrollbar
     *
     * @param vertical
     * @return
     */
    private AbstractScroll getScroll(final boolean vertical) {
        Lookup<ScrollBar> lookup = as(Parent.class, Node.class).lookup(ScrollBar.class,
                new LookupCriteria<ScrollBar>() {

                    @Override
                    public boolean check(ScrollBar control) {
                        return (control.getOrientation() == Orientation.VERTICAL) == vertical && 
                                control.isVisible();
                    }
                });
        int count = lookup.size();
        if (count == 0) {
            return null;
        } else if (count == 1) {
            return lookup.as(AbstractScroll.class);
        } else {
            throw new JemmyException("There are more than 1 " + (vertical ? "vertical" : "horizontal")
                    + " ScrollBars in this TableView");
        }
    }

    /**
     * {@inheritDoc }
     */
    Wrap<? extends TableCell> scrollTo(final int row, final int column) {

        checkScrolls();

        if (vScroll != null) {
            vScroll.caret().to(new Caret.Direction() {

                public int to() {
                    int[] shown = shown();
                    if (shown[1] > row) {
                        return -1;
                    }
                    if (shown[3] < row) {
                        return 1;
                    }
                    return 0;
                }
            });
        }
        if (hScroll != null) {
            hScroll.caret().to(new Caret.Direction() {

                public int to() {
                    int[] shown = shown();
                    if (shown[0] > column) {
                        return -1;
                    }
                    if (shown[2] < column) {
                        return 1;
                    }
                    return 0;
                }
            });
        }
        return null;
        //as(Parent.class, Node.class).lookup(TableCell.class, ) 
    }

    /**
     * Identifies which elements are shown in the TableView currently.
     *
     * @return {minColumn, minRow, maxColumn, maxRow} of cells that are fully
     * visible in the list.
     */
    private int[] shown() {
        final Rectangle viewArea = getScreenBounds(getEnvironment(), getClippedContainerWrap().getControl());

        int[] res = new GetAction<int[]>() {

            @Override
            @SuppressWarnings("unchecked")
            public void run(Object... parameters) {
                final int[] res = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, -1, -1};

                as(Parent.class, Node.class).lookup(TableCell.class, new LookupCriteria<TableCell>() {

                    @Override
                    public boolean check(TableCell control) {
                        if (control.isVisible() && control.getOpacity() == 1.0) {
                            Rectangle bounds = getScreenBounds(getEnvironment(), control);
                            int column = getColumnIndex(control);
                            int row = getRowIndex(control);
                            if (viewArea.contains(bounds) && row >= 0 && column >= 0) {

                                res[0] = Math.min(res[0], column);
                                res[1] = Math.min(res[1], row);
                                res[2] = Math.max(res[2], column);
                                res[3] = Math.max(res[3], row);
                            }
                        }
                        return false;
                    }
                }).size();

                setResult(res);
            }
        }.dispatch(getEnvironment());

        return res;
    }

    private static int getRowIndex(TableCell tableCell) {
        return tableCell.getTableRow().getIndex();
    }

    private static int getColumnIndex(TableCell tableCell) {
        return tableCell.getTableView().getVisibleLeafIndex(tableCell.getTableColumn());
    }

    @Override
    public Focus focuser() {
        final Rectangle bounds = getScreenBounds();
        return new FXClickFocus(this, new Point(
                Math.max(bounds.getWidth() / 2, bounds.getWidth() - 3),
                Math.max(bounds.getHeight() / 2, bounds.getHeight() - 3)));
    }
}
