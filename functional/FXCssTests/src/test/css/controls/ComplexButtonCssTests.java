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
package test.css.controls;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.ScrollablePageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.TestNodeLeaf;
import test.javaclient.shared.Utils;

/**
 *
 * @author sergey.lugovoy@oracle.com
 */
public class ComplexButtonCssTests extends BasicButtonChooserApp {

    private static String stylesheets = "/test/css/resources/Buttons.css";
    private static int WIDTH = 400;
    private static int HEIGHT = 400;

    public ComplexButtonCssTests() {
        super(WIDTH, HEIGHT, "ComplexCssTests", false);
    }

    public static enum Pages {
        GREEN, ROUND_RED, BEVEL_GREY, GLASS_GREY, SHINY_ORANGE, DARK_BLUE, RECORD_SALES,
        RICH_BLUE, BIG_YELLOW, IPHONE_TOOLBAR, IPHONE, IPAD_DARK_GREY, IPAD_GREY,
        LION_DEFAULT, LION, WINDOWS7_DEFAULT, WINDOWS7;
    }

    @Override
    protected TestNode setup() {
        ScrollablePageWithSlots node = new ScrollablePageWithSlots("Complex Css Tests", WIDTH, HEIGHT);
        combinedTestChooserPresenter.getScene().getStylesheets().add(getClass().getResource(stylesheets).toExternalForm());
        for (Pages page : Pages.values()) {
            node.add(getPageSlot(page));
        }
        return node;
    }

    private PageWithSlots getPageSlot(Pages page) {
        PageWithSlots pageWithSlot = new PageWithSlots(page.name(), WIDTH, HEIGHT);
        Pane slot = new Pane();
        slot.setPrefSize(200, 200);
        if (page == Pages.IPHONE_TOOLBAR) {
            Button b = new Button("iPhone");
            b.setId("iphone");
            ToolBar temp = new ToolBar(b);
            temp.setId(page.name().toLowerCase());
            slot.getChildren().add(temp);
        } else {
            Button temp = new Button("Button");
            temp.setId(page.name().toLowerCase());
            temp.setMinWidth(100);
            slot.getChildren().add(temp);
        }
        pageWithSlot.add(new TestNodeLeaf(page.name(), slot));
        return pageWithSlot;
    }

    public static void main(String[] args) {
        Utils.launch(ComplexButtonCssTests.class, args);
    }
}
