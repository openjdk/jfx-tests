/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved. DO NOT
 * ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package org.jemmy.fx;

import javafx.scene.Node;
import org.jemmy.Rectangle;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.lookup.RelativeCoordinateLookup;

/**
 *
 * @author Andrey Nazarov
 */
public class FXRelativeCoordinateLookup<CONTROL extends Node> extends RelativeCoordinateLookup<CONTROL> {

    public FXRelativeCoordinateLookup(Wrap wrap, boolean includeControl, int hr, int vr) {
        super(wrap, includeControl, hr, vr);
    }

    @Override
    protected Rectangle getBounds(CONTROL control) {
        return new NodeWrap<CONTROL>(Environment.getEnvironment(), control).getScreenBounds();
    }
    
}
