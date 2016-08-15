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
package org.jemmy.fx.control.caspian;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;
import org.jemmy.JemmyException;
import org.jemmy.Point;
import org.jemmy.Vector;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.input.KnobDragScrollerImpl;
import org.jemmy.input.ScrollTrack;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Scroll;
import org.jemmy.interfaces.Shifter;

/**
 * Base class for deriving classes like SliderScroller and ScrollBarScroller.
 * Class adds getter for Shifter interface to track of UI control.
 * @author ineverov, shura
 */
public class KnobTrackScrollerImpl extends KnobDragScrollerImpl {

    public static final String SLIDER_KNOB_STYLECLASS = "thumb";
    public static final String SLIDER_TRACK_STYLECLASS = "track";

    protected boolean isVertical;
    Parent<Node> skin;
    Wrap<? extends Control> wrap;

    /**
     * Just a repeater for super constructor to use in derived classes
     *
     * @param wrap - (for instance, wrap of scrollBar)
     * @param scroll
     */
    public KnobTrackScrollerImpl(Wrap<? extends Node> wrap, Scroll scroll) {
        super(wrap, scroll);
    }

    public KnobTrackScrollerImpl(Wrap<? extends Node> wrap, Scroll scroll, boolean reverse) {
        super(wrap, scroll, reverse);
    }

    /**
     * To use in constructors of derived classes to set fields.
     */
    protected final void setWraps(Wrap<? extends Control> wrap, final Class skinClass) {
        this.wrap = wrap;
        skin = wrap.as(Parent.class, Node.class);
        isVertical = wrap.getProperty(Boolean.class, Scroll.VERTICAL_PROP_NAME).booleanValue();
    }

    @Override
    protected Wrap<?> getKnob() {
        return skin.lookup(StackPane.class,
                new ByStyleClass<>(KnobTrackScrollerImpl.SLIDER_KNOB_STYLECLASS)).wrap();
    }

    public Shifter getTrack() {
        Wrap<?> track = skin.lookup(StackPane.class,
                new ByStyleClass<>(KnobTrackScrollerImpl.SLIDER_TRACK_STYLECLASS)).wrap();
        return new ScrollTrack(track, wrap, isVertical, 0);
    }

    @Override
    public Vector getScrollVector() {
        Point end = isVertical ? new Point(0, wrap.getControl().getHeight())
                : new Point(wrap.getControl().getWidth(), 0);
        return new Vector(wrap.toAbsolute(new Point(0, 0)), wrap.toAbsolute(end));
    }

    @Override
    public void scrollTo(double value) {
        to(value);
    }

    @Override
    public void scrollTo(ScrollCondition condition) {
        throw new UnsupportedOperationException("Use to(Direction).");
    }

    /**
     * Scrolling will be done, if scrollBar is visible, otherwise, we think,
     * that the whole content is visible.
     *
     * @param value
     */
    @Override
    public void to(double value) {
        if (isVisible()) {
            super.to(value);
        }
    }

    /**
     *
     * @param condition
     */
    @Override
    public void to(Direction condition) {
        if (condition.to() != 0) {
            visibilityCheck();
            super.to(condition);
        }
    }

    private void visibilityCheck() {
        if (!isVisible()) {
            throw new JemmyException("Attempt to scroll invisible scroll bar");
        }
    }

    private boolean isVisible() {
        return wrap.getProperty(Boolean.class, "isVisible");
    }
}
