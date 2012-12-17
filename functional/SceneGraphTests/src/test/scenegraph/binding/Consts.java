/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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
        new Lighting(),
        null
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
