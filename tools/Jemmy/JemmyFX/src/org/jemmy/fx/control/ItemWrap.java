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

import javafx.scene.Node;
import org.jemmy.JemmyException;
import org.jemmy.Rectangle;
import org.jemmy.control.As;
import org.jemmy.control.ControlType;
import org.jemmy.control.Wrap;
import org.jemmy.dock.DockInfo;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.EditableCellOwner.EditableCell;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Show;
import org.jemmy.interfaces.Showable;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.timing.DescriptiveLookupCriteria;

/**
 * A few compound Java FX controls could be used as a parent for objects, which
 * are actually contained within the control. This associates the data with
 * corresponding nodes so that you could do all the things with the data which
 * you usually do with nodes. Such as clicking on, using as a parent for nodes,
 * etc.
 *
 * @author shura
 */
@ControlType(Object.class)
@DockInfo(name = "org.jemmy.fx.control.ItemDock", multipleCriteria = false)
public abstract class ItemWrap<DATA extends Object> extends Wrap<DATA> implements Showable, Show, EditableCell<DATA> {

    public static final String ITEM_PROP_NAME = "item";

    @ObjectLookup("object")
    public static <T> LookupCriteria<T> byObject(Class<T> type, final Object data) {
        return new DescriptiveLookupCriteria<>(control -> control.equals(data), () -> "an element which is equals to " + data.toString());
    }

    @ObjectLookup("toString and comparison policy")
    public static <T> LookupCriteria<T> byToString(Class<T> type, final String data,
                                                   final StringComparePolicy policy) {
        return new DescriptiveLookupCriteria<>(control -> policy.compare(data, control.toString()), () -> "an element which is equals to " + data.toString());
    }

    protected Wrap<?> viewWrap;
    protected CellEditor<? super DATA> editor;
    private Class<DATA> dataClass;
    private final Object item;

    /**
     * @param item
     * @param data
     * @param editor
     */
    public ItemWrap(Object item, DATA data, Wrap<?> listViewWrap, CellEditor<? super DATA> editor) {
        super(listViewWrap.getEnvironment(), data);
        this.viewWrap = listViewWrap;
        this.editor = editor;
        this.item = item;
    }

    public ItemWrap(Class<DATA> dataClass, Object item, DATA data, Wrap<?> listViewWrap, CellEditor<? super DATA> editor) {
        this(item, data, listViewWrap, editor);
        this.dataClass = dataClass;
    }

    public Object getItem() {
        return item;
    }

    @Override
    public void edit(DATA newValue) {
        if (editor == null) {
            throw new JemmyException("Editor is null for <" + toString() + ">.");
        } else {
            as(Showable.class).shower().show();
            editor.edit(this, newValue);
        }
    }

    @Override
    public void select() {
        as(Showable.class).shower().show();
        mouse().click();
    }

    @Override
    public Class getType() {
        //Object.class - this is only needed for editing where type is not
        //really sticking outside anywhere
        return dataClass == null ? Object.class : dataClass;
    }

    /**
     * There is a node associated with every piece of data. This node could serve
     * as a parent.
     *
     * @return
     * @see #cellWrap()
     */
    @As(Node.class)
    public Parent<Node> asNodeParent() {
        return cellWrap().as(Parent.class, Node.class);
    }

    @Override
    public Show shower() {
        return cellWrap().as(Showable.class).shower();
    }

    @Override
    public void show() {
        cellWrap().as(Showable.class).shower().show();
    }

    @Override
    public Rectangle getScreenBounds() {
        return cellWrap().getScreenBounds();
    }

    /**
     * There is a node associated with every piece of data.
     *
     * @return
     * @see #asNodeParent()
     */
    public abstract Wrap<? extends Node> cellWrap();

}
