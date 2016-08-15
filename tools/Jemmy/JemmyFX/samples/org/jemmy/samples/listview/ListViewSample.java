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
package org.jemmy.samples.listview;

import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.ListItemDock;
import org.jemmy.fx.control.ListViewDock;
import org.jemmy.fx.control.TextFieldCellEditor;
import org.jemmy.interfaces.List;
import org.jemmy.lookup.Any;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.samples.SampleBase;
import org.jemmy.samples.listview.ListViewApp.Record;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

/**
 * This shows typical set of operations which could be performed of a ListView.
 * ListView could be handled through <code>Selectable</code> and <code>List</code> Jemmy control interfaces or
 * by a <code>ListItemDoc</code>k lookups and functionality.
 * @author kam, shura
 */
public class ListViewSample extends SampleBase {
    private static SceneDock scene;
    private static ListViewDock listView1;
    private static ListViewDock listView2;
    private static ListViewDock listView3;

    @BeforeClass
    public static void launch() throws InterruptedException {
        startApp(ListViewApp.class);
        scene = new SceneDock();

        /*
        * Looking up for ListView control. The best option is to do that
        * by id. Check lookup sample for more info on lookup.
        */
        listView1 = new ListViewDock(scene.asParent(), "list");
        listView2 = new ListViewDock(scene.asParent(), "records-list");
        listView3 = new ListViewDock(scene.asParent(), "countries-list");
    }

    /**
     * ListView lookup is no different than other components - check the lookup
     * samples, list item lookup is another matter.
     */
    @Test
    public void lookup() {
        //you could find a cell by it's content
        new ListItemDock(listView2.asList(), new Record("Record 15")).asEditableCell().select();
        //by index
        new ListItemDock(listView2.asList(), 10, new Any()).asEditableCell().select();
        //by toString() of the object
        new ListItemDock(listView3.asList(), "man", StringComparePolicy.SUBSTRING).asEditableCell().select();
        //or any other way by creating a custom lookup criteria
        new ListItemDock(listView1.asList(), cntrl -> cntrl instanceof Date).asEditableCell().select();
    }

    /**
     * Selecting an item by item object.
     */
    @Test
    public void selectItemByItemObject() {
        new ListItemDock(listView1.asList(), "Item 1").mouse().click();
        //and also
        listView1.selector().select("Item 2");
    }

    /**
     * Getting selected item.
     */
    @Test
    public void getSelectedItem() {
        Object selected = listView1.asSelectable().getState();
        System.out.println("Selected item of listView1 = " + selected);
    }

    /**
     * Selecting an item by item index.
     */
    @Test
    public void selectItemByItemIndex() {
        int itemIndex = 15;
        listView2.asList().select(itemIndex);
    }

    /**
     * Selecting an item of specific type.
     */
    @Test
    public void selectItemOfSpecificType() {
        ListItemDock item = new ListItemDock(listView1.asList(), Date.class, new Any());
        listView1.asSelectable().selector().select(item.control());
    }

    /**
     * Selecting multiple items by indexes
     */
    @Test
    public void selectMultipleItemsByIndexes() {
        int[] itemIndexes = {20, 30, 40};
        listView2.asList().select(itemIndexes);
    }

    /**
     * Selecting multiple items by lookup criteria
     */
    @Test
    public void selectMultipleItemsByItemsLookupCriteria() {
        listView3.asList().select(item -> "USA".equals(item) || "Russia".equals(item) || "Germany".equals(item));
    }

    /**
     * Looking up for some specific item in the list.
     */
    @Test
    public void lookupForItem() {
        // Looking up using custom criteria on item value
        ListItemDock listItemDock1 = new ListItemDock(listView3.asList(), item -> item instanceof String && ((String) item).endsWith("land"));
        System.out.println("looked up the following item: " + listItemDock1.control());

        // Doing almost the same eith a specisal lookup constructor.
        ListItemDock listItemDock2 = new ListItemDock(listView3.asList(), "land", StringComparePolicy.SUBSTRING);
        System.out.println("looked up the following item: " + listItemDock2.control());
    }

    /**
     * Scrolling happens automatically in case an Item is selected. Should you
     * need to scroll anyway ...
     */
    @Test
    public void scrollToItem() {
        int itemIndex = 100;

        // Getting a cell by item index
        ListItemDock listItemDock = new ListItemDock(listView2.asList(), itemIndex, new Any());

        // and show it
        listItemDock.shower().show();
    }

    /**
     * Editing a value of a cell in editable ListView
     */
    //@Test//https://javafx-jira.kenai.com/browse/RT-31165
    public void editListItem() {
        String value = "Lithuania";

        // Getting List interface and setting up cell editor to work with TextFieldCells
        List<Object> asList = listView3.asList();
        asList.setEditor(new TextFieldCellEditor<>());

        // Looking up for the necessary item using the same approach as in
        // lookupForItem() test
        ListItemDock listItemDock = new ListItemDock(asList, item -> item instanceof String && ((String) item).contains("<"));

        // Editing the value
        listItemDock.asEditableCell().edit(value);
    }
}
