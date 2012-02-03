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

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.jemmy.action.GetAction;
import org.jemmy.env.Environment;
import org.jemmy.lookup.ControlHierarchy;

/**
 *
 * @author shura
 */
public class NodeHierarchy implements ControlHierarchy {

    private javafx.scene.Parent root;
    private Scene scene;
    private Environment env;

    public NodeHierarchy(Parent root, Environment env) {
        this.root = root;
        this.env = env;
        this.scene = null;
    }

    public NodeHierarchy(Scene scene, Environment env) {
        this.scene = scene;
        this.env = env;
        this.root = null;
    }

    public List<?> getChildren(final Object subParent) {
        GetAction<List<?>> children = new GetAction<List<?>>() {

            @Override
            public void run(Object... parameters) {
                if (subParent instanceof Parent) {
                    setResult(new ArrayList(Parent.class.cast(subParent).getChildrenUnmodifiable()));
                } else {
                    setResult(null);
                }
            }
        };
        env.getExecutor().execute(env, true, children);
        return children.getResult();
    }

    public Object getParent(final Object child) {
        GetAction<Object> childAction = new GetAction<Object>() {

            @Override
            public void run(Object... parameters) {
                if (!(child instanceof Node)) {
                    setResult(null);
                    return;
                }
                Node nd = Node.class.cast(child);
                if (nd.getParent() != null) {
                    setResult(nd.getParent());
                } else {
                    setResult(nd.getScene());
                }
            }
        };
        env.getExecutor().execute(env, true, childAction);
        return childAction.getResult();
    }

    public List<?> getControls() {
        GetAction<List<?>> controls = new GetAction<List<?>>() {
            @Override
            public void run(Object... parameters) {                
                if (root != null) {
                    setResult(root.getChildrenUnmodifiable());
                } else {
                    List root = new ArrayList();
                    root.add(scene.getRoot());
                    setResult(root);
                }
            }
        };
        env.getExecutor().execute(env, true, controls);
        return controls.getResult();
    }
}
