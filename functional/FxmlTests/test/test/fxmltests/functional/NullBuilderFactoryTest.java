/*
 * Copyright (c) 2014, 2023, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.fxmltests.functional;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.junit.Test;
import test.fxmltests.app.NullBuilderFactoryApp;
import test.javaclient.shared.Utils;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public class NullBuilderFactoryTest {

    @Test(timeout = 10000)
    public void testFxmlLoaderUsingDefaultBuilderFactoryWhenSetToNull() {
        Utils.launch(NullBuilderFactoryApp.class, null);
        Utils.waitFor(() -> {
            try {
                Node imageView =  NullBuilderFactoryApp
                        .getStage()
                        .getScene()
                        .lookup("#image");
                return ((ImageView)imageView).getImage() != null;
            } catch (NullPointerException npe) {
                return false;
            }
        });
    }
}
