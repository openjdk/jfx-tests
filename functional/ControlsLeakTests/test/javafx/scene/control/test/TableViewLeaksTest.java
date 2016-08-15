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
import javafx.scene.control.test.tableview.ApplicationInteractionFunctions;
import javafx.scene.control.test.util.perfomance.NodeCounterChecker;
import javafx.scene.control.test.util.perfomance.NodeCounterChecker.Measurement;
import javafx.scene.control.test.util.perfomance.NodeCounterChecker.Mode;
import javafx.scene.control.test.util.perfomance.StateVerificator;
import org.jemmy.control.Wrap;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.Parent;
import org.junit.Test;

/**
 * @author Alexander Kirov
 */
public class TableViewLeaksTest extends ApplicationInteractionFunctions {

    @Test
    public void onScrollingLeakTest() throws InterruptedException {
        provideSpaceForControl(520, 520, false);
        setSize(520, 520);
        final int columns = 20;
        final int rows = 20000;
        for (int i = 0; i < columns; i++) {
            addColumn("items" + String.valueOf(i), i);
        }
        setNewDataSize(rows);

        final Wrap<? extends ScrollBar> vertical = findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true);
        final Wrap<? extends ScrollBar> horizontal = findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true);

        new NodeCounterChecker(testedControl, 500, 1, new Runnable() {
            public void run() {
                AbstractScroll vert = vertical.as(AbstractScroll.class);
                AbstractScroll hor = horizontal.as(AbstractScroll.class);

                vert.to(vert.maximum());
                hor.to(hor.maximum());
                vert.to(vert.minimum());
                hor.to(hor.minimum());
            }
        }, Mode.TEST, new StateVerificator() {
            @Override
            public boolean verify(List<Measurement> telemetry, int currentIteration) {
                return ((double) telemetry.get(currentIteration).nodesCount / telemetry.get(0).nodesCount < 1.01);
            }
        }).start();
    }

    @Test
    public void onDataAddingRemovingAreaLeakTest() throws InterruptedException {
        provideSpaceForControl(520, 520, false);
        setSize(520, 520);
        final int columns = 10;
        final int rows = 10000;
        for (int i = 0; i < columns; i++) {
            addColumn("items" + String.valueOf(i), i);
        }
        setNewDataSize(rows);

        final Wrap<? extends ScrollBar> vertical = findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true);

        new NodeCounterChecker(testedControl, 500, 1, new Runnable() {
            public void run() {
                setNewDataSize(0);
                setNewDataSize(rows);

                AbstractScroll vert = vertical.as(AbstractScroll.class);

                vert.to(vert.maximum());
                vert.to(vert.minimum());
            }
        }, Mode.TEST, new StateVerificator() {
            @Override
            public boolean verify(List<Measurement> telemetry, int currentIteration) {
                return ((double) telemetry.get(currentIteration).nodesCount / telemetry.get(0).nodesCount <= 1.05);
            }
        }).start();
    }
}
