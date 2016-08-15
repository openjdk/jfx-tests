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
 */
package test.scenegraph.modality;

import javafx.stage.Stage;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import org.junit.Before;
import org.junit.Test;
import static test.javaclient.shared.Utils.isLinux;
import test.scenegraph.modality.ModalityWindow.WindowsRenderType;

/**
 * @author Alexander Kirov
 */
public class ModalityZOrderAndFocusTest extends ModalityBase {

    @Before
    public void setUp() throws Exception {
        setUpTyped(WindowsRenderType.HIERARHICAL);
    }

    @Test
    public void case1Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.APPAPP, true);

        tryToCloseByButton("stage0", "stage1");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage2");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage0", "stage1");
        checkScreenshot("ModalityGreen100100", "stage0");
        throwScreenshotError();
    }

    @Test
    public void case2Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.WINWIN, true);

        tryToCloseByButton("stage0", "stage1");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage2");
        checkScreenshot("ModalityGreen100100", "stage0");
        throwScreenshotError();
    }

    @Test
    public void case3Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.WINAPP, true);

        tryToCloseByButton("stage0", "stage1");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage2");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage0", "stage1");
        checkScreenshot("ModalityGreen100100", "stage0");
        throwScreenshotError();
    }

    @Test
    public void case4Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.APPWIN, true);

        tryToCloseByButton("stage0", "stage2");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage1");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage2");
        checkScreenshot("ModalityGreen100100", "stage0");
        throwScreenshotError();
    }

    @Test
    public void case5Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.NONE, true);

        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage1");
        checkScreenshot("ModalityGreen100100", "stage0");
        throwScreenshotError();
    }

    @Test
    public void case6Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.APP, true);

        tryToCloseByButton("stage0");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage1");
        //Commented out, because it looks like, it works fine both on linux and
        //windows (at leats, it works on ubuntu12).
//        if (!isLinux()) {
        checkScreenshot("ModalityGreen100100", "stage0");
//        } else {
//            negativeScreenshotCheck("ModalityGreen100100", "stage0");
//        }
        throwScreenshotError();
    }

    @Test
    public void case7Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.WIN, true);

        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage0");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage1");
        checkScreenshot("ModalityGreen100100", "stage0");
        throwScreenshotError();
    }

    @Test
    public void case8Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.WIN, true);
        createSubstage("stage1", ModalityWindow.TestCase.WINWIN, true);
        createSubstage("stage3", ModalityWindow.TestCase.APP, true);

        if (!isLinux()) {
            tryToCloseByButton("stage0", "stage1", "stage2", "stage3", "stage4");
            negativeScreenshotCheck("ModalityGreen100100", "stage2");
            tryToCloseByButton("stage1");
            negativeScreenshotCheck("ModalityGreen100100", "stage0");
            tryToCloseByButton("stage2", "stage3", "stage1");
            checkScreenshot("ModalityGreen100100", "stage0");
        } else {
            tryToCloseByButton("stage0", "stage1", "stage2", "stage3", "stage4");
            checkScreenshot("ModalityGreen100100", "stage3");
            tryToCloseByButton("stage1");
            negativeScreenshotCheck("ModalityGreen100100", "stage0");
            tryToCloseByButton("stage2", "stage3", "stage1");
            checkScreenshot("ModalityGreen100100", "stage0");
        }
        //Commented out, because it looks like, it works fine both on linux and
        //windows (at leats, it works on ubuntu12).
