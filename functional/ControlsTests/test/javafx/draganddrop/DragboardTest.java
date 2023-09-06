/*
 * Copyright (c) 2014, 2023, Oracle and/or its affiliates. All rights reserved.
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
package javafx.draganddrop;

import javafx.scene.input.TransferMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Andrey Nazarov
 */
@RunWith(FilteredTestRunner.class)
public class DragboardTest extends ClipboardTest {

    @Override
    protected void transfer() throws InterruptedException {
        dnd();
    }

    ////TODO@Smoke
    @Test
    public void contentNotReceivingDifferentTransferModes1Test() throws InterruptedException {
        setTransferMode(TransferMode.COPY, TransferMode.LINK);
        setTransferData(allFormat);
        transfer();
        verifyContentNotComing();
    }

    ////TODO@Smoke
    @Test
    public void contentNotReceivingDifferentTransferModes2Test() throws InterruptedException {
        setTransferMode(TransferMode.COPY, TransferMode.MOVE);
        setTransferData(allFormat);
        transfer();
        verifyContentNotComing();
    }

    ////TODO@Smoke
    @Test
    public void contentNotReceivingDifferentTransferModes3Test() throws InterruptedException {
        setTransferMode(TransferMode.MOVE, TransferMode.COPY);
        setTransferData(allFormat);
        transfer();
        verifyContentNotComing();
    }

    ////TODO@Smoke
    @Test
    public void contentNotReceivingDifferentTransferModes4Test() throws InterruptedException {
        setTransferMode(TransferMode.MOVE, TransferMode.LINK);
        setTransferData(allFormat);
        transfer();
        verifyContentNotComing();
    }

    ////TODO@Smoke
    @Test
    public void contentNotReceivingDifferentTransferModes5Test() throws InterruptedException {
        setTransferMode(TransferMode.LINK, TransferMode.MOVE);
        setTransferData(allFormat);
        transfer();
        verifyContentNotComing();
    }

    ////TODO@Smoke
    @Test
    public void contentNotReceivingDifferentTransferModes6Test() throws InterruptedException {
        setTransferMode(TransferMode.LINK, TransferMode.COPY);
        setTransferData(allFormat);
        transfer();
        verifyContentNotComing();
    }
}
