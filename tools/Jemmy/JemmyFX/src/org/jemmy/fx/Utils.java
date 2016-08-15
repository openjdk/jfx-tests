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

/**
 *
 * @author andrey
 */
public class Utils {

    public static AbstractScroll getContainerScroll(Parent<Node> parent, final boolean vertical) {
        Lookup<ScrollBar> lookup = parent.lookup(ScrollBar.class,
                control -> (control.getOrientation() == Orientation.VERTICAL) == vertical
                        && control.isVisible());
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

    /**
     * This method is specially for cases, when navigation to be done in only 1
     * direction - vertical or horizontal (listView in horizontal or vertical
     * orientation; treeView). It will not work, if scrolling is needed in both
     * coordinates (like positioning on cell in TableView or TreeTableView).
     *
     * @param parent
     * @param cell
     * @param scroll
     */
    public static void makeCenterVisible(final Wrap parent, final Wrap cell, AbstractScroll scroll) {
        makeCenterVisibleCommon(scroll, new CenterDirection(parent, cell, null) {
            public int to(Rectangle parentBounds, Rectangle itemBounds, Point center) {
                if (center.x < parentBounds.x || center.y < parentBounds.y) {
                    return -1;
                }
                if (center.x > parentBounds.x + parentBounds.width || center.y > parentBounds.y + parentBounds.height) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

    /**
     * Method is for positioning on cells in TreeTableView and TableView. You
     * need to specify an orientation, in which to navigate. You will need two
     * this method calls - for each scroller for each orientation.
     *
     * @param parent
     * @param cell
     * @param scroll
     * @param orientation
     */
    public static void makeCenterVisible(final Wrap parent, final Wrap cell, AbstractScroll scroll, final Orientation orientation) {
        makeCenterVisibleCommon(scroll, new CenterDirection(parent, cell, orientation) {
            public int to(Rectangle parentBounds, Rectangle itemBounds, Point center) {
                if (Orientation.HORIZONTAL.equals(orientation)) {
                    if (center.x < parentBounds.x + parentBounds.width / 2.0 - parentBounds.width / 4.0) {
                        return -1;
                    }
                    if (center.x > parentBounds.x + parentBounds.width / 2.0 + parentBounds.width / 4.0) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    if (center.y < parentBounds.y) {
                        return -1;
                    }
                    if (center.y > parentBounds.y + parentBounds.height) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        });
    }

    private static void makeCenterVisibleCommon(AbstractScroll scroll, final CenterDirection direction) {
        if (scroll != null) {
            Caret c = scroll.caret();
            c.to(direction);
        }
    }

    private static abstract class CenterDirection implements Caret.Direction {

        Wrap parent;
        Wrap cell;
        Orientation orientation;

        public CenterDirection(final Wrap parent, final Wrap cell, Orientation orientation) {
            this.parent = parent;
            this.cell = cell;
            this.orientation = orientation;
        }

        @Override
        public int to() {
            Rectangle parentBounds = parent.getScreenBounds();
            Rectangle itemBounds = cell.getScreenBounds();
            Point center = new Point(itemBounds.x + itemBounds.width / 2., itemBounds.y + itemBounds.height / 2.);
            return to(parentBounds, itemBounds, center);
        }

        protected abstract int to(Rectangle parentBounds, Rectangle itemBounds, Point center);
    }
}