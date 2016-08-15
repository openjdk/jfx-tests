/*
 * Copyright (c) 2009-2013, Oracle and/or its affiliates. All rights reserved.
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
package test.scenegraph.scene;

import com.sun.glass.ui.Robot;
import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jemmy.input.glass.GlassInputFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.Utils;
import test.scenegraph.app.SimpleApp;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public class SceneOnOtherThreadTest extends TestBase {

    @BeforeClass
    public static void init() {
        Utils.launch(SimpleApp.class, null);
    }

    @Test(timeout = 2000)
    public void testSceneCreationOnNonFxThread() throws InterruptedException {
        Stage stage = SimpleApp.getStage();
        int centerX = (int)(stage.getX() + stage.getWidth() / 2);
        int centerY = (int)(stage.getY() + stage.getHeight() / 2);
        Control area = new TextField("");
        area.setStyle("-fx-background-color: #ff0000;");
        area.setMinWidth(stage.getWidth());
        area.setMinHeight(stage.getHeight());
        Scene newScene = new Scene(area);
        Platform.runLater(() -> {
            stage.setScene(newScene);
        });
        Utils.waitFor(() -> stage.getScene() == newScene);
        Robot glassRobot  = GlassInputFactory.getRobot();
        Utils.waitFor(() -> {
            AtomicInteger color = new AtomicInteger();
            Platform.runLater(() -> {
                color.set(glassRobot.getPixelColor(centerX, centerY));
            });
            while (color.get() == 0) {}
            Color c = new Color(color.get(), true);
            return Color.RED.equals(c);
        });
    }

}
