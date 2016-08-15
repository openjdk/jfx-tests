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

import java.io.IOException;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javax.xml.parsers.ParserConfigurationException;
import org.jemmy.JemmyException;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.FutureAction;
import org.jemmy.action.GetAction;
import org.jemmy.control.*;
import org.jemmy.dock.DefaultWrapper;
import org.jemmy.dock.DockInfo;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.env.Environment;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.LookupCriteria;
import org.xml.sax.SAXException;

/**
 * Test support for JavaFX node. For simplicity, there is no special wrap class
 * around <code>Parent</code> - any node could be treated as a parent for other
 * nodes. If a node is not a <code>Parent</code> the hierarchy will be
 * considered empty.<br/> Check <a href="../samples/lookup">lookup samples</a>
 * and <a href="../samples/input">input samples</a> to get a feeling of what
 * could be done with on this generic level.<br/> All properties common to all
 * nodes are defined on this level.
 *
 * @param <T>
 * @author shura, mrkam
 * @see NodeDock
 */
@ControlType(Node.class)
@MethodProperties({"getBaselineOffset", "getBoundsInLocal", "getBoundsInParent",
    "getCacheHint", "getClip", "getContentBias", "getCursor", "getEffect",
    "getId", "getLayoutBounds", "getLayoutX", "getLayoutY", "getOpacity",
    "getParent", "getProperties", "getRotate", "getRotationAxis", "getScaleX",
    "getScaleY", "getScaleZ", "getStyle", "getStyleClass", "getTransforms",
    "getTranslateX", "getTranslateY", "getTranslateZ", "getUserData",
    "hasProperties", "isCache", "isDisable", "isDisabled",
    FXClickFocus.IS_FOCUSED_PROP, "isHover", "isManaged",
    "isMouseTransparent", "isPickOnBounds", "isPressed", "isResizable",
    "isVisible"})
@ControlInterfaces(value = {Parent.class, Showable.class}, encapsulates = Node.class)
@DockInfo(generateSubtypeLookups = true)
public class NodeWrap<T extends Node> extends Wrap<T> implements Focusable {

    public static final Wrapper WRAPPER;

    static {
        try {
            WRAPPER = new JemmySupportWrapper(NodeWrap.class.getClassLoader(), "support.xml", Root.ROOT.getEnvironment());
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            throw new JemmyException("Unable to load support information", ex);
        }
    }

    /**
     * The node's scene.
     */
    protected Scene scene;
    private Parent parent = null;
    private Mouse mouse = null;
    private Focus focus;
    private Showable show = null;

    /**
     * "Wraps" a node should a wrap be needed.
     *
     * @param <TP>
     * @param env an Environment to assign to the wrap.
     * @param type in case a class implementing scene is known
     * @param control the scene itself
     * @return
     * @see NodeDock#NodeDock(org.jemmy.env.Environment, javafx.scene.Node)
     */
    @DefaultWrapper
    public static <TP extends Node> Wrap<? extends TP> wrap(Environment env, Class<TP> type, TP control) {
        Wrap<? extends TP> res = new WrapperDelegate(new WrapperDelegate(NodeWrap.WRAPPER, env), env).wrap(type, control);
        res.setEnvironment(env);
        return res;
    }

    /**
     * Constructs lookup criteria by an id value. By-id lookup is the most basic
     * and the most reliable approach.
     *
     * @param <B>
     * @param tp Node type
     * @param id expected node id
     * @return
     * @see NodeDock#NodeDock(org.jemmy.interfaces.Parent, java.lang.String)
     * @see ByID
     */
    @ObjectLookup("id")
    public static <B extends Node> LookupCriteria<B> idLookup(Class<B> tp, String id) {
        return new ByID<B>(id);
    }

    private NodeWrap(Environment env, Scene scene, T node) {
        super(env, node);
        this.scene = scene;
        focus = new FXClickFocus(this);
    }

    /**
     * Creates the wrap.
     *
     * @param env
     * @param node
     */
    public NodeWrap(Environment env, T node) {
        this(env, node.getScene(), node);
    }

    /**
     * Gets scene of the node. Every node has a scene.
     *
     * @return
     */
    @Property("scene")
    public Scene getScene() {
        return scene;
    }

