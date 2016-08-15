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
import javafx.application.Platform;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import client.util.JettyServer;

/**
 * Test for refusing reflected XSS attacks.
 * @author Dmitry Ginzburg
 */

public class ReflectedXSSTest extends GenericTestClass {
    Node body;

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for refusing reflected XSS attacks: checks whether WebNode correctly searches for XSS reflected attacks
     */
    @Test(timeout=10000)
    public void test1() {
        final int port = 29876;
        JettyServer jetty = JettyServer.getInstance(port);
        jetty.setBaseDir (getPath (this.getClass(), "resource"));
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                engine.load ("http://127.0.0.1:" + port + "/xss-frame-victim.html?q=<script>script_ran=true</script>");
                prepareWaitPageLoading();
            }
        });
        doWaitPageLoading();
        body = null;
        Platform.runLater (new Runnable() {
            public void run() {
                Document page = engine.getDocument();
                body = page.getElementsByTagName("body").item (0);
            }
        });

        doWait (new Tester () {
            public boolean isPassed () {
                return body != null;
            }
        });

        //Browser sees that we're trying to send some script in GET-query (...q=<script>script_ran=true</script> withing ) to xss-frame-victim.html
        //Then it blocks the same script to be executed. So variable ran_script stay undefined or smth like this and we can see it.
        Assert.assertTrue (body.getTextContent().contains ("true"));
    }
}

