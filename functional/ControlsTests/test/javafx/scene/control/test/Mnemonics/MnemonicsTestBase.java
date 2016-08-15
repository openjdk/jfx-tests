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

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.control.test.ControlsTestBase;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import test.javaclient.shared.Utils;

public class MnemonicsTestBase  extends ControlsTestBase {

    static Wrap<? extends Scene> scene = null;
    static Parent<Node> sceneAsParent = null;

    static final String MNEMONIC_UNDERLINE_STYLE_CLASS = "mnemonic-underline";

    static boolean isLinux;

    //If test is run on Linux this modifier is used to invoke mnemonics
    //along with the key assigned to the control.
    static Keyboard.KeyboardModifiers[] mod;

    @BeforeClass
    public static void setUpClass() throws Exception {
        scene = Root.ROOT.lookup().wrap();
        sceneAsParent = scene.as(Parent.class, Node.class);

        isLinux = Utils.isLinux();
        mod = isLinux ? new Keyboard.KeyboardModifiers[]{Keyboard.KeyboardModifiers.ALT_DOWN_MASK} : new Keyboard.KeyboardModifiers[]{};
    }

    @Before
    public void setUp() {
    }

    protected static void removeFocus(Wrap<? extends Node> ... wraps) {
        boolean focused;
        do {
            focused = false;
            for (Wrap<? extends Node> wrap : wraps) {
                if (wrap.getProperty(java.lang.Boolean.class, "isFocused")) {
                    scene.keyboard().pushKey(KeyboardButtons.TAB);
                    wrap.waitProperty("isFocused", Boolean.FALSE);
                    focused = true;
                }
            }
        } while (focused == true);

        for (Wrap<? extends Node> wrap : wraps) {
            if (wrap.getProperty(java.lang.Boolean.class, "isFocused")) {
                wrap.waitProperty("isFocused", Boolean.FALSE);
            }
        }
    }

    protected static void checkUnderline(Wrap<? extends Node> wrap, Boolean exist) throws Throwable {
        if (!exist && wrap.as(Parent.class, Node.class).lookup(Line.class, new ByStyleClass(MNEMONIC_UNDERLINE_STYLE_CLASS)).size() == 0 ) {
            return;
        }
        final Wrap<? extends Line> underline_wrap = wrap.as(Parent.class, Node.class).lookup(Line.class, new ByStyleClass(MNEMONIC_UNDERLINE_STYLE_CLASS)).wrap();
        underline_wrap.waitState(new State<Boolean>() {
            public Boolean reached() {
                return new GetAction<Boolean>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        final Line control = underline_wrap.getControl();
                        setResult(control.getStroke().isOpaque() && control.isVisible() && (control.getOpacity() == 1.0));
                    }
                }.dispatch(underline_wrap.getEnvironment());
            }
        }, exist);
        if (exist) {
            final Wrap<? extends Text> text_wrap = wrap.as(Parent.class, Node.class).lookup(Text.class).wrap();
            Rectangle text_bounds = text_wrap.getScreenBounds();
            final String str = text_wrap.as(org.jemmy.interfaces.Text.class).text();
            ArrayList<Rectangle> bounds_array = new ArrayList<Rectangle>();
            for (int i = 0; i < str.length() + 1; i++) {
                bounds_array.add(null);
            }
            for (int y = 0; y < text_bounds.height; y++) {
                for (int x = 0; x < text_bounds.width; x++) {
                    final Text control = text_wrap.getControl();
                    final int charIndex = new GetAction<Integer>() {
                        @Override
                        public void run(Object... os) throws Exception {
                            setResult(control.impl_hitTestChar(new Point2D((Integer)os[0], (Integer)os[1] - control.getBaselineOffset())).getCharIndex());
                        }
                    }.dispatch(wrap.getEnvironment(), x, y);
                    if (bounds_array.get(charIndex) == null) {
                        bounds_array.set(charIndex, new Rectangle(x, y, 0, 0));
                    } else  {
                        bounds_array.get(charIndex).add(x, y);
                    }
                }
            }
            int underline_index = -1;
            if (wrap.is(org.jemmy.interfaces.Text.class)) {
                underline_index = wrap.as(org.jemmy.interfaces.Text.class).text().indexOf("_");
            } else {
                final Wrap<? extends Labeled> sub_wrap = wrap.as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
                    public boolean check(Node cntrl) {
                        return cntrl instanceof Labeled;
                    }
                }).wrap();
                underline_index = sub_wrap.as(org.jemmy.interfaces.Text.class).text().indexOf("_");
            }
            Rectangle underlined_char_bounds = bounds_array.get(underline_index);
            underlined_char_bounds.translate(text_bounds.x, text_bounds.y);
            Rectangle suggested_rect = new Rectangle(underlined_char_bounds.x - 2, underlined_char_bounds.y + underlined_char_bounds.height - 1,
                                                     underlined_char_bounds.width + 4, text_bounds.height - underlined_char_bounds.height + 1);
            assertTrue(suggested_rect.contains(underline_wrap.getScreenBounds()));
        }
    }

    protected static KeyboardButton getButton(Wrap<? extends Labeled> wrap) {
        String text = wrap.getControl().getText();
        int index = text.indexOf("_");
        String letter = text.substring(index + 1, index + 2).toUpperCase();
        try {
            Integer.parseInt(letter);
        } catch (Exception ex) {
            return KeyboardButtons.valueOf(letter);
        }
        return KeyboardButtons.valueOf("D" + letter);
    }
}
