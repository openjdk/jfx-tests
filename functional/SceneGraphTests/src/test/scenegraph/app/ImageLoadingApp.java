/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.jemmy.image.GlassImage;
import org.jemmy.image.GlassPixelImageComparator;
import org.jemmy.image.pixel.Raster;
import org.jemmy.input.RobotDriver;
import test.javaclient.shared.JemmyUtils;
import test.javaclient.shared.Utils;

public class ImageLoadingApp {

    final JFrame frame;
    protected final JFXPanel fxContent = new JFXPanel();
    public JTextField currentImageNameLabel;
    protected JLabel currentImageLabel;
    protected int counter = 0;
    protected JLabel blackImage;
    protected JLabel whiteImage;
    public JCheckBox check;
    public JButton check_button;
    public JButton next_button;
    protected Scene scene;
    protected ImageView blackBackgroundView;
    protected ImageView whiteBackgroundView;
    protected Rectangle blackRectangle;
    protected Rectangle whiteRectangle;
    protected static String IMAGE_BASE; //= "/test/scenegraph/resources/loading/gif/";
    public List<File> files;
    protected ArrayList<Boolean> blackMask;
    protected ArrayList<Boolean> whiteMask;
    protected JComponent mask;
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    public List<String> checkAll(String path) {
        ArrayList<String> incorrectImages = new ArrayList<String>();
        for (int i = 0; i < files.size(); i++) {
            next_button.doClick();
            check_button.doClick();
            try {
                if (!checkImg()) {
                    incorrectImages.add(currentImageNameLabel.getText());
                }
            } catch (Exception e) {
                incorrectImages.add(currentImageNameLabel.getText());
            }
        }
        return incorrectImages;
    }

    public void setImageBase(String path) {
        System.out.println("ILA: loading images");
        IMAGE_BASE = path;
        File folder = new File(getClass().getResource(IMAGE_BASE).getFile());
        System.out.println("folder = " + folder);
        files = new ArrayList<File>();
        System.out.println("ILA: " + folder.isFile());
        System.out.println("ILA: " + folder.isDirectory());
        System.out.println("ILA: " + folder.canRead());
        System.out.println("ILA: " + folder.getAbsolutePath());
//        try {
//            System.out.println("ILA: " + folder.getCanonicalPath());
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
        System.out.println("ILA: " + folder.listFiles().length);
        for (File file : folder.listFiles()) {
            System.out.println("Trying " + file);
            if (file.isFile()) {
                System.out.println("Loaded " + file.getAbsolutePath());
                files.add(file);
            }
        }
        System.out.println("ILA: loading images done");
    }

