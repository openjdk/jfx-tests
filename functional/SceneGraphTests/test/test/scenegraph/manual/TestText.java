/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.manual;

import client.test.RunUI;
import com.oracle.jdk.sqe.cc.markup.Covers;
import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import org.jemmy.action.AbstractExecutor;
import org.jemmy.env.TestOut;
import org.jemmy.fx.Root;
import org.junit.BeforeClass;
import test.javaclient.shared.TestBase;
import test.scenegraph.app.TestTextApp;

/**
 *
 * @author Sergey Grinev
 */
@Covers(level=Level.FULL, value="javafx.scene.Text.alignment")
public class TestText extends TestBase {

    @RunUI
    @BeforeClass
    public static void runUI() {
        TestTextApp.main(null);
        
        Root.ROOT.getEnvironment().setOutput(AbstractExecutor.NON_QUEUE_ACTION_OUTPUT, TestOut.getNullOutput());
    }
}
