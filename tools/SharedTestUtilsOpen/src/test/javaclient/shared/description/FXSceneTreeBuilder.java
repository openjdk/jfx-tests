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
 */

package test.javaclient.shared.description;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.PopupWindow;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.lookup.Lookup;

public class FXSceneTreeBuilder {
    static void addChilren(TreeNode<FXSimpleNodeDescription> description, Parent parent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node.isVisible()) {
                final TreeNode<FXSimpleNodeDescription> treeNode = new TreeNode<FXSimpleNodeDescription>(new FXSimpleNodeDescription(node));
                description.addChild(treeNode);
                if (node instanceof Parent) {
                    addChilren(treeNode, (Parent)node);
                }
            }
        }
    }

    public static TreeNode<FXSimpleNodeDescription> build(final Parent node) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return build(node, null);
    }

    public static TreeNode<FXSimpleNodeDescription> build(final Parent node, final Parent relative) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TreeNode<FXSimpleNodeDescription> root = new TreeNode<FXSimpleNodeDescription>(new FXSimpleNodeDescription(node, relative));
        addChilren(root, node);
        return root;
    }

    public static ArrayList<TreeNode<FXSimpleNodeDescription>> build(final Wrap<? extends Parent> wrap) {
        return new GetAction<ArrayList<TreeNode<FXSimpleNodeDescription>>>() {
            @Override
            public void run(Object... os) throws Exception {
                ArrayList<TreeNode<FXSimpleNodeDescription>> list = new ArrayList<TreeNode<FXSimpleNodeDescription>>();
                list.add(build(wrap.getControl()));
                final Lookup<Scene> lookup = Root.ROOT.lookup(new ByWindowType(PopupWindow.class));
                for (int  i = 0; i < lookup.size(); i++) {
                    Wrap<? extends Scene> popup = lookup.wrap(i);
                    if (wrap.getScreenBounds().intersects(popup.getScreenBounds())) {
                        list.add(build(popup.getControl().getRoot(), wrap.getControl()));
                    }
                }
                setResult(list);
            }
        }.dispatch(wrap.getEnvironment());
    }
}
