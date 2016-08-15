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

import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.test.Change;
import javafx.scene.control.test.chart.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;

public abstract class TextInputBaseApp extends BaseApp {

    public enum Pages {

        appendText, clear, selectRange, selectEndOfNextWord, InputTest,
        CtrlDeleteTest, CtrlBackspaceTest,}

    {
        SLOT_WIDTH = 180;
        SLOT_HEIGHT = 80;
    }
    static final String INITIAL_TEXT = "Initial text";
    static final int INITIAL_TEXT_LEN = INITIAL_TEXT.length();
    public final static String MULTILINE_BTN_ID = "MBID";
    public final static String RANDOM_TEXT_BTN_ID = "RTBID";
    public final static String INPUT_AREA_ID = "IAID";
    final static String RANDOM_SYMBOLS_TEXT = "cwq34rjf 34kovm4 334r gfrgf4343,,78766\n"
            + "n2346ffg,4523/6578sdfg\tsdfgg as;lkbbg..\n"
            + "qwew123 12332 435456ddfgf! ghgfh!!sdfgfsdg56 tgrfgd*()#$%^\n"
            + "dfas4!@#$$%^^ gdhb 23545%$^#$%^4356 %*%&**^&* %&&*$%^&$^";
    final static String MULTI_LINE_TEXT = "Line 5 line 5 line 5 line 5 line 5 line 5\n"
            + "Line 2 line 2 line 2 line 2 line 2 line 2\n"
            + "Line 3 line 3 line 3 line 3 line 3 line 3\n"
            + "Line 4 line 4 line 4 line 4 line 4 line 4\n"
            + "Line 5 line 5 line 5 line 5 line 5 line 5\n";
    List<Change<TextInputControl>> changes = TextInputChanger.getTextInputChanges(new PageReporter());

    protected TextInputBaseApp(int width, int height, String title, boolean showAdditionalActionButton) {
        super(width, height, title, showAdditionalActionButton);

        setupCtrlDelete_BackspaceTest(Pages.CtrlBackspaceTest);

        setupCtrlDelete_BackspaceTest(Pages.CtrlDeleteTest);
    }

    private void setupCtrlDelete_BackspaceTest(final Pages p) {
        TestNode tn = new TestNode(p.name()) {
            @Override
            public Node drawNode() {
                Pane pane = new Pane();
                final TextInputControl ta = (TextInputControl) createObject(210.0, 100.0);
                ta.setId(INPUT_AREA_ID + this.getName());
                Button b1 = new Button(),
                        b2 = new Button();
                b1.setText("MULTILINE TEXT");
                b2.setText("RANDOM SYMBOLS TEXT");
                b1.setId(MULTILINE_BTN_ID + p.name());
                b2.setId(RANDOM_TEXT_BTN_ID + p.name());
                b1.setOnMouseClicked(new EventHandler() {
                    public void handle(Event t) {
                        ta.setText(MULTI_LINE_TEXT);
                    }
                });
                b2.setOnMouseClicked(new EventHandler() {
                    public void handle(Event t) {
                        ta.setText(RANDOM_SYMBOLS_TEXT);
                    }
                });
                VBox box = new VBox();
                box.getChildren().addAll(ta, b1, b2);
                pane.getChildren().add(box);
                return pane;
            }
        };
        rootTestNode.add(tn, p.name());
    }

    protected class EmptyNode extends TestNode {

        public EmptyNode() {
        }

        @Override
        public Node drawNode() {
            text = "one two three four five"; // TODO: find appropriate method
            return (TextInputControl) createObject();
        }
    }

    protected class ChangerNode extends TestNode {

        Change change;

        public ChangerNode(Change change) {
            this.change = change;
        }

        @Override
        public Node drawNode() {
            TextInputControl text_input = (TextInputControl) createObject();
            change.apply(text_input);
            return text_input;
        }
    }

    @Override
    protected TestNode setup() {

        final PageWithSlots interactive_page = new PageWithSlots(Pages.InputTest.name(), height, width);
        interactive_page.add(new EmptyNode(), Pages.InputTest.name());
        rootTestNode.add(interactive_page);

        String last_name = new String();
        PageWithSlots page = null;
        for (Change change : changes) {
            if (last_name != change.getName()) {
                last_name = change.getName();
                page = new PageWithSlots(change.getName(), height, width);
                page.add(new ChangerNode(change), change.getSuffix());
            } else {
                page.add(new ChangerNode(change), change.getSuffix());
            }
        }

        return rootTestNode;
    }

    class PageReporter extends Reporter {

        @Override
        public void report(String string) {
            reportGetterFailure(text + "\n" + getStackTrace());
        }

        protected String getStackTrace() {
            String trace = "Stack trace:\n";
            for (StackTraceElement ste : new Throwable().getStackTrace()) {
                trace += "\t" + ste + "\n";
            }
            return trace;
        }
    }
    protected String text = INITIAL_TEXT;

    protected abstract Object createObject();

    protected abstract Object createObject(double width, double height);
}
