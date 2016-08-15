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
package test.scenegraph.fx3d.utils;

import java.nio.ByteBuffer;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.LightBase;
import javafx.scene.PointLight;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

/**
 *
 * @author Andrey Glushchenko
 */
public class VisibleLight extends Group {

    public enum LightType {

        Ambient, Point
    }
    private PhongMaterial material;
    private LightBase lightBase;
    private LightType type;

    private void makeConstantImage(WritableImage img, Color color) {
        int w = (int) img.getWidth();
        int h = (int) img.getHeight();
        ByteBuffer scanline = ByteBuffer.allocate(3 * w);
        byte r = (byte) ((int) Math.round(color.getRed() * 255.0));
        byte g = (byte) ((int) Math.round(color.getGreen() * 255.0));
        byte b = (byte) ((int) Math.round(color.getBlue() * 255.0));
        for (int i = 0; i < w; i++) {
            scanline.put(r);
            scanline.put(g);
            scanline.put(b);
        }
        scanline.rewind();
        img.getPixelWriter().setPixels(0, 0, w, w, PixelFormat.getByteRgbInstance(), scanline, 0);
    }

    public VisibleLight(Color color) {
        this(color, LightType.Point);
    }

    public VisibleLight(Color color, LightType lt) {
        type = lt;
        if (lt.equals(LightType.Point)) {
            lightBase = new PointLight();
        } else {
            lightBase = new AmbientLight();
        }
        material = new PhongMaterial();
        material.setDiffuseMap(null);
        material.setBumpMap(null);
        material.setSpecularMap(null);
        material.setSpecularColor(Color.TRANSPARENT);
        setColor(color);

        final Sphere light = new Sphere(50);
        light.setScaleX(0.2);
        light.setScaleY(0.2);
        light.setScaleZ(0.2);
        light.setMaterial(material);

        this.getChildren().addAll(lightBase, light);
    }
    public AmbientLight getAmbient(){
        if(type==LightType.Point){
            return null;
        }
        return (AmbientLight)lightBase;
    }
    public PointLight getPoint(){
        if(type==LightType.Ambient){
            return null;
        }
        return (PointLight)lightBase;
    }
    public LightBase getBase(){
        return lightBase;
    }

    public void setLightType(LightType lt) {
        this.getChildren().remove(lightBase);
        if (lt.equals(LightType.Point)) {
            lightBase = new PointLight(lightBase.getColor());
        } else {
            lightBase = new AmbientLight(lightBase.getColor());
        }

        this.getChildren().add(lightBase);
    }

    public final void setColor(Color color) {
        lightBase.setColor(color);
        WritableImage selfIllumMap = new WritableImage(64, 64);
        makeConstantImage(selfIllumMap, color);
        material.setSelfIlluminationMap(selfIllumMap);
    }
}
