/*
 * Copyright (c) 2009, 2023, Oracle and/or its affiliates. All rights reserved.
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

import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import org.jemmy.action.Action;
import org.jemmy.fx.Root;
import org.jemmy.input.glass.GlassInputFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.Utils;
import test.scenegraph.app.SimpleApp;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public class SceneOnOtherThreadTest extends TestBase {

    @BeforeClass
    public static void init() {
        Utils.launch(SimpleApp.class, null);
    }

    @Test(timeout = 10000)
    public void testSceneCreationOnNonFxThread() throws InterruptedException {
        Stage stage = SimpleApp.getStage();
        int centerX = (int)(stage.getX() + stage.getWidth() / 2);
        int centerY = (int)(stage.getY() + stage.getHeight() / 2);
        Control area = new TextField("");
        area.setStyle("-fx-background-color: #ff0000;");
        area.setMinWidth(stage.getWidth());
        area.setMinHeight(stage.getHeight());
        Scene newScene = new Scene(area);
        var env = Root.ROOT.getEnvironment();
        var executor = env.getExecutor();
        executor.execute(env, true,
                new Action() {
                    @Override
                    public void run(Object... objects) throws Exception {
                        stage.setScene(newScene);
                    }
                });
        Robot glassRobot  = GlassInputFactory.getRobot();
        Root.ROOT.lookup().wrap().waitState(() -> {
            var color = new AtomicReference<Color>();
            executor.execute(env, true,
                    new Action() {
                        @Override
                        public void run(Object... objects) throws Exception {
                            color.set(glassRobot.getPixelColor(centerX, centerY));
                        }
                    });
            return color.get();
        }, Color.RED);
    }

}
