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
package test.scenegraph.functional.graphics;

import javafx.scene.Node;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import junit.framework.Assert;
import org.jemmy.control.Wrap;
import org.junit.Before;
import org.junit.BeforeClass;
import test.javaclient.shared.TestBase;
import test.scenegraph.app.LocalTo_TransformsApp;
import static org.jemmy.fx.Lookups.*;
import org.junit.Test;

/**
 *
 * @author asakharu
 */
public class LocalTo_TransformsTest extends TestBase
{

    @BeforeClass
    public static void runUI()
    {
        LocalTo_TransformsApp.main(null);
    }

    @Before
    @Override
    public void before()
    {
        super.before();
        circleOneWrap = byID(getScene(), "circle_one", Shape.class);
        circleTwoWrap = byID(getScene(), "circle_two", Shape.class);
        circleThreeWrap = byID(getScene(), "circle_three", Shape.class);
        circleFourWrap = byID(getScene(), "circle_four", Shape.class);
        realTranform.setMxx(1);
        realTranform.setMxy(0);
        realTranform.setMxz(0);
        realTranform.setMyx(0);
        realTranform.setMyy(1);
        realTranform.setMyz(0);
        realTranform.setMzx(0);
        realTranform.setMzy(0);
        realTranform.setMzz(1);
    }

    @Test
    public void sameParentAndSceneTransforms()
    {
        Node node = circleOneWrap.getControl();
        Transform localToScene = node.localToSceneTransformProperty().getValue();
        Transform localToParent = node.localToParentTransformProperty().getValue();

        realTranform.setTx(node.getTranslateX() + node.getLayoutX());
        realTranform.setTy(node.getTranslateY() + node.getLayoutY());
        realTranform.setTz(0);

        Assert.assertTrue(isTransformsEqual(localToParent, localToScene));
        Assert.assertTrue(isTransformsEqual(localToParent, realTranform));
    }

    @Test
    public void differentParentAndSceneTransforms()
    {
        Node node = circleTwoWrap.getControl();
        Transform localToScene = node.localToSceneTransformProperty().getValue();
        Transform localToParent = node.localToParentTransformProperty().getValue();

        realTranform.setTx(node.getTranslateX() + node.getLayoutX());
        realTranform.setTy(node.getTranslateY() + node.getLayoutY());
        realTranform.setTz(0);

        Assert.assertFalse(isTransformsEqual(localToParent, localToScene));
        Assert.assertTrue(isTransformsEqual(localToParent, realTranform));

        realTranform.setTx(node.getTranslateX() + node.getLayoutX() +
                node.getParent().getTranslateX() + node.getParent().getLayoutX());
        realTranform.setTy(node.getTranslateY() + node.getLayoutY() +
                node.getParent().getTranslateY() + node.getParent().getLayoutY());

        Assert.assertTrue(isTransformsEqual(localToScene, realTranform));
    }

    @Test
    public void doubleNestedNodeParentAndSceneTransforms()
    {
        Node node = circleThreeWrap.getControl();
        Transform localToScene = node.localToSceneTransformProperty().getValue();
        Transform localToParent = node.localToParentTransformProperty().getValue();

        realTranform.setTx(node.getTranslateX() + node.getLayoutX());
        realTranform.setTy(node.getTranslateY() + node.getLayoutY());
        realTranform.setTz(0);

        Assert.assertFalse(isTransformsEqual(localToParent, localToScene));
        Assert.assertTrue(isTransformsEqual(localToParent, realTranform));

        double tx = 0, ty = 0;
        for(Node n = node; n != null; n = n.getParent())
        {
            tx += n.getTranslateX() + n.getLayoutX();
            ty += n.getTranslateY() + n.getLayoutY();
        }
        realTranform.setTx(tx);
        realTranform.setTy(ty);

        Assert.assertTrue(isTransformsEqual(localToScene, realTranform));
    }

    @Test
    public void parentAndScene3DTransforms()
    {
        Node node = circleFourWrap.getControl();
        Transform localToScene = node.localToSceneTransformProperty().getValue();
        Transform localToParent = node.localToParentTransformProperty().getValue();

        realTranform.setTx(node.getTranslateX() + node.getLayoutX());
        realTranform.setTy(node.getTranslateY() + node.getLayoutY());
        realTranform.setTz(node.getTranslateZ());

        Assert.assertFalse(isTransformsEqual(localToParent, localToScene));
        Assert.assertTrue(isTransformsEqual(localToParent, realTranform));

        double tx = 0, ty = 0;
        for(Node n = node; n != null; n = n.getParent())
        {
            tx += n.getTranslateX() + n.getLayoutX();
            ty += n.getTranslateY() + n.getLayoutY();
        }
        realTranform.setTx(tx);
        realTranform.setTy(ty);

        Assert.assertTrue(isTransformsEqual(localToScene, realTranform));
    }

    private boolean isTransformsEqual(Transform first, Transform second)
    {
        return first.getMxx() == second.getMxx() &&
                first.getMxy() == second.getMxy() &&
                first.getMxz() == second.getMxz() &&
                first.getMyx() == second.getMyx() &&
                first.getMyy() == second.getMyy() &&
                first.getMyz() == second.getMyz() &&
                first.getMzx() == second.getMzx() &&
                first.getMzy() == second.getMzy() &&
                first.getMzz() == second.getMzz() &&
                first.getTx() == second.getTx() &&
                first.getTy() == second.getTy() &&
                first.getTz() == second.getTz();
    }

    Wrap<? extends Node> circleOneWrap, circleTwoWrap, circleThreeWrap, circleFourWrap;
    Affine realTranform = new Affine();

}
