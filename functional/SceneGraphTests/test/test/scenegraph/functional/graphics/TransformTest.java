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

import test.javaclient.shared.TestBase;
import org.junit.Before;
import javafx.scene.transform.Translate;
import javafx.scene.transform.Shear;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import org.jemmy.Point;
import org.jemmy.TimeoutExpiredException;
import org.jemmy.timing.State;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.*;
import javax.transaction.TransactionRequiredException;
import org.jemmy.control.Wrap;
import org.junit.BeforeClass;
import test.scenegraph.app.TransformsApp;
import static org.jemmy.fx.Lookups.*;

/**
 *
 * @author Sergey Grinev
 */
public class TransformTest extends TestBase {

    static Wrap<? extends Rectangle> rect;
    static Wrap<? extends Text> labelX;
    static Wrap<? extends Text> labelY;
    static Wrap<? extends Text> clear;
    static List<Wrap<? extends Text>> buttons;
    static Wrap<? extends Text> buttonAffine;

    //@RunUI
    @BeforeClass
    public static void RunUI() {
        TransformsApp.main(null);
    }

    @Before
    @Override
    public void before() {
        super.before();
        rect = byID(getScene(), "the rectangle", Rectangle.class);
        labelX = byID(getScene(), "X", Text.class);
        labelY = byID(getScene(), "Y", Text.class);
        clear = byText(getScene(), "clear", Text.class);
        buttons = new ArrayList<Wrap<? extends Text>>();
        buttons.add(byText(getScene(), "scale", Text.class));
        buttons.add(byText(getScene(), "rotate", Text.class));
        buttons.add(byText(getScene(), "shear", Text.class));
        buttons.add(byText(getScene(), "translate", Text.class));
        buttonAffine = byText(getScene(), "affine", Text.class);
        tt = new boolean[buttons.size()+2];
    }
    private boolean[] tt;
    private boolean ttAffine = false;
    private Scale scale = new Scale(.33f, .66f, 100, 100);
    private Rotate rotate = new Rotate(45f, 100, 100);
    private Shear shear = new Shear(-.33f, .1f, 80, 70);
    private Translate translate = new Translate(50, 50);
    private double mxx = 0.2, mxy = 0.9, myx = 0.8, myy = 0.1, mxt = 1, myt = 0.1;
    private Affine affine = Transform.affine(mxx, myx, mxy, myy, mxt, myt);



    protected Point scale(final Point p) {
        Point scaled = new Point(
                getScale().getPivotX() + (p.x - getScale().getPivotX())*getScale().getX(),
                getScale().getPivotY() + (p.y - getScale().getPivotY())*getScale().getY());
        return scaled;
    }

    protected Point rotate(final Point p) {
        double x1 = p.x - getRotate().getPivotX();
        double y1 = p.y - getRotate().getPivotY();
        double angle = (double) ((90-getRotate().getAngle()) * Math.PI / 180f);
        double x2 = (double) (x1 * Math.cos(angle) - y1 * Math.sin(angle));
        double y2 = (double) (x1 * Math.sin(angle) + y1 * Math.cos(angle));
        Point result = new Point(
                getRotate().getPivotX() + x2,
                getRotate().getPivotY() + y2);
        return result;
    }

    protected Point shear(final Point p) {
        Point result = new Point(
                p.x + (getShear().getX() * (p.y - getShear().getPivotY())),
                p.y + (getShear().getY() * (p.x - getShear().getPivotX()))
                );
        return result;
    }

    protected Point translate(final Point p) {
        Point result = new Point(
                p.x + getTranslate().getX(),
                p.y + getTranslate().getY()
                );
        return result;
    }

    //                              mxx myx  mxy myy  tx   ty
    //Affine aff = Translate.affine(0.2, 0.8, .9, 0.1, 1, 0.1);

    protected Point affine(final Point p) {
        Point result = new Point (
                getAffine().getMxx()*p.x + getAffine().getMxy()*p.y + getAffine().getTx(),
                getAffine().getMyx()*p.x + getAffine().getMyy()*p.y + getAffine().getTy()
                );
        return result;
    }

    private void checkLabel(final Wrap<? extends Text> label, final int value) {
        label.waitState(new State<Boolean>() {
            @Override
            public Boolean reached() {
                String labelValue = label.getControl().getText();
                if (Math.abs(Integer.parseInt(labelValue) - value) <= 7) {
                    return true;
                }
                System.err.println("reached: " + labelValue + " instead of " + value);
                return null;
            }
        });
    }

