package org.jemmy.fx.control;


import javafx.scene.control.TableCell;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.SceneDock;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Table;
import org.jemmy.lookup.EqualsLookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.junit.*;


/**
 *
 * @author Alexander Kouznetsov
 */
public class TableViewLookupTest /*extends TableViewTestBase*/ {
    
    public TableViewLookupTest() {
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
    public void cellLookup() {
        System.out.println("testLookup");
        
        final String B = "B";
        /*
        Wrap<? extends TableCell> tableCellWrap = tableDock.asParent().
                lookup(TableCell.class, new LookupCriteria<TableCell>() {

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
*/
        tableDock.wrap().waitState(new State() {

            public Object reached() {
                return tableDock.getSelection().contains(new Point(0, 2)) ? true : null;
            }
        });
    }

    @Test
    public void itemLookup() {
        table.lookup(new EqualsLookup("5")).wrap().mouse().click();
        tableDock.wrap().waitState(new State() {

            public Object reached() {
                return tableDock.getSelection().contains(new Point(1, 5)) ? true : null;
            }
        });
        new TableCellItemDock(table, new EqualsLookup("Mikhail")).asCell().select();
        tableDock.wrap().waitState(new State() {

            public Object reached() {
                return tableDock.getSelection().contains(new Point(0, 0)) ? true : null;
            }
        });
    }
    
    @Test
    public void edit() {
        table.setEditor(new TextFieldCellEditor());
        //new TableCellItemDock(table, new EqualsLookup("B")).asCell().edit("Aleksander");
    }
}
