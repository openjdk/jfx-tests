/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.fx.control;

import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.jemmy.control.Wrap;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.Controls;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.input.StringMenuOwner;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Tree;
import org.jemmy.resources.StringComparePolicy;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author shura
 */
public class MenuTest {
    
    public MenuTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(Controls.class);
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
    public void pushMenu() throws InterruptedException {
        Parent<Node> parent = Root.ROOT.lookup().as(Parent.class, Node.class);
        Wrap<? extends MenuBar> bar = parent.lookup(MenuBar.class).wrap();
//        Parent<Menu> barParent = bar.as(Parent.class, Menu.class);
//        Wrap<? extends Menu> menu1 = barParent.
//                lookup(new ByTextMenuItem<Menu>("menu1", StringComparePolicy.EXACT)).wrap();
//        menu1.mouse().move();
//        menu1.mouse().move();
//        Parent<MenuItem> menu1_parent = menu1.as(Parent.class, MenuItem.class);
//        Wrap<? extends MenuItem> sub_menu1 = menu1_parent.
//                lookup(new ByTextMenuItem<MenuItem>("sub-menu1", StringComparePolicy.EXACT)).wrap();
//        sub_menu1.mouse().move();
//        Parent<MenuItem> sub_menu1_parent = sub_menu1.as(Parent.class, MenuItem.class);
//        Wrap<? extends MenuItem> sub_sub_menu1 = sub_menu1_parent.lookup(new ByTextMenuItem<MenuItem>("sub-sub-menu1", StringComparePolicy.EXACT)).wrap();
//        sub_sub_menu1.mouse().click();
//        Parent<MenuItem> sub_sub_menu1_parent = sub_sub_menu1.as(Parent.class, MenuItem.class);
//        Wrap<? extends MenuItem> item = sub_sub_menu1_parent.lookup(new ByTextMenuItem<MenuItem>("item1", StringComparePolicy.EXACT)).wrap();
//        item.mouse().click();

        StringMenuOwner menu_owner = bar.as(StringMenuOwner.class, MenuItem.class);
        //menu_owner.push("menu1", "sub-menu1", "sub-sub-menu1", "item1");

//        menu_owner.menu().select(new ByTextMenuItem("menu1", StringComparePolicy.EXACT),
//                                    new ByTextMenuItem("sub-menu1", StringComparePolicy.EXACT),
//                                    new ByTextMenuItem("sub-sub-menu1", StringComparePolicy.EXACT));

        Wrap<MenuItem> sub_menu1 = menu_owner.menu().select(new ByTextMenuItem("menu1", StringComparePolicy.EXACT),
                                    new ByTextMenuItem("sub-menu1", StringComparePolicy.EXACT));

        StringMenuOwner sub_menu1_tree = sub_menu1.as(StringMenuOwner.class, MenuItem.class);
        sub_menu1_tree.push("sub-sub-menu1", "item1");

        Wrap<? extends MenuButton> menu_button = parent.lookup(MenuButton.class).wrap();
        StringMenuOwner button_menu_owner = menu_button.as(StringMenuOwner.class, MenuItem.class);

        button_menu_owner.push("menu1", "sub-menu1", "sub-sub-menu1", "item1");

//        MenuBarDock bar = new MenuBarDock(new SceneDock().asParent());
////        bar.asStringMenuOwner().push("menu2");
////        new LabeledDock(new SceneDock().asParent(), "menu2 pushed", StringComparePolicy.SUBSTRING);
//        bar.asStringMenuOwner().push("menu0", "item0");
//        new LabeledDock(new SceneDock().asParent(), "item0 pushed", StringComparePolicy.SUBSTRING);
//        bar.asStringMenuOwner().push("menu1", "sub-menu1", "item1");
//        new LabeledDock(new SceneDock().asParent(), "item1 pushed", StringComparePolicy.SUBSTRING);
    }

}
