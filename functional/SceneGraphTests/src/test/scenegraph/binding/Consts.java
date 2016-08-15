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
package test.scenegraph.binding;

import java.util.Arrays;
import javafx.scene.effect.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

/**
 *
 * @author Sergey Grinev
 */
public class Consts {

    static final LinearGradient GRAD = new LinearGradient(0, 0, 1, 2, true, CycleMethod.REPEAT,
            new Stop[]{new Stop(0, Color.AQUA), new Stop(0.5f, Color.RED)});
    static final Object[] PAINTS = new Object[]{Color.RED, Color.ORANGE, Color.YELLOW,
        Color.GREEN, Color.BLUE, Color.LIGHTBLUE, Color.VIOLET,
        Color.WHITE, Color.BLACK, Color.DARKGRAY, Color.TRANSPARENT, GRAD};
    static final Object[] COLORS = Arrays.copyOfRange(PAINTS, 0, PAINTS.length - 1);
    static final Object[] EFFECTS = new Object[]{
        new InnerShadow(10, Color.YELLOW),
        new DropShadow(),
        new GaussianBlur(),
        new Glow(),
        new Lighting()
    };
    final static FloatMap MAP_WAVES = new FloatMap();
    final static FloatMap MAP_WAVES_2 = new FloatMap();

    static {
        MAP_WAVES.setWidth(100);
        MAP_WAVES.setHeight(80);
        for (int i = 0; i < MAP_WAVES.getWidth() - 1; i++) {
            float v = (float) ((Math.sin(i / 30f * Math.PI) - 0.5f) / 20f);
            for (int j = 0; j < MAP_WAVES.getHeight() - 1; j++) {
                float u = (float) ((Math.sin(j / 30f * Math.PI) - 0.5f) / 20f);
                MAP_WAVES.setSamples(i, j, u, v);
            }
        }

        MAP_WAVES_2.setWidth(100);
        MAP_WAVES_2.setHeight(80);
        for (int i = 0; i < MAP_WAVES_2.getWidth() - 1; i++) {
            float v = (float) ((Math.cos(i / 30f * Math.PI) - 0.5f) / 20f);
            for (int j = 0; j < MAP_WAVES_2.getHeight() - 1; j++) {
                float u = (float) ((Math.cos(j / 30f * Math.PI) - 0.5f) / 20f);
                MAP_WAVES_2.setSamples(i, j, u, v);
            }
        }
    }
}
