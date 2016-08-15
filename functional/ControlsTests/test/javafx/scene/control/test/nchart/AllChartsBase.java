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
package javafx.scene.control.test.nchart;

import javafx.scene.Node;
import javafx.scene.chart.Chart;
import static javafx.scene.control.test.chart.apps.ChartIDsInterface.*;
import org.jemmy.Point;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.junit.After;
import org.junit.Before;
import static test.javaclient.shared.TestUtil.isEmbedded;

/**
 * @author Alexander Kirov
 *
 * This class is a part of experimental tests on Charts, which are aimed to
 * replace tests with huge amount of images, and which (probably) could fail at
 * any moment and for unknown reason. They could be fixed or disabled.
 */
public abstract class AllChartsBase extends ChartTestCommon {

    protected boolean resetHardByDefault = true;//switcher of hard and soft reset mode.
    protected boolean doNextResetHard = resetHardByDefault;
    protected static final boolean useInvariantChecking = true;

    @Before
    @Override
    public void before2() {
        initWrappers();
        Environment.getEnvironment().setTimeout("wait.state", isEmbedded() ? 60000 : 2000);
        Environment.getEnvironment().setTimeout("wait.control", isEmbedded() ? 60000 : 1000);
        scene.mouse().move(new Point(0, 0));
        super.before2();
    }

    @After
    public void tearDown() {
        if (doNextResetHard) {
            resetSceneHard();
        } else {
            resetSceneSoft();
        }

        doNextResetHard = resetHardByDefault;
        currentSettingOption = SettingOption.PROGRAM;
    }

    protected void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        testedControl = parent.lookup(Chart.class, new ByID<Chart>(TESTED_CHART_ID)).wrap();
        controlAsParent = testedControl.as(Parent.class, Node.class);
    }

    protected void resetSceneHard() {
        clickButtonForTestPurpose(HARD_RESET_BUTTON_ID);
    }

    protected void resetSceneSoft() {
        clickButtonForTestPurpose(SOFT_RESET_BUTTON_ID);
    }
}