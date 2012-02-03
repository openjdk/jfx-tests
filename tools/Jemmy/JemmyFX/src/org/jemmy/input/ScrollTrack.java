/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package org.jemmy.input;

import javafx.scene.control.Control;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.interfaces.Mouse;
import org.jemmy.interfaces.Scroll;
import org.jemmy.interfaces.Shifter;

/**
 * Implementation of Shifter for controls owning a track which can be moved with mouse clicks.
 * eg Slider, ScrollBar
 * @author ineverov
 */
public class ScrollTrack implements Shifter {

    /**
     * Time to sleep between clicks over scrolling control to avoid confusing with double-click
     */
    public static final Timeout SHIFT_CLICK_TIMEOUT =
                                 new Timeout("ScrollTrack.shiftClick", 1);

    static {
        Environment.getEnvironment().initTimeout(SHIFT_CLICK_TIMEOUT);
    }

    //    private Wrap<Control> ctrlWrap;
    private final boolean isVertical;
    private final Wrap<?> trackNode;
    private final Wrap<? extends Control> owningNode;
    private final int unitIncClickOffset, blockIncClickOffset;

    public ScrollTrack(Wrap<?> track, Wrap<? extends Control> wrap, boolean vertical, int edgeArrowLength) {
        trackNode = track;
        owningNode = wrap;
        isVertical = vertical;
        unitIncClickOffset = 2;
        blockIncClickOffset = edgeArrowLength + unitIncClickOffset;
//        ctrlWrap=controlWrap;
    }

    public void shift(Dir direction) {
        shift(direction, Inc.BLOCK);
    }

    public void shift(Dir direction, Inc increment) {
        shift(direction, increment, 1);
    }

    public void shift(Dir direction, int count) {
        shift(direction, Inc.BLOCK, count);
    }

    public void shift(Dir direction, Inc increment, int count) {
        int offset = (increment == Inc.BLOCK) ? blockIncClickOffset:
                                                 unitIncClickOffset;

        click(direction, offset, count);
    }

    /**
     * TODO Keep that here to evolve to some ClickableShifter interface later
     *
     * Can that Shifter perform mouse clicks to "track" area of owning Shiftable.
     * @see click
    public boolean canClick() {
        return true;
    }
     */

    /**
     * TODO Keep that here to evolve to some ClickableShifter interface later
     *
     * Does single click to specified position of "track".
     * If that Shifter can not click it does nothing.
     * @param direction defines edge from which the position is calculated
     * @param offset of the position in pixels from edge defined with direction
 
    protected void click(Dir direction, int offset) {
        click(direction, offset, 1);
    }
     */

    /**
     * TODO Keep that here to evolve to some ClickableShifter interface later
     *
     * Does several single clicks to specified position of "track".
     * If that Shifter can not click it does nothing.
     * @param direction defines edge from which the position is calculated
     * @param offset of the position in pixels from edge defined with direction
     * @param count defines number of clicks
     */
    protected void click(Dir direction, int offset, int count) {
        boolean increase = (direction == Dir.MORE);

        Point cp = ScrollerImpl.createScrollPoint(trackNode, ! isVertical, increase, offset);
        click(cp, count);
    }

    /**
     * TODO Keep that here to evolve to some ClickableShifter interface later
     *
     * Does several single clicks to specified point related to "track".
     * @param clickPoint holds desired click position
     * @param count defines number of clicks
     */
    public void click(Point clickPoint, int count) {
        Mouse mouse = trackNode.as(Mouse.class);

        while (count > 0) {
            mouse.click(1, clickPoint);
            if (--count > 0) {
                SHIFT_CLICK_TIMEOUT.sleep();
            }
        }
    }

    /**
     * TODO Keep that here to evolve to some ClickableShifter interface later
     *
     * Utility method to calculate a Point in track area corresponding to given value.
     * @param value in the range of owning Shiftable.
     * @return point on "track" .
     */
    public Point createPoint(double value) {
        double min = ((Scroll)owningNode).minimum();
        double max = ((Scroll)owningNode).maximum();
        if (value < min || value > max)
            return null;

        boolean increase = isVertical;
        double dOffset = value - min;
        Rectangle bounds = trackNode.getScreenBounds();
        int len = isVertical? bounds.height: bounds.width;
        Point cp = ScrollerImpl.createScrollPoint(trackNode, ! isVertical, increase, (int)(dOffset/(max-min) * len));
        
        return cp;
    }

   /**
     * TODO Keep that here to evolve to some ClickableShifter interface later
     *
     * Utility method to get minimum value of the range corresponding to track area.
     * @return 
     */
    public double minimum() {
        return ((Scroll) owningNode).minimum();
    }

    /**
     * TODO Keep that here to evolve to some ClickableShifter interface later
     *
     * Utility method to get maximum value of the range corresponding to track area.
     * @return
     */
    public double maximum() {
        return ((Scroll) owningNode).maximum();
    }
}
