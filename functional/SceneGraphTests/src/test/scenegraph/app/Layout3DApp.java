/*
 * Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.scenegraph.app;

import test.javaclient.shared.CombinedTestChooserPresenter;
import test.javaclient.shared.CombinedTestChooserPresenter3D;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author shubov
 */
public class Layout3DApp extends Layout2App {

    public Layout3DApp() {
        super(1000, 760, "Layout3D", false);
    }

    public static void main(String args[]) {
        Utils.launch(Layout3DApp.class, args);
    }

    @Override
    public TestNode setup() {
        setZOffset(10);
        return super.setup();
    }

    @Override
    protected CombinedTestChooserPresenter createCombinedTestChooserPresenter(int width, int height, String title, boolean showButtons) {
        return new CombinedTestChooserPresenter3D(width, height, title, getButtonsPane());
    }


}
