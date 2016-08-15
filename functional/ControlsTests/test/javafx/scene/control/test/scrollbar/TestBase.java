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
package javafx.scene.control.test.scrollbar;

import javafx.scene.control.ScrollBar;
import org.junit.Before;
import javafx.scene.Scene;
import javafx.scene.control.test.util.UtilTestFunctions;
import javafx.scene.Node;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Parent;
import org.junit.After;
import org.junit.BeforeClass;
import static javafx.scene.control.test.scrollbar.ScrollBarApp.*;
import org.jemmy.Point;
import org.jemmy.env.Environment;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import static test.javaclient.shared.TestUtil.isEmbedded;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class TestBase extends UtilTestFunctions {

    static Wrap<? extends Scene> scene;
    static Wrap<? extends ScrollBar> testedControl;

    @BeforeClass
    public static void setUpClass() throws Exception {
        ScrollBarApp.main(null);
    }

    @Before
    public void setUp() {
        initWrappers();
        Environment.getEnvironment().setTimeout("wait.state", isEmbedded() ? 60000 : 2000);
        Environment.getEnvironment().setTimeout("wait.control", isEmbedded() ? 60000 : 1000);
        scene.mouse().move(new Point(0, 0));
    }

    @After
    public void tearDown() {
        resetScene();
        currentSettingOption = SettingOption.PROGRAM;
    }

    protected void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);

        testedControl = parent.lookup(ScrollBar.class, new ByID<ScrollBar>(TESTED_SCROLLBAR_ID)).wrap();
    }

    //              WORKING WITH LISTENING TEXT FIELDS
    protected void resetScene() {
        clickButtonForTestPurpose(RESET_BUTTON_ID);
    }

    //                       UTIL SECTION
    static protected enum Properties {

        prefWidth, prefHeight, value, max, min, blockIncrement, unitIncrement, orientation, visibleAmount, focusTraversable
    };
}
