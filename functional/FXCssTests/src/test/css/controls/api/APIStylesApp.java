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
package test.css.controls.api;

import com.sun.javafx.runtime.VersionInfo;
import java.net.URL;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import test.css.controls.ControlPage;
import test.css.controls.ControlsCSSApp;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.ScrollablePageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.TestNodeLeaf;
import test.javaclient.shared.Utils;

/**
 * @author sergey.lugovoy@oracle.com
 */
public class APIStylesApp extends BasicButtonChooserApp {

    private static int WIDTH = 1500;
    private static int HEIGHT = 900;

    TestNode rootTestNode = new TestNode();

    public APIStylesApp() {
        this(WIDTH, HEIGHT, VersionInfo.getRuntimeVersion() + ", Background API Tests", false);
    }

    public APIStylesApp(int width, int height, String title, boolean showAdditionalActionButton) {
        super(width, height, title, showAdditionalActionButton);
    }

    @Override
    protected TestNode setup() {
        URL css = ControlsCSSApp.class.getResource("/test/css/resources/style.css");
        combinedTestChooserPresenter.getScene().getStylesheets().add(css.toExternalForm());
        if (showButtons) {
            for (ControlPage page : ControlPage.filteredValues()) {
                createPages(page, rootTestNode);
            }
        }
        return rootTestNode;
    }

    private void createPages (ControlPage page, TestNode rootNode) {
        ScrollablePageWithSlots pageWithSlot = new ScrollablePageWithSlots(page.name(), width, height);
        createPage(page, pageWithSlot);
        rootNode.add(pageWithSlot);
    }

    private void createPage(ControlPage page, TestNode rootNode) {
        for (APIControlPage stylePage : APIControlPage.values()) {
            Pane slot = new Pane();
            slot.setPrefSize(page.slotWidth, page.slotHeight);
            final Node control = page.factory.createControl();
            Pane innerPane = new Pane();
            innerPane.setLayoutX(page.INNER_PANE_SHIFT);
            innerPane.setLayoutY(page.INNER_PANE_SHIFT);
            innerPane.setPrefSize(page.slotWidth - page.INNER_PANE_SHIFT, page.slotHeight - ControlPage.INNER_PANE_SHIFT);
            innerPane.setMaxSize(page.slotWidth - page.INNER_PANE_SHIFT, page.slotHeight - page.INNER_PANE_SHIFT);
            innerPane.getChildren().add(control);
            if (showButtons) {
                stylePage.setStyle(control);
            }
            rootNode.add(new TestNodeLeaf(stylePage.name().replace("_", "-"), innerPane));
        }
    }

    public void open(final ControlPage page) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createPages(page, rootTestNode);
            }
        });
    }

    public static void main(String[] args) {
        Utils.launch(APIStylesApp.class, args);
    }
}