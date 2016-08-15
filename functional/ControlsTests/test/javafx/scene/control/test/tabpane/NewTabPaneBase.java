/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
package javafx.scene.control.test.tabpane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableMap;
import javafx.factory.ControlsFactory;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import static javafx.scene.control.test.tabpane.NewTabPaneApp.*;
import javafx.scene.control.test.util.UtilTestFunctions;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController;
import static javafx.scene.control.test.utils.ptables.NodesChoserFactory.*;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.TabWrap;
import org.jemmy.interfaces.Close;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import static test.javaclient.shared.TestUtil.isEmbedded;

/**
 * @author Dmitry Zinkevich <dmitry.zinkevich@oracle.com>
 */
public class NewTabPaneBase extends UtilTestFunctions {

    private static Wrap<? extends Scene> scene;
    private boolean resetHardByDefault = false;//switcher of hard and soft reset mode.
    private boolean doNextResetHard = resetHardByDefault;
    private Wrap<? extends ChoiceBox> contentChooser;
    private Wrap<? extends TextField> newTabIndex;
    private Wrap<? extends TextField> newTabId;
    protected Wrap<? extends TabPane> testedControl;
    protected Parent<Tab> tabPaneAsParent;
    protected Selectable<Object> tabPaneAsSelectable;

    protected static enum TabProperties {

        disable, disabled, prefWidth, hover, closable, selected, pressed
    };

    @BeforeClass
    public static void setUpClass() throws Exception {
        NewTabPaneApp.main(null);
    }

