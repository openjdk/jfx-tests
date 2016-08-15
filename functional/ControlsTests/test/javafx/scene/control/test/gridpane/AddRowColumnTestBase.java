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
package javafx.scene.control.test.gridpane;

import java.util.Set;
import javafx.collections.ObservableList;
import javafx.factory.ControlsFactory;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import static javafx.scene.control.test.gridpane.AddRowColumnApp.*;
import javafx.scene.control.test.util.UtilTestFunctions;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Andrey Glushchenko
 */
@RunWith(FilteredTestRunner.class)
public class AddRowColumnTestBase extends UtilTestFunctions {

    private Wrap<? extends GridPane> oldGrid;
    private Wrap<? extends GridPane> testedGridPane = null;
    private Wrap<? extends VBox> vBox = null;
    private Wrap<? extends ChoiceBox> cbInside = null;
    private Wrap<? extends ChoiceBox> cbOutside = null;
    private static Wrap<? extends Scene> scene;

    @BeforeClass
    public static void runUI() {
        AddRowColumnApp.main(null);
    }

    @Before
    public void initTest() throws InterruptedException {
        lookupControls();
    }

    protected void chooseExistingElement(ControlsFactory elem) {
        selectObjectFromChoiceBox(cbInside, elem);
    }

    protected void chooseCreatingElement(ControlsFactory elem) {
        selectObjectFromChoiceBox(cbOutside, elem);
    }

    protected void recreateGrid() throws InterruptedException {
        oldGrid = testedGridPane;
        clickButtonForTestPurpose(BUTTON_REGENERATE_ID);
        lookupGrid();
    }

    protected void addRow() throws InterruptedException {
        oldGrid = testedGridPane;
        clickButtonForTestPurpose(BUTTON_ADD_ROW_ID);
        lookupGrid();
    }

    protected void addColumn() throws InterruptedException {
        oldGrid = testedGridPane;
        clickButtonForTestPurpose(BUTTON_ADD_COLUMN_ID);
        lookupGrid();
    }

