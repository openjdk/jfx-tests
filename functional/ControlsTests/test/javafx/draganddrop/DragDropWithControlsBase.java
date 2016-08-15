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

import java.io.File;
import java.util.Map.Entry;
import java.util.*;
import static javafx.draganddrop.DragDropWithControls.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.TransferMode;
import javafx.util.Pair;
import junit.framework.Assert;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.fx.control.CheckBoxWrap.State;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.lookup.ByStringLookup;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.timing.Waiter;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.JemmyUtils;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.Utils;

/**
 * @author Andrey Nazarov
 */
@RunWith(FilteredTestRunner.class)
public class DragDropWithControlsBase extends TestBase {

    @BeforeClass
    public static void setComparatorDistance() {
        if (Utils.isMacOS()) {
            JemmyUtils.comparatorDistance = 0.15f;
        }
    }

    public DragDropWithControlsBase() {
    }
    static Wrap<? extends Scene> sceneSource = null;
    static Wrap<? extends Scene> sceneTarget = null;
    static Wrap<? extends ChoiceBox> srcChoice = null;
    static Wrap<? extends ChoiceBox> trgChoice = null;
    static Selectable srcChooser = null;
    static Selectable trgChooser = null;

    static protected enum ExecutionMode {

        PROGRAM, MANUAL
    };
    protected ExecutionMode defaultExecutionMode = ExecutionMode.PROGRAM;

    @BeforeClass
    public static void runUI() throws Exception {
        DragDropWithControls.main(null);
        sceneSource = Root.ROOT.lookup(new ByRootNodeIdLookup<Scene>(DragDropWithControls.TITLE_SOURCE_STAGE)).wrap();
        sceneTarget = Root.ROOT.lookup(new ByRootNodeIdLookup<Scene>(DragDropWithControls.TITLE_TARGET_STAGE)).wrap();
        srcChoice = sceneSource.as(Parent.class, Node.class).lookup(ChoiceBox.class, new ByID<ChoiceBox>(ID_NODE_CHOOSER)).wrap();
        trgChoice = sceneTarget.as(Parent.class, Node.class).lookup(ChoiceBox.class, new ByID<ChoiceBox>(ID_NODE_CHOOSER)).wrap();
        srcChooser = srcChoice.as(Selectable.class);
        trgChooser = trgChoice.as(Selectable.class);
    }

    protected boolean checkOrder(List<? extends Comparable> list) {
        if (list.isEmpty()) {
            return false;
        }
        if (list.size() == 1) {
            return true;
        }
        boolean result;
        ListIterator<? extends Comparable> iter = list.listIterator();
        iter.next();
        do {
            result = iter.next().compareTo(iter.previous()) < 0 ? false : true;
            iter.next();
        } while (result && iter.hasNext());
        return result;
    }

    protected void dnd() throws InterruptedException {
        sceneSource.mouse().click(1, new Point(0, 0));
        Wrap from = Lookups.byID(sceneSource, ID_DRAG_SOURCE, Node.class);
        Wrap to = Lookups.byID(sceneTarget, ID_DRAG_TARGET, Node.class);
        Point fromPoint = from.getClickPoint();
        Point toPoint = to.getClickPoint();
        final Object fromControl = from.getControl();
        if (fromControl instanceof MenuBar || fromControl instanceof ToolBar
                || fromControl instanceof ScrollPane || fromControl instanceof Pagination) {
            fromPoint = new Point(2, 2);
        }
        if (fromControl instanceof TitledPane) {
            fromPoint = new Point(5, 30);
        }
        final Object toControl = to.getControl();
        if (toControl instanceof MenuBar || toControl instanceof ToolBar
                || toControl instanceof ScrollPane || toControl instanceof Pagination) {
            toPoint = new Point(2, 2);
        }
        if (toControl instanceof TitledPane) {
            toPoint = new Point(30, 30);
        }
        dnd(from, fromPoint, to, toPoint);
    }

    protected void clipboardCopyPaste() throws InterruptedException {
        Lookups.byID(sceneSource, ID_TO_CLIPBOARD_BUTTON, Node.class).mouse().click();
        Thread.sleep(1000);
        Lookups.byID(sceneTarget, ID_FROM_CLIPBOARD_BUTTON, Node.class).mouse().click();
        Thread.sleep(1000);
    }

    protected void verifyContent(final DataFormat... expectedDataFormats) {
        new Waiter(new Timeout("", 20000)).ensureState(new org.jemmy.timing.State() {
            public Object reached() {
                try {
                    doVerifyContent(expectedDataFormats);
                } catch (Throwable ex) {
                    return null;
                }
                return true;
            }
        });
    }

