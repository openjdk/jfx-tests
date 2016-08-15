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

import org.junit.Before;
import org.junit.Test;
import test.scenegraph.modality.ModalityWindow.WindowsRenderType;

/**
 * @author Alexander Kirov
 */
public class ModalityWithChoiceBoxTest extends ModalityBase{
    @Before
    public void setUp() {
        setUpTyped(WindowsRenderType.LIST);
    }

    @Test
    public void case1Test() throws InterruptedException {
        createSubstageByChoiceBox("stage0", ModalityWindow.TestCase.APPAPP, true);
    }

    @Test
    public void case2Test() throws InterruptedException {
        createSubstageByChoiceBox("stage0", ModalityWindow.TestCase.WINWIN, true);
    }

    @Test
    public void case3Test() throws InterruptedException {
        createSubstageByChoiceBox("stage0", ModalityWindow.TestCase.WINAPP, true);
    }

    @Test
    public void case4Test() throws InterruptedException {
        createSubstageByChoiceBox("stage0", ModalityWindow.TestCase.APPWIN, true);
    }

    @Test
    public void case5Test() throws InterruptedException {
        createSubstageByChoiceBox("stage0", ModalityWindow.TestCase.NONE, true);
    }

    @Test
    public void case6Test() throws InterruptedException {
        createSubstageByChoiceBox("stage0", ModalityWindow.TestCase.APP, true);
    }

    @Test
    public void case7Test() throws InterruptedException {
        createSubstageByChoiceBox("stage0", ModalityWindow.TestCase.WIN, true);
    }

    @Test
    public void case8Test() throws InterruptedException {
        createSubstageByChoiceBox("stage0", ModalityWindow.TestCase.NONE, true);
        createSubstageByChoiceBox("stage1", ModalityWindow.TestCase.WIN, true);
        createSubstageByChoiceBox("stage2", ModalityWindow.TestCase.WINWIN, true);
    }

    @Test
    public void case9Test() throws InterruptedException {
        createSubstageByChoiceBox("stage0", ModalityWindow.TestCase.NONE, true);
        createSubstageByChoiceBox("stage1", ModalityWindow.TestCase.WIN, true);
        createSubstageByChoiceBox("stage2", ModalityWindow.TestCase.APP, true);
        createSubstageByChoiceBox("stage3", ModalityWindow.TestCase.APP, true);
    }

    @Test
    public void case10Test() throws InterruptedException {
        createSubstageByChoiceBox("stage0", ModalityWindow.TestCase.WINWIN, true);
        createSubstageByChoiceBox("stage2", ModalityWindow.TestCase.WINWIN, true);
    }

    @Test
    public void case11Test() throws InterruptedException {
        createSubstageByChoiceBox("stage0", ModalityWindow.TestCase.APP, true);
        createSubstageByChoiceBox("stage1", ModalityWindow.TestCase.WINWIN, true);
        createSubstageByChoiceBox("stage3", ModalityWindow.TestCase.APP, true);
    }

    @Test
    public void case12Test() throws InterruptedException {
        createSubstageByChoiceBox("stage0", ModalityWindow.TestCase.APP, true);
        createSubstageByChoiceBox("stage1", ModalityWindow.TestCase.NONE, false);
        createSubstageByChoiceBox("stage2", ModalityWindow.TestCase.APP, true);
    }

    @Test
    public void case13Test() throws InterruptedException {
        createSubstageByChoiceBox("stage0", ModalityWindow.TestCase.APP, true);
        createSubstageByChoiceBox("stage1", ModalityWindow.TestCase.NONE, false);
        createSubstageByChoiceBox("stage2", ModalityWindow.TestCase.WIN, true);
    }
}
