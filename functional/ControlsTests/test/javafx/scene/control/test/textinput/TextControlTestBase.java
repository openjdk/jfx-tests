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

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextInputControl;
import static javafx.scene.control.test.textinput.TextControlApp.RESET_BUTTON_ID;
import static javafx.scene.control.test.textinput.TextControlApp.TESTED_TEXT_INPUT_CONTROL_ID;
import static javafx.scene.control.test.textinput.TextFieldPropertiesApp.SET_ON_ACTION_BUTTON_ID;
import javafx.scene.control.test.util.UtilTestFunctions;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.scene.text.Text;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.junit.After;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class TextControlTestBase extends UtilTestFunctions {

    static Wrap<? extends TextInputControl> testedControl;
    static Wrap<? extends Scene> scene;

    @After
    public void tearDown() {
        resetScene();
    }

    //                  TESTING WORKFLOW MANAGEMENT
    protected void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);

        testedControl = parent.lookup(TextInputControl.class, new ByID<TextInputControl>(TESTED_TEXT_INPUT_CONTROL_ID)).wrap();
    }

    protected void resetScene() {
        clickButtonForTestPurpose(RESET_BUTTON_ID);
    }

    //                   TESTED CONTROL MANAGEMENT
    protected void addText() {
        clickButtonForTestPurpose(javafx.scene.control.test.textinput.TextControlApp.ADD_TEXT_BUTTON_ID);
    }

    protected void addListener() {
        clickButtonForTestPurpose(SET_ON_ACTION_BUTTON_ID);
    }

    protected void selectAll() {
        requestFocusOnControl(testedControl);
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                ((TextInputControl) (testedControl.getControl())).selectAll();
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void setSize(double width, double height) throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, height);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, width);
    }

    //                       STATE ANALIZERS
    protected Double getScrollBarValue(Wrap<? extends ScrollBar> scrollBarWrap) {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((ScrollBar) ((Wrap<? extends ScrollBar>) os[0]).getControl()).getValue());
            }
        }.dispatch(scrollBarWrap.getEnvironment(), scrollBarWrap);
    }

    protected Double getWidthValue(Wrap<? extends TextInputControl> textInputWrap) {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((TextInputControl) (((Wrap<? extends TextInputControl>) (os[0])).getControl())).getWidth());
            }
        }.dispatch(textInputWrap.getEnvironment(), textInputWrap);
    }

    protected Double getHeightValue(Wrap<? extends TextInputControl> textInputWrap) {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((TextInputControl) (((Wrap<? extends TextInputControl>) (os[0])).getControl())).getHeight());
            }
        }.dispatch(textInputWrap.getEnvironment(), textInputWrap);
    }

    /**
     * @return Wrap of text node, which represents the text.
     */
    protected Wrap<? extends Text> getTextNode() {
        return testedControl.as(Parent.class, Node.class).lookup(Text.class).wrap();
    }

    protected Double getFontSize(final Wrap<? extends Text> wrap) {
        if (wrap != null) {
            return new GetAction<Double>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(wrap.getControl().getFont().getSize());
                }
            }.dispatch(Root.ROOT.getEnvironment());
        } else {
            return null;
        }
    }

    protected Double getActualFontSize() {
        return getFontSize(getTextNode());
    }

    static protected enum Properties {

        prefWidth, prefHeight, anchor, caretposition, selectedtext, selection, focused, disabled, editable, text, prefcolumncount, prefrowcount, prompttext, wraptext, scrollleft, scrolltop, focustraversable, font
    };
}