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

package javafx.scene.control.test.textinput;

import com.sun.javafx.scene.text.HitInfo;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.test.textinput.TextInputChanger.TextInputControlWrapInterface;
import javafx.scene.text.Text;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifier;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;
import test.javaclient.shared.Utils;

public class TextInputExternalWrap implements TextInputControlWrapInterface {

    protected Wrap<? extends TextInputControl> wrap = null;
    protected Wrap<? extends Text> textWrap = null;
    protected Keyboard kbd = null;
    protected static KeyboardModifier CTRL_DOWN_MASK_OS = Utils.isMacOS() ? CTRL_DOWN_MASK_OS = KeyboardModifiers.META_DOWN_MASK : KeyboardModifiers.CTRL_DOWN_MASK;

    public TextInputExternalWrap(Wrap<? extends TextInputControl> _wrap) {
        wrap = _wrap;
        kbd = wrap.keyboard();
        Parent<Node> parent = wrap.as(Parent.class, Node.class);
        textWrap = parent.lookup(Text.class, new LookupCriteria<Text>(){
            public boolean check(Text cntrl) {
                return cntrl.isVisible();
            }
        }).wrap();

        wrap.getControl().requestFocus();
    }
    public TextInputControl getControl() {
        return wrap.getControl();
    }
    public Wrap<? extends TextInputControl> getWrap() {
        return wrap;
    }
    public void backward() {
        kbd.pushKey(KeyboardButtons.LEFT);
    }
    public void copy() {
        kbd.pushKey(KeyboardButtons.C, CTRL_DOWN_MASK_OS);
    }
    public void cut() {
        kbd.pushKey(KeyboardButtons.X, CTRL_DOWN_MASK_OS);
    }
    public boolean deleteNextChar() {
        boolean res = new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(wrap.getControl().getSelection().getStart() < wrap.getControl().getText().length());
            }

        }.dispatch(Root.ROOT.getEnvironment());
        kbd.pushKey(KeyboardButtons.DELETE);
        return res;
    }
    public boolean deletePreviousChar() {
        boolean res = new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(wrap.getControl().getSelection().getStart() != 0);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        kbd.pushKey(KeyboardButtons.BACK_SPACE);
        return res;
    }
    public void end() {
        kbd.pushKey(KeyboardButtons.END);
        // workaround
        //textWrap.mouse().click(1, new Point(textWrap.getScreenBounds().width, 0));
        //HitInfo hit = textWrap.getControl().impl_hitTestChar(new Point2D(0, 0));
    }
    public void forward() {
        kbd.pushKey(KeyboardButtons.RIGHT);
    }
    public void home() {
        kbd.pushKey(KeyboardButtons.HOME);
        // workaround
        //textWrap.mouse().click(1, new Point(0, 0));
    }
    public void nextWord() {
        if (Utils.isMacOS()) {
            kbd.pushKey(KeyboardButtons.RIGHT, KeyboardModifiers.ALT_DOWN_MASK);
        } else {
            kbd.pushKey(KeyboardButtons.RIGHT, CTRL_DOWN_MASK_OS);
        }
    }
    public void paste() {
        kbd.pushKey(KeyboardButtons.V, CTRL_DOWN_MASK_OS);
    }
    public void previousWord() {
        if (Utils.isMacOS()) {
            kbd.pushKey(KeyboardButtons.LEFT, KeyboardModifiers.ALT_DOWN_MASK);
        } else {
            kbd.pushKey(KeyboardButtons.LEFT, CTRL_DOWN_MASK_OS);
        }
    }
    public void selectAll() {
        kbd.pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
    }
    public void selectBackward() {
        kbd.pushKey(KeyboardButtons.LEFT, KeyboardModifiers.SHIFT_DOWN_MASK);
    }
    public void selectEnd() {
        kbd.pushKey(KeyboardButtons.END, KeyboardModifiers.SHIFT_DOWN_MASK);
    }
    public void selectForward() {
        kbd.pushKey(KeyboardButtons.RIGHT, KeyboardModifiers.SHIFT_DOWN_MASK);
    }
    public void selectHome() {
        kbd.pushKey(KeyboardButtons.HOME, KeyboardModifiers.SHIFT_DOWN_MASK);
    }
    public void selectNextWord() {
        if (Utils.isMacOS()) {
            KeyboardModifier modifiers[] = {KeyboardModifiers.SHIFT_DOWN_MASK, KeyboardModifiers.ALT_DOWN_MASK};
            kbd.pushKey(KeyboardButtons.RIGHT, modifiers);
        } else {
            KeyboardModifier modifiers[] = {KeyboardModifiers.SHIFT_DOWN_MASK, CTRL_DOWN_MASK_OS};
            kbd.pushKey(KeyboardButtons.RIGHT, modifiers);
        }
    }
    public void selectPreviousWord() {
        if (Utils.isMacOS()) {
            KeyboardModifier modifiers[] = {KeyboardModifiers.SHIFT_DOWN_MASK, KeyboardModifiers.ALT_DOWN_MASK};
            kbd.pushKey(KeyboardButtons.LEFT, modifiers);
        } else {
            KeyboardModifier modifiers[] = {KeyboardModifiers.SHIFT_DOWN_MASK, CTRL_DOWN_MASK_OS};
            kbd.pushKey(KeyboardButtons.LEFT, modifiers);
        }
    }
//    // temporary workaround
//    protected void push(final Node target, final KeyCode key_code, final boolean shift, final boolean ctrl) {
//        new GetAction() {
//            @Override
//            public void run(Object... parameters) {
//                try {
//                    KeyEvent ke = KeyEvent.impl_keyEvent(target, " ", "", key_code.impl_getCode(), shift, ctrl, false, false, KeyEvent.KEY_PRESSED);
//                    target.fireEvent(ke);
//                    ke = KeyEvent.impl_keyEvent(target, " ", "", key_code.impl_getCode(), shift, ctrl, false, false, KeyEvent.KEY_RELEASED);
//                    target.fireEvent(ke);
//                } catch (Exception ex) {
//                    int i = 0;
//                }
//            }
//        }.dispatch(wrap.getEnvironment());
//    }
    public void setText(String str) {
        selectAll();
        type(str);
    }
    public void type(String str) {
        for (int i = 0; i < str.length(); i++)
            kbd.typeChar(str.charAt(i));
    }
}