/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.imageops;

import javafx.scene.paint.Color;

/**
 *
 * @author Alexander Petrov
 */
public interface ColorComponentProvider {
    public double getComponent(Color color);
    public double getComponent(int sRGBColor);
    
    public Color getColor(double componentValue);
    public int getARGBColor(int componentValue);
    
    public String getGoldImagePath();
}
