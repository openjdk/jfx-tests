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
import org.jemmy.control.ControlType;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.EditableCellOwner.EditableCell;
import org.jemmy.interfaces.Show;
import org.jemmy.interfaces.Showable;

/**
 *
 * @author shura
 */
@ControlType(Object.class)
public abstract class ItemWrap<ITEM extends Object> extends Wrap<ITEM> implements Showable, Show, EditableCell<ITEM> {

    protected Wrap<?> viewWrap;
    protected CellEditor<? super ITEM> editor;

    /**
     *
     * @param env
     * @param item
     * @param viewWrap
     */
    public ItemWrap(ITEM item, Wrap<?> listViewWrap, CellEditor<? super ITEM> editor) {
        super(listViewWrap.getEnvironment(), item);
        this.viewWrap = listViewWrap;
        this.editor = editor;
    }

    public void edit(ITEM newValue) {
        as(Showable.class).shower().show();
        editor.edit(this, newValue);
    }

    public void select() {
        as(Showable.class).shower().show();
        mouse().click();
    }

    public Class getType() {
        //this is only needed for editing where type is not really sticking
        //outside anywhere
        return Object.class;
    }
    
    protected abstract Wrap<? extends Node> cellWrap();

}
