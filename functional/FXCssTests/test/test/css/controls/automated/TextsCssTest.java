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
package test.css.controls.automated;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Parent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.css.controls.ControlsCSSApp;
import test.javaclient.shared.Utils;
import static test.css.controls.ControlPage.TextFields;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 *
 * @author Sergey Lugovoy
 */
public class TextsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        ControlsCSSApp.main(null);
    }

    @Before
    public void createPage() {
        ((ControlsCSSApp) getApplication()).open(TextFields);
    }

    /**
     * test TextBox with css: -fx-highlight-text-fill
     */
    @Test
    public void TextBoxes_HIGHLIGHT_TEXT_FILL() throws Exception {
        testAdditionalAction(TextFields.name(), "HIGHLIGHT-TEXT-FILL", false);
        Wrap<? extends TextField> textField = Root.ROOT.lookup(Scene.class).wrap().as(Parent.class, Node.class).lookup(TextField.class).wrap();
        textField.mouse().click();
        textField.keyboard().pushKey(Keyboard.KeyboardButtons.A, Utils.isMacOS() ? Keyboard.KeyboardModifiers.META_DOWN_MASK : Keyboard.KeyboardModifiers.CTRL_DOWN_MASK);
        String normalizedName = Utils.normalizeName(TextFields.name() + "HIGHLIGHT-TEXT-FILL");
        ScreenshotUtils.checkScreenshot(new StringBuilder(getName()).append("-").append(normalizedName).toString(),
               ScreenshotUtils.getPageContent());
    }

    /**
     * test TextBox with css: -fx-highlight-fill
     */
    @Test
    public void TextBoxes_HIGHLIGHT_FILL() throws Exception {
        testAdditionalAction(TextFields.name(), "HIGHLIGHT-FILL", false);
        Wrap<? extends TextField> textField = Root.ROOT.lookup(Scene.class).wrap().as(Parent.class, Node.class).lookup(TextField.class).wrap();
        textField.mouse().click();
        textField.keyboard().pushKey(Keyboard.KeyboardButtons.A, Utils.isMacOS() ? Keyboard.KeyboardModifiers.META_DOWN_MASK : Keyboard.KeyboardModifiers.CTRL_DOWN_MASK);
        String normalizedName = Utils.normalizeName(TextFields.name() + "HIGHLIGHT-FILL");
        ScreenshotUtils.checkScreenshot(new StringBuilder(getName()).append("-").append(normalizedName).toString(),
                ScreenshotUtils.getPageContent());
    }

    public String getName() {
        return "ControlCss";
    }
}
