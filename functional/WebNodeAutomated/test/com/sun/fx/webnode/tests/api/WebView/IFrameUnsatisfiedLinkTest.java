/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.fx.webnode.tests.api.WebView;

import com.sun.fx.webnode.tests.commonUtils.GenericTestClass;
import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.html.HTMLIFrameElement;

/**
 * Test for JDK-8134470, UnsatisfiedLinkError calling getId
 * on HTMLIFrameElement
 * @author andrey.rusakov@oracle.com
 */
public class IFrameUnsatisfiedLinkTest extends GenericTestClass {

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    @Test
    public void test() {
        AtomicBoolean passed = new AtomicBoolean(false);
        Platform.runLater(() -> {
            initViewWithEngine();
            engine.getLoadWorker().stateProperty().addListener(
                    (ov, old, now) -> {
                        if (now == Worker.State.SUCCEEDED) {
                            ((HTMLIFrameElement) engine.getDocument()
                            .getElementsByTagName("iframe").item(0)).getId();
                            passed.set(true);
                        }
                    });
            engine.loadContent("<html><body>"
                    + "<iframe id='123' src='resource/example1.html'/>"
                    + "</body></html>");
        });
        doWait(passed::get);
    }
}
