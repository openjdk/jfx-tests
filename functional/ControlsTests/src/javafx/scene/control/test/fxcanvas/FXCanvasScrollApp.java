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
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.embed.swt.FXCanvas;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import test.javaclient.shared.OtherThreadRunner;
import test.javaclient.shared.Utils;

public class FXCanvasScrollApp {

    public static String TEXT_INPUT_ID = "text.input.id";
    public static String BUTTON_ID = "button.id";
    public static String SCROLL_CONTAINER_ID = "scroll.container.id";
    public static String CHECK_ID = "check.id";
    public static int SCENE_WIDTH = 200;
    public static int SCENE_HEIGHT = 200;
    protected Scene scene;
    protected Shell shell;

    protected FXCanvasScrollApp() {
        shell = new Shell();
        shell.setText(this.getClass().getSimpleName());
        shell.setLayout(new FillLayout());

        ScrolledComposite scrollPane = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        FXCanvas fxCanvas = new FXCanvas(scrollPane, SWT.BORDER);
        fxCanvas.setScene(createScene(SCROLL_CONTAINER_ID));
        scrollPane.setContent(fxCanvas);
        scrollPane.setExpandHorizontal(true);
        scrollPane.setExpandVertical(true);
        fxCanvas.pack();
        scrollPane.setMinSize(fxCanvas.getSize());

        shell.pack();
        Monitor monitor = shell.getMonitor();
        Rectangle monitorRect = monitor.getClientArea();
        Rectangle shellRect = shell.getBounds();
        shellRect.x = Math.max(0, (monitorRect.width - shellRect.width) / 2);
        shellRect.y = Math.max(0, (monitorRect.height - shellRect.height) / 2);
        shell.setBounds(shellRect);
        shell.open();
    }

    protected Scene createScene(String id) {
        final VBox pane = new VBox();
        pane.setMinSize(SCENE_WIDTH, SCENE_HEIGHT);
        pane.setMaxSize(SCENE_WIDTH, SCENE_HEIGHT);
        pane.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
        pane.setId(id);
        Scene scene = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);
        final TextField text_input = new TextField("TextInput");
        text_input.setId(TEXT_INPUT_ID);
        pane.getChildren().add(text_input);

        HBox button_box = new HBox(5);
        pane.getChildren().add(button_box);

        final CheckBox check = new CheckBox("Button pressed");
        check.setId(CHECK_ID);

        final Button button = new Button("Button");
        button.setId(BUTTON_ID);
        button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            public void handle(javafx.event.ActionEvent t) {
                check.setSelected(true);
            }
        });

        button_box.getChildren().add(button);
        button_box.getChildren().add(check);

        Utils.setCustomFont(scene);

        return scene;
    }

    public static void startAndWaitShell() throws InterruptedException {
        Semaphore shellWaiter = new Semaphore(0);
        OtherThreadRunner.invokeOnMainThread(new Runnable() {
            public void run() {
                Display display = new Display();
                Shell shell = new FXCanvasScrollApp().shell;
                shellWaiter.release();
                while (!shell.isDisposed() && OtherThreadRunner.isRunning()) {
                    if (!display.readAndDispatch()) {
                        display.sleep();
                    }
                }
                display.dispose();
            }
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
