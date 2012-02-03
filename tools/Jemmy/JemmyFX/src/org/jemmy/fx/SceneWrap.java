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
package org.jemmy.fx;

import java.awt.Point;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Window;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.awt.AWT;
import org.jemmy.awt.Showing;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Wrap;
import org.jemmy.dock.DefaultParent;
import org.jemmy.dock.DefaultWrapper;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.env.Environment;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.TypeControlInterface;
import org.jemmy.lookup.AbstractParent;
import org.jemmy.lookup.Any;
import org.jemmy.lookup.HierarchyLookup;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

/**
 *
 * @author shura
 */
@ControlType(Scene.class)
@ControlInterfaces(value = Parent.class, encapsulates = Node.class)
public class SceneWrap<T extends Scene> extends Wrap<Scene> {

    @DefaultWrapper
    public static <TP extends Scene> Wrap<? extends TP> wrap(Environment env, Class<TP> type, TP control) {
        Wrap<? extends TP> res = Root.ROOT.getSceneWrapper().wrap(type, control);
        res.setEnvironment(env);
        return res;
    }

    @ObjectLookup("title and comparison policy")
    public static <B extends Scene> LookupCriteria<B> titleLookup(Class<B> tp, String title, StringComparePolicy policy) {
        return new ByTitleSceneLookup<B>(title, policy);
    }

    @ObjectLookup("")
    public static <B extends Scene> LookupCriteria<B> anyLookup(Class<B> tp) {
        return new Any<B>();
    }

    @DefaultParent("all scenes")
    public static <CONTROL extends Scene> Parent<? super Scene> getRoot(Class<CONTROL> controlType) {
        return Root.ROOT;
    }
    private Parent parent = null;

    public SceneWrap(Environment env, Scene node) {
        super(env, node);
    }

    @Override
    public Rectangle getScreenBounds() {
        return getScreenBounds(getEnvironment(), getControl());
    }

    public static Rectangle getScreenBounds(final Environment env, final Scene scene) {
        GetAction<Rectangle> bounds = new GetAction<Rectangle>() {

            @Override
            public void run(Object... parameters) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
                Rectangle sceneBounds = getSceneBounds(env, scene);
                Point stageCoordinates = null;
                // TODO: stub
                String prop = System.getProperty("javafx.swinginteroperability");
                if (prop != null && prop.compareToIgnoreCase("true") == 0) {
                    JFXPanel panel = AWT.getAWT().lookup(JFXPanel.class, new Showing<JFXPanel>() {

                        @Override
                        public boolean check(JFXPanel control) {
                            return super.check(control) && (control.getScene() == scene);
                        }
                    }).wrap().getControl();
                    sceneBounds.translate((int) panel.getLocationOnScreen().getX(), (int) panel.getLocationOnScreen().getY());
                } else {
                    Window window = scene.getWindow();
                    /*
                     * Field host_field =
                     * window.getClass().getDeclaredField("host");
                     * host_field.setAccessible(true); Object host =
                     * host_field.get(window); Field panel_field =
                     * host.getClass().getDeclaredField("this$0");
                     * panel_field.setAccessible(true); JFXPanel panel =
                     * (JFXPanel)panel_field.get(host);
                     * sceneBounds.translate((int)panel.getLocationOnScreen().getX(), (int)panel.getLocationOnScreen().getY());
                     */
                    // TODO: RT-12793
                    sceneBounds.translate((int) window.getX(), (int) window.getY());
                }
                setResult(sceneBounds);
            }
        };
        env.getExecutor().execute(env, true, bounds);
        return bounds.getResult();
    }

    /**
     * Returns bounds of the Scene relative to Stage containing it
     */
    private static Rectangle getSceneBounds(Environment env, final Scene scene) {
        return new Rectangle(scene.getX(), scene.getY(),
                scene.getWidth(), scene.getHeight());
    }

    /**
     * Returns bounds of the Stage relative to screen
     */
    private static Rectangle getScreenBounds(Environment env, final Window window) {
        return new Rectangle(window.getX(), window.getY(),
                window.getWidth(), window.getHeight());
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Parent.class.isAssignableFrom(interfaceClass) && Node.class.equals(type)) {
            return true;
        }
        return super.is(interfaceClass, type);
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Parent.class.isAssignableFrom(interfaceClass) && Node.class.equals(type)) {
            if (parent == null) {
                parent = new SceneParentNode();
            }
            return (INTERFACE) parent;
        }
        return super.as(interfaceClass, type);
    }

    private class SceneParentNode extends AbstractParent<Node> {

        @Override
        public <ST extends Node> Lookup<ST> lookup(Class<ST> controlClass, LookupCriteria<ST> criteria) {
            javafx.scene.Parent parent = new GetAction<javafx.scene.Parent>() {

                public void run(Object... parameters) throws Exception {
                    setResult(getControl().getRoot());
                }
            }.dispatch(getEnvironment());
            return new HierarchyLookup<ST>(getEnvironment(), new NodeHierarchy(getControl(), getEnvironment()),
                    new NodeWrapper(getEnvironment()), controlClass, criteria);
        }

        @Override
        public Lookup<Node> lookup(LookupCriteria<Node> criteria) {
            return lookup(Node.class, criteria);
        }

        @Override
        public Class<Node> getType() {
            return Node.class;
        }
    }
}
