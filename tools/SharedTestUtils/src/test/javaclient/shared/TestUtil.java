/*
 * Copyright (c) 2010-2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.javaclient.shared;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.image.Image;
import test.javaclient.shared.description.TreeNode;
import test.javaclient.shared.screenshots.ImagesManager;

/**
 * Utility methods
 *
 * @author Sergey Grinev
 */
public class TestUtil {

    public static final boolean IS_GOLDEN_MODE = Boolean.getBoolean("test.javaclient.creategolden");
    private static final boolean IS_SLOW_MODE = Boolean.getBoolean("test.javaclient.slowmode");
    public static final boolean IS_DESCRIBING_MODE = Boolean.getBoolean("test.javaclient.createdescription");
    public static final boolean IS_DESCRIPTION_MODE = Boolean.getBoolean("test.javaclient.description");
    /**
     * Suffixes for embedded mode.
     */
    public static final String[] SUFFIXES = new String[]{"First", "Second", "Third", "Forth"};

    /**
     * For embedded mode. Verify -Djavatest.vmOptions="-DembeddedMode=X",
     * whether X is true or false
     *
     * @return true if X is true otherwise false
     */
    public static boolean isEmbedded() {
        String vmOpt = System.getProperty("embeddedMode");
        return vmOpt != null && vmOpt.equals("true");
    }

    /**
     * For embedded mode. Verify -Djavatest.vmOptions="-DembeddedPlatform=X",
     * whether X is eglfb
     *
     * @return argument value or null if it is not presented
     */
    public static String getEmbeddedFxPlatform() {
        String vmOpt = System.getProperty("embeddedPlatform");
        return (vmOpt != null) ? vmOpt : null;
    }





    public static boolean write(Serializable obj, String name) {
        try {
            FileOutputStream ostream = new FileOutputStream(name);
            ObjectOutputStream p = new ObjectOutputStream(ostream);
            p.writeObject(obj);
            p.flush();
            ostream.close();
        } catch (IOException ex) {
            Logger.getLogger(TreeNode.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public static Object read(String name)  {
        Object obj = null;
        try {
            FileInputStream ostream = new FileInputStream(name);
            ObjectInputStream p = new ObjectInputStream(ostream);
             obj = p.readObject();
            ostream.close();
        } catch (IOException ex) {
            Logger.getLogger(TreeNode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TreeNode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }



    /**
     * Verify screenshots of two wraps
     *
     * @param testName test name for a test
     * @param existing wrap for object already placed on UI
     * @param oneToWaitFor wrap for object can be not yet placed on UI
     */
    public static void compareScreenshots(String testName, Wrap existing, Wrap oneToWaitFor) {
        Image sceneImage = existing.getScreenImage();
        String diffName = ImagesManager.getInstance().getScreenshotPath(testName + "-diff"); //= RESULT_PATH + testName + "-diff.png";
        String resName = ImagesManager.getInstance().getScreenshotPath(testName); //= RESULT_PATH + testName + IMAGE_POSTFIX;
        oneToWaitFor.waitImage(sceneImage, resName, diffName);
    }

    /**
     * Debugging Code. Will slow test execution if
     * <code>IS_SLOW_MODE</code> is set.
     */
    public static void slow(long delay) {
        if (IS_SLOW_MODE) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
            }
        }
    }

    public static Wrap<? extends Scene> getScene() {
        final Wrap<? extends Scene> scene;
        if (AppLauncher.getInstance().getMode() == AppLauncher.Mode.SWING) {
            scene = SwingAWTUtils.getScene();
        } else if (AppLauncher.getInstance().getMode() == AppLauncher.Mode.SWT) {
            scene = SWTUtils.getScene();
        } else {
            scene = Root.ROOT.lookup(new ByWindowType(Stage.class)).lookup(Scene.class).wrap(0);
            Utils.deferAction(new Runnable() {
                public void run() {
                    scene.getControl().getWindow().setFocused(true);
                }
            });
        }
        return scene;
    }

    public static String getTitle(Scene scene) {
        Window window = scene.getWindow();
        if (Stage.class.isInstance(window)) {
            return ((Stage) window).getTitle();
        }
        return "";
    }

    public static void setTitle(Scene scene, String title) {
        Window window = scene.getWindow();
        if (Stage.class.isInstance(window)) {
            ((Stage) window).setTitle(title);
        }
    }
}
