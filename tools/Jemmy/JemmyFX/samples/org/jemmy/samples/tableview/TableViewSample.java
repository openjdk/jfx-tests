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
package org.jemmy.samples.tableview;

import org.jemmy.Point;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.TableCellItemDock;
import org.jemmy.fx.control.TableViewDock;
import org.jemmy.fx.control.TextFieldCellEditor;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.samples.SampleBase;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Similar to other compound controls, table could be operated either through
 * its control interfaces or through a dock class, such as <code>TableCellItemDock</code>
 * @author shura
 */
public class TableViewSample extends SampleBase {
    private static SceneDock scene;
    private static TableViewDock tableView;

    @BeforeClass
    public static void launch() throws InterruptedException {
        startApp(TableViewApp.class);
        scene = new SceneDock();

        /**
        * Looking up for TableView control. The best option is to do that
        * by id.
        */
        tableView = new TableViewDock(scene.asParent(), "table");

        //set an editor right away
        tableView.asTable().setEditor(new TextFieldCellEditor<Object>());
    }

    /**
     * TableView lookup is no different than other components - check the lookup
     * samples, table item lookup is another matter.
     */
    @Test
    public void lookup() {
        //you could find a cell by it's content
        new TableCellItemDock(tableView.asTable(), "Vlastname").asEditableCell().select();
        //by coordinates
        new TableCellItemDock(tableView.asTable(), 4, 3).asEditableCell().select();
        //by toString() of the object
        new TableCellItemDock(tableView.asTable(), "Ul", StringComparePolicy.SUBSTRING).asEditableCell().select();
        //or any other way by creating a custom lookup criteria
        new TableCellItemDock(tableView.asTable(), cntrl -> cntrl instanceof Date).asEditableCell().select();
    }

    /**
     * Selecting a cell by column and row indexes.
     */
    @Test
    public void selectCellByColumnAndRow() {
        new TableCellItemDock(tableView.asTable(), 5, 3).asEditableCell().select();
        //or
        tableView.asTable().select(new Point(6, 3));
    }

    /**
     * Scrolling happens automatically when you are trying to select a cell. Should
     * you need the scrolling anyway ...
     */
    @Test
    public void scrollToCell() {
        new TableCellItemDock(tableView.asTable(), "Tlastname").shower().show();
    }

    /**
     * Getting information on the table size
     */
    @Test
    public void getTableSize() {
        System.out.println("tableView has " + tableView.getColumns().size() +
                " columns and " + tableView.getItems().size() + " rows.");
    }

    /**
     * Selecting multiple cells.
     */
    @Test
    public void selectMultipleCells() {
        tableView.asTable().select(new Point(1, 1), new Point(2, 5), new Point(3, 10));
    }

    /**
     * Editing cell value.
     */
    @Test
    public void editCell() {
        new TableCellItemDock(tableView.asTable(), 3, 2).asEditableCell().edit("Kouznetsov");
    }

    /**
     * Sorting by field.
     */
    @Test
    public void sort() {
        // No direct API for that http://javafx-jira.kenai.com/browse/JMY-170
    }

    /**
     * Getting selected cells
     */
    @Test
    public void getSelectedCells() {
        List<Point> selection = tableView.getSelection();
        System.out.println("Selected cells coordinates are = " + selection);
    }
}
