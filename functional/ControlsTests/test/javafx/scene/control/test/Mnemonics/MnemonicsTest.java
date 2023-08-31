/*
 * Copyright (c) 2014, 2023 Oracle and/or its affiliates. All rights reserved.
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
package javafx.scene.control.test.Mnemonics;


import java.util.Arrays;
import javafx.factory.ControlsFactory;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.test.util.PropertyGridHelper;
import javafx.scene.control.test.util.BooleanPropertyChanger;
import javafx.scene.control.test.util.PropertyHelper;
import javafx.scene.control.test.utils.PropertyCheckingGrid;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Selectable;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

@RunWith(FilteredTestRunner.class)
public class MnemonicsTest extends MnemonicsTestBase {
    protected static Wrap<? extends ChoiceBox> choice;

    @BeforeClass
    public static void setUpClass() throws Exception {
        MnemonicsApp.main(null);
        MnemonicsTestBase.setUpClass();
        choice = sceneAsParent.lookup(ChoiceBox.class).wrap();
        PropertyGridHelper.addChanger(Labeled.class, "mnemonicParsing", new BooleanPropertyChanger(false, Boolean.TRUE, Arrays.asList(Boolean.TRUE, Boolean.FALSE)) {
            public void changeByUI(Wrap<?> obj, Boolean value) {
                throw new UnsupportedOperationException("Not supported.");
            }
        });
        PropertyGridHelper.addChanger(Label.class, "mnemonicParsing", new BooleanPropertyChanger(false, Boolean.FALSE, Arrays.asList(Boolean.TRUE, Boolean.FALSE)) {
            public void changeByUI(Wrap<?> obj, Boolean value) {
                throw new UnsupportedOperationException("Not supported.");
            }
        });
        PropertyGridHelper.addChanger(Hyperlink.class, "mnemonicParsing", new BooleanPropertyChanger(false, Boolean.FALSE, Arrays.asList(Boolean.TRUE, Boolean.FALSE)) {
            public void changeByUI(Wrap<?> obj, Boolean value) {
                throw new UnsupportedOperationException("Not supported.");
            }
        });
    }

    //TODO@Smoke
    @Test(timeout = 300000)
    public void propertyTest() throws Throwable {
        final Selectable<ControlsFactory> selectable = choice.as(Selectable.class);
        for (ControlsFactory cl : selectable.getStates()) {
            selectable.selector().select(cl);
            Wrap<? extends Labeled> item = sceneAsParent.lookup(Labeled.class, new ByID(MnemonicsApp.LABELED_ID)).wrap();
            PropertyGridHelper<Labeled> propertyTableHelper = new PropertyGridHelper<Labeled>(item, sceneAsParent.lookup(PropertyCheckingGrid.class).wrap());
            PropertyHelper<Boolean, Labeled> propertyHelper = propertyTableHelper.getPropertyHelper(Boolean.class, "mnemonicParsing");
            propertyHelper.checkProperty(false);

            removeFocus(item);
            propertyHelper.setValue(false);
            checkUnderline(item, false);
            final KeyboardButton button = getButton(item);
            scene.keyboard().pushKey(button, mod);
            item.waitProperty("isFocused", Boolean.FALSE);
            try {
                propertyHelper.setValue(true);
                if (isLinux) {
                    scene.keyboard().pressKey(KeyboardButtons.ALT);
                } else {
                    scene.keyboard().pushKey(KeyboardButtons.ALT);
                }
                checkUnderline(item, true);
                if (isLinux) {
                    scene.keyboard().releaseKey(KeyboardButtons.ALT);
                }
                scene.keyboard().pushKey(button, mod);

                item.waitProperty("isFocused", Boolean.TRUE);
            } catch (Throwable th) {
                throw th;
            } finally {
                scene.keyboard().pushKey(KeyboardButtons.ALT);
            }
        }
    }
}
