/*
 * Copyright (c) 2012, 2023, Oracle and/or its affiliates. All rights reserved.
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
package scenebuilder;


import com.oracle.javafx.scenebuilder.app.SceneBuilderApp;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Browser;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.SceneDock;

import java.awt.AWTException;

/**
 *
 * @author andrey
 */
public class RunGUIBrowser {

    public static void main(String[] args) throws AWTException {
        new Thread(() -> Application.launch(SceneBuilderApp.class)).start();
        SceneDock mainScene = new SceneDock(new ByWindowType(Stage.class));
        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                mainScene.wrap().getControl().setOnKeyPressed(new EventHandler<KeyEvent>() {
                    boolean browserStarted = false;
                    @Override
                    public void handle(KeyEvent ke) {
                        if (!browserStarted && ke.isControlDown() && ke.isShiftDown() && ke.getCode() == KeyCode.B) {
                            browserStarted = true;
                            javafx.application.Platform.runLater(() -> Browser.runBrowser());
                        }
                    }
                });
            }
        }.dispatch(mainScene.wrap().getEnvironment());
        System.err.println("Click Ctrl-Shift-B to run FX Browser.");
    }
}
