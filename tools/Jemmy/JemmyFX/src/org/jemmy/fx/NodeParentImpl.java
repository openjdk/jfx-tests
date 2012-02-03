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
import javafx.scene.Parent;
import org.jemmy.env.Environment;
import org.jemmy.lookup.AbstractParent;
import org.jemmy.lookup.HierarchyLookup;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;

/**
 *
 * @author andrey
 */
public class NodeParentImpl extends AbstractParent<Node> {

    private Environment env;
    private Parent parent;

    public NodeParentImpl(NodeWrap<? extends Node> wrap) {
        this((javafx.scene.Parent) wrap.getControl(), wrap.getEnvironment());
    }

    public NodeParentImpl(Parent parent, Environment env) {
        this.env = env;
        this.parent = parent;
    }

    @Override
    public <ST extends Node> Lookup<ST> lookup(Class<ST> controlClass, LookupCriteria<ST> criteria) {
        return new HierarchyLookup<ST>(env, new NodeHierarchy(parent, env), new NodeWrapper(env), controlClass, criteria);
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
