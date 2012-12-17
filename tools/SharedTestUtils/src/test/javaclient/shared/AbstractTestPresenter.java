/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.javaclient.shared;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author shubov
 */
public interface AbstractTestPresenter<CONTAINER> {
    void show(CONTAINER stage);
    void showTestNode(TestNode tn);
    String getScreenshotPaneName();
    Scene getScene();
}
