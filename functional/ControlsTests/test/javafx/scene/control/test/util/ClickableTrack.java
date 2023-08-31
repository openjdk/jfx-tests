/*
 * Copyright (c) 2014, 2023 Oracle and/or its affiliates. All rights reserved.
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

package javafx.scene.control.test.util;

import org.jemmy.JemmyException;
import org.jemmy.Point;
import org.jemmy.fx.input.ScrollTrack;
import org.jemmy.fx.interfaces.Shiftable;
import org.jemmy.fx.interfaces.Shifter;

/**
 * The class to encapsulate calculations for performing mouse click actions over Slider, ScrollBar
 * @author ineverov
 */
public class ClickableTrack {

//    Wrap control;
    ScrollTrack track;
    double tickSize;

    public ClickableTrack(Shiftable s, double tickSize){
        Shifter sh = s.shifter();
        if (! ScrollTrack.class.isAssignableFrom(sh.getClass())) {
            throw new UnsupportedOperationException(
                      "Given Shiftable should return shifter implemented via the ScrollTrack");
        }
        // TODO to check evolution of Shiftable and ScrollTrack to avoid that cast
        track = (ScrollTrack) sh;
        this.tickSize = tickSize;
    }
/*
    public ClickableTrack(Wrap<? extends Slider> sl){
        this(sl.as(Shiftable.class));
        control = sl;
    }
*/
    public void click(double toValue, int count) {
        Point p = track.createPoint(toValue);
        if (p == null) {
            throw new JemmyException("Value "+ toValue +" is out of range for "+ track);
        }
        track.click(p, count);
    }

    public void click(double toValue) {
        click(toValue, 1);
    }

    public double adjustedPosition(double toValue) {
        double min = track.minimum();
        double gap = toValue - min;
        if (gap <= 0) {
            return min;
        }
        if (toValue >= track.maximum()) {
            return track.maximum();
        }
        if (tickSize <= 0) {
            return toValue;
        }
        long ticks = Math.round(gap/tickSize);
        return min + tickSize*ticks;
    }

}
