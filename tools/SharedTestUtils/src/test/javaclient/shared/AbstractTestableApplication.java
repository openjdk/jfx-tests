/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.javaclient.shared;

import javafx.scene.Node;

/**
 *
 * @author shubov
 */
public interface AbstractTestableApplication {

    String getFailures();
    TestNode openPage(final String top_level, final String inner_level);
    TestNode openPage(final String top_level);
    void doAdditionalAction(final String top_level, final String inner_level);
    String getScreenshotPaneName();
    Node getNodeForScreenshot();
}
