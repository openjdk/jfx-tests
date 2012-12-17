/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.transparency;

import javafx.scene.Node;

/**
 * 
 * @author Alexander Petrov
 */

public interface Factory {
    /**
     * Create node for testing.
     * @param lcd is LCD text work.
     * @return created node.
     */
    public Node createNode(boolean lcd);
    
    /**
     * True if LCD text work in this test.
     * @return 
     */
    public boolean isLCDWork();
    
    /**
     * Testing action on node.
     * @param node testing node.
     */
    public void action(Node node);
}
