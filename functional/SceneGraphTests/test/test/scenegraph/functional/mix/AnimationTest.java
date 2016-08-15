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
import javafx.scene.Node;
import javafx.scene.text.Text;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Lookups;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import test.scenegraph.app.AnimationApp;
import test.scenegraph.app.AnimationApp.CheckedIds;
import test.scenegraph.app.AnimationApp.Pages;

public class AnimationTest extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        AnimationApp.main(null);
    }


    protected void clickButton(String name) {
        Wrap<? extends Node> label = null;
        while (null == label) {  // TODO: remove this workaround
            try {
                label = Lookups.byID(getScene(), name, Node.class);
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
        final Wrap<? extends Text> txtEnd1 = Lookups.byID(getScene(), "txtEnd1", Text.class);
        final Wrap<? extends Text> txtEnd2 = Lookups.byID(getScene(), "txtEnd2", Text.class);
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
        final Wrap<? extends Text> txtX = Lookups.byID(getScene(), CheckedIds.finishX.name(), Text.class);
        final Wrap<? extends Text> txtY = Lookups.byID(getScene(), CheckedIds.finishY.name(), Text.class);
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
        final Wrap<? extends Text> txtX = Lookups.byID(getScene(), CheckedIds.finishX.name(), Text.class);
        final Wrap<? extends Text> txtY = Lookups.byID(getScene(), CheckedIds.finishY.name(), Text.class);
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
        final Wrap<? extends Text> txtY = Lookups.byID(getScene(), CheckedIds.onFinishFloatCounter.name(), Text.class);
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
        final Wrap<? extends Text> txtS = Lookups.byID(getScene(), CheckedIds.currentState.name(), Text.class);
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
        final Wrap<? extends Text> txtX = Lookups.byID(getScene(), CheckedIds.currentX.name(), Text.class);
        final Wrap<? extends Text> txtY = Lookups.byID(getScene(), CheckedIds.currentY.name(), Text.class);
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
