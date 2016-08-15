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
package org.jemmy.samples.environment;

import org.jemmy.control.Wrap;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.interfaces.Mouse;
import org.jemmy.samples.SampleBase;
import org.jemmy.samples.lookup.LookupApp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * How to use Jemmy timeouts. This sample only talks about timeouts in general.
 * Please consult component specific samples to learn component specific timeouts
 * @author shura
 */
public class TimeoutsSample extends SampleBase {
    private static SceneDock scene;
    private static NodeDock node;

    @BeforeClass
    public static void runApp() throws InterruptedException {
        startApp(LookupApp.class);
        scene = new SceneDock();
        node = new NodeDock(scene.asParent(), "lbl_01");
    }

    /**
     * Timeouts are handled through environment.
     */
    @Test
    public void environment() {
        scene.wrap().getEnvironment().setTimeout("test.timeout", 999);
        assertEquals((long)999, node.wrap().getEnvironment().getTimeout("test.timeout").getValue());
    }

    /**
     * Timeouts are defined wherever appropriate.
     */
    @Test
    public void mouseClick() {
        assertTrue(node.wrap().getEnvironment().getTimeout(Mouse.CLICK).getValue() > 0);
    }

    /**
     * Timeouts are used for configurable sleeps.
     */
    @Test
    public void mouseClickConfigure() {
        //this will cause a 10 seconds delay between mouse press and mouse release
        Root.ROOT.getEnvironment().setTimeout(Mouse.CLICK, 10000);
        //this will cause a waitState() methods to wait maximum for a minute
        Root.ROOT.getEnvironment().setTimeout(Wrap.WAIT_STATE_TIMEOUT, 60000);
    }

}