    /**
     * An utility method to get scene bounds.
     *
     * @param env Used to post code through the FX event queue.
     * @param nd The node
     * @return Absolute screen coordinates
     */
    public static Rectangle getScreenBounds(final Environment env, final Node nd) {
        GetAction<Rectangle> bounds = new GetAction<Rectangle>() {

            @Override
            public void run(Object... parameters) {
                Bounds rect = nd.localToScreen(nd.getLayoutBounds());
                Rectangle res = new Rectangle(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
                setResult(res);
            }
        };
        env.getExecutor().execute(env, true, bounds);
        return bounds.getResult();
    }

    @Override
    public Rectangle getScreenBounds() {
        return getScreenBounds(getEnvironment(), getControl());
    }

    /**
     * This makes possible to search for other nodes within another node's
     * hierarchy.
     *
     * @return node as a parent
     */
    @As(Node.class)
    public Parent<Node> asParent() {
        if (parent == null) {
            parent = new NodeParentImpl(this);
        }
        return parent;
    }

    @Override
    public Mouse mouse() {
        if (mouse == null) {
            mouse = super.mouse();
            if (!Root.isPropertyTrue(getEnvironment(), Root.USE_NATIVE_COORDINATES)) {
                mouse = new RelativeMouse(super.mouse(), this);
            }
        }
        return mouse;
    }

    public Focus focuser() {
        return focus;
    }

    /**
     * Defines whether a node is completely within another node.
     *
     * @param parent
     * @param cell
     * @param env
     * @return
     */
    public static boolean isInside(Node parent, Node cell, Environment env) {
        return isInBounds(parent, cell, env, false) && isInBounds(parent, cell, env, true);
    }

    /**
     * Defines whether a node is completely within vertical or horizontal bounds
     * of another node.
     *
     * @param parent
     * @param cell
     * @param env
     * @return
     */
    public static boolean isInBounds(Node parent, Node cell, Environment env, boolean isVertical) {
        if (cell != null) {
            Rectangle bounds = NodeWrap.getScreenBounds(env, cell);
            Rectangle viewBounds = NodeWrap.getScreenBounds(env, parent);
            return isVertical ? bounds.y >= viewBounds.y && bounds.y + bounds.height <= viewBounds.y + viewBounds.height
                    : bounds.x >= viewBounds.x && bounds.x + bounds.width <= viewBounds.x + viewBounds.width;

        } else {
            return false;
        }
    }

    /**
     * Transforms point in local control coordinate system to screen
     * coordinates.
     *
     * @param local
     * @return
     * @see #toLocal(org.jemmy.Point)
     */
    @Override
    public Point toAbsolute(final Point local) {
        Point p = convertToAbsoluteLayout(this, local);
        Rectangle screenBounds = getScreenBounds();
        return new Point(p.x + screenBounds.x, p.y + screenBounds.y);
    }

    /**
     * Transforms point in screen coordinates to local control coordinate
     * system.
     *
     * @param global
     * @return coordinates which should be used for mouse operations.
     * @see #toAbsolute(org.jemmy.Point)
     */
    @Override
    public Point toLocal(final Point global) {
        Rectangle screenBounds = getScreenBounds();
        Point local = new Point(global.x - screenBounds.x, global.y - screenBounds.y);
        return convertToLocalLayout(this, local);
    }

    static Point convertToAbsoluteLayout(final NodeWrap<? extends Node> node, final Point p) {
        return new FutureAction<>(node.getEnvironment(), () -> {
            Bounds layout = node.getControl().getLayoutBounds();
            Bounds toScene = node.getControl().localToScene(layout);
            Point2D loc = node.getControl().localToScene(layout.getMinX() + p.x, layout.getMinY() + p.y);
            return new Point(loc.getX() - toScene.getMinX(), loc.getY() - toScene.getMinY());
        }).get();
    }

    static Point convertToLocalLayout(final NodeWrap<? extends Node> node, final Point p) {
        return new FutureAction<>(node.getEnvironment(), () -> {
            Bounds layout = node.getControl().getLayoutBounds();
            Bounds toScene = node.getControl().localToScene(layout);
            Point2D loc = node.getControl().sceneToLocal(toScene.getMinX() + p.x, toScene.getMinY() + p.y);
            return new Point(loc.getX() - layout.getMinX(), loc.getY() - layout.getMinY());
        }).get();
    }

    /**
     * Tells if stage of the scene is focused.
     *
     * @return
     */
    public boolean isFocused() {
        return new FutureAction<>(getEnvironment(),
                () -> {
                    final Parent<Node> par = as(Parent.class, Node.class);
                    return getControl().isFocused()
                    || (par.lookup(node -> node.isFocused()).size() > 0);
                }).get();
    }

    /**
     * Turns into Showable. If a node is shown, the stage is shown first. In the
     * future, some other steps may be implemented such as switching tabs,
     * scrolling, etc, etc.
     *
     * @return
     * @see Showable
     */
    @As
    public Showable asShowable() {
        if (show == null) {
            show = new NodeShowable();
        }
        return show;
    }

    private class NodeShowable implements Showable, Show {

        public Show shower() {
            return this;
        }

        public void show() {
            SceneWrap.show(getEnvironment(), getScene());
        }
    }
}
