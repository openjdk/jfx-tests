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

import javafx.scene.web.WebView;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import org.jemmy.Rectangle;
import org.jemmy.action.FutureAction;
import org.jemmy.control.*;
import org.jemmy.dock.DockInfo;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Show;
import org.jemmy.interfaces.Showable;
import org.w3c.dom.NodeList;

@ControlType({org.w3c.dom.Node.class})
@DockInfo(name = "org.jemmy.fx.control.WebNodeDock")
@ControlInterfaces(value = {Parent.class},
        encapsulates = {org.w3c.dom.Node.class},
        name = {"asWebNodeParent"})
public class WebNodeWrap<CONTROL extends org.w3c.dom.Node> extends Wrap<CONTROL>
        implements Showable, Show {

    public static final String WEB_NODE_PATH_PROP_NAME = "webnode.path";

    protected WebViewWrap<? extends WebView> view;
    protected String path = new String();
    protected JSObject bounds;

    /**
     * @param node
     * @param pane
     */
    @SuppressWarnings("unchecked")
    public WebNodeWrap(WebViewWrap<? extends WebView> pane, CONTROL node) {
        super(pane.getEnvironment(), node);
        this.view = pane;
    }

    public Class<WebView> getType() {
        return WebView.class;
    }

    @As(org.w3c.dom.Node.class)
    public <T extends org.w3c.dom.Node> Parent<T> asWebNodeParent(Class<T> type) {
        return new WebNodeParent(view, this, type);
    }

    @Override
    public Rectangle getScreenBounds() {

        return new FutureAction<>(Root.ROOT.getEnvironment(), () -> {
            if (bounds == null) {
                if (path.isEmpty()) {
                    getPath(getControl());
                }
                bounds = (JSObject) view.getControl().getEngine().executeScript("document" + path + ".getBoundingClientRect()");
            }

            Integer right = getIntegerMember(bounds, "right");
            Integer top = getIntegerMember(bounds, "top");
            Integer bottom = getIntegerMember(bounds, "bottom");
            Integer left = getIntegerMember(bounds, "left");

            Rectangle rect = view.getScreenBounds();
            return new Rectangle(rect.x + left, rect.y + top, right - left, bottom - top);
        }).get();
    }

    private Integer getIntegerMember(JSObject bounds, String member) throws JSException {
        final Object value = bounds.getMember(member);
        if (value instanceof Double) {
            return (int) Math.round((Double) value);
        }
        return (Integer) bounds.getMember(member);
    }

    @Property(WEB_NODE_PATH_PROP_NAME)
    public String getPath() {
        if (path.isEmpty()) {
            getPath(getControl());
        }
        return path;
    }

    protected void getPath(org.w3c.dom.Node node) {
        final org.w3c.dom.Node parentNode = node.getParentNode();
        if (parentNode == null) {
            return;
        }
        final NodeList childNodes = parentNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).equals(node)) {
                path = ".childNodes[" + i + "]" + path;
                getPath(parentNode);
                break;
            }
        }
    }

    public Show shower() {
        return this;
    }

    public void show() {
        new FutureAction(Root.ROOT.getEnvironment(), () -> {
            if (path.isEmpty()) {
                getPath(getControl());
            }
            String script = "document" + path + ".scrollIntoView(false)";
            view.getControl().getEngine().executeScript(script);
        });
    }
}
