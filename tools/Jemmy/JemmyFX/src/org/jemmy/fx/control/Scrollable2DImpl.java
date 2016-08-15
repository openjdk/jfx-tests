/*
 * Copyright (c) 2012-2013, Oracle and/or its affiliates. All rights reserved.
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
package org.jemmy.fx.control;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollBar;
import org.jemmy.JemmyException;
import org.jemmy.Point;
import org.jemmy.action.FutureAction;
import org.jemmy.control.As;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.dock.Shortcut;
import org.jemmy.fx.Root;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.*;
import org.jemmy.interfaces.Caret.Direction;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;

/**
 * That is common implementation for Scrollable2D interface, which allows to
 * work with two scrolls.
 *
 * @author Alexander Kirov
 */
public class Scrollable2DImpl<SCROLL_CONTROL extends ScrollBar> implements Scrollable2D {

    private AbstractScroll emptyScroll = new EmptyScroll();
    private static Scroller emptyScroller = new EmptyScroller();
    private AbstractScroll hScroll, vScroll;
    private ScrollsLookupCriteria<SCROLL_CONTROL> scrollsLookupCriteria;
    private Wrap<? extends Control> scrolledControl;

    public Scrollable2DImpl(Wrap<? extends Control> control, ScrollsLookupCriteria<SCROLL_CONTROL> scrollsLookupCriteria) {
        this.scrolledControl = control;
        this.scrollsLookupCriteria = scrollsLookupCriteria;
    }

    @As(Orientation.class)
    public Scroll asVerticalScroll() {
        checkScrolls();
        if (vScroll != null) {
            return vScroll;
        } else {
            return emptyScroll;
        }
    }

    @As(Orientation.class)
    public Scroll asHorisontalScroll() {
        checkScrolls();
        if (hScroll != null) {
            return hScroll;
        } else {
            return emptyScroll;
        }
    }

    @Property(HORIZONTAL_MAXIMUM_PROP_NAME)
    public double hmax() {
        checkScrolls();
        if (hScroll != null) {
            return hScroll.maximum();
        } else {
            return 0;
        }
    }

    @Property(HORIZONTAL_MINIMUM_PROP_NAME)
    public double hmin() {
        checkScrolls();
        if (hScroll != null) {
            return hScroll.minimum();
        } else {
            return 0;
        }
    }

    @Property(VERTICAL_MAXIMUM_PROP_NAME)
    public double vmax() {
        checkScrolls();
        if (vScroll != null) {
            return vScroll.maximum();
        } else {
            return 0;
        }
    }

    @Property(VERTICAL_MINIMUM_PROP_NAME)
    public double vmin() {
        checkScrolls();
        if (vScroll != null) {
            return vScroll.minimum();
        } else {
            return 0;
        }
    }

    @Property(HORIZONTAL_POSITION_PROP_NAME)
    public double hpos() {
        checkScrolls();
        if (hScroll != null) {
            return hScroll.position();
        } else {
            return 0;
        }
    }

    @Property(VERTICAL_POSITION_PROP_NAME)
    public double vpos() {
        checkScrolls();
        if (vScroll != null) {
            return vScroll.position();
        } else {
            return 0;
        }
    }

    @Property(VERTICAL_SCROLLABLE_PROP_NAME)
    public boolean isVerticalScrollable() {
        checkScrolls();
        return vScroll != null;
    }

    @Property(HORIZONTAL_SCROLLABLE_PROP_NAME)
    public boolean isHorizontalScrollable() {
        checkScrolls();
        return hScroll != null;
    }

    @Shortcut
    public Caret hcaret() {
        checkScrolls();
        if (vScroll != null) {
            return vScroll.caret();
        } else {
            return emptyScroller;
        }
    }

    @Shortcut
    public Caret vcaret() {
        checkScrolls();
        if (vScroll != null) {
            return vScroll.caret();
        } else {
            return emptyScroller;
        }
    }

    @Shortcut
    public void hto(double position) {
        checkScrolls();
        if (hScroll != null) {
            position = Math.min(position, hmax());
            position = Math.max(position, hmin());
            hScroll.to(position);
        }
    }

    @Shortcut
    public void hto(Direction condition) {
        checkScrolls();
        if (hScroll != null) {
            hScroll.scroller().to(condition);
        }
    }

    @Shortcut
    public void vto(double position) {
        checkScrolls();
        if (vScroll != null) {
            position = Math.min(position, vmax());
            position = Math.max(position, vmin());
            vScroll.to(position);
        }
    }

    @Shortcut
    public void vto(Direction condition) {
        checkScrolls();
        if (vScroll != null) {
            vScroll.scroller().to(condition);
        }
    }

    /**
     * We are checking scrollBars each time, because they can disappear at any
     * moment.
     */
    protected void checkScrolls() {
        vScroll = getScroll(Orientation.VERTICAL);
        hScroll = getScroll(Orientation.HORIZONTAL);
    }

    /**
     * @return must return null, if scroll not found.
     */
    private AbstractScroll getScroll(final Orientation orientation) {
        Lookup<ScrollBar> lookup = scrolledControl.as(Parent.class, Node.class).lookup(ScrollBar.class, scrollsLookupCriteria.forOrientation(orientation));
        int count = lookup.size();
        if (count == 0) {
            return null;
        } else if (count == 1) {
            return lookup.as(AbstractScroll.class);
        } else {
            throw new JemmyException("There are more than 1 "
                    + ((orientation.equals(Orientation.VERTICAL)) ? "vertical" : "horizontal")
                    + " ScrollBars in control <" + scrolledControl + ">.");
        }
    }

    @Shortcut
    public void to(double hposition, double vposition) {
        hto(hposition);
        vto(vposition);
    }

    @Shortcut
    public void to(Point point) {
        to(point.x, point.y);
    }

    @Shortcut
    public void to(Direction hCondition, Direction vCondition) {
        hto(hCondition);
        vto(vCondition);
    }

    /**
     * This interface is used to find scrolls for scrolling, depending on
     * orientation. You need to implement only 1 method - to check, that
     * scrollBar, having needed orientation, is that one, which is needed for
     * scrolling in that direction.
     *
     * @param <CONTROL> for special cases of ScrollBar - like VirtualScrollBar.
     */
    public abstract static class ScrollsLookupCriteria<SCROLL_BAR extends ScrollBar> implements LookupCriteria<SCROLL_BAR> {

        private Orientation orientation;

        public ScrollsLookupCriteria forOrientation(Orientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public boolean check(final SCROLL_BAR control) {
            boolean correctOrientation = new FutureAction<>(Root.ROOT.getEnvironment(), () -> {
                if (orientation != null) {
                    return control.getOrientation().equals(orientation);
                } else {
                    //For case, when orientation checking is left for user.
                    return true;
                }
            }).get();


            if (correctOrientation && (control.getOpacity() > 0.1) && (control.isVisible()) && (!control.isDisabled())) {
                return checkFor(control);
            } else {
                return false;
            }
        }

        /**
         * This function is needed only for checking of correct choice of found
         * ScrollBar - that it is right that ScrollBar, which is needed.
         * Opacity, disabled state, visibility, orientation are already checked.
         *
         * @param scrollBar - instance of checked scrollBar
         * @return is that right scrollBar
         */
        public abstract boolean checkFor(SCROLL_BAR scrollBar);
    }

    public static class EmptyScroll extends AbstractScroll {

        private EmptyScroller emptyScroller = new EmptyScroller();

        @Override
        public double position() {
            throw new JemmyException("");
        }

        @Override
        public Caret caret() {
            return emptyScroller;
        }

        @Override
        public double maximum() {
            throw new JemmyException("");
        }

        @Override
        public double minimum() {
            throw new JemmyException("");
        }

        @Override
        public double value() {
            throw new JemmyException("");
        }

        @Override
        public Scroller scroller() {
            return emptyScroller;
        }
    }

    public static class EmptyScroller implements Scroller {

        @Override
        public void to(double value) {
        }

        @Override
        public void to(Caret.Direction condition) {
        }

        @Override
        public void scrollTo(double value) {
        }

        @Override
        public void scrollTo(Scroller.ScrollCondition condition) {
        }
    }
}