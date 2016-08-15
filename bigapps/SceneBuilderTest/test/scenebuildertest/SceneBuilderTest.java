
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
package scenebuildertest;

import com.oracle.javafx.authoring.Main;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByTitleSceneLookup;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.ByIdMenuItem;
import org.jemmy.fx.control.ListItemDock;
import org.jemmy.fx.control.ListViewDock;
import org.jemmy.fx.control.MenuBarDock;
import org.jemmy.fx.control.TextInputControlDock;
import org.jemmy.fx.control.TitledPaneDock;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.resources.StringComparePolicy;
import org.junit.BeforeClass;
import org.junit.Test;
import static com.oracle.javafx.authoring.internal.QEConstants.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.PopupWindow;
import org.jemmy.fx.ByText;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.lookup.LookupCriteria;

/**
 * This sample demonstrates different approaches for testing real application.
 *
 * @author andrey
 */
public class SceneBuilderTest {

    @BeforeClass
    public static void RunUI() {
        //Use Application.launch(...) to run JavaFX application.
        new Thread(new Runnable() {
            public void run() {
                Application.launch(Main.class);
            }
        }).start();
    }

    @Test
    public void testPrototype() {
        //find Scene Builder scene
        final SceneDock sceneBuilder = new SceneDock(new ByTitleSceneLookup<>("Scene Builder", StringComparePolicy.SUBSTRING));
        //find library panel  by node ID using Scene Builder's scene as Parent
        ListViewDock library = new ListViewDock(sceneBuilder.asParent(), ID_LIBRARY_PANEL_LIST_VIEW);

        //find button in library
        ListItemDock button = new ListItemDock(library.asList(), new ByID("Button"));

        //show list item with button by scrolling listview
        button.shower().show();

        //find design surface by node ID "Anchor Pane".
        NodeDock designSurface = new NodeDock(sceneBuilder.asParent(), "AnchorPane");

        //drag list item with button and drop in center of design surface
        button.drag().dnd(designSurface.wrap(), designSurface.wrap().getClickPoint());

        //find properties panel by node ID
        TitledPaneDock titledPaneDock = new TitledPaneDock(sceneBuilder.asParent(), "Properties");

        //find text field for property "Text"
        TextInputControlDock textField = new TextInputControlDock(new NodeDock(titledPaneDock.asParent(), "TextValue").asParent());
        //clear and type text
        textField.clear();
        textField.type("Submit");
        //push enter to complete
        textField.keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);

        //Add text field/////////////////////////////////////////////////////////////////////////////////////////////////////

        //find text field in library
        ListItemDock textFieldControl = new ListItemDock(library.asList(), new ByID("Text Field"));

        //show list item with text field by scrolling listview
        textFieldControl.shower().show();

        //drag list item with text field and drop on design surface near button
        textFieldControl.drag().dnd(designSurface.wrap(), designSurface.wrap().getClickPoint().translate(70, 50));

        //find text field on design surface and double click to invoke inline editing
        new TextInputControlDock(designSurface.asParent()).mouse().click(2);

        //find inline text field and type text "Name".
        TextInputControlDock inline = new TextInputControlDock(sceneBuilder.asParent(), ID_TEXT_INLINE_EDITING);
        inline.clear();
        inline.type("Name");
        inline.keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);


        //show preview
        //find menu bar by ID then push "Preview" menu. Finally push "Show preview" menu item. All menu item have id's that help to find them.
        new MenuBarDock(sceneBuilder.asParent(), ID_MENU_BAR).menu().push(new ByIdMenuItem(ID_PREVIEW_MENU), new ByIdMenuItem(ID_PREVIEW_MENU_ITEM));

        //find preview scene. Criteria: scene is not existing scene builder scene and scene.getWindow is not PopupWindow
        SceneDock previewScene = new SceneDock(new LookupCriteria<Scene>() {
            @Override
            public boolean check(Scene cntrl) {
                return (cntrl != sceneBuilder.wrap().getControl() && !(cntrl.getWindow() instanceof PopupWindow));
            }
        });

        //find Button with text "Submit" on preview scene
        new LabeledDock(previewScene.asParent(), Button.class, new ByText<Button>("Submit"));
        //find TextField with text "Name" on preview scene
        new TextInputControlDock(previewScene.asParent(), TextField.class, new ByText<TextField>("Name"));


        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SceneBuilderTest.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
