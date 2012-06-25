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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Wrap;
import org.jemmy.dock.DockInfo;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.fx.NodeWrap;
import org.jemmy.fx.WindowElement;
import org.jemmy.interfaces.Caret.Direction;
import org.jemmy.interfaces.EditableCellOwner.EditableCell;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.LookupCriteria;


/**
 * Wrapper for item classes held in ListCell children of ListView. It implements Showable to be able
 * moving hidden Cells to visible area via Show.show(). That can be needed eg. for clicking the cell with mouse.
 *
 * The class is ported from ListCellOperator in the project JemmyFX.
 * @param <ITEM>
 * @author KAM, shura
 */

@ControlType(Object.class)
@ControlInterfaces({EditableCell.class, Showable.class})
@DockInfo(name="org.jemmy.fx.control.ListItemDock")
public class ListItemWrap<ITEM extends Object> extends ItemWrap<ITEM> implements Showable {

    private ListViewWrap<? extends ListView> listViewWrap;
    private int index = -1;
    
    /**
     *
     * @param env
     * @param item
     * @param listViewWrap
     */
    public ListItemWrap(ITEM item, int index, ListViewWrap<? extends ListView> listViewWrap, CellEditor<? super ITEM> editor) {
        super(item, listViewWrap, editor);
        this.listViewWrap = listViewWrap;
        this.index = index;
    }

    int getIndex() {
        return index;
    }

    /**
     * This method finds listCell for the selected item. Should be invoked only
     * using FX.deferAction()
     * That can be needed for cases like obtaining screenBounds for corresponding ListCell.
     */
    @Override
    protected Wrap<? extends ListCell> cellWrap() {
        return listViewWrap.as(Parent.class, Node.class).lookup(ListCell.class,
          new ListItemByObjectLookup<ITEM>(getControl())).wrap(0);
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
        final List<ITEM> items = listViewWrap.getItems();

        final long desiredIndex = new GetAction<Long>() {

            @Override
            public void run(Object... parameters) {
                int len=items.size();
                
                for (int i=0; i<len; i++) {
                    if (getControl().equals(items.get(i))) {
                        setResult((long)i);
                        return;
                    }
                }
            }
            
        }.dispatch(getEnvironment());

        Wrap<? extends ScrollBar> scr =listViewWrap.as(Parent.class, Node.class).
                lookup(ScrollBar.class, new ScrollBarWrap.ByOrientationScrollBar(true)).
                wrap();

        if (!scr.getProperty(Boolean.class, "isVisible")) {
            return;
        }

        Caret c = scr.as(Scroll.class).caret();

        Direction direction = new Direction() {

            /**
             * @return < 0 to scroll toward decreasing value, > 0 - vice versa
             * 0 to stop scrolling
             * NOTE - see implementation KnobDragScrollerImpl.to(Direction) which is used in ScrollBarWrap
             *        better to return constant values (-1 || 0 || +1) to get smooth dragging
             */
            @Override
            public int to() {
                final int[] minmax = new int[]{listViewWrap.as(Selectable.class, Object.class).getStates().size(), -1}; //minmax[0] - the least index of visible element. minmax[1] - the most index. 
                final List items = listViewWrap.getItems();
                listViewWrap.as(Parent.class, Node.class).lookup(ListCell.class,
                        new LookupCriteria<ListCell>() {

                            public boolean check(ListCell control) {
                                if (NodeWrap.isInside(listViewWrap.getControl(), control, getEnvironment())) {
                                    int index = items.indexOf(control.getItem());
                                    if (index >= 0) {
                                        if (index < minmax[0]) {
                                            minmax[0] = index;
                                        //} else if (index > minmax[1]) {//doesn't work, if we have 1 element in list. So rewrite:
                                        }
                                        if (index > minmax[1]) {
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

    /**
     * Identifies which elements are shown in the list currently.
     * @return list of indices of all elements that are fully visible in the
     * list.
     */
    List<Long> shown() {
        final ListView lv = (ListView) listViewWrap.getControl();
        final Bounds viewArea = lv.getBoundsInLocal();

        List<Long> res = new GetAction<List<Long>>() {

            @Override
            @SuppressWarnings("unchecked")
            public void run(Object... parameters) {
                final List<Long> res = new LinkedList<Long>();

                listViewWrap.as(Parent.class, Node.class).lookup(
                                     ListCell.class, new LookupCriteria<ListCell>() {

                    @Override
                    public boolean check(ListCell control) {
                        if (control.isVisible() && control.getOpacity() == 1.0 ) {
                            Bounds controlBounds = lv.sceneToLocal(
                                    control.localToScene(control.getBoundsInLocal()));
                            if (viewArea.contains(controlBounds)) {
                                Long index = new Long(control.getIndex());
                                    res.add(index);
                                    return false;
                                }
                            }
                        return false;
                    }
                }).size();

                setResult(res);
            }
        }.dispatch(getEnvironment());

        Collections.sort(res);
        return res;
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
            return (INTERFACE) listViewWrap.as(interfaceClass);
        }
        return super.as(interfaceClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if(WindowElement.class.equals(interfaceClass) && Scene.class.equals(type)) {
            return (INTERFACE) listViewWrap.as(interfaceClass, type);
        }
        if(Parent.class.equals(interfaceClass) && Node.class.equals(type)) {
            return cellWrap().as(interfaceClass, type);
        }
        return super.as(interfaceClass, type);
    }

    public static class ListItemByObjectLookup<ITEM> implements LookupCriteria<ListCell> {

        private final ITEM item;

        public ListItemByObjectLookup(ITEM item) {
            this.item = item;
        }

        @Override
        public boolean check(ListCell control) {
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
