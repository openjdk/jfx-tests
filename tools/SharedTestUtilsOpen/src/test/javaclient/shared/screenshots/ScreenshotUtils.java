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
package test.javaclient.shared.screenshots;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jemmy.Rectangle;
import org.jemmy.TimeoutExpiredException;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.Root;
import org.jemmy.image.Image;
import org.jemmy.image.ImageLoader;
import org.jemmy.interfaces.Parent;
import org.junit.Assert;
import test.javaclient.shared.AbstractTestableApplication;
import test.javaclient.shared.JemmyUtils;

/**
 * @author Andrey Glushchenko, andrey.rusakov@oracle.com
 */
public class ScreenshotUtils {

    private static final List<Throwable> SCREENSHOT_ERRORS = new LinkedList<>();
    private static AbstractTestableApplication application;

    /**
     * Verify or generate golden screenshot for a test.
     *
     * @param testClass
     * @param testName test name for a test
     * @param scene scene to take a screenshot of
     */
    public static void checkScreenshot(Class testClass, String testName, Wrap node) {
        checkScreenshot(testClass.getName() + "." + testName, node);
    }

    /**
     * Verify or generate golden screenshot for a test.
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
        ImageLoader imgLoader = node.getEnvironment().getImageLoader();
        List<String> goldenImages = GoldenImageManager.getTestImages(testName, ".png");
        String resultPath = GoldenImageManager.getScreenshotPath(testName);
        if (goldenImages.isEmpty()) {
            Image sceneImage = (rect == null) ? node.getScreenImage() : node.getScreenImage(rect);
            sceneImage.save(resultPath);
            throw new RuntimeException("No golden images found for " + testName
                    + ", actual image saved to " + resultPath);
        }
        boolean nothingMatched = true;
        for (int i = 0; i < goldenImages.size(); i++) {
            String goldenPath = goldenImages.get(i);
            String goldenName = Paths.get(goldenPath).getFileName().toString()
                    .replaceFirst("\\.[^\\.]*$", String.format("-%02d", i));
            String diffPath = GoldenImageManager.getScreenshotPath(goldenName + "-diff");
            Image image = imgLoader.load(goldenPath);
            try {
                if (rect != null) {
                    node.waitImage(image, rect, resultPath, diffPath);
                } else {
                    node.waitImage(image, resultPath, diffPath);
                }
                nothingMatched = false;
                break;
            } catch (TimeoutExpiredException imagesAreDifferentException) {
                try {
                    String expectedName = GoldenImageManager.getScreenshotPath(goldenName + "-expected");
                    imgLoader.load(goldenPath).save(expectedName);
                } catch (Throwable ex) {
                    System.err.println(ex);
                }
            }
        }
        if (nothingMatched) {
            throw new TimeoutExpiredException("Control having expected image has not been reached");
        }
    }

    public static void setApplication(AbstractTestableApplication application) {
        ScreenshotUtils.application = application;
    }

    public static void setScene(Wrap<? extends Scene> scene) {
        // ScreenshotUtils.scene = scene;
    }

    private static void error(Throwable ex) {
        SCREENSHOT_ERRORS.add(ex);
        ex.printStackTrace();
    }

    public static void setComparatorDistance(float comparatorDistance) {
        JemmyUtils.comparatorDistance = comparatorDistance;
    }

    public static void throwScreenshotErrors() {
        StringBuilder sb = new StringBuilder();
        for (Throwable ex : SCREENSHOT_ERRORS) {
            sb.append(ex.toString()).append("\n");
        }
        SCREENSHOT_ERRORS.clear();
        String msg = sb.toString();
        if (!"".equals(msg) && msg != null) {
            Assert.fail(msg);
        }
    }

    public static void checkPageContentScreenshot(String name) {
        checkPageContentScreenshot(name, false);
    }

    public static void checkPageContentScreenshot(String name, boolean valuable_rect) {
        try {
            if (application.getNodeForScreenshot() == null) {
                final Wrap<? extends Scene> scene = Root.ROOT.lookup(new ByWindowType(Stage.class)).lookup(Scene.class).wrap(0);

                checkScreenshot(name, scene);
            } else if (valuable_rect) {
                checkScreenshot(name, getPageContent(), getPageContentSize());
            } else {
                checkScreenshot(name, getPageContent());
            }
        } catch (Throwable th) {
            error(th);
        }
    }

    public static Wrap<? extends Node> getPageContent() {
        Node n = application.getNodeForScreenshot();
        final Wrap<? extends Scene> scene = Root.ROOT.lookup(new ByWindowType(Stage.class)).lookup(Scene.class).wrap(0);
        //return new NodeDock(scene.as(Parent.class, Node.class), n.getId()).wrap();
        return new NodeDock((Parent<Node>)scene.as(Parent.class, Node.class), n.getId()).wrap();
    }

    /**
     *
     * @return rectangle contains all children of page content. This rectangle may have different
     * size then size of Content.
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
