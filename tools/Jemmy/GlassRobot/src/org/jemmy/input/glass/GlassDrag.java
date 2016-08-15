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
package org.jemmy.input.glass;

import org.jemmy.Point;
import org.jemmy.action.Action;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.interfaces.Drag;
import org.jemmy.interfaces.Modifier;
import org.jemmy.interfaces.Mouse.MouseButton;
import org.jemmy.interfaces.Mouse.MouseButtons;
import org.jemmy.interfaces.Showable;

/**
 *
 * @author shura
 */
public class GlassDrag implements Drag {

    public static final int DND_POINTS = 10;

    static {
        Environment.getEnvironment().setTimeout(BEFORE_DRAG_TIMEOUT);
        Environment.getEnvironment().setTimeout(BEFORE_DROP_TIMEOUT);
        Environment.getEnvironment().setTimeout(IN_DRAG_TIMEOUT);
    }

    Wrap<?> source;

    public GlassDrag(Wrap<?> source) {
        this.source = source;
    }

    @Override
    public void dnd(Point point) {
        dnd(source, point);
    }

    @Override
    public void dnd(Wrap wrap, Point point) {
        dnd(null, wrap, point);
    }

    @Override
    public void dnd(Point point, Wrap wrap, Point point1) {
        dnd(point, wrap, point1, MouseButtons.BUTTON1);
    }

    @Override
    public void dnd(Point point, Wrap wrap, Point point1, MouseButton mb) {
        dnd(point, wrap, point1, mb, new Modifier[0]);
    }

    @Override
    public void dnd(Point orig, final Wrap target, final Point dest, final MouseButton mb, final Modifier... mdfrs) {
        final Point pnt = (orig == null) ? source.getClickPoint() : orig;
        source.getEnvironment().getExecutor().execute(source.getEnvironment(), false,
                new Action() {

                    @Override
                    public void run(Object... os) throws Exception {
                        if (source.is(Showable.class)) {
                            source.as(Showable.class).shower().show();
                        }
                        source.mouse().move(pnt);
                        source.mouse().press(mb, mdfrs);
                        source.getEnvironment().getTimeout(BEFORE_DRAG_TIMEOUT.getName()).sleep();
                        Point intermediatePoint = new Point();
                        int xDistance = target.getScreenBounds().x + dest.x - source.getScreenBounds().x - pnt.x;
                        int yDistance = target.getScreenBounds().y + dest.y - source.getScreenBounds().y - pnt.y;
                        int startX = pnt.x + source.getScreenBounds().x;
                        int startY = pnt.y + source.getScreenBounds().y;
                        int endX = startX + xDistance;
                        int endY = startY + yDistance;
                        for(int i = 0; i < DND_POINTS + 1; i++) {
                            intermediatePoint.x = startX + xDistance * i / DND_POINTS - source.getScreenBounds().x;
                            intermediatePoint.y = startY + yDistance * i / DND_POINTS - source.getScreenBounds().y;
                            source.mouse().move(intermediatePoint);
                            source.getEnvironment().getTimeout(IN_DRAG_TIMEOUT.getName()).sleep();
                        }
                        source.mouse().move(new Point(endX - source.getScreenBounds().x, endY - source.getScreenBounds().y));
                        source.getEnvironment().getTimeout(BEFORE_DROP_TIMEOUT.getName()).sleep();
                        target.mouse().release(mb, mdfrs);                    }
                });
    }
}
