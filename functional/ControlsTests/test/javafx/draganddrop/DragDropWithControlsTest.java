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
package javafx.draganddrop;


import java.util.EnumSet;
import static javafx.draganddrop.DragDropWithControls.*;
import javafx.factory.ControlsFactory;
import javafx.factory.Panes;
import javafx.factory.Shapes;
import javafx.scene.input.DataFormat;
import javafx.scene.input.TransferMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Andrey Nazarov, Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class DragDropWithControlsTest extends DragDropWithControlsBase {

    private boolean useOnlyPlainText = false;

//    @Test(timeout = 300000)
//    public void testGenerator() {
//        StringBuilder sb = new StringBuilder();
//        int counter = 0;
//        int groupSize = srcChooser.getStates().size();
//        for (Object o1 : srcChooser.getStates()) {
//            for (Object o2 : trgChooser.getStates()) {
//                String src = normalize(o1.toString());
//                String trg = normalize(o2.toString());
//
//                if (counter % groupSize == 0) {
//                    sb.append("/**\n");
//                    sb.append("* test drag and drop with source " + src + "\n");
//                    sb.append("*/\n");
//                    sb.append("////TODO@Smoke\n");
//                    sb.append("@Test(timeout = 300000)\n");
//                    sb.append("public void from" + src + "() throws InterruptedException {\n");
//                }
//
//                src = fullName(o1);
//                trg = fullName(o2);
//
//                DataFormat[] sourceContent = new DataFormat[1];
//                DataFormat[] targetContent = new DataFormat[1];
//                if (counter % 17 == 0) {
//                    targetContent = dataFormatToCheckBoxID.keySet().toArray(targetContent);
//                } else {
//                    targetContent[0] = (DataFormat) dataFormatToCheckBoxID.keySet().toArray()[counter % dataFormatToCheckBoxID.keySet().toArray().length];
//                }
//                sourceContent[0] = (DataFormat) dataFormatToCheckBoxID.keySet().toArray()[counter % dataFormatToCheckBoxID.keySet().toArray().length];
//                TransferMode mode = (TransferMode) EnumSet.of(TransferMode.COPY, TransferMode.LINK, TransferMode.MOVE).toArray()[counter % EnumSet.of(TransferMode.COPY, TransferMode.LINK, TransferMode.MOVE).toArray().length];
//
//                sb.append("setTransferMode(TransferMode." + mode + ");\n");
//                sb.append("selectNodeToTransfer(srcChoice, " + src + ");\n");
//                sb.append("selectNodeToTransfer(trgChoice, " + trg + ");\n");
//                sb.append(""
//                        + "setSourceContent( useOnlyPlainText ? DataFormat.PLAIN_TEXT :" + getName(sourceContent) + ");\n"
//                        + "if (useOnlyPlainText) {\n"
//                        + "setTargetContent(DataFormat.PLAIN_TEXT);\n"
//                        + "} else {"
//                        + "setTargetContent(" + getName(targetContent) + ");\n"
//                        + "}\n"
//                        + "dnd();\n"
//                        + "verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT :" + getName(sourceContent) + ");");
//
//                if (counter % groupSize == groupSize - 1) {
//                    sb.append("}\n\n");
//                }
//
//                counter++;
//            }
//        }
//        System.err.println(sb.toString());
//    }

    /**
     * test drag and drop with source Button
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromButton() throws InterruptedException {
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Buttons.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
    }

    /**
     * test drag and drop with source ChoiceBoxe
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromChoiceBoxe() throws InterruptedException {
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ChoiceBoxes.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
    }

    /**
     * test drag and drop with source ComboBoxe
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromComboBoxe() throws InterruptedException {
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ComboBoxes.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
    }

    /**
     * test drag and drop with source EditableComboBoxe
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromEditableComboBoxe() throws InterruptedException {
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.EditableComboBoxes.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
    }

    /**
     * test drag and drop with source Pagination
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromPagination() throws InterruptedException {
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Paginations.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
    }

    /**
     * test drag and drop with source ColorPicker
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromColorPicker() throws InterruptedException {
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ColorPickers.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
    }

    /**
     * test drag and drop with source CheckBoxe
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromCheckBoxe() throws InterruptedException {
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.CheckBoxes.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
    }

    /**
     * test drag and drop with source RadioButton
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromRadioButton() throws InterruptedException {
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.RadioButtons.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
    }

    /**
     * test drag and drop with source TextField
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromTextField() throws InterruptedException {
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextFields.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
    }

    /**
     * test drag and drop with source TextArea
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromTextArea() throws InterruptedException {
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TextAreas.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
    }

    /**
     * test drag and drop with source PasswordField
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromPasswordField() throws InterruptedException {
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PasswordFields.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
    }

    /**
     * test drag and drop with source Slider
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromSlider() throws InterruptedException {
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Sliders.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
    }

    /**
     * test drag and drop with source Label
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromLabel() throws InterruptedException {
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Labels.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
    }

    /**
     * test drag and drop with source Hyperlink
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromHyperlink() throws InterruptedException {
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Hyperlinks.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
    }

    /**
     * test drag and drop with source ImageView
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromImageView() throws InterruptedException {
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ImageView.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
    }

    /**
     * test drag and drop with source MediaView
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromMediaView() throws InterruptedException {
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.MediaView.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
    }

    /**
     * test drag and drop with source Separator
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromSeparator() throws InterruptedException {
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Separators.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
    }

    /**
     * test drag and drop with source ScrollBar
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromScrollBar() throws InterruptedException {
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollBars.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
    }

    /**
     * test drag and drop with source ScrollPane
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromScrollPane() throws InterruptedException {
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ScrollPanes.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
    }

    /**
     * test drag and drop with source ProgressIndicator
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromProgressIndicator() throws InterruptedException {
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressIndicators.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
    }

    /**
     * test drag and drop with source ProgressBar
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromProgressBar() throws InterruptedException {
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ProgressBars.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
    }

    /**
     * test drag and drop with source ListView
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromListView() throws InterruptedException {
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.ListViews.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
    }

    /**
     * test drag and drop with source PressedToggleButton
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromPressedToggleButton() throws InterruptedException {
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.PressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
    }

    /**
     * test drag and drop with source UnPressedToggleButton
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromUnPressedToggleButton() throws InterruptedException {
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.UnPressedToggleButtons.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
    }

    /**
     * test drag and drop with source Toolbar
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromToolbar() throws InterruptedException {
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Toolbars.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
    }

    /**
     * test drag and drop with source Menubar
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromMenubar() throws InterruptedException {
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Menubars.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
    }

    /**
     * test drag and drop with source SplitMenuButton
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromSplitMenuButton() throws InterruptedException {
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitMenuButtons.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
    }

    /**
     * test drag and drop with source TabPane
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromTabPane() throws InterruptedException {
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TabPanes.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
    }

    /**
     * test drag and drop with source TitledPane
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromTitledPane() throws InterruptedException {
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TitledPanes.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
    }

    /**
     * test drag and drop with source TableView
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromTableView() throws InterruptedException {
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TableViews.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
    }

    /**
     * test drag and drop with source TreeTableView
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromTreeTableView() throws InterruptedException {
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeTableViews.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
    }

    /**
     * test drag and drop with source TreeView
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromTreeView() throws InterruptedException {
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.TreeViews.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
    }

    /**
     * test drag and drop with source Accordion
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromAccordion() throws InterruptedException {
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.Accordions.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
    }

    /**
     * test drag and drop with source SplitPane
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromSplitPane() throws InterruptedException {
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.SplitPanes.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
    }

    /**
     * test drag and drop with source DatePicker
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromDatePicker() throws InterruptedException {
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, ControlsFactory.DatePickers.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
    }

    /**
     * test drag and drop with source Line
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromLine() throws InterruptedException {
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Line.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
    }

    /**
     * test drag and drop with source Cirle
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromCirle() throws InterruptedException {
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Shapes.Cirle.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
    }

    /**
     * test drag and drop with source VBox
     */
////TODO@Smoke
    @Test(timeout = 300000)
    public void fromVBox() throws InterruptedException {
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Buttons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ChoiceBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.EditableComboBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Paginations.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ColorPickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.CheckBoxes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.RadioButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TextAreas.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PasswordFields.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Sliders.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Labels.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Hyperlinks.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ImageView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.MediaView.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Separators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ScrollPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressIndicators.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ProgressBars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.ListViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.PressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL, DataFormat.IMAGE, DF_CUSTOM_STRING, DataFormat.FILES, DataFormat.PLAIN_TEXT, DF_CUSTOM_BYTES, DataFormat.RTF, DataFormat.HTML, DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.UnPressedToggleButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Toolbars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Menubars.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitMenuButtons.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TabPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TitledPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.PLAIN_TEXT);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.PLAIN_TEXT);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeTableViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_BYTES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_BYTES);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.TreeViews.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.RTF);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.RTF);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.Accordions.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.HTML);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.HTML);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.SplitPanes.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_CLASS);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_CLASS);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, ControlsFactory.DatePickers.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.URL);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.URL);
        setTransferMode(TransferMode.MOVE);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, Shapes.Line.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.IMAGE);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.IMAGE);
        setTransferMode(TransferMode.LINK);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, Shapes.Cirle.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DF_CUSTOM_STRING);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DF_CUSTOM_STRING);
        setTransferMode(TransferMode.COPY);
        selectNodeToTransfer(srcChoice, Panes.VBox.name());
        selectNodeToTransfer(trgChoice, Panes.VBox.name());
        setSourceContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
        if (useOnlyPlainText) {
            setTargetContent(DataFormat.PLAIN_TEXT);
        } else {
            setTargetContent(DataFormat.FILES);
        }
        dnd();
        verifyContent(useOnlyPlainText ? DataFormat.PLAIN_TEXT : DataFormat.FILES);
    }
}
