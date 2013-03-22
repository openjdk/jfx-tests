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
package test.javaclient.shared;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jemmy.TimeoutExpiredException;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.image.Image;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
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

    public static Object read(String name) {
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
    public static void compareScreenshots(String testName, final Wrap existing, final Wrap oneToWaitFor) {
        long time = 1000;
        try {
            new Waiter(new Timeout("tryWaitTimeout", time)).waitState(
                    new State<Boolean>() {
                        @Override
                        public Boolean reached() {
                            return oneToWaitFor.getScreenImage().compareTo(existing.getScreenImage()) != null
                                    ? null : Boolean.TRUE;
                        }});
        } catch (TimeoutExpiredException ex) {
            //There is a difference
            Image res = oneToWaitFor.getScreenImage();
            Image expected = existing.getScreenImage();
            Image diff = res.compareTo(expected);
            
            String resName = ImagesManager.getInstance().getScreenshotPath(testName);
            String diffName = ImagesManager.getInstance().getScreenshotPath(testName + "-diff");
            String expectedName = ImagesManager.getInstance().getScreenshotPath(testName + "-expected");
            
            oneToWaitFor.getEnvironment().getOutput(Wrap.OUTPUT).println(
                    String.format("Saving diff to [%s], result to [%s], expected to [%s]", diffName, resName, expected));
            
            res.save(resName);
            diff.save(diffName);
            expected.save(expectedName);
        }
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

        scene = Root.ROOT.lookup(new ByWindowType(Stage.class)).lookup(Scene.class).wrap(0);
        Utils.deferAction(new Runnable() {
            public void run() {
                scene.getControl().getWindow().setFocused(true);
            }
        });

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
