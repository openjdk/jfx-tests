/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jemmy.fx.control;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import org.jemmy.control.Wrap;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Text;
import org.junit.*;

/**
 *
 * @author shura
 */
public class ComboBoxTest {

    public ComboBoxTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(ComboBoxApp.class);
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
    public void select() throws InterruptedException {
        Parent<Node> parent = Root.ROOT.lookup().as(Parent.class, Node.class);
        Wrap<? extends ComboBox> bar = parent.lookup(ComboBox.class).wrap();
        bar.as(Selectable.class).selector().select("Item 1");
        bar.as(Text.class).type("Text");
    }

}
