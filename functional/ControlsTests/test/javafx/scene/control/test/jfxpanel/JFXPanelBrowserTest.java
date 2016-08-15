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
package javafx.scene.control.test.jfxpanel;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.test.ControlsTestBase;
import org.jemmy.Rectangle;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.JemmyProperties;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public class JFXPanelBrowserTest extends ControlsTestBase {

    static final int LOADING_DELAY = 7000;
    static final int SEQUENTIAL_REFRESH_DELAY = 2000;

    private static String proxyHost;
    private static String proxyPort;

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.setProperty("javafx.swinginteroperability", "true");
        JFXPanelBrowserApp.main(null);

        //Save proxy settings
        proxyHost = System.getProperty("http.proxyHost");
        proxyPort = System.getProperty("http.proxyPort");

        System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
        System.setProperty("http.proxyPort", "80");
        //BrowserApp.main(null);
        JemmyProperties.setCurrentDispatchingModel(JemmyProperties.ROBOT_MODEL_MASK);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        //Restore proxy settinigs
        System.setProperty("http.proxyHost", null == proxyHost ? "" : proxyHost);
        System.setProperty("http.proxyPort", null == proxyPort ? "" : proxyPort);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

//    @ScreenshotCheck
//    @Smoke
    @Test(timeout = 300000)
    public void browserTest() throws InterruptedException {
        Wrap<? extends Scene> scene = Root.ROOT.lookup().wrap();
        Parent<Node> parent = scene.as(Parent.class, Node.class);
        Wrap refresh = parent.lookup(new ByID<Node>(JFXPanelBrowserApp.BUTTON_ID)).wrap();
        Wrap content = parent.lookup(new ByID<Node>(JFXPanelBrowserApp.CONTENT_ID)).wrap();
        for (int i = 0; i < 3; i++) { // WebEngine bug
            refresh.mouse().click();
            Thread.sleep(SEQUENTIAL_REFRESH_DELAY);
        }
        Thread.sleep(LOADING_DELAY);
        Rectangle content_bounds = content.getScreenBounds();
        ScreenshotUtils.checkScreenshot("BrowserTest", content, new Rectangle((int) content_bounds.getWidth() - 25, (int) content_bounds.getHeight() - 25));
    }
}