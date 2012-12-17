/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.transparency;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.shape.Rectangle;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Lookups;
import org.jemmy.image.*;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.JemmyUtils;
import test.javaclient.shared.TestBase;

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
    public void PopupTransparency() throws InterruptedException {
        Wrap<? extends Button> wrapBtnPopup = Lookups.byID(scene, "BtnShowPopup", Button.class);
        wrapBtnPopup.mouse().click();

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }

        Wrap<? extends Rectangle> wrapGreenRect = Lookups.byID(scene, "RectGreen", Rectangle.class);
        Wrap<? extends CheckBox> wrapCheckBox_isSupportedTransparentWindow = Lookups.byID(scene, "TRANSPARENT_WINDOW", CheckBox.class);

        Image image = scene.getScreenImage();
        JemmyUtils.comparePopUpRGB(image, wrapGreenRect.getControl(), wrapCheckBox_isSupportedTransparentWindow.getControl(), TransparencyWindowApp.smallRectSize);
    }
}
