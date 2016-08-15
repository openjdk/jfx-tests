/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.sun.fx.webnode.tests.bridge.javascript2java.helpers;

/**
 * A helper class for checking bound objects behavior.
 * @author Irina Grineva
 */
public class HelperObject {

    public String stringField;
    public String unicodeField;
    public int intField;
    public boolean booleanField;
    public double doubleField;
    public float floatField;
    public char charField;
    public Object objectField;

    public HelperObject(String stringField, int intField, boolean booleanField, double doubleField, float floatField, char charField, Object objectField, String unicodeField) {
        this.stringField = stringField;
        this.unicodeField = unicodeField;
        this.intField = intField;
        this.booleanField = booleanField;
        this.doubleField = doubleField;
        this.floatField = floatField;
        this.charField = charField;
        this.objectField = objectField;
    }

    public int publicField = 0;
    protected int protectedField = 1;
    private int privateField = 2;
    public static int staticField = 3;

    public HelperObject(String s) {
            stringField = s;
    }

    public HelperObject() {

    }

    public int doSomethingPublic() {
        return publicField;
    }

    protected int doSomethingProtected() {
        return protectedField;
    }

    private int doSomethingPrivate() {
        return privateField;
    }

    public static int doSomethingStatic() {
        return staticField;
    }

    public static int doSomethingStatic2() {
        return 42;
    }

    public static String doSomethingStatic3() {
        return "Test!";
    }

    // Do not swap with next doSomehingOverloaded1 !!!
    public int doSomethingOverloaded1(String a) {
        return 1;
    }
    // Do not swap with previous doSomehingOverloaded1 !!!
    public int doSomethingOverloaded1(int a) {
        return a + 10;
    }
    // Do not swap with next doSomehingOverloaded2 !!!
    public int doSomethingOverloaded2(float a) {
        return 3;
    }
    // Do not swap with previous doSomehingOverloaded2 !!!
    public int doSomethingOverloaded2(double a) {
        return 4;
    }

    public int doParamImportant(int param) {
        return param + 10;
    }

    public Exception e = new Exception("I throw up!");
    public void iThrowException() throws Exception {
        throw e;
    }

    public SpecialException e2 = new SpecialException();
    public void iThrowException2() throws SpecialException {
        throw e2;
    }

    public String doString(String s) {
        return s;
    }

    public int doInt(int i) {
        return i;
    }

    public boolean doBoolean(boolean b) {
        return b;
    }

    public double doDouble(double d) {
        return d;
    }

    public double doFloat(float f) {
        return f;
    }

    public char doChar(char c) {
        return c;
    }

    public Object doObject(Object o) {
        return o;
    }

    public void justDoIt() {
        // Done :)
    }
}
