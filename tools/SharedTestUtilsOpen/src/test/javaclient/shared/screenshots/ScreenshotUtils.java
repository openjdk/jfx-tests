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
package test.javaclient.shared.screenshots;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jemmy.Rectangle;
import org.jemmy.TimeoutExpiredException;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.Root;
import org.jemmy.image.Image;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import org.junit.Assert;
import test.javaclient.shared.AbstractTestableApplication;
import test.javaclient.shared.JemmyUtils;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.description.FXSceneTreeBuilder;
import test.javaclient.shared.description.FXSimpleNodeDescription;
import test.javaclient.shared.description.TreeNode;

/**
 * @author Andrey Glushchenko
 */
public class ScreenshotUtils {
    //private static Wrap<? extends Scene> scene = null;
    private static AbstractTestableApplication application;
/**
     * Verify or generate golden screenshot for a test depending on
     * <code>IS_GOLDEN_MODE</code> status.
     *
     * @param testClass
     * @param testName test name for a test
     * @param scene scene to take a screenshot of
     */
    public static void checkScreenshot(Class testClass, String testName, Wrap node) {
        checkScreenshot(testClass.getName() + "." + testName, node);
    }
    /**
     * Verify or generate golden screenshot for a test depending on
     * <code>IS_GOLDEN_MODE</code> status.
     *
     * @param testName test name for a test
     * @param scene scene to take a screenshot of
     */
    public static void checkScreenshot(String testName, Wrap node) {
        checkScreenshot(testName, node, null);
    }

    public static void checkScreenshot(String testName, Wrap node, int width, int height) {
        checkScreenshot(testName, node, new Rectangle(width, height));
    }

    public static void checkScreenshot(String testName, final Wrap node, Rectangle rect) {
        Image sceneImage;
        if (rect != null) {
            sceneImage = node.getScreenImage(rect);
        } else {
            sceneImage = node.getScreenImage();
        }
        String name = ImagesManager.getInstance().lookupGoldenScreenshot(testName);
        String descrName = ImagesManager.getInstance().getDescriptionPath(testName);
        Assert.assertNotNull("Invalid images path", name);
        if (TestUtil.IS_GOLDEN_MODE) {
            //TODO: specified directory
            sceneImage.save(ImagesManager.getInstance().getScreenshotPath(testName));
        } else {
            String diffName = ImagesManager.getInstance().getScreenshotPath(testName + "-diff");
            String resName = ImagesManager.getInstance().getScreenshotPath(testName);
            if (TestUtil.IS_DESCRIPTION_MODE) {
                String descr_name = ImagesManager.getInstance().lookupGoldenDescription(testName);
                if (new File(descr_name).exists()) {
                    try {
                        List<TreeNode<FXSimpleNodeDescription>> list = (List<TreeNode<FXSimpleNodeDescription>>) TestUtil.read(descr_name);
                        node.waitState(new State<List<TreeNode<FXSimpleNodeDescription>>>() {
                            public List<TreeNode<FXSimpleNodeDescription>> reached() {
                                return FXSceneTreeBuilder.build(node);
                            }

                            @Override
                            public String toString() {
                                return "Control having expected image description";
                            }
                        }, list);
                    } catch (TimeoutExpiredException e) {
                        if (diffName != null) {
                            node.getEnvironment().getOutput(Wrap.OUTPUT).println("Saving difference to " + diffName);
                            final Image diffImage = node.getScreenImage().compareTo(node.getEnvironment().getImageLoader().load(name));
                            if (diffImage != null) {
                                diffImage.save(diffName);
                            }
                        }
                        throw e;
                    } finally {
                        if (resName != null) {
                            node.getEnvironment().getOutput(Wrap.OUTPUT).println("Saving result to " + resName);
                            node.getScreenImage().save(resName);
                        }
                    }
                } else {
                    Assert.fail("Golden image description (" + descr_name + ") not found. Actual image is saved as " + descrName);
                    ArrayList<TreeNode<FXSimpleNodeDescription>> description = FXSceneTreeBuilder.build(node);
                    TestUtil.write(description, descrName);
                }
            } else {
                if (new File(name).exists()) {
                    try {
                        if (rect != null) {
                            node.waitImage(node.getEnvironment().getImageLoader().load(name), rect, resName, diffName);
                        } else {
                            node.waitImage(node.getEnvironment().getImageLoader().load(name), resName, diffName);
                        }
                    } catch (TimeoutExpiredException imagesAreDifferentException) {
                        //If images are really different, then not only diff and
                        //original images are to put in the results folder, but also golden image.
                        try {
                            String expectedName = ImagesManager.getInstance().getScreenshotPath(testName + "-expected");
                            node.getEnvironment().getImageLoader().load(name).save(expectedName);
                        } catch (Throwable ex) {
                            ex.printStackTrace();
                        }
                        throw imagesAreDifferentException;
                    }
                    if (TestUtil.IS_DESCRIBING_MODE) {
                        ArrayList<TreeNode<FXSimpleNodeDescription>> description = FXSceneTreeBuilder.build(node);
                        TestUtil.write(description, descrName);
                    }
                } else {
                    sceneImage.save(resName);
                    Assert.fail("Golden image (" + name + ") not found. Actual image is saved as " + resName);
                }
            }
        }
    }
    private static List<Throwable> screenshotErrors = new LinkedList<Throwable>();

