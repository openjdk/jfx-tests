/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
package javafx.scene.control.test.nchart;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.test.util.UtilTestFunctions;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.Root;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.Assert;

/**
 * @author Alexander Kirov
 *
 * This class is a part of experimental tests on Charts, which are aimed to
 * replace tests with huge amount of images, and which (probably) could fail at
 * any moment and for unknown reason. They could be fixed or disabled.
 */
public class Utils {

    static UtilTestFunctions.RectanglesRelations convertSideToRelation(Side side) {
        switch (side) {
            case BOTTOM:
                return UtilTestFunctions.RectanglesRelations.ABOVE;
            case TOP:
                return UtilTestFunctions.RectanglesRelations.BELOW;
            case LEFT:
                return UtilTestFunctions.RectanglesRelations.RIGHTER;
            case RIGHT:
                return UtilTestFunctions.RectanglesRelations.LEFTER;
            default:
                throw new IllegalStateException("No case provided.");
        }
    }

    static UtilTestFunctions.RectanglesRelations convertSideToCentering(Side side) {
        switch (side) {
            case BOTTOM:
            case TOP:
                return UtilTestFunctions.RectanglesRelations.CENTERED_IN_VERTICAL;
            case LEFT:
            case RIGHT:
                return UtilTestFunctions.RectanglesRelations.CENTERED_IN_HORIZONTAL;
            default:
                throw new IllegalStateException("No case provided.");
        }
    }

    /**
     * Convert input array in a way, so coordinates from sceneLocal become
     * screen global.
     *
     * @param lines array of lines.
     * @return input array;
     */
    static Line[] convertToGlobalCoordinates(Wrap container, Line[] lines) {
        final Rectangle screenBounds = container.getScreenBounds();
        for (int i = 0; i < lines.length; i++) {
            lines[i].setStartX(lines[i].getStartX() + screenBounds.x);
            lines[i].setStartY(lines[i].getStartY() + screenBounds.y);
            lines[i].setEndX(lines[i].getEndX() + screenBounds.x);
            lines[i].setEndY(lines[i].getEndY() + screenBounds.y);
        }
        return lines;
    }

    static void doCheckingWithExceptionCatching(final RunnableWithThrowable task) throws Throwable {
        final ObjectProperty<Throwable> exception = new SimpleObjectProperty(null);
        try {
            new Waiter(new Timeout("", 10000)).ensureState(new State<Boolean>() {
                public Boolean reached() {
                    try {
                        task.run();
                    } catch (Throwable ex) {
                        exception.set(ex);
                        task.invokeOnFail();
                        return null;
                    }
                    return Boolean.TRUE;
                }
            });
        } catch (Throwable ex) {
            throw new Exception("Could reach invariant state", ex.initCause(exception.get()));
        }
    }

    /**
     * @param styleClasses - list where to do lookup;
     * @param part part of string (substring)
     */
    static String findStyleClassByPattern(List<String> styleClasses, String part) {
        String result = null;
        for (String stClass : styleClasses) {
            if (stClass.contains(part)) {
                if (result != null) {
                    if (!result.equals(stClass)) {
                        throw new IllegalStateException("Found two style classes : <" + result + "> and <" + stClass + ">.");
                    }
                } else {
                    result = stClass;
                }
            }
        }
        return result;
    }

    /**
     * Removes previous style, if it could be found in the styles list.
     *
     * @param style
     * @param value
     * @return
     */
    public static String applyStyle(final Wrap<? extends Node> testedControl, final String style, Object value) {
        final String styleToApply = " " + style + ": " + value + ";";
        return new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                String currentStyle = testedControl.getControl().getStyle();
                String newStyle;
                if (currentStyle.indexOf(style) >= 0) {
                    //need to remove existing style;
                    int pos = currentStyle.indexOf(style);
                    int commaPos = currentStyle.indexOf(";", pos);
                    newStyle = currentStyle.replace(currentStyle.subSequence(pos, commaPos + 1), styleToApply);
                } else {
                    newStyle = currentStyle + styleToApply;
                }
                testedControl.getControl().setStyle(newStyle);
                setResult(testedControl.getControl().getStyle());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    /**
     * This function checks, that nodes, which are represented by list of wraps
     * don't intersect.
     *
     * @param nodes
     */
    static void checkNonIntersectance(final Wrap chartContentWrap, List<Wrap> nodes) {
        ArrayList<Rectangle> bounds = new ArrayList<Rectangle>();

        for (final Wrap<? extends Node> wrap : nodes) {
            Rectangle screenBounds = wrap.getScreenBounds();
            if (wrap.getControl() instanceof Text) {
                screenBounds = new GetAction<Rectangle>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        TextBoundsType remember = ((Text) wrap.getControl()).getBoundsType();
                        ((Text) wrap.getControl()).setBoundsType(TextBoundsType.VISUAL);
                        Bounds bounds = ((Text) wrap.getControl()).getBoundsInParent();
                        Rectangle rec = chartContentWrap.getScreenBounds();
                        setResult(new Rectangle(rec.x + bounds.getMinX(), rec.y + bounds.getMinY(), bounds.getWidth(), bounds.getHeight()));
                        ((Text) wrap.getControl()).setBoundsType(remember);
                    }
                }.dispatch(Root.ROOT.getEnvironment());
            }
            if (new GetAction<Boolean>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(wrap.getControl().isVisible() && wrap.getControl().getOpacity() > 0.1);
                }
            }.dispatch(Root.ROOT.getEnvironment())) {
                bounds.add(screenBounds);
            }
        }

        for (int i = 0; i < bounds.size(); i++) {
            for (int j = 0; j < bounds.size(); j++) {
                if (i != j) {
                    if (bounds.get(i).intersects(bounds.get(j))) {
                        Assert.fail("Intersection detected : <" + nodes.get(i).getControl() + "> and <" + nodes.get(j).getControl() + ">.");
                    }
                }
            }
        }
    }

    public static abstract class RunnableWithThrowable {

        public abstract void run() throws Throwable;

        public RunnableWithThrowable(ChartDescriptionProvider p) {
            this.p = p;
        }
        public final Runnable defaultOnFail = new Runnable() {
            public void run() {
                p.clearState();
            }
        };

        public void invokeOnFail() {
            if (onFail != null) {
                onFail.run();
            }
        }

        public void setOnFail(Runnable runnable) {
            this.onFail = runnable;
        }
        private Runnable onFail = defaultOnFail;
        private ChartDescriptionProvider p;
    }
}