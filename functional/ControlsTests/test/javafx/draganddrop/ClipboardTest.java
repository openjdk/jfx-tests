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
package javafx.draganddrop;

import static javafx.draganddrop.DragDropWithControls.*;
import javafx.scene.input.DataFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Andrey Nazarov
 */
@RunWith(FilteredTestRunner.class)
public class ClipboardTest extends CommonContentNotReceivingTests {

    protected void transfer() throws InterruptedException {
        clipboardCopyPaste();
    }

    /**
     * test passing plain text through clipboard
     */
    //@Smoke
    @Test(timeout = 300000)
    public void plainTextTest() throws Exception {
        setTransferData(DataFormat.PLAIN_TEXT);
        transfer();
        verifyContent(DataFormat.PLAIN_TEXT);
    }

    /**
     * test passing url through clipboard
     */
    //@Smoke
    @Test(timeout = 300000)
    public void urlTest() throws Exception {
        setTransferData(DataFormat.URL);
        transfer();
        verifyContent(DataFormat.URL);
    }

    /**
     * test passing html through clipboard
     */
    //@Smoke
    @Test(timeout = 300000)
    public void htmlTest() throws Exception {
        setTransferData(DataFormat.HTML);
        transfer();
        verifyContent(DataFormat.HTML);
    }

    /**
     * test passing image through clipboard
     */
    @Test(timeout = 300000)
    public void imageTest() throws Exception {
        setTransferData(DataFormat.IMAGE);
        transfer();
        verifyContent(DataFormat.IMAGE);
    }

    /**
     * test passing rtf through clipboard
     */
    //@Smoke
    @Test(timeout = 300000)
    public void rtfTest() throws Exception {
        setTransferData(DataFormat.RTF);
        transfer();
        verifyContent(DataFormat.RTF);
    }

    /**
     * test passing files through clipboard
     */
    //@Smoke
    @Test(timeout = 300000)
    public void filesTest() throws Exception {
        setTransferData(DataFormat.FILES);
        transfer();
        verifyContent(DataFormat.FILES);
    }

    /**
     * test passing custom bytes through clipboard
     */
    //@Smoke
    @Test(timeout = 300000)
    public void customBytesTest() throws Exception {
        setTransferData(DF_CUSTOM_BYTES);
        transfer();
        verifyContent(DF_CUSTOM_BYTES);
    }

    /**
     * test passing custom string through clipboard
     */
    //@Smoke
    @Test(timeout = 300000)
    public void customStringTest() throws Exception {
        setTransferData(DF_CUSTOM_STRING);
        transfer();
        verifyContent(DF_CUSTOM_STRING);
    }

    /**
     * test passing custom class through clipboard
     */
    //@Smoke
    @Test(timeout = 300000)
    public void customClassTest() throws Exception {
        setTransferData(DF_CUSTOM_CLASS);
        transfer();
        verifyContent(DF_CUSTOM_CLASS);
    }

    /**
     * test passing all data formats through clipboard
     */
    //@Smoke
    @Test(timeout = 300000)
    public void allFormatsTest() throws Exception {
        setTransferData(allFormat);
        transfer();
        verifyContent(allFormat);
    }

    /**
     * test passing plain text, html, rtf through clipboard
     */
    //@Smoke
    @Test(timeout = 300000)
    public void partFormatsTest() throws Exception {
        DataFormat[] dfs = new DataFormat[]{DataFormat.PLAIN_TEXT, DataFormat.HTML, DataFormat.RTF};
        setTransferData(dfs);
        transfer();
        verifyContent(dfs);
    }

    /**
     * test passing url, html, rtf through clipboard
     */
    //@Smoke
    @Test(timeout = 300000)
    public void partFormats2Test() throws Exception {
        DataFormat[] dfs = new DataFormat[]{DataFormat.URL, DataFormat.HTML, DataFormat.RTF};
        setSourceContent(dfs);
        setTargetContent(allFormat);
        transfer();
        verifyContent(dfs);
    }
}
