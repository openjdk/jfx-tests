/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.fxapp;

import javafx.scene.control.Button;
import org.jemmy.fx.Lookups;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.Utils;

/**
 *
 * @author Sergey Grinev
 */
public class LifecycleStopTest extends TestBase {
    @BeforeClass
    public static void runUI() {
        LifecycleApp.reset();
        Utils.launch(LifecycleApp.class, LifecycleApp.DEFAULT_PARAMS);
    }

    @Test
    public void init() {
        Assert.assertFalse(LifecycleApp.status.start == -1);
        Assert.assertFalse(LifecycleApp.status.init == -1);
        Assert.assertTrue(LifecycleApp.status.stop == -1);
        final Wrap<? extends Button> closeBtn = Lookups.byText(scene, "close", Button.class);
        closeBtn.mouse().move();
        closeBtn.mouse().click();

        new Waiter(new Timeout("Closed state timeout", 5000)).ensureState(new State<Boolean>() {

            public Boolean reached() {
                return LifecycleApp.status.stop != -1 ? Boolean.TRUE : null;
            }
        });
    }

}
