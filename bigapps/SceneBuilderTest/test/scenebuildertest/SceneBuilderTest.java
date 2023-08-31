
/*
 * Copyright (c) 2009, 2023, Oracle and/or its affiliates. All rights reserved.
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

import com.oracle.javafx.scenebuilder.app.SceneBuilderApp;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.jemmy.fx.ByTitleSceneLookup;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.NodeParentImpl;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.AccordionDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.fx.control.ListViewDock;
import org.jemmy.fx.control.ScrollBarDock;
import org.jemmy.fx.control.TextInputControlDock;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.BySubControl;
import org.jemmy.resources.StringComparePolicy;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This sample demonstrates different approaches for testing real application.
 *
 * @author andrey
 */
public class SceneBuilderTest {

    @BeforeClass
    public static void RunUI() {
        new Thread(() -> Application.launch(SceneBuilderApp.class)).start();
    }

    @Test
    public void testPrototype() throws InterruptedException {
        var templateSelector = new SceneDock(new ByTitleSceneLookup<>("Scene Builder", StringComparePolicy.SUBSTRING));
        new NodeDock(templateSelector.asParent(), new ByText<>("Basic Application")).mouse().click();

        //TODO
        //may be identify the scene by the content
        var sceneBuilder = new SceneDock(new ByTitleSceneLookup<>("Untitled", StringComparePolicy.SUBSTRING));
        var library = new AccordionDock(sceneBuilder.asParent(), "libAccordion");
        library.selector().select("Controls");
        var controls = new ListViewDock(library.asParent(), "ControlsList");
        var buttonInPalette = new LabeledDock(controls.asParent(), "Button", StringComparePolicy.EXACT);
        buttonInPalette.mouse().click();

        var designSurface = new NodeDock(sceneBuilder.asParent(), "contentSubScene");

        buttonInPalette.drag().dnd(buttonInPalette.wrap().getClickPoint(),
                designSurface.wrap(),
                designSurface.wrap().getClickPoint());

        var inspector = new AccordionDock(sceneBuilder.asParent(), "accordion");
        inspector.selector().select("Properties");
        var id = new TextInputControlDock(inspector.asParent(), "Id Value");
        //TODO
        //why is awuto scroll not working?
        var propertyScroll = new ScrollBarDock(
                new NodeDock(sceneBuilder.asParent(), "propertiesTitledPane")
                .asParent(), sb -> sb.getOrientation() == Orientation.VERTICAL && sb.isVisible()).asScroll();
        //TODO
        //scroll till visible
        propertyScroll.to(propertyScroll.maximum());
        id.clear();
        id.type("Awesome!");
        id.keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);

        sceneBuilder.keyboard().pushKey(Keyboard.KeyboardButtons.P, Keyboard.KeyboardModifiers.META_DOWN_MASK);

        //TODO
        //how to better find it?
        var preview = new SceneDock(new BySubControl<Scene, Node>(new ByID<>("Awesome!")) {
            @Override
            protected Parent<Node> asParent(Scene scene) {
                return new NodeParentImpl(scene.getRoot(), sceneBuilder.environment());
            }
        });

        new NodeDock(preview.asParent(), "Awesome!").mouse().click();
    }
}
