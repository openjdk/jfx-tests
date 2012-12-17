/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.app;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import test.javaclient.shared.*;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class ImagePatternApp extends BasicButtonChooserApp
{
    
    public ImagePatternApp()
    {
        super(600, 400, "ImagePatterns", false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Utils.launch(ImagePatternApp.class, args);
    }
    
    @Override
    protected TestNode setup() 
    {
        final TestNode root = new TestNode();
        PageWithSlots page;
        image = new Image(getClass().getResourceAsStream("/test/scenegraph/resources/square.png"));
        
        for(Shapes shape: Shapes.values())
        {
            page = new PageWithSlots(shape.toString(), 500, 500);
            page.setSlotSize(150, 150);
            root.add(page);
            Shape sh;
            
            sh = shape.getShape();
            sh.setFill(new ImagePattern(image, 0, 0, 0, 0, true));
            page.add(new Leaf("size0", sh));
            
            sh = shape.getShape();
            sh.setFill(new ImagePattern(image, 0, 0, 0.5, 0.5, true));
            page.add(new Leaf("size0.5", sh));
            
            sh = shape.getShape();
            sh.setFill(new ImagePattern(image, 0, 0, 1, 1, true));
            page.add(new Leaf("size1", sh));
            
            sh = shape.getShape();
            sh.setFill(new ImagePattern(image, 0, 0, 1.5, 1.5, true));
            page.add(new Leaf("size1.5", sh));
            
            sh = shape.getShape();
            sh.setFill(new ImagePattern(image, 0, 0, 2, 2, true));
            page.add(new Leaf("size2", sh));
        }
        
        for(Shapes shape: Shapes.values())
        {
            page = new PageWithSlots(shape.toString() + "(Nonproportional)", 500, 500);
            page.setSlotSize(150, 150);
            root.add(page);
            Shape sh;
            
            sh = shape.getShape();
            Bounds bounds = sh.getBoundsInLocal();
            sh.setFill(new ImagePattern(image, bounds.getMinX(), bounds.getMinY(), 0, 0, false));
            page.add(new Leaf("size0", sh));
            
            sh = shape.getShape();
            sh.setFill(new ImagePattern(image, bounds.getMinX(), bounds.getMinY(), bounds.getWidth() * 0.5, bounds.getHeight() * 0.5, false));
            page.add(new Leaf("size0.5", sh));
            
            sh = shape.getShape();
            sh.setFill(new ImagePattern(image, bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), false));
            page.add(new Leaf("size1", sh));
            
            sh = shape.getShape();
            sh.setFill(new ImagePattern(image, bounds.getMinX(), bounds.getMinY(), bounds.getWidth() * 1.5, bounds.getHeight() * 1.5, false));
            page.add(new Leaf("size1.5", sh));
            
            sh = shape.getShape();
            sh.setFill(new ImagePattern(image, bounds.getMinX(), bounds.getMinY(), bounds.getWidth() * 2, bounds.getHeight() * 2, false));
            page.add(new Leaf("size2", sh));
        }
        
        page = new PageWithSlots("Formats", 500, 500);
        page.setSlotSize(150, 150);
        root.add(page);
        Shape sh;
        
        sh = Shapes.RECTANGLE.getShape();
        image = new Image(getClass().getResourceAsStream("/test/scenegraph/resources/square.png"));
        sh.setFill(new ImagePattern(image, 0, 0, 0.5, 0.5, true));
        page.add(new Leaf("png", sh));
        
        sh = Shapes.RECTANGLE.getShape();
        image = new Image(getClass().getResourceAsStream("/test/scenegraph/resources/square.jpg"));
        sh.setFill(new ImagePattern(image, 0, 0, 0.5, 0.5, true));
        page.add(new Leaf("jpg", sh));
        
        sh = Shapes.RECTANGLE.getShape();
        image = new Image(getClass().getResourceAsStream("/test/scenegraph/resources/square.bmp"));
        sh.setFill(new ImagePattern(image, 0, 0, 0.5, 0.5, true));
        page.add(new Leaf("bmp", sh));
        
        sh = Shapes.RECTANGLE.getShape();
        image = new Image(getClass().getResourceAsStream("/test/scenegraph/resources/square.gif"));
        sh.setFill(new ImagePattern(image, 0, 0, 0.5, 0.5, true));
        page.add(new Leaf("gif", sh));
        
//        sh = Shapes.RECTANGLE.getShape();
//        image = new Image("file:resources/test/scenegraph/resources/loading/manual/animated/rotating_earth.gif");
//        sh.setFill(new ImagePattern(image, 0, 0, 0.5, 0.5, true));
//        page.add(new Leaf("gif-animated", sh));
        
        
        return root;
    }

    private Image image;
    
    public interface ShapeFactory
    {
        Shape createShape();
    }
    
    public enum Shapes
    {
        CIRCLE(new ShapeFactory() {

            public Shape createShape() 
            {
                return  new Circle(55, 55, 50);
            }
        }),
        ELLIPSE(new ShapeFactory() {

            public Shape createShape() 
            {
                return new Ellipse(55, 55, 50, 35);
            }
        }),
        POLYGON(new ShapeFactory() {

            public Shape createShape() 
            {
                return new Polygon(50, 20, 
                                   30, 50, 
                                   70, 80, 
                                   110, 50, 
                                   90, 20);
            }
        }),
        RECTANGLE(new ShapeFactory() {

            public Shape createShape() 
            {
                return new Rectangle(5, 5, 100, 100);
            }
        }),
        TEXT(new ShapeFactory() {

            public Shape createShape() 
            {
                Text text = new Text(50, 50, "Text");
                text.setStyle("-fx-font-size: 40; -fx-font-family: sans-serif; -fx-font-weight: bold;");
                text.setBoundsType(TextBoundsType.VISUAL);
                return text;
            }
        })
        ;

        private Shapes(ShapeFactory factory) 
        {
            this.factory = factory;
        }
        
        public Shape getShape()
        {
            return factory.createShape();
        }
        
        private ShapeFactory factory;
    }
    
    public static class Leaf extends TestNodeLeaf
    {
       
        public Leaf(String nodeName, Node content)
        {
            super(nodeName, content);
            setSize(150, 150);
        }
        
    }
    
}
