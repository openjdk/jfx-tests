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
package javafx.scene.control.test;

import java.util.List;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.test.treeview.TestBase;
import static javafx.scene.control.test.treeview.TreeViewNewApp.*;
import javafx.scene.control.test.util.perfomance.NodeCounterChecker;
import javafx.scene.control.test.util.perfomance.NodeCounterChecker.Mode;
import javafx.scene.control.test.util.perfomance.StateVerificator;
import javafx.scene.control.test.utils.ptables.TabPaneWithControl;
import static javafx.scene.control.test.treeview.TreeViewCommonFunctionality.*;
import static javafx.scene.control.test.utils.ptables.AbstractPropertyController.*;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.Parent;
import org.junit.Test;

/**
 * @author Alexander Kirov
 */
public class TreeViewNodesLeaksTest extends TestBase {

    @Test
    public void nodesExpandingCollapsingLeakTest() throws InterruptedException {
        setSize(220, 220);
        final int size = 5;
        for (int i = 0; i < size; i++) {
            addElement("item-" + String.valueOf(i), ROOT_NAME, i, true);
            for (int j = 0; j < size; j++) {
                addElement("item-" + String.valueOf(i) + "-" + String.valueOf(j), "item-" + String.valueOf(i), j, false);
            }
        }

        switchToPropertiesTab(ROOT_NAME);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        for (int i = 0; i < size; i++) {
            switchToPropertiesTab("item-" + String.valueOf(i));
            setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        }

        new NodeCounterChecker(testedControl, 300, 1, new Runnable() {
            public void run() {
                for (Boolean expanded : new Boolean[]{Boolean.FALSE, Boolean.TRUE}) {
                    for (int i = 0; i < size; i++) {
                        switchToPropertiesTab("item-" + String.valueOf(i));
                        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, expanded);
                    }
                    switchToPropertiesTab(ROOT_NAME);
                    setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, expanded);
                }

            }
        }, Mode.TEST, new StateVerificator() {
            @Override
            public boolean verify(List<NodeCounterChecker.Measurement> telemetry, int currentIteration) {
                return ((double) telemetry.get(currentIteration).nodesCount / telemetry.get(0).nodesCount < 1.05);
            }
        }).start();
    }

    @Test
    public void addRemoveNodesLeakTest() throws InterruptedException {
        setSize(220, 220);
        final int size = 5;
        for (int i = 0; i < size; i++) {
            addElement("item-" + String.valueOf(i), ROOT_NAME, i, true);
            for (int j = 0; j < size; j++) {
                addElement("item-" + String.valueOf(i) + "-" + String.valueOf(j), "item-" + String.valueOf(i), j, false);
            }
        }

        switchToPropertiesTab(ROOT_NAME);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        for (int i = 0; i < size; i++) {
            switchToPropertiesTab("item-" + String.valueOf(i));
            setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        }

        new NodeCounterChecker(testedControl, 300, 1, new Runnable() {
            public void run() {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        removeItem("item-" + String.valueOf(i) + "-" + String.valueOf(j));
                    }
                    removeItem("item-" + String.valueOf(i));
                }

                final Wrap<? extends TabPaneWithControl> sceneWrap = scene.as(Parent.class, Node.class).lookup(TabPaneWithControl.class, new ByID(TabPaneWithControl.TAB_PANE_WITH_CONTROL_ID)).wrap();
                new GetAction() {
                    @Override
                    public void run(Object... os) throws Exception {
                        sceneWrap.getControl().refresh();
                    }
                }.dispatch(Root.ROOT.getEnvironment());

                for (int i = 0; i < size; i++) {
                    addElement("item-" + String.valueOf(i), ROOT_NAME, i, true);
                    for (int j = 0; j < size; j++) {
                        addElement("item-" + String.valueOf(i) + "-" + String.valueOf(j), "item-" + String.valueOf(i), j, false);
                    }
                }

                switchToPropertiesTab(ROOT_NAME);
                setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
                for (int i = 0; i < size; i++) {
                    switchToPropertiesTab("item-" + String.valueOf(i));
                    setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
                }
            }
        }, Mode.TEST, new StateVerificator() {
            @Override
            public boolean verify(List<NodeCounterChecker.Measurement> telemetry, int currentIteration) {
                return ((double) telemetry.get(currentIteration).nodesCount / telemetry.get(0).nodesCount < 1.05);
            }
        }).start();
    }

    @Test
    public void onScrollingNodesLeakTest() throws InterruptedException {
        setSize(220, 220);
        final int sizeLevel1 = 10;
        final int sizeLevel2 = 1000;

        for (int i = 0; i < sizeLevel1; i++) {
            addElement("item-" + String.valueOf(i), ROOT_NAME, i, true);
            for (int j = 0; j < sizeLevel2; j++) {
                addElement("item-" + String.valueOf(i) + "-" + String.valueOf(j), "item-" + String.valueOf(i), j, false);
            }
        }

        switchToPropertiesTab(ROOT_NAME);
        setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        for (int i = 0; i < sizeLevel1; i++) {
            switchToPropertiesTab("item-" + String.valueOf(i));
            setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        }

        final Wrap<? extends ScrollBar> vertical = findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true);

        new NodeCounterChecker(testedControl, 300, 1, new Runnable() {
            public void run() {
                AbstractScroll vert = vertical.as(AbstractScroll.class);
                vert.to(vert.maximum());
                vert.to(vert.minimum());
            }
        }, Mode.TEST, new StateVerificator() {
            @Override
            public boolean verify(List<NodeCounterChecker.Measurement> telemetry, int currentIteration) {
                return ((double) telemetry.get(currentIteration).nodesCount / telemetry.get(0).nodesCount < 1.05);
            }
        }).start();
    }
}
