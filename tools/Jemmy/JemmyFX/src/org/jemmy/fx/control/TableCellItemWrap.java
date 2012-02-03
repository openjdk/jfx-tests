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
