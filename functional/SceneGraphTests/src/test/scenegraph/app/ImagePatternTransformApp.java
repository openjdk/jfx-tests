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
