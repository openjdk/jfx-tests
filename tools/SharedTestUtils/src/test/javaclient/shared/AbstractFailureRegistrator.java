/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.javaclient.shared;

/**
 *
 * @author shubov
 */
public interface AbstractFailureRegistrator {

    void registerFailure(final String text);
    void clearFailures();
    String getFailures();
}
