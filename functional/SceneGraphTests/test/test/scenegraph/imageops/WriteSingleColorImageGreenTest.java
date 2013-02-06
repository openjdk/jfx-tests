/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.imageops;

/**
 *
 * @author Alexander Petrov
 */
public class WriteSingleColorImageGreenTest extends WriteSingleColorImageTestBase{

    @Override
    protected ColorComponentProvider getColorComponentProvider() {
        return ColorComponents.Green;
    }
}
