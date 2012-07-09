/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.SceneDock;
import org.jemmy.interfaces.Table;
import org.jemmy.resources.StringComparePolicy;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author shura
 */
public class TableItemTest {
    
    public TableItemTest() {
    }

    static TableViewDock tableDock;
    static Table table;
            
    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(TableViewApp.class);
        tableDock = new TableViewDock(new SceneDock().asParent());
        table = tableDock.asTable();
        table.setEditor(new TextFieldCellEditor<Object>());
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
    public void byCoords() {
        new TableCellItemDock(table, 1, 3).asEditableCell().select();
        new TableCellItemDock(table, 0, 0).asEditableCell().select();
        new TableCellItemDock(table, 1, 4).asEditableCell().select();
        new TableCellItemDock(table, 1, tableDock.getItemCount() - 1).asEditableCell().select();
    }
    @Test
    public void byToString() {
        new ScrollBarDock(tableDock.asParent()).asScroll().to(1);
        new TableCellItemDock(table, "Mi", StringComparePolicy.SUBSTRING).asEditableCell().select();
        new TableCellItemDock(table, "D", StringComparePolicy.EXACT).asEditableCell().select();
    }
    @Test
    public void byValue() {
        new TableCellItemDock(table, "D").asEditableCell().select();
        new TableCellItemDock(table, "8").asEditableCell().select();
        new TableCellItemDock(table, "G").asEditableCell().select();
    }
}
