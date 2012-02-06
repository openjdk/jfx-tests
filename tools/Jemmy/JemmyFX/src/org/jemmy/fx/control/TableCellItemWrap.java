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

import java.util.List;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Wrap;
import org.jemmy.dock.DockInfo;
import org.jemmy.fx.NodeWrap;
import org.jemmy.fx.WindowElement;
import org.jemmy.interfaces.Caret.Direction;
import org.jemmy.interfaces.EditableCellOwner.EditableCell;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.LookupCriteria;


/**
 * This represents a content of a single tree cell, as opposed to table items
 * which is a row.
 *
 * @param <ITEM>
 * @author KAM, shura
 */

@ControlType(Object.class)
@ControlInterfaces(value={EditableCell.class, Showable.class}, name={"asCell"})
@DockInfo(name="org.jemmy.fx.control.TableCellItemDock")
public class TableCellItemWrap<ITEM extends Object> extends ItemWrap<ITEM> implements Showable {

    private TableViewWrap<? extends TableView> tableViewWrap;
    private Object row;
    private TableColumn column;
    
    /**
     *
     * @param env
     * @param cellItem
     * @param listViewWrap
     */
    public TableCellItemWrap(TableViewWrap<? extends TableView> listViewWrap, 
            Object row, TableColumn column, ITEM cellItem, CellEditor<? super ITEM> editor) {
        super(cellItem, listViewWrap, editor);
        this.tableViewWrap = listViewWrap;
        this.row = row;
        this.column = column;
    }

    /**
     * This method finds listCell for the selected item. Should be invoked only
     * using FX.deferAction()
     * That can be needed for cases like obtaining screenBounds for corresponding ListCell.
     */
    @Override
    protected Wrap<? extends TableCell> cellWrap() {
        return tableViewWrap.as(Parent.class, Node.class).lookup(TableCell.class,
          new TableListItemByObjectLookup<ITEM>(getControl())).wrap(0);
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
        final List<ITEM> items = tableViewWrap.getItems();

        final long desiredIndex = items.indexOf(row);

//        Caret c = tableViewWrap.as(Scroll.class).caret();
        CaretOwner scroller = (CaretOwner) tableViewWrap.as(Parent.class, Node.class).
                lookup(ScrollBar.class).as(CaretOwner.class);
        Caret c = scroller.caret();

        Direction direction = new Direction() {

            /**
             * @return < 0 to scroll toward decreasing value, > 0 - vice versa
             * 0 to stop scrolling
             * NOTE - see implementation KnobDragScrollerImpl.to(Direction) which is used in ScrollBarWrap
             *        better to return constant values (-1 || 0 || +1) to get smooth dragging
             */
            @Override
            public int to() {
                final int[] minmax = new int[]{Integer.MAX_VALUE, -1};
                final List items = tableViewWrap.getItems();
                tableViewWrap.as(Parent.class, Node.class).lookup(TableCell.class,
                        new LookupCriteria<TableCell>() {

                            public boolean check(TableCell control) {
                                if (NodeWrap.isInside(tableViewWrap.getControl(), control, getEnvironment())) {
                                    int index = items.indexOf(control.getItem());
                                    if (index >= 0) {
                                        if (index < minmax[0]) {
                                            minmax[0] = index;
                                        } else if (index > minmax[1]) {
                                            minmax[1] = index;
                                        }
                                    }
                                }
                                return true;
                            }
                        }).size();
                int index = items.indexOf(getControl());
                if (index < minmax[0]) {
                    return -1;
                } else if (index > minmax[1]) {
                    return 1;
                } else {
                    return 0;
                }
            }

            @Override
            public String toString() {
                return "'" + getControl() + "' state at index " + desiredIndex;
            }
        };
        c.to(direction);
    }

    @Override
    public <INTERFACE extends ControlInterface> boolean is(Class<INTERFACE> interfaceClass) {
        if(WindowElement.class.equals(interfaceClass)) {
            return true;
        }
        return super.is(interfaceClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if(WindowElement.class.equals(interfaceClass) && Scene.class.equals(type)) {
            return true;
        }
        if(Parent.class.equals(interfaceClass) && Node.class.equals(type)) {
            return cellWrap().is(interfaceClass, type);
        }
        return super.is(interfaceClass, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> interfaceClass) {
        if(WindowElement.class.equals(interfaceClass)) {
            return (INTERFACE) tableViewWrap.as(interfaceClass);
        }
        return super.as(interfaceClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if(WindowElement.class.equals(interfaceClass) && Scene.class.equals(type)) {
            return (INTERFACE) tableViewWrap.as(interfaceClass, type);
        }
        if(Parent.class.equals(interfaceClass) && Node.class.equals(type)) {
            return cellWrap().as(interfaceClass, type);
        }
        return super.as(interfaceClass, type);
    }

    public static class TableListItemByObjectLookup<ITEM> implements LookupCriteria<TableCell> {

        private final ITEM item;

        public TableListItemByObjectLookup(ITEM item) {
            this.item = item;
        }

        @Override
        public boolean check(TableCell control) {
            if (control.isVisible() && control.getOpacity() == 1.0) {
                if ((control.getItem() != null) && control.getItem().equals(item)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Looking for a visible listCell with the value '" + item + "'";
        }
    }
}
