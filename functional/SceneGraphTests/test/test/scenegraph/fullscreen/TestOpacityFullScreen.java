/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.fullscreen;

import client.test.RunUI;
import org.jemmy.action.AbstractExecutor;
import org.jemmy.env.TestOut;
import org.jemmy.fx.Root;
import org.junit.BeforeClass;
import test.javaclient.shared.TestBase;

/**
 *
 * @author Alexander Petrov
 */
public class TestOpacityFullScreen extends TestBase{
    
    @RunUI
    @BeforeClass
    public static void runUI() {
        FSTestApp.main(null);

        Root.ROOT.getEnvironment().setOutput(AbstractExecutor.NON_QUEUE_ACTION_OUTPUT, TestOut.getNullOutput());
    }
}
