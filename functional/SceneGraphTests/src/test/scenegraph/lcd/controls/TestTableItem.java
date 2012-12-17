/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.controls;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Alexander Petrov
 */
public class TestTableItem {

    StringProperty test = new SimpleStringProperty("Test");

    public String getTest() {
        return test.get();
    }

    public void setTest(String test) {
        this.test = new SimpleStringProperty(test);
    }

    public StringProperty testProperty() {
        return this.test;
    }
};