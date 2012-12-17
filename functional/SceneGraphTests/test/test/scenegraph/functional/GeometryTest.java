/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional;

import javafx.scene.Node;
import javafx.scene.text.Text;
import junit.framework.Assert;
import org.jemmy.fx.ByID;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.app.GeometryApp;
import test.javaclient.shared.TestBase;


/**
 *
 * @author Sergey Grinev
 */
public class GeometryTest extends TestBase {
    
    @BeforeClass
    public static void runUI() {
        GeometryApp.main(null);
    }

    @Test
    public void bounds() throws InterruptedException {
        openPage("Bounds");

        //scene, "translate", Text.class
        Lookup<? extends Text> values = scene.as(Parent.class, Node.class).lookup(Text.class, new ByID<Text>("result"));
        values.wait(33);
        for (int i = 0; i < 33; i++) {
            Assert.assertTrue(Boolean.valueOf(values.wrap(i).getControl().getText()));

        }
    }
}
