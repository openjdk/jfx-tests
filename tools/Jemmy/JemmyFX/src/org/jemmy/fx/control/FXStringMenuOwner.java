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
class FXStringMenuOwner extends StringMenuOwner<MenuItem> {

    Parent<Menu> parent;
    public FXStringMenuOwner(Wrap<?> wrap, Parent<Menu> parent) {
        super(wrap);
        this.parent = parent;
    }

    @Override
    protected LookupCriteria<MenuItem> createCriteria(String string, StringComparePolicy scp) {
        return new ByTextMenuItem(string, scp);
    }

    public Class<MenuItem> getType() {
        return MenuItem.class;
    }

    public org.jemmy.interfaces.Menu menu() {
        return new org.jemmy.interfaces.Menu() {

            public void push(LookupCriteria... lcs) {
                select(lcs).mouse().click();
            }

            public Wrap select(LookupCriteria... criteria) {
                if (criteria.length == 0) {
                    throw new IllegalStateException("criteria list supposed to be not empty");
                }
                Wrap<? extends MenuItem> res = findMenu(criteria[0]);
                if (criteria.length > 1) {
                    res = showMenu(res, decreaseCriteria(criteria));
                }
                res.mouse().move();
                return res;
            }

            private Wrap<? extends javafx.scene.control.Menu> findMenu(LookupCriteria<Menu> criteria) {
                return parent.lookup(criteria).wrap();
            }
        };
    }

    static Wrap<? extends MenuItem> showMenu(Wrap<? extends MenuItem> parent, LookupCriteria<MenuItem>... criteria) {
        if (criteria.length == 0) {
            throw new IllegalStateException("Length supposed to be greater than 0 at this moment");
        }
        parent.mouse().move();
        Parent<MenuItem> container = parent.as(Parent.class, MenuItem.class);
        Wrap<? extends MenuItem> next = container.lookup(criteria[0]).wrap();
        if (criteria.length == 1) {
            return next;
        } else {
            if (!(next.getControl() instanceof Menu)) {
                throw new IllegalStateException("Should be menu: " + next.getControl().toString());
            }
            return showMenu((Wrap<? extends Menu>) next, decreaseCriteria(criteria));
        }
    }

    static <T> LookupCriteria<T>[] decreaseCriteria(LookupCriteria<T>[] longer) {
        LookupCriteria[] res = new LookupCriteria[longer.length - 1];
        for (int i = 1; i < longer.length; i++) {
            res[i - 1] = longer[i];
        }
        return res;
    }
}
