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

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.control.Wrapper;
import org.jemmy.interfaces.CellOwner;
import org.jemmy.interfaces.Keyboard.KeyboardModifier;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Mouse.MouseButtons;
import org.jemmy.interfaces.Showable;
import org.jemmy.lookup.*;

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
        return new PlainLookup<ST>(wrap.getEnvironment(),
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
        List<Wrap<? extends ITEM>> res = new ArrayList<Wrap<? extends ITEM>>();
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
        return new GetAction<List<ITEM>>() {

            @Override
            public void run(Object... parameters) throws Exception {
                List<ITEM> res = new ArrayList<ITEM>();
                for (Object item : wrap.getControl().getTabs()) {
                    res.add((ITEM) item);
                }
                setResult(res);
            }
        }.dispatch(wrap.getEnvironment());
    }

}