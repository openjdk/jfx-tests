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
package javafx.scene.control.test.textinput;

import client.test.Smoke;
import java.util.EnumSet;
import javafx.scene.control.TextField;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.scene.text.Font;
import org.jemmy.Rectangle;
import org.jemmy.timing.State;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class TextFieldPropertiesTest extends TextControlCommonTests {

    @BeforeClass
    public static void setUpClass() throws Exception {
        TextFieldPropertiesApp.main(null);
    }

    @Smoke
    @Test(timeout = 300000)
    public void prefColumnCountPropertyTest() throws InterruptedException {
        assertEquals(getNewControl().prefColumnCountProperty().getValue(), 12, 0);
        prefColumnCountCommonTest();
    }

    @Smoke
    @Test(timeout = 300000)
    public void onActionPropertyTest() {
        onActionPropertyCommonTest();
    }

    @Override
    protected TextField getNewControl() {
        return new TextField();
    }

    @Smoke
    @Test(timeout = 300000)//RT-28547
    /**
     * Test checks, that size of control depends (weak check) correctly on the
     * size of font.
     */
    public void changingSizeOnFontChangeTest() throws InterruptedException {
        removeStylesheet();
        setPropertyByTextField(AbstractPropertyController.SettingType.SETTER, Properties.text, "Text");

        for (AbstractPropertyController.SettingType type : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            selectObjectFromChoiceBox(type, Properties.font, new Font(10));

            //Wait, while textInput will be resized.
            Thread.sleep(1000);

            final Rectangle initBounds = testedControl.getScreenBounds();
            //Font becomes larger, control will increase the size.
            selectObjectFromChoiceBox(type, Properties.font, new Font(20));

            testedControl.waitState(new State() {
                public Object reached() {
                    Rectangle boundsAfter = testedControl.getScreenBounds();
                    if (boundsAfter.contains(initBounds) && !boundsAfter.equals(initBounds)) {
                        return true;
                    }
                    return null;
                }
            });

            //Font becomes as before, control will be expected, to resize, to be as before.
            selectObjectFromChoiceBox(type, Properties.font, new Font(10));

            testedControl.waitState(new State() {
                public Object reached() {
                    Rectangle boundsAfter = testedControl.getScreenBounds();
                    if (boundsAfter.equals(initBounds)) {
                        return true;
                    }
                    return null;
                }
            });
        }
        restoreStylesheet();
    }
}