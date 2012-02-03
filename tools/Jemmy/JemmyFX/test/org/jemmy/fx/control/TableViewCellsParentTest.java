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
