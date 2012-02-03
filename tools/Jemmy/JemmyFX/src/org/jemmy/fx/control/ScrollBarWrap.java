/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this License Header
 * Notice in each file.
 *
 * If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
 */
package org.jemmy.fx.control;


import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.MethodProperties;
import org.jemmy.control.Property;
import org.jemmy.env.Environment;
import org.jemmy.fx.NodeParent;
import org.jemmy.fx.Root;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.Caret;
import org.jemmy.interfaces.ControlInterface;
import org.jemmy.interfaces.Scroll;
import org.jemmy.interfaces.Scroller;
import org.jemmy.interfaces.Shiftable;
import org.jemmy.interfaces.Shifter;
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
     * @param scene
     * @param nd
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
                return Root.ROOT.getThemeFactory().caret(ScrollBarWrap.this, this);
            }
        };
    }

    public static ScrollBarWrap<ScrollBar> find(NodeParent parent, LookupCriteria<ScrollBar> criteria) {
        return new ScrollBarWrap<ScrollBar>(parent.getEnvironment(), parent.getParent().lookup(ScrollBar.class, criteria).get());
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
        return Root.ROOT.getThemeFactory().track(this, theScroll);
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
