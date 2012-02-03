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

import javafx.scene.Scene;
import javafx.scene.control.TextInputControl;
import org.jemmy.action.GetAction;
import org.jemmy.fx.ByText;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.env.Environment;
import org.jemmy.input.ClickFocus;
import org.jemmy.input.SelectionText;
import org.jemmy.interfaces.ControlInterface;
import org.jemmy.interfaces.Focus;
import org.jemmy.interfaces.Focusable;
import org.jemmy.interfaces.IntervalSelectable;
import org.jemmy.interfaces.IntervalSelector;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Text;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

@ControlType(TextInputControl.class)
@ControlInterfaces({SelectionText.class})
public class TextInputControlWrap<T extends TextInputControl> extends ControlWrap<T> implements Text, IntervalSelectable, Focusable {

    @ObjectLookup("text and comparison policy")
    public static <B extends TextInputControl> LookupCriteria<B> textLookup(Class<B> tp, String id, StringComparePolicy policy) {
        return new ByText<B>(id, policy);
    }

    public static final String IS_FOCUSED_PROP_NAME = "isFocused";
    SelectionText inputter;
    TextInputFocus focus = null;

    /**
     *
     * @param scene
     * @param nd
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
        if(text().contains("\n")) {
            inputter.addNavKeys(KeyboardButtons.UP, KeyboardButtons.DOWN);
        }
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @param arg0
     */
    @Override
    public void type(String arg0) {
        inputter.type(arg0);
    }

    /**
     *
     */
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
     * @return
     */
    protected SelectionText input() {
        return inputter;
    }

    /**
     * Selects interval.
     * @param start
     * @param end
     */
    public void select(int start, int end) {
        caret().to(start);
        caret().selectTo(end);
    }

    /**
     * Selects <code>index</code>'th occurance of the regex.
     * @param regex
     * @param index
     */
    public void select(String regex, int index) {
        inputter.select(regex, index);
    }

    /**
     * Selects first occurance of the regex.
     * @param regex
     */
    public void select(String regex) {
        inputter.select(regex);
    }

    /**
     * Retuns the selection portion of the text.
     * @return
     */
    public String selection() {
        return inputter.selection();
    }

    public Focus focuser() {
        if(focus == null) {
            focus = new TextInputFocus();
        }
        return focus;
    }

    @Override
    public <INTERFACE extends ControlInterface> boolean is(Class<INTERFACE> interfaceClass) {
        if(Text.class.isAssignableFrom(interfaceClass) || interfaceClass.isAssignableFrom(SelectionText.class)) return true;
        return super.is(interfaceClass);
    }

    @Override
    public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> interfaceClass) {
        if(Text.class.isAssignableFrom(interfaceClass) || interfaceClass.isAssignableFrom(SelectionText.class))
            return (INTERFACE) inputter;
        return super.as(interfaceClass);
    }

    private class TextInputFocus extends ClickFocus {

        public TextInputFocus() {
            super(TextInputControlWrap.this);
        }

        @Override
        public void focus() {
            if(!getProperty(Boolean.class, IS_FOCUSED_PROP_NAME)) {
                super.focus();
            }
            waitProperty(IS_FOCUSED_PROP_NAME, true);
        }

    }
}