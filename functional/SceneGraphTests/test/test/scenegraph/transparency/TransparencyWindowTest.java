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

import java.util.Arrays;
import java.util.concurrent.Callable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.jemmy.JemmyException;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Lookups;
import org.jemmy.image.*;
import org.junit.BeforeClass;
import org.junit.Test;
import test.embedded.helpers.Configuration;
import test.embedded.helpers.GraphicsCheckBoxes;
import static test.javaclient.shared.JemmyUtils.getRGBColors;
import static test.javaclient.shared.JemmyUtils.usingGlassRobot;
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

    private static boolean isTransparencySupported(Node node) {
        if(Configuration.isEmbedded()) {
            return GraphicsCheckBoxes.isChecked((Text)node);
        } else {
            return ((CheckBox)node).isSelected();
        }
    }

    /**
     * Show half-transparent popup window and compare colors at popup window &
     * parent window to check
     * Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW) feature
     */
    @Test
    public void PopupTransparency() throws Throwable {
        Wrap<? extends Node> wrapBtnPopup = Lookups.byID(getScene(), "BtnShowPopup", Node.class);
        wrapBtnPopup.mouse().click();

        PopupTest.checkStatementWithWaiting("Check transparency", wrapBtnPopup.getEnvironment(), new Callable<String>() {
            public String call() {
                Wrap<? extends Rectangle> wrapGreenRect = Lookups.byID(getScene(), "RectGreen", Rectangle.class);
                Wrap<? extends Node> wrapCheckBox_isSupportedTransparentWindow = Lookups.byID(getScene(), "TRANSPARENT_WINDOW", Node.class);

                Image image = getScene().getScreenImage();
                String status = null;
                try {
                    status = ccomparePopUpRGB(image, wrapGreenRect.getControl(), wrapCheckBox_isSupportedTransparentWindow.getControl(), TransparencyWindowApp.smallRectSize);
                } catch (JemmyException ex) {
                    return ex.getMessage();
                }
                return status;
            }
        });

    }
    public static String ccomparePopUpRGB(Image image, Rectangle rec, Node chB, int smallRectSize){
        String status = checkAssert(true,"Internal error: Image is not of proper type AWTImage/GlassImage", usingGlassRobot() ? image instanceof GlassImage : image instanceof AWTImage);
        if (null != status)
            return status;

        Object rgbOnPopupAndGreenRect = getRGBColors(image, (int)(rec.getX()) + 1, (int)(rec.getY()) + 1);
        Object rgbOnPopup = getRGBColors(image, (int)(rec.getX()) - 1, (int)(rec.getY()) + 1);
        Object rgbOutsidePopup = getRGBColors(image, (int)(rec.getX()) - smallRectSize - 1, (int)(rec.getY()));

        status = checkAssert(true,"Internal Error: Popup is not showed", usingGlassRobot() ? !Arrays.equals((double[]) rgbOnPopup, (double[]) rgbOutsidePopup) : ((Integer) rgbOnPopup).intValue() != ((Integer) rgbOutsidePopup).intValue());
        if (null != status)
            return status;

        if (isTransparencySupported(chB)) {
            // Transparent window is supported
            status = checkAssert(false,"Transparency is not supported but Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW) == true",
                    usingGlassRobot() ? Arrays.equals((double[]) rgbOnPopup,(double[]) rgbOnPopupAndGreenRect) : ((Integer) rgbOnPopup).intValue() == ((Integer) rgbOnPopupAndGreenRect).intValue());
        if (null != status)
            return status;
        } else {
            // Transparent window isn't supported
            status = checkAssert(true,"Transparency is supported but Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW) == false",
                    usingGlassRobot() ? Arrays.equals((double[]) rgbOnPopup,(double[]) rgbOnPopupAndGreenRect) : ((Integer) rgbOnPopup).intValue() == ((Integer) rgbOnPopupAndGreenRect).intValue());
        if (null != status)
            return status;
        }
        return status;
    }
    private static String checkAssert(final boolean _expected, final String _msg,final boolean _chk)
    {
        String status = null;
        if (_expected == _chk)
            return status;
        else
            return _msg;
    }

}