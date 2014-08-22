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


import java.io.File;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;


public class loadfxmlApp1 extends BasicButtonChooserApp {

    public static final String RESOURCE_BASE = "/test/fxmltests/resources/";
    public static final String defaultResourcePath = RESOURCE_BASE + "test-production-gear-flowers.fxml";

    public static enum Pages {
        FileLoad
    }
    public loadfxmlApp1() {
        super(800, 800, "LoadFXML", false);
    }

    private class slotDefaults extends TestNode {

        @Override
        public Node drawNode() {
            Node uplevelNode = null;
            URL resource = getClass().getResource(defaultResourcePath);
            File f = new File(resource.getFile());
            boolean pass = true;
            JavaFXBuilderFactory factory = new JavaFXBuilderFactory();
            if (f.getName().endsWith(".fxml")) {
                System.out.println("loading " + f.getPath());
                try {
                    //uplevelNode = FXMLLoader.load(f.toURI().toURL(), null, factory);
                    uplevelNode = FXMLLoader.load(loadfxmlApp1.class.getResource(defaultResourcePath), null, factory);
                } catch (Exception e) {
                    pass = false;
                    System.out.println("message: " + e.getMessage());
                }
            }
            return pass ? uplevelNode : new Rectangle(10, 10, 10, 10) {

                {
                    setFill(Color.RED);
                }
            };
        }
    }

    @Override
    public TestNode setup() {
        System.out.println("java.library.path=" + System.getProperty("java.library.path"));
        TestNode rootTestNode = new TestNode();

        final int heightPageContentPane = 800;
        final int widthPageContentPane = 800;

        final PageWithSlots loadfxmlPage = new PageWithSlots(Pages.FileLoad.name(), heightPageContentPane, widthPageContentPane);
        loadfxmlPage.setSlotSize(240, 180);

        loadfxmlPage.add(new slotDefaults(), "defaults");

        rootTestNode.add(loadfxmlPage);
        return rootTestNode;
    }

    public static void main(String[] args) {
        Utils.launch(loadfxmlApp1.class, args);
    }
}
