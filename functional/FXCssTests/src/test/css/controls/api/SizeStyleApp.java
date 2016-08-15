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
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import test.css.controls.ControlPage;
import test.css.controls.ControlsCSSApp;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.ScrollablePageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author sergey.lugovoy@oracle.com
 */
public class SizeStyleApp extends BasicButtonChooserApp {

    private static int WIDTH = 800;
    private static int HEIGHT = 600;
    public static final String CONTROL_ID = "control_id";
    public static final String GOLDEN_CONTROL_ID = "golden_control_id";

    public SizeStyleApp() {
        super(WIDTH, HEIGHT, "Size style, fx : " + VersionInfo.getRuntimeVersion(), false);
    }

    public static TestNode createContainer(Region control, Region goldenControl, SizePages page) {
        final HBox pane = new HBox();
        pane.setSpacing(50);
        control.getStyleClass().addAll(page.toString());
        pane.setMinSize(250, 250);
        control.setId(CONTROL_ID);
        goldenControl.setId(GOLDEN_CONTROL_ID);
        page.setSize(goldenControl);
        pane.getChildren().addAll(control, goldenControl);
        return new TestNode() {
            @Override
            public Node drawNode() {
                return pane;
            }
        };
    }

    public enum SizePages {

        MIN_WIDTH(new SizeSetter() {
            @Override
            public void setSize(Region control) {
                control.setMinWidth(100);
            }
        }), MAX_WIDTH(new SizeSetter() {
            @Override
            public void setSize(Region control) {
                control.setMaxWidth(50);
            }
        }), MIN_HEIGHT(new SizeSetter() {

            @Override
            public void setSize(Region control) {
                control.setMinHeight(100);
            }
        }), MAX_HEIGHT(new SizeSetter() {

            @Override
            public void setSize(Region control) {
                control.setMaxHeight(50);
            }
        }), PREF_WIDTH(new SizeSetter() {

            @Override
            public void setSize(Region control) {
                control.setPrefWidth(100);
            }
        }), PREF_HEIGHT(new SizeSetter() {

            @Override
            public void setSize(Region control) {
                control.setPrefHeight(100);
            }
        });
        private SizeSetter sizeSetter;

        private SizePages(SizeSetter setter) {
            this.sizeSetter = setter;
        }

        private static interface SizeSetter {
            public void setSize(Region control);
        };

        public void setSize(Region control) {
            sizeSetter.setSize(control);
        }
    }



    @Override
    protected TestNode setup() {
        URL css = ControlsCSSApp.class.getResource("/test/css/resources/sizes.css");
        combinedTestChooserPresenter.getScene().getStylesheets().add(css.toExternalForm());
        TestNode rootNode = new TestNode();
        for (ControlPage page : ControlPage.values()) {
            ScrollablePageWithSlots pageWithSlots = new ScrollablePageWithSlots(page.name(), WIDTH, HEIGHT - 30);
            for (SizePages pages : SizePages.values()) {
                Node node  = page.factory.createControl();
                if (node instanceof Region){
                    pageWithSlots.add(createContainer((Region) node , (Region) page.factory.createControl(), pages), pages.name());
                }
            }
            rootNode.add(pageWithSlots);
        }
        return rootNode;
    }

    public static void main(String[] args) {
        Utils.launch(SizeStyleApp.class, args);
    }
}
