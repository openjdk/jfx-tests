/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.interfaces.List;
import org.jemmy.lookup.Any;
import org.jemmy.resources.StringComparePolicy;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author shura
 */
public class ListItemTest {

    public ListItemTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(ListApp.class);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    ListViewDock lst;
    List list;

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
    public void byCriteria() {
        assertEquals("  33 ", new ListItemDock(lst.asList(), 2, control -> control.toString().contains("3")).control());
    }

    @Test
    public void byToString() {
        assertEquals("  14 ", new ListItemDock(lst.asList(), 1, "4", StringComparePolicy.SUBSTRING).control());
    }

    @Test
    public void byValue() {
        ListItemDock i24 = new ListItemDock(lst.asList(), "  24 ");
        i24.asEditableCell().select();
        assertEquals("  24 ", i24.control());
    }

    @Test
    public void byIndex() {
        ListItemDock i5 = new ListItemDock(lst.asList(), 5, new Any());
        i5.asEditableCell().select();
        assertEquals("  5 ", i5.control());
    }
}
