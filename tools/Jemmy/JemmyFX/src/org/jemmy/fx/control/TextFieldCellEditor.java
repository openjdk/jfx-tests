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
import javafx.scene.control.TextField;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.EditableCellOwner.CellEditor;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Showable;
import org.jemmy.interfaces.Text;

/**
 *
 * @author shura
 */
public class TextFieldCellEditor<ITEM> implements CellEditor<ITEM> {

    public static final Timeout BETWEEN_CLICKS_TIMEOUT = new Timeout("sleep.between.click", 1000);

    static {
        Root.ROOT.getEnvironment().setTimeout(BETWEEN_CLICKS_TIMEOUT);
    }

    public void edit(Wrap<? extends ITEM> item, ITEM newValue) {
        if(!item.is(Parent.class, Node.class)) {
            throw new IllegalStateException(item.getClass().getName() +
                    " would need to be a parent for editing");
        }
        item.as(Showable.class).shower().show();
        Parent<Node> parent = item.as(Parent.class, Node.class);
        initializeEditing(item);
        Wrap<? extends TextField> field = parent.lookup(TextField.class).wrap();
        field.as(Text.class).clear();
        field.as(Text.class).type(newValue.toString());
        field.keyboard().pushKey(KeyboardButtons.ENTER);
    }

    protected void initializeEditing(Wrap<?> cell) {
        cell.mouse().click();
        cell.getEnvironment().getTimeout(BETWEEN_CLICKS_TIMEOUT).sleep();
        cell.mouse().click();
    }

}
