/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.sun.fx.webnode.tests.api.WebEngine;

import com.sun.fx.webnode.tests.commonUtils.GenericTestClass;
import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;

import client.test.OnlyRunModeMethod;
import client.test.RunModes;

/**
 * Test for javafx.scene.web.WebEngine.reload() method.
 * @author Dmitrij Pochepko
 */
public class reloadTest extends GenericTestClass {

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    private void writeFile(FileWriter fw, String title) throws IOException{
        fw.write("<HTML><HEAD><TITLE>"+title+"</TITLE></HEAD><BODY>test</BODY></HTML>");
        fw.flush();
    }

    private String filePath = "";

    /**
     * Test for javafx.scene.web.WebEngine.reload() method. Checks that a document
     * is loaded properly from the outer source when WebEngine.reload() is called.
     */
    @OnlyRunModeMethod(RunModes.DESKTOP)
    @Test(timeout=10000)
    public void test1() {
        File f = null;

        try {
            f = File.createTempFile ("WebNodeTemp", ".html");
            filePath = f.getAbsolutePath();
        } catch (IOException ex) {
            Logger.getLogger(reloadTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (f == null)
                throw new IOException ("File is not initialized correctly.");
            FileWriter fw = new FileWriter(f);
            writeFile(fw, "title1");
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(reloadTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                engine.load("file:///" + filePath);
                prepareWaitPageLoading();
            }
        });
        doWaitPageLoading();
        String title1 = engine.getTitle();
        Assert.assertEquals("title1", title1);

        try {
            if (f == null)
                throw new IOException ("File is not initialized correctly.");
            FileWriter fw = new FileWriter(f);
            writeFile(fw, "title2");
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(reloadTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Platform.runLater(new Runnable() {
            public void run() {
                engine.reload();
            }
        });

        doWait(new Tester() {
            public boolean isPassed() {
                return (engine != null) && (engine.getTitle() != null) && engine.getTitle().equals("title2");
            }
        });
    }
}
