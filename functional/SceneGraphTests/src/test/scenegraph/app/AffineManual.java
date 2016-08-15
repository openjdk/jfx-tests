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

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.transform.Affine;
import javafx.scene.transform.MatrixType;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

/**
 * All overloaded methods do the same as in {@link Affine} but with it's own implementation.
 *
 * @author Aleksandr Sakharuk
 */
public class AffineManual extends Affine
{

    /**
     * The same as {@link Affine#append(double mxx, double mxy, double tx, double myx, double myy, double ty)}
     */
    @Override
    public void append(double mxx, double mxy, double tx, double myx, double myy, double ty)
    {
    setMxx(getMxx() * mxx + getMxy() * myx);
    setMxy(getMxx() * mxy + getMxy() * myy);
    setMxz(getMxz());
    setTx(getMxx() * tx + getMxy() * ty + getTx());

    setMyx(getMyx() * mxx + getMyy() * myx);
    setMyy(getMyx() * mxy + getMyy() * myy);
    setMyz(getMyz());
    setTy(getMyx() * tx + getMyy() * ty + getTy());

    setMzx(getMzx() * mxx + getMzy() * myx);
    setMzy(getMzx() * mxy + getMzy() * myy);
    setMzz(getMzz());
    setTz(getMzx() * tx + getMzy() * ty + getTz());
    }

    /**
     * The same as {@link Affine#append(double mxx, double mxy, double mxz, double tx, double myx,
        double myy, double myz, double ty, double mzx, double mzy, double mzz, double tz)}
     */
    @Override
    public void append(double mxx, double mxy, double mxz, double tx, double myx,
        double myy, double myz, double ty, double mzx, double mzy, double mzz, double tz)
    {
    double xx = getMxx() * mxx + getMxy() * myx + getMxz() * mzx;
    double xy = getMxx() * mxy + getMxy() * myy + getMxz() * mzy;
    double xz = getMxx() * mxz + getMxy() * myz + getMxz() * mzz;
    double trX = getMxx() * tx + getMxy() * ty + getMxz() * tz + getTx();

    double yx = getMyx() * mxx + getMyy() * myx + getMyz() * mzx;
    double yy = getMyx() * mxy + getMyy() * myy + getMyz() * mzy;
    double yz = getMyx() * mxz + getMyy() * myz + getMyz() * mzz;
    double trY = getMyx() * tx + getMyy() * ty + getMyz() * tz + getTy();

    double zx = getMzx() * mxx + getMzy() * myx + getMzz() * mzx;
    double zy = getMzx() * mxy + getMzy() * myy + getMzz() * mzy;
    double zz = getMzx() * mxz + getMzy() * myz + getMzz() * mzz;
    double trZ = getMzx() * tx + getMzy() * ty + getMzz() * tz + getTz();

        setMxx(xx);
        setMxy(xy);
        setMxz(xz);
        setTx(trX);

        setMyx(yx);
        setMyy(yy);
        setMyz(yz);
        setTy(trY);

        setMzx(zx);
        setMzy(zy);
        setMzz(zz);
        setTz(trZ);
    }

    /**
     * The same as {@link Affine#append(Transform transform)}
     */
    @Override
    public void append(Transform transform)
    {
        append(transform.getMxx(), transform.getMxy(), transform.getMxz(), transform.getTx(),
                transform.getMyx(), transform.getMyy(), transform.getMyz(), transform.getTy(),
                transform.getMzx(), transform.getMzy(), transform.getMzz(), transform.getTz());
    }

    /**
     * The same as {link Affine#append(double[] matrix, MatrixType type, int offset)}
     */
    @Override
    public void append(double[] matrix, MatrixType type, int offset)
    {
        switch(type)
        {
            case MT_2D_2x3:
            case MT_2D_3x3:
                append(matrix[offset], matrix[offset + 1], matrix[offset + 2],
                        matrix[offset + 3], matrix[offset + 4], matrix[offset + 5]);
                break;
            case MT_3D_3x4:
            case MT_3D_4x4:
                append(matrix[offset], matrix[offset + 1], matrix[offset + 2], matrix[offset + 3],
                        matrix[offset + 4], matrix[offset + 5], matrix[offset + 6], matrix[offset + 7],
                        matrix[offset + 8], matrix[offset + 9], matrix[offset + 10], matrix[offset + 11]);
                break;
        }
    }

    /**
     * The same as {link Affine#appendRotation(double angle)}
     */
    @Override
    public void appendRotation(double angle)
    {
        append(new Rotate(angle));
    }

    @Override
    public void appendRotation(double andle, double pivatX, double pivotY)
    {
        append(new Rotate(andle, pivatX, pivotY));
    }

    @Override
    public void appendRotation(double angle, Point2D pivot)
    {
        append(new Rotate(angle, pivot.getX(), pivot.getY()));
    }

    @Override
    public void appendRotation(double angle, double pivotX, double pivotY, double pivotZ,
            double axisX, double axisY, double axisZ)
    {
        append(new Rotate(angle, pivotX, pivotY, pivotZ, new Point3D(axisX, axisY, axisZ)));
    }

    @Override
    public void appendRotation(double angle, double pivotX, double pivotY, double pivotZ, Point3D axis)
    {
        append(new Rotate(angle, pivotX, pivotY, pivotZ, axis));
    }

    @Override
    public void appendRotation(double angle, Point3D pivot, Point3D axis)
    {
        append(new Rotate(angle, pivot.getX(), pivot.getY(), pivot.getZ(), axis));
    }

}
