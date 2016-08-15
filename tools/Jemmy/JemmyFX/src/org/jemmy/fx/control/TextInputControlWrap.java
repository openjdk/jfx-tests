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

import javafx.scene.control.TextInputControl;
import org.jemmy.action.FutureAction;
import org.jemmy.action.GetAction;
import org.jemmy.control.As;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.dock.DockInfo;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByText;
import org.jemmy.input.ClickFocus;
import org.jemmy.input.SelectionText;
import org.jemmy.interfaces.*;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

/**
 * Test support for Java FX text editing controls. Supported operations are:
 * type, clean, select, move caret - all that is supported in
 * <code>SelectionText</code> control interface. See <a
 * href="../../samples/text/TextSample.java"><code>TextSample</code></a> for
 * more info.
 *
 * @param <T>
 * @author shura
 * @see SelectionText
 */
@ControlType(TextInputControl.class)
@ControlInterfaces({SelectionText.class})
@DockInfo(generateSubtypeLookups = true)
public class TextInputControlWrap<T extends TextInputControl> extends ControlWrap<T> implements Text, IntervalSelectable, Focusable {

    /**
     * Turns a text sample and a string comparison logic into lookup criteria.
     *
     * @param <B>
     * @param tp
     * @param text
     * @param policy
     * @return
     */
    @ObjectLookup("text and comparison policy")
    public static <B extends TextInputControl> LookupCriteria<B> textLookup(Class<B> tp, String text, StringComparePolicy policy) {
        return new ByText<>(text, policy);
    }

    /**
     * Name of the focused property.
     */
    public static final String IS_FOCUSED_PROP_NAME = "isFocused";
    private SelectionText inputter;
    private TextInputFocus focus = null;

    /**
     * Wraps a text input control.
     *
     * @param env
     * @param node
     */
    public TextInputControlWrap(Environment env, T node) {
        super(env, node);
        inputter = new SelectionText(this) {
            @Override
            public String text() {
                return TextInputControlWrap.this.text();
            }

            @Override
            public double position() {
                return TextInputControlWrap.this.dot();
            }

            @Override
            public double anchor() {
                return TextInputControlWrap.this.mark();
            }
        };
        //huh?
        //TBD why there's not get$multiline() ?
        final String text = text();
        if (text != null && text.contains("\n")) {
            inputter.addNavKeys(KeyboardButtons.UP, KeyboardButtons.DOWN);
        }
    }

    @Property(TEXT_PROP_NAME)
    @Override
    public String text() {
        return new GetAction<String>() {
            @Override
            public void run(Object... parameters) {
                setResult(getControl().getText());
            }

            @Override
            public String toString() {
                return "Getting text of " + getControl();
            }
        }.dispatch(getEnvironment());
    }

    /**
     * End of the selection.
     *
     * @return
     */
    @Property(POSITION_PROP_NAME)
    public int dot() {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... parameters) {
                setResult(getControl().getSelection().getEnd());
            }

            @Override
            public String toString() {
                return "Getting position of " + getControl();
            }
        }.dispatch(getEnvironment());
    }

    /**
     * Start of the selection.
     *
     * @return
     */
    public int mark() {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... parameters) {
                setResult(getControl().getSelection().getStart());
            }

            @Override
            public String toString() {
                return "Getting selection position of " + getControl();
            }
        }.dispatch(getEnvironment());
    }

    @Override
    public void type(String arg0) {
        inputter.type(arg0);
    }

    @Override
    public void clear() {
        inputter.clear();
    }

    @Override
    public double anchor() {
        return inputter.anchor();
    }

    @Override
    public IntervalSelector caret() {
        return inputter.caret();
    }

    @Override
    public double position() {
        return inputter.position();
    }

    @Override
    public void to(double position) {
        inputter.to(position);
    }

    /**
     * An instance of SelectionText which is actually used to perform input
     *
     * @return
     */
    protected SelectionText input() {
        return inputter;
    }

    /**
     * Selects an interval.
     *
     * @param start
     * @param end
     */
    public void select(int start, int end) {
        caret().to(start);
        caret().selectTo(end);
    }

    /**
     * Selects
     * <code>index</code>'th occurance of the regex.
     *
     * @param regex
     * @param index
     */
    public void select(String regex, int index) {
        inputter.select(regex, index);
    }

    /**
     * Selects first occurance of the regex.
     *
     * @param regex
     */
    public void select(String regex) {
        inputter.select(regex);
    }

    /**
     * Retuns the selection portion of the text.
     *
     * @return
     */
    public String selection() {
        return inputter.selection();
    }

    @Override
    public Focus focuser() {
        if (focus == null) {
            focus = new TextInputFocus();
        }
        return focus;
    }

    /**
     * Allows to use the control as text control interface.
     *
     * @return
     */
    @As
    public SelectionText asText() {
        return inputter;
    }

    private class TextInputFocus extends ClickFocus {

        public TextInputFocus() {
            super(TextInputControlWrap.this);
        }

        @Override
        public void focus() {
            if (!getProperty(Boolean.class, IS_FOCUSED_PROP_NAME)) {
                //Wait, until skin has allocated a text input control.
                //(sometimes, it is not allocated, but already in the scene)
                waitState(() -> {
                    final double square = new FutureAction<>(getEnvironment(), () -> getControl().getWidth() * getControl().getHeight()).get();
                    if (square > 1) {
                        return Boolean.TRUE;
                    } else {
                        return null;
                    }
                });
                super.focus();
            }
            waitProperty(IS_FOCUSED_PROP_NAME, true);
        }
    }
}