/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.SceneDock;
import org.jemmy.lookup.EqualsLookup;
import org.jemmy.lookup.LookupCriteria;
import static org.junit.Assert.assertEquals;
import org.junit.*;

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
        list.setEditor(new TextFieldCellEditor<Object>());
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
        new ListItemDock(list, lst.asSelectable().getStates().size() - 1).
                asEditableCell().edit("four");
    }

    @Test
    public void multySelect() throws InterruptedException {
        int count = 0;
        for(Object i : lst.wrap().getControl().getItems()) {
            if(i.toString().trim().length() > 2) count++;
        }
        lst.asList().select(new LookupCriteria() {

            public boolean check(Object control) {
                return ((String)control).trim().length() > 2;
            }
        });
        assertEquals(count, lst.wrap().getControl().getSelectionModel().getSelectedItems().size());
    }
    
    @Test
    public void cellSelect() throws InterruptedException {
        new ListItemDock(lst.asList(), 0).asEditableCell().select();
    }
}
