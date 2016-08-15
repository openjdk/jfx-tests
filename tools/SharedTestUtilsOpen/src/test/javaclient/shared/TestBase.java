/*
 * Copyright (c) 2009, 2016, Oracle and/or its affiliates. All rights reserved.
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

package test.javaclient.shared;

import com.sun.glass.ui.Application;
import com.sun.glass.ui.Robot;
import java.io.File;
import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import test.javaclient.shared.screenshots.ScreenshotUtils;
import static test.javaclient.shared.TestUtil.isEmbedded;
import test.javaclient.shared.screenshots.GoldenImageManager;

/**
 * Base class for scenegraph tests. Provides jemmy initialization, scene finding
 * and {@link test.scenegraph.app.AbstractApp} based tests support.
 *
 * @author Sergey Grinev, Andrey Glushchenko
 */
public class TestBase extends TestBaseBase {

    private static boolean isTest = false;
    protected static Robot robot = null;

    static {
        Utils.initializeAwt();
        if (Utils.isMacOS()) {
            JemmyUtils.runInOtherJVM(true);
        }
    }

    @BeforeClass
    public static void setUpClass() {
        TestBaseBase.setUpClass();
        isTest = true;
    }

    public static boolean isTest() {
        return isTest;
    }
    /*
     * I don't know what is it, but many tests use it. Copy from TestBaseOld
     *
     */
    public void setWaitImageDelay(long _delay) {
        if(isEmbedded()) {
            _delay += 18000;
        }
        Root.ROOT.getEnvironment().setTimeout(Wrap.WAIT_STATE_TIMEOUT, _delay);
    }

    @Before
    @Override
    public void before() {
        super.before();
    }
    protected void testCommon(String name) {
        testCommon(name, null, true, false);
    }
    public void testCommon(String toplevel_name, String innerlevel_name) {
        testCommon(toplevel_name, innerlevel_name, true, false);
    }
    public void testCommon(String toplevel_name, String innerlevel_name, int pagesNumber) {
        testCommon(toplevel_name, innerlevel_name, true, false, pagesNumber);
    }
    public void testCommon(String toplevel_name, String innerlevel_name, boolean shoots, boolean valuable_rect) {
        testExecuter(toplevel_name, innerlevel_name, shoots, valuable_rect, 1);
    }
    public void testCommon(String toplevel_name, String innerlevel_name, boolean shoots, boolean valuable_rect, int pagesNumber) {
        testExecuter(toplevel_name, innerlevel_name, shoots, valuable_rect, pagesNumber);
    }
    protected void testExecuter(String toplevel_name, String innerlevel_name, boolean shoots, boolean valuable_rect, int pagesNumber) {
        testExecuter(toplevel_name, innerlevel_name, shoots, valuable_rect, pagesNumber,true);

    }
    /**
     * For embedded purpose.
     *
     * @param pagesNumber define number of control variations to show on the
     * page(1 for normal mode, several for embedded mode). pageName is local
     * variable to store original page name, it is modified in case of embedded
     * mode : PageNameFirst, ... see TestUtil SUFFIXES array
     */
    protected void testExecuter(final String toplevel_name, final String innerlevel_name, boolean shoots, boolean valuable_rect, int pagesNumber,boolean throwErrors) {
        boolean isEmbedded = TestUtil.isEmbedded();
        String pageName = toplevel_name;
        if (!isEmbedded) {
            pagesNumber = 1;
        }
        for (int i = 0; i < pagesNumber; i++) {
            //checking pagesNumber > 1 to use suffixes only in case of embedded mode and multiply page slots
            if (isEmbedded && pagesNumber > 1) {
                pageName = toplevel_name;
                pageName += TestUtil.SUFFIXES[i];
            }


            getScene().mouse().move(new Point(0, 0));
            final String pageNameForTest = pageName;

            new GetAction() {
                @Override
                public void run(Object... parameters) {

                    if ( null == application ) { // sometimes in applet. reason: unknown
                     application = AbstractApp2.getLastInstance();
                    }

                    final TestNode tn = getApplication().openPage(pageNameForTest, innerlevel_name);
                    Assert.assertNotNull(tn);
                    Assert.assertNotNull("Test node named: '"
                                    + pageNameForTest + ((null == innerlevel_name) ? "" : (" - " + innerlevel_name))
                                    + "' wasnt found in :" + application.toString(),tn);
                }
            }.dispatch(Environment.getEnvironment());

            try {
                Thread.sleep(600); // ugly workaround to be removed ASAP
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            verifyFailures();

            if (shoots) {
                try {
                    Thread.sleep(AppLauncher.getInstance().getTestDelay());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                //verify screenshot
                String normalizedName = Utils.normalizeName(pageName + (innerlevel_name != null ? innerlevel_name : ""));
                ScreenshotUtils.checkPageContentScreenshot(new StringBuilder(getName()).append("-").append(normalizedName).toString(), valuable_rect);
                if (throwErrors) {
                    ScreenshotUtils.throwScreenshotErrors();
                }
            }
        }
    }

    private void doRenderToImageAndWait(final Wrap<? extends Node> noda,
            final SnapshotParameters _sp) {
        // 2. do "RenderToImage", show rendered image in ImageView, replace scene root
        // with tmp Group with ImageView in it.
        new GetAction() {
            @Override
            public void run(Object... parameters) {
                ((BasicButtonChooserApp) application).doRenderToImage(getScene().getControl(),
                        (null == noda) ? null : noda.getControl(), _sp);
            }
        }.dispatch(Environment.getEnvironment());

        // wait rendered image ready
        getScene().waitState(new State() {
            public Object reached() {
                if ((new GetAction() {
                    @Override
                    public void run(Object... parameters) {
                        if (((BasicButtonChooserApp) application).isImageReady()) {
                            setResult(Boolean.TRUE);
                        }
                    }
                }.dispatch(Environment.getEnvironment())) == Boolean.TRUE) {
                    return Boolean.TRUE;
                }
                return null;
            }
        });
    }

    public void testCommonsAdditional(String slotName, String additionalActionButtonName) {
        openPage(slotName);
        clickTextButton(additionalActionButtonName);
        verifyGetters();

        //verify screenshot
        ScreenshotUtils.checkPageContentScreenshot(new StringBuilder(getName()).append("-").append(slotName).append("-").append("additional").toString());

    }

    public void testAdditionalAction(String toplevel_name, String innerlevel_name) {
        testAdditionalAction(toplevel_name, innerlevel_name, true);
    }

    public void testAdditionalAction(String toplevel_name, String innerlevel_name, boolean shoots) {
        testAdditionalAction(toplevel_name, innerlevel_name, shoots, true);
    }

    public void testAdditionalAction(final String toplevel_name, final String innerlevel_name, boolean shoots, boolean throwError) {
        getScene().mouse().move(new Point(0, 0));
        try {
            Thread.sleep(100); // ugly workaround to be removed ASAP
        } catch (InterruptedException ex) {
        }
        if ( null == application ) { // sometimes in applet. reason: unknown
            application = AbstractApp2.getLastInstance();
        }

        Assert.assertNotNull(application);  //sometimes
        new GetAction() {
            @Override
            public void run(Object... parameters) {
                TestNode tn =
                        application.openPage(toplevel_name, innerlevel_name);
                        Assert.assertNotNull(tn);
            }
        }.dispatch(Environment.getEnvironment());

        try {
            Thread.sleep(100); // ugly workaround to be removed ASAP
        } catch (InterruptedException ex) {
        }
        new GetAction() {
            @Override
            public void run(Object... parameters) {
                application.doAdditionalAction(toplevel_name, innerlevel_name);
            }
        }.dispatch(Environment.getEnvironment());

        try {
            Thread.sleep(400); // ugly workaround to be removed ASAP
        } catch (InterruptedException ex) {
        }
        verifyFailures();

        if (shoots) {
            //verify screenshot
            String testName = new StringBuilder(getName()).append("-").append(toplevel_name).append(innerlevel_name).append("-").append("additional").toString();
            String pathToGoldenScreenshot = GoldenImageManager.getScreenshotPath(testName);
            if (!(new File(pathToGoldenScreenshot)).exists()) {
                testName = new StringBuilder(getName()).append("-").append(toplevel_name).append(innerlevel_name).toString();
            }
            ScreenshotUtils.checkPageContentScreenshot(testName);
            if (throwError) {
                ScreenshotUtils.throwScreenshotErrors();
            }
        }

    }

    public void testRenderSceneToImage(String toplevel_name, String innerlevel_name) { //SCENE
        testRenderSomethingToImage(toplevel_name, innerlevel_name, "scene-",
                null);

    }

    public void testRenderSomethingToImage(String toplevel_name, String innerlevel_name,
            String prefixForFilename, final Wrap<? extends Node> noda) {

        String filename = prefixForFilename + getNameForScreenShot(toplevel_name, innerlevel_name);
        org.jemmy.image.Image imageJemmy = (null == noda) ? getScene().getScreenImage() : noda.getScreenImage();

        String resName = GoldenImageManager.getScreenshotPath(filename);
        String res2Name = GoldenImageManager.getScreenshotPath(filename + "-2");

        imageJemmy.save(resName);

        doRenderToImageAndWait(noda, null);

        // TAKE A SHOT USING JEMMY, save it (scene with ImageView with rendered image in it)
        //org.jemmy.image.Image sceneImageJemmy2 = scene.getScreenImage();
        //sceneImageJemmy2.save(ImagesManager.getInstance().getScreenshotPath(filename + "2"));

        String diffName = GoldenImageManager.getScreenshotPath(filename + "-diff");
        boolean scenesEqual = true;
        try {
            if (null == noda) {
                getScene().waitImage(getScene().getEnvironment().getImageLoader().load(resName),
                        res2Name, diffName);
            } else {
                Wrap<? extends Node> imViewWrap =
                        (new NodeDock(getScene().as(Parent.class, Node.class), "ViewOfNodeSnapshot")).wrap();
                //Lookups.byID(scene, "ViewOfNodeSnapshot", ImageView.class);

                imViewWrap.waitImage(getScene().getEnvironment().getImageLoader().load(resName),
                        res2Name, diffName);
            }
        } catch (org.jemmy.TimeoutExpiredException ex) {
            scenesEqual = false;
        } finally {
            restoreSceneRoot();
        }

        if (scenesEqual) {
            System.out.print("delete scene- and node- images.... ");
            new File(resName).delete();
            new File(res2Name).delete();
            System.out.println(" Completed.");
        } else {
            System.out.print("scene/node render to image failed.");
            throw new org.jemmy.TimeoutExpiredException("waitImage failed for file:" + resName);
        }
    }
    public void testNodeSnapshotWithParameters (String toplevel_name, String innerlevel_name,
            final SnapshotParameters _sp, final String param_name) {

        final Wrap<? extends Node> noda = ScreenshotUtils.getPageContent();
        doRenderToImageAndWait(noda, _sp);

        org.jemmy.TimeoutExpiredException saved_ex = null;
        try {

                Wrap<? extends Node> imViewWrap =
                        Lookups.byID(getScene(), "ViewOfNodeSnapshot", ImageView.class);

                ScreenshotUtils.checkScreenshot(new StringBuilder(getName()).append("-")
                        .append(param_name).toString(), imViewWrap);

        } catch (org.jemmy.TimeoutExpiredException ex) {
            saved_ex = ex;
        } finally {
            restoreSceneRoot();
        }
        if ( null != saved_ex) {
            throw saved_ex;
        }
    }

    /**
     * Utility method to slow down test if specific keys is set (false by
     * default)
     */
    @After
    public void after() {
        TestUtil.slow(1000);
    }

    protected void dnd(final Wrap from, final Wrap to) {
        Point fromPoint = from.getClickPoint();
        Point toPoint = to.getClickPoint();
        if (from.getControl() == to.getControl()) {
            //If "from" control is the same as "to" control, drag is performed within a control
            //from top-left corner to bottom-right corner.
            double width = from.getScreenBounds().width / 3;
            double height = from.getScreenBounds().height / 3;
            fromPoint.move((int) (fromPoint.x - width), (int) (fromPoint.y - height));
            toPoint.move((int) (toPoint.x + width), (int) (toPoint.y + height));
        }
        dnd(from, fromPoint, to, toPoint);
    }

    protected void dnd(Wrap from, Point fromPoint, Wrap to, Point toPoint) {
        try {
            if (!Utils.isWindows()) {
                System.err.println("Use jemmy robot");
                from.drag().dnd(fromPoint, to, toPoint);
            } else {
                System.err.println("Use Glass robot");
                dndRobot(from, fromPoint, to, toPoint);
            }
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.err.println("Error while DnD: " + ex);
        }
    }

    private void dndRobot(Wrap from, Point fromPoint, Wrap to, Point toPoint) throws InterruptedException {
        Point absFromPoint = new Point(fromPoint);
        Point absToPoint = new Point(toPoint);
        absFromPoint.translate((int) from.getScreenBounds().getX(), (int) from.getScreenBounds().getY());
        absToPoint.translate((int) to.getScreenBounds().getX(), (int) to.getScreenBounds().getY());
        Semaphore s = new Semaphore(0);
        Platform.runLater(() -> {
            if (robot == null) {
                robot = Application.GetApplication().createRobot();
            }
            robot.mouseMove(absFromPoint.x, absFromPoint.y);
            robot.mousePress(Robot.MOUSE_LEFT_BTN);
            final int STEPS = 50;
            int dx = absToPoint.x - absFromPoint.x;
            int dy = absToPoint.y - absFromPoint.y;
            for (int i = 0; i <= STEPS; i++) {
                robot.mouseMove(absFromPoint.x + dx * i / STEPS, absFromPoint.y + dy * i / STEPS);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    System.err.println("Error while dragging: " + ex);
                    ex.printStackTrace();
                }
            }
            robot.mouseRelease(Robot.MOUSE_LEFT_BTN);
            s.release();
        });
        s.acquire();
    }
}
