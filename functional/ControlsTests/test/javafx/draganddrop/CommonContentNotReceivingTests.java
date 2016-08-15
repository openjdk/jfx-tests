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

import java.util.ArrayList;
import static javafx.draganddrop.DragDropWithControls.*;
import javafx.scene.input.DataFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public abstract class CommonContentNotReceivingTests extends DragDropWithControlsBase {

    protected abstract void transfer() throws InterruptedException;
    protected DataFormat[] allFormat = new ArrayList<DataFormat>(dataFormatToCheckBoxID.keySet()).toArray(new DataFormat[]{});

//    @Test
//    public void generatorNotATest() {
//        StringBuilder test = new StringBuilder();
//        for (DataFormat o1 : allFormat) {
//            for (DataFormat o2 : allFormat) {
//                String src = getNameSimple(o1);
//                String trg = getNameSimple(o2);
//
//                if (!o1.equals(o2)) {
//                    test.append("/**\n");
//
//                    test.append("* test drag and drop or clipboard with source format " + src + " and target format " + trg + "\n");
//                    test.append("*/\n");
//                    test.append("@Test(timeout = 300000)\n");
//                    test.append("public void send" + getSimplestName(o1) + "Receive" + getSimplestName(o2) + "Test() throws InterruptedException {\n");
//
//                    test.append("setSourceContent(" + src + ");\n");
//                    test.append("setTargetContent(" + trg + ");");
//
//                    test.append("transfer();\n");
//                    test.append("verifyContentNotComing();\n");
//
//                    test.append("}\n\n");
//                }
//            }
//        }
//
//        System.err.println(test.toString());
//    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_CLASSand
    * target format DF_CUSTOM_BYTES
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_CLASSReceiveDF_CUSTOM_BYTESTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_CLASS);
        setTargetContent(DF_CUSTOM_BYTES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_CLASSand
    * target format DataFormat.FILES
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_CLASSReceiveFILESTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_CLASS);
        setTargetContent(DataFormat.FILES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_CLASSand
    * target format DataFormat.URL
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_CLASSReceiveURLTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_CLASS);
        setTargetContent(DataFormat.URL);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_CLASSand
    * target format DataFormat.RTF
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_CLASSReceiveRTFTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_CLASS);
        setTargetContent(DataFormat.RTF);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_CLASSand
    * target format DF_CUSTOM_STRING
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_CLASSReceiveDF_CUSTOM_STRINGTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_CLASS);
        setTargetContent(DF_CUSTOM_STRING);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_CLASSand
    * target format DataFormat.HTML
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_CLASSReceiveHTMLTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_CLASS);
        setTargetContent(DataFormat.HTML);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_CLASSand
    * target format DataFormat.PLAIN_TEXT
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_CLASSReceivePLAIN_TEXTTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_CLASS);
        setTargetContent(DataFormat.PLAIN_TEXT);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_CLASSand
    * target format DataFormat.IMAGE
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_CLASSReceiveIMAGETest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_CLASS);
        setTargetContent(DataFormat.IMAGE);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_BYTESand
    * target format DF_CUSTOM_CLASS
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_BYTESReceiveDF_CUSTOM_CLASSTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_BYTES);
        setTargetContent(DF_CUSTOM_CLASS);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_BYTESand
    * target format DataFormat.FILES
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_BYTESReceiveFILESTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_BYTES);
        setTargetContent(DataFormat.FILES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_BYTESand
    * target format DataFormat.URL
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_BYTESReceiveURLTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_BYTES);
        setTargetContent(DataFormat.URL);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_BYTESand
    * target format DataFormat.RTF
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_BYTESReceiveRTFTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_BYTES);
        setTargetContent(DataFormat.RTF);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_BYTESand
    * target format DF_CUSTOM_STRING
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_BYTESReceiveDF_CUSTOM_STRINGTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_BYTES);
        setTargetContent(DF_CUSTOM_STRING);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_BYTESand
    * target format DataFormat.HTML
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_BYTESReceiveHTMLTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_BYTES);
        setTargetContent(DataFormat.HTML);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_BYTESand
    * target format DataFormat.PLAIN_TEXT
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_BYTESReceivePLAIN_TEXTTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_BYTES);
        setTargetContent(DataFormat.PLAIN_TEXT);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_BYTESand
    * target format DataFormat.IMAGE
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_BYTESReceiveIMAGETest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_BYTES);
        setTargetContent(DataFormat.IMAGE);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.FILESand
    * target format DF_CUSTOM_CLASS
    */
    @Test(timeout = 30000)
    public void sendFILESReceiveDF_CUSTOM_CLASSTest() throws InterruptedException {
        setSourceContent(DataFormat.FILES);
        setTargetContent(DF_CUSTOM_CLASS);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.FILESand
    * target format DF_CUSTOM_BYTES
    */
    @Test(timeout = 30000)
    public void sendFILESReceiveDF_CUSTOM_BYTESTest() throws InterruptedException {
        setSourceContent(DataFormat.FILES);
        setTargetContent(DF_CUSTOM_BYTES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.FILESand
    * target format DataFormat.URL
    */
    @Test(timeout = 30000)
    public void sendFILESReceiveURLTest() throws InterruptedException {
        setSourceContent(DataFormat.FILES);
        setTargetContent(DataFormat.URL);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.FILESand
    * target format DataFormat.RTF
    */
    @Test(timeout = 30000)
    public void sendFILESReceiveRTFTest() throws InterruptedException {
        setSourceContent(DataFormat.FILES);
        setTargetContent(DataFormat.RTF);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.FILESand
    * target format DF_CUSTOM_STRING
    */
    @Test(timeout = 30000)
    public void sendFILESReceiveDF_CUSTOM_STRINGTest() throws InterruptedException {
        setSourceContent(DataFormat.FILES);
        setTargetContent(DF_CUSTOM_STRING);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.FILESand
    * target format DataFormat.HTML
    */
    @Test(timeout = 30000)
    public void sendFILESReceiveHTMLTest() throws InterruptedException {
        setSourceContent(DataFormat.FILES);
        setTargetContent(DataFormat.HTML);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.FILESand
    * target format DataFormat.PLAIN_TEXT
    */
    @Test(timeout = 30000)
    public void sendFILESReceivePLAIN_TEXTTest() throws InterruptedException {
        setSourceContent(DataFormat.FILES);
        setTargetContent(DataFormat.PLAIN_TEXT);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.FILESand
    * target format DataFormat.IMAGE
    */
    @Test(timeout = 30000)
    public void sendFILESReceiveIMAGETest() throws InterruptedException {
        setSourceContent(DataFormat.FILES);
        setTargetContent(DataFormat.IMAGE);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.URLand
    * target format DF_CUSTOM_CLASS
    */
    @Test(timeout = 30000)
    public void sendURLReceiveDF_CUSTOM_CLASSTest() throws InterruptedException {
        setSourceContent(DataFormat.URL);
        setTargetContent(DF_CUSTOM_CLASS);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.URLand
    * target format DF_CUSTOM_BYTES
    */
    @Test(timeout = 30000)
    public void sendURLReceiveDF_CUSTOM_BYTESTest() throws InterruptedException {
        setSourceContent(DataFormat.URL);
        setTargetContent(DF_CUSTOM_BYTES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.URLand
    * target format DataFormat.FILES
    */
    @Test(timeout = 30000)
    public void sendURLReceiveFILESTest() throws InterruptedException {
        setSourceContent(DataFormat.URL);
        setTargetContent(DataFormat.FILES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.URLand
    * target format DataFormat.RTF
    */
    @Test(timeout = 30000)
    public void sendURLReceiveRTFTest() throws InterruptedException {
        setSourceContent(DataFormat.URL);
        setTargetContent(DataFormat.RTF);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.URLand
    * target format DF_CUSTOM_STRING
    */
    @Test(timeout = 30000)
    public void sendURLReceiveDF_CUSTOM_STRINGTest() throws InterruptedException {
        setSourceContent(DataFormat.URL);
        setTargetContent(DF_CUSTOM_STRING);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.URLand
    * target format DataFormat.HTML
    */
    @Test(timeout = 30000)
    public void sendURLReceiveHTMLTest() throws InterruptedException {
        setSourceContent(DataFormat.URL);
        setTargetContent(DataFormat.HTML);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.URLand
    * target format DataFormat.PLAIN_TEXT
    */
    @Test(timeout = 30000)
    public void sendURLReceivePLAIN_TEXTTest() throws InterruptedException {
        setSourceContent(DataFormat.URL);
        setTargetContent(DataFormat.PLAIN_TEXT);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.URLand
    * target format DataFormat.IMAGE
    */
    @Test(timeout = 30000)
    public void sendURLReceiveIMAGETest() throws InterruptedException {
        setSourceContent(DataFormat.URL);
        setTargetContent(DataFormat.IMAGE);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.RTFand
    * target format DF_CUSTOM_CLASS
    */
    @Test(timeout = 30000)
    public void sendRTFReceiveDF_CUSTOM_CLASSTest() throws InterruptedException {
        setSourceContent(DataFormat.RTF);
        setTargetContent(DF_CUSTOM_CLASS);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.RTFand
    * target format DF_CUSTOM_BYTES
    */
    @Test(timeout = 30000)
    public void sendRTFReceiveDF_CUSTOM_BYTESTest() throws InterruptedException {
        setSourceContent(DataFormat.RTF);
        setTargetContent(DF_CUSTOM_BYTES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.RTFand
    * target format DataFormat.FILES
    */
    @Test(timeout = 30000)
    public void sendRTFReceiveFILESTest() throws InterruptedException {
        setSourceContent(DataFormat.RTF);
        setTargetContent(DataFormat.FILES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.RTFand
    * target format DataFormat.URL
    */
    @Test(timeout = 30000)
    public void sendRTFReceiveURLTest() throws InterruptedException {
        setSourceContent(DataFormat.RTF);
        setTargetContent(DataFormat.URL);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.RTFand
    * target format DF_CUSTOM_STRING
    */
    @Test(timeout = 30000)
    public void sendRTFReceiveDF_CUSTOM_STRINGTest() throws InterruptedException {
        setSourceContent(DataFormat.RTF);
        setTargetContent(DF_CUSTOM_STRING);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.RTFand
    * target format DataFormat.HTML
    */
    @Test(timeout = 30000)
    public void sendRTFReceiveHTMLTest() throws InterruptedException {
        setSourceContent(DataFormat.RTF);
        setTargetContent(DataFormat.HTML);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.RTFand
    * target format DataFormat.PLAIN_TEXT
    */
    @Test(timeout = 30000)
    public void sendRTFReceivePLAIN_TEXTTest() throws InterruptedException {
    //See https://javafx-jira.kenai.com/browse/RT-18004
    if (Utils.isMacOS()) { return; }
        setSourceContent(DataFormat.RTF);
        setTargetContent(DataFormat.PLAIN_TEXT);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.RTFand
    * target format DataFormat.IMAGE
    */
    @Test(timeout = 30000)
    public void sendRTFReceiveIMAGETest() throws InterruptedException {
        setSourceContent(DataFormat.RTF);
        setTargetContent(DataFormat.IMAGE);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_STRINGand
    * target format DF_CUSTOM_CLASS
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_STRINGReceiveDF_CUSTOM_CLASSTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_STRING);
        setTargetContent(DF_CUSTOM_CLASS);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_STRINGand
    * target format DF_CUSTOM_BYTES
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_STRINGReceiveDF_CUSTOM_BYTESTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_STRING);
        setTargetContent(DF_CUSTOM_BYTES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_STRINGand
    * target format DataFormat.FILES
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_STRINGReceiveFILESTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_STRING);
        setTargetContent(DataFormat.FILES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_STRINGand
    * target format DataFormat.URL
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_STRINGReceiveURLTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_STRING);
        setTargetContent(DataFormat.URL);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_STRINGand
    * target format DataFormat.RTF
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_STRINGReceiveRTFTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_STRING);
        setTargetContent(DataFormat.RTF);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_STRINGand
    * target format DataFormat.HTML
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_STRINGReceiveHTMLTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_STRING);
        setTargetContent(DataFormat.HTML);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_STRINGand
    * target format DataFormat.PLAIN_TEXT
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_STRINGReceivePLAIN_TEXTTest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_STRING);
        setTargetContent(DataFormat.PLAIN_TEXT);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DF_CUSTOM_STRINGand
    * target format DataFormat.IMAGE
    */
    @Test(timeout = 30000)
    public void sendDF_CUSTOM_STRINGReceiveIMAGETest() throws InterruptedException {
        setSourceContent(DF_CUSTOM_STRING);
        setTargetContent(DataFormat.IMAGE);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.HTMLand
    * target format DF_CUSTOM_CLASS
    */
    @Test(timeout = 30000)
    public void sendHTMLReceiveDF_CUSTOM_CLASSTest() throws InterruptedException {
        setSourceContent(DataFormat.HTML);
        setTargetContent(DF_CUSTOM_CLASS);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.HTMLand
    * target format DF_CUSTOM_BYTES
    */
    @Test(timeout = 30000)
    public void sendHTMLReceiveDF_CUSTOM_BYTESTest() throws InterruptedException {
        setSourceContent(DataFormat.HTML);
        setTargetContent(DF_CUSTOM_BYTES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.HTMLand
    * target format DataFormat.FILES
    */
    @Test(timeout = 30000)
    public void sendHTMLReceiveFILESTest() throws InterruptedException {
        setSourceContent(DataFormat.HTML);
        setTargetContent(DataFormat.FILES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.HTMLand
    * target format DataFormat.URL
    */
    @Test(timeout = 30000)
    public void sendHTMLReceiveURLTest() throws InterruptedException {
        setSourceContent(DataFormat.HTML);
        setTargetContent(DataFormat.URL);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.HTMLand
    * target format DataFormat.RTF
    */
    @Test(timeout = 30000)
    public void sendHTMLReceiveRTFTest() throws InterruptedException {
        setSourceContent(DataFormat.HTML);
        setTargetContent(DataFormat.RTF);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.HTMLand
    * target format DF_CUSTOM_STRING
    */
    @Test(timeout = 30000)
    public void sendHTMLReceiveDF_CUSTOM_STRINGTest() throws InterruptedException {
        setSourceContent(DataFormat.HTML);
        setTargetContent(DF_CUSTOM_STRING);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.HTMLand
    * target format DataFormat.PLAIN_TEXT
    */
    @Test(timeout = 30000)
    public void sendHTMLReceivePLAIN_TEXTTest() throws InterruptedException {
        setSourceContent(DataFormat.HTML);
        setTargetContent(DataFormat.PLAIN_TEXT);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.HTMLand
    * target format DataFormat.IMAGE
    */
    @Test(timeout = 30000)
    public void sendHTMLReceiveIMAGETest() throws InterruptedException {
        setSourceContent(DataFormat.HTML);
        setTargetContent(DataFormat.IMAGE);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.PLAIN_TEXTand
    * target format DF_CUSTOM_CLASS
    */
    @Test(timeout = 30000)
    public void sendPLAIN_TEXTReceiveDF_CUSTOM_CLASSTest() throws InterruptedException {
        setSourceContent(DataFormat.PLAIN_TEXT);
        setTargetContent(DF_CUSTOM_CLASS);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.PLAIN_TEXTand
    * target format DF_CUSTOM_BYTES
    */
    @Test(timeout = 30000)
    public void sendPLAIN_TEXTReceiveDF_CUSTOM_BYTESTest() throws InterruptedException {
        setSourceContent(DataFormat.PLAIN_TEXT);
        setTargetContent(DF_CUSTOM_BYTES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.PLAIN_TEXTand
    * target format DataFormat.FILES
    */
    @Test(timeout = 30000)
    public void sendPLAIN_TEXTReceiveFILESTest() throws InterruptedException {
        setSourceContent(DataFormat.PLAIN_TEXT);
        setTargetContent(DataFormat.FILES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.PLAIN_TEXTand
    * target format DataFormat.URL
    */
    @Test(timeout = 30000)
    public void sendPLAIN_TEXTReceiveURLTest() throws InterruptedException {
        setSourceContent(DataFormat.PLAIN_TEXT);
        setTargetContent(DataFormat.URL);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.PLAIN_TEXTand
    * target format DataFormat.RTF
    */
    @Test(timeout = 30000)
    public void sendPLAIN_TEXTReceiveRTFTest() throws InterruptedException {
    //See https://javafx-jira.kenai.com/browse/RT-18004
    if (Utils.isMacOS()) { return; }
        setSourceContent(DataFormat.PLAIN_TEXT);
        setTargetContent(DataFormat.RTF);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.PLAIN_TEXTand
    * target format DF_CUSTOM_STRING
    */
    @Test(timeout = 30000)
    public void sendPLAIN_TEXTReceiveDF_CUSTOM_STRINGTest() throws InterruptedException {
        setSourceContent(DataFormat.PLAIN_TEXT);
        setTargetContent(DF_CUSTOM_STRING);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.PLAIN_TEXTand
    * target format DataFormat.HTML
    */
    @Test(timeout = 30000)
    public void sendPLAIN_TEXTReceiveHTMLTest() throws InterruptedException {
        setSourceContent(DataFormat.PLAIN_TEXT);
        setTargetContent(DataFormat.HTML);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.PLAIN_TEXTand
    * target format DataFormat.IMAGE
    */
    @Test(timeout = 30000)
    public void sendPLAIN_TEXTReceiveIMAGETest() throws InterruptedException {
        setSourceContent(DataFormat.PLAIN_TEXT);
        setTargetContent(DataFormat.IMAGE);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.IMAGEand
    * target format DF_CUSTOM_CLASS
    */
    @Test(timeout = 30000)
    public void sendIMAGEReceiveDF_CUSTOM_CLASSTest() throws InterruptedException {
        setSourceContent(DataFormat.IMAGE);
        setTargetContent(DF_CUSTOM_CLASS);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.IMAGEand
    * target format DF_CUSTOM_BYTES
    */
    @Test(timeout = 30000)
    public void sendIMAGEReceiveDF_CUSTOM_BYTESTest() throws InterruptedException {
        setSourceContent(DataFormat.IMAGE);
        setTargetContent(DF_CUSTOM_BYTES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.IMAGEand
    * target format DataFormat.FILES
    */
    @Test(timeout = 30000)
    public void sendIMAGEReceiveFILESTest() throws InterruptedException {
        setSourceContent(DataFormat.IMAGE);
        setTargetContent(DataFormat.FILES);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.IMAGEand
    * target format DataFormat.URL
    */
    @Test(timeout = 30000)
    public void sendIMAGEReceiveURLTest() throws InterruptedException {
        setSourceContent(DataFormat.IMAGE);
        setTargetContent(DataFormat.URL);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.IMAGEand
    * target format DataFormat.RTF
    */
    @Test(timeout = 30000)
    public void sendIMAGEReceiveRTFTest() throws InterruptedException {
        setSourceContent(DataFormat.IMAGE);
        setTargetContent(DataFormat.RTF);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.IMAGEand
    * target format DF_CUSTOM_STRING
    */
    @Test(timeout = 30000)
    public void sendIMAGEReceiveDF_CUSTOM_STRINGTest() throws InterruptedException {
        setSourceContent(DataFormat.IMAGE);
        setTargetContent(DF_CUSTOM_STRING);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.IMAGEand
    * target format DataFormat.HTML
    */
    @Test(timeout = 30000)
    public void sendIMAGEReceiveHTMLTest() throws InterruptedException {
        setSourceContent(DataFormat.IMAGE);
        setTargetContent(DataFormat.HTML);
        transfer();
        verifyContentNotComing();
    }

    /**
    * test drag and drop or clipboard with source format DataFormat.IMAGEand
    * target format DataFormat.PLAIN_TEXT
    */
    @Test(timeout = 30000)
    public void sendIMAGEReceivePLAIN_TEXTTest() throws InterruptedException {
        setSourceContent(DataFormat.IMAGE);
        setTargetContent(DataFormat.PLAIN_TEXT);
        transfer();
        verifyContentNotComing();
    }

}