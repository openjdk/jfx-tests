/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.function.Function;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TreeTableCell;
import javafx.util.Callback;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.FutureAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.FXClickFocus;
import org.jemmy.fx.NodeWrap;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.Caret;
import org.jemmy.interfaces.Focus;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;

/**
 * @author Alexander Kirov
 */
public class TableUtils {

    public Focus focuser(Wrap<? extends Node> wrap) {
        final Rectangle bounds = wrap.getScreenBounds();
        return new FXClickFocus(wrap, new Point(
                Math.max(bounds.getWidth() / 2, bounds.getWidth() - 3),
                Math.max(bounds.getHeight() / 2, bounds.getHeight() - 3)));
    }

    public static <T extends Node> void scrollToInSingleDimension(final Wrap<? extends Control> controlWrap,
            final Class<T> itemClass,
            final Callback<T, Integer> indexCallback,
            final Integer targetControlIndex,
            final Caret caret,
            final boolean isVertical) {
        caret.to(new Caret.Direction() {
            /**
             * @return < 0 to scroll toward decreasing value, > 0 - vice versa 0
             * to stop scrolling NOTE - see implementation
             * KnobDragScrollerImpl.to(Direction) which is used in ScrollBarWrap
             * better to return constant values (-1 || 0 || +1) to get smooth
             * dragging
             */
            final javafx.scene.Parent clippedContainer = getClippedContainerControl(controlWrap);

            @Override
            public int to() {
                Bounds visibleBounds = shown1dImpl(controlWrap, clippedContainer, indexCallback, itemClass, isVertical);
                if (targetControlIndex < visibleBounds.getBegin()) {
                    return -1;
                } else if (targetControlIndex > visibleBounds.getEnd()) {
                    return 1;
                } else {
                    return 0;
                }
            }

            @Override
            public String toString() {
                return "'" + controlWrap.getControl() + "' state at index " + targetControlIndex;
            }
        });
    }

    public static <CellClass extends Node> Bounds shown1d(
            final Wrap<? extends Control> controlWrap,
            final Callback<CellClass, Integer> indexCallback,
            final Class<CellClass> itemClass,
            final boolean isVertical) {

        return shown1dImpl(controlWrap, getClippedContainerControl(controlWrap), indexCallback, itemClass, isVertical);
    }

    /**
     * Extracted for optimization purpose - we don't need to find clipped
     * container each time.
     *
     * @parameter clippedContainerWrap - precomputed wrap of clipped container.
     */
    private static <CellClass extends Node> Bounds shown1dImpl(
            final Wrap<? extends Control> controlWrap,
            final javafx.scene.Parent clippedContainer,
            final Callback<CellClass, Integer> indexCallback,
            final Class<CellClass> itemClass,
            final boolean isVertical) {
        final int[] minmax = new int[]{Integer.MAX_VALUE, -1};
        final Parent<Node> parent = controlWrap.as(Parent.class, Node.class);
        parent.lookup(itemClass, cntrl -> {
            boolean checkIndices;
            checkIndices = NodeWrap.isInBounds(clippedContainer, cntrl, controlWrap.getEnvironment(), isVertical);
            if (checkIndices) {
                int index = indexCallback.call(cntrl);
                if (index >= 0) {
                    if (index < minmax[0]) {
                        minmax[0] = index;
                    }
                    if (index > minmax[1]) {
                        minmax[1] = index;
                    }
                }
            }

            return true;
        }).size();

        return new Bounds(minmax[0], minmax[1]);
    }

    /**
     * checkScrolls(); must be called before this method call.
     *
     * @param row
     * @param column
     * @return
     */
    static <CellClass extends IndexedCell> Wrap<? extends IndexedCell> scrollTo(final Environment env,
            final Wrap<? extends Control> wrap,
            AbstractScroll hScroll,
            AbstractScroll vScroll,
            final int row,
            final int column,
            final Function<CellClass, Point> infoProvider,
            final Class<CellClass> cellType) {

        final Rectangle actuallyVisibleArea = getActuallyVisibleArea(wrap);
        if (vScroll != null) {
            vScroll.caret().to(() -> {
                int[] shown = shown(env, wrap, infoProvider, cellType, actuallyVisibleArea);
                if (shown[1] > row) {
                    return -1;
                }
                if (shown[3] < row) {
                    return 1;
                }
                return 0;
            });
        }

        if (hScroll != null) {
            hScroll.caret().to(() -> {
                int[] shown = shown(env, wrap, infoProvider, cellType, actuallyVisibleArea);
                if (shown[0] > column) {
                    return -1;
                }
                if (shown[2] < column) {
                    return 1;
                }
                return 0;
            });
        }
        return null;
    }

    /**
     * Identifies which elements are shown in the TableView currently.
     *
     * @return {minColumn, minRow, maxColumn, maxRow} of cells that are fully
     * visible in the list.
     */
    public static <CellClass extends IndexedCell> int[] shown(
            Environment env,
            final Wrap<? extends Control> wrap,
            final Function<CellClass, Point> infoProvider,
            final Class<CellClass> cellType) {

        final Rectangle actuallyVisibleArea = getActuallyVisibleArea(wrap);

        return shown(env, wrap, infoProvider, cellType, actuallyVisibleArea);
    }

