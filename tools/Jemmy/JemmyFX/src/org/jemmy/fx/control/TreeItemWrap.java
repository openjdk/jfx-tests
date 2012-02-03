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
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.dock.DockInfo;
import org.jemmy.fx.NodeWrap;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Caret.Direction;
import org.jemmy.interfaces.EditableCellOwner.EditableCell;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.LookupCriteria;

/**
 * This wraps an object within the tree
 * @author barbashov, shura
 * @param <DATA> 
 */
@ControlType(Object.class)
@ControlInterfaces({org.jemmy.interfaces.TreeItem.class, EditableCell.class})
@DockInfo(name = "org.jemmy.fx.control.TreeItemDock")
public class TreeItemWrap<DATA> extends ItemWrap<DATA> implements EditableCell<DATA> {

    private Class<DATA> dataClass;
    private TreeNodeWrap<TreeItem> theWrap;
    /**
     *
     * @param env
     * @param item
     * @param TreeViewWrap
     */
    TreeItemWrap(Class<DATA> dataClass, TreeItem<DATA> item,
            TreeViewWrap<? extends TreeView> treeViewWrap,
            CellEditor<? super DATA> editor) {
        super(item.getValue(), treeViewWrap, editor);
        this.dataClass = dataClass;
        theWrap = new TreeNodeWrap(item, treeViewWrap, editor);
    }

    public TreeItem getItem() {
        return theWrap.getControl();
    }

    public Class<DATA> getDataClass() {
        return dataClass;
    }

    public TreeViewWrap<? extends TreeView> tree() {
        return theWrap.getViewWrap();
    }

    @Override
    public Rectangle getScreenBounds() {
        return theWrap.getScreenBounds();
    }

    @Override
    public <INTERFACE extends ControlInterface> boolean is(Class<INTERFACE> interfaceClass) {
        if (org.jemmy.interfaces.TreeItem.class.equals(interfaceClass)) {
            return true;
        }
        return super.is(interfaceClass);
    }

    @Override
    public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> interfaceClass) {
        if (org.jemmy.interfaces.TreeItem.class.equals(interfaceClass)) {
            return (INTERFACE) Root.ROOT.getThemeFactory().treeItem(theWrap);
        }
        return super.as(interfaceClass);
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (org.jemmy.interfaces.TreeItem.class.equals(interfaceClass)) {
            return true;
        }
        if (Parent.class.equals(interfaceClass) && Node.class.equals(type)) {
            return true;
        }
        return super.is(interfaceClass, type);
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (org.jemmy.interfaces.TreeItem.class.equals(interfaceClass)) {
            return (INTERFACE) Root.ROOT.getThemeFactory().treeItem(theWrap);
        }
        if (Parent.class.equals(interfaceClass) && Node.class.equals(type)) {
            return theWrap.as(interfaceClass, type);
        }
        return super.as(interfaceClass, type);
    }

    public final Wrap<? extends Node> getNode() {
        return theWrap.getNode();
    }

    @Override
    public Show shower() {
        return theWrap.shower();
    }

    @Property(TreeNodeWrap.EXPANDED_PROP_NAME)
    public boolean isExpanded() {
        return theWrap.isExpanded();
    }

    @Property(TreeNodeWrap.LEAF_PROP_NAME)
    public boolean isLeaf() {
        return theWrap.isLeaf();
    }

    @Override
    public void edit(DATA newValue) {
        editor.edit(this, newValue);
    }

    @Override
    public Class<DATA> getType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Wrap<? extends Node> cellWrap() {
        return getNode();
    }

    public void show() {
        theWrap.show();
    }
}
