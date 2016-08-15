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
package javafx.scrollEvent;

import client.test.OnlyPlatformMethod;
import client.test.Platforms;
import test.javaclient.shared.FilteredTestRunner;
import org.junit.runner.RunWith;
import client.test.Smoke;
import org.jemmy.fx.ByID;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.jemmy.interfaces.Selectable;
import org.junit.Test;
import static org.junit.Assert.*;
import static javafx.scrollEvent.ScrollEventApp.*;
import javafx.stage.PopupWindow;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Keyboard.*;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Mouse;
import org.jemmy.lookup.Lookup;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class ScrollEventTest extends TestBase {

    @Smoke
    @Test(timeout=300000)
    public void commonTest() {
        for (Object nf : nodeChooser.as(Selectable.class).getStates()) {
            System.out.println("Testing " + nf.toString());
            nodeChooser.as(Selectable.class).selector().select(nf);
            targetControl = parent.lookup(Node.class, new ByID<Node>(ID_TARGET_NODE)).wrap();
            applyTest(+1, Direction.UP);
            applyTest(-1, Direction.DOWN);
        }
    }

    @Smoke
    @Test(timeout=300000)
    public void horizontalTest() throws InterruptedException {
        for (Object nf : nodeChooser.as(Selectable.class).getStates()) {
            nodeChooser.as(Selectable.class).selector().select(nf);
            targetControl = parent.lookup(Node.class, new ByID<Node>(ID_TARGET_NODE)).wrap();

            sendScrollEvent(scene, targetControl, 13, 19);
            checkParameters(13, 19, nf.toString());

            Thread.sleep(1000);

            sendScrollEvent(scene, targetControl, -13, -19);
            checkParameters(-13, -19, nf.toString());
        }
    }

    /**
     * This test checks that context menu appears
     * when different key combinations are pressed.
     *
     */
    @Smoke
    @OnlyPlatformMethod(Platforms.WINDOWS)
    @Test(timeout=300000)
    public void contextMenuKeyCombinationsTestWindows() {
        enableContextMenuTest();

        for (Object nf : nodeChooser.as(Selectable.class).getStates()) {
            nodeChooser.as(Selectable.class).selector().select(nf);

            targetControl = parent.lookup(Node.class, new ByID<Node>(ID_TARGET_NODE)).wrap();

            //Test right mouse button click
            int expectedCounterValue = 1;
            targetControl.mouse().click(1, targetControl.getClickPoint(), Mouse.MouseButtons.BUTTON3);

            /**
             * Test can fail on the next line, if screen is not hight, and there was an error on selecting item from the dropdown.
             */

            checkListener(ScrollEventApp.getContextMenuOnShownCounterID(), expectedCounterValue);

            closeContextMenu();

            //Test Shift + F10 combination
            expectedCounterValue++;
            targetControl.keyboard().pushKey(KeyboardButtons.F10, KeyboardModifiers.SHIFT_DOWN_MASK);
            checkListener(ScrollEventApp.getContextMenuOnShownCounterID(), expectedCounterValue);

            closeContextMenu();

            //Test context menu button
            expectedCounterValue++;
            targetControl.keyboard().pushKey(KeyboardButtons.CONTEXT_MENU);
            checkListener(ScrollEventApp.getContextMenuOnShownCounterID(), expectedCounterValue);
        }
    }

     /**
     * This test checks that context menu appears
     * when different key combinations are pressed.
     *
     */
    @Smoke
    @OnlyPlatformMethod(Platforms.MAC)
    @Test(timeout=300000)
    public void contextMenuKeyCombinationsTestMac() {
        enableContextMenuTest();

        for (Object nf : nodeChooser.as(Selectable.class).getStates()) {
            nodeChooser.as(Selectable.class).selector().select(nf);

            targetControl = parent.lookup(Node.class, new ByID<Node>(ID_TARGET_NODE)).wrap();

            //Test right mouse button click
            int expectedCounterValue = 1;
            targetControl.mouse().click(1, targetControl.getClickPoint(), Mouse.MouseButtons.BUTTON3);
            checkListener(ScrollEventApp.getContextMenuOnShownCounterID(), expectedCounterValue);

            closeContextMenu();

            //Test left mouse button click
            expectedCounterValue++;
            targetControl.mouse().click(1, targetControl.getClickPoint(), Mouse.MouseButtons.BUTTON1, KeyboardModifiers.CTRL_DOWN_MASK);
            checkListener(ScrollEventApp.getContextMenuOnShownCounterID(), expectedCounterValue);
        }
    }

    /**
     * This test checks that context menu appears
     * when different key combinations are pressed.
     *
     */
    @Smoke
    @OnlyPlatformMethod(Platforms.UNIX)
    @Test(timeout=300000)
    public void contextMenuKeyCombinationsTestLinux() {
        enableContextMenuTest();

        for (Object nf : nodeChooser.as(Selectable.class).getStates()) {
            nodeChooser.as(Selectable.class).selector().select(nf);

            targetControl = parent.lookup(Node.class, new ByID<Node>(ID_TARGET_NODE)).wrap();

            //Test right mouse button click
            int expectedCounterValue = 1;

            targetControl.mouse().click(1, targetControl.getClickPoint(), Mouse.MouseButtons.BUTTON3);
            checkListener(ScrollEventApp.getContextMenuOnShownCounterID(), expectedCounterValue);

            closeContextMenu();

            //Test context menu button
            expectedCounterValue++;
            targetControl.keyboard().pushKey(KeyboardButtons.CONTEXT_MENU);
            checkListener(ScrollEventApp.getContextMenuOnShownCounterID(), expectedCounterValue);
        }
    }

    /**
     * This test checks that context menu appears
     * but there are no other popup windows
     *
     * Input: each control gets right mouse click
     * Expected output: only one new window appears on the stage
     *
     */
    @Smoke
    @Test(timeout=300000)
    public void contextMenuPopupTest() {
        enableContextMenuTest();

        for (Object nf : nodeChooser.as(Selectable.class).getStates()) {
            nodeChooser.as(Selectable.class).selector().select(nf);

            targetControl = parent.lookup(Node.class, new ByID<Node>(ID_TARGET_NODE)).wrap();
            targetControl.mouse().click(1, targetControl.getClickPoint(), Mouse.MouseButtons.BUTTON3);

            Lookup<Scene> popUpLookup = Root.ROOT.lookup(new ByWindowType(PopupWindow.class)).lookup(Scene.class);
            assertEquals("There must be only one popup window for control <" + nf + ">", 1, popUpLookup.size());
        }
    }
}
