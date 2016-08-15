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

import javax.swing.JSlider;
import javafx.scene.control.Button;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import test.javaclient.shared.Utils;


public class JFXPanelApp {

    public static String TEXT_INPUT_ID = "text.input.id";
    public static String BUTTON_ID = "button.id";
    public static String MAIN_CONTAINER_ID = "main.container.id";
    public static String HEAVY_POPUP_CONTAINER_ID = "heavy.container.id";
    public static String LIGHT_POPUP_CONTAINER_ID = "light.container.id";
    public static String MENU_POPUP_CONTAINER_ID = "menu.container.id";
    public static String CHECK_ID = "check.id";

    public static String HEAVYWEIGHT_POPUP_BTN = "Show heavywieght popup";
    public static String LIGHTWEIGHT_POPUP_BTN = "Show lightwieght popup";
    public static String MENU_POPUP_BTN = "Create popup menu";

    public static String RESET_BTN = "Reset content";

    public static int SCENE_WIDTH = 200;
    public static int SCENE_HEIGHT = 200;

    protected Scene scene;
    Popup lightPopup = null;
    Popup heavyPopup = null;

    final TransparentJFXPanel lightweight_popup_fx_panel = new TransparentJFXPanel();
    final TransparentJFXPanel heavyweight_popup_fx_panel = new TransparentJFXPanel();
    final TransparentJFXPanel menu_popup_fx_panel = new TransparentJFXPanel();
    final TransparentJFXPanel mainJavafxPanel = new TransparentJFXPanel();

    protected JFXPanelApp() {
        final JFrame frame = new JFrame(this.getClass().getSimpleName());

        /*final JPanel glass = (JPanel) frame.getGlassPane();
        glass.setVisible(false);
        glass.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseReleased(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
        });*/

//        frame.setBounds(0, 0, 500, 300);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lightweight_popup_fx_panel.setBorder(BorderFactory.createLineBorder(Color.black));

        final JToggleButton popup_button_light = new JToggleButton(LIGHTWEIGHT_POPUP_BTN);
        popup_button_light.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (lightPopup == null) {
                    Point location = frame.getContentPane().getLocationOnScreen();
                    lightPopup = PopupFactory.getSharedInstance().getPopup(popup_button_light, lightweight_popup_fx_panel, (int)location.getX(), (int)location.getY());
                    lightPopup.show();
                } else {
                    lightPopup.hide();
                    lightPopup = null;
                }
            }
        });

        heavyweight_popup_fx_panel.setBorder(BorderFactory.createLineBorder(Color.black));

        final JToggleButton popup_button_heavy = new JToggleButton(HEAVYWEIGHT_POPUP_BTN);
        popup_button_heavy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (heavyPopup == null) {
                    Point location = frame.getContentPane().getLocationOnScreen();
                    heavyPopup = PopupFactory.getSharedInstance().getPopup(popup_button_heavy, heavyweight_popup_fx_panel, 0, 0);
                    heavyPopup.show();
                } else {
                    heavyPopup.hide();
                    heavyPopup = null;
                }
            }
        });

        menu_popup_fx_panel.setBorder(BorderFactory.createLineBorder(Color.black));

        final JButton popup_menu_button = new JButton(MENU_POPUP_BTN);
        popup_menu_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPopupMenu popup = new JPopupMenu();
                popup.add(menu_popup_fx_panel);
                System.out.println("menu_popup_fx_panel.getPreferredSize(): " + menu_popup_fx_panel.getPreferredSize());
                popup.show(frame, -200, -100);
            }
        });

        mainJavafxPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        final TransparentJFXPanel scrollJavafxPanel = new TransparentJFXPanel();
        scrollJavafxPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        final JSlider alpha = new JSlider(0, 100, 100);
        alpha.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                float val = ((float)alpha.getValue())/100;
                mainJavafxPanel.setAlpha(val);
                menu_popup_fx_panel.setAlpha(val);
                heavyweight_popup_fx_panel.setAlpha(val);
                lightweight_popup_fx_panel.setAlpha(val);
            }
        });

        JButton reset_button = new JButton(RESET_BTN);
        reset_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });


        frame.getContentPane().add(alpha, BoxLayout.X_AXIS);
        frame.getContentPane().add(popup_button_heavy, BoxLayout.X_AXIS);
        frame.getContentPane().add(popup_button_light, BoxLayout.X_AXIS);
        frame.getContentPane().add(popup_menu_button, BoxLayout.X_AXIS);
        frame.getContentPane().add(reset_button, BoxLayout.X_AXIS);
        frame.getContentPane().add(mainJavafxPanel, BoxLayout.X_AXIS);

        reset();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    protected void reset() {
        final CountDownLatch cl = new CountDownLatch(1);

        Platform.runLater(new Runnable() {
            public void run() {
                heavyweight_popup_fx_panel.setScene(createScene(HEAVY_POPUP_CONTAINER_ID));
                lightweight_popup_fx_panel.setScene(createScene(LIGHT_POPUP_CONTAINER_ID));
                menu_popup_fx_panel.setScene(createScene(MENU_POPUP_CONTAINER_ID));
                mainJavafxPanel.setScene(createScene(MAIN_CONTAINER_ID));
                cl.countDown();
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
        });

        try {
            cl.await();
        } catch (Exception z) {
        }
    }

    class TransparentJFXPanel extends JFXPanel {
        float alpha = 1;

        public TransparentJFXPanel() {
            super();
            setOpaque(false);
        }

        public void setAlpha(float alpha) {
            this.alpha = alpha;
            this.repaint();
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            super.paint(g2);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JFXPanelApp();
            }
        });
    }
}