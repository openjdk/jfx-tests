/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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

package javafx.scene.control.test;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Mouse;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;

public abstract class MenuTestBase extends ControlsTestBase {
    static Wrap<? extends Scene> scene;
    static Parent<Node> sceneAsParent;
    static Wrap contentPane;
    static Wrap<? extends Node> container;
    static Wrap<? extends Object> object;
    static Wrap<? extends Label> check;

    protected void keyboardSelectionCheck(KeyboardButtons key) throws InterruptedException {
        final Lookup<MenuItem> lookup = upperLevelLookup();
        for (int i = 0; i < lookup.size(); i++) {
            final int index = i;
            keyboardNavigate(key, i, false);
            container.keyboard().pushKey(KeyboardButtons.ENTER);
            container.waitState(new State() {
                public Object reached() {
                    return check.getProperty(String.class, Wrap.TEXT_PROP_NAME).contentEquals(lookup.get(index).getText()) ? Boolean.TRUE : null;
                }
            });
        }
    }

    protected final Lookup<MenuItem> upperLevelLookup() {
        return object.as(Parent.class, MenuItem.class).lookup(new LookupCriteria<MenuItem>() {
            public boolean check(MenuItem control) {
                return control.getParentMenu() == null;
            }
        });
    }

    protected void hoverCycle() throws Throwable {
        Lookup<MenuItem> lookup = upperLevelLookup();
        for (int i = 0; i < lookup.size(); i++) {
            lookup.wrap(i).mouse().move();
            Thread.sleep(300);
            checkScreenshot(getClass().getSimpleName() + "-hoverTest-" + i, contentPane);
        }
        throwScreenshotError();
    }

    protected void keyboardHoverCycle(KeyboardButtons key, boolean up) throws Throwable {
        for (int i = 0; i < object.as(Parent.class, MenuItem.class).lookup().size(); i++) {
            keyboardNavigate(key, i, up);
            checkScreenshot(getClass().getSimpleName() + "-keyboardHoverTest-" + i, contentPane);
        }
        throwScreenshotError();
    }

    protected void keyboardNavigate(KeyboardButtons key, int index, boolean up) throws InterruptedException {
        expandByKeyboard(key);
        for (int j = 0; j <= (up ? (object.as(Parent.class, MenuItem.class).lookup().size() - index - 1) : index); j++) {
            container.keyboard().pushKey(up ? KeyboardButtons.UP : KeyboardButtons.DOWN);
            Mouse.CLICK.sleep();
        }
    }

    protected void collapseByKeyboard() {
        container.keyboard().pushKey(KeyboardButtons.ESCAPE);
        checkShown(false);
    }

    protected void expandByKeyboard(KeyboardButtons key) {
        if (isShown()) {
            collapseByKeyboard();
        }
        container.keyboard().pushKey(key);
        checkShown(true);
    }

    protected void focus() {
        int attempt = 0;
        while (!isFocused() && attempt++ < sceneAsParent.lookup().size()) {
            scene.keyboard().pushKey(KeyboardButtons.TAB);
        }
    }

    protected boolean isFocused() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) {
                setResult(container.getControl().isFocused() ||
                    (container.as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
                        public boolean check(Node control) {
                            return control.isFocused();
                        }
                    }).size() > 0));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected abstract boolean isShown();

    protected abstract void checkShown(final boolean shown);
}
