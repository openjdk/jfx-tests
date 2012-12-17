/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.modality;

import org.jemmy.control.Wrap;
import org.junit.Before;
import test.javaclient.shared.AppLauncher;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * @author Sergey Grinev
 * @author Alexander Kirov
 */
public class Utils {

    static {
        test.javaclient.shared.Utils.initializeAwt();
    }

    @Before
    public void delay() {
        try {
            Thread.sleep(AppLauncher.getInstance().getTestDelay());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Check, whether the saved in golden images screenshot is the same as now.
     *
     * @param name
     * @param node
     */
    protected void checkScreenshot(String name, Wrap node) {
        try {
            ScreenshotUtils.checkScreenshot(name, node);
        } catch (Throwable th) {
            if (screenshotError == null) {
                screenshotError = th;
            }
        }
    }

    /**
     * This method checks, that applied screenshot is not such as in a picture
     * file
     *
     * @param name - name of file
     * @param node - node wrapper
     */
    protected void negativeCheckScreenshot(String name, Wrap node) {
        try {
            ScreenshotUtils.checkScreenshot(name, node);
            screenshotError = new IllegalStateException(
                    "When comparing pictures \"" + name
                    + "\" was found that they are equal, but that must not be");
        } catch (Throwable th) {
        }
    }

    /**
     * Throws local variable which stores the first appeared exception
     *
     * @throws Throwable
     */
    protected void throwScreenshotError() throws Throwable {
        if (screenshotError != null) {
            Throwable throwable = screenshotError;
            screenshotError = null;
            throw throwable;
        }
    }
    /**
     * The first appeared error
     */
    protected Throwable screenshotError = null;
}
