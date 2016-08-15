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
import javafx.util.Callback;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.FutureAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.fx.NodeWrap;
import org.jemmy.interfaces.*;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.EditableCellOwner.EditableCell;
import org.jemmy.lookup.LookupCriteria;
import static org.jemmy.fx.control.TableUtils.*;

/**
 * This wraps a node that renders the tree's data item
 *
 * @param <DATA>
 * @author barbashov, shura
 */
@ControlType(Object.class)
@ControlInterfaces({org.jemmy.interfaces.TreeItem.class})
public class TreeNodeWrap<T extends TreeItem> extends Wrap<T>
        implements Showable, Show, EditableCell<Object> {

    private TreeViewWrap<? extends TreeView> treeViewWrap;
    private CellEditor<Object> editor;

    public TreeNodeWrap(T item,
                        TreeViewWrap<? extends TreeView> treeViewWrap,
                        CellEditor<Object> editor) {
        super(treeViewWrap.getEnvironment(), item);
        this.treeViewWrap = treeViewWrap;
        this.editor = editor;
    }

    TreeViewWrap<? extends TreeView> getTreeViewWrap() {
        return treeViewWrap;
    }

    public TreeViewWrap<? extends TreeView> tree() {
        return treeViewWrap;
    }

    @Override
    public Rectangle getScreenBounds() {
        return new FutureAction<>(getEnvironment(), () -> {
            TreeCell treeCell = treeViewWrap.getTreeCell(getControl());
            if (treeCell != null) {
                return NodeWrap.getScreenBounds(getEnvironment(), treeCell);
            }
            return null;
        }).get();
    }

    @Override
    public Point getClickPoint() {
        return TableUtils.getClickPoint(treeViewWrap, this);
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
            return (INTERFACE) ThemeDriverFactory.getThemeFactory().treeItem(this, treeViewWrap);
        }
        return super.as(interfaceClass);
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Parent.class.equals(interfaceClass) && Node.class.equals(type)) {
            return true;
        }
        return super.is(interfaceClass, type);
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Parent.class.equals(interfaceClass) && Node.class.equals(type)) {
            return (INTERFACE) asParent();
        }
        return super.as(interfaceClass, type);
    }

    public Parent asParent() {
        return getNode().as(Parent.class, Node.class);
    }

    public final Wrap<? extends Node> getNode() {
        return treeViewWrap.as(Parent.class, Node.class).
                lookup(TreeCell.class, new LookupCriteria<TreeCell>() {

                    public boolean check(TreeCell cntrl) {
                        return cntrl.getItem() != null && cntrl.getTreeItem().equals(getControl());
                    }

                    @Override
                    public String toString() {
                        return "Node for an item " + getControl().toString();
                    }
                }).wrap();
    }

    @Override
    public Show shower() {
        return this;
    }

    @Override
    public void show() {
        final TreeItem parent = new FutureAction<>(getEnvironment(), () -> getControl().getParent()).get();
        if (parent != null && !new FutureAction<>(getEnvironment(), () -> parent.isExpanded()).get()) {
            new TreeItemWrap<>(TreeItem.class, parent, treeViewWrap, null).asTreeItem().expand();
        }
        scrollTo();
    }

    private Wrap<? extends javafx.scene.Parent> clippedContainer;

    private Wrap<? extends javafx.scene.Parent> getClippedContainerWrap() {
        if (clippedContainer == null) {
            clippedContainer = ((Parent<Node>) treeViewWrap.as(Parent.class, Node.class)).lookup(javafx.scene.Parent.class, control -> control.getClass().getName().endsWith("VirtualFlow$ClippedContainer")).wrap();
        }
        return clippedContainer;
    }

    private void scrollTo() {
        TableUtils.scrollToInSingleDimension(treeViewWrap, TreeCell.class,
                cell-> treeViewWrap.getRow(cell.getTreeItem()), treeViewWrap.getRow(getControl()),
        treeViewWrap.as(Scroll.class).caret(), true);
    }

    protected boolean isReallyVisible(Node node) {
        Node parent = node;
        while (parent != null) {
            if (parent.isVisible() == false) {
                return false;
            }
            parent = parent.getParent();
        }
        return true;
    }

    @Property(TreeItemWrap.EXPANDED_PROP_NAME)
    public boolean isExpanded() {
        return new FutureAction<>(getEnvironment(), () -> getControl().isExpanded()).get();
    }

    @Property(TreeItemWrap.LEAF_PROP_NAME)
    public boolean isLeaf() {
        return new FutureAction<>(getEnvironment(), () -> getControl().isLeaf()).get();
    }

    public void edit(Object newValue) {
        editor.edit(this, newValue);
    }

    public Class<Object> getType() {
        return Object.class;
    }

    public void select() {
        as(Showable.class).shower().show();
        mouse().click();
    }
}
