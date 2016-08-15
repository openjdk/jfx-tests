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
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.test.textinput.undo.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
@RunWith(FilteredTestRunner.class)
public class TextAreaUndoRedoTest extends UndoRedoBaseTests {

    @BeforeClass
    public static void setUpClass() throws Exception {
        TextAreaPropertiesApp.main(null);
    }

    @Before
    public void textAreaBeforeAction() throws InterruptedException {
        setSize(200, 50);
    }

    @Smoke
    @Test
    public void MultilineUndo() throws InterruptedException {
        List<Change> changes = new ArrayList<Change>();

        changes.add(new TypeCharsChange("line 1"));
        changes.add(TypeSpecSharChange.ENTER);
        changes.add(new TypeCharsChange("line 2"));
        changes.add(TypeSpecSharChange.ENTER);
        changes.add(new TypeCharsChange("line 3"));
        changes.add(new UndoChange());
        changes.add(new UndoChange());
        changes.add(new UndoChange());

        for (int i = 3; i > 0; --i) {
            Assert.assertTrue("Test edit chain. See output", testChanges(String.format("Multiline undo (%d)", i), changes.subList(0, changes.size() - i)));
        }
    }

    @Smoke
    @Test
    public void DeleteLineUndo() throws InterruptedException {
        List<Change> changes = new ArrayList<Change>();

        changes.add(new TypeCharsChange("line 1"));
        changes.add(TypeSpecSharChange.ENTER);
        changes.add(new TypeCharsChange("line 2"));
        changes.add(TypeSpecSharChange.ENTER);
        changes.add(new BackspaceChange());
        changes.add(new BackspaceChange());
        changes.add(new UndoChange());
        changes.add(new UndoChange());

        Assert.assertTrue("Test edit chain. See output", testChanges("Undo deletion line", changes));
    }
}
