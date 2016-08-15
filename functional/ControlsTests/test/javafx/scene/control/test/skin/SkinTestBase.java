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
package javafx.scene.control.test.skin;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.test.util.UtilTestFunctions;
import javafx.scrollEvent.ScrollEventApp;
import org.jemmy.control.Wrap;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import static javafx.scene.control.test.skin.ControlsSkinsApp.*;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;

/**
 * @author Alexander Kirov
 *
 * See https://javafx-jira.kenai.com/browse/RT-33520
 */
@RunWith(FilteredTestRunner.class)
public class SkinTestBase
        extends UtilTestFunctions {

    Wrap<? extends Scene> scene;
    Wrap<? extends ChoiceBox> nodeChooser;
    Wrap<? extends Node> targetControl;

    @BeforeClass
    public static void setUpClass() throws Exception {
        ControlsSkinsApp.main(null);
    }

    @Before
    public void setUp() {
        initWrappers();
    }

    protected void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);

        nodeChooser = parent.lookup(ChoiceBox.class, new ByID<ChoiceBox>(NODE_CHOOSER_ID)).wrap();
    }

    protected void replaceSkin() {
        clickButtonForTestPurpose(REPLACE_SKIN_BUTTON_ID);
    }
}
