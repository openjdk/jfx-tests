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

import javafx.scene.Scene;
import javafx.scene.control.Button;
import org.jemmy.fx.Root;
import org.jemmy.fx.Lookups;
import org.jemmy.control.Wrap;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.app.TimelineApp;
import test.javaclient.shared.TestBase;
import static org.junit.Assert.*;

/**
 *
 * @author Sergey Grinev
 */
public class TimelineTest extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        TimelineApp.main(null);
    }

    @Test
    public void simpleTimeline() throws InterruptedException {
        doTest("1", 0, length, new IndexStrategy() {
            public int getIndex(int i) {
                return i;
            }
        });
    }

    @Test
    public void reverse() throws InterruptedException {
        doTest("2", 0, length * 2 - 1, new IndexStrategy() {
            public int getIndex(int i) {
                return i < length ? i : length * 2 - 2 - i;
            }
        });
    }

    @Test
    public void infiniteStop() throws InterruptedException {
        final String id = "3";
        final int stopStep = 2;
        doTest(id, 0, length * 2 + stopStep + 1, new IndexStrategy() {
            public int getIndex(int i) {
                return i % length;
            }
        });
        clickButton("stop");
        checkRect(id, stopStep);
        // check if timeline started from the beginning
        doTest(id, 0, length, new IndexStrategy() {
            public int getIndex(int i) {
                return i;
            }
        });
        // just for cleanup
        clickButton("stop");
    }

    @Test
    public void infinitePause() throws InterruptedException {
        final String id = "4";
        final int stopStep = 2;
        doTest(id, 0, length + stopStep + 1, new IndexStrategy() {
            public int getIndex(int i) {
                return i % length;
            }
        });
        clickButton("pause");
        checkRect(id, stopStep);
        // check if timeline continues
        doTest(id, stopStep + 1, length + stopStep, new IndexStrategy() {
            public int getIndex(int i) {
                return i%length;
            }
        });
        // just for cleanup
        clickButton("pause");
    }

    private interface IndexStrategy { // if only we had closures
        int getIndex(int i);
    }

    private static final int length = TimelineApp.colors.length;

    private void doTest(String id, int from, int to, IndexStrategy cl) {
        clickButton("play" + id);

        for (int i = from; i < to; i++) {
            int idx = cl.getIndex(i);
            System.err.println("waiting " + idx);
            checkRect(id, idx);
        }
    }

    private void checkRect(String id, int idx) {
        Wrap<? extends Rectangle> rect = Lookups.byID(getScene(), "rect_" + id + "_" + idx, Rectangle.class);
        Paint color = rect.getControl().getFill();
        assertEquals(color, TimelineApp.colors[idx]);
    }

    protected void clickButton(String name) {
        final Wrap<? extends Button> button = Lookups.byText(getScene(), name, Button.class);
        button.mouse().click();
    }
}
