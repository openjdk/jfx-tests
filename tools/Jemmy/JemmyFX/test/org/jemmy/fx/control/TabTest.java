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
 * questions.
 */
package org.jemmy.fx.control;

import javafx.scene.control.Tab;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.SceneDock;
import org.junit.*;

/**
 *
 * @author shura
 */
public class TabTest {

    public TabTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(TabApp.class);
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

    @Test
    public void select() throws InterruptedException {
        new TextInputControlDock(new SceneDock().asParent()).wrap().mouse().click(3);
        final TabPaneDock tpd = new TabPaneDock(new SceneDock().asParent());
        tpd.wrap().waitState(() -> tpd.asSelectable().getStates().size(), 6);
        for (Tab t : tpd.asSelectable().getStates()) {
            System.out.println("Selecting " + t.getText());
            tpd.asSelectable().selector().select(t);
        }
        for (int i = tpd.asSelectable().getStates().size() - 1; i>=0; i-=2) {
            tpd.asSelectable().selector().select(tpd.asSelectable().getStates().get(i));
        }
    }

    @Test
    public void tabDock() {
        new TabDock(new TabPaneDock(new SceneDock().asParent()).asTabParent(), 3).asCell().select();
    }
}