    private void doVerifyContent(DataFormat... expectedDataFormats) {
        if (expectedDataFormats == null) {
            Assert.fail("Null pointer.");
        }
        if (Utils.isMacOS()) {
            //On Mac OS it is possible to receive more content then it was sent.
            //See http://javafx-jira.kenai.com/browse/RT-18004
            if (1 == expectedDataFormats.length
                    && DataFormat.RTF == expectedDataFormats[0]) {
                assertTrue("When sending RTF it is expected to receive also a string",
                        receivedContent.size() >= 1 && receivedContent.size() <= 2);
            } else {
                assertTrue(expectedDataFormats.length >= receivedContent.size());
            }
        } else {
            assertEquals(expectedDataFormats.length, receivedContent.size());
        }
        for (DataFormat df : expectedDataFormats) {
            if (df.equals(DF_CUSTOM_BYTES)) {
                assertArrayEquals((byte[]) dataFormatToCheckBoxID.get(df).getValue(), (byte[]) receivedContent.get(df));
            } else if (df.equals(DataFormat.IMAGE)) {
                Wrap srcImage = Lookups.byID(sceneSource, ID_SRC_IMAGE, ImageView.class);
                Wrap receivedImage = Lookups.byID(sceneTarget, ID_RECEIVED_IMAGE, ImageView.class);
                TestUtil.compareScreenshots(getClass().getSimpleName(),
                        srcImage, receivedImage);
            } else if (df.equals(DataFormat.FILES)) {
                final File expectedFile = ((List<File>) dataFormatToCheckBoxID.get(DataFormat.FILES).getValue()).get(0);
                final File actualFile = ((List<File>) receivedContent.get(DataFormat.FILES)).get(0);
                assertEquals(expectedFile.getAbsoluteFile(), actualFile);
            } else {
                assertEquals(dataFormatToCheckBoxID.get(df).getValue(), receivedContent.get(df));
            }
        }
    }

    protected void verifyContentNotComing() {
        assertEquals(receivedContent.size(), 0, 0);
        if (eventList.contains(DragEvents.DRAG_DONE)) {
            assertTrue(eventList.contains(DragEvents.EMPTY_DRAG_DONE));
        }
    }

    protected void verifyDragAndDrop() {
        assertEquals("Expected plain text only", receivedContent.size(), 1);
        assertEquals("Expected equals plain text strings", receivedContent.get(DataFormat.PLAIN_TEXT), CONTENT_PLAIN_TEXT);
        assertEquals("Expected all events", new HashSet(eventList).size(), DragEvents.values().length);
        assertTrue("Expected events in appropriate order", checkOrder(eventList));
    }

    protected void setTransferMode(final TransferMode tm) {
        setTransferMode(tm, tm);
    }

    protected void setTransferMode(final TransferMode tmsource, final TransferMode tmtarget) {
        for (final TransferMode temp : EnumSet.of(TransferMode.COPY, TransferMode.LINK, TransferMode.MOVE)) {
            new GetAction() {
                @Override
                public void run(Object... os) throws Exception {
                    setSelected(Lookups.byText(sceneSource, temp.name(), CheckBox.class), temp.equals(tmsource));
                    setSelected(Lookups.byText(sceneTarget, temp.name(), CheckBox.class), temp.equals(tmtarget));
                }
            }.dispatch(Root.ROOT.getEnvironment());
        }
    }

    protected void setSelected(final Wrap<? extends CheckBox> checkBox, final boolean selected) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                checkBox.getControl().setSelected(selected);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void setTransferData(DataFormat... dfs) {
        setSourceContent(dfs);
        setTargetContent(dfs);
    }

    protected void setSourceContent(DataFormat... dfs) {
        uncheck(sceneSource, dfs);
        for (DataFormat df : dfs) {
            setDataContent(sceneSource, dataFormatToCheckBoxID.get(df).getKey(), State.CHECKED);
        }
    }

    protected void setTargetContent(DataFormat... dfs) {
        uncheck(sceneTarget, dfs);
        for (DataFormat df : dfs) {
            setDataContent(sceneTarget, dataFormatToCheckBoxID.get(df).getKey(), State.CHECKED);
        }
    }

