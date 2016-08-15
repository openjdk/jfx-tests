/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
 */
package com.oracle.jdk.sqe.cc.markup;

import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author shura
 */
public class CoverageUnitTest {

    public CoverageUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    CoverageUnit instance;

    @Before
    public void setUp() {
        instance = new CoverageUnit("feature1", "class1", Level.LOW);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addSourceUnit method, of class CoverageUnit.
     */
    @Test
    public void testAddSourceUnit() {
        instance.addSourceUnit("class2");
        assertEquals(2, instance.sourceUnits.size());
        assertEquals("class2", instance.sourceUnits.get(instance.sourceUnits.size() - 1));
        instance.addSourceUnit("class2");
        assertEquals(2, instance.sourceUnits.size());
    }

    /**
     * Test of merge method, of class CoverageUnit.
     */
    @Test
    public void testMerge() {
        CoverageUnit another = new CoverageUnit("feature1", "class2", Level.FULL);
        instance.merge(another);
        assertEquals(2, instance.sourceUnits.size());
        assertEquals("class2", instance.sourceUnits.get(instance.sourceUnits.size() - 1));
        assertEquals(Level.FULL, instance.level);
    }

    @Test
    public void testMergeInvalid() {
        CoverageUnit another = new CoverageUnit("feature2", "class1", Level.LOW);
        try {
            instance.merge(another);
            fail("Must throw an exception");
        } catch(Exception e) {
        }
    }
}
