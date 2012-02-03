/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this License Header
 * Notice in each file.
 *
 * If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
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
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.FXClickFocus;
import org.jemmy.fx.NodeWrap;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;

/**
 * Wrapper for TableView control. 
 * Everywhere coordinates are of form {column, row}
 * @param <CONTROL>
 * @author Shura
 */
@ControlType({TableView.class})
@ControlInterfaces(value = {Table.class}, encapsulates = {Object.class})
public class TableViewWrap<CONTROL extends TableView> extends NodeWrap<CONTROL>
        implements Focusable {

    public static final String SELECTION_PROP_NAME = "selection";
    private AbstractScroll hScroll, vScroll;

    public TableViewWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    @Property("itemCount")
    public int getSize() {
        return new GetAction<Integer>() {

            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getControl().getItems().size());
            }
        }.dispatch(getEnvironment());
    }
    
    @Property("items")
    public ObservableList getItems() {
        return new GetAction<ObservableList>() {

            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getControl().getItems());
            }
        }.dispatch(getEnvironment());
    }
    
    @Property("selection")
    public java.util.List<Point> selection() {
        return new GetAction<java.util.List<Point>>() {

            @Override
            public void run(Object... parameters) throws Exception {
                java.util.List<Point> res = new ArrayList<Point>();
                for(TablePosition tp : (java.util.List<TablePosition>)getControl().getSelectionModel().getSelectedCells()) {
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
                setResult((TableColumn)getControl().getColumns().get(index));
            }
        }.dispatch(getEnvironment());
    }
    
    @Property("getColumns")
    public java.util.List<TableColumn> getColumns() {
        return new GetAction<java.util.List<TableColumn>>() {

            @Override
            public void run(Object... parameters) throws Exception {
                setResult((java.util.List<TableColumn>)getControl().getColumns());
            }
        }.dispatch(getEnvironment());
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Parent.class.equals(interfaceClass)
                && TableCell.class.equals(type)) {
            return true;
        }
        if (Table.class.equals(interfaceClass)
                && Object.class.equals(type)) {
            return true;
        }
        return super.is(interfaceClass, type);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (CellOwner.class.isAssignableFrom(interfaceClass)) {
            return (INTERFACE) new TableCellItemParent(this, type);
        }
        return super.as(interfaceClass, type);
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
     * @param vertical
     * @return 
     */
    private AbstractScroll getScroll(final boolean vertical) {
        Lookup<ScrollBar> lookup = as(Parent.class, Node.class).lookup(ScrollBar.class,
                new LookupCriteria<ScrollBar>() {

                    @Override
                    public boolean check(ScrollBar control) {
                        return (control.getOrientation() == Orientation.VERTICAL) == vertical;
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
    private Wrap<? extends TableCell> scrollTo(int index) {

        checkScrolls();

        final int column = 0;
        final int row = index;

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

                as(Parent.class, TableCell.class).lookup(new LookupCriteria<TableCell>() {

                    @Override
                    public boolean check(TableCell control) {
                        if (control.isVisible() && control.getOpacity() == 1.0) {
                            Rectangle bounds = getScreenBounds(getEnvironment(), control);
                            int column = getColumnIndex(control);
                            int row = getRowIndex(control);
                            if (viewArea.contains(bounds)) {

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
