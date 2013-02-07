/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.javaclient.shared;

import java.util.Collection;
import javafx.scene.Node;

/**
 *
 * @author shubov
 */
public interface ActionHolder {

    public Node drawNode();
    public Collection<? extends Node> draw();
    public void additionalAction();
}
