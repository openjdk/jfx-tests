/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.manual;

import test.scenegraph.app.InterpolatorApp;
import client.test.RunUI;
import org.jemmy.action.AbstractExecutor;
import org.jemmy.fx.Root;
import org.jemmy.env.TestOut;
import org.junit.BeforeClass;
import test.javaclient.shared.TestBase;

/**
 *
 * @author Sergey Grinev
 */
public class Interpolator extends TestBase {

    @RunUI
    @BeforeClass
    public static void runUI() {
        InterpolatorApp.main(null);

        Root.ROOT.getEnvironment().setOutput(AbstractExecutor.NON_QUEUE_ACTION_OUTPUT, TestOut.getNullOutput());
    }
}
