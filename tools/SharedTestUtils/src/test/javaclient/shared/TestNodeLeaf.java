/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.javaclient.shared;

import javafx.scene.Node;

/**
 *
 * @author Sergey Grinev
 */
public class TestNodeLeaf extends TestNode {
    private final Node content;

    public TestNodeLeaf(String nodeName, Node content) {
        super(nodeName);
        this.content = content;

        //magic line for test generation - don't remove this comment
        //System.err.println("\n@Test\npublic void " + PageWithSlots.debug_lastPageName + "_" + Utils.normalizeName(nodeName).replace("-", "_") + "() {\ntestCommon(\"" + PageWithSlots.debug_lastPageName + "\", \"" + nodeName + "\");\n}");
    }

    @Override
    public Node drawNode() {
        return content;
    }
}
