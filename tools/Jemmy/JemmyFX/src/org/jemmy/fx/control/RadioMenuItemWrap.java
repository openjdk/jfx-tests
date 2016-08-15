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

import javafx.scene.control.RadioMenuItem;
import org.jemmy.control.*;
import org.jemmy.env.Environment;
import org.jemmy.interfaces.Selectable;

/**
 * This is a menu item and so could be found within the menu hierarchy. It could
 * also be selected/unselected.
 * Please consult <a href="../../samples/menu">samples</a> for more info.
 * @see MenuBarWrap
 * @author shura
 */
@ControlType(RadioMenuItem.class)
@MethodProperties(MenuItemWrap.IS_SELECTED_PROP_NAME)
@ControlInterfaces(value=Selectable.class, encapsulates=Boolean.class)
public class RadioMenuItemWrap<T extends RadioMenuItem>  extends MenuItemWrap<T> {
    private final SelectableImpl selectable = new SelectableImpl();

    public RadioMenuItemWrap(Environment env, T item) {
        super(env, item);
    }

    @As(Boolean.class)
    public Selectable<Boolean> asSelectable() {
        return selectable;
    }
}
