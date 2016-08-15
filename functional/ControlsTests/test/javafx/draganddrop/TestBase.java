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
package javafx.draganddrop;

import com.sun.glass.ui.Application;
import com.sun.glass.ui.Robot;
import javafx.scene.control.test.ControlsTestBase;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import test.javaclient.shared.Utils;

/**
 * @author Andrey Nazarov
 */
public class TestBase extends ControlsTestBase {

    static Robot robot = null;

    static {
        if (Utils.isMacOS()) {
            runInOtherJVM(true);
        }
    }

    // It's a stub. Currently mac's implementation of glass robot is incorrect, Jemmy's remote robot requires
    // additional unwanted permissions while running in jnlp mode. So now we use glass robot for win and Jemmy
    // remote robot for mac using the fact of absence of jnlp mode for it.
    protected void dnd(Wrap from, Point from_point, Wrap to, Point to_point) throws InterruptedException {
        if (!Utils.isWindows()) {
            System.err.println("Use jemmy robot");
            from.mouse().move(from_point);
            from.mouse().press();
            final int STEPS = 50;
            int differenceX = to.toAbsolute(to_point).x - from.toAbsolute(from_point).x;
            int differenceY = to.toAbsolute(to_point).y - from.toAbsolute(from_point).y;
            for (int i = 0; i <= STEPS; i++) {
                from.mouse().move(new Point(from_point.x + differenceX * i / STEPS, from_point.y + differenceY * i / STEPS));
                Thread.sleep(20);
            }
            from.mouse().release();
        } else {
            System.err.println("Use glass robot");
            final Point abs_from_point = new Point(from_point);
            abs_from_point.translate((int) from.getScreenBounds().getX(), (int) from.getScreenBounds().getY());
            final Point abs_to_point = new Point(to_point);
            abs_to_point.translate((int) to.getScreenBounds().getX(), (int) to.getScreenBounds().getY());
            if (robot == null) {
                robot = new GetAction<com.sun.glass.ui.Robot>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(com.sun.glass.ui.Application.GetApplication().createRobot());
                    }
                }.dispatch(Root.ROOT.getEnvironment()); // can not be done in static block due to initialization problems on Mac
            }
            Application.invokeAndWait(new Runnable() {

                public void run() {
                    robot.mouseMove(abs_from_point.x, abs_from_point.y);
                }
            });

            Application.invokeAndWait(new Runnable() {

                public void run() {
                    robot.mousePress(1);
                }
            });

            final int STEPS = 50;
            final int differenceX = abs_to_point.x - abs_from_point.x;
            final int differenceY = abs_to_point.y - abs_from_point.y;
            for (int ii = 0; ii <= STEPS; ii++) {
                final int i = ii;
                Application.invokeAndWait(new Runnable() {

                    public void run() {
                        robot.mouseMove(abs_from_point.x + differenceX * i / STEPS, abs_from_point.y + differenceY * i / STEPS);
                    }
                });

                Thread.sleep(20);
            }
            Application.invokeAndWait(new Runnable() {

                public void run() {
                    robot.mouseRelease(1);
                }
            });

        }
        Thread.sleep(100);
    }
}
