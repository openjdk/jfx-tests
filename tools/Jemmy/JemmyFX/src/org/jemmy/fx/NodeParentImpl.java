/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
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
