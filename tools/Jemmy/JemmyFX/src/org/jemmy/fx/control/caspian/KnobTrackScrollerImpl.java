/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package org.jemmy.fx.control.caspian;

import com.sun.javafx.scene.control.skin.SkinBase;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;
import org.jemmy.Point;
import org.jemmy.Vector;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.control.Wrap;
import org.jemmy.input.KnobDragScrollerImpl;
import org.jemmy.input.ScrollTrack;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Scroll;
import org.jemmy.interfaces.Shifter;
import org.jemmy.lookup.Any;

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
     * @param wrap
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
        Wrap<? extends SkinBase> skinWrap = wrap.as(Parent.class, Node.class).
                                                      lookup(skinClass, new Any<SkinBase>()).wrap(0);
        skin = skinWrap.as(Parent.class, Node.class);
        isVertical = wrap.getProperty(Boolean.class, Scroll.VERTICAL_PROP_NAME).booleanValue();
    }

    @Override
    protected Wrap<?> getKnob() {
        return skin.lookup(StackPane.class,
                           new ByStyleClass<StackPane>(KnobTrackScrollerImpl.SLIDER_KNOB_STYLECLASS)).wrap();
    }

    public Shifter getTrack() {
        Wrap<?> track = skin.lookup(StackPane.class,
                           new ByStyleClass<StackPane>(KnobTrackScrollerImpl.SLIDER_TRACK_STYLECLASS)).wrap();
        return new ScrollTrack(track, wrap, isVertical, 0);
    }

    @Override
    public Vector getScrollVector() {
        Point end = isVertical ? new Point(0, wrap.getControl().getHeight()) :
                                 new Point(wrap.getControl().getWidth(), 0);
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
}