    @Before
    public void setUp() {
        initWrappers();
        Environment.getEnvironment().setTimeout("wait.state", isEmbedded() ? 60000 : 2000);
        Environment.getEnvironment().setTimeout("wait.control", isEmbedded() ? 60000 : 1000);
        scene.mouse().move(new Point(0, 0));
        try {
            setPropertyBySlider(AbstractPropertyController.SettingType.SETTER, TabProperties.prefWidth, 200);
        } catch (InterruptedException ex) {
            Logger.getLogger(NewTabPaneBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
        if (doNextResetHard) {
            resetSceneHard();
        } else {
            resetSceneSoft();
        }

        doNextResetHard = resetHardByDefault;
        currentSettingOption = UtilTestFunctions.SettingOption.PROGRAM;
    }

    protected void moveFocusFromTestedControl() {
        findButton(FOCUS_RECEIVING_BUTTON_ID).mouse().click();
    }

    protected void moveMouseFromTestedControl() {
        findButton(FOCUS_RECEIVING_BUTTON_ID).mouse().click();
    }

    /**
     *
     * @return tab that is currently selected
     */
    protected Tab getSelectedTab() {
        return new GetAction<Tab>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(testedControl.getControl().getSelectionModel().getSelectedItem());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void setUserData(final int IDX, final Object USER_DATA) {
        new GetAction<Object>() {
            @Override
            public void run(Object... parameters) throws Exception {
                TabWrap tabWrap = getTabWrapByIndex(IDX);
                ((Tab) tabWrap.getControl()).setUserData(USER_DATA);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected Object getUserData(final int IDX) {
        return new GetAction<Object>() {
            @Override
            public void run(Object... parameters) throws Exception {
                TabWrap tabWrap = getTabWrapByIndex(IDX);
                setResult(((Tab) tabWrap.getControl()).getUserData());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected int getPropertiesCount(final int IDX) {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(((TabPane) testedControl.getControl()).getTabs().get(IDX).getProperties().size());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void setProperties(final int IDX, Map<Object, Object> props) {
        new GetAction<Object>() {
            @Override
            public void run(Object... parameters) throws Exception {
                TabWrap tabWrap = getTabWrapByIndex(IDX);
                ((Tab) tabWrap.getControl()).getProperties().clear();

                Map<Object, Object> props = (Map<Object, Object>) parameters[0];
                ((Tab) tabWrap.getControl()).getProperties().putAll(props);
            }
        }.dispatch(Root.ROOT.getEnvironment(), props);
    }

    protected Map<Object, Object> getProperties(final int IDX) {
        return new GetAction<Map<Object, Object>>() {
            @Override
            public void run(Object... parameters) throws Exception {
                TabWrap tabWrap = getTabWrapByIndex(IDX);
                ObservableMap<Object, Object> properties = ((Tab) tabWrap.getControl()).getProperties();

                setResult(Collections.unmodifiableMap(properties));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected Boolean tabHasProperties(final int IDX) {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(testedControl.getControl().getTabs().get(IDX).hasProperties());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    /*
     * Copy and paste from TabPane2Test
     */
    protected Lookup getTabLookup(final boolean visible) {
        Parent pane_as_parent = testedControl.as(Parent.class, Node.class);
        final Wrap<Node> tab_pane_header = pane_as_parent.lookup(Node.class, new ByStyleClass("tab-header-area")).wrap();
        Parent<Node> header_as_parent = tab_pane_header.as(Parent.class, Node.class);
        final Wrap<Node> control_btn = header_as_parent.lookup(Node.class, new ByStyleClass("control-buttons-tab")).wrap();
        Lookup<Node> tab_lookup = header_as_parent.lookup(Node.class, new LookupCriteria<Node>() {
            public boolean check(Node control) {
                Bounds ctrl_bounds = control.getBoundsInParent();
                Rectangle ctrl_rect = new Rectangle(ctrl_bounds.getMinX(), ctrl_bounds.getMinY(), ctrl_bounds.getWidth(), ctrl_bounds.getHeight());
                Rectangle header_rect = new Rectangle(0, 0, tab_pane_header.getScreenBounds().getWidth(), tab_pane_header.getScreenBounds().getHeight());

                Bounds ctrl_btn_bounds = control_btn.getControl().getBoundsInParent();

                Rectangle ctrl_btn_rect = new Rectangle(ctrl_btn_bounds.getMinX(), ctrl_btn_bounds.getMinY(),
                        ctrl_btn_bounds.getWidth(), ctrl_btn_bounds.getHeight());

                return control.getStyleClass().contains("tab")
                        && (!visible || (header_rect.contains(ctrl_rect)
                        && !ctrl_btn_rect.intersects(ctrl_rect)));
            }
        });
        return tab_lookup;
    }

    protected Wrap<? extends Node> getPlaceholderWrap(final Tab tab) {
        Wrap placeholder_wrap;

        final TabPane tabPane = new GetAction<TabPane>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(testedControl.getControl());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        final Integer tabIndex = new GetAction<Integer>() {
            @Override
            public void run(Object... parameters) throws Exception {
                Tab tab = (Tab) parameters[0];
                setResult(tabPane.getTabs().indexOf(tab));
            }
        }.dispatch(Root.ROOT.getEnvironment(), tab);

        Parent pane_as_parent = testedControl.as(Parent.class, Node.class);
        Wrap<Node> tab_pane_header = pane_as_parent.lookup(Node.class, new ByStyleClass("tab-header-area")).wrap();
        Parent<Node> header_as_parent = tab_pane_header.as(Parent.class, Node.class);

        ArrayList<Wrap> labels = new ArrayList<Wrap>();
        Lookup lookup = getTabLookup(false);
        for (int i = 0; i < lookup.size(); i++) {
            labels.add(lookup.wrap(i));
        }

        final Side side = new GetAction<Side>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(tabPane.getSide());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        Collections.sort(labels, new Comparator<Wrap>() {
            public int compare(Wrap w1, Wrap w2) {
                switch (side) {
                    case LEFT:
                    case RIGHT: {
                        return Double.compare(w1.getScreenBounds().getY(), w2.getScreenBounds().getY());
                    }
                    default: {
                        return Double.compare(w1.getScreenBounds().getX(), w2.getScreenBounds().getX());
                    }
                }
            }
        });
        placeholder_wrap = labels.get(tabIndex.intValue());

        Wrap down_button = header_as_parent.lookup(Node.class, new LookupCriteria<Node>() {
            public boolean check(Node node) {
                return node.getStyleClass().contains("tab-down-button");
            }
        }).wrap();

        if (!testedControl.getScreenBounds().contains(placeholder_wrap.getScreenBounds())
                || down_button.getScreenBounds().intersects(placeholder_wrap.getScreenBounds())) {

            Lookup scene_lookup = Root.ROOT.lookup(new LookupCriteria<Scene>() {
                public boolean check(Scene scene) {
                    if (scene.getWindow() instanceof ContextMenu) {
                        Object property = scene.getRoot().getProperties().get(TabPane.class);
                        return property != null && property == tabPane;
                    }
                    return false;
                }
            });

            Wrap<? extends Scene> popup_scene_wrap;

            if (scene_lookup.size() == 0) {
                down_button.mouse().click();
                popup_scene_wrap = Root.ROOT.lookup(new LookupCriteria<Scene>() {
                    public boolean check(Scene scene) {
                        if (scene.getWindow() instanceof ContextMenu) {
                            scene.getRoot().getProperties().put(TabPane.class, tabPane);
                            return true;
                        }
                        return false;
                    }
                }).wrap();
            } else {
                popup_scene_wrap = scene_lookup.wrap();
            }

            int tabsCount = new GetAction<Integer>() {
                @Override
                public void run(Object... parameters) throws Exception {
                    setResult(tabPane.getTabs().size());
                }
            }.dispatch(Root.ROOT.getEnvironment());

            Lookup popup_lookup = popup_scene_wrap.as(Parent.class, Node.class).lookup(Node.class, new ByStyleClass("menu-item"));
            placeholder_wrap = popup_lookup.wrap(tabIndex.intValue() - tabsCount + popup_lookup.size());
            /*
             * If poppup was opened it should be closed then
             */
            popup_scene_wrap.keyboard().pushKey(Keyboard.KeyboardButtons.ESCAPE);

        }

        return placeholder_wrap;
    }

    /**
     *
     * @param INDEX index of wrapped tab
     * @return TabWrap
     */
    protected TabWrap getTabWrapByIndex(final int INDEX) {
        return (TabWrap) tabPaneAsParent.lookup(Tab.class, new LookupCriteria<Tab>() {
            public boolean check(Tab control) {
                return control == tabPaneAsSelectable.getStates().get(INDEX);
            }
        }).wrap();
    }

    protected void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(org.jemmy.interfaces.Parent.class, Node.class);
        testedControl = parent.lookup(TabPane.class, new ByID<TabPane>(TESTED_TABPANE_ID)).wrap();
        contentChooser = parent.lookup(ChoiceBox.class, new ByID(NODE_CHOSER_CHOICE_BOX_ID)).wrap();
        newTabIndex = parent.lookup(TextField.class, new ByID(TABPANE_ADD_INDEX_TEXT_FIELD_ID)).wrap();
        newTabId = parent.lookup(TextField.class, new ByID(NEW_TAB_ID_TEXT_FIELD_ID)).wrap();
        tabPaneAsSelectable = testedControl.as(Selectable.class, Tab.class);
        tabPaneAsParent = testedControl.as(Parent.class, Tab.class);

        initChangingController(parent);
    }

    protected List<String> populateTabPane(final int size) {
        List<String> tabs = new ArrayList<String>(size);
        for (int i = 0; i < size; i++) {
            tabs.add("tab #" + i);
        }

        for (int i = 0; i < size; i++) {
            addTab(tabs.get(i), i);
        }

        return tabs;
    }

    protected void addTab(String tabName, int idx) {
        addTab(tabName, idx, false);
    }

    protected void addTab(String tabName, int idx, boolean switchToTabPropertiesPanel) {
        addTab(tabName, idx, switchToTabPropertiesPanel, ControlsFactory.Labels);
    }

    protected void addTab(String tabName, int idx, boolean switchToTabPropertiesPanel, ControlsFactory content) {
        addTab(tabName, idx, switchToTabPropertiesPanel, false, ControlsFactory.Labels);
    }

    protected void addTab(String tabName, int idx, boolean switchToTabPropertiesPanel, boolean vetoOfContent) {
        addTab(tabName, idx, switchToTabPropertiesPanel, vetoOfContent, ControlsFactory.Labels);
    }

    protected void addTab(String tabName, int idx, boolean switchToTabPropertiesPanel, final boolean vetoOfContent, ControlsFactory content) {
        //contentChooser.as(Selectable.class).selector().select(content);
        selectObjectFromChoiceBox(contentChooser, content);

        setText(newTabIndex, String.valueOf(idx));
        setText(newTabId, tabName);
        final Wrap<? extends CheckBox> сheckBox = findCheckBox(VETO_CHECKBOX_ID);
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                сheckBox.getControl().setSelected(vetoOfContent);
            }
        }.dispatch(Root.ROOT.getEnvironment());

        clickButtonForTestPurpose(NODE_CHOOSER_ACTION_BUTTON_ID);

        if (switchToTabPropertiesPanel) {
            switchToPropertiesTab(tabName);
        }
    }

    protected void tryToCloseTabByIndex(int index) {
        Lookup lookup = testedControl.as(Parent.class, Tab.class).lookup(Tab.class);
        Close close = (Close) lookup.wrap(index).as(Close.class);
        close.close();
    }

    protected void checkTabsCount(int expectedTabsCount) {
        assertEquals(new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().getTabs().size());
            }
        }.dispatch(Root.ROOT.getEnvironment()), expectedTabsCount, 0);
    }

    protected void resetSceneHard() {
        clickButtonForTestPurpose(RESET_BUTTON_ID);
        initWrappers();
    }

    protected void resetSceneSoft() {
        clickButtonForTestPurpose(RESET_SOFTLY_BUTTON_ID);
        initWrappers();
    }
}
