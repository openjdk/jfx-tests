/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import javafx.scene.control.TreeItem;
import org.jemmy.fx.SceneDock;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.EqualsLookup;
import org.jemmy.resources.StringComparePolicy;
import org.junit.*;

/**
 *
 * @author shura
 */
public class TreeItemTest {

    private TreeViewDock tree;
    private LabeledDock selection;

    public TreeItemTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        TreeTest.setUpClass();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        tree = new TreeViewDock(new SceneDock().asParent());
        selection = new LabeledDock(new SceneDock().asParent(), "selection");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void byCriteria() {
        TreeItemDock ti00 = new TreeItemDock(tree.asItemParent(), new EqualsLookup<Object>("00"));
        ti00.asTreeItem().expand();
        new TreeItemDock(tree.asItemParent(), new EqualsLookup<Object>("001")).asEditableCell().select();
    }

    @Test
    public void byObject() {
        TreeItemDock ti01 = new TreeItemDock(tree.asItemParent(), "01");
        ti01.asTreeItem().collapse();
        new TreeItemDock(
                ti01.asItemParent(), String.class, "012").asEditableCell().select();
    }

    @Test
    public void byToString() {
        new TreeItemDock(
                new TreeItemDock(tree.asItemParent(), "02").asItemParent(),
                "23", StringComparePolicy.SUBSTRING).asEditableCell().select();
    }

    @Test
    public void byToStringPath() {
        new TreeItemDock(tree.asItemParent(), StringComparePolicy.EXACT, "0", "02", "023").asEditableCell().select();
    }

    @Test
    public void byPath() {
        new TreeItemDock(tree.asItemParent(), "0", "02", "024").asEditableCell().select();
    }

    @Test
    public void byCriteriaPath() {
        new TreeItemDock(tree.asItemParent(),
                new EqualsLookup<Object>("0"),
                new EqualsLookup<Object>("02"),
                new EqualsLookup<Object>("025")).asEditableCell().select();
    }

    @Test
    public void autoExpand() {
        new TreeItemDock(tree.asItemParent(), "00").asTreeItem().collapse();
        new TreeItemDock(tree.asItemParent(), "01").asTreeItem().collapse();
        new TreeItemDock(tree.asItemParent(), "029").asTreeItem().collapse();
        new TreeItemDock(tree.asItemParent(), "02").asTreeItem().collapse();
        new TreeItemDock(tree.asItemParent(), "0").asTreeItem().collapse();
        new TreeItemDock(tree.asItemParent(), "0290").asEditableCell().select();
    }

    @Test
    public void lookupItem() {
        tree.wrap().as(Parent.class, TreeItem.class).lookup(TreeApp.MyTreeItem.class).wrap().mouse().click();
    }
}
