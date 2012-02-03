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

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.TreeSelector;
import org.jemmy.lookup.LookupCriteria;

class MenuTreeSelectorImpl implements TreeSelector {
    protected Parent<Menu> parent;

    public MenuTreeSelectorImpl(Parent<Menu> parent) {
        this.parent = parent;
    }

    public Wrap select(LookupCriteria... criteria) {
        if (criteria.length == 0) {
            throw new IllegalStateException("The criteria list is supposed to be not empty");
        }
        Wrap<? extends MenuItem> res = parent.lookup(criteria[0]).wrap();
        if (criteria.length > 1) {
            res = showMenu(res, decreaseCriteria(criteria));
        }
        res.mouse().move();
        return res;
    }

    static Wrap<? extends MenuItem> showMenu(Wrap<? extends MenuItem> parent, LookupCriteria<MenuItem>... criteria) {
        if (criteria.length == 0) {
            throw new IllegalStateException("Length is supposed to be greater than 0 at this moment");
        }
        parent.mouse().click();
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
