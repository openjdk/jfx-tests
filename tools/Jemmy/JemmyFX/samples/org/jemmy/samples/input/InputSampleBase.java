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
 * questions.
 */
package org.jemmy.samples.input;

import java.util.HashMap;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jemmy.control.Wrap;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.input.AWTRobotInputFactory;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.samples.SampleBase;
import org.jemmy.timing.State;
import org.junit.After;
import org.junit.BeforeClass;

/**
 *
 * @author shura
 */
public class InputSampleBase extends SampleBase {

    protected static SceneDock scene;
    protected static NodeDock redTarget;
    protected static NodeDock blueTarget;

    @BeforeClass
    public static void before() throws InterruptedException {
        startApp(InputApp.class);
        scene = new SceneDock();
        redTarget = new NodeDock(scene.asParent(), Rectangle.class, new TargetCriteria(Color.RED));
        blueTarget = new NodeDock(scene.asParent(), Rectangle.class, new TargetCriteria(Color.BLUE));
    }

    @After
    public void after() throws InterruptedException {
        Thread.sleep(1000);
    }

    private static class TargetCriteria implements LookupCriteria<Rectangle> {

        private Color color;

        public TargetCriteria(Color color) {
            this.color = color;
        }

        public boolean check(Rectangle cntrl) {
            return cntrl.getWidth() == 100 && cntrl.getHeight() == 100 && cntrl.getFill().equals(color);
        }

        @Override
        public String toString() {
            return "100x100 rectangle of " + color.toString();
        }
    }

    protected void checkMouseEvent(final Color color, final EventType type, final MouseButton btn, final int x, final int y, final int times) {

        Root.ROOT.getEnvironment().getWaiter(Wrap.WAIT_STATE_TIMEOUT).ensureValue(true, new State<Boolean>() {

            public Boolean reached() {
                if (InputApp.events.size() > 0) {
                    for (Event e : InputApp.events.toArray(new Event[0])) {
                        if (e instanceof MouseEvent) {
                            if (e.getEventType().equals(type)
                                    && ((Rectangle) e.getSource()).getFill().equals(color)
                                    && ((MouseEvent) e).getButton().equals(btn)
                                    && ((MouseEvent) e).getX() == x
                                    && ((MouseEvent) e).getY() == y
                                    && ((MouseEvent) e).getClickCount() == times) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        });
    }

    protected void checkKeyboardEvent(final EventType type, final KeyCode btn, final String character) {

        Root.ROOT.getEnvironment().getWaiter(Wrap.WAIT_STATE_TIMEOUT).ensureValue(true, new State<Boolean>() {

            public Boolean reached() {
                for (Event e : InputApp.events.toArray(new Event[0])) {
                    if (e instanceof KeyEvent) {
                        if (e.getEventType().equals(type)
                                && ((KeyEvent) e).getCode().equals(btn)
                                && (!e.getEventType().equals(KeyEvent.KEY_TYPED) || ((KeyEvent) e).getCharacter().equals(character))) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }
}
