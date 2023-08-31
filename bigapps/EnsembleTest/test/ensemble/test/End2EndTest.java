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
package ensemble.test;

import ensemble.control.Popover;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import org.jemmy.fx.ByText;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.SceneWrap;
import org.jemmy.fx.control.ChoiceBoxDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.fx.control.TextInputControlDock;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.BySubControl;
import org.jemmy.resources.StringComparePolicy;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class End2EndTest extends EnsembleTestBase {

    private static final String ALTERNATIVE_CHOICE = "Cat";
    private static final String DEFAULT_CHOICE = "Dog";

    @Test
    public void source() throws InterruptedException {
        //select colored buttons
        util().selectDemo("Controls", "Button", "Colored Buttons");
        //push one
        new NodeDock(util().sceneAsParent(), Button.class, new ByText<Button>("Indigo")).mouse().click();
        //open the source
        assertTrue(util().viewSource().control() != null);
    }

    @Test
    public void stages() throws InterruptedException {
        //select advanced stage
        util().selectDemo("Scenegraph", "Advanced Stage");
        new NodeDock(util().sceneAsParent(), Button.class, new ByText<>("Create a Stage")).mouse().click();
        var roundScene = new SceneDock(new BySubControl<Scene, Node>(n -> n instanceof Circle) {
            @Override
            protected Parent<Node> asParent(Scene scene) {
                return new SceneWrap<Scene>(util().mainScene().environment(), scene).asParent();
            }
        });
        new NodeDock(roundScene.asParent(), Button.class, new ByText<Button>("Close me")).mouse().click();
    }

    @Test
    public void search() throws InterruptedException {
        //search for "choiceb"
        TextInputControlDock searchField = new TextInputControlDock(util().mainToolbar().asParent(), TextField.class);
        searchField.asSelectionText().clear();
        searchField.asSelectionText().type("choiceb");

        new LabeledDock(
                new NodeDock(util().sceneAsParent(), Popover.class, po -> po.getStyleClass().contains("right-tooth")).asParent(),
        "An example of a ChoiceBox with several options.",
        StringComparePolicy.SUBSTRING).mouse().click();

        //TODO
        Thread.sleep(1000);

        //check that the choice box is indeed shown
        final ChoiceBoxDock theChoiceBox = new ChoiceBoxDock(util().sampleArea().asParent());
        theChoiceBox.wrap().waitState(() -> theChoiceBox.asSelectable().getState(), DEFAULT_CHOICE);
        theChoiceBox.asSelectable().selector().select(ALTERNATIVE_CHOICE);
    }
}
