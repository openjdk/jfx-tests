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
package javafx.scene.control.test.slider;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import static javafx.scene.control.test.slider.SliderNewApp.*;
import javafx.scene.control.test.util.ClickableTrack;
import javafx.scene.control.test.util.UtilTestFunctions;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class TestBase extends UtilTestFunctions {

    static Wrap<? extends Slider> testedControl;
    static Wrap<? extends Scene> scene;

    @BeforeClass
    public static void setUpClass() throws Exception {
        SliderNewApp.main(null);
        currentSettingOption = SettingOption.PROGRAM;
    }

    @After
    public void tearDown() {
        resetScene();
    }

    protected void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);

        testedControl = parent.lookup(Slider.class, new ByID<Slider>(TESTED_SLIDER_ID)).wrap();
    }

    protected void resetScene() {
        clickButtonForTestPurpose(RESET_BUTTON_ID);
    }

    protected void applyCustomLabelFormatter() {
        clickButtonForTestPurpose(APPLY_CUSTOM_LABEL_FORMATTER);
    }

    protected void testClick(double value, ClickableTrack track) {
        track.click(value);
        checkTextFieldValue(Properties.value, value);
    }

    /**
     * Precision : 0.2;
     *
     * @param pos [0;1] - relative position.
     */
    protected void waitKnobRelativePosition(final double pos) {
        final double precision = 0.2;
        final Wrap knobWrap = testedControl.as(Parent.class, Node.class).lookup(new ByStyleClass("thumb")).wrap();
        final Orientation orientation = new GetAction<Orientation>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().getOrientation());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        testedControl.waitState(new State<Boolean>() {
            public Boolean reached() {
                Point knobCenter = knobWrap.getClickPoint().move(knobWrap.getScreenBounds().x, knobWrap.getScreenBounds().y);
                Rectangle controlRec = testedControl.getScreenBounds();

                if (Orientation.HORIZONTAL.equals(orientation)) {
                    return Math.abs((knobCenter.x - controlRec.x) / ((double) controlRec.width) - pos) < precision;
                } else {
                    return Math.abs((knobCenter.y - controlRec.y) / ((double) controlRec.height) - pos) < precision;
                }
            }
        }, Boolean.TRUE);
    }

    static protected enum Properties {

        prefWidth, prefHeight, snapToTicks, showTickLabels, showTickMarks, value, max, min, blockIncrement, majorTickUnit, minorTickCount, orientation, valueChanging, focusTraversable
    };
}
