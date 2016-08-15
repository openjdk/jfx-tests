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
package org.jemmy.fx.control;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollBar;
import org.jemmy.JemmyException;
import org.jemmy.control.Wrap;
import org.jemmy.fx.control.Scrollable2DImpl.EmptyScroll;
import org.jemmy.fx.control.Scrollable2DImpl.EmptyScroller;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.Caret;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Scroll;
import org.jemmy.interfaces.Scroller;
import org.jemmy.lookup.Lookup;

/**
 * @author Alexander Kirov
 */
class TableTreeScroll implements Scroll {

    private AbstractScroll emptyScroll = new EmptyScroll();
    private Scroller emptyScroller = new EmptyScroller();
    protected AbstractScroll hScroll, vScroll;
    private Wrap<? extends Control> control;

    public TableTreeScroll(Wrap<? extends Control> control) {
        this.control = control;
    }

    /**
     * In case direct scrolling is needed. Scroller value is in the interval
     * from 0 to 1.
     *
     * @return
     */
    public Scroll asScroll() {
        checkScrolls();
        if (vScroll != null) {
            return vScroll;
        } else {
            return emptyScroll;
        }
    }

    /**
     * Obtains wrap for scrollbar
     *
     * @param vertical
     * @return
     */
    private AbstractScroll getScroll(final boolean vertical) {
        final Parent<Node> parent = control.as(Parent.class, Node.class);
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
                    + " ScrollBars in this TableView");
        }
    }

    /**
     * Initialize wraps for ScrollBars if they're not yet initialized
     */
    protected void checkScrolls() {
        if (vScroll == null) {
            vScroll = getScroll(true);
        }
        if (hScroll == null) {
            hScroll = getScroll(false);
        }
    }

    public double maximum() {
        checkScrolls();
        if (vScroll != null) {
            return vScroll.maximum();
        } else {
            return 0;
        }
    }

    public double minimum() {
        checkScrolls();
        if (vScroll != null) {
            return vScroll.minimum();
        } else {
            return 0;
        }
    }

    @Deprecated
    public double value() {
        return position();
    }

    public double position() {
        checkScrolls();
        if (vScroll != null) {
            return vScroll.value();
        } else {
            return 0;
        }
    }

    public Caret caret() {
        checkScrolls();
        if (vScroll != null) {
            return vScroll.caret();
        } else {
            return emptyScroller;
        }
    }

    public void to(double position) {
        checkScrolls();
        if (vScroll != null) {
            vScroll.to(position);
        }
    }

    @Deprecated
    public Scroller scroller() {
        checkScrolls();
        if (vScroll != null) {
            return vScroll.scroller();
        } else {
            return emptyScroller;
        }
    }
}