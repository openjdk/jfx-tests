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

import java.util.List;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.jemmy.action.GetAction;
import org.jemmy.control.As;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.MethodProperties;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.input.StringMenuOwner;
import org.jemmy.interfaces.Parent;

@ControlType({Menu.class})
@ControlInterfaces(value = {Parent.class, StringMenuOwner.class},
        encapsulates = {MenuItem.class, MenuItem.class}, name={"asMenuParent"})
public class MenuWrap<ITEM extends Menu> extends MenuItemWrap<ITEM> {

    private StringMenuOwnerImpl menuOwner = null;
    private Parent<MenuItem> parent = null;

    /**
     *
     * @param env
     * @param scene
     * @param nd
     */
    @SuppressWarnings("unchecked")
    public MenuWrap(Environment env, ITEM item) {
        super(env, item);
    }

    @As(MenuItem.class)
    public Parent<MenuItem> asMenuParent() {
        if (parent == null) {
            parent = new MenuItemParent(this) {

                @Override
                protected List getControls() {
                    return new GetAction<List<?>>() {

                        @Override
                        public void run(Object... os) throws Exception {
                            setResult(getControl().getItems());
                        }
                    }.dispatch(getEnvironment());
                }
            };
        }
        return parent;
    }

    @As(MenuItem.class)
    public StringMenuOwner<MenuItem> asMenuOwner() {
        if(menuOwner == null) {
             menuOwner = new StringMenuOwnerImpl(this, this.as(Parent.class, Menu.class));
        }
        return menuOwner;
    }
    
    @Property("isShowing")
    public boolean isShowing() {
        return isShowing(getControl(), getEnvironment());
    }
    
    static boolean isShowing(final Menu menu, Environment env) {
        return new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(menu.isShowing());
            }
        }.dispatch(env);
    }
}