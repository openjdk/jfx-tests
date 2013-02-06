/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.scenegraph.app;

import javafx.animation.Interpolator;

/**
 *
 * @author shubov
 */


/**
 *
 * test "custom interpolator"
 */
public class AnimationBooleanInterpolator extends Interpolator {
    protected double curve(double t) {
        if (((t > 0.2d )&& (t<0.3d)) ||
                ((t > 0.4d )&& (t<0.5d)) ||
                ((t > 0.6d )&& (t<0.7d)) ||
                ((t > 0.8d )&& (t<0.9d)))
            return 1;
        return 0;
    }


}
