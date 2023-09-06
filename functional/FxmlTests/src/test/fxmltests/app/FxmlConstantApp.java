/*
 * Copyright (c) 2014, 2023 Oracle and/or its affiliates. All rights reserved.
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author cementovoz
 */
public class FxmlConstantApp extends BasicButtonChooserApp {

    private static String FXML_RESOURCE = "/test/fxmltests/resources/constants.fxml";
    private static int WIDTH = 450;
    private static int HEIGHT = 200;

    public enum Pages {
        property
    }

    public FxmlConstantApp() {
        super(WIDTH, HEIGHT, VersionInfo.getRuntimeVersion(), false);
    }

    public class FxmlLoadPage extends TestNode {

        @Override
        public Node drawNode() {
            try {
                return FXMLLoader.load(getClass().getResource(FXML_RESOURCE));
            }
            catch (Exception exc) {
                exc.printStackTrace();
                return new Rectangle () {
                    {
                        setFill(Color.RED);
                    }
                };
            }
        }
    }

    @Override
    protected TestNode setup() {
        TestNode root = new TestNode();
        PageWithSlots propertyPage = new PageWithSlots(FxmlConstantApp.Pages.property.name(), HEIGHT - 20, WIDTH - 20);
        propertyPage.add(new FxmlLoadPage(), Pages.property.name());
        root.add(propertyPage);
        return root;
    }

    public static void main(String[] args) {
        Utils.launch(FxmlConstantApp.class, args);
    }
}
