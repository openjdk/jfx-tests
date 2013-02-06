/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
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

import test.scenegraph.app.AnimationApp;
import test.javaclient.shared.TestBase;
import test.scenegraph.app.AnimationApp.CheckedIds;
import test.scenegraph.app.AnimationApp.Pages;

public class AnimationTest extends TestBase {
    
    //@RunUI
    @BeforeClass
    public static void runUI() {
        AnimationApp.main(null);
    }
    

    protected void clickButton(String name) {
        Wrap<? extends Button> label = null;
        while (null == label) {  // TODO: remove this workaround
            try {
                label = Lookups.byID(scene, name, Button.class);
            } catch (Exception e) {
            }
        }
        label.mouse().move();
        label.mouse().move();
        label.mouse().press();
        label.mouse().release();
    }

/**
 *
 * test  keyValue(WritableObjectValue ...
 */
    @Test
    public void KeyValueObject() throws InterruptedException {
        openPage(Pages.KeyValueCtorObject.name());
        clickButton("Start");
        try { Thread.sleep(3000); } catch (Exception e) { }
        final Wrap<? extends Text> txtEnd1 = Lookups.byID(scene, "txtEnd1", Text.class);
        final Wrap<? extends Text> txtEnd2 = Lookups.byID(scene, "txtEnd2", Text.class);
        assertEquals("end1", txtEnd1.getControl().getText());
        assertEquals("end2", txtEnd2.getControl().getText());
        return;
    }

/**
 *
 * test  keyValue(WritableLongValue ...
 */
    @Test
    public void KeyValueLong() throws InterruptedException {
        openPage(Pages.KeyValueCtorLong.name());
        clickButton("Start");
        try { Thread.sleep(3000); } catch (Exception e) { }
        final Wrap<? extends Text> txtX = Lookups.byID(scene, CheckedIds.finishX.name(), Text.class);
        final Wrap<? extends Text> txtY = Lookups.byID(scene, CheckedIds.finishY.name(), Text.class);
        assertEquals("finish X = 300", txtX.getControl().getText());
        assertEquals("finish Y = 200", txtY.getControl().getText());
        return;
    }

/**
 *
 * test  keyValue(WritableIntegerValue ...
 */
    @Test
    public void KeyValueInteger() throws InterruptedException {
        openPage(Pages.KeyValueCtorInteger.name());
        clickButton("Start");
        try { Thread.sleep(3000); } catch (Exception e) { }
        final Wrap<? extends Text> txtX = Lookups.byID(scene, CheckedIds.finishX.name(), Text.class);
        final Wrap<? extends Text> txtY = Lookups.byID(scene, CheckedIds.finishY.name(), Text.class);
        assertEquals("finish X = 300", txtX.getControl().getText());
        assertEquals("finish Y = 200", txtY.getControl().getText());
        return;
    }

/**
 *
 * test  keyValue(WritableFloatValue ...
 */
    @Test
    public void KeyValueFloat() throws InterruptedException {
        openPage(Pages.KeyValueCtorFloat.name());
        clickButton("Start");
        try { Thread.sleep(3000); } catch (Exception e) { }
        final Wrap<? extends Text> txtY = Lookups.byID(scene, CheckedIds.onFinishFloatCounter.name(), Text.class);
        assertEquals("1", txtY.getControl().getText());
        return;
    }

/**
 *
 * test  javafx.animation.Animation.Status of timeline
 */
    @Test
    public void Speed() throws InterruptedException {
        openPage(Pages.Speed.name());
        clickButton("Start");
        try { Thread.sleep(1000); } catch (Exception e) { }
        final Wrap<? extends Text> txtS = Lookups.byID(scene, CheckedIds.currentState.name(), Text.class);
        assertEquals(Status.RUNNING, Status.valueOf(txtS.getControl().getText()));

        clickButton("Pause");
        try { Thread.sleep(150); } catch (Exception e) { }
        assertEquals(Status.PAUSED, Status.valueOf(txtS.getControl().getText()));

        clickButton("Stop");
        try { Thread.sleep(50); } catch (Exception e) { }
        assertEquals(Status.STOPPED, Status.valueOf(txtS.getControl().getText()));

        return;
    }

/**
 *
 * test  javafx.animation.Animation.Status of timeline
 */
    @Test
    public void KeyValue() throws InterruptedException {
        openPage(Pages.KeyValue.name());
        clickButton("Start");
        try { Thread.sleep(3000); } catch (Exception e) { }
        final Wrap<? extends Text> txtX = Lookups.byID(scene, CheckedIds.currentX.name(), Text.class);
        final Wrap<? extends Text> txtY = Lookups.byID(scene, CheckedIds.currentY.name(), Text.class);
        assertEquals("Translate X = 300", txtX.getControl().getText());
        assertEquals("Translate Y = 200", txtY.getControl().getText());
        return;
    }

/**
 *
 * test  keyValue(WritableBooleanValue ...
 */
    @Test
    public void KeyValueCtorBoolean() throws InterruptedException {
        openPage(Pages.KeyValueCtorBoolean.name());
        clickButton("PlayFromDuration");
        try { Thread.sleep(3000); } catch (Exception e) { }
        clickButton("PlayFromCuepoint");
        try { Thread.sleep(3000); } catch (Exception e) { }
        clickButton("Stop");
        return;
    }
}
