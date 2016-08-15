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
package org.jemmy.interfaces;

import org.jemmy.Point;
import org.jemmy.control.Property;
import org.jemmy.dock.Shortcut;
import org.jemmy.interfaces.Caret.Direction;

/**
 * Interface for controls, which allows scrolling in two directions - vertical
 * and horizontal. By implementation, it must support a behavior, when scroll
 * method is called, but cannot be done, according to scrollBar invisibility -
 * it just should do nothing.
 *
 * @author Alexander Kirov
 */
public interface Scrollable2D extends ControlInterface {

    public static final String VERTICAL_PROP_NAME = "vertical";
    public static final String HORIZONTAL_PROP_NAME = "horizontal";
    public static final String HORIZONTAL_MAXIMUM_PROP_NAME = "hmaximum";
    public static final String HORIZONTAL_MINIMUM_PROP_NAME = "hminimum";
    public static final String HORIZONTAL_POSITION_PROP_NAME = "hposition";
    public static final String VERTICAL_MAXIMUM_PROP_NAME = "vmaximum";
    public static final String VERTICAL_MINIMUM_PROP_NAME = "vminimum";
    public static final String VERTICAL_POSITION_PROP_NAME = "vposition";
    public static final String VERTICAL_SCROLLABLE_PROP_NAME = "isvscrollable";
    public static final String HORIZONTAL_SCROLLABLE_PROP_NAME = "ishscrollable";

    public Scroll asVerticalScroll();

    public Scroll asHorisontalScroll();

    @Property(HORIZONTAL_MAXIMUM_PROP_NAME)
    public double hmax();

    @Property(HORIZONTAL_MINIMUM_PROP_NAME)
    public double hmin();

    @Property(VERTICAL_MAXIMUM_PROP_NAME)
    public double vmax();

    @Property(VERTICAL_MINIMUM_PROP_NAME)
    public double vmin();

    @Property(HORIZONTAL_POSITION_PROP_NAME)
    public double hpos();

    @Property(VERTICAL_POSITION_PROP_NAME)
    public double vpos();

    /**
     * Answers on question, is there possibility to do a scrolling in vertical
     * dimension. For instance, if vertical scrollbar visible or not.
     */
    @Property(VERTICAL_SCROLLABLE_PROP_NAME)
    public boolean isVerticalScrollable();

    /**
     * Answers on question, is there possibility to do a scrolling in horizontal
     * dimension. For instance, if horizontal scrollbar visible or not.
     */
    @Property(HORIZONTAL_SCROLLABLE_PROP_NAME)
    public boolean isHorizontalScrollable();

    @Shortcut
    public Caret hcaret();

    @Shortcut
    public Caret vcaret();

    /**
     * @param position Must support arguments, which are more than hmax
     * (including Double.MAX_VALUE), and less than hmin (including
     * Double.MIN_VALUE), dealing with them like with hmax or hmin values
     * correspondingly.
     */
    @Shortcut
    public void hto(double position);

    /**
     * @param condition - condition of scrolling in horizontal direction.
     */
    @Shortcut
    public void hto(Direction condition);

    /**
     * @param position Must support arguments, which are more than vmax
     * (including Double.MAX_VALUE), and less than vmin (including
     * Double.MIN_VALUE), dealing with them like with vmax or vmin values
     * correspondingly.
     */
    @Shortcut
    public void vto(double position);

    /**
     * @param condition - condition of scrolling in vertical direction.
     */
    @Shortcut
    public void vto(Direction condition);

    /**
     *
     * @param hposition Must support arguments, which are more than hmax
     * (including Double.MAX_VALUE), and less than hmin (including
     * Double.MIN_VALUE), dealing with them like with hmax or hmin values
     * correspondingly.
     * @param vposition Must support arguments, which are more than vmax
     * (including Double.MAX_VALUE), and less than vmin (including
     * Double.MIN_VALUE), dealing with them like with vmax or vmin values
     * correspondingly.
     */
    @Shortcut
    public void to(double hposition, double vposition);

    @Shortcut
    public void to(Point point);

    @Shortcut
    public void to(Direction hCondition, Direction vCondition);
}