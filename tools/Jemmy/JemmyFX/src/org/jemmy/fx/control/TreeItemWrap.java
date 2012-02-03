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
