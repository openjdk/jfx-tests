/*
 * Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.scenegraph.app;

import test.javaclient.shared.CombinedTestChooserPresenter;
import test.javaclient.shared.CombinedTestChooserPresenter3D;
import test.javaclient.shared.Utils;

/**
 *
 * @author shubov
 */
public class Effects3DApp extends Effects2App {

    Effects2App effects2App;
    public Effects3DApp() {
        super(600, 650, "Effects3D", false);
    }

    public static void main(String args[]) {
        Utils.launch(Effects3DApp.class, args);
    }

    @Override
    protected CombinedTestChooserPresenter createCombinedTestChooserPresenter(int width, int height, String title, boolean showButtons) {
        return new CombinedTestChooserPresenter3D(width, height, title, getButtonsPane());
    }
    
    @Override
    protected void initPredefinedFont() {
    }


}