    private void checkLabels(int x, int y) {
        try {
            checkLabel(labelX, x);
            checkLabel(labelY, y);
        } catch (TimeoutExpiredException e) {
            System.out.println("Coordinates: (" + labelX.getControl().getText() + "," + labelY.getControl().getText() + ")");
            throw e;
        }
    }

    private void click(Point p) throws InterruptedException {
        clear.mouse().click();
        checkLabels(0, 0);
        if (p != null) {
            rect.mouse().click(1, p);
            checkLabels(p.x, p.y);
        } else {
            rect.mouse().click();
            checkLabels(50, 50);
        }
    }

    static Point[] pnts = {
        new Point(25, 75), new Point(50, 50), new Point(75, 25),
        new Point(25, 25), new Point(75, 75), null
    };


    private void clickPoints()  throws InterruptedException {
        for (Point p : pnts) {
            System.out.println("Clicking at " + p + "");
            click(p); // verify jemmy fx coordinates
            click2(p);// verify transforms are right
        }

    }

    // recursive loop to combine all transforms
    public void select(int index, boolean req) throws InterruptedException {
        for (boolean state : new Boolean[]{true, false}) {
            tt[index] = state;
            buttons.get(index).mouse().click();
            if (req && index < buttons.size() - 1) {
                select(index + 1, req);
            } else {
                clickPoints();
            }
        }
    }

    @Test
    public void all() throws InterruptedException {
       select(0, true);
    }

    @Test
    public void affine() throws InterruptedException {
       buttonAffine.mouse().click();
       ttAffine = true;
       clickPoints();
       ttAffine = false;
       buttonAffine.mouse().click();
    }

    private void click2(Point p) throws InterruptedException {
        clear.mouse().click();
        checkLabels(0, 0);
        if (p != null) {
            clickGlobal(p);
            checkLabels(p.x, p.y);
        }
    }

    private void clickGlobal(Point p) {
        Point point = new Point(p);
        point.translate(150, 100);

        // transforms are applied in reversed order for unknown FX reason
        if (ttAffine) {
            point = affine(point);
        }
        if (tt[3]) {
            point = translate(point);
        }
        if (tt[2]) {
            point = shear(point);
        }
        if (tt[1]) {
            point = rotate(point);
        }
        if (tt[0]) {
            point = scale(point);
        }

        getScene().mouse().click(1, point);
    }

    //@Test
    public void debug() throws Exception {
        Point p = pnts[2];
        com.sun.javafx.geom.Rectangle r = new com.sun.javafx.geom.Rectangle(100, 100, 100, 100);
        Point point = new Point(p);
        point.translate(r.x, r.y);

        clear.mouse().click();
        buttons.get(0).mouse().click();
        getScene().mouse().click(1, (((rotate(point)))));
        checkLabels(p.x, p.y);
        clear.mouse().click();
        buttons.get(1).mouse().click();
        getScene().mouse().click(1, ((rotate(scale(point)))));
        checkLabels(p.x, p.y);
        clear.mouse().click();
        buttons.get(2).mouse().click();
        getScene().mouse().click(1, (scale(rotate(shear(point)))));
        checkLabels(p.x, p.y);
        clear.mouse().click();
        buttons.get(3).mouse().click();
        getScene().mouse().click(1, scale(rotate(shear(translate(point)))));
        checkLabels(p.x, p.y);

        Thread.sleep(100);
        System.err.println("done");
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(Scale scale) {
        this.scale = scale;
    }

    /**
     * @param rotate the rotate to set
     */
    public void setRotate(Rotate rotate) {
        this.rotate = rotate;
    }

    /**
     * @param shear the shear to set
     */
    public void setShear(Shear shear) {
        this.shear = shear;
    }

    /**
     * @param translate the translate to set
     */
    public void setTranslate(Translate translate) {
        this.translate = translate;
    }

    /**
     * @param affine the affine to set
     */
    public void setAffine(Affine affine) {
        this.affine = affine;
    }

    /**
     * @return the scale
     */
    public Scale getScale() {
        return scale;
    }

    /**
     * @return the rotate
     */
    public Rotate getRotate() {
        return rotate;
    }

    /**
     * @return the shear
     */
    public Shear getShear() {
        return shear;
    }

    /**
     * @return the translate
     */
    public Translate getTranslate() {
        return translate;
    }

    /**
     * @return the affine
     */
    public Affine getAffine() {
        return affine;
    }

}
