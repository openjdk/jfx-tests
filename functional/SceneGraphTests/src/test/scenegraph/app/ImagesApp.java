/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.app;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import java.io.InputStream;
import javafx.scene.Node;
import javafx.scene.effect.ImageInput;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.Light.Distant;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Rectangle2D;
import test.javaclient.shared.TestNodeLeaf;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import test.javaclient.shared.PageWithSlots;
import static test.javaclient.shared.Utils.*;

/**
 *
 * @author Sergey Grinev
 */
public class ImagesApp extends BasicButtonChooserApp {
    public ImagesApp() {
        super(600, 400, "Images", false);
    }
    public static final String IMAGE_BASE = "/test/scenegraph/resources/";
    public static final String defaultImagePath = IMAGE_BASE + "car.png";

    @Override
    protected TestNode setup() {
        final TestNode root = new TestNode();
        TestNode page;

        root.add(page = new PageWithSlots("Image", height, width));

        {
            ImageView iv = new ImageView();
            InputStream is = getClass().getResourceAsStream(defaultImagePath);
            Image image = new Image(is);
            iv.setImage(image);
            page.add(new SmallLeaf("defaults", iv));
        }
        {
            ImageView iv = new ImageView();
            Image image = new Image(getClass().getResourceAsStream(defaultImagePath), 50, 50, false, false);
            if (image.getRequestedHeight() != 50) {
                reportGetterFailure("image.getRequestedHeight()");
            }
            if (image.getRequestedWidth() != 50) {
                reportGetterFailure("image.getRequestedWidth()");
            }
            if (image.getHeight() != 50) {
                reportGetterFailure("image.getHeight()");
            }
            if (image.getWidth() != 50) {
                reportGetterFailure("image.getWidth()");
            }
            if (image.isPreserveRatio()) {
                reportGetterFailure("image.isPreserveRatio()");
            }
            if (image.isSmooth()) {
                reportGetterFailure("image.isSmooth()");
            }

            iv.setImage(image);
            page.add(new SmallLeaf("resize", iv));

            try { Thread.sleep(500); }
            catch(Exception e) { e.printStackTrace();}

            if (image.getProgress() != 100) {
                reportGetterFailure("image.getProgress()");
            }

        }
        {
            ImageView iv = new ImageView();
            Image image = new Image(getClass().getResourceAsStream(defaultImagePath), 50, 50, true, false);
            iv.setImage(image);
            page.add(new SmallLeaf("resize preserve", iv));
        }
        {
            ImageView iv = new ImageView();
            Image image = new Image(getClass().getResourceAsStream(defaultImagePath), 50, 50, false, true);
            iv.setImage(image);
            if (!image.isSmooth()) {
                reportGetterFailure("image.isSmooth()");
            }
            page.add(new SmallLeaf("resize smooth", iv));
        }
        {
            ImageView iv = new ImageView();
            //Image placeholder = new Image(getClass().getResourceAsStream(IMAGE_BASE + "placeholder.png"));
            Image image = new Image(getClass().getResourceAsStream(defaultImagePath));//, placeholder);
            iv.setImage(image);
            page.add(new SmallLeaf("placeholder (may blink)", iv));
        }

        root.add(page = new PageWithSlots("ImageView", height, width));
        final Image image = new Image(getClass().getResourceAsStream(defaultImagePath));

        {
            ImageView iv = new ImageView();
            iv.setImage(image);
            page.add(new SmallLeaf("as is", iv));
        }
        {
            ImageView iv = new ImageView();
            iv.setImage(image);
            iv.setFitWidth(40);
            iv.setPreserveRatio(false);
            page.add(new SmallLeaf("fit width 40", iv));
        }
        {
            ImageView iv = new ImageView();
            iv.setImage(image);
            iv.setFitWidth(40);
            iv.setSmooth(true);
            page.add(new SmallLeaf("fit smoth", iv));
        }
        {
            ImageView iv = new ImageView();
            iv.setImage(image);
            iv.setFitWidth(40);
            iv.setPreserveRatio(true);
            page.add(new SmallLeaf("fit preserve", iv));
        }
        {
            ImageView iv = new ImageView();
            iv.setImage(image);
            iv.setFitHeight(30);
            page.add(new SmallLeaf("fit height", iv));
        }
        {
            ImageView iv = new ImageView();
            iv.setImage(image);
            iv.setViewport(new Rectangle2D(11, 11, image.getWidth() - 22, image.getHeight() - 22));
            page.add(new SmallLeaf("viewport", iv));
        }
        {
            ImageView iv = new ImageView();
            iv.setImage(image);
            iv.setRotate(-40);
            iv.setTranslateY(20);
            page.add(new SmallLeaf("rotate", iv));
        }
        {
            Group gr = new Group();
            final Canvas canvas = new Canvas(110, 110);
            GraphicsContext gc = canvas.getGraphicsContext2D();

            gc.drawImage(image, 10, 20);
            gr.getChildren().add(canvas);
            page.add(new SmallLeaf("canvas", gr));
        }

        root.add(page = new PageWithSlots("ImageInEffects", height, width));
        {
            Rectangle rect = new Rectangle(0, 0, 80, 50);
            rect.setFill(Color.RED);
            Lighting l = new Lighting();
            Distant dl = new Distant();
            dl.setAzimuth(90);
            dl.setElevation(25);
            l.setLight(dl);
            ImageInput effect = new ImageInput();
            effect.setSource(image);
            effect.setX(5);
            effect.setY(5);
            l.setBumpInput(effect);
            rect.setEffect(l);

            page.add(new SmallLeaf("Lighting-Identity", rect));
        }
        {
            ImageView iv = new ImageView();
            iv.setImage(image);
            iv.setEffect(new SepiaTone());

            page.add(new SmallLeaf("SepiaTone", iv));
        }
        root.add(page = new PageWithSlots("ImagePattern", height, width));
        {
            Rectangle rect = new Rectangle(50, 50);
            InputStream is = getClass().getResourceAsStream(defaultImagePath);
            rect.setFill(new ImagePattern(image, 0.0f, 0.0f, 0.5f, 0.5f, true));
            page.add(new SmallLeaf("defaults", rect));
        }
        {
            Rectangle rect = new Rectangle(50, 50);
            ImagePattern imagePattern = new ImagePattern(image, 0.0, 0.0, 0.5, 0.5, true);
            if (imagePattern.getHeight() != 0.5) {
                reportGetterFailure("imagePattern.getHeight()");
            }
            if (!imagePattern.getImage().equals(image)) {
                reportGetterFailure("imagePattern.getImage()");
            }
            if (imagePattern.getWidth() != 0.5) {
                reportGetterFailure("imagePattern.getWidth()");
            }
            if (imagePattern.getX() != 0) {
                reportGetterFailure("imagePattern.getX()");
            }
            if (imagePattern.getY() != 0) {
                reportGetterFailure("imagePattern.getY()");
            }
            if (imagePattern.isProportional() != true) {
                reportGetterFailure("imagePattern.isProportional()");
            }

            rect.setFill(imagePattern);
            rect.setScaleX(0.75);
            rect.setScaleY(0.8);
            page.add(new SmallLeaf("resize", rect));
        }

        root.add(page = new PageWithSlots("ImagePattern with effects", height, width));
        {
            Rectangle rect = new Rectangle(50, 50);
            InputStream is = getClass().getResourceAsStream(defaultImagePath);
            rect.setFill(new ImagePattern(image, 0.0f, 0.0f, 0.5f, 0.5f, true));
            rect.setStyle("-fx-stroke: black;");
            page.add(new SmallLeaf("stroke", rect));
        }
        {
            Rectangle rect = new Rectangle(50, 50);
            InputStream is = getClass().getResourceAsStream(defaultImagePath);
            rect.setFill(new ImagePattern(image, 0.0f, 0.0f, 0.5f, 0.5f, true));
            rect.setStyle("-fx-effect: dropshadow(three-pass-box, black, 10, 0.5, 0, 0);");
            page.add(new SmallLeaf("dropshadow", rect));
        }

        return root;
    }

    private class SmallLeaf extends TestNodeLeaf {
        public SmallLeaf(String nodeName, Node content) {
            super(nodeName, content);
            setSize(100, 100);
        }
    }

    public static void main(String args[]) {
        test.javaclient.shared.Utils.launch(ImagesApp.class, args);
    }
}
