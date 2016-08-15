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

package org.jemmy.fx.control.caspian;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByObject;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.ByText;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.ColorPickerWrap;
import org.jemmy.interfaces.Editor;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Text;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.timing.State;

class ColorEditorImpl implements Editor<Color> {

    protected final ColorPickerWrap<? extends ColorPicker> picker;

    protected static final String COLOR_PALETTE_STYLE_CLASS = "color-palette";
    protected static final String COLOR_RECT_STYLE_CLASS = "color-rect";
    protected static final String TOGGLE_BUTTON_STYLE_CLASS = "right-pill";
    protected static final String WEB_COLOR_STYLE_CLASS = "webcolor-field";
    protected static final String ALPHA_SETTINGS_STYLE_CLASS = "color-input-field";
    protected static final String USE_TEXT = "Use";

    public ColorEditorImpl(ColorPickerWrap picker) {
        this.picker = picker;
    }

    public Color value() {
        return new GetAction<Color>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(picker.getControl().getValue());
            }
            @Override
            public String toString() {
                return "Getting value of " + picker.getControl();
            }
        }.dispatch(picker.getEnvironment());
    }

    public void enter(final Color state) {
        if (value() == state) {
            return;
        }
        picker.mouse().click();
        Wrap<? extends Scene> popup = Root.ROOT.lookup(new LookupCriteria<Scene>() {
            public boolean check(Scene cntrl) {
                return Root.ROOT.lookup(new ByObject<Scene>(cntrl)).wrap().as(Parent.class, Node.class).lookup(new ByStyleClass(COLOR_PALETTE_STYLE_CLASS)).size() == 1;
            }
        }).wrap();
        final Lookup lookup = popup.as(Parent.class, Node.class).lookup(Shape.class, new ByStyleClass(COLOR_RECT_STYLE_CLASS)).lookup(Shape.class, new LookupCriteria<Shape>() {
            public boolean check(Shape cntrl) {
                return cntrl.getFill().equals(state);
            }
        });
        if (lookup.size() > 0) {
            lookup.wrap().mouse().click();
            return;
        }
        popup.as(Parent.class, Node.class).lookup(Hyperlink.class).wrap().mouse().click();
        Wrap<? extends Scene> dlg = Root.ROOT.lookup(new LookupCriteria<Scene>() {
            public boolean check(Scene cntrl) {
                return Root.ROOT.lookup(new ByObject<Scene>(cntrl)).wrap().as(Parent.class, Node.class).lookup(ToggleButton.class, new ByStyleClass(TOGGLE_BUTTON_STYLE_CLASS)).size() > 0;
            }
        }).wrap();
        dlg.as(Parent.class, Node.class).lookup(ToggleButton.class, new ByStyleClass(TOGGLE_BUTTON_STYLE_CLASS)).wrap().mouse().click();
        Wrap<? extends TextField> color = dlg.as(Parent.class, Node.class).lookup(TextField.class, new ByStyleClass(WEB_COLOR_STYLE_CLASS)).wrap();
        final String hex = "#" + Integer.toHexString(((int) (state.getRed() * 255) << 16) + ((int) (state.getGreen() * 255) << 8) + (int) (state.getBlue() * 255)).toUpperCase();
        if (!color.as(Text.class).text().equals(hex)) {
            color.as(Text.class).clear();
            color.as(Text.class).type(hex);
        }
        Wrap<? extends TextField> alpha = dlg.as(Parent.class, Node.class).lookup(TextField.class, new ByStyleClass(ALPHA_SETTINGS_STYLE_CLASS) {
            @Override
            public boolean check(Node cntrl) {
                javafx.scene.Parent parent = cntrl.getParent();
                if (parent != null) {
                    return super.check(cntrl) && parent.isVisible();
                }
                return super.check(cntrl);
            }

        }).wrap();
        String opacity = String.valueOf((int) (state.getOpacity()*100));
        if (!alpha.as(Text.class).text().equals(opacity)) {
            alpha.as(Text.class).clear();
            alpha.as(Text.class).type(opacity);
        }
        dlg.as(Parent.class, Node.class).lookup(new ByText(USE_TEXT, StringComparePolicy.EXACT)).wrap().mouse().click();
        picker.waitState(new State<Boolean>() {
            public Boolean reached() {
                return ((Math.abs(value().getRed() - state.getRed()) <= 0.01) &&
                       (Math.abs(value().getGreen() - state.getGreen()) <= 0.01) &&
                       (Math.abs(value().getBlue() - state.getBlue()) <= 0.01) &&
                       (Math.abs(value().getOpacity()- state.getOpacity()) <= 0.01));
            }
        }, Boolean.TRUE);
    }

    public Class<Color> getType() {
        return Color.class;
    }
}