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
package javafx.scene.control.test.textinput;

import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputControl;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author alexandr_kirov
 */
public class PasswordFieldApp extends TextInputBaseApp {

    public enum Pages {

        PrefColumnCount, PromptText
    }

    public PasswordFieldApp() {
        super(600, 400, "PasswordField", false); // "true" stands for "additionalActionButton = "
    }

    @Override
    protected TestNode setup() {
        super.setup();

        Integer[] counts = new Integer[]{0, 5, 10, 20};
        setupInteger(PasswordFieldApp.Pages.PrefColumnCount.name(), counts);

        String[] texts = new String[]{"", "new text"};
        setupString(PasswordFieldApp.Pages.PromptText.name(), texts);

        return rootTestNode;
    }

//    private class NodeWithChangedMask extends TestNode {
//
//        public NodeWithChangedMask() {
//        }
//
//        @Override
//        public Node drawNode() {
//            PasswordField pf = (PasswordField) createObject();
//            pf.setEchoChar("#");
//            return (TextInputControl) pf;
//        }
//    }

    protected Object createObject() {
        return createObject(350 / 4, 20);
    }

    protected Object createObject(double width, double height) {
        PasswordField passwordField = new PasswordField();
        passwordField.setText(text);
        passwordField.setMinSize(width, height);
        passwordField.setPrefSize(width, height);
        passwordField.setMaxSize(width, height);
        return passwordField;
    }

    public static void main(String[] args) {
        Utils.launch(PasswordFieldApp.class, args);
    }
}
