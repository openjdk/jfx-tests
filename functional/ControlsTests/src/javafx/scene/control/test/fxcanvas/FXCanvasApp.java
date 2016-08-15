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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.embed.swt.FXCanvas;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import test.javaclient.shared.OtherThreadRunner;
import test.javaclient.shared.Utils;

public class FXCanvasApp {

    public static String TEXT_INPUT_ID = "text.input.id";
    public static String BUTTON_ID = "button.id";
    public static String MAIN_CONTAINER_ID = "main.container.id";
    public static String HEAVY_POPUP_CONTAINER_ID = "heavy.container.id";
    public static String MENU_POPUP_CONTAINER_ID = "menu.container.id";
    public static String CHECK_ID = "check.id";

    public static String HEAVYWEIGHT_POPUP_BTN = "Show heavywieght popup";
    public static String MENU_POPUP_BTN = "Create popup menu";

    public static String RESET_BTN = "Reset content";

    public static int SCENE_WIDTH = 200;
    public static int SCENE_HEIGHT = 200;

    protected Scene scene;
    Shell lightPopup = null;
    Shell heavyPopup = null;

    TransparentJFXPanel heavyweight_popup_fx_panel;
    TransparentJFXPanel menu_popup_fx_panel;
    TransparentJFXPanel mainJavafxPanel;

    Shell shell;
    Scale alpha;

    protected FXCanvasApp() {
        shell = new Shell();
        shell.setText(this.getClass().getSimpleName());
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        shell.setLayout(layout);

        final org.eclipse.swt.widgets.Button popup_button_heavy = new org.eclipse.swt.widgets.Button(shell, SWT.TOGGLE);
        popup_button_heavy.setText(HEAVYWEIGHT_POPUP_BTN);
        popup_button_heavy.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                if (heavyPopup == null) {
                    final Shell popup = heavyPopup = new Shell(shell, SWT.NONE);
                    popup.setLayout(new FillLayout());
                    heavyweight_popup_fx_panel = new TransparentJFXPanel(popup, SWT.NONE);
                    heavyweight_popup_fx_panel.setScene(createScene(HEAVY_POPUP_CONTAINER_ID));
                    heavyweight_popup_fx_panel.setAlpha(alpha.getSelection());
                    popup.pack();
                    Rectangle rect = popup.getMonitor().getClientArea();
                    //popup.setLocation(rect.x, rect.y);
                    popup.setLocation(100, 100);
                    popup.setVisible(true);
                } else {
                    heavyPopup.dispose();
                    heavyPopup = null;
                    heavyweight_popup_fx_panel = null;
                }
            }
        });

        final org.eclipse.swt.widgets.Button popupMenuButton = new org.eclipse.swt.widgets.Button(shell, SWT.PUSH);
        popupMenuButton.setText(MENU_POPUP_BTN);
        popupMenuButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                final Shell popup = new Shell(shell, SWT.ON_TOP);
                popup.addListener(SWT.Deactivate, new Listener() {
                    public void handleEvent(Event e) {
                        menu_popup_fx_panel = null;
                        popup.dispose();
                    }
                });
                popup.setLayout(new FillLayout());
                menu_popup_fx_panel = new TransparentJFXPanel(popup, SWT.NONE);
                menu_popup_fx_panel.setScene(createScene(MENU_POPUP_CONTAINER_ID));
                menu_popup_fx_panel.setAlpha(alpha.getSelection());
                popup.pack();
                System.out.println("menu_popup_fx_panel.getPreferredSize(): " + menu_popup_fx_panel.getSize());
                popup.setLocation(200, 100);
                popup.open();
            }
        });

        mainJavafxPanel = new TransparentJFXPanel(shell, SWT.NONE);

        //final SWTFXPanel scrollJavafxPanel = new SWTFXPanel(shell, SWT.NONE);
        alpha = new Scale(shell, SWT.HORIZONTAL);
        alpha.setMaximum(255);
        alpha.setSelection(255);
        alpha.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                int val = alpha.getSelection();
                mainJavafxPanel.setAlpha(val);
                if (menu_popup_fx_panel != null) {
                    menu_popup_fx_panel.setAlpha(val);
                }
                if (heavyweight_popup_fx_panel != null) {
                    heavyweight_popup_fx_panel.setAlpha(val);
                }
            }
        });

        final org.eclipse.swt.widgets.Button reset_button = new org.eclipse.swt.widgets.Button(shell, SWT.PUSH);
        reset_button.setText(RESET_BTN);
        reset_button.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                reset();
            }
        });

        mainJavafxPanel.moveAbove(null);
        reset_button.moveBelow(mainJavafxPanel);
        popupMenuButton.moveBelow(reset_button);

        mainJavafxPanel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        alpha.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        reset();

        shell.pack();
        Monitor monitor = shell.getMonitor();
        Rectangle monitorRect = monitor.getClientArea();
        Rectangle shellRect = shell.getBounds();
        shellRect.x = Math.max(0, (monitorRect.width - shellRect.width) / 2);
        shellRect.y = Math.max(0, (monitorRect.height - shellRect.height) / 2);
        shell.setBounds(shellRect);
        shell.open();
    }

    protected void reset() {
        if (heavyweight_popup_fx_panel != null) {
            heavyweight_popup_fx_panel.setScene(createScene(HEAVY_POPUP_CONTAINER_ID));
        }
        if (menu_popup_fx_panel != null) {
            menu_popup_fx_panel.setScene(createScene(MENU_POPUP_CONTAINER_ID));
        }
        mainJavafxPanel.setScene(createScene(MAIN_CONTAINER_ID));
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

        HBox buttonBox = new HBox(5);
        pane.getChildren().add(buttonBox);

        final CheckBox check = new CheckBox("Button pressed");
        check.setId(CHECK_ID);

        final Button button = new Button("Button");
        button.setId(BUTTON_ID);
        button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            public void handle(javafx.event.ActionEvent t) {
                check.setSelected(true);
            }
        });

        buttonBox.getChildren().addAll(button, check);

        Utils.setCustomFont(scene);

        return scene;
    }

    static class TransparentJFXPanel extends FXCanvas {

        int alpha = 255;
        static boolean FilterAdded;
        static boolean USE_SHELL_ALPHA;

        public TransparentJFXPanel(Composite parent, int style) {
            super(parent, style | SWT.DOUBLE_BUFFERED);
            if (!FilterAdded) {
                //TODO - filter is left dangling in Display
                getDisplay().addFilter(SWT.Paint, new Listener() {
                    public void handleEvent(Event e) {
                        paint(e);
                    }
                });
                FilterAdded = true;
            }
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
            if (USE_SHELL_ALPHA && (getShell().getStyle() & SWT.TITLE) == 0) {
                getShell().setAlpha(alpha);
            } else {
                this.redraw();
            }
        }

        public void paint(Event e) {
            if (USE_SHELL_ALPHA && (getShell().getStyle() & SWT.TITLE) == 0) {
                return;
            }
            if (e.widget instanceof TransparentJFXPanel) {
                Rectangle rect = getClientArea();
                drawBackground(e.gc, rect.x, rect.y, rect.width, rect.height);
                e.gc.setAlpha(alpha);
            }
        }
    }

    public static void startAndWaitShell() throws InterruptedException {
        Semaphore shellWaiter = new Semaphore(0);
        OtherThreadRunner.invokeOnMainThread(() -> {
            Display display = new Display();
            Shell shell = new FXCanvasApp().shell;
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
