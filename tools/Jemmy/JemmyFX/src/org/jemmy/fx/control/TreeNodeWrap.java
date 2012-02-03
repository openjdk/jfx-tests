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
import org.jemmy.interfaces.*;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.EditableCellOwner.EditableCell;
import org.jemmy.lookup.LookupCriteria;

/**
 * This wraps an object within the tree
 * @author barbashov, shura
 * @param <DATA> 
 */
@ControlType(Object.class)
@ControlInterfaces({org.jemmy.interfaces.TreeItem.class})
@DockInfo(name = "org.jemmy.fx.control.TreeNodeDock")
public class TreeNodeWrap<T extends javafx.scene.control.TreeItem> extends Wrap<T> 
    implements Showable, Show, EditableCell<Object> {

    public static final String EXPANDED_PROP_NAME = "expanded";
    public static final String LEAF_PROP_NAME = "leaf";

    private TreeViewWrap<? extends TreeView> treeViewWrap;
    private CellEditor<Object> editor;

    public TreeNodeWrap(T item,
            TreeViewWrap<? extends TreeView> treeViewWrap,
            CellEditor<Object> editor) {
        super(treeViewWrap.getEnvironment(), item);
        this.treeViewWrap = treeViewWrap;
        this.editor = editor;
    }
    
    TreeViewWrap<? extends TreeView> getViewWrap() {
        return treeViewWrap;
    }

    public TreeViewWrap<? extends TreeView> tree() {
        return treeViewWrap;
    }

    @Override
    public Rectangle getScreenBounds() {
        return new GetAction<Rectangle>() {

            @Override
            public void run(Object... parameters) {
                TreeCell treeCell = treeViewWrap.getTreeCell(getControl());
                if (treeCell != null) {
                    setResult(NodeWrap.getScreenBounds(getEnvironment(), treeCell));
                }
            }
        }.dispatch(getEnvironment());
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
            return (INTERFACE) Root.ROOT.getThemeFactory().treeItem(this);
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
            return (INTERFACE) Root.ROOT.getThemeFactory().treeItem(this);
        }
        if (Parent.class.equals(interfaceClass) && Node.class.equals(type)) {
            return getNode().as(interfaceClass, type);
        }
        return super.as(interfaceClass, type);
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
        System.out.println("Showing " + getControl().getValue());
        treeViewWrap.as(Scroll.class).caret().to(new Direction() {

            public int to() {
                final int[] minmax = new int[]{treeViewWrap.getStates().size(), -1};
                treeViewWrap.as(Parent.class, Node.class).lookup(TreeCell.class,
                        new LookupCriteria<TreeCell>() {

                            public boolean check(TreeCell control) {
                                if (NodeWrap.isInside(treeViewWrap.getControl(), control, getEnvironment())) {
                                    int index = treeViewWrap.getRow(control.getTreeItem());
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
                int index = treeViewWrap.getRow(getControl());
                if (index < minmax[0]) {
                    return -1;
                } else if (index > minmax[1]) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
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

    @Property(EXPANDED_PROP_NAME)
    public boolean isExpanded() {
        return new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().isExpanded());
            }
        }.dispatch(getEnvironment());
    }

    @Property(LEAF_PROP_NAME)
    public boolean isLeaf() {
        return new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().isLeaf());
            }
        }.dispatch(getEnvironment());
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
