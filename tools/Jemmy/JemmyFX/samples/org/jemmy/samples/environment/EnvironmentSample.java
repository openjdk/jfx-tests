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

import javafx.scene.layout.GridPane;
import org.jemmy.env.Environment;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.samples.SampleBase;
import org.jemmy.samples.lookup.LookupApp;
import org.jemmy.samples.lookup.LookupSample;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * How to use Jemmy environment.
 *
 * @author shura
 */
public class EnvironmentSample extends SampleBase {

    private static SceneDock scene;
    private static NodeDock parent;
    private static NodeDock node1;
    private static NodeDock node2;

    @BeforeClass
    public static void runApp() throws InterruptedException {
        startApp(LookupApp.class);
        scene = new SceneDock();
        parent = new NodeDock(scene.asParent(), GridPane.class);
        node1 = new NodeDock(parent.asParent(), "lbl_00");
        node2 = new NodeDock(scene.asParent(), "lbl_01");
    }

    /**
     * All environment is "inherited" from container to children.
     */
    @Test
    public void inherit() {
        scene.wrap().getEnvironment().setProperty("test.class", this.getClass());
        assertEquals(this.getClass(), node1.wrap().getEnvironment().getProperty("test.class"));
    }

    /**
     * All environment is "inherited" from container to children but only if
     * children was looked inside the parent.
     */
    @Test
    public void inheritFromParent() {
        parent.wrap().getEnvironment().setProperty("test.class.1", this.getClass());
        assertNull(node2.wrap().getEnvironment().getProperty("test.class.1"));
    }

    /**
     * All environment is "inherited" from container to children and could be
     * overriden at any level.
     */
    @Test
    public void inheritOverride() {
        scene.wrap().getEnvironment().setProperty("test.class.2", this.getClass());
        parent.wrap().getEnvironment().setProperty("test.class.2", LookupSample.class);
        assertEquals(LookupSample.class, node1.wrap().getEnvironment().getProperty("test.class.2"));
    }

    /**
     * Environment hierarchy grows from
     * <code>Environment.getEnvironment()</code>.
     */
    @Test
    public void environment() {
        assertNull(Environment.getEnvironment().getParentEnvironment());
        assertNotNull(Environment.getEnvironment().getOutput());
        Environment.getEnvironment().setProperty("test", "1");
        assertEquals(node1.wrap().getEnvironment().getProperty("test"), "1");
    }

    /**
     * All FX specific environment is set in
     * <code>Root.ROOT.getEnvironment()</code>.
     */
    @Test
    public void environmentRoot() {
        assertTrue(Root.ROOT.getEnvironment().getExecutor() instanceof org.jemmy.fx.QueueExecutor);
    }

    /**
     * Property types could be used for type control.
     */
    @Test
    public void types() {
        scene.wrap().getEnvironment().setProperty(String.class, this.getClass().getName());
        String res = scene.wrap().getEnvironment().getProperty(String.class);
        assertEquals(res, this.getClass().getName());
    }
}
