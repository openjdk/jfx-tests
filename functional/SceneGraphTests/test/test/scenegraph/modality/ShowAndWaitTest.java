/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
 */
package test.scenegraph.modality;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import junit.framework.Assert;
import org.jemmy.fx.ByID;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.junit.Before;
import org.junit.Test;
import test.scenegraph.modality.ModalityWindow.WindowsRenderType;

/**
 * Test for Stage.show() & Stage.showAndWait() API
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
public class ShowAndWaitTest extends ModalityBase {

    @Before
    public void setUp() {
        setUpTyped(WindowsRenderType.LIST);
    }

    @Test
    public void show_ModalityNONE_Test() throws InterruptedException {
        createSubstage("stage0", ModalityWindow.TestCase.NONE, false, false);
        Assert.assertFalse("Procces blocked at stage.show()", isBlocked("stage1"));
    }

    @Test
    public void show_ModalityAPP_Test() throws InterruptedException {
        createSubstage("stage0", ModalityWindow.TestCase.APP, false, false);
        Assert.assertFalse("Procces blocked at stage.show()", isBlocked("stage1"));
    }

    @Test
    public void show_ModalityWIN_Test() throws InterruptedException {
        createSubstage("stage0", ModalityWindow.TestCase.WIN, false, false);
        Assert.assertFalse("Procces blocked at stage.show()", isBlocked("stage1"));
    }

    @Test
    public void showAndWait_ModalityNONE_Test() throws InterruptedException {
        createSubstage("stage0", ModalityWindow.TestCase.NONE, false, true);
        Assert.assertTrue("Procces blocked at stage.show()", isBlocked("stage1"));
    }

    @Test
    public void showAndWait_ModalityAPP_Test() throws InterruptedException {
        createSubstage("stage0", ModalityWindow.TestCase.APP, false, true);
        Assert.assertTrue("Procces blocked at stage.show()", isBlocked("stage1"));
    }

    @Test
    public void showAndWait_ModalityWIN_Test() throws InterruptedException {
        createSubstage("stage0", ModalityWindow.TestCase.WIN, false, true);
        Assert.assertTrue("Procces blocked at stage.show()", isBlocked("stage1"));
    }

    @Test
    public void showAndWait_closeOwner_Test() throws InterruptedException {
        createSubstage("stage0", ModalityWindow.TestCase.NONE, true, false);
        createSubstage("stage1", ModalityWindow.TestCase.NONE, true, true);

        tryToCloseByButton("stage1");

        Assert.assertFalse("Procces blocked at stage.show()", isSceneExists("stage2"));
    }

    private void createSubstage(final String stageID, ModalityWindow.TestCase type, boolean setSceneAsParent, boolean isShowAndWait) {
        Parent<Node> parent = getSceneWrapAsParent(stageID);
        parent.lookup(CheckBox.class, new ByID<CheckBox>(ModalityWindow.SHOWANDWAIT_CHECKBOX_ID)).wrap().as(Selectable.class, Boolean.class).selector().select(isShowAndWait);

        createSubstage(stageID, type, setSceneAsParent);
    }

    private boolean isBlocked(final String stageID) {
        Parent<Node> parent = getSceneWrapAsParent(stageID);
        return parent.lookup(ProgressIndicator.class, new ByID<ProgressIndicator>(ModalityWindow.WAIT_PROGRESS_ID)).wrap().getControl().isIndeterminate();
    }
}
