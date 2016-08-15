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
import javafx.scene.control.ScrollBar;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.MethodProperties;
import org.jemmy.control.Property;
import org.jemmy.env.Environment;
import org.jemmy.fx.NodeParent;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.LookupCriteria;


/**
 * The wrapper is ported from the project JemmyFX, original class
 * is   org.jemmy.fx.control.ScrollBarWrap
 *
 * @param <T>
 * @author Shura
 */
@ControlType({ScrollBar.class})
@ControlInterfaces({Scroll.class})
@MethodProperties({"getOrientation"})
public class ScrollBarWrap<T extends ScrollBar> extends ControlWrap<T> implements Scroll, Shiftable {

    AbstractScroll theScroll;

    /**
     *
     * @param env
     * @param node
     */
    public ScrollBarWrap(Environment env, T node) {
        super(env, node);
        theScroll = new AbstractScroll() {

            @Override
            public double position() {
                return new GetAction<Double>() {

                    @Override
                    public void run(Object... parameters) {
                        setResult((double) getControl().getValue());
                    }

                    @Override
                    public String toString() {
                        return "Getting position of " + getControl();
                    }
                }.dispatch(getEnvironment());
            }

            @Override
            public Caret caret() {
                return scroller();
            }

            @Override
            public double maximum() {
                return new GetAction<Double>() {

                    @Override
                    public void run(Object... parameters) {
                        setResult((double) getControl().getMax());
                    }

                    @Override
                    public String toString() {
                        return "Getting  maximum of " + getControl();
                    }
                }.dispatch(getEnvironment());
            }

            @Override
            public double minimum() {
                return new GetAction<Double>() {

                    @Override
                    public void run(Object... parameters) {
                        setResult((double) getControl().getMin());
                    }

                    @Override
                    public String toString() {
                        return "Getting  minimum of " + getControl();
                    }
                }.dispatch(getEnvironment());
            }

            @Override
            public double value() {
                return position();
            }

            @Override
            public Scroller scroller() {
                return ThemeDriverFactory.getThemeFactory().caret(ScrollBarWrap.this, this);
            }
        };
    }

    public static ScrollBarWrap<ScrollBar> find(NodeParent parent, LookupCriteria<ScrollBar> criteria) {
        return new ScrollBarWrap<>(parent.getEnvironment(), parent.getParent().lookup(ScrollBar.class, criteria).get());
    }

    public static ScrollBarWrap<ScrollBar> find(NodeParent parent, final boolean vertical) {
        return find(parent, new ByOrientationScrollBar(vertical));
    }

    /**
     * @return
     */
    @Property(VERTICAL_PROP_NAME)
    public boolean vertical() {
        return new GetAction<Boolean>() {

            @Override
            public void run(Object... parameters) {
                setResult(getControl().getOrientation() == Orientation.VERTICAL);
            }

            @Override
            public String toString() {
                return "Checking if vertical of " + getControl();
            }
        }.dispatch(getEnvironment());
    }

    @Override
    @Property(MAXIMUM_PROP_NAME)
    public double maximum() {
        return theScroll.maximum();
    }

    @Override
    @Property(MINIMUM_PROP_NAME)
    public double minimum() {
        return theScroll.minimum();
    }

    @Override
    @Property(VALUE_PROP_NAME)
    public double position() {
        return theScroll.position();
    }

    @Deprecated
    @Override
    public double value() {
        return position();
    }

    @Override
    @Deprecated
    public Scroller scroller() {
        return theScroll.scroller();
    }

    @Override
    public Caret caret() {
        return scroller();
    }

    public Shifter shifter() {
        return ThemeDriverFactory.getThemeFactory().track(this, theScroll);
    }

    @Override
    public void to(double position) {
        theScroll.to(position);
    }

    @Override
    public <INTERFACE extends ControlInterface> boolean is(Class<INTERFACE> interfaceClass) {
        if(interfaceClass.isAssignableFrom(AbstractScroll.class)) {
            return true;
        }
        return super.is(interfaceClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> interfaceClass) {
        if(interfaceClass.isAssignableFrom(AbstractScroll.class)) {
            return (INTERFACE) theScroll;
        }
        return super.as(interfaceClass);
    }

    public static class ByOrientationScrollBar implements LookupCriteria<ScrollBar> {

        private final boolean vertical;

        public ByOrientationScrollBar(boolean vertical) {
            this.vertical = vertical;
        }

        @Override
        public boolean check(ScrollBar control) {
            return (control.getOrientation() == Orientation.VERTICAL) == vertical;
        }
    }

}
