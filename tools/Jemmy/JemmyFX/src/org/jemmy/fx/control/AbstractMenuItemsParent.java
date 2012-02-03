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
import org.jemmy.JemmyException;
import org.jemmy.control.Wrap;
import org.jemmy.control.Wrapper;

public abstract class AbstractMenuItemsParent<ITEM extends MenuItem> extends AbstractItemsParent<ITEM> {

    public AbstractMenuItemsParent(Wrap wrap, Class<ITEM> itemClass) {
        super(wrap, new ItemWrapper(wrap), itemClass);
    }

    protected static class ItemWrapper<ITEM extends MenuItem> implements Wrapper {
        protected Wrap wrap;

        public ItemWrapper(Wrap wrap) {
            this.wrap = wrap;
        }

        @Override
        public <T> Wrap<? extends T> wrap(Class<T> controlClass, T control) {
            if (MenuItem.class.isAssignableFrom(controlClass)) {
                if (Menu.class.isAssignableFrom(control.getClass())) {
                    return (Wrap<? extends T>) new MenuWrap<Menu>((Menu)control, wrap);
                }
                return (Wrap<? extends T>) new MenuItemWrap<ITEM>((ITEM)control, wrap);
            }
            throw new JemmyException("Unexpected control class is used: " + controlClass);
        }
    }
}
