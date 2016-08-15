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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.draganddrop.DragDropWithControls.dataFormatToCheckBoxID;
import javafx.scene.input.DataFormat;

/**
 *
 * @author Dmitry Zinkevich <dmitry.zinkevich@oracle.com>
 */
public class CommonContentNotReceivingTestsGenerator {
    static String fileName = "test/javafx/draganddrop/CommonContentNotReceivingTests.java";
    static DataFormat[] allFormats;

    static {
        final boolean imageTypeFound = dataFormatToCheckBoxID.containsKey(DataFormat.IMAGE);
        int size = dataFormatToCheckBoxID.size();
        if (! imageTypeFound) {
            size++;
        }

        allFormats = new DataFormat[size];

        int i = 0;

        for (DataFormat dataFormat : dataFormatToCheckBoxID.keySet()) {
            allFormats[i++] = dataFormat;
        }

        if (! imageTypeFound) {
            allFormats[i] = DataFormat.IMAGE;
        }
    }

    static class Helper extends DragDropWithControlsBase {
        @Override
        public String getName(DataFormat[] dfArr) {
            return super.getName(dfArr);
        }

        @Override
        public String getNameSimple(DataFormat df) {
            return super.getNameSimple(df);
        }
    }

    /*
     * Generates tests that check that unexpected content won't be received during dnd
     */
    public static void main(String[] args) {

        if (allFormats.length != 9) {
            throw new IllegalStateException("[Expected 9 types of content]");
        }

        Helper helper = new Helper();

        BufferedWriter writer = null;
        try {
            File f = new File(fileName);
            writer = new BufferedWriter(new FileWriter(f));
            writer.append("/*\n" +
            " * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.\n" +
            " * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.\n" +
            " */\n" +
            "package javafx.draganddrop;\n" +
            "\n" +
            "import client.test.Smoke;\n" +
            "import java.rmi.RemoteException;\n" +
            "import java.util.ArrayList;\n" +
            "import static javafx.draganddrop.DragDropWithControls.*;\n" +
            "import javafx.scene.input.DataFormat;\n" +
            "import org.junit.Test;\n" +
            "import org.junit.runner.RunWith;\n" +
            "import test.javaclient.shared.FilteredTestRunner;\n" +
            "import test.javaclient.shared.Utils;\n\n" +
            "/**\n" +
            " * @author Alexander Kirov\n" +
            " */\n" +
            "@RunWith(FilteredTestRunner.class)\n" +
            "public abstract class CommonContentNotReceivingTests extends DragDropWithControlsBase {\n" +
            "\n" +
            "\tprotected abstract void transfer() throws InterruptedException, RemoteException;\n" +
            "\tprotected DataFormat[] allFormat = new ArrayList<DataFormat>(dataFormatToCheckBoxID.keySet()).toArray(new DataFormat[]{});");

            //Need to keep this
            writer.append("\n\n//    @Test" +
            "\n//    public void generatorNotATest() {" +
            "\n//        StringBuilder test = new StringBuilder();" +
            "\n//        for (DataFormat o1 : allFormat) {" +
            "\n//            for (DataFormat o2 : allFormat) {" +
            "\n//                String src = getNameSimple(o1);" +
            "\n//                String trg = getNameSimple(o2);"+
            "\n//" +
            "\n//                if (!o1.equals(o2)) {" +
            "\n//                    test.append(\"/**\\n\");" +
            "\n//" +
            "\n//                    test.append(\"* test drag and drop or clipboard with source format \" + src + \" and target format \" + trg + \"\\n\");" +
            "\n//                    test.append(\"*/\\n\");" +
            "\n//                    test.append(\"@Test(timeout = 300000)\\n\");" +
            "\n//                    test.append(\"public void send\" + getSimplestName(o1) + \"Receive\" + getSimplestName(o2) + \"Test() throws InterruptedException, RemoteException {\\n\");" +
            "\n//" +
            "\n//                    test.append(\"setSourceContent(\" + src + \");\\n\");" +
            "\n//                    test.append(\"setTargetContent(\" + trg + \");\");" +
            "\n//" +
            "\n//                    test.append(\"transfer();\\n\");" +
            "\n//                    test.append(\"verifyContentNotComing();\\n\");" +
            "\n//" +
            "\n//                    test.append(\"}\\n\\n\");" +
            "\n//                }" +
            "\n//            }" +
            "\n//        }" +
            "\n//" +
            "\n//        System.err.println(test.toString());" +
            "\n//    }\n\n");

            for (DataFormat o1 : allFormats) {
                for (DataFormat o2 : allFormats) {
                    String src = helper.getNameSimple(o1);
                    String trg = helper.getNameSimple(o2);

                    if (!o1.equals(o2)) {
                        writer.append(
                         "\t/**\n" +
                        "\t* test drag and drop or clipboard with source format " + src + "and \n" +
                        "\t* target format " + trg + "\n" +
                        "\t*/\n" +
                        "\t@Test(timeout = 30000)\n");

                        String simplestName1 = helper.getSimplestName(o1);
                        String simplestName2 = helper.getSimplestName(o2);

                        writer.append("\tpublic void send" + simplestName1 + "Receive" + simplestName2 +
                                      "Test() throws InterruptedException, RemoteException {\n");

                        if (   ("RTF".equals(simplestName1) && "PLAIN_TEXT".equals(simplestName2))
                            || ("PLAIN_TEXT".equals(simplestName1) && "RTF".equals(simplestName2)))
                        {
                            writer.append("\t//See https://javafx-jira.kenai.com/browse/RT-18004\n" +
                                          "\tif (Utils.isMacOS()) { return; }\n");
                        }

                        writer.append(
                        "\t\tsetSourceContent(" + src + ");\n" +
                        "\t\tsetTargetContent(" + trg + ");\n" +
                        "\t\ttransfer();\n" +
                        "\t\tverifyContentNotComing();\n" +
                        "\t}\n\n");
                    }
                }
            }
            writer.append("}");

        } catch (IOException ex) {
            Logger.getLogger(CommonContentNotReceivingTestsGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(CommonContentNotReceivingTestsGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
 }
        }
    }
}
