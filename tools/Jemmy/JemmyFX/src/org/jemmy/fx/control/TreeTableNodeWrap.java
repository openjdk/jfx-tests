/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.As;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.fx.NodeWrap;
import org.jemmy.interfaces.EditableCellOwner;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Scroll;
import org.jemmy.interfaces.Show;
import org.jemmy.interfaces.Showable;
import org.jemmy.lookup.LookupCriteria;

/**
 * This wraps a node that represents the treeTables's data item
 * representation - TreeTableRow.
 *
 * @author Alexander Kirov
 */
@ControlType(Object.class)
@ControlInterfaces({org.jemmy.interfaces.TreeItem.class})
public class TreeTableNodeWrap<T extends TreeItem> extends Wrap<T>
        implements Showable, Show, EditableCellOwner.EditableCell<Object>, org.jemmy.interfaces.TreeItem<Object> {

    private CellEditor<Object> editor;
    private TreeTableItemWrap itemWrap;
    private TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap;

    public TreeTableNodeWrap(T item, TreeTableItemWrap itemWrap,
            TreeTableViewWrap<? extends TreeTableView> treeTableViewWrap,
            CellEditor<Object> editor) {
        super(treeTableViewWrap.getEnvironment(), item);
        this.itemWrap = itemWrap;
        this.treeTableViewWrap = treeTableViewWrap;
        this.editor = editor;
    }

    @Override
    public Rectangle getScreenBounds() {
        return new GetAction<Rectangle>() {

            @Override
            public void run(Object... parameters) {
                setResult(NodeWrap.getScreenBounds(getEnvironment(), getNode().getControl()));
            }
        }.dispatch(getEnvironment());
    }

    @Override
    public Point getClickPoint() {
        return TableUtils.getTableRowClickPoint(treeTableViewWrap, this);
    }

    @As(Node.class)
    public Parent<Node> asParent() {
        return getNode().as(Parent.class, Node.class);
    }

    public final Wrap<? extends Node> getNode() {
        return treeTableViewWrap.as(Parent.class, Node.class).
                lookup(TreeTableRow.class, new LookupCriteria<TreeTableRow>() {

            @Override
            public boolean check(TreeTableRow cntrl) {
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
        final TreeItem parent = new GetAction<TreeItem>() {

            @Override
            public void run(Object... parameters) throws Exception {
                setResult(getControl().getParent());
            }
        }.dispatch(getEnvironment());
        if (parent != null && !new GetAction<Boolean>() {

            @Override
            public void run(Object... parameters) throws Exception {
                setResult(parent.isExpanded());
            }
        }.dispatch(getEnvironment())) {
            new TreeTableItemWrap<TreeItem>(TreeItem.class, parent, treeTableViewWrap, null).expand();
        }
        scrollTo();
    }

    private void scrollTo() {
        TableUtils.scrollToInSingleDimension(treeTableViewWrap, TreeTableRow.class, (TreeTableRow p) -> treeTableViewWrap.getRow(p.getTreeItem()), treeTableViewWrap.getRow(getControl()),
                treeTableViewWrap.as(Scroll.class).caret(), true);
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
        return new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().isExpanded());
            }
        }.dispatch(getEnvironment());
    }

    @Property(TreeItemWrap.LEAF_PROP_NAME)
    public boolean isLeaf() {
        return new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().isLeaf());
            }
        }.dispatch(getEnvironment());
    }

    /**
     * Works with the first column. It's behavior is equivalent to behavior of
     * editing in treeView, for case, when treeTableView has only 1 column.
     */
    @Override
    public void edit(Object newValue) {
        new TreeTableCellWrap(treeTableViewWrap,
                treeTableViewWrap.getRow(getControl()),
                treeTableViewWrap.getColumn(0),
                editor).edit(newValue);
    }

    @Override
    public Class<Object> getType() {
        return Object.class;
    }

    @Override
    public void select() {
        as(Showable.class).shower().show();
        mouse().click();
    }

    @Override
    public void expand() {
        itemWrap.expand();
    }

    @Override
    public void collapse() {
        itemWrap.collapse();
    }
}