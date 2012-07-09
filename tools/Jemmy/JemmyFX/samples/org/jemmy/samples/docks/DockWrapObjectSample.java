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
package org.jemmy.samples.docks;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.interfaces.Parent;
import org.jemmy.samples.SampleBase;
import org.jemmy.samples.buttons.ButtonsApp;
import org.junit.*;

/**
 * This sample shows what is dock, wrap and the wrapped object.
 * @author shura
 */
public class DockWrapObjectSample extends SampleBase {
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        startApp(ButtonsApp.class);
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

    /**
     * <code>*Dock</code> classes are the true API classes. Docks should be used
     * whenever possible. All straightforward operations should be available 
     * through docks. If it is not so, please file a feature request.<br/>
     * Dock classes source is generated. They have many different methods which
     * are all convenience methods. Such methods as lookup constructors, 
     * <code>as*()</code> methods to perform operations and <code>get*()</code>
     * method to get properties. Please consult other samples to see how to use 
     * docks.
     */
    @Test
    public void dock() {
        //Docks have ...
        //lookup constructors
        LabeledDock button = new LabeledDock(new SceneDock().asParent(), Button.class);
        //as* methods
        Parent<Node> parent = button.asParent();
        //get* methods
        //please make sure to use these rather than direct calls to Java FX node
        //method, becuase this calls the appropriate method on the event queue
        double opacity = button.getOpacity();
        //you can get a wrap out of it
        //see next test method on how to deal with wraps
        Wrap<? extends Labeled> wrap = button.wrap();
        //or a control itself
        Labeled theButtonItself = button.control();
    }
    
    /**
     * Wraps are SPI classes which are not supposed to be used directly. 
     * Other than some public constants and a few methods of 
     * <code>org.jemmy.control.Wrap</code>. Wraps
     * "wrap" controls allowing to use them for testing purposes. Wraps
     * contain the code which allows to access the underlying control. Wraps
     * provide interface to the control through "control interfaces".
     */
    @Test
    public void wrap() {
        //you can lookup through control interfaces
        //wraps provide as() method
        //Parent is a control interface
        Wrap<? extends Scene> scene = Root.ROOT.lookup().wrap();
        Wrap<? extends Labeled> button = scene.as(Parent.class, Node.class).lookup(Button.class).wrap();
        //they also have getProperty(String) and waitProperty(String, Object)
        button.waitProperty("getOpacity", 1.);
        //and you can get the object out of it
        Labeled theButtonItself = button.getControl();
        //notice though that directly accessing the object is not a good idea.
        //see next test method on how to deal with the underlying object
    }
    
    /**
     * It is always dangerous to directly access objects within Java FX 
     * hierarchy. All the access should be done through event queue. If done
     * otherwise results are unpredictable at the best.
     */
    @Test
    public void object() {
        final LabeledDock button = new LabeledDock(new SceneDock().asParent(), Button.class);
        double opacity = new GetAction<Double>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(button.control().getOpacity());
            }
        }.dispatch(button.wrap().getEnvironment());
    }
}