    public static void setApplication(AbstractTestableApplication application) {
        ScreenshotUtils.application = application;
    }
    public static void setScene(Wrap<? extends Scene> scene) {
       // ScreenshotUtils.scene = scene;
    }
    
    private static void error(Throwable ex){
        screenshotErrors.add(ex);
        ex.printStackTrace();
    }
    /**
     * Set image comparator distance. Should be called from constructor or {}
     * class initialization block.
     *
     * @param comparatorDistance distance
     */
    public static void setComparatorDistance(float comparatorDistance) {
        JemmyUtils.comparatorDistance = comparatorDistance;
    }
    public static void throwScreenshotErrors(){
        StringBuilder sb = new StringBuilder();
        for(Throwable ex: screenshotErrors){
            sb.append(ex.toString()).append("\n");
        }
        screenshotErrors.clear();
        String msg = sb.toString();
        if(!"".equals(msg) && msg!=null){
            Assert.fail(msg);
        }
    }

    public static void checkPageContentScreenshot(String name) {
        checkPageContentScreenshot(name, false);
    }

    public static void checkPageContentScreenshot(String name, boolean valuable_rect) {
        try {
        if(application.getNodeForScreenshot()==null){
        final Wrap<? extends Scene> scene = Root.ROOT.lookup(new ByWindowType(Stage.class)).lookup(Scene.class).wrap(0);

            checkScreenshot(name, scene);
        }else{
            if (valuable_rect) {
                checkScreenshot(name, getPageContent(), getPageContentSize());
            } else {
                checkScreenshot(name, getPageContent());
            }
        }
        } catch (Throwable th) {
            error(th);
        }
    }
    public  static Wrap<? extends Node> getPageContent() {
        Node n = application.getNodeForScreenshot();
        final Wrap<? extends Scene> scene = Root.ROOT.lookup(new ByWindowType(Stage.class)).lookup(Scene.class).wrap(0);
        return new NodeDock(scene.as(Parent.class, Node.class), n.getId()).wrap();
    }
    /**
     *
     * @return rectangle contains all children of page content. This rectangle
     * may have different size then size of Content.
     */
    public static Rectangle getPageContentSize() {
        Node node = getPageContent().getControl();
        Rectangle rect = new Rectangle();
        if (node instanceof Pane) {
            Pane p = (Pane) node;
            for (Node n : p.getChildren()) {
                rect.add((int) n.getBoundsInParent().getMaxX(), (int) n.getBoundsInParent().getMaxY());
            }
        } else {
            rect.add((int) node.getBoundsInParent().getMaxX(), (int) node.getBoundsInParent().getMaxY());
        }
        return rect;
    }
    
}
