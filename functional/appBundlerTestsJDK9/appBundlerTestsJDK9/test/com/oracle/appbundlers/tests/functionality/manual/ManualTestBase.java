/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.manual;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.oracle.appbundlers.tests.functionality.TestBase;
import com.oracle.appbundlers.utils.BundlingManager;
import com.oracle.appbundlers.utils.Config;
import com.oracle.appbundlers.utils.ExtensionType;

/**
 * @author Dmitry.Ermashov@oracle.com;
 */
public abstract class ManualTestBase extends TestBase {

    private static final Logger LOG = Logger.getLogger(ManualTestBase.class.getName());
    protected final Object LOCK = new Object();
    protected Boolean result;

    @Test(dataProvider = "getBundlers")
    @Override
    public void runTest(BundlingManager bundlingManager) throws Exception {
        for (ExtensionType extension : ExtensionType.values()) {
            if (!Config.CONFIG_INSTANCE.manualOnly()) {
                throw new SkipException("Skipping manual test");
            }

            String testName = this.getClass().getName() + "::" + testMethod.getName() + "$" + bundlingManager.toString();
            this.bundlingManager = bundlingManager;

            LOG.log(Level.INFO, "Running manual test: {0}.", testName);
            try {
//                prepareTestEnvironment();
                validate();
                bundlingManager.execute(getAllParams(extension), this.currentParameter.getApp());
                doManualVerifications();
            } finally {
                cleanUp();
                LOG.log(Level.INFO, "Finished manual test: {0}.", testName);
            }
            verifyResult();
        }
    }

    public void createDialogWithInstructions(String instructions) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Instructions");
            frame.setPreferredSize(new Dimension(400, 300));
            frame.setLayout(new BorderLayout());
            frame.add(new JTextArea(instructions), BorderLayout.CENTER);
            Container container = new Container();
            JButton pass = new JButton("Pass");
            JButton fail = new JButton("Fail");
            container.setLayout(new FlowLayout());
            container.add(pass);
            container.add(fail);
            frame.add(container, BorderLayout.SOUTH);
            pass.addActionListener((ActionEvent e) -> {
                synchronized (LOCK) {
                    result = Boolean.TRUE;
                    LOCK.notifyAll();
                    frame.dispose();
                }
            });
            fail.addActionListener((ActionEvent e) -> {
                synchronized (LOCK) {
                    result = Boolean.FALSE;
                    LOCK.notifyAll();
                    frame.dispose();
                }
            });

            frame.pack();
            frame.setVisible(true);
        });
    }

    protected abstract void doManualVerifications() throws Exception;

    public void verifyResult() {
        if (result.equals(Boolean.FALSE)) {
            throw new RuntimeException("User evaluated - the test FAILED");
        }
    }
}