    private static <CellClass extends IndexedCell> int[] shown(
            Environment env,
            final Wrap<? extends Control> wrap,
            final Function<CellClass, Point> infoProvider,
            final Class<CellClass> cellType,
            final Rectangle actuallyVisibleArea) {
        return new FutureAction<>(env, () -> {
            final int[] res = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, -1, -1};

            final Parent<Node> parent = wrap.as(Parent.class, Node.class);
            parent.lookup(cellType, control -> {
                if (control.isVisible() && control.getParent().isVisible() && control.getOpacity() == 1.0) {
                    Rectangle bounds = NodeWrap.getScreenBounds(wrap.getEnvironment(), control);
                    Point cellCoord = infoProvider.apply(control);
                    int column = cellCoord.x;
                    int row = cellCoord.y;
                    if (actuallyVisibleArea.contains(bounds) && row >= 0 && column >= 0) {
                        res[0] = Math.min(res[0], column);
                        res[1] = Math.min(res[1], row);
                        res[2] = Math.max(res[2], column);
                        res[3] = Math.max(res[3], row);
                    }
                }
                return false;
            }).size();

            return res;
        }).get();
    }

    /**
     * @param controlWrap wrap of control like listView, tableView, treeView, or
     * treeTableView.
     * @param treeRowWrap wrap of row : treeItem, treeTableRow or tableRow,
     * listItem.
     * @return center of area, which is visible part of row/item.
     */
    public static Point getClickPoint(Wrap controlWrap, Wrap rowWrap) {
        Rectangle visibleAreaBounds = rowWrap.getScreenBounds();
        return getClickPointCommon(controlWrap, visibleAreaBounds);
    }

    /**
     * Method, specific for TableView and TreeTableView, which counts only
     * actual cells width, without space of the last empty column.
     */
    public static Point getTableRowClickPoint(Wrap controlWrap, Wrap rowWrap) {
        Rectangle clickableArea = TableUtils.constructClickableArea(rowWrap);
        return getClickPointCommon(controlWrap, clickableArea);
    }

    /**
     * Used for ListView, TreeView, TableView, TreeTableView.
     *
     * @param wrap of control.
     * @return
     */
    static Rectangle getActuallyVisibleArea(final Wrap<? extends Control> wrap) {
        final Rectangle viewArea = getContainerWrap((Parent <Node>)wrap.as(Parent.class, Node.class)).getScreenBounds();
        final Rectangle clippedContainerArea = getClippedContainerWrap((Parent <Node>)wrap.as(Parent.class, Node.class)).getScreenBounds();
        return new Rectangle(viewArea.x, viewArea.y, clippedContainerArea.width, clippedContainerArea.height);
    }

    /**
     * @return wrap of parent container that contains Cells
     */
    static Wrap<? extends javafx.scene.Parent> getContainerWrap(Parent<Node> parent) {
        return getParentWrap(parent, VIRTIAL_FLOW_CLASS_NAME);
    }

    static Wrap<? extends javafx.scene.Parent> getClippedContainerWrap(Parent<Node> parent) {
        return getParentWrap(parent, CLIPPED_CONTAINER_CLASS_NAME);
    }

    static private Wrap<? extends javafx.scene.Parent> getParentWrap(Parent<Node> parent, final String className) {
        return parent.lookup(javafx.scene.Parent.class, control -> control.getClass().getName().endsWith(className)).wrap();
    }

    static private javafx.scene.Parent getClippedContainerControl(Wrap<? extends Control> controlWrap) {
        final Parent<Node> parent = controlWrap.as(Parent.class, Node.class);
        final Wrap<? extends javafx.scene.Parent> clippedContainerWrap = getClippedContainerWrap(parent);
        return clippedContainerWrap.getControl();
    }

    /**
     * Common algorithm
     */
    private static Point getClickPointCommon(Wrap controlWrap, Rectangle clickableArea) {
        Rectangle visibleAreaBounds = TableUtils.getActuallyVisibleArea(controlWrap);
        Rectangle intersection = clickableArea.intersection(visibleAreaBounds);
        int dx = intersection.x - clickableArea.x;
        int dy = intersection.y - clickableArea.y;
        return new Point(dx + intersection.width / 2, dy + intersection.height / 2);
    }

    /**
     * Special for TableView, and TreeTableView, as the last column contains
     * empty not clickable space.
     */
    private static Rectangle constructClickableArea(Wrap<?> wrap) {
        Rectangle rec = null;
        final Lookup lookup = wrap.as(Parent.class, Node.class).lookup(IndexedCell.class);
        for (int i = 0; i < lookup.size(); i++) {
            Wrap cell = lookup.wrap(i);
            if (rec == null) {
                rec = cell.getScreenBounds();
            } else {
                rec = rec.union(cell.getScreenBounds());
            }
        }
        return rec;
    }
    private static final String VIRTIAL_FLOW_CLASS_NAME = "VirtualFlow";
    private static final String CLIPPED_CONTAINER_CLASS_NAME = "VirtualFlow$ClippedContainer";

    public final static class TableViewIndexInfoProvider implements Function<TableCell, Point> {

        TableViewWrap theWrap;

        public TableViewIndexInfoProvider(TableViewWrap wrap) {
            this.theWrap = wrap;
        }

        @Override
        public Point apply(TableCell t) {
            return new Point(theWrap.getColumnIndex(t), theWrap.getRowIndex(t));
        }
    }

    public final static class TreeTableViewIndexInfoProvider implements Function<TreeTableCell, Point> {

        TreeTableViewWrap theWrap;

        public TreeTableViewIndexInfoProvider(TreeTableViewWrap wrap) {
            this.theWrap = wrap;
        }

        @Override
        public Point apply(TreeTableCell t) {
            return new Point(theWrap.getColumnIndex(t), theWrap.getRowIndex(t));
        }
    }

    public static class Bounds {

        private final int begin;
        private final int end;

        public Bounds(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        public int getBegin() {
            return begin;
        }

        public int getEnd() {
            return end;
        }

        @Override
        public String toString() {
            return "Bounds : begin <" + begin + ">, end <" + end + ">.";
        }
    }
}
