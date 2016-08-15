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
package test.fxmltests.app;

import com.sun.javafx.runtime.VersionInfo;
import javafx.scene.Node;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class CustomControlApp extends BasicButtonChooserApp {

    private static int WIDTH = 300;
    private static int HEIGHT = 300;

    public enum Pages {
        customControl
    }

    public CustomControlApp() {
        super(WIDTH, HEIGHT, VersionInfo.getRuntimeVersion(), false);
    }

    private class CustomControlPage extends TestNode {

        @Override
        public Node drawNode() {
            return new CustomControl();
        }
    }

    @Override
    protected TestNode setup() {
        TestNode root = new TestNode();
        PageWithSlots pageCustomControl = new PageWithSlots(Pages.customControl.name(), HEIGHT - 15, WIDTH - 10);
        pageCustomControl.add(new CustomControlPage(), Pages.customControl.name());
        root.add(pageCustomControl);
        return root;
    }


    public static void main(String[] args) {
        Utils.launch(CustomControlApp.class, null);
    }
}
