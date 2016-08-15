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
 * questions.
 */
package org.jemmy.samples.text;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.TextDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.fx.control.TextInputControlDock;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.samples.SampleBase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * How to access text node and text input controls with Jemmy
 *
 * @author shura
 */
public class TextSample extends SampleBase {

    static SceneDock scene;
    @BeforeClass
    public static void launch() throws InterruptedException {
        startApp(TextApp.class);
        scene = new SceneDock();
    }

    @Before
    public void reset() {
        new LabeledDock(scene.asParent(), "Reset", StringComparePolicy.EXACT).mouse().click();
    }

    /**
     * How to find a text control.
     */
    @Test
    public void lookup() {
        //naturally, you could find it by it's text
        //we will find a Text here
        TextDock text = new TextDock(scene.asParent(), "uneditable",
                StringComparePolicy.SUBSTRING);
        assertTrue(text.wrap().getControl() instanceof Text);

        //text, however, could be unknown or even empty, so, you would want to consider
        //one of the generic lookup approaches.
        //such as by id
        //that would be a TextField
        TextInputControlDock singleLine = new TextInputControlDock(scene.asParent(),
                "single");
        assertTrue(singleLine.wrap().getControl() instanceof TextField);

        //than, again, you may use node type
        //lets find a text area
        TextInputControlDock multiLine = new TextInputControlDock(scene.asParent(),
                TextArea.class);
        assertTrue(multiLine.wrap().getControl() instanceof TextArea);

        //or a text area with certain sub-text
        TextInputControlDock multiLine2 = new TextInputControlDock(scene.asParent(),
                TextArea.class, "line", StringComparePolicy.SUBSTRING);
        assertEquals(multiLine.control(), multiLine2.control());

    }

    /**
     * How to navigate in a text control.
     */
    @Test
    public void navigate() {
        TextInputControlDock multiLine = new TextInputControlDock(scene.asParent(),
                "multi", StringComparePolicy.SUBSTRING);

        //of course you could move cursor to a position
        multiLine.asSelectionText().caret().to(4);

        //or to a portion of text
        multiLine.asSelectionText().to("line");

        //or to an end of a portion of text
        multiLine.asSelectionText().to("text", false);

        //or to an end of an index'th occurance os a portion of text
        multiLine.asSelectionText().to("t", false, 1);

        //where a portion of text is really a regex
        multiLine.asSelectionText().to("[ue].t");
    }

    /**
     * How to select text in a text control.
     */
    @Test
    public void select() {
        TextInputControlDock multiLine = new TextInputControlDock(scene.asParent(),
                "multi", StringComparePolicy.SUBSTRING);

        //of course you could select a portion of text by position
        multiLine.asSelectionText().caret().to(6);
        multiLine.asSelectionText().caret().selectTo(10);

        //or by text
        multiLine.asSelectionText().select("multi\n");

        //which could be a regex
        multiLine.asSelectionText().select("[ue].t", 1);
    }

    /**
     * How to clear text in a text control.
     */
    @Test
    public void clear() {
        TextInputControlDock multiLine = new TextInputControlDock(scene.asParent(),
                "multi", StringComparePolicy.SUBSTRING);

        //just clear
        multiLine.asSelectionText().clear();
    }


    /**
     * How to type text in a text control.
     */
    @Test
    public void type() {
        TextInputControlDock singleLine = new TextInputControlDock(scene.asParent(),
                "single", StringComparePolicy.SUBSTRING);
        TextInputControlDock multiLine = new TextInputControlDock(scene.asParent(),
                "multi", StringComparePolicy.SUBSTRING);

        //typing starts right where cursor it
        multiLine.asSelectionText().to("text\n", false);

        multiLine.asSelectionText().type("and we are adding another line\n");

        //so, if you are going to replace the whole text, clear old text first
        singleLine.clear();
        singleLine.type("still a single line");
    }

}
