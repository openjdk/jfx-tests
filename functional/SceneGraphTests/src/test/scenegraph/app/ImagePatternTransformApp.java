/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.app;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Shear;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import test.javaclient.shared.*;
import test.scenegraph.app.ImagePatternApp.Leaf;
import test.scenegraph.app.ImagePatternApp.Shapes;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class ImagePatternTransformApp extends BasicButtonChooserApp
{
    
    public ImagePatternTransformApp()
    {
        super(800, 600, "ImagePatternTransforms", false);
    }
    
    public static void main(String[] args) 
    {
        Utils.launch(ImagePatternTransformApp.class, args);
    }

    @Override
    protected TestNode setup() 
    {
        final TestNode root = new TestNode();
        PageWithSlots page;
        image = new Image(getClass().getResourceAsStream("/test/scenegraph/resources/square.png"));
        
        for(Shapes shape: Shapes.values())
        {
            page = new PageWithSlots(shape.toString(), 800, 600);
            page.setSlotSize(200, 200);
            root.add(page);
            Shape sh;
            
            for(TransformCommand tc: TransformCommand.values())
            {
                sh = shape.getShape();
                sh.setFill(new ImagePattern(image, 0, 0, 0.5, 0.5, true));
                tc.transformShape(sh);
                ImagePatternApp.Leaf leaf = new ImagePatternApp.Leaf(tc.toString(), sh);
                leaf.setSize(200, 200);
                page.add(leaf);
            }
        }
        
        return root;
    }
    
    private Image image;
    
    public enum TransformCommand
    {
        
        TRANSLATE(new Translate(15, 20)),
        SCALE(new Scale(1.1, 0.9)),
        ROTATE(new Translate(80, 0), new Rotate(45)),
        SHEAR(new Shear(0.1, 0.2)),
        AFFINE(new Translate(30, 10), Transform.affine(0.2, 0.8, .9, 0.1, 1, 0.1))
        ;
        
        private TransformCommand(Transform... transforms)
        {
            this.transforms = transforms;
        }
        
        public void transformShape(Shape shape)
        {
            shape.getTransforms().addAll(transforms);
        }
        
        private Transform[] transforms;
    }
    
}
