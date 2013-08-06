/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.richtext;

import junit.framework.Assert;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 *
 * @author Andrey Glushchenko
 */
public class RichTextRealWorldExampleTest extends TestBase {

    private static RichTextRealWorldExampleApp application = null;

    @BeforeClass
    public static void setUp() {
        RichTextRealWorldExampleApp.main(null);
        application = RichTextRealWorldExampleApp.getApplication();
    }

    /**
     * Test for content height.
     */
    @Test
    public void heightTest() {
        Assert.fail("Need to resolve RT-28788");

//        double etalon = ?;
//        Assert.assertEquals("Content height has unexpected value!",etalon, getContentHeight());

    }

    /**
     * Test for content.
     */
    @Test
    public void mark0Test() {
        scrollToMark(0);
        checkScreenshot("mark0Test");
    }

    /**
     * Test for content.
     */
    @Test
    public void mark1Test() {
        scrollToMark(1);
        checkScreenshot("mark1Test");
    }

    /**
     * Test for content.
     */
    @Test
    public void mark2Test() {
        scrollToMark(2);
        checkScreenshot("mark2Test");
    }

    private double getContentHeight() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.getContentHeight());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private void scrollToMark(final int i) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.scrollToMark(i);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private void checkScreenshot(final String testName) {
        new Waiter(Wrap.WAIT_STATE_TIMEOUT).ensureState(new State() {
            public Object reached() {
                try {
                    ScreenshotUtils.checkScreenshot("RichTextRealWorldExampleTest-" + testName, scene);
                    return true;
                } catch (Throwable t) {
                    return null;
                }
            }
        });
    }
}
