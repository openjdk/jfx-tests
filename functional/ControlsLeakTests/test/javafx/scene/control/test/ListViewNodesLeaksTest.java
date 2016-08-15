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

import java.util.Collection;
import java.util.List;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollBar;
import static javafx.scene.control.test.ListView.NewListViewApp.*;
import javafx.scene.control.test.ListView.TestBase;
import javafx.scene.control.test.util.perfomance.NodeCounterChecker;
import javafx.scene.control.test.util.perfomance.NodeCounterChecker.Mode;
import javafx.scene.control.test.util.perfomance.StateVerificator;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.Parent;
import org.junit.Test;

/**
 * @author Alexander Kirov
 */
public class ListViewNodesLeaksTest extends TestBase {

    @Test
    public void editingLeaksTest() throws InterruptedException {
        setSize(220, 220);

        addElements(1, 2, 3, 4, 5);

        new NodeCounterChecker(testedControl, 100, 0, new Runnable() {
            public void run() {
                final Wrap<? extends ComboBox> choice = (Wrap<? extends ComboBox>) findControl(LIST_FACTORY_CHOICE_ID);
                Collection items = new GetAction<Collection>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(choice.getControl().getItems());
                    }
                }.dispatch(Root.ROOT.getEnvironment());

                for (final Object factory : items) {
                    new GetAction() {
                        @Override
                        public void run(Object... os) throws Exception {
                            choice.getControl().getSelectionModel().select(factory);
                        }
                    }.dispatch(Root.ROOT.getEnvironment());
                }
            }
        }, Mode.TEST, new StateVerificator() {
            @Override
            public boolean verify(List<NodeCounterChecker.Measurement> telemetry, int currentIteration) {
                System.out.println(currentIteration + " " + telemetry.get(currentIteration).nodesCount);
                return (double) telemetry.get(currentIteration).nodesCount / telemetry.get(0).nodesCount < 1.05;
            }
        }).start();
    }

    /**
     * Adds very many controls in list view and scrolls up and down.
     */
    @Test
    public void allControlsInListLeakTest() throws InterruptedException {
        setSize(220, 220);
        final int size = 10;
        final int controlsFactorySize = controlsFactorySize();

        for (int i = 0; i < size; i++) {
            for (int control = 0; control < controlsFactorySize; control++) {
                addControlToPosition(control, 0);
            }
        }

        final Wrap<? extends ScrollBar> vertical = findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true);

        new NodeCounterChecker(testedControl,
                500, 1, new Runnable() {
            public void run() {
                AbstractScroll vert = vertical.as(AbstractScroll.class);
                vert.to(vert.maximum());
                vert.to(vert.minimum());
            }
        }, Mode.TEST,
                new StateVerificator() {
                    @Override
                    public boolean verify(List<NodeCounterChecker.Measurement> telemetry, int currentIteration) {
                        return (double) telemetry.get(currentIteration).nodesCount / telemetry.get(0).nodesCount < 1.05;
                    }
                }).start();
    }

    @Test
    public void onScrollingNodesLeakTest() throws InterruptedException {
        setSize(220, 220);
        final int size = 10000;

        for (int i = 0; i < size; i++) {
            addElement("item-" + String.valueOf(i), 0);
            if (i < size / 2) {
                if (i % 13 == 0) {
                    addFormAtPos(0);
                }
                if (i % 17 == 0) {
                    addRectangleAtPos(0);
                }
                if (i % 11 == 0) {
                    addTextFieldAtPos(0);
                }
            }
        }

        final Wrap<? extends ScrollBar> vertical = findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true);

        new NodeCounterChecker(testedControl,
                500, 1, new Runnable() {
            public void run() {
                AbstractScroll vert = vertical.as(AbstractScroll.class);
                vert.to(vert.maximum());
                vert.to(vert.minimum());
            }
        }, Mode.TEST,
                new StateVerificator() {
                    @Override
                    public boolean verify(List<NodeCounterChecker.Measurement> telemetry, int currentIteration) {
                        return (telemetry.get(currentIteration).nodesCount < 100) && ((double) telemetry.get(currentIteration).nodesCount / telemetry.get(0).nodesCount < 1.05);
                    }
                }).start();
    }

    @Test
    public void dataAddingRemovingNodesLeakTest() throws InterruptedException {
        setSize(220, 220);
        final int size = 10000;

        for (int i = 0; i < size; i++) {
            addElement("item-" + String.valueOf(i), 0);
            if (i < size / 2) {
                if (i % 13 == 0) {
                    addFormAtPos(0);
                }
                if (i % 17 == 0) {
                    addRectangleAtPos(0);
                }
                if (i % 11 == 0) {
                    addTextFieldAtPos(0);
                }
            }
        }

        new NodeCounterChecker(testedControl, 500, 1, new Runnable() {
            public void run() {
                for (int i = 0; i < size; i++) {
                    removeFromPos(0);
                }

                for (int i = 0; i < size; i++) {
                    addElement("item-" + String.valueOf(i), 0);
                    if (i < size / 2) {
                        if (i % 13 == 0) {
                            addFormAtPos(0);
                        }
                        if (i % 17 == 0) {
                            addRectangleAtPos(0);
                        }
                        if (i % 11 == 0) {
                            addTextFieldAtPos(0);
                        }
                    }
                }
            }
        }, Mode.TEST, new StateVerificator() {
            @Override
            public boolean verify(List<NodeCounterChecker.Measurement> telemetry, int currentIteration) {
                return (telemetry.get(currentIteration).nodesCount < 100) && ((double) telemetry.get(currentIteration).nodesCount / telemetry.get(0).nodesCount < 1.05);
            }
        }).start();
    }
}
