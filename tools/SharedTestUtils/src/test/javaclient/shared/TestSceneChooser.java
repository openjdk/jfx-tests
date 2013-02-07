/*
 * Copyright (c) 2010-2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.javaclient.shared;

import java.util.Collection;

/**
 *
 * @author shubov
 */
public interface TestSceneChooser {

    void addTestNodeToChooser(final TestNode tn);
    void addTestNodeToChooser(final TestNode tn, String nodeName);
    void addTestNodesToChooser(final Collection<TestNode> setupedNodes);
    boolean highlightItem(String item);
}
