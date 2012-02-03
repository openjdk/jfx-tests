/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package org.jemmy.fx;

import javafx.scene.Node;
import javafx.scene.Scene;
import org.jemmy.env.Environment;
import org.jemmy.interfaces.Parent;

/**
 *
 * @author shura
 */
public interface NodeParent{
    public SceneWrap<? extends Scene> getScene();
    public Parent<Node> getParent();
    public Environment getEnvironment();
}
