/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
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