    protected void test_addColumn(final ControlsFactory exElem, final ControlsFactory crElem) throws InterruptedException, Exception {
        chooseCreatingElement(crElem);
        addColumn();
        Exception ex = new GetAction<Exception>() {
            @Override
            public void run(Object... os) throws Exception {
                ObservableList<Node> list = testedGridPane.getControl().getChildren();
                if (!(2 == list.size())) {
                    setResult(new Exception("GridPane has to many children! ( " + list.size() + " )"));
                    return;
                }
                for (Node item : list) {
                    if (GridPane.getColumnIndex(item) == 0) {
                        if (GridPane.getRowIndex(item) == 0) {
                            if (!((EXISTING_ELEMENT_ID + exElem.name()).equals(item.getId()))) {
                                setResult(new Exception("IDs are not equivalent"));
                                return;
                            }
                        } else {
                            if (GridPane.getRowIndex(item) == 1) {
                                if (!item.getId().equals(CREATING_ELEMENT_ID + crElem.name())) {
                                    setResult(new Exception("IDs are not equivalent"));
                                    return;
                                }
                            } else {
                                setResult(new Exception("Unexpected Element Index! Col - 0 Row - "
                                        + GridPane.getRowIndex(item)));
                                return;
                            }
                        }
                    } else {
                        setResult(new Exception("Unexpected Element Index! Col - "
                                + GridPane.getColumnIndex(item) + " Row - "
                                + GridPane.getRowIndex(item) + " " + item));
                        return;
                    }
                }
                setResult(null);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        if (ex != null) {
            throw ex;
        }
    }

    protected void test_addRow(final ControlsFactory exElem, final ControlsFactory crElem) throws InterruptedException, Exception {
        chooseCreatingElement(crElem);
        addRow();
        Exception ex = new GetAction<Exception>() {
            @Override
            public void run(Object... os) throws Exception {
                ObservableList<Node> list = testedGridPane.getControl().getChildren();
                if (!(2 == list.size())) {
                    setResult(new Exception("GridPane has to many children! ( " + list.size() + " )"));
                    return;
                }
                for (Node item : list) {
                    if (GridPane.getRowIndex(item) == 0) {
                        if (GridPane.getColumnIndex(item) == 0) {
                            if (!((EXISTING_ELEMENT_ID + exElem.name()).equals(item.getId()))) {
                                setResult(new Exception("IDs are not equivalent"));
                                return;
                            }
                        } else {
                            if (GridPane.getColumnIndex(item) == 1) {
                                if (!(CREATING_ELEMENT_ID + crElem.name()).equals(item.getId())) {
                                    setResult(new Exception("IDs are not equivalent"));
                                    return;
                                }
                            } else {
                                setResult(new Exception("Unexpected Element Index! Col - "
                                        + GridPane.getColumnIndex(item) + " Row - 0"));
                                return;
                            }
                        }
                    } else {
                        setResult(new Exception("Unexpected Element Index! Col - "
                                + GridPane.getColumnIndex(item) + " Row - "
                                + GridPane.getRowIndex(item)));
                        return;
                    }
                }
                setResult(null);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        if (ex != null) {
            throw ex;
        }
    }

    private void lookupGrid() throws InterruptedException {
        testedGridPane = parent.lookup(GridPane.class, new ByID<GridPane>(LOCAL_GRID_ID)).wrap();
        new Waiter(Wrap.WAIT_STATE_TIMEOUT).ensureState(new State() {
            @Override
            public Object reached() {
                if (oldGrid != null) {
                    if (testedGridPane.getControl().equals(oldGrid.getControl())) {
                        testedGridPane = parent.lookup(GridPane.class, new ByID<GridPane>(LOCAL_GRID_ID)).wrap();
                        return null;
                    }
                }
                return new GetAction<Boolean>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        Bounds b = testedGridPane.getControl().getLayoutBounds();
                        if (b.getHeight() <= 0 || b.getWidth() <= 0) {
                            setResult(null);
                            return;
                        }
                        ObservableList<Node> childs = testedGridPane.getControl().getChildrenUnmodifiable();
                        for (Node child : childs) {
                            b = child.getLayoutBounds();
                            if (b.getHeight() <= 0 || b.getWidth() <= 0) {
                                setResult(null);
                                return;
                            }
                            if (b.getMinX() < 0 || b.getMinY() < 0) {
                                setResult(null);
                                return;
                            }
                        }
                        setResult(Boolean.TRUE);
                    }
                }.dispatch(Root.ROOT.getEnvironment());
            }
        });
    }

    private void lookupControls() throws InterruptedException {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        lookupGrid();
        vBox = parent.lookup(VBox.class, new ByID<VBox>(LOCAL_VBOX_ID)).wrap();
        cbInside = parent.lookup(ChoiceBox.class, new ByID<ChoiceBox>(CHOICE_INSIDE_ID)).wrap();
        cbOutside = parent.lookup(ChoiceBox.class, new ByID<ChoiceBox>(CHOICE_OUTSIDE_ID)).wrap();
    }

    private void checkBounds() throws Exception {
        Exception ex = new GetAction<Exception>() {
            @Override
            public void run(Object... os) throws Exception {
                ObservableList<Node> list = testedGridPane.getControl().getChildren();
                Bounds gridPaneBoundsInParent = testedGridPane.getControl().getBoundsInParent();
                for (Node item1 : list) {
                    Bounds bPar1 = item1.localToScene(item1.getLayoutBounds());
                    for (Node item2 : list) {
                        if (item1 != item2) {
                            Bounds bPar2 = item2.localToScene(item2.getLayoutBounds());
                            if (bPar1.intersects(bPar2)) {
                                System.out.println(bPar1);
                                System.out.println(bPar2);
                                setResult(new Exception("Elements are intersect!(" + item1.getClass() + " and " + item2.getClass() + ")"));
                                return;
                            }
                            if (!gridPaneBoundsInParent.contains(bPar2)) {
                                System.out.println(gridPaneBoundsInParent);
                                System.out.println(bPar2);
                            }
                            if (!gridPaneBoundsInParent.contains(bPar2)) {
                                setResult(new Exception("Element outside the Grid!(" + item2.getClass() + ")"));
                                return;
                            }
                        }
                    }
                    if (!gridPaneBoundsInParent.contains(bPar1)) {
                        setResult(new Exception("Element outside the Grid!(" + item1.getClass() + ")"));
                        return;
                    }
                }
                if (gridPaneBoundsInParent.intersects(vBox.getControl().getBoundsInParent())) {
                    setResult(new Exception("Grid intersects VBox! (" + list + ")"));
                    return;
                }
                setResult(null);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        if (ex != null) {
            throw ex;
        }
    }

    protected void test_addRowColumn(ControlsFactory factory) throws InterruptedException, Exception {
        chooseExistingElement(factory);
        recreateGrid();
        Set<ControlsFactory> cf = AddRowColumnApp.getControlsSet();
        for (ControlsFactory control : cf) {
            test_addRow(factory, control);
            checkBounds();
            test_addColumn(factory, control);
            checkBounds();
        }
    }
}