    public ImageLoadingApp() {
        frame = new JFrame(this.getClass().getSimpleName());
        mask = new MaskPane();
        frame.setGlassPane(mask);
        mask.setVisible(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel image_box = new JPanel();
        image_box.setLayout(new GridLayout(2, 1));
        image_box.setBackground(new Color(128, 128, 128));

        Box swing_box = Box.createVerticalBox();
        Box swing_content_box = Box.createHorizontalBox();
        JLabel swing_header = new JLabel("Template");
        swing_box.add(swing_header);
        swing_header.setAlignmentX(Component.CENTER_ALIGNMENT);
        swing_box.add(swing_content_box);

        blackImage = new JLabel();
        blackImage.setOpaque(true);
        blackImage.setBackground(Color.BLACK);

        whiteImage = new JLabel();
        whiteImage.setOpaque(true);
        whiteImage.setBackground(Color.WHITE);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentImageNameLabel = new JTextField();
        currentImageNameLabel.setEditable(false);
        currentImageNameLabel.setMinimumSize(new Dimension(300, 30));
        currentImageNameLabel.setMaximumSize(new Dimension(300, 30));
        currentImageLabel = new JLabel("Current image: ");

        JButton reset_button = new JButton("Reload image");
        reset_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
                mask.setVisible(false);
            }
        });

        next_button = new JButton("Next image");
        next_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
                mask.setVisible(false);
                if (++counter >= files.size()) {
                    counter = 0;
                }
            }
        });

        check = new JCheckBox();

        check_button = new JButton("Check image");
        check_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkImg();
                } catch (Exception ex) {
                    Logger.getLogger(ImageLoadingApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Box control_box = Box.createHorizontalBox();
        control_box.add(next_button);
        control_box.add(reset_button);
        control_box.add(check_button);
        control_box.add(check);
        control_box.add(currentImageLabel);
        control_box.add(currentImageNameLabel);
        control_box.add(new Filler(new Dimension(0, 0),
                new Dimension(Integer.MAX_VALUE, 0),
                new Dimension(Integer.MAX_VALUE, 0)));

        blackImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        whiteImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        blackImage.setAlignmentY(Component.CENTER_ALIGNMENT);
        whiteImage.setAlignmentY(Component.CENTER_ALIGNMENT);

        swing_content_box.add(blackImage);
        swing_content_box.add(Box.createHorizontalGlue());
        swing_content_box.add(whiteImage);
        swing_content_box.add(Box.createHorizontalGlue());

        Box fx_box = Box.createVerticalBox();
        JLabel fx_header = new JLabel("Tested");
        fx_box.add(fx_header);
        fx_header.setAlignmentX(Component.CENTER_ALIGNMENT);
        fx_box.add(fx_header);
        fx_box.add(fxContent);

        image_box.add(swing_box);
        image_box.add(fx_box);

        frame.getContentPane().add(image_box);
        frame.getContentPane().add(control_box);

        initFXScene();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public boolean checkImg() throws AWTException {
        mask.setVisible(false);
        if (JemmyUtils.usingGlassRobot()) {
            org.jemmy.image.Image gl_img_b = RobotDriver.createScreenCapture(new org.jemmy.Rectangle(blackImage.getLocationOnScreen().x, blackImage.getLocationOnScreen().y, blackImage.getSize().width, blackImage.getSize().height));
            org.jemmy.image.Image gl_img_w = RobotDriver.createScreenCapture(new org.jemmy.Rectangle(whiteImage.getLocationOnScreen().x, whiteImage.getLocationOnScreen().y, whiteImage.getSize().width, whiteImage.getSize().height));
            org.jemmy.image.Image fx_img_b = RobotDriver.createScreenCapture((org.jemmy.Rectangle) getRect(blackBackgroundView));
            org.jemmy.image.Image fx_img_w = RobotDriver.createScreenCapture((org.jemmy.Rectangle) getRect(whiteBackgroundView));
            blackMask = new ArrayList<Boolean>();
            whiteMask = new ArrayList<Boolean>();
            boolean equal_b = isEqual(gl_img_b, fx_img_b, blackMask);
            boolean equal_w = isEqual(gl_img_w, fx_img_w, whiteMask);
            boolean res = !(equal_b && equal_w);
            check.setSelected(res);
            mask.setVisible(true);
            mask.repaint();
            return res;
        } else {
            try {
                java.awt.Robot robot = new java.awt.Robot();
                BufferedImage awt_img_b = robot.createScreenCapture(new java.awt.Rectangle(blackImage.getLocationOnScreen(), blackImage.getSize()));
                BufferedImage awt_img_w = robot.createScreenCapture(new java.awt.Rectangle(whiteImage.getLocationOnScreen(), whiteImage.getSize()));
                BufferedImage fx_img_b = robot.createScreenCapture((java.awt.Rectangle) getRect(blackBackgroundView));
                BufferedImage fx_img_w = robot.createScreenCapture((java.awt.Rectangle) getRect(whiteBackgroundView));
                blackMask = new ArrayList<Boolean>();
                whiteMask = new ArrayList<Boolean>();
                boolean equal_b = isEqual(awt_img_b, fx_img_b, blackMask);
                boolean equal_w = isEqual(awt_img_w, fx_img_w, whiteMask);
                boolean res = !(equal_b && equal_w);
                check.setSelected(res);
                mask.setVisible(true);
                mask.repaint();
                return res;
            } catch (AWTException ex) {
                Logger.getLogger(ImageLoadingApp.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

    }
    int shift = 0;

    class MaskPane extends JComponent {

        public MaskPane() {
            setOpaque(false);
        }

        void check(final ImageView view, final ArrayList<Boolean> mask, final Graphics g) {
            if (view != null && view.getImage() != null && mask != null) {
                java.awt.Rectangle black_location = (java.awt.Rectangle) getRect(view);
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

    boolean isEqual(BufferedImage alpha_true_img, BufferedImage white_bkg_img, ArrayList<Boolean> mask) {
        if (alpha_true_img == white_bkg_img) {
            return true;
        }
        if (alpha_true_img == null || white_bkg_img == null) {
            return false;
        }
        if (alpha_true_img.getWidth() != white_bkg_img.getWidth()) {
            return false;
        }
        if (alpha_true_img.getHeight() != white_bkg_img.getHeight()) {
            return false;
        }
        boolean diff = false;
        for (int i = 0; i < alpha_true_img.getWidth(); i++) {
            for (int j = 0; j < alpha_true_img.getHeight(); j++) {
                Color true_alpha_clr = new Color(alpha_true_img.getRGB(i, j), true);
                Color white_bkg_clr = new Color(white_bkg_img.getRGB(i, j), true);
                if (!white_bkg_clr.equals(true_alpha_clr) || !white_bkg_clr.equals(true_alpha_clr)) {
                    mask.add(true);
                    diff = true;
                } else {
                    mask.add(false);
                }
            }
        }
        return diff;
    }

    boolean isEqual(org.jemmy.image.Image alpha_true_img, org.jemmy.image.Image white_bkg_img, ArrayList<Boolean> mask) {
        GlassImage gl_alpha_true_img = ((GlassImage) alpha_true_img);
        GlassImage gl_white_bkg_img = ((GlassImage) white_bkg_img);
        if (gl_alpha_true_img.compareTo(white_bkg_img) == null) {
            return true;
        }
        if (gl_alpha_true_img == null || gl_white_bkg_img == null) {
            return false;
        }
        if (gl_alpha_true_img.getImage().getWidth() != gl_white_bkg_img.getImage().getWidth()) {
            return false;
        }
        if (gl_alpha_true_img.getImage().getHeight() != gl_white_bkg_img.getImage().getHeight()) {
            return false;
        }
        boolean diff = false;
        for (int i = 0; i < gl_alpha_true_img.getImage().getWidth(); i++) {
            for (int j = 0; j < gl_alpha_true_img.getImage().getHeight(); j++) {

                double[] colors_a = new double[gl_alpha_true_img.getSupported().length];
                gl_alpha_true_img.getColors(i, j, colors_a);
                double[] colors_a_alpha = new double[]{
                    colors_a[GlassPixelImageComparator.arrayIndexOf(gl_alpha_true_img.getSupported(), Raster.Component.RED)],
                    colors_a[GlassPixelImageComparator.arrayIndexOf(gl_alpha_true_img.getSupported(), Raster.Component.GREEN)],
                    colors_a[GlassPixelImageComparator.arrayIndexOf(gl_alpha_true_img.getSupported(), Raster.Component.BLUE)],
                    colors_a[GlassPixelImageComparator.arrayIndexOf(gl_alpha_true_img.getSupported(), Raster.Component.ALPHA)]
                };

                double[] colors_w = new double[gl_white_bkg_img.getSupported().length];
                gl_white_bkg_img.getColors(i, j, colors_w);
                double[] colors_w_bkg = new double[]{
                    colors_w[GlassPixelImageComparator.arrayIndexOf(gl_white_bkg_img.getSupported(), Raster.Component.RED)],
                    colors_w[GlassPixelImageComparator.arrayIndexOf(gl_white_bkg_img.getSupported(), Raster.Component.GREEN)],
                    colors_w[GlassPixelImageComparator.arrayIndexOf(gl_white_bkg_img.getSupported(), Raster.Component.BLUE)],
                    colors_w[GlassPixelImageComparator.arrayIndexOf(gl_white_bkg_img.getSupported(), Raster.Component.ALPHA)]
                };

                if (!Arrays.equals(colors_w_bkg, colors_a_alpha)) {
                    mask.add(true);
                    diff = true;
                } else {
                    mask.add(false);
                }
            }
        }
        return diff;
    }

    boolean isEqual(BufferedImage alpha_true_img, BufferedImage black_bkg_img, BufferedImage white_bkg_img) {
        if (alpha_true_img == black_bkg_img && alpha_true_img == white_bkg_img) {
            return true;
        } // true if both are null
        if (alpha_true_img == null || black_bkg_img == null || white_bkg_img == null) {
            return false;
        }
        if (alpha_true_img.getWidth() != black_bkg_img.getWidth() || alpha_true_img.getWidth() != white_bkg_img.getWidth()) {
            return false;
        }
        if (alpha_true_img.getHeight() != black_bkg_img.getHeight() || alpha_true_img.getHeight() != white_bkg_img.getHeight()) {
            return false;
        }
        for (int i = 0; i < alpha_true_img.getWidth(); i++) {
            for (int j = 0; j < alpha_true_img.getHeight(); j++) {
                Color true_alpha_clr = new Color(alpha_true_img.getRGB(i, j), true);
                Color black_bkg_clr = new Color(black_bkg_img.getRGB(i, j), true);
                Color white_bkg_clr = new Color(white_bkg_img.getRGB(i, j), true);
                Color true_black_bkg_clr = addToBackground(Color.BLACK, true_alpha_clr);
                Color true_white_bkg_clr = addToBackground(Color.WHITE, true_alpha_clr);
                if (!black_bkg_clr.equals(true_black_bkg_clr) || !white_bkg_clr.equals(true_white_bkg_clr)) {
                    System.out.println("Not equals:" + i + " " + j);
                    return false;
                }
            }
        }
        return true;
    }

    private Color addToBackground(Color background, Color color) {
        return new Color((background.getRed() * (255 - color.getAlpha()) + color.getRed() * color.getAlpha()) / 255,
                (background.getGreen() * (255 - color.getAlpha()) + color.getGreen() * color.getAlpha()) / 255,
                (background.getBlue() * (255 - color.getAlpha()) + color.getBlue() * color.getAlpha()) / 255,
                255);
    }

    private Object getRect(ImageView iv) {
        if (JemmyUtils.usingGlassRobot()) {
            Bounds rect = iv.localToScene(iv.getLayoutBounds());
            return new org.jemmy.Rectangle((int) (fxContent.getScene().getWindow().getX() + rect.getMinX() + fxContent.getScene().getX()), (int) (fxContent.getScene().getWindow().getY() + fxContent.getScene().getY() + rect.getMinY()), (int) iv.getImage().getWidth(), (int) iv.getImage().getHeight());
        } else {
            Bounds rect = iv.localToScene(iv.getLayoutBounds());
            return new java.awt.Rectangle((int) (fxContent.getScene().getWindow().getX() + rect.getMinX() + fxContent.getScene().getX()), (int) (fxContent.getScene().getWindow().getY() + fxContent.getScene().getY() + rect.getMinY()), (int) iv.getImage().getWidth(), (int) iv.getImage().getHeight());
        }
    }

    protected void load() {
        check.setSelected(false);

        URL url = getClass().getResource(IMAGE_BASE + "/" + files.get(counter).getName());
        ImageIcon icon = new ImageIcon(url); // image_path a_image  -- use a_image to load bmp image. //  
        //BufferedImage has some problems with loading and rendering of images. Even with lossless .png and .gif images
        icon.getImage().flush();
        currentImageNameLabel.setText(files.get(counter).getName());
        currentImageLabel.setText(" Current image: [" + (counter + 1) + " from " + files.size() + "] ");
        blackImage.setIcon(icon);
        whiteImage.setIcon(icon);

        Image image = new Image(getClass().getResourceAsStream(IMAGE_BASE + "/" + files.get(counter).getName()));
        blackBackgroundView.setImage(image);
        whiteBackgroundView.setImage(image);
        blackRectangle.setWidth(image.getWidth());
        blackRectangle.setHeight(image.getHeight());
        whiteRectangle.setWidth(image.getWidth());
        whiteRectangle.setHeight(image.getHeight());
        int width = WIDTH;
        int height = HEIGHT;
        if (image.getWidth() * 2 > width) {
            width = (int) (image.getWidth() * 2 + 100);
        }
        if (image.getHeight() * 2 > height) {
            height = (int) (image.getHeight() * 2 + 100);
        }
        frame.setSize(width, height);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ImageLoadingApp ilm = new ImageLoadingApp();
                String path = "/test/scenegraph/resources/loading/png/base";
                ilm.setImageBase(path);
            }
        });
    }
}