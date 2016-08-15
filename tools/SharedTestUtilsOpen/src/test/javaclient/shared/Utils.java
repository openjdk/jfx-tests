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

import javafx.application.Application;
import com.sun.javafx.runtime.VersionInfo;
import com.sun.prism.GraphicsPipeline;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jemmy.fx.Browser;
import static test.javaclient.shared.JemmyUtils.usingGlassRobot;
import test.javaclient.shared.imagescomparator.ImageDuplicateExtractor;

/**
 *
 * @author Sergey Grinev
 */
public class Utils {

    public static void addBrowser(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            boolean browserStarted = false;

            public void handle(KeyEvent ke) {
                if (!browserStarted && ke.isControlDown() && ke.isShiftDown() && ke.getCode() == KeyCode.B) {
                    browserStarted = true;
                    deferAction(new Runnable() {
                        public void run() {
                            try {
                                Browser.runBrowser();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

        System.err.println("Click Ctrl-Shift-B to run FX Browser.");

    }

    @Deprecated
    public static void start(final Runnable action) {
        com.sun.javafx.application.PlatformImpl.startup(action);
    }

    public static void deferAction(Runnable action) {
        javafx.application.Platform.runLater(action);
    }

    public static void launch(final Class cl, String[] args) {
        if (TestBase.isTest()) {
            BasicButtonChooserApp.showButtonsPane(false);
        }
        AppLauncher.getInstance().launch(cl, args);
    }

    @Deprecated
    public static String __FILE__(Object _this, String path) {
        return _this.getClass().getResource(path).toString();
    }

    public static Node labeledValue(String label, boolean value, String valueId) {
        return labeledValue(label, Boolean.toString(value), valueId);
    }

    public static Node labeledValue(String label, String value, String valueId) {
        HBox box = new HBox();
        box.getChildren().add(new Text(label + " "));
        Text txtValue = new Text(value);
        txtValue.setId(valueId);
        txtValue.setFill(Color.RED);
        box.getChildren().add(txtValue);
        return box;
    }
    private static final Pattern BAD_ONES = Pattern.compile("[\\.,: _()]");

    public static String normalizeName(String name) {
        if (name == null) {
            return null;
        }
        Matcher matcher = BAD_ONES.matcher(name);
        if (matcher.find()) {
            return matcher.replaceAll("_");
        }
        return name;
    }

    public static void setCustomFont(final Scene scene) {
        if (null == scene) {
            throw new NullPointerException("Null scene appears to be");
        }
        boolean success = true;
        try {
            final InputStream is = Utils.class.getResourceAsStream("font/arialw7.ttf");
            final URL url = ImageDuplicateExtractor.class.getResource("custom_font.css");

            if ((null != is) && (null != url)){
                Font.loadFont(is, 12);
                scene.getStylesheets().add(url.toExternalForm());
            } else {
                System.out.println("epic fail: is="+is+" url="+url);
                throw new NullPointerException("setCustomFont failed");
            }
        }catch(Throwable t){
            success = false;
        }
        finally {
            if (!success) {
                throw new RuntimeException("Font is not loaded");
            }
        }
    }

    public static class TextButton extends StackPane {

        public final String name;//to use AbstactApp in rendering tests
        private final static Color activeColor = Color.RED;
        private final static Color passiveColor = Color.LIGHTGREEN;
        private final Runnable textAction;

        public String getName() {
            return name;
        }

        public void setTextHandler(EventHandler<MouseEvent> handler) {
            this.setOnMousePressed(handler);
        }

        public TextButton(String name, final Runnable action) {
            this(name, action, passiveColor);
        }

        public TextButton(String name, final Runnable action, final Color color) {
            this.name = name;
            this.textAction = action;
            this.setId(name);
            Rectangle bg = new Rectangle(100, 20, color);
            bg.setArcHeight(5);
            bg.setArcHeight(5);
            getChildren().add(bg);
            final Text txt = new Text(name);
            getChildren().add(txt);
            bg.widthProperty().bind(new DoubleBinding() {
                {
                    bind(txt.boundsInLocalProperty());
                }

                @Override
                protected double computeValue() {
                    return txt.getBoundsInLocal().getWidth() + 10;
                }
            });

            EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
                public void handle(MouseEvent t) {
                    if (null != textAction) {
                        textAction.run();
                    }
                }
            };
            this.setOnMousePressed(handler);
        }

        public void setFillWithActiveColor() {
        }

        public void setFillWithPassiveColor() {
        }
    }

    public static boolean isSmokeTesting() {
        String annotations = System.getProperty("javatest.testset.annotations");
        if (annotations == null) {
            return false;
        } else {
            return annotations.contains("Smoke") && !annotations.contains("!Smoke");
        }
    }

    public static class LayoutSize {

        public double minWidth;
        public double minHeight;
        public double prefWidth;
        public double prefHeight;
        public double maxWidth;
        public double maxHeight;

        public LayoutSize(double min_width,
                double min_height,
                double pref_width,
                double pref_height,
                double max_width,
                double max_height) {
            minWidth = min_width;
            minHeight = min_height;
            prefWidth = pref_width;
            prefHeight = pref_height;
            maxWidth = max_width;
            maxHeight = max_height;
        }

        public void apply(Control control) {
            control.setMinWidth(minWidth);
            control.setMinHeight(minHeight);
            control.setPrefWidth(prefWidth);
            control.setPrefHeight(prefHeight);
            control.setMaxWidth(maxWidth);
            control.setMaxHeight(maxHeight);
        }

        public void apply(Region region) {
            region.setMinWidth(minWidth);
            region.setMinHeight(minHeight);
            region.setPrefWidth(prefWidth);
            region.setPrefHeight(prefHeight);
            region.setMaxWidth(maxWidth);
            region.setMaxHeight(maxHeight);
        }
    }

    public static String getRunEnvironmentInfo() {
        GraphicsPipeline pipeline = com.sun.prism.GraphicsPipeline.getPipeline();
        String prim_order = System.getProperty("prism.order");
        return " FX: " + (pipeline != null ? pipeline.getClass().getSimpleName() : "No Pipeline info")
                + " (prism.order:" + (prim_order != null ? prim_order : "No Prism Order info") + ")"
                + " " + VersionInfo.getRuntimeVersion()
                + " (build " + VersionInfo.getHudsonBuildNumber() + ")"
                + " Java: " + System.getProperty("java.runtime.version")
                + " Jvm: " + System.getProperty("java.vm.name")
                + " LFeel env/get: " + System.getProperty("javafx.userAgentStylesheetUrl"
                             + " / " + Application.getUserAgentStylesheet());
    }

    public static boolean isJ2D() {
        return com.sun.prism.GraphicsPipeline.getPipeline()
                .getClass().getName().endsWith("J2DPipeline")
                || com.sun.prism.GraphicsPipeline.getPipeline()
                .getClass().getName().endsWith("SWPipeline");
    }

    public static boolean isCaspian() {
        return "caspian".equalsIgnoreCase(System.getProperty("javafx.userAgentStylesheetUrl"));
    }

    public static void initializeAwt() {
        try {
            if (JemmyUtils.usingGlassRobot() || TestUtil.isEmbeddedGraphicsProfile()) {
                return;
            }

            if (isMacOS()) {
                JemmyUtils.runInOtherJVM(true);
            } else {
                java.awt.GraphicsEnvironment.isHeadless();
            }

            // old voodoo magic doesn't work on Mac
            // Toolkit.getDefaultToolkit();

            // voodoo magic reasons can be found here: RT-12529
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isMacOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("mac") >= 0) {
            return true;
        }
        return false;
    }

    public static boolean isLinux() {
        String os = System.getProperty("os.name").toLowerCase();
        return ((os.indexOf("linux") >= 0) || (os.indexOf("ubuntu") >= 0));
    }

    public static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("windows") >= 0) {
            return true;
        }
        return false;
    }

    public static boolean isWindows8() {
        String os = System.getProperty("os.name").toLowerCase();
        if (isWindows() && os.contains("8")) {
            return true;
        }
        return false;
    }

    public static boolean isGlassRobotAvailable() {
        return usingGlassRobot();
        //return isWindows();
    }

    public static void setTitleToStage(Stage stage, String appName) {
        if (stage != null) {
            stage.setTitle(appName + getRunEnvironmentInfo());
        }
    }

    public static void waitFor(Supplier <Boolean> event) {
        while (!event.get()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
