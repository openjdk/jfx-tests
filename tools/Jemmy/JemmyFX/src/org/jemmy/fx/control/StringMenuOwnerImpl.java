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

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.jemmy.control.Wrap;
import org.jemmy.input.StringMenuOwner;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

/**
 *
 * @author shura
 */
class StringMenuOwnerImpl extends StringMenuOwner<MenuItem> {

    private final Parent<Menu> parent;
    public StringMenuOwnerImpl(Wrap<?> wrap, Parent<Menu> parent) {
        super(wrap);
        this.parent = parent;
    }

    @Override
    protected LookupCriteria<MenuItem> createCriteria(String string, StringComparePolicy compare_policy) {
        return new ByTextMenuItem(string, compare_policy);
    }

    public Class<MenuItem> getType() {
        return MenuItem.class;
    }

    public org.jemmy.interfaces.Menu menu() {
        return new MenuImpl(parent);
    }

    protected void prepare() {
    }

    class MenuImpl extends MenuTreeSelectorImpl implements org.jemmy.interfaces.Menu {
        public MenuImpl(Parent<Menu> parent) {
            super(parent);
        }

        public void push(LookupCriteria... criteria) {
            prepare();
            select(criteria).mouse().click();
        }
    }
}
