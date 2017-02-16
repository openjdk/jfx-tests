/*
 * Copyright (c) 2014, 2016, Oracle and/or its affiliates. All rights reserved.
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
package javafx.scene.control.test.fxcanvas;

import java.util.concurrent.Semaphore;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.embed.swt.FXCanvas;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebView;
import test.javaclient.shared.OtherThreadRunner;
import test.javaclient.shared.Utils;

public class FXCanvasBrowserApp {

    public static final String CONTENT_ID = "content.id";
    public static final String BUTTON_ID = "button.id";
    public static final String SUCCESS_LABEL_ID = "SUCCESS_LABEL_ID";
    public static final String SUCCESS_MESSAGE = "SUCCESS";
    public static int SCENE_WIDTH = 200;
    public static int SCENE_HEIGHT = 200;
    protected Scene scene;
    protected Label successLabel;
    WebView browser = null;
    Shell shell;

    protected FXCanvasBrowserApp() {
        shell = new Shell();
        shell.setText(this.getClass().getSimpleName());
        shell.setLayout(new FillLayout());
        FXCanvas fxCanvas = new FXCanvas(shell, SWT.BORDER);

        browser = new WebView();
        browser.getEngine().getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
            public void changed(ObservableValue ov, State oldState, State newState) {
                if (newState == State.SUCCEEDED) {
                    successLabel.setText(SUCCESS_MESSAGE);
                }
            }
        });
        fxCanvas.setScene(createScene());

        shell.pack();
        Monitor monitor = shell.getMonitor();
        Rectangle monitorRect = monitor.getClientArea();
        Rectangle shellRect = shell.getBounds();
        shellRect.x = Math.max(0, (monitorRect.width - shellRect.width) / 2);
        shellRect.y = Math.max(0, (monitorRect.height - shellRect.height) / 2);
        shell.setBounds(shellRect);
        shell.open();
    }

    protected Scene createScene() {
        final VBox pane = new VBox();
        pane.setMinSize(SCENE_WIDTH, SCENE_HEIGHT);
        pane.setMaxSize(SCENE_WIDTH, SCENE_HEIGHT);
        pane.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
        Scene scene = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);

        Button button = new Button("Refresh");
        button.setId(BUTTON_ID);
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                browser.getEngine().load("http://education.oracle.com/");
            }
        });
        browser.setId(CONTENT_ID);

        successLabel = new Label();
        successLabel.setId(SUCCESS_LABEL_ID);

        pane.setVgrow(browser, Priority.ALWAYS);
        pane.getChildren().add(new HBox(button, successLabel));
        pane.getChildren().add(browser);

        Utils.setCustomFont(scene);

        return scene;
    }

    public static void startAndWaitShell() throws InterruptedException {
        Semaphore shellWaiter = new Semaphore(0);
        OtherThreadRunner.invokeOnMainThread(() -> {
            Display display = new Display();
            Shell shell = new FXCanvasBrowserApp().shell;
            shellWaiter.release();
            while (!shell.isDisposed() && OtherThreadRunner.isRunning()) {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            }
            display.dispose();
        });
        shellWaiter.acquire();
    }

    public static void main(String[] args) {
        try {
            startAndWaitShell();
        } catch (InterruptedException ex) {
            System.err.printf("Failed to start SWT application: %s.\n", ex);
        }
    }
}
