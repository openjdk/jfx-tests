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
package javafx.scene.control.test.util;

import javafx.scene.control.TextField;
import static javafx.scene.control.test.utils.ptables.AbstractPropertyController.*;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import static org.junit.Assert.*;

/**
 * Interface contains classes where each represents a test
 * which could be applied to several controls.
 *
 * @author Dmitry Zinkevich
 */
public interface PropertyTest {

    static enum Properties {
        editable
    }

    void test();

    /**
     * Test of editor property for such controls as ComboBox or DatePicker.
     * It checks that TextField returned by getEditor() method remains the same after
     * changing editable property
     */
    static class EditorPropertyTest implements PropertyTest {

        final Wrap testedControl;
        final GetAction<TextField> getEditorAction;

        /**
         * @param testedControl wrapped control. It is expected to have property 'editable' and 'editor'.
         */
        public EditorPropertyTest(Wrap testedControl) {
            if (null == testedControl) {
                throw new IllegalArgumentException("Wrap is null");
            }

            this.testedControl = testedControl;

            getEditorAction = new GetAction<TextField>() {
                @Override
                public void run(Object... os) throws Exception {
                    Object control = EditorPropertyTest.this.testedControl.getControl();
                    setResult((TextField) control.getClass().getMethod("getEditor").invoke(control));
                }
            };
        }

        public void test() {
            UtilTestFunctions.setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable);

            TextField editorOld = getEditor();

            UtilTestFunctions.setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable, false);
            UtilTestFunctions.setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable);

            TextField editorNew = getEditor();

            assertEquals(editorNew, editorOld);
        }

        TextField getEditor() {
            TextField editor = getEditorAction.dispatch(testedControl.getEnvironment());

            assertTrue("[Cannot access editor getter]", null != editor);

            return editor;
        }
    }

    /**
     * Test of the editor parent property for such controls as ComboBox or DatePicker.
     * It checks that TextFields parent remains the same after
     * changing editable property
     */
    static class EditorParentPropertyTest extends EditorPropertyTest {

        final GetAction<javafx.scene.Parent> getEditorParentAction;
        final TextField editor;

        public EditorParentPropertyTest(Wrap testedControl) {
            super(testedControl);

            editor = getEditorAction.dispatch(testedControl.getEnvironment());

            getEditorParentAction = new GetAction<javafx.scene.Parent>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(EditorParentPropertyTest.this.editor.getParent());
                }
            };
        }

        @Override
        public void test() {
            UtilTestFunctions.setPropertyByToggleClick(SettingType.SETTER, Properties.editable, Boolean.TRUE);

            javafx.scene.Parent parentOld = getEditorParentAction.dispatch(testedControl.getEnvironment());

            UtilTestFunctions.setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable, Boolean.FALSE);
            assertNull("[Editor parent must be null]", getEditorParentAction.dispatch(testedControl.getEnvironment()));

            UtilTestFunctions.switchOffBinding(SettingType.UNIDIRECTIONAL, Properties.editable);
            UtilTestFunctions.setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable, Boolean.TRUE);

            javafx.scene.Parent parentNew = getEditorParentAction.dispatch(testedControl.getEnvironment());
            assertSame("[Parent property wasn't expected to change]", parentNew, parentOld);

            UtilTestFunctions.setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable, Boolean.FALSE);
            assertNull("[Editor parent must be null]", getEditorParentAction.dispatch(testedControl.getEnvironment()));
        }
    }
}
