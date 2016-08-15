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
package test.scenegraph.fx3d.depth;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import org.junit.Assert;
import test.scenegraph.fx3d.utils.GroupMover;

/**
 *
 * @author Andrey Glushchenko
 */
public abstract class BaseCase extends Group {

    private PhongMaterial material[] = new PhongMaterial[]{
        new PhongMaterial(Color.RED),
        new PhongMaterial(Color.BLUE),
        new PhongMaterial(Color.GREEN)
};
    protected GroupMover rb;
    protected Shape3D[] shapes;

    public BaseCase(){
        shapes = getShapes();
        if(shapes.length>3){
            Assert.fail("This class represents API to view <=3 shapes. Currently shapes: " + shapes.length);
        }
        for(int i=0; i<shapes.length; i++){
            shapes[i].setMaterial(material[i]);
        }

        rb = new GroupMover(shapes);

        this.getChildren().add(rb.getGroup());
    }

    public GroupMover getRotationBox(){
        return rb;
    }

    protected abstract Shape3D[] getShapes();

}
