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

package javafx.scene.control.test.focus;

import client.test.Keywords;
import javafx.factory.ControlsFactory;
import org.junit.Test;

/**
 *
 * @author Andrey Glushchenko
 */
public class FocusUniquenessRequestTest extends FocusRequestTestBase {
    private void test(String name) throws Exception{
        openPage(name);
        focusUniquenessTest();
    }

    /**
     *  for Buttons
    **/
    @Test(timeout=20000)
    public void test_Buttons() throws Exception {
        test(ControlsFactory.Buttons.name());
    }

    /**
     *  for ChoiceBoxes
    **/
    @Test(timeout=20000)
    public void test_ChoiceBoxes() throws Exception {
        test(ControlsFactory.ChoiceBoxes.name());
    }

    /**
     *  for ComboBoxes
    **/
    @Test(timeout=20000)
    public void test_ComboBoxes() throws Exception {
        test(ControlsFactory.ComboBoxes.name());
    }

    /**
     *  for EditableComboBoxes
    **/
    @Test(timeout=20000)
    public void test_EditableComboBoxes() throws Exception {
        test(ControlsFactory.EditableComboBoxes.name());
    }

    /**
     *  for Paginations
    **/
    @Test(timeout=20000)
    public void test_Paginations() throws Exception {
        test(ControlsFactory.Paginations.name());
    }

    /**
     *  for ColorPickers
    **/
    @Test(timeout=20000)
    public void test_ColorPickers() throws Exception {
        test(ControlsFactory.ColorPickers.name());
    }

    /**
     *  for CheckBoxes
    **/
    @Test(timeout=20000)
    public void test_CheckBoxes() throws Exception {
        test(ControlsFactory.CheckBoxes.name());
    }

    /**
     *  for RadioButtons
    **/
    @Test(timeout=20000)
    public void test_RadioButtons() throws Exception {
        test(ControlsFactory.RadioButtons.name());
    }

    /**
     *  for TextFields
    **/
    @Test(timeout=20000)
    public void test_TextFields() throws Exception {
        test(ControlsFactory.TextFields.name());
    }

    /**
     *  for TextAreas
    **/
    @Test(timeout=20000)
    public void test_TextAreas() throws Exception {
        test(ControlsFactory.TextAreas.name());
    }

    /**
     *  for PasswordFields
    **/
    @Test(timeout=20000)
    public void test_PasswordFields() throws Exception {
        test(ControlsFactory.PasswordFields.name());
    }

    /**
     *  for Sliders
    **/
    @Test(timeout=20000)
    public void test_Sliders() throws Exception {
        test(ControlsFactory.Sliders.name());
    }

    /**
     *  for Labels
    **/
    @Test(timeout=20000)
    public void test_Labels() throws Exception {
        test(ControlsFactory.Labels.name());
    }

    /**
     *  for Hyperlinks
    **/
    @Test(timeout=20000)
    public void test_Hyperlinks() throws Exception {
        test(ControlsFactory.Hyperlinks.name());
    }

    /**
     *  for ImageView
    **/
    @Test(timeout=20000)
    public void test_ImageView() throws Exception {
        test(ControlsFactory.ImageView.name());
    }

    /**
     *  for MediaView
    **/
    @Test(timeout=20000)
    @Keywords(keywords = "media")
    public void test_MediaView() throws Exception {
        test(ControlsFactory.MediaView.name());
    }

    /**
     *  for Separators
    **/
    @Test(timeout=20000)
    public void test_Separators() throws Exception {
        test(ControlsFactory.Separators.name());
    }

    /**
     *  for ScrollBars
    **/
    @Test(timeout=20000)
    public void test_ScrollBars() throws Exception {
        test(ControlsFactory.ScrollBars.name());
    }

    /**
     *  for ScrollPanes
    **/
    @Test(timeout=20000)
    public void test_ScrollPanes() throws Exception {
        test(ControlsFactory.ScrollPanes.name());
    }

    /**
     *  for ProgressIndicators
    **/
    @Test(timeout=20000)
    public void test_ProgressIndicators() throws Exception {
        test(ControlsFactory.ProgressIndicators.name());
    }

    /**
     *  for ProgressBars
    **/
    @Test(timeout=20000)
    public void test_ProgressBars() throws Exception {
        test(ControlsFactory.ProgressBars.name());
    }

    /**
     *  for PressedToggleButtons
    **/
    @Test(timeout=20000)
    public void test_PressedToggleButtons() throws Exception {
        test(ControlsFactory.PressedToggleButtons.name());
    }

    /**
     *  for UnPressedToggleButtons
    **/
    @Test(timeout=20000)
    public void test_UnPressedToggleButtons() throws Exception {
        test(ControlsFactory.UnPressedToggleButtons.name());
    }

    /**
     *  for SplitMenuButtons
    **/
    @Test(timeout=20000)
    public void test_SplitMenuButtons() throws Exception {
        test(ControlsFactory.SplitMenuButtons.name());
    }

    /**
     *  for TabPanes
    **/
    @Test(timeout=20000)
    public void test_TabPanes() throws Exception {
        test(ControlsFactory.TabPanes.name());
    }

    /**
     *  for TitledPanes
    **/
    @Test(timeout=20000)
    public void test_TitledPanes() throws Exception {
        test(ControlsFactory.TitledPanes.name());
    }

    /**
     *  for SplitPanes
    **/
    @Test(timeout=20000)
    public void test_SplitPanes() throws Exception {
        test(ControlsFactory.SplitPanes.name());
    }

    /**
     *  for DatePickers
    **/
    @Test(timeout=20000)
    public void test_DatePickers() throws Exception {
        test(ControlsFactory.DatePickers.name());
    }
}
