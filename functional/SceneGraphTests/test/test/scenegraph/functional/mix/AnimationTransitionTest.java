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
package test.scenegraph.functional.mix;

import javafx.animation.Animation.Status;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.jemmy.fx.Lookups;
import org.jemmy.control.Wrap;
import org.jemmy.timing.State;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import test.scenegraph.app.AnimationTransitionApp.CheckedIds;
import test.scenegraph.app.AnimationTransitionApp.Pages;
import test.scenegraph.app.AnimationTransitionApp;
import test.javaclient.shared.TestBase;

public class AnimationTransitionTest extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        AnimationTransitionApp.main(null);
    }


    protected void clickButton(String name) {
        // TODO: remove this awful workaround
        Wrap<? extends Button> label = null;
        boolean ok = false;
        for (int k = 0; (k < 6) && (false == ok); ++k) {
            try {
                label = Lookups.byID(getScene(), name, Button.class);
                if (null != label) {
                    ok = true;
                }
            } catch (Exception e) {
            }
        }

        label.mouse().move();
        label.mouse().click();
    }
    private void doTest(final String testName){
        openPage(testName);
        clickButton("Start");
        final Wrap<? extends Text> txtS = waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }

    private Wrap<? extends Text> waitStopped() {
        final Wrap<? extends Text> txtS = Lookups.byID(getScene(), CheckedIds.currentState.name(), Text.class);
        txtS.waitState(new State<String>() {
            public String reached() {
                return txtS.getControl().getText();
            }
        },  Status.STOPPED.toString());

// was:     Thread.sleep(AnimationTransitionApp.typicalDuration);
        return txtS;
    }

/**
 *
 * test PathTransition() with SVGPath
 */
    @Test
    public void TransitionSvgPath() throws InterruptedException {
        doTest(Pages.TransitionSvgPath.name());
    }
/**
 *
 * test PathTransition() with Path
 */
    @Test
    public void TransitionPath() throws InterruptedException {
        openPage(Pages.TransitionPath.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(getScene(), CheckedIds.currentState.name(), Text.class);
        waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }
/**
 *
 * test FadeTransition() from default to value
 */
    @Test
    public void TransitionFadeFromDefaultToValue() throws InterruptedException {
        doTest(Pages.TransitionFadeFromDefaultToValue.name());
    }
/**
 *
 * test FadeTransition() from predefined by value
 */
    @Test
    public void TransitionFadeFromPredefinedByValue() throws InterruptedException {
        doTest(Pages.TransitionFadeFromPredefinedByValue.name());
    }
/**
 *
 * test RotateTransition() to angle
 */
    @Test
    public void TransitionRotateToAngle() throws InterruptedException {
        doTest(Pages.TransitionRotateToAngle.name());
    }
/**
 *
 * test RotateTransition() by angle
 */
    @Test
    public void TransitionRotateByAngle() throws InterruptedException {
        doTest(Pages.TransitionRotateByAngle.name());
    }
/**
 *
 * test TranslateTransition() by value
 */
    @Test
    public void TransitionTranslateBy() throws InterruptedException {
        doTest(Pages.TransitionTranslateBy.name());
    }
/**
 *
 * test TranslateTransition() to value
 */
    @Test
    public void TransitionTranslateTo() throws InterruptedException {
        doTest(Pages.TransitionTranslateTo.name());
    }
/**
 *
 * test ScaleTransition() to value
 */
    @Test
    public void TransitionScaleTo() throws InterruptedException {
        doTest(Pages.TransitionScaleTo.name());
    }
/**
 *
 * test ScaleTransition() by value
 */
    @Test
    public void TransitionScaleBy() throws InterruptedException {
        doTest(Pages.TransitionScaleBy.name());
    }
/**
 *
 * test ParallelTransition()
 */
    @Test
    public void TransitionParallel() throws InterruptedException {
        doTest(Pages.TransitionParallel.name());
    }
/**
 *
 * test SequentialTransition()
 */
    @Test
    public void TransitionSequential() throws InterruptedException {
        doTest(Pages.TransitionSequential.name());
    }

/**
 *
 * test FillTransition()
 */
    @Test
    public void TransitionFill() throws InterruptedException {
        doTest(Pages.TransitionFill.name());
    }

/**
 *
 * test StrokeTransition()
 */
    @Test
    public void TransitionStroke() throws InterruptedException {
        doTest(Pages.TransitionStroke.name());
    }


}
