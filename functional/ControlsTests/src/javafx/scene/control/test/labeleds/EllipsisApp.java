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
package javafx.scene.control.test.labeleds;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuButton;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.Pane;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;

/**
 *
 * @author Andrey Glushchenko
 */
public class EllipsisApp extends BasicButtonChooserApp {

    private TestNode rootTestNode = null;
    private static int WIDTH = 600;
    private static int HEIGHT = 300;
    public static String TITLE = "Ellipsis Test";
    public static String TEST_NAME = "EllipsisStringTest";

    public EllipsisApp() {
        super(WIDTH, HEIGHT, TITLE, false);
    }
    private static String TEXT = "12 34\n56\n78\n901234567890012345678900123456789001234567890";
    private static String ELLIPSIS1 = "N\nN";
    private static String ELLIPSIS2 = "\tT";
    private static String ELLIPSIS3 = "www";

    @Override
    protected TestNode setup() {
        rootTestNode = new TestNode();
        rootTestNode.add(new TestPage(), TEST_NAME);
        return rootTestNode;
    }

    private class TestPage extends TestNode {

        private Pane p = null;
        private Button button1 = null;//Hyperlink,Button and ToggleButton
        private Button button2 = null;
        private Button button3 = null;
        private CheckBox box1 = null;//RadioButton and CheckBox
        private CheckBox box2 = null;
        private CheckBox box3 = null;
        private MenuButton mButton1 = null;//SplitMenuButton and MenuButton
        private MenuButton mButton2 = null;
        private MenuButton mButton3 = null;
        private int mWidth = 150;
        private int mHeight = 110;
        private int pWidth = 100;
        private int pHeight = 60;
        private OverrunStyle def = OverrunStyle.CENTER_ELLIPSIS;

        private Labeled createCase1(Labeled labeled) {
            labeled.setText(TEXT);
            labeled.setTextOverrun(def);

            labeled.setEllipsisString(ELLIPSIS1);
            return labeled;
        }

        private Labeled createCase2(Labeled labeled) {
            labeled.setText(TEXT);
            labeled.setTextOverrun(def);
            labeled.setEllipsisString(ELLIPSIS2);
            return labeled;
        }

        private Labeled createCase3(Labeled labeled) {
            labeled.setText(TEXT);
            labeled.setTextOverrun(def);
            labeled.setEllipsisString(ELLIPSIS3);
            return labeled;
        }

        @Override
        public Node drawNode() {
            p = new Pane();
            button1 = new Button();
            box1 = new CheckBox();
            mButton1 = new MenuButton();

            button1.setMaxHeight(mHeight);
            box1.setMaxHeight(mHeight);
            mButton1.setMaxHeight(mHeight);
            button1.setMaxWidth(mWidth);
            box1.setMaxWidth(mWidth);
            mButton1.setMaxWidth(mWidth);

            button1.setPrefHeight(pHeight);
            box1.setPrefHeight(pHeight);
            mButton1.setPrefHeight(pHeight);
            button1.setPrefWidth(pWidth);
            box1.setPrefWidth(pWidth);
            mButton1.setPrefWidth(pWidth);

            box1.setLayoutX(mWidth + 20);
            mButton1.setLayoutX((mWidth + 20) * 2);

            button2 = new Button();
            box2 = new CheckBox();
            mButton2 = new MenuButton();

            button2.setMaxHeight(mHeight);
            box2.setMaxHeight(mHeight);
            mButton2.setMaxHeight(mHeight);
            button2.setMaxWidth(mWidth);
            box2.setMaxWidth(mWidth);
            mButton2.setMaxWidth(mWidth);

            button2.setPrefHeight(pHeight);
            box2.setPrefHeight(pHeight);
            mButton2.setPrefHeight(pHeight);
            button2.setPrefWidth(pWidth);
            box2.setPrefWidth(pWidth);
            mButton2.setPrefWidth(pWidth);

            box2.setLayoutX(mWidth + 20);
            mButton2.setLayoutX((mWidth + 20) * 2);

            button2.setLayoutY(mHeight + 20);
            box2.setLayoutY(mHeight + 20);
            mButton2.setLayoutY(mHeight + 20);

            button3 = new Button();
            box3 = new CheckBox();
            mButton3 = new MenuButton();

            button3.setMaxHeight(mHeight);
            box3.setMaxHeight(mHeight);
            mButton3.setMaxHeight(mHeight);
            button3.setMaxWidth(mWidth);
            box3.setMaxWidth(mWidth);
            mButton3.setMaxWidth(mWidth);

            button3.setPrefHeight(pHeight);
            box3.setPrefHeight(pHeight);
            mButton3.setPrefHeight(pHeight);
            button3.setPrefWidth(pWidth);
            box3.setPrefWidth(pWidth);
            mButton3.setPrefWidth(pWidth);

            box3.setLayoutX(mWidth + 20);
            mButton3.setLayoutX((mWidth + 20) * 2);

            button3.setLayoutY((mHeight + 20) * 2);
            box3.setLayoutY((mHeight + 20) * 2);
            mButton3.setLayoutY((mHeight + 20) * 2);

            button1 = (Button) createCase1(button1);
            box1 = (CheckBox) createCase1(box1);
            mButton1 = (MenuButton) createCase1(mButton1);

            button3 = (Button) createCase3(button3);
            box3 = (CheckBox) createCase3(box3);
            mButton3 = (MenuButton) createCase3(mButton3);

            button2 = (Button) createCase2(button2);
            box2 = (CheckBox) createCase2(box2);
            mButton2 = (MenuButton) createCase2(mButton2);

            p.getChildren().addAll(button1, box1, mButton1,
                    button2, box2, mButton2,
                    button3, box3, mButton3);
            return p;
        }
    }

    public static void main(String[] args) {
        test.javaclient.shared.Utils.launch(EllipsisApp.class, args);
    }
}