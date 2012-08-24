/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import org.jemmy.fx.AppExecutor;
import org.junit.BeforeClass;

/**
 *
 * @author shura
 */
public class ListItemHorizontalTest extends ListItemTest {

    public ListItemHorizontalTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(ListApp.class, ListApp.H_MODE);
    }
}
