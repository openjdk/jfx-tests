/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.richtext;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 *
 * @author Andrey Glushchenko
 */
public class RichTextSpecSymbTest extends TestBase {

    private Wrap<? extends Pane> screenshotArea = null;
    private static RichTextSpecSymbApp app = null;

    @BeforeClass
    public static void setUp() {
        RichTextSpecSymbApp.main(null);
        app = RichTextSpecSymbApp.getApplication();
    }

    @Before
    public void lookup() {
        Parent p = scene.as(Parent.class, Node.class);
        screenshotArea = p.lookup(Pane.class, new ByID<Pane>(RichTextSpecSymbApp.TESTING_BOX_ID)).wrap();
    }

    /**
     * Test for line break with special symbols.
     */
    @Test(timeout = 2000)
    public void part1() {
        generateFor(0);
        checkScreenshot("part1");
    }

    /**
     * Test for line break with special symbols.
     */
    @Test(timeout = 2000)
    public void part2() {
        generateFor(1);
        checkScreenshot("part2");
    }

    /**
     * Test for line break with special symbols.
     */
    @Test(timeout = 2000)
    public void part3() {
        generateFor(2);
        checkScreenshot("part3");
    }

    /**
     * Test for line break with special symbols.
     */
    @Test(timeout = 2000)
    public void part4() {
        generateFor(3);
        checkScreenshot("part4");
    }

    /**
     * Test for line break with special symbols.
     */
    @Test(timeout = 2000)
    public void part5() {
        generateFor(4);
        checkScreenshot("part5");
    }

    /**
     * Test for line break with special symbols.
     */
    @Test(timeout = 2000)
    public void part6() {
        generateFor(5);
        checkScreenshot("part6");
    }

    /**
     * Test for line break with special symbols.
     */
    @Test(timeout = 2000)
    public void part7() {
        generateFor(6);
        checkScreenshot("part7");
    }

    /**
     * Test for line break with special symbols.
     */
    @Test(timeout = 2000)
    public void part8() {
        generateFor(7);
        checkScreenshot("part8");
    }

    /**
     * Test for line break with special symbols.
     */
    @Test(timeout = 2000)
    public void part9() {
        generateFor(8);
        checkScreenshot("part9");
    }

    /**
     * Test for line break with special symbols.
     */
    @Test(timeout = 2000)
    public void part10() {
        generateFor(9);
        checkScreenshot("part10");
    }

    /**
     * Test for line break with special symbols.
     */
    @Test(timeout = 2000)
    public void part11() {
        generateFor(10);
        checkScreenshot("part11");
    }

    private void generateFor(final int i) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                app.generateFor(i);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private void checkScreenshot(final String testName) {
        new Waiter(Wrap.WAIT_STATE_TIMEOUT).ensureState(new State() {
            public Object reached() {
                try {
                    ScreenshotUtils.checkScreenshot("RichTextSpecSimbTest-" + testName, screenshotArea);
                    return true;
                } catch (Throwable t) {
                    return null;
                }
            }
        });
    }
}
