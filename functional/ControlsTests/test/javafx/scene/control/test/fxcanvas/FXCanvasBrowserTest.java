/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
 * questions.
 */
package javafx.scene.control.test.fxcanvas;

import client.test.Keywords;
import client.test.ScreenshotCheck;
import client.test.Smoke;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.test.ControlsTestBase;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.JemmyProperties;
import test.javaclient.shared.CanvasRunner;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(CanvasRunner.class)
public class FXCanvasBrowserTest extends ControlsTestBase {

    static final int LOADING_DELAY = 10000;
    static final int SEQUENTIAL_REFRESH_DELAY = 2000;

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.setProperty("javafx.swtinteroperability", "true");
        FXCanvasBrowserApp.main(null);
        //BrowserApp.main(null);
        JemmyProperties.setCurrentDispatchingModel(JemmyProperties.ROBOT_MODEL_MASK);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    @Keywords(keywords = "swt")
    public void browserTest() throws InterruptedException, Throwable {
        Wrap<? extends Scene> scene = Root.ROOT.lookup().wrap();
        Parent<Node> parent = scene.as(Parent.class, Node.class);
        Wrap refresh = parent.lookup(new ByID<Node>(FXCanvasBrowserApp.BUTTON_ID)).wrap();
        Wrap content = parent.lookup(new ByID<Node>(FXCanvasBrowserApp.CONTENT_ID)).wrap();
        for (int i = 0; i < 3; i++) { // WebEngine bug
            refresh.mouse().click();
            Thread.sleep(SEQUENTIAL_REFRESH_DELAY);
        }

        final Wrap<? extends Label> label = scene.as(Parent.class, Node.class).lookup(Label.class, new ByID(FXCanvasBrowserApp.SUCCESS_LABEL_ID)).wrap();
        label.waitState(new State() {

            public Object reached() {
                String text = new GetAction<String>() {

                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(label.getControl().getText());
                    }
                }.dispatch(Root.ROOT.getEnvironment());
                if (FXCanvasBrowserApp.SUCCESS_MESSAGE.equals(text)) {
                    return true;
                } else {
                    return null;
                }
            }
        });

        Thread.sleep(LOADING_DELAY);
        Rectangle content_bounds = content.getScreenBounds();
        ScreenshotUtils.checkScreenshot("FXCanvasBrowserTest", content, new Rectangle((int) content_bounds.getWidth() - 20, (int) content_bounds.getHeight() - 20));
        throwScreenshotError();
    }
}
