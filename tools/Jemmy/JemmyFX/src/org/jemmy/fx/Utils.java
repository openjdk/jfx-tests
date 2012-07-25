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
package org.jemmy.fx;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import org.jemmy.JemmyException;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.control.Wrap;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.Caret;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;

/**
 *
 * @author andrey
 */
public class Utils {

    public static AbstractScroll getContainerScroll(Parent<Node> parent, final boolean vertical) {
        Lookup<ScrollBar> lookup = parent.lookup(ScrollBar.class,
                new LookupCriteria<ScrollBar>() {

                    @Override
                    public boolean check(ScrollBar control) {
                        return (control.getOrientation() == Orientation.VERTICAL) == vertical
                                && control.isVisible();
                    }
                });
        int count = lookup.size();
        if (count == 0) {
            return null;
        } else if (count == 1) {
            return lookup.as(AbstractScroll.class);
        } else {
            throw new JemmyException("There are more than 1 " + (vertical ? "vertical" : "horizontal")
                    + " ScrollBars in this " + parent.getClass().getSimpleName());
        }
    }

    public static void makeCenterVisible(final Wrap parent, final Wrap cell, AbstractScroll scroll) {
        if (scroll != null) {
            Caret c = scroll.caret();
            Caret.Direction direction = new Caret.Direction() {

                public int to() {
                    Rectangle itemBounds = cell.getScreenBounds();
                    Rectangle parentBounds = parent.getScreenBounds();
                    Point center = new Point(itemBounds.x + itemBounds.width / 2., itemBounds.y + itemBounds.height / 2.);
                    if (center.x < parentBounds.x || center.y < parentBounds.y) {
                        return -1;
                    }
                    if (center.x > parentBounds.x + parentBounds.width || center.y > parentBounds.y + parentBounds.height) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            };
            c.to(direction);
        }
    }
}
