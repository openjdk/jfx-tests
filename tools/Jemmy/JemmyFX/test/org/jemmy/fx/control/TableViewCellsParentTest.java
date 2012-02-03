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
 * questions.
 */
package org.jemmy.fx.control;


import javafx.scene.control.TableCell;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;
import static org.junit.Assert.*;
import org.junit.*;


/**
 *
 * @author Alexander Kouznetsov
 */
public class TableViewCellsParentTest/* extends TableViewTestBase*/ {
    /*
    protected static Parent<TableCell> tableViewCellsParent;
    
    public TableViewCellsParentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        TableViewTestBase.setUpClass();
        tableViewCellsParent = tableViewWrap.as(Parent.class, TableCell.class);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testWrap() {
        System.out.println("testWrap");
        assertTrue("tableViewWrap.as(Parent.class, TableCell.class) returns "
                + "TabelViewCellsParent", 
                tableViewCellsParent instanceof TableViewCellsParent);
    }
    
    @Test
    public void testLookup() {
        System.out.println("testLookup");
        
        final String B = "B";
        
        Wrap<? extends TableCell> tableCellWrap = tableViewCellsParent.lookup(new LookupCriteria<TableCell>() {

            public boolean check(TableCell control) {
                String item = (String) control.getItem();
                return item != null && item.contains(B);
            }
        }).wrap();

        assertTrue("tableViewCellsParent.lookup().wrap() returns "
                + "TableCellWrap", 
                tableCellWrap instanceof TableCellWrap);
        
        assertTrue(tableCellWrap.getProperty(String.class, "getItem").contains(B));
        
        tableCellWrap.mouse().click();
        labelWrap.waitProperty("text", "selected = 0, 2");
    }
    
    @Test
    public void testTableViewCellsParentByPosition() {
        System.out.println("testTableViewCellsParentByPosition");
        
        tableViewCellsParent.lookup(TableCell.class, 
                new TableViewCellsParent.ByPosition(1, 3)).wrap().mouse().click();

        labelWrap.waitProperty("text", "selected = 1, 3");
    }
    
    @Test
    public void testTableViewCellsParentBySelection() {
        System.out.println("testTableViewCellsParentBySelection");

        tableViewCellsParent.lookup(TableCell.class, 
                new TableViewCellsParent.ByPosition(1, 4)).wrap().mouse().click();
        labelWrap.waitProperty("text", "selected = 1, 4");
        
        Wrap<? extends TableCell> tableCellWrap = (Wrap<? extends TableCell>) 
                tableViewCellsParent
                .lookup(TableCell.class, new TableViewCellsParent.BySelection())
                .wrap();
        assertEquals(tableCellWrap.getProperty(String.class, "getItem"), "4");
        
    }
     */
    
}
