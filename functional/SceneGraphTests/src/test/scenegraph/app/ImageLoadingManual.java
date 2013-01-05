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
 */
package test.scenegraph.app;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javax.swing.Box.Filler;
import javax.swing.*;
import test.javaclient.shared.Utils;

public class ImageLoadingManual {

    final JFrame frame;
    protected final JFXPanel fxContent = new JFXPanel();
    public JTextField currentImageNameLabel;
    protected JLabel currentImageLabel;
    protected Scene scene;
    protected ImageView blackBackgroundView;
    protected ImageView whiteBackgroundView;
    protected Rectangle blackRectangle;
    protected Rectangle whiteRectangle;
    protected static String IMAGE_BASE; 
    public List<File> files;
    protected ArrayList<Boolean> blackMask;
    protected ArrayList<Boolean> whiteMask;
    protected JComponent mask;

    public ImageLoadingManual(String path) {
        frame = new JFrame(this.getClass().getSimpleName());
        mask = new MaskPane();
        frame.setGlassPane(mask);
        mask.setVisible(true);
        frame.setSize(640, 480);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel image_box = new JPanel();
        image_box.setLayout(new GridLayout(2, 1));
        image_box.setBackground(new Color(128, 128, 128));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentImageNameLabel = new JTextField();
        currentImageNameLabel.setEditable(false);
        currentImageNameLabel.setMinimumSize(new Dimension(600, 30));
        currentImageNameLabel.setMaximumSize(new Dimension(800, 30));
        currentImageLabel = new JLabel("Current image: ");

        Box control_box = Box.createHorizontalBox();
        control_box.add(currentImageLabel);
        control_box.add(currentImageNameLabel);
        control_box.add(new Filler(new Dimension(0, 0),
                new Dimension(Integer.MAX_VALUE, 0),
                new Dimension(Integer.MAX_VALUE, 0)));

        Box fx_box = Box.createVerticalBox();
        JLabel fx_header = new JLabel("Tested");
        fx_box.add(fx_header);
        fx_header.setAlignmentX(Component.CENTER_ALIGNMENT);
        fx_box.add(fx_header);
        fx_box.add(fxContent);
        image_box.add(fx_box);

        frame.getContentPane().add(image_box);
        frame.getContentPane().add(control_box);

        initFXScene();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        load(path);
        mask.setVisible(false);
    }

    int shift = 0;

    class MaskPane extends JComponent {

        public MaskPane() {
            setOpaque(false);
        }

        void check(final ImageView view, final ArrayList<Boolean> mask, final Graphics g) {
            if (view != null && view.getImage() != null && mask != null) {
                java.awt.Rectangle black_location = getRect(view);
                Point glass_location = getLocationOnScreen();
                g.setColor(Color.RED);
                int count = 0;
                for (int i = 0; i < Math.sqrt(mask.size()); i++) {
                    for (int j = 0; j < Math.sqrt(mask.size()); j++) {
                        if (mask.size() > count && mask.get(count)) {
                            g.fillRect(i + black_location.x - glass_location.x, j + black_location.y - glass_location.y, 1, 1);
                        }
                        count++;
                    }
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            check(blackBackgroundView, blackMask, g);
            check(whiteBackgroundView, whiteMask, g);
        }
    }

    private java.awt.Rectangle getRect(ImageView iv) {
        Bounds rect = iv.localToScene(iv.getLayoutBounds());
        return new java.awt.Rectangle((int) (fxContent.getScene().getWindow().getX() + rect.getMinX() + fxContent.getScene().getX()), (int) (fxContent.getScene().getWindow().getY() + fxContent.getScene().getY() + rect.getMinY()), (int) iv.getImage().getWidth(), (int) iv.getImage().getHeight());
    }

    protected void load(String path) {
       currentImageNameLabel.setText(path);
        currentImageLabel.setText(" Current image: ");
        Image image = new Image(getClass().getResourceAsStream(path));
        blackBackgroundView.setImage(image);
        whiteBackgroundView.setImage(image);
        blackRectangle.setWidth(image.getWidth());
        blackRectangle.setHeight(image.getHeight());
        whiteRectangle.setWidth(image.getWidth());
        whiteRectangle.setHeight(image.getHeight());
    }

    protected void initFXScene() {
        final CountDownLatch cl = new CountDownLatch(1);

        Platform.runLater(new Runnable() {

            public void run() {
                fxContent.setScene(createScene());
                cl.countDown();
            }

            protected Scene createScene() {
                final HBox pane = new HBox();
                Scene scene = new Scene(pane, new javafx.scene.paint.Color(0.5, 0.5, 0.5, 1));
                Utils.addBrowser(scene);
                blackBackgroundView = new ImageView();
                whiteBackgroundView = new ImageView();

                Group black_box = new Group();
                black_box.getChildren().add(blackRectangle = new Rectangle(blackBackgroundView.getFitWidth(), blackBackgroundView.getFitHeight()));
                blackRectangle.setFill(javafx.scene.paint.Color.BLACK);
                black_box.getChildren().add(blackBackgroundView);

                Group white_box = new Group();
                white_box.getChildren().add(whiteRectangle = new Rectangle(whiteBackgroundView.getFitWidth(), whiteBackgroundView.getFitHeight()));
                whiteRectangle.setFill(javafx.scene.paint.Color.WHITE);
                white_box.getChildren().add(whiteBackgroundView);

                StackPane white_stack = new StackPane();
                white_stack.setAlignment(Pos.TOP_LEFT);

                StackPane black_stack = new StackPane();
                black_stack.setAlignment(Pos.TOP_LEFT);

                black_stack.getChildren().add(black_box);
                white_stack.getChildren().add(white_box);

                pane.getChildren().addAll(black_stack, white_stack);

                pane.setHgrow(white_stack, Priority.ALWAYS);
                pane.setHgrow(black_stack, Priority.ALWAYS);
                return scene;
            }
        });

        try {
            cl.await();
        } catch (Exception z) {
        }
    }

    
    //used only for debugging purporses
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                String path = "/test/scenegraph/resources/loading/manual/animated/splash.gif";
                ImageLoadingManual ilm = new ImageLoadingManual(path);
                
            }
        });
    }
}