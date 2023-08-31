/*
 * Copyright (c) 2023, Oracle and/or its affiliates. All rights reserved.
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
package test.ensemble;

import ensemble.EnsembleApp;
import ensemble.control.Popover;
import ensemble.samplepage.SamplePage;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Browser;
import org.jemmy.fx.ByText;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.ToggleButtonDock;
import org.jemmy.fx.control.WebViewDock;
import org.jemmy.interfaces.Parent;

import java.util.Objects;

public class EnsembleTestUtil {

    private SceneDock mainScene;
    private Parent<Node> sceneAsParent;
    private NodeDock mainToolbar;

    public SceneDock mainScene() {
        return mainScene;
    }

    public Parent<Node> sceneAsParent() {
        return sceneAsParent;
    }

    public NodeDock mainToolbar() {
        return mainToolbar;
    }

    public void start() {
        launchEnsemble();
        init();
        addBrowser();
    }

    private void launchEnsemble() {
        new Thread(() -> Application.launch(EnsembleApp.class, new String[0]), "FX app launch thread").start();
    }

    // init operators for UI elements that constantly persist on scene
    private void init() {
        mainScene = new SceneDock(new ByWindowType(Stage.class));
        sceneAsParent = mainScene.asParent();
        mainToolbar = new NodeDock(sceneAsParent, bar -> bar.getStyleClass().contains("ensmeble-tool-bar"));
    }

    public NodeDock sampleArea() {
        return new NodeDock(sceneAsParent, SamplePage.class);
    }

    // ads spy utility to browse scene content
    public void addBrowser() {
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

    public void selectInPupup(String text) throws InterruptedException {
        //TODO
        Thread.sleep(1000);
        Wrap<? extends Popover> popover = sceneAsParent.lookup(Popover.class).wrap();
        popover.as(Parent.class, Node.class)
                .lookup(ListCell.class, cell -> Objects.equals(((ListCell)cell).getText(), text))
                .wrap().mouse().click();
    }

    public void selectDemo(String... texts) throws InterruptedException {
        new ToggleButtonDock(sceneAsParent, "list").mouse().click();
        for(String text : texts) selectInPupup(text);
        //TODO
        Thread.sleep(1000);
    }

    public WebViewDock viewSource() {
        new NodeDock(sceneAsParent, Hyperlink.class, new ByText<Hyperlink>("VIEW SOURCE")).mouse().click();
        return new WebViewDock(sceneAsParent);
    }

    public static void main(String[] args) {
        new EnsembleTestUtil().start();
    }
}
