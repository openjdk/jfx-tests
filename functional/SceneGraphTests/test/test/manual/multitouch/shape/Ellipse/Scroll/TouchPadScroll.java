/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.manual.multitouch.shape.Ellipse.Scroll;

import client.test.RunUI;
import org.jemmy.action.AbstractExecutor;
import org.jemmy.env.TestOut;
import org.jemmy.fx.Root;
import test.multitouch.app.MultiTouchApp;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
public class TouchPadScroll {

    @RunUI
    public static void runUI() {
        MultiTouchApp.main(null);
        Root.ROOT.getEnvironment().setOutput(AbstractExecutor.NON_QUEUE_ACTION_OUTPUT, TestOut.getNullOutput());
    }
}
