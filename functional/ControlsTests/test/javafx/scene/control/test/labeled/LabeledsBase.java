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
package javafx.scene.control.test.labeled;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Labeled;
import javafx.scene.control.test.labeleds.LabeledsAbstactApp;
import javafx.scene.control.test.labeleds.LabeledsAbstactApp.LabeledsPages;
import junit.framework.Assert;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Labeled control API test
 *
 */
@RunWith(FilteredTestRunner.class)
public abstract class LabeledsBase extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.005f);
    }

    /**
     * Constructor test
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void constructorsTest() throws InterruptedException {
        testCommon(LabeledsPages.Constructors.name(), null, true, true);
    }

    /**
     * Test for setText API
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void setTextTest() throws InterruptedException {
        testCommon(LabeledsPages.setText.name(), null, true, true);
    }

    /**
     * Test for setContentDisplay API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setContentDisplayTest() throws InterruptedException {
        testCommon(LabeledsPages.setContentDisplay.name(), null, true, true);
    }

    /**
     * Test for setWrapText API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setWrapTextTest() throws InterruptedException {
        testCommon(LabeledsPages.setWrapText.name(), null, true, true);
    }

    /**
     * Test for setFont API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setFontTest() throws InterruptedException {
        testCommon(LabeledsPages.setFont.name(), null, true, true);
    }

    /**
     * Test for setGraphicTextGap API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setGraphicTextGapTest() throws InterruptedException {
        testCommon(LabeledsPages.setGraphicTextGap.name(), null, true, true);
    }

    /**
     * Test for setAlignment API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setAlignmentTest() throws InterruptedException {
        testCommon(LabeledsPages.setAlignment.name(), null, true, true);
    }

    /**
     * Test for setTextAlignment API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setTextAlignmentTest() throws InterruptedException {
        testCommon(LabeledsPages.setTextAlignment.name(), null, true, true);
    }

    /**
     * Test for setTextOverrun API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setTextOverrunSingleLineTest() throws InterruptedException {
        testCommon(LabeledsPages.setTextOverrunSingleLine.name(), null, true, true);
    }

    /**
     * Test for setTextOverrun API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setTextOverrunMultiLineTest() throws InterruptedException {
        testCommon(LabeledsPages.setTextOverrunMultiLine.name(), null, true, true);
    }

    /**
     * Test for setEllipsisString API by screenshot
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setEllipsisStringScreenshotTest() throws InterruptedException {
        testCommon(LabeledsPages.setEllipsisString.name(), null, true, true);
    }

    /**
     * Test for setEllipsisString API. Testing by text.
     */
    @Test(timeout = 300000)
    public void setEllipsisStringTextTest() throws InterruptedException {
        openPage(LabeledsPages.setEllipsisString.name());
        testEllipsisText();
    }

    /**
     * Test for setEllipsisString API. Testing by size.
     */
    @Test(timeout = 300000)
    public void setEllipsisStringSizeTest() throws InterruptedException {
        openPage(LabeledsPages.setEllipsisString.name());
        testEllipsisSize();
    }

    /**
     * Test for setTextOverrun API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setTextOverrunSingleLineWrappedTest() throws InterruptedException {
        testCommon(LabeledsPages.setTextOverrunSingleLineWrapped.name(), null, true, true);
    }

    /**
     * Test for setTextOverrun API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setTextOverrunMultiLineWrappedTest() throws InterruptedException {
        testCommon(LabeledsPages.setTextOverrunMultiLineWrapped.name(), null, true, true);
    }

    private void testEllipsisText() throws InterruptedException {
        org.jemmy.interfaces.Parent p = getScene().as(org.jemmy.interfaces.Parent.class, Node.class);
        final Wrap<? extends Labeled> node = p.lookup(new ByText<Labeled>(LabeledsAbstactApp.ELLIPSIS_TEXT)).wrap();
        new Waiter(Wrap.WAIT_STATE_TIMEOUT).ensureState(new State() {
            @Override
            public Object reached() {
                return (node.getControl().getChildrenUnmodifiable().size() > 0) ? true : null;
            }
        });
        Assert.assertEquals(getEllipsingString(), searchText(node.getControl()));
    }

    private static String searchText(Node node) {
        if (node instanceof LabeledText) {
            return (((LabeledText) node).getText());
        }
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            ObservableList<Node> list = parent.getChildrenUnmodifiable();
            String temp = null;
            for (Node n : list) {
                temp = searchText(n);
                if (temp != null) {
                    return temp;
                }
            }
        }
        return null;
    }

    private void testEllipsisSize() {
        org.jemmy.interfaces.Parent p = getScene().as(org.jemmy.interfaces.Parent.class, Node.class);

        final Wrap<? extends Labeled> node = p.lookup(new ByID<Labeled>(LabeledsPages.setEllipsisString.name() + LabeledsAbstactApp.CONTROL)).wrap();
        final Wrap<? extends Labeled> goldennode = p.lookup(new ByID<Labeled>(LabeledsPages.setEllipsisString.name() + LabeledsAbstactApp.GOLDEN)).wrap();

        new Waiter(Wrap.WAIT_STATE_TIMEOUT).waitState(new State() {
            @Override
            public Object reached() {
                return (node.getControl().getChildrenUnmodifiable().size() > 0) ? true : null;
            }
        });

        LabeledText targetText = searchLabeledText(node.getControl());
        Bounds targetTextBounds = targetText.getBoundsInParent();
        Bounds controlBounds = node.getControl().getBoundsInLocal();
        Bounds goldenControlBounds = goldennode.getControl().getBoundsInLocal();
        Assert.assertTrue(controlBounds.contains(targetTextBounds));
        Assert.assertEquals(goldenControlBounds.getWidth(), controlBounds.getWidth());
        Assert.assertEquals(goldenControlBounds.getHeight(), controlBounds.getHeight());
    }

    private static LabeledText searchLabeledText(Node node) {

        if (node instanceof LabeledText) {
            return (LabeledText) node;
        }
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            ObservableList<Node> list = parent.getChildrenUnmodifiable();
            LabeledText temp = null;
            for (Node n : list) {
                temp = searchLabeledText(n);
                if (temp != null) {
                    return temp;
                }
            }
        }
        return null;
    }

    /**
     * function for Ellipsis Tests. Function get "golden string" from the
     * Application, specific for every control test.
     *
     * @return Expected string
     */
    protected abstract String getEllipsingString();
}
