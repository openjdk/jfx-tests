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

package com.sun.fx.webnode.tests.customizable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author Irina Grineva
 */
public class CustomizablePropertyLauncher extends CustomizableLauncher {

    @Override
    protected HBox buttons() {
        Button disableJS = new Button("Disable JavaScript");
        disableJS.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                engine.javaScriptEnabledProperty().set(false);
            }
        });
        Button enableJS = new Button("Enable JavaScript");
        enableJS.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                engine.javaScriptEnabledProperty().set(true);
            }
        });
        Button disableContextMenu = new Button("Disable context menu");
        disableContextMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                view.contextMenuEnabledProperty().set(false);
            }
        });
        Button enableContextMenu = new Button("Enable context menu");
        enableContextMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                view.contextMenuEnabledProperty().set(true);
            }
        });
        Button attachStyleSheet = new Button("Attach user stylesheet");
        attachStyleSheet.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                engine.userStyleSheetLocationProperty().set(CustomizableLauncher.class.getResource("resources/user.css").toExternalForm());
            }
        });
        Button detachStyleSheet = new Button("Detach user stylesheet");
        detachStyleSheet.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                engine.userStyleSheetLocationProperty().set(null);
            }
        });
        Button attachExternalStyleSheet = new Button("Attach user stylesheet (external URL)");
        attachExternalStyleSheet.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                engine.userStyleSheetLocationProperty().set("http://google.com");
            }
        });
        Button attachMalformedStyleSheet = new Button("Attach user stylesheet (malformed URL)");
        attachMalformedStyleSheet.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                engine.userStyleSheetLocationProperty().set("ololo");
            }
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(disableJS, enableJS, disableContextMenu, enableContextMenu, attachStyleSheet, detachStyleSheet, attachExternalStyleSheet, attachMalformedStyleSheet);

        return buttons;
    }

}
