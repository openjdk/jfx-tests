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

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import org.jemmy.JemmyException;
import org.jemmy.control.Wrap;
import org.jemmy.control.Wrapper;
import org.jemmy.lookup.*;

import java.util.Collections;
import java.util.List;

abstract class MenuItemParent extends AbstractParent<MenuItem> {

    private final Wrap wrap;
    private final Wrapper itemWrapper;
    private final ControlHierarchy items;

    protected MenuItemParent(Wrap wrap) {
        this.wrap = wrap;
        itemWrapper = new ItemWrapper(wrap);
        items = new MenuHierarchy();
    }

    public <ST extends MenuItem> Lookup<ST> lookup(Class<ST> type, LookupCriteria<ST> lc) {
        return new HierarchyLookup<ST>(wrap.getEnvironment(), items, itemWrapper, type, lc);
    }

    public Lookup<MenuItem> lookup(LookupCriteria<MenuItem> lc) {
        return lookup(MenuItem.class, lc);
    }

    public Class<MenuItem> getType() {
        return MenuItem.class;
    }

    protected abstract List<?> getControls();

    private static class ItemWrapper implements Wrapper {

        protected Wrap wrap;

        public ItemWrapper(Wrap wrap) {
            this.wrap = wrap;
        }

        @Override
        public <T> Wrap<? extends T> wrap(Class<T> controlClass, T control) {
            if (MenuItem.class.isInstance(control)) {
                if (Menu.class.isInstance(control)) {
                    return (Wrap<? extends T>)
                            new MenuWrap<>(wrap.getEnvironment(), Menu.class.cast(control));
                } else if (RadioMenuItem.class.isInstance(control)) {
                    return (Wrap<? extends T>)
                            new RadioMenuItemWrap<>(wrap.getEnvironment(), RadioMenuItem.class.cast(control));
                } else if (CheckMenuItem.class.isInstance(control)) {
                    return (Wrap<? extends T>)
                            new CheckMenuItemWrap<>(wrap.getEnvironment(), CheckMenuItem.class.cast(control));
                } else {
                    return (Wrap<? extends T>) new MenuItemWrap(wrap.getEnvironment(), MenuItem.class.cast(control));
                }
            }
            throw new JemmyException("Unexpected control class is used: " + controlClass);
        }
    }

    private class MenuHierarchy implements ControlHierarchy {

        public List<?> getChildren(Object o) {
            if(o instanceof Menu) {
                return Menu.class.cast(o).getItems();
            } else {
                return Collections.emptyList();
            }
        }

        public Object getParent(Object o) {
            return MenuItem.class.cast(o).getParentMenu();
        }

        public List<?> getControls() {
            return MenuItemParent.this.getControls();
        }

    }
}
