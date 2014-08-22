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
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.SceneDock;
import org.jemmy.interfaces.Table;
import org.jemmy.lookup.EqualsLookup;
import org.junit.*;

import static org.junit.Assert.assertTrue;

/**
 * @author Alexander Kouznetsov
 */
public class TableViewLookupTest /*extends TableViewTestBase*/ {

    static TableViewDock tableDock;
    static Table table;

    public TableViewLookupTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(TableViewApp.class);
        tableDock = new TableViewDock(new SceneDock().asParent());
        table = tableDock.asTable();
        table.setEditor(new TextFieldCellEditor<>());
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
    public void cellLookup() {
        System.out.println("testLookup");

        final String D = "D";

        Wrap<? extends TableCell> tableCellWrap = tableDock.asParent().
                lookup(TableCell.class, control -> {
                    String item = (String) control.getItem();
                    return item != null && item.contains(D);
                }).wrap();
        assertTrue("tableViewCellsParent.lookup().wrap() returns "
                + "TableCellWrap",
                tableCellWrap instanceof TableCellWrap);

        assertTrue(tableCellWrap.getProperty(String.class, "getItem").contains(D));

        tableCellWrap.mouse().click();

        tableDock.wrap().waitState(() -> tableDock.getSelection().contains(new Point(0, 4)) ? true : null);
    }

    @Test
    public void itemLookup() {
        table.lookup(new EqualsLookup("5")).wrap().mouse().click();
        tableDock.wrap().waitState(() -> tableDock.getSelection().contains(new Point(1, 5)) ? true : null);
        new TableCellItemDock(table, new EqualsLookup("Mikhail")).asEditableCell().select();
        tableDock.wrap().waitState(() -> tableDock.getSelection().contains(new Point(0, 0)) ? true : null);
    }

    @Test
    public void edit() {
        table.setEditor(new TextFieldCellEditor());
        new TableCellItemDock(table, new EqualsLookup("B")).asEditableCell().edit("Aleksander");
    }
}
