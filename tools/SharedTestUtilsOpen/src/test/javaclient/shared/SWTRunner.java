/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.javaclient.shared;

import org.junit.runners.model.InitializationError;

public class SWTRunner extends OtherThreadRunner {

    public SWTRunner(Class testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    public boolean runOtherThread() {
        return Boolean.getBoolean("javafx.swtinteroperability") && Utils.isMacOS();
    }
}