//            negativeScreenshotCheck("ModalityGreen100100", "stage0");
//            negativeScreenshotCheck("ModalityGreen100100", "stage1");
//            negativeScreenshotCheck("ModalityGreen100100", "stage2");
//            negativeScreenshotCheck("ModalityGreen100100", "stage3");
//            checkScreenshot("ModalityGreen100100", "stage4");
//            tryToCloseByButton("stage4");
//            negativeScreenshotCheck("ModalityGreen100100", "stage1");
//            negativeScreenshotCheck("ModalityGreen100100", "stage2");
//            checkScreenshot("ModalityGreen100100", "stage3");
//            tryToCloseByButton("stage2", "stage1");
//            negativeScreenshotCheck("ModalityGreen100100", "stage1");
//            checkScreenshot("ModalityGreen100100", "stage3");
//            tryToCloseByButton("stage3");
//            negativeScreenshotCheck("ModalityGreen100100", "stage1");
//        }
        throwScreenshotError();
    }

    @Test
    public void case9Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.NONE, true);
        createSubstage("stage1", ModalityWindow.TestCase.WIN, true);
        createSubstage("stage2", ModalityWindow.TestCase.APP, true);
        createSubstage("stage3", ModalityWindow.TestCase.APP, true);

        tryToCloseByButton("stage0", "stage1", "stage2", "stage3", "stage4");
        negativeScreenshotCheck("ModalityGreen100100", "stage2");
        tryToCloseByButton("stage2");
        negativeScreenshotCheck("ModalityGreen100100", "stage2");
        tryToCloseByButton("stage1", "stage2", "stage3", "stage1", "stage2", "stage1");
        checkScreenshot("ModalityGreen100100", "stage0");
        throwScreenshotError();
    }

    @Test
    public void case10Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.WINWIN, true);
        createSubstage("stage2", ModalityWindow.TestCase.WINWIN, true);

        tryToCloseByButton("stage0", "stage2");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage1", "stage0", "stage2");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage3");
        negativeScreenshotCheck("ModalityGreen100100", "stage2");
        tryToCloseByButton("stage2", "stage0", "stage4");
        checkScreenshot("ModalityGreen100100", "stage2");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage0", "stage2");
        checkScreenshot("ModalityGreen100100", "stage0");
        throwScreenshotError();
    }

    @Test
    public void case11Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.APP, true);
        createSubstage("stage1", ModalityWindow.TestCase.WINWIN, true);
        createSubstage("stage3", ModalityWindow.TestCase.APP, true);
        if (!isLinux()) {
            createSubstage("stage2", ModalityWindow.TestCase.NONE, true);
        }

        if (!isLinux()) {
            tryToCloseByButton("stage0", "stage1", "stage2", "stage3", "stage4",
                    "stage0", "stage1", "stage3", "stage1", "stage0",
                    "stage2", "stage0", "stage1");
            checkScreenshot("ModalityGreen100100", "stage0");
        } else {//LinuxOS
            checkScreenshot("ModalityGreen100100", "stage4");
            tryToCloseByButton("stage4");
            checkScreenshot("ModalityGreen100100", "stage3");
            tryToCloseByButton("stage3");
            checkScreenshot("ModalityGreen100100", "stage2");
            tryToCloseByButton("stage2");
            checkScreenshot("ModalityGreen100100", "stage1");
            tryToCloseByButton("stage1");
            checkScreenshot("ModalityGreen100100", "stage0");
            tryToCloseByButton("stage0");
        }
        throwScreenshotError();
    }

    @Test
    public void case12Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.APP, true);
        createSubstage("stage1", ModalityWindow.TestCase.NONE, false);
        createSubstage("stage2", ModalityWindow.TestCase.APP, true);

        tryToCloseByButton("stage0", "stage2", "stage1");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage3");
        checkScreenshot("ModalityGreen100100", "stage2");
        tryToCloseByButton("stage2");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        checkScreenshot("ModalityGreen100100", "stage1");
        tryToCloseByButton("stage1");
        checkScreenshot("ModalityGreen100100", "stage0");
        throwScreenshotError();
    }

    @Test
    public void case13Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.APP, true);
        createSubstage("stage1", ModalityWindow.TestCase.NONE, false);
        createSubstage("stage2", ModalityWindow.TestCase.WIN, true);

        tryToCloseByButton("stage0", "stage2", "stage1");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage2");
        negativeScreenshotCheck("ModalityGreen100100", "stage0");
        tryToCloseByButton("stage3");
        checkScreenshot("ModalityGreen100100", "stage2");
        tryToCloseByButton("stage2");
        checkScreenshot("ModalityGreen100100", "stage0");
        throwScreenshotError();
    }

    @Test
    public void case14Test() throws InterruptedException, Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.APP, true);
        createSubstage("stage1", ModalityWindow.TestCase.NONE, false);
        createSubstage("stage2", ModalityWindow.TestCase.WIN, true);
        createSubstage("stage3", ModalityWindow.TestCase.APP, true);

        if (isLinux()) {
            tryToCloseByButton("stage0", "stage2", "stage1", "stage3", "stage4");
            negativeScreenshotCheck("ModalityGreen100100", "stage0");
            tryToCloseByButton("stage1");
            checkScreenshot("ModalityGreen100100", "stage3");
            tryToCloseByButton("stage3", "stage2");
            checkScreenshot("ModalityGreen100100", "stage0");
        } else {
            tryToCloseByButton("stage0", "stage2", "stage1", "stage3", "stage4");
            negativeScreenshotCheck("ModalityGreen100100", "stage0");
            tryToCloseByButton("stage3");
            checkScreenshot("ModalityGreen100100", "stage2");
            tryToCloseByButton("stage2", "stage1");
            checkScreenshot("ModalityGreen100100", "stage0");
        }

        throwScreenshotError();
    }

    @Test
    public void case15Test() throws Throwable {
        createSubstage("stage0", ModalityWindow.TestCase.NONE, true);
        createSubstage("stage1", ModalityWindow.TestCase.WIN, true);

        new GetAction() {

            @Override
            public void run(Object... parameters) throws InterruptedException {
                for (Stage stage : ModalityWindow.allStages) {
                    stage.close();
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());

        checkScreenshot("ModalityGreen100100", "primaryStage");

        throwScreenshotError();
    }
}
