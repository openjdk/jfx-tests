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
package ensemble.test;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.web.WebView;
import org.jemmy.action.GetAction;
import org.jemmy.fx.*;
import org.jemmy.fx.control.*;
import org.jemmy.lookup.AbstractLookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.Test;

public class End2EndTest extends EnsembleTestBase {

    private static final String ALTERNATIVE_CHOICE = "Cat";
    private static final String CHECK_BOXE_SAMPLE_NAME = "Check Boxes";
    private static final String CHOICE_BOX_SAMPLE_NAME = "Choice Box";
    private static final String COPY_SOURCE_BTN = "Copy Source";
    private static final String DEFAULT_CHOICE = "Dog";
    private static final String SOURCE_CODE = "public class ChoiceBoxSample extends Application";
    private static final String SAMPLE_PAGE_TITLE = "Sample";
    private static final String SOURCE_CODE_PAGE_TITLE = "Source Code";
    private static final String INFO_TEXT = "A sample that shows a choice box";
    private static final String JAVADOC = "";

    @Test
    public void choiseBox() {
        //search for "choice "
        TextInputControlDock searchField = new TextInputControlDock(mainToolbar.asParent(), TextField.class);
        searchField.asSelectionText().clear();
        searchField.asSelectionText().type("choice ");

        //verify popup content
        SceneDock popup_scene = new SceneDock(Root.ROOT.lookup(new ByWindowType<Scene>(ContextMenu.class)));
        LabeledDock popupItem = new LabeledDock(popup_scene.asParent(), new ByStyleClass<Labeled>("item-label"));

        //make sure the description dialog is there
        popupItem.mouse().move();
        SceneDock descWindow = new SceneDock(new LookupCriteria<Scene>() {

            public boolean check(Scene cntrl) {
                Node first = cntrl.getRoot().getChildrenUnmodifiable().get(0);
                return first != null & first.getId() != null
                        && first.getId().equals("search-info-box");
            }
        });
        //check text of the info message
        final LabeledDock info = new LabeledDock(descWindow.asParent(), "search-info-description");
        info.wrap().
                waitState(new State<String>() {

            public String reached() {
                return info.asText().text().contains(INFO_TEXT) ? info.asText().text() : null;
            }
        });

        //select the search result
        popupItem.mouse().click();
        waitSamplesTreeSelection(CHOICE_BOX_SAMPLE_NAME);

        //check the _Choice Box_ label
        TabPaneDock sample_area = new TabPaneDock(mainScene.asParent(), new ByID<TabPane>("source-tabs"));
        System.out.println(new LabeledDock(sample_area.asParent(), Label.class).wrap().getControl().getStyleClass());
        new LabeledDock(sample_area.asParent(), new ByStyleClass<Labeled>("page-header"));


        //check that the choice box is indeed shown
        final ChoiceBoxDock theChoiceBox = new ChoiceBoxDock(sample_area.asParent());
        theChoiceBox.wrap().waitState(new State<String>() {

            public String reached() {
                return (String) theChoiceBox.asSelectable().getState();
            }
        }, DEFAULT_CHOICE);
        //and also there are other animals
        theChoiceBox.asSelectable().selector().select(ALTERNATIVE_CHOICE);


        //get the tab pane
        TabPaneDock sampleTabPane = new TabPaneDock(mainScene.asParent(), new ByID<TabPane>("source-tabs"));

        //show the source
        new TabDock(sampleTabPane.asTabParent(), new LookupCriteria<Tab>() {

            @Override
            public boolean check(Tab cntrl) {
                return cntrl.getText().equals(SOURCE_CODE_PAGE_TITLE);
            }
        }).asCell().select();

        //copy the source to clipboard
        new ControlDock(sampleTabPane.asParent(), new ByText(COPY_SOURCE_BTN)).mouse().click();

        // check clipboard
        new Waiter(Root.ROOT.getEnvironment().getTimeout(AbstractLookup.WAIT_CONTROL_TIMEOUT)).ensureValue(true, new State<Boolean>() {

            @Override
            public Boolean reached() {
                String content = new GetAction<String>() {

                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(Clipboard.getSystemClipboard().getString());
                    }
                }.dispatch(Root.ROOT.getEnvironment());
                return content != null && content.contains(SOURCE_CODE);
            }
        });

        //test documentation
        TabDock sampleTab = new TabDock(sample_area.asTabParent(), new LookupCriteria<Tab>() {

            @Override
            public boolean check(Tab cntrl) {
                return cntrl.getText().compareTo(SAMPLE_PAGE_TITLE) == 0;
            }
        });
        sampleTab.asCell().select();
        NodeDock right_sidebar = new NodeDock(sampleTab.asParent(),
                new ByStyleClass<Node>("right-sidebar"));
        sampleTab.asParent().lookup(Hyperlink.class,
                new ByText<Hyperlink>(ChoiceBox.class.getName())).wrap().mouse().click();
        new NodeDock(mainScene.asParent(), WebView.class, new LookupCriteria<WebView>() {

            public boolean check(WebView cntrl) {
                return cntrl.getEngine().getLocation().endsWith("docs/api/javafx/scene/control/ChoiceBox.html");
            }
        });

        //let's look for check box now
        searchField.asSelectionText().clear();
        searchField.asSelectionText().type("check ");

        //verify popup content
        popup_scene = new SceneDock(Root.ROOT.lookup(new ByWindowType<Scene>(ContextMenu.class)));
        new LabeledDock(popup_scene.asParent(), new ByStyleClass<Labeled>("item-label")).mouse().click();
        waitSamplesTreeSelection(CHECK_BOXE_SAMPLE_NAME);
    }
}