/*
 * Copyright (c) 2009-2013, Oracle and/or its affiliates. All rights reserved.
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
 */
package test.scenegraph.transparency;

import java.util.concurrent.Callable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.shape.Rectangle;
import org.jemmy.JemmyException;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Lookups;
import org.jemmy.image.*;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.JemmyUtils;
import test.javaclient.shared.TestBase;
import test.scenegraph.stage.PopupTest;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
public class TransparencyWindowTest extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        TransparencyWindowApp.main(null);
    }

    /**
     * Show half-transparent popup window and compare colors at popup window &
     * parent window to check
     * Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW) feature
     */
    @Test
    public void PopupTransparency() throws Throwable {
        Wrap<? extends Button> wrapBtnPopup = Lookups.byID(scene, "BtnShowPopup", Button.class);
        wrapBtnPopup.mouse().click();

        PopupTest.checkStatementWithWaiting("Check transparency", wrapBtnPopup.getEnvironment(), new Callable<String>() {
            public String call() {
                Wrap<? extends Rectangle> wrapGreenRect = Lookups.byID(scene, "RectGreen", Rectangle.class);
                Wrap<? extends CheckBox> wrapCheckBox_isSupportedTransparentWindow = Lookups.byID(scene, "TRANSPARENT_WINDOW", CheckBox.class);

                Image image = scene.getScreenImage();
                try {
                    JemmyUtils.comparePopUpRGB(image, wrapGreenRect.getControl(), wrapCheckBox_isSupportedTransparentWindow.getControl(), TransparencyWindowApp.smallRectSize);
                } catch (JemmyException ex) {
                    return ex.getMessage();
                }
                return null;
            }
        });
    }
}