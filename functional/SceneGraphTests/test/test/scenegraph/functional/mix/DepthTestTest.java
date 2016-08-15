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
 */
package test.scenegraph.functional.mix;

import com.sun.javafx.runtime.VersionInfo;
import java.util.Arrays;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.shape.Shape;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import static org.jemmy.fx.Lookups.byID;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.ComboBoxWrap;
import org.jemmy.fx.control.ToggleButtonWrap;
import org.jemmy.timing.State;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.JemmyUtils;
import test.javaclient.shared.TestBase;
import test.scenegraph.app.DepthTestApp;
import test.scenegraph.app.DepthTestScene;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class DepthTestTest extends TestBase
{

    @BeforeClass
    public static void RunUI()
    {
        DepthTestApp.main();
    }

    @Override
    @Before
    public void before()
    {
        super.before();
        System.out.println("fx: " + VersionInfo.getRuntimeVersion());
        firstDTest = byID(getScene(), "first_depth_test", ComboBox.class).as(ComboBoxWrap.class);
        secondDTest = byID(getScene(), "second_depth_test", ComboBox.class).as(ComboBoxWrap.class);
        parentDTest = byID(getScene(), "parent_depth_test", ComboBox.class).as(ComboBoxWrap.class);
        firstNodeCombo = byID(getScene(), "first_node_combo", ComboBox.class).as(ComboBoxWrap.class);
        secondNodeCombo = byID(getScene(), "second_node_combo", ComboBox.class).as(ComboBoxWrap.class);
        firstToFront = byID(getScene(), "first_node_to_front", RadioButton.class).as(ToggleButtonWrap.class);
        secondToFront = byID(getScene(), "second_node_to_front", RadioButton.class).as(ToggleButtonWrap.class);
        indicator = byID(getScene(), "indicator", Shape.class);
        referenceGreen = byID(getScene(), "reference_green", Shape.class);
        referenceRed = byID(getScene(), "reference_red", Shape.class);
    }

    @Test
    public void mouseEventDeliveryTest()
    {
        if(Platform.isSupported(ConditionalFeature.SCENE3D))
        {
            final Object referenceColor = getCenterColor(referenceGreen);

            iterate(new TestCore() {

                @Override
                protected void catchError()
                {
                    Object detectedColor = getCenterColor(indicator);

                    if(!(JemmyUtils.usingGlassRobot() ? Arrays.equals((double[]) detectedColor, (double[]) referenceColor) : ((Integer) detectedColor).intValue() == ((Integer) referenceColor).intValue()))
                    {
                        StringBuilder builder = new StringBuilder();
                        builder.append("Wrong node reached with following configuration:\nFirst node: ").
                                append(firstNodeCombo.getControl().getValue()).append("\nSecond node: ").
                                append(secondNodeCombo.getControl().getValue()).
                                append("\nFirst node DepthTest: ").append(firstDTest.getControl().getValue()).
                                append("\nSecond node DepthTest: ").append(secondDTest.getControl().getValue()).
                                append("\nParent node DepthTest: ").append(parentDTest.getControl().getValue()).
                                append("\nWith ").append(firstToFront.getControl().isSelected() ? "first" : "second").
                                append(" node infront\n");
                        System.err.println(builder.toString());
                        setPassed(false);
                    }
                }
            });
        }
    }

    @Test
    public void renderingTest()
    {
        if(Platform.isSupported(ConditionalFeature.SCENE3D))
        {
            iterate(new TestCore() {

                @Override
                protected void catchError()
                {
                    Object color = isOnTop(firstNode) ? getCenterColor(referenceGreen) : getCenterColor(referenceRed);
                    Point point = getCheckpoint();
                    Object detectedColor = getColor(getScene(), point.getX(), point.getY());
                    if(!(JemmyUtils.usingGlassRobot() ? Arrays.equals((double[]) detectedColor, (double[]) color) : ((Integer) detectedColor).intValue() == ((Integer) color).intValue()))
                    {
                        StringBuilder builder = new StringBuilder();
                        builder.append("Wrong color detected on nodes intesection area with following configuration:\nFirst node: ").
                                append(firstNodeCombo.getControl().getValue()).append("\nSecond node: ").
                                append(secondNodeCombo.getControl().getValue()).
                                append("\nFirst node DepthTest: ").append(firstDTest.getControl().getValue()).
                                append("\nSecond node DepthTest: ").append(secondDTest.getControl().getValue()).
                                append("\nParent node DepthTest: ").append(parentDTest.getControl().getValue()).
                                append("\nWith ").append(firstToFront.getControl().isSelected() ? "first" : "second").
                                append(" node infront\n");
                        System.err.println(builder.toString());
                        setPassed(false);
                    }
                }

                public boolean isOnTop(Wrap<? extends Node> node)
                {
                    boolean meets = false;
                    boolean onTop = node.equals(secondNode);
                    boolean firstDTEnable = isDepthTestEnable(firstDTest);
                    boolean secondDTEnable = isDepthTestEnable(secondDTest);
                    boolean inFront = onTop ? secondToFront.getState() : firstToFront.getState();

                    if((!firstDTEnable || !secondDTEnable) && onTop)
                        meets = true;
                    if(firstDTEnable && secondDTEnable && inFront)
                        meets = true;

                    return meets;
                }

                private boolean isDepthTestEnable(ComboBoxWrap<? extends ComboBox> cb)
                {
                    boolean enable = cb.getControl().getValue().equals(DepthTest.ENABLE) ||
                            (cb.getControl().getValue().equals(DepthTest.INHERIT) &&
                            parentDTest.getControl().getValue().equals(DepthTest.ENABLE));
                    return enable;
                }
            });
        }
    }

    private Point getCheckpoint()
    {
        Rectangle sceneBounds = getScene().getScreenBounds();

        Point p = getGlobalCheckpoint();
        p.setLocation(p.getX() - sceneBounds.getX(), p.getY() - sceneBounds.getY());
        return p;
    }

    private Point getGlobalCheckpoint()
    {
        Rectangle firstBounds = firstNode.getScreenBounds(), secondBounds = secondNode.getScreenBounds();
        Rectangle intesection = firstBounds.intersection(secondBounds);

        double x = intesection.getX();
        double y = intesection.getY();
        double width = intesection.getWidth();
        double height = intesection.getHeight();
        Point p = new Point(x + 0.5 * width, y + 0.5 * height);

        return p;
    }

    private void testClick()
    {
        getScene().mouse().click(1, getCheckpoint());
    }

    private void waitForIndicator()
    {
        indicator.waitState(new State<Boolean>(){

            public Boolean reached() {
                Object indicatorColor = getCenterColor(indicator);
                if(!(JemmyUtils.usingGlassRobot() ?
                        Arrays.equals((double[]) indicatorColor, (double[]) ((Object) 0)) :
                        ((Integer) indicatorColor).intValue() == 0))
                    return true;
                return false;
            }

        }, Boolean.TRUE);
    }

    private void iterate(TestCore core)
    {
        boolean doneWell = true;

        for(final DepthTestScene.Nodes node1: DepthTestScene.Nodes.values())
        {
            for(final DepthTestScene.Nodes node2: DepthTestScene.Nodes.values())
            {
                for(final DepthTest dt1: DepthTest.values())
                {
                    for(final DepthTest dt2: DepthTest.values())
                    {
                        for(final DepthTest dt3: new DepthTest[]{DepthTest.DISABLE, DepthTest.ENABLE})
                        {
                            for(final ToggleButtonWrap<? extends RadioButton> tbw:
                                    new ToggleButtonWrap[]{firstToFront, secondToFront})
                            {
                                new GetAction<Object>() {

                                    @Override
                                    public void run(Object... os) throws Exception {
                                        firstNodeCombo.getControl().getSelectionModel().select(node1);
                                        secondNodeCombo.getControl().getSelectionModel().select(node2);
                                        firstDTest.getControl().getSelectionModel().select(dt1);
                                        secondDTest.getControl().getSelectionModel().select(dt2);
                                        parentDTest.getControl().getSelectionModel().select(dt3);
                                        tbw.getControl().fire();
                                    }
                                }.dispatch(Root.ROOT.getEnvironment());
                                firstNode = byID(getScene(), firstNodeCombo.getControl().getValue().toString() + "-green", Node.class);
                                secondNode = byID(getScene(), secondNodeCombo.getControl().getValue().toString() + "-red", Node.class);
                                doneWell &= core.execute();
                            }
                        }
                    }
                }
            }
        }
        Assert.assertTrue(doneWell);
    }

    private Object getCenterColor(Wrap<?> wrap)
    {
        Rectangle wrapBounds = wrap.getScreenBounds();
        return getColor(wrap, wrapBounds.getWidth() / 2, wrapBounds.getHeight() / 2);
    }

    private Object getColor(Wrap<?> wrap, double localX, double localY)
    {
        Rectangle wrapBounds = wrap.getScreenBounds();
        Rectangle sceneBounds = getScene().getScreenBounds();
        Object detectedColor = JemmyUtils.getRGBColors(getScene().getScreenImage(),
                    (int) wrapBounds.getX() - (int) sceneBounds.getX() + (int) localX,
                    (int) wrapBounds.getY() - (int) sceneBounds.getY() + (int) localY);
        return detectedColor;
    }

    private ComboBoxWrap<? extends ComboBox> firstDTest, secondDTest, parentDTest;
    private ComboBoxWrap<? extends ComboBox> firstNodeCombo, secondNodeCombo;
    private ToggleButtonWrap<? extends RadioButton> firstToFront, secondToFront;
    private Wrap<? extends Node> indicator, firstNode, secondNode, referenceGreen, referenceRed;

    abstract class TestCore
    {

        public boolean execute()
        {
            initialPreparation();
            catchError();
            indicatorPreparation();
            return passed;
        }

        private void initialPreparation()
        {
            passed = true;
            testClick();
            waitForIndicator();
            Object detectedColor = getCenterColor(indicator);
        }

        private void indicatorPreparation()
        {
            new GetAction<Object>() {

                @Override
                public void run(Object... parameters) throws Exception {
                    indicator.getControl().setStyle("-fx-fill: black;");
                }
            }.dispatch(Root.ROOT.getEnvironment());
        }

        protected abstract void catchError();

        public void setPassed(boolean passed)
        {
            this.passed = passed;
        }

        private boolean passed;

    }

}
