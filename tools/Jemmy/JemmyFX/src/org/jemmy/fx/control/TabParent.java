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

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.jemmy.action.FutureAction;
import org.jemmy.control.Wrap;
import org.jemmy.control.Wrapper;
import org.jemmy.interfaces.CellOwner;
import org.jemmy.interfaces.Keyboard.KeyboardModifier;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Mouse.MouseButtons;
import org.jemmy.interfaces.Showable;
import org.jemmy.lookup.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TabParent<ITEM extends Tab> extends AbstractParent<ITEM>
        implements CellOwner<ITEM>, ControlList {

    public static final String SELECTED_ITEM_PROP_NAME = "selectedItem";
    protected Wrapper wrapper;
    protected Class<ITEM> itemClass;
    protected Scene scene;
    TabPaneWrap<? extends TabPane> wrap;

    public TabParent(TabPaneWrap<? extends TabPane> tabPaneOp, Class<ITEM> itemClass) {
        this.wrap = tabPaneOp;
        this.wrapper = new ItemWrapper<ITEM>(tabPaneOp);
        this.itemClass = itemClass;
    }

    public Wrapper getWrapper() {
        return wrapper;
    }

    @Override
    public <ST extends ITEM> Lookup<ST> lookup(Class<ST> controlClass, LookupCriteria<ST> criteria) {
        return new PlainLookup<>(wrap.getEnvironment(),
                this, wrapper, controlClass, criteria);
    }

    @Override
    public Lookup<ITEM> lookup(LookupCriteria<ITEM> criteria) {
        return this.lookup(itemClass, criteria);
    }

    @Override
    public Class<ITEM> getType() {
        return itemClass;
    }

    public List<Wrap<? extends ITEM>> select(LookupCriteria<ITEM>... criteria) {
        List<Wrap<? extends ITEM>> res = new ArrayList<>();
        KeyboardModifier[] mods = new KeyboardModifier[0];
        for (LookupCriteria<ITEM> cr : criteria) {
            Lookup<ITEM> lu = lookup(cr);
            for (int j = 0; j < lu.size(); j++) {
                Wrap<? extends ITEM> w = lu.wrap(j);
                w.as(Showable.class).shower().show();
                w.mouse().click(1, w.getClickPoint(), MouseButtons.BUTTON1,
                        mods);
                mods = new KeyboardModifier[]{KeyboardModifiers.CTRL_DOWN_MASK};
                res.add(w);
            }
        }
        return res;
    }

    protected static class ItemWrapper<ITEM extends Tab> implements Wrapper {

        TabPaneWrap<? extends TabPane> tabPaneOp;

        public ItemWrapper(TabPaneWrap<? extends TabPane> tabPaneOp) {
            this.tabPaneOp = tabPaneOp;
        }

        @Override
        public <T> Wrap<? extends T> wrap(Class<T> controlClass, T control) {
            if (controlClass.isAssignableFrom(Tab.class)) {
                return (Wrap<? extends T>) new TabWrap(tabPaneOp, (ITEM) control);
            }
            return null;
        }

    }

    @Override
    public List<ITEM> getControls() {
        return new FutureAction<>(wrap.getEnvironment(), () ->
                wrap.getControl().getTabs().stream().map(item -> (ITEM) item).collect(Collectors.toList())
        ).get();
    }

}