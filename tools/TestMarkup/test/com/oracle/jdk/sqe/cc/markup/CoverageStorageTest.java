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
import java.io.File;
import java.util.HashMap;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author shura
 */
public class CoverageStorageTest {

    public CoverageStorageTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    CoverageStorage instance = new CoverageStorage(false);
    CoverageStorage template = new CoverageStorage(false);

    @Before
    public void setUp() {
        instance.add("feature1", "class1", Level.LOW);
        instance.add("feature1", "class2", Level.MEDIUM);
        instance.add("feature2", "class1", Level.FULL);
        instance.add("feature3", "class2", Level.FULL);
        template.add("feature1");
        template.add("feature2");
        template.add("feature3");
        template.add("feature4");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class CoverageStorage.
     */
    @Test
    public void testAdd() {
        instance.add("feature1", "class3", Level.FULL);
        assertEquals(3, instance.coverage.get("feature1").sourceUnits.size());
        instance.add("feature4", "class3", Level.FULL);
        assertEquals(4, instance.coverage.size());
        assertEquals(1, instance.coverage.get("feature4").sourceUnits.size());
    }

    /**
     * Test of merge method, of class CoverageStorage.
     */
    @Test
    public void testMerge() {
        CoverageStorage another = new CoverageStorage(false);
        instance.add("feature1", "class1", Level.LOW);
        instance.add("feature1", "class2", Level.FULL);
        instance.add("feature3", "class1", Level.FULL);
        instance.add("feature3", "class2", Level.FULL);
        instance.merge(another);
        assertEquals(Level.FULL, instance.coverage.get("feature1").level);
        assertEquals(2, instance.coverage.get("feature3").sourceUnits.size());
    }

    /**
     * Test of read method, of class CoverageStorage.
     */
    @Test
    public void testReadWrite() throws Exception {
        File file = File.createTempFile("coverage", ".fcov");
        instance.write(file);
        CoverageStorage copy = new CoverageStorage(false);
        copy.read(file);
        for(String feature : copy.coverage.keySet()) {
            assertEquals(instance.coverage.get(feature).feature, copy.coverage.get(feature).feature);
            assertEquals(instance.coverage.get(feature).level, copy.coverage.get(feature).level);
            assertEquals(instance.coverage.get(feature).sourceUnits, copy.coverage.get(feature).sourceUnits);
        }
        assertEquals(instance.coverage.keySet().size(), copy.coverage.keySet().size());
    }

    @Test
    public void testMergeWithTempale() {
        instance.merge(template);
        assertMerge(instance);
    }

    @Test
    public void testMergeTempaleWith() {
        template.merge(instance);
        assertMerge(template);
    }

    private void assertMerge(CoverageStorage merge) {
        assertEquals(4, merge.coverage.keySet().size());
        assertEquals(2, merge.coverage.get("feature1").sourceUnits.size());
        assertEquals(1, merge.coverage.get("feature2").sourceUnits.size());
        assertEquals(1, merge.coverage.get("feature3").sourceUnits.size());
    }

}
