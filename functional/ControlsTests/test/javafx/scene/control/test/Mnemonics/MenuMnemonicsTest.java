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
package javafx.scene.control.test.Mnemonics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.test.mix.MenuItemTest;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByText;
import org.jemmy.fx.control.MenuItemWrap;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.resources.StringComparePolicy;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

@RunWith(FilteredTestRunner.class)
public class MenuMnemonicsTest extends MnemonicsTestBase {

    @BeforeClass
    public static void setUpClass() throws Exception {
        MenuMnemonicsApp.main(null);
        MnemonicsTestBase.setUpClass();
    }

    @Test
    public void menuTest() throws Throwable {
        Wrap<? extends MenuBar> bar = sceneAsParent.lookup(MenuBar.class).wrap();
        Wrap<? extends Menu> menu0 = bar.as(Parent.class, Menu.class).lookup(new LevelLookup<Menu>(0, 0)).wrap();
        Wrap<? extends Menu> item1 = menu0.as(Parent.class, Menu.class).lookup(new LevelLookup<Menu>(1, 0)).wrap();
        Wrap<? extends Menu> item2 = item1.as(Parent.class, Menu.class).lookup(new LevelLookup<Menu>(2, 0)).wrap();
        Wrap<? extends Node> menu_node0 = getNode(menu0);
        KeyboardButton menu0_button = getMenuButton(menu0);
        KeyboardButton item1_button = getMenuButton(item1);
        KeyboardButton item2_button = getMenuButton(item2);
        removeFocus(bar);
        checkUnderline(menu_node0, false);
        scene.keyboard().pushKey(menu0_button, mod);
        MenuItemTest.checkExpanded(menu0, false);
        MenuItemTest.expand(menu0, true);
        checkUnderline(getNode(item1), false);
        scene.keyboard().pushKey(item1_button, mod);
        MenuItemTest.checkExpanded(item1, false);
        MenuItemTest.expand(item1, true);
        checkUnderline(getNode(item2), false);

        scene.mouse().click();
        try {
            if (isLinux) {
                scene.keyboard().pressKey(KeyboardButtons.ALT);
            } else {
                scene.keyboard().pushKey(KeyboardButtons.ALT);
            }
            checkUnderline(menu_node0, true);
            scene.keyboard().pushKey(menu0_button);
            MenuItemTest.checkExpanded(menu0, true);
            checkUnderline(getNode(item1), true);
            scene.keyboard().pushKey(item1_button);
            MenuItemTest.checkExpanded(item1, true);
            checkUnderline(getNode(item2), true);
        } catch (Throwable th) {
            throw th;
        } finally {
            if (isLinux) {
                scene.keyboard().pressKey(KeyboardButtons.ALT);
            } else {
                scene.keyboard().pushKey(KeyboardButtons.ALT);
            }
        }
    }

    protected static KeyboardButton getMenuButton(Wrap<? extends MenuItem> wrap) {
        final String text = wrap.getControl().getText();
        final int index = text.indexOf("_");
        return KeyboardButtons.valueOf(text.substring(index + 1, index + 2));
    }

    protected static Wrap<? extends Labeled> getNode(Wrap<? extends Menu> item) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Method method = MenuItemWrap.class.getDeclaredMethod("findWrap", MenuItem.class);
        method.setAccessible(true);
        return (Wrap<? extends Labeled>) method.invoke(item, item.getControl());
    }

    class LevelLookup<T extends Object> extends ByText<T> {
        public LevelLookup(int level, int index) {
            super("Menu " + level + " " + index, StringComparePolicy.SUBSTRING);
        }
    }
}