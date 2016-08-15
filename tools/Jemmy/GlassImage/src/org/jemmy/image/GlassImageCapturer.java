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
package org.jemmy.image;

import com.sun.glass.ui.Application;
import com.sun.glass.ui.Robot;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.timing.State;

/**
 *
 * @author shura
 */
public class GlassImageCapturer implements ImageCapturer{
    public static final Timeout WAIT_FACTORY = new Timeout("wait.for.robot.init", 60000);
    private static Robot robot;
    private static Environment env = null;

    static {
        Environment.getEnvironment().setTimeout(WAIT_FACTORY);
        try {
            //call GlassImage static init
            Class.forName(GlassImage.class.getName());
        } catch (ClassNotFoundException ex) {
        }
    }

    public static void setInitEnvironment(Environment e) {
        env = e;
    }

    @Override
    public GlassImage capture(final Wrap<?> wrap, final Rectangle rctngl) {
        final Rectangle rect = wrap.getScreenBounds();
        return new GetAction<GlassImage>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(new GlassImage(wrap.getEnvironment(),
                        getRobot().
                        getScreenCapture(rect.x + rctngl.x, rect.y + rctngl.y, rctngl.width, rctngl.height)));
            }
        }.dispatch(wrap.getEnvironment());
    }

    public static Robot getRobot() {
        if (robot == null) {
            robot = Environment.getEnvironment().getWaiter(WAIT_FACTORY).ensureState(new State<Robot>() {

                @Override
                public Robot reached() {
                    try {
                        return new GetAction<Robot>() {
                            @Override
                            public void run(Object... os) throws Exception {
                                setResult(Application.GetApplication().createRobot());
                            }
                        }.dispatch(env);
                    } catch (Exception e) {
                        return null;
                    }
                }

                @Override
                public String toString() {
                    return "Waiting for the glass robot to init.";
                }
            });
        }
        return robot;
    }
}
