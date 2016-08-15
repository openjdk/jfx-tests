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

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jemmy.JemmyException;
import org.jemmy.Rectangle;
import org.jemmy.action.Action;
import org.jemmy.action.FutureAction;
import org.jemmy.control.*;
import org.jemmy.dock.DefaultParent;
import org.jemmy.dock.DefaultWrapper;
import org.jemmy.dock.DockInfo;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.env.Environment;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Show;
import org.jemmy.interfaces.Showable;
import org.jemmy.lookup.ControlList;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.timing.State;

/**
 * Support for Java FX scene. A scene could be found within top-level hierarchy
 * represented by <code>Root</code> class, using standard lookup approaches such
 * as by a title, or by implementing a custom lookup criteria. Besides that a
 * scene could be brought to front.<br>
 * You could find more on lookup in a <a href="../samples/lookup/LookupSample.java">lookup sample</a>
 *
 * @param <T>
 * @author shura
 * @see Root
 * @see SceneDock
 */
@ControlType(Scene.class)
@ControlInterfaces(value = {Parent.class, Showable.class}, encapsulates = {Node.class})
@DockInfo(generateSubtypeLookups = true)
public class SceneWrap<T extends Scene> extends Wrap<Scene> {

    /**
     * Should a scene be already found, this creates a <code>Wrap</code> around it.
     *
     * @param <TP>
     * @param env     an Environment to assign to the wrap.
     * @param type    in case a class implementing scene is known
     * @param control the scene itself
     * @return
     */
    @DefaultWrapper
    public static <TP extends Scene> Wrap<? extends TP> wrap(Environment env, Class<TP> type, TP control) {
        Wrap<? extends TP> res = Root.ROOT.getSceneWrapper().wrap(type, control);
        res.setEnvironment(env);
        return res;
    }

    /**
     * Creates a lookup criteria which compare scene title with a text sample according
     * to comparison rules.
     *
     * @param <B>    in case a class implementing scene is known
     * @param tp     in case a class implementing scene is known
     * @param title  expected title or a portion of it
     * @param policy how to compare
     * @return
     */
    @ObjectLookup("title and comparison policy")
    public static <B extends Scene> LookupCriteria<B> titleLookup(Class<B> tp, String title, StringComparePolicy policy) {
        return new ByTitleSceneLookup<>(title, policy);
    }

    /**
     * This returns a parent which would be used for scene lookup when no other
     * parent specified, which is, basically, always.
     *
     * @param <CONTROL>   required by contract
     * @param controlType required by contract
     * @return a <code>ControlList</code> build around <code>Window.imple_getWindows()</code> - Root#ROOT
     * @see Root#ROOT
     * @see ControlList
     */
    @DefaultParent("all scenes")
    public static <CONTROL extends Scene> Parent<? super Scene> getRoot(Class<CONTROL> controlType) {
        return Root.ROOT;
    }

    private Parent parent = null;
    private Showable showable = null;

    /**
     * Wraps a scene.
     *
     * @param env  an environment specific for this wrap.
     * @param node the scene.
     */
    public SceneWrap(Environment env, Scene node) {
        super(env, node);
    }

    @Override
    public Rectangle getScreenBounds() {
        return getScreenBounds(getEnvironment(), getControl());
    }

    /**
     * An utility method to get scene bounds.
     *
     * @param env   Used to post code through the FX event queue.
     * @param scene the scene
     * @return Absolute screen coordinates
     */
    public static Rectangle getScreenBounds(final Environment env, final Scene scene) {
        return new FutureAction<>(env, () -> {
            Rectangle sceneBounds = getSceneBounds(env, scene);
            Window window = scene.getWindow();
            if (Double.isNaN(window.getX())) {
                throw new JemmyException("Unable to obtain containers's window coordinates in " + SceneWrap.class.getName());
            }
            sceneBounds.translate((int) window.getX(), (int) window.getY());
            return sceneBounds;
        }).get();
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

    @Property("isFocused")
    public boolean isFocused() {
        return isFocused(getControl(), getEnvironment());
    }

    @Property("isShowing")
    public boolean isShowing() {
        return new FutureAction<>(getEnvironment(), () -> getControl().getWindow().isShowing()).get();
    }

    /**
     * Turns the scene into a parent for nodes for further lookup.
     *
     * @return an implementation of <code>Parent&lt;Node&gt;</code>
     */
    @As(Node.class)
    public Parent<Node> asParent() {
        if (parent == null) {
            parent = new NodeParentImpl(new SceneNodeHierarchy(getControl(), getEnvironment()), getEnvironment());
        }
        return parent;
    }

    /**
     * Turns a scene into a Showable. The wrapped scene will be shown using API
     * calls.
     *
     * @return
     */
    @As
    public Showable asShowable() {
        if (showable == null) {
            showable = new SceneShowable();
        }
        return showable;
    }

    static void show(final Environment env, final Scene scene) {
        if (isStageInstance(scene, env)) {
            env.getExecutor().execute(env, true, new Action() {
                @Override
                public void run(Object... os) throws Exception {
                    Window stage = scene.getWindow();
                    if (!((Stage) stage).isFocused()) {
                        ((Stage) stage).toFront();
                    }
                }
            });
            env.getWaiter(WAIT_STATE_TIMEOUT).ensureValue(true, new State<Boolean>() {
                public Boolean reached() {
                    return isFocused(scene, env);
                }
            });
        }
    }

    private static boolean isFocused(final Scene scene, Environment env) {
        return new FutureAction<>(env, () -> scene.getWindow().isFocused()).get();
    }

    private static boolean isStageInstance(final Scene scene, Environment env) {
        return new FutureAction<>(env, () -> scene.getWindow() instanceof Stage).get();
    }

    private class SceneShowable implements Showable, Show {

        public Show shower() {
            return this;
        }

        public void show() {
            SceneWrap.show(getEnvironment(), getControl());
        }
    }
}
