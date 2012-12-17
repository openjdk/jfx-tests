/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.imageops;

import javafx.scene.image.Image;

/**
 *
 * @author Alexander Petrov
 */
public class ReadImageBlueTest extends ReadImageTestBase{

    @Override
    protected ColorComponentProvider getColorComponentProvider() {
        return ColorComponents.Blue;
    }

    @Override
    protected Image getImage() {
        return new Image(getColorComponentProvider().getGoldImagePath());
    }
}
