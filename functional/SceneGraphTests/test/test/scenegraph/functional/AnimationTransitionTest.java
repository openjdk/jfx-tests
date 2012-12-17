/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.scenegraph.functional;

import javafx.animation.Animation.Status;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.jemmy.fx.Lookups;
import org.jemmy.control.Wrap;
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
                label = Lookups.byID(scene, name, Button.class);
                if (null != label) {
                    ok = true;
                }
            } catch (Exception e) {
            }
        }

        label.mouse().move();
        label.mouse().click();
    }

/**
 *
 * test PathTransition() with SVGPath
 */
    @Test
    public void TransitionSvgPath() throws InterruptedException {
        openPage(Pages.TransitionSvgPath.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }
/**
 *
 * test PathTransition() with Path
 */
    @Test
    public void TransitionPath() throws InterruptedException {
        openPage(Pages.TransitionPath.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
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
        openPage(Pages.TransitionFadeFromDefaultToValue.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }
/**
 *
 * test FadeTransition() from predefined by value
 */
    @Test
    public void TransitionFadeFromPredefinedByValue() throws InterruptedException {
        openPage(Pages.TransitionFadeFromPredefinedByValue.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }
/**
 *
 * test RotateTransition() to angle
 */
    @Test
    public void TransitionRotateToAngle() throws InterruptedException {
        openPage(Pages.TransitionRotateToAngle.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }
/**
 *
 * test RotateTransition() by angle
 */
    @Test
    public void TransitionRotateByAngle() throws InterruptedException {
        openPage(Pages.TransitionRotateByAngle.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }
/**
 *
 * test TranslateTransition() by value
 */
    @Test
    public void TransitionTranslateBy() throws InterruptedException {
        openPage(Pages.TransitionTranslateBy.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }
/**
 *
 * test TranslateTransition() to value
 */
    @Test
    public void TransitionTranslateTo() throws InterruptedException {
        openPage(Pages.TransitionTranslateTo.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }
/**
 *
 * test ScaleTransition() to value
 */
    @Test
    public void TransitionScaleTo() throws InterruptedException {
        openPage(Pages.TransitionScaleTo.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }
/**
 *
 * test ScaleTransition() by value
 */
    @Test
    public void TransitionScaleBy() throws InterruptedException {
        openPage(Pages.TransitionScaleBy.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }
/**
 *
 * test ParallelTransition()
 */
    @Test
    public void TransitionParallel() throws InterruptedException {
        openPage(Pages.TransitionParallel.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }
/**
 *
 * test SequentialTransition()
 */
    @Test
    public void TransitionSequential() throws InterruptedException {
        openPage(Pages.TransitionSequential.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }

/**
 *
 * test FillTransition()
 */
    @Test
    public void TransitionFill() throws InterruptedException {
        openPage(Pages.TransitionFill.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }

/**
 *
 * test StrokeTransition()
 */
    @Test
    public void TransitionStroke() throws InterruptedException {
        openPage(Pages.TransitionStroke.name());
        clickButton("Start");
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        waitStopped();
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));
        verifyFailures();
    }

    private void waitStopped() {
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        for (int k = 0; k < 6; k++) {
            try {
                Thread.sleep(AnimationTransitionApp.typicalDuration);
            } catch (Exception e) { }
            if ( Status.STOPPED.toString().equals(txtS.getControl().getText())  )
                break;
        }
    }

}
