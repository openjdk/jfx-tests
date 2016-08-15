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

import javafx.factory.ControlsFactory;
import org.junit.Test;

/**
 *
 * @author Andrey Glushchenko
 */
public class AddRowColumnTest extends AddRowColumnTestBase{

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnTabPanesTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.TabPanes);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnTreeTableViewsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.TreeTableViews);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnHyperlinksTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.Hyperlinks);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnAccordionsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.Accordions);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnListViewsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.ListViews);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnToolbarsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.Toolbars);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnButtonsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.Buttons);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnUnPressedToggleButtonsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.UnPressedToggleButtons);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnProgressIndicatorsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.ProgressIndicators);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnLabelsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.Labels);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnScrollBarsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.ScrollBars);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnDatePickersTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.DatePickers);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnPasswordFieldsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.PasswordFields);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnSlidersTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.Sliders);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnChoiceBoxesTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.ChoiceBoxes);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnSplitMenuButtonsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.SplitMenuButtons);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnProgressBarsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.ProgressBars);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnScrollPanesTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.ScrollPanes);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnTitledPanesTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.TitledPanes);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnSeparatorsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.Separators);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnPaginationsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.Paginations);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnTextFieldsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.TextFields);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnColorPickersTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.ColorPickers);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnTreeViewsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.TreeViews);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnComboBoxesTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.ComboBoxes);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnTableViewsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.TableViews);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnSplitPanesTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.SplitPanes);
    }

    /**
     * Test for GridPane addRow and addColumn
    **/
    @Test(timeout=60000)
    public void addRowColumnPressedToggleButtonsTest() throws InterruptedException, Exception {
        test_addRowColumn(ControlsFactory.PressedToggleButtons);
    }
}