    protected void selectNodeToTransfer(final Wrap<? extends ChoiceBox> wrap, final String name) {
        if (defaultExecutionMode == ExecutionMode.MANUAL) {
            wrap.as(Selectable.class).selector().select(name);
        } else {//Program execution
            Boolean res = new GetAction<Boolean>() {
                @Override
                public void run(Object... os) throws Exception {
                    try {
                        ChoiceBox cb = wrap.getControl();
                        for (Object obj : cb.getItems()) {
                            if (obj.toString().equals(name)) {
                                cb.getSelectionModel().select(obj);
                            }
                        }
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                        setResult(false);
                        return;
                    }
                    setResult(true);
                }
            }.dispatch(Root.ROOT.getEnvironment());
            if (!res) {
                throw new IllegalStateException("Exception on control instantiation.");
            }
        }
    }

    protected void uncheck(Wrap<? extends Scene> parent, DataFormat... except) {
        Set<DataFormat> excepts = new HashSet(Arrays.asList(except));
        for (Entry<DataFormat, Pair<String, Object>> df2Id : dataFormatToCheckBoxID.entrySet()) {
            if (!excepts.contains(df2Id.getKey())) {
                setDataContent(parent, df2Id.getValue().getKey(), State.UNCHECKED);
            }
        }
    }

    private void setDataContent(Wrap<? extends Scene> parent, String checkBoxId, final CheckBoxWrap.State state) {
        final Wrap<? extends CheckBox> wrap = Lookups.byText(parent, checkBoxId, CheckBox.class);
        if (defaultExecutionMode == ExecutionMode.MANUAL) {
            wrap.as(Selectable.class).selector().select(state);
        } else {
            new GetAction() {
                @Override
                public void run(Object... os) throws Exception {
                    boolean checked = state.equals(CheckBoxWrap.State.CHECKED) ? true : false;
                    ((CheckBox) wrap.getControl()).setSelected(checked);
                }
            }.dispatch(Root.ROOT.getEnvironment());
        }
    }

    protected String getName(DataFormat[] dfArr) {
        StringBuilder name = new StringBuilder();
        for (DataFormat df : dfArr) {
            String candidate = "";

            candidate = getNameSimple(df);

            name.append(", " + candidate);
        }
        name.replace(0, 1, "");
        return name.toString();
    }

    protected String getNameSimple(DataFormat df) {
        String candidate = "";

        candidate = getSimplestName(df);

        if (!(df.equals(DF_CUSTOM_BYTES) || df.equals(DF_CUSTOM_CLASS) || df.equals(DF_CUSTOM_STRING))) {
            candidate = "DataFormat." + candidate;
        }

        return candidate;
    }

    protected String getSimplestName(DataFormat df) {
        if (df.equals(DataFormat.FILES)) {
            return "FILES";
        }

        if (df.equals(DataFormat.HTML)) {
            return "HTML";
        }

        if (df.equals(DataFormat.IMAGE)) {
            return "IMAGE";
        }

        if (df.equals(DataFormat.PLAIN_TEXT)) {
            return "PLAIN_TEXT";
        }

        if (df.equals(DataFormat.RTF)) {
            return "RTF";
        }

        if (df.equals(DataFormat.URL)) {
            return "URL";
        }

        if (df.equals(DF_CUSTOM_BYTES)) {
            return "DF_CUSTOM_BYTES";
        }

        if (df.equals(DF_CUSTOM_CLASS)) {
            return "DF_CUSTOM_CLASS";
        }

        if (df.equals(DF_CUSTOM_STRING)) {
            return "DF_CUSTOM_STRING";
        }

        return null;
    }

    protected String fullName(Object obj) {
        return obj.getClass().getSimpleName() + "." + obj.toString() + ".name()";
    }

    protected String normalize(String s) {
        return s.endsWith("s") ? s.substring(0, s.length() - 1) : s;
    }

    public static class ByRootNodeIdLookup<T extends Scene> extends ByStringLookup<T> {

        /**
         *
         * @param text the expected title
         */
        public ByRootNodeIdLookup(String text) {
            super(text);
        }

        /**
         *
         * @param text the expected title
         * @param policy a way to compare scene title to the expected
         */
        public ByRootNodeIdLookup(String text, StringComparePolicy policy) {
            super(text, policy);
        }

        @Override
        public String getText(final T control) {
            return new GetAction<String>() {
                @Override
                public void run(Object... parameters) {
                    Node root = control.getRoot();
                    if (root != null) {
                        setResult(root.getId());
                    } else {
                        setResult(null);
                    }
                }
            }.dispatch(Environment.getEnvironment());
        }
    }
}
