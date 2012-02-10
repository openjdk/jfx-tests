/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
package ensemble.test;

import ensemble.Ensemble2;
import java.awt.AWTException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Browser;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.ToolBarDock;
import org.jemmy.fx.control.TreeViewDock;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import org.junit.BeforeClass;

public class EnsembleTestBase {

    protected static SceneDock mainScene;
    protected static Parent<Node> sceneAsParent;
    protected static TreeViewDock samplesTree;
    protected static ToolBarDock mainToolbar;
    protected static ToolBarDock pageTreeToolbar;

    protected static void launch() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Application.launch(Ensemble2.class, new String[0]);
            }
        }, "FX app launch thread").start();

    }

    @BeforeClass
    public static void runUI() {
        launch();
        initBase();
        addBrowser();
    }

    // init operators for UI elements that constantly persist on scene
    protected static void initBase() {
        mainScene = new SceneDock(new ByWindowType(Stage.class));
        sceneAsParent = mainScene.asParent();
        mainToolbar = new ToolBarDock(sceneAsParent, new ByID<ToolBar>("mainToolBar"));
        samplesTree = new TreeViewDock(sceneAsParent, new ByID<TreeView>("page-tree"));
        pageTreeToolbar = new ToolBarDock(sceneAsParent, new ByID<ToolBar>("page-tree-toolbar"));
    }

    // ads spy utility to browse scene content
    public static void addBrowser() {
        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                mainScene.wrap().getControl().setOnKeyPressed(new EventHandler<KeyEvent>() {

                    boolean browserStarted = false;

                    @Override
                    public void handle(KeyEvent ke) {
                        if (!browserStarted && ke.isControlDown() && ke.isShiftDown() && ke.getCode() == KeyCode.B) {
                            browserStarted = true;
                            javafx.application.Platform.runLater(new Runnable() {

                                public void run() {
                                    try {
                                        Browser.runBrowser();
                                    } catch (AWTException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }.dispatch(mainScene.wrap().getEnvironment());

        System.err.println("Click Ctrl-Shift-B to run FX Browser.");
    }

    protected void waitSamplesTreeSelection(String itemText) {
        samplesTree.wrap().waitState(new State<String>() {

            public String reached() {
                return (String) ((TreeItem)samplesTree.wrap().getControl().getSelectionModel().getSelectedItem()).getValue();
            }
        }, itemText);
    }

    public static void main(String[] args) {
        runUI();
    }
}