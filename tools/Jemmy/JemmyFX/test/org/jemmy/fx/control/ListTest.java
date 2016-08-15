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

import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.SceneDock;
import org.jemmy.lookup.Any;
import org.jemmy.lookup.EqualsLookup;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author shura
 */
public class ListTest {

    public ListTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(ListApp.class);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    ListViewDock lst;
    org.jemmy.interfaces.List list;

    @Before
    public void setUp() throws InterruptedException {
        lst = new ListViewDock(new SceneDock().asParent());
        list = lst.asList();
        list.setEditor(new TextFieldCellEditor<>());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void stringLookup() throws InterruptedException {
//        Scroll scroll = new ScrollBarDock(lst.asParent()).asScroll();
//        scroll.to(scroll.maximum());
//        new ListItemDock(lst.asList(), new EqualsLookup<Object>("many")).asCell().select();
        ListItemDock item = new ListItemDock(list, new EqualsLookup<Object>("2"));
        item.asEditableCell().edit("two");
    }

    @Test
    public void indexLookup() {
        new ListItemDock(list, lst.asSelectable().getStates().size() - 1, new Any<>()).
                asEditableCell().edit("four");
    }

    @Test
    public void multySelect() throws InterruptedException {
        int count = 0;
        for(Object i : lst.wrap().getControl().getItems()) {
            if(i.toString().trim().length() > 2) count++;
        }
        lst.asList().select(control -> ((String)control).trim().length() > 2);
        assertEquals(count, lst.wrap().getControl().getSelectionModel().getSelectedItems().size());
    }

    @Test
    public void cellSelect() throws InterruptedException {
        new ListItemDock(lst.asList(), 0, new Any<>()).asEditableCell().select();
    }
}
