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

import client.test.Smoke;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import static javafx.scene.control.test.Mnemonics.MnemonicsTestBase.scene;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByID;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.Utils;

/**
 * Class provides tests for mnemonics behavior.
 * According to the mnemonics spec,
 * mnemonics are platform specific. Therefore tests design is aimed to
 * consider the difference between platforms and to avoid boilerplate.
 *
 * If tests are run on Ubuntu with Unity the ALT key may invoke the HUD.
 * In that case you should change system keyboard mapping to avoid such behavior.
 *
 */
@RunWith(FilteredTestRunner.class)
public class LabelsMnemonicsTest extends MnemonicsTestBase {

    @BeforeClass
    public static void setUpClass() throws Exception {
        LabelsMnemonicsApp.main(null);
        MnemonicsTestBase.setUpClass();
    }


    @Smoke
    @Test(timeout = 300000)
    public void staticTest() throws Throwable {
        if (!Utils.isMacOS()) {
            staticSequence(KeyboardButtons.ALT);
            if (!Utils.isLinux()) {
                // current spec is unclear
                // staticSequence(KeyboardButtons.F10);
            }
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void dynamicTest() throws Throwable {
        if (!Utils.isMacOS()) {
            dynamicSequence(KeyboardButtons.ALT);
            if (!Utils.isLinux()) {
                // current spec is unclear
                // dynamicSequence(KeyboardButtons.F10);
            }
        }
    }

    public void staticSequence(KeyboardButton activate_button) throws Throwable {
        Wrap<? extends Button> button1 = sceneAsParent.lookup(Button.class, new ByID<Button>(LabelsMnemonicsApp.BUTTON_STATIC_ID)).wrap();
        Wrap<? extends Label> label1 = sceneAsParent.lookup(Label.class, new ByID<Label>(LabelsMnemonicsApp.LABEL_STATIC_SET_ID)).wrap();
        Wrap<? extends Button> button2 = sceneAsParent.lookup(Button.class, new ByID<Button>(LabelsMnemonicsApp.BUTTON_STATIC_2_ID)).wrap();
        Wrap<? extends Label> label2 = sceneAsParent.lookup(Label.class, new ByID<Label>(LabelsMnemonicsApp.LABEL_STATIC_RESET_ID)).wrap();
        Wrap<? extends Node> button3 = sceneAsParent.lookup(Button.class, new ByID<Button>(LabelsMnemonicsApp.BUTTON_STATIC_REVERSED_ID)).wrap();
        Wrap<? extends Label> label3 = sceneAsParent.lookup(Label.class, new ByID<Label>(LabelsMnemonicsApp.LABEL_STATIC_REVERSED_ID)).wrap();
        KeyboardButton button1_kbb = getButton(button1);//B
        KeyboardButton label1_kbb = getButton(label1);//S
        KeyboardButton label2_kbb = getButton(label2);//R
        KeyboardButton label3_kbb = getButton(label3);//O

        checkUnderline(label1, false);
        checkUnderline(label2, false);
        checkUnderline(label3, false);

        removeFocus(button1, button2, button3);
        scene.keyboard().pushKey(button1_kbb);
        button1.waitProperty("isFocused", Boolean.FALSE);
        scene.keyboard().pushKey(label1_kbb);
        button1.waitProperty("isFocused", Boolean.FALSE);
        scene.keyboard().pushKey(label2_kbb);
        button2.waitProperty("isFocused", Boolean.FALSE);
        scene.keyboard().pushKey(label3_kbb);
        button3.waitProperty("isFocused", Boolean.FALSE);

        try {
            if (isLinux) {
                scene.keyboard().pressKey(activate_button);
            } else {
                scene.keyboard().pushKey(activate_button);
            }

            checkUnderline(label1, true);
            checkUnderline(label2, true);
            checkUnderline(label3, true);
            checkUnderline(button1, true);

             if (isLinux) { scene.keyboard().releaseKey(activate_button); }

            removeFocus(button1);

            scene.keyboard().pushKey(button1_kbb, mod);
            button1.waitProperty("isFocused", Boolean.TRUE);
            removeFocus(button1);
            scene.keyboard().pushKey(label1_kbb, mod);
            button1.waitProperty("isFocused", Boolean.TRUE);
            scene.keyboard().pushKey(label2_kbb, mod);
            button2.waitProperty("isFocused", Boolean.TRUE);
            scene.keyboard().pushKey(label3_kbb, mod);
            button3.waitProperty("isFocused", Boolean.TRUE);
        } catch (Throwable th) {
            throw th;
        } finally {
            scene.keyboard().pushKey(activate_button);
        }
        checkUnderline(label1, false);
        checkUnderline(label2, false);
        checkUnderline(label3, false);
    }

    public void dynamicSequence(KeyboardButton activate_button) throws Throwable {
        Wrap<? extends Button> button1 = sceneAsParent.lookup(Button.class, new ByID<Button>(LabelsMnemonicsApp.BUTTON_DYNAMIC_1_ID)).wrap();
        Wrap<? extends Button> button2 = sceneAsParent.lookup(Button.class, new ByID<Button>(LabelsMnemonicsApp.BUTTON_DYNAMIC_2_ID)).wrap();
        Wrap<? extends Label> label1 = sceneAsParent.lookup(Label.class, new ByID<Label>(LabelsMnemonicsApp.LABEL_DYNAMIC_1_ID)).wrap();
        Wrap<? extends Label> label2 = sceneAsParent.lookup(Label.class, new ByID<Label>(LabelsMnemonicsApp.LABEL_DYNAMIC_2_ID)).wrap();
        Selector check_parse1 = sceneAsParent.lookup(CheckBox.class, new ByID<CheckBox>(LabelsMnemonicsApp.CHECK_DYNAMIC_PARSE_1_ID)).wrap().as(Selectable.class).selector();
        Selector check_parse2 = sceneAsParent.lookup(CheckBox.class, new ByID<CheckBox>(LabelsMnemonicsApp.CHECK_DYNAMIC_PARSE_2_ID)).wrap().as(Selectable.class).selector();
        Selector check_set1 = sceneAsParent.lookup(CheckBox.class, new ByID<CheckBox>(LabelsMnemonicsApp.CHECK_DYNAMIC_SET_1_ID)).wrap().as(Selectable.class).selector();
        Selector check_set2 = sceneAsParent.lookup(CheckBox.class, new ByID<CheckBox>(LabelsMnemonicsApp.CHECK_DYNAMIC_SET_2_ID)).wrap().as(Selectable.class).selector();
        Selector check_reversed_set1 = sceneAsParent.lookup(CheckBox.class, new ByID<CheckBox>(LabelsMnemonicsApp.CHECK_DYNAMIC_REVERSED_SET_1_ID)).wrap().as(Selectable.class).selector();
        Selector check_reversed_set2 = sceneAsParent.lookup(CheckBox.class, new ByID<CheckBox>(LabelsMnemonicsApp.CHECK_DYNAMIC_REVERSED_SET_2_ID)).wrap().as(Selectable.class).selector();
        KeyboardButton label1_kbb = getButton(label1);
        KeyboardButton label2_kbb = getButton(label2);

        try {
            check_set1.select(CheckBoxWrap.State.CHECKED);
            new Timeout("Item list delay", 200).sleep();
            check_set2.select(CheckBoxWrap.State.CHECKED);
            new Timeout("Item list delay", 200).sleep();
            check_parse1.select(CheckBoxWrap.State.CHECKED);
            new Timeout("Item list delay", 200).sleep();
            check_parse2.select(CheckBoxWrap.State.CHECKED);
            new Timeout("Item list delay", 200).sleep();

            if (isLinux) {
                scene.keyboard().pressKey(activate_button);
            } else {
                scene.keyboard().pushKey(activate_button);
            }

            checkUnderline(label1, true);
            checkUnderline(label2, true);

            if (isLinux) { scene.keyboard().releaseKey(activate_button); }

            scene.keyboard().pushKey(label1_kbb, mod);
            button1.waitProperty("isFocused", Boolean.TRUE);
            scene.keyboard().pushKey(label2_kbb, mod);
            button2.waitProperty("isFocused", Boolean.TRUE);
        } catch (Throwable th) {
            throw th;
        } finally {
            scene.keyboard().pushKey(activate_button);
        }

        checkUnderline(label1, false);
        checkUnderline(label2, false);

        try {
            scene.keyboard().pushKey(KeyboardButtons.ALT);

            check_reversed_set1.select(CheckBoxWrap.State.CHECKED);
            check_reversed_set2.select(CheckBoxWrap.State.CHECKED);

            removeFocus(button1, button2);
            scene.keyboard().pushKey(label2_kbb, mod);
            button1.waitProperty("isFocused", Boolean.TRUE);
            removeFocus(button1);
            scene.keyboard().pushKey(label1_kbb, mod);
            button2.waitProperty("isFocused", Boolean.TRUE);

            check_reversed_set1.select(CheckBoxWrap.State.UNCHECKED);
            check_reversed_set2.select(CheckBoxWrap.State.UNCHECKED);
            check_set1.select(CheckBoxWrap.State.UNCHECKED);
            check_set2.select(CheckBoxWrap.State.UNCHECKED);

            removeFocus(button1, button2);
            scene.keyboard().pushKey(label1_kbb);
            button1.waitProperty("isFocused", Boolean.FALSE);
            removeFocus(button2);
            scene.keyboard().pushKey(label2_kbb);
            button2.waitProperty("isFocused", Boolean.FALSE);
        } catch (Throwable th) {
            throw th;
        } finally {
            scene.keyboard().pushKey(KeyboardButtons.ALT);
        }
    }
}
