/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional;

import java.awt.GraphicsEnvironment;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.swing.JFrame;
import org.jemmy.Rectangle;
import org.jemmy.TimeoutExpiredException;
import org.jemmy.action.GetAction;
import org.jemmy.awt.AWT;
import org.jemmy.awt.Showing;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByTitleSceneLookup;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.Root;
import org.jemmy.image.GlassImage;
import org.jemmy.image.GlassImageCapturer;
import org.jemmy.image.Image;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import static test.javaclient.shared.JemmyUtils.getScreenCapture;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.screenshots.ImagesManager;
import test.scenegraph.app.StageSceneApp;

/**
 *
 * @author Sergey Grinev
 */
public class StageSceneTest extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() throws Exception {
        StageSceneApp.main(null);
    }

    @Test
    public void StageSizePositionTitle() {
        boolean isRemote = (test.javaclient.shared.AppLauncher.getInstance().getMode() == test.javaclient.shared.AppLauncher.Mode.REMOTE);
        boolean isJnlp = (null != System.getProperty("jnlpx.home"));

        if (isRemote && !isJnlp) {
            System.out.println(" ====== applet mode =======");
        } else {

            String prop = System.getProperty("javafx.swinginteroperability");
            if (prop != null && prop.compareToIgnoreCase("true") == 0) {
                JFrame frame = AWT.getAWT().lookup(JFrame.class, new Showing<JFrame>()).wrap(0).getControl();
                assertTrue(frame.getX() == 50);
                assertTrue(frame.getY() == 70);
                assertTrue(frame.getWidth() == 250);
                assertTrue(frame.getHeight() == 100);
            } else {
                Window stage = Root.ROOT.lookup(Scene.class,
                        new ByTitleSceneLookup<Scene>(StageSceneApp.class.getSimpleName())).wait(1).get(0).getWindow();
                assertTrue(stage.getX() == 50);
                assertTrue(stage.getY() == 70);
                assertTrue(stage.getWidth() == 250);
                assertTrue(stage.getHeight() == 100);
            }

            final String testname = "StageSizePositionTitle";

            String name = ImagesManager.getInstance().lookupGoldenScreenshot(testname);

            final String diffID = ImagesManager.getInstance().getScreenshotPath("StageSizePositionTitle-diff");
            final String resID = ImagesManager.getInstance().getScreenshotPath(testname);
            final Rectangle rect = new Rectangle(50, 70, 250, 100);

            // we can't use node based capturing because we have to capture part of screen independently and check if stage is really at this place
            waitScreenImage(name, diffID, resID, rect);

        }
        TestUtil.slow(1500);
    }

    void waitFullScreen(Stage _stage) {
        final Stage stage = _stage;
        scene.waitState(new State() {

            public Object reached() {
                if ((new GetAction<Boolean>() {

                    @Override
                    public void run(Object... os) throws Exception {
                        if (((Stage) stage).isFullScreen()) {
                            setResult(Boolean.TRUE);
                        }
                    }
                }).dispatch(scene.getEnvironment()) == Boolean.TRUE) {
                    return Boolean.TRUE;
                }
                return null;
            }
        });
    }

    @Test
    public void fullScreen() {
        Wrap<? extends Button> label = Lookups.byID(scene, "full screen", Button.class);
        label.mouse().click();

        String prop = System.getProperty("javafx.swinginteroperability");
        if (prop != null && prop.compareToIgnoreCase("true") == 0) {
            JFrame frame = AWT.getAWT().lookup(JFrame.class, new Showing<JFrame>()).wrap(0).getControl();
            assertTrue(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getFullScreenWindow() == frame);
        } else {
            Window stage = Root.ROOT.lookup(Scene.class, new ByTitleSceneLookup<Scene>(StageSceneApp.class.getSimpleName())).wait(1).get(0).getWindow();
            if (stage instanceof Stage) {
                waitFullScreen((Stage) stage);
                assertTrue(((Stage) stage).isFullScreen());
            } else {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        final Rectangle sceneSize = scene.getScreenBounds();

        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                boolean foundProperScreenAndTestSize = false;
                for (Screen s : Screen.getScreens()) {
                    if ((s.getBounds().getWidth() == sceneSize.width) && (s.getBounds().getHeight() == sceneSize.height)) {
                        foundProperScreenAndTestSize = true;
                        System.out.println("* Screen: " + s + " is appropriate for fullscreen scene (size: " + sceneSize + ")");
                    } else {
                        System.out.println("Screen: " + s + " is not appropriate for fullscreen scene (size: " + sceneSize + ")");
                    }
                }
                assertTrue("Cannot found appropriete screen. See log above", foundProperScreenAndTestSize);
            }
        }.dispatch(Root.ROOT.getEnvironment());

        // we can't use node based capturing because we have to capture part of screen independently and check if stage is really at this place
        final String diffID = ImagesManager.getInstance().getScreenshotPath("StageSceneTest-fullscreen-diff");
        final String resID = ImagesManager.getInstance().getScreenshotPath("StageSceneTest-fullscreen");
        waitScreenImage(ImagesManager.getInstance().lookupGoldenScreenshot("StageFullScreen"), diffID, resID, new Rectangle(250, 100, 100, 100));
        StageSceneApp.setNonFullScreen();
    }

    static Image diffImage = null;
    private void waitScreenImage(final String goldenName, String diffID,final  String resID, final Rectangle rect) {
        final Image golden = TestUtil.IS_GOLDEN_MODE ? null : Root.ROOT.getEnvironment().getImageLoader().load(goldenName);
        try {
            Root.ROOT.getEnvironment().getWaiter(Wrap.WAIT_STATE_TIMEOUT).ensureState(new State<Object>() {

                public Object reached() {
                    Image screenshot = null;
                    if (golden instanceof GlassImage) {

                        com.sun.glass.ui.Pixels p = GlassImageCapturer.getRobot().getScreenCapture(rect.x , rect.y , rect.width, rect.height);
                        screenshot = new GlassImage( Root.ROOT.getEnvironment(), p );

                    } else {
                        screenshot = getScreenCapture(rect);
                    }

                    if (resID != null && null != screenshot) {
                        System.out.println("Saving result to " + resID); // wrong output?
                        screenshot.save(resID);
                    }

                    if (TestUtil.IS_GOLDEN_MODE) {
                        screenshot.save(goldenName);
                        return true;
                    }

                    diffImage = screenshot.compareTo(golden);
                    return (diffImage == null) ? true : null;
                }

                @Override
                public String toString() {
                    return "Stage having expected image";
                }
            });
        } catch (TimeoutExpiredException e) {
            if (diffID != null) {
                System.out.println("Saving difference to " + diffID); // wrong output?
                diffImage.save(diffID);
            }
            throw e;
        } finally {
        }
    }
}
