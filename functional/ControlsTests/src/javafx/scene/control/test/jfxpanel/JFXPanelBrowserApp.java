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

package javafx.scene.control.test.jfxpanel;

import javafx.scene.control.Button;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebView;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import test.javaclient.shared.Utils;


public class JFXPanelBrowserApp {

    public static String CONTENT_ID = "content.id";
    public static String BUTTON_ID = "button.id";

    public static int SCENE_WIDTH = 200;
    public static int SCENE_HEIGHT = 200;

    protected Scene scene;

    WebView browser = null;

    protected JFXPanelBrowserApp() {
        final JFrame frame = new JFrame(this.getClass().getSimpleName());

        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JFXPanel scrollJavafxPanel = new JFXPanel();
        scrollJavafxPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        frame.getContentPane().add(new JScrollPane(scrollJavafxPanel), BoxLayout.X_AXIS);

        final CountDownLatch cl = new CountDownLatch(1);

        Platform.runLater(new Runnable() {
            public void run() {
                browser = new WebView();
                scrollJavafxPanel.setScene(createScene());
                cl.countDown();
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

                pane.setVgrow(browser, Priority.ALWAYS);
                pane.getChildren().add(button);
                pane.getChildren().add(browser);

                Utils.setCustomFont(scene);

                return scene;
            }
        });

        try {
            cl.await();
        } catch (Exception z) {
        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                new JFXPanelBrowserApp();
            }
        });
    }
}