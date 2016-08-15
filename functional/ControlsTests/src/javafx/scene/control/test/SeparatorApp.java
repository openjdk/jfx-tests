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
package javafx.scene.control.test;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 * @author Andrey Glushchenko
 */
public class SeparatorApp extends BasicButtonChooserApp {

    public static enum Pages {

        Constructors, VSeparator, HSeparator
    }

    public static enum SeparatorsPages {

        isVertical, setHalignment, setValignment
    }
    private static final int spacing = 100;
    private static final Utils.LayoutSize layout = new Utils.LayoutSize(50, 50, 50, 50, 50, 50);

    public SeparatorApp() {
        super(800, 200, "Separators", false);
    }

    @Override
    protected TestNode setup() {
        TestNode root = new TestNode();
        HorizontalSeparatorPage hsp = new HorizontalSeparatorPage();
        VerticalSeparatorPage vsp = new VerticalSeparatorPage();

        root.add(new SeparatorConstructorsPage(), Pages.Constructors.name());
        for (SeparatorsPages page : SeparatorsPages.values()) {
            root.add(vsp.getPage(page), Pages.VSeparator.name() + "-" + page.name());
            root.add(hsp.getPage(page), Pages.HSeparator.name() + "-" + page.name());
        }

        return root;
    }

    private class SeparatorConstructorsPage extends TestNode {

        @Override
        public Node drawNode() {
            HBox root = new HBox();
            root.setSpacing(spacing);
            Separator separator = new Separator();
            if (separator.getOrientation() != Orientation.HORIZONTAL) {
                reportGetterFailure("vertical_separator.getOrientation()");
            }
            if (separator.getValignment() != VPos.CENTER) {
                reportGetterFailure("separator.getValignment()");
            }
            if (separator.getHalignment() != HPos.CENTER) {
                reportGetterFailure("separator.getHalignment()");
            }
            layout.apply(separator);

            root.getChildren().add(separator);

            Separator vertical_separator = new Separator(Orientation.VERTICAL);
            if (vertical_separator.getOrientation() != Orientation.VERTICAL) {
                reportGetterFailure("vertical_separator.getOrientation()");
            }
            if (vertical_separator.getValignment() != VPos.CENTER) {
                reportGetterFailure("vertical_separator.getValignment()");
            }
            if (vertical_separator.getHalignment() != HPos.CENTER) {
                reportGetterFailure("vertical_separator.getHalignment()");
            }
            layout.apply(vertical_separator);
            root.getChildren().add(vertical_separator);

            Separator horizontal_separator = new Separator(Orientation.HORIZONTAL);
            if (horizontal_separator.getOrientation() != Orientation.HORIZONTAL) {
                reportGetterFailure("horizontal_separator.getOrientation()");
            }
            if (horizontal_separator.getValignment() != VPos.CENTER) {
                reportGetterFailure("horizontal_separator.getValignment()");
            }
            if (horizontal_separator.getHalignment() != HPos.CENTER) {
                reportGetterFailure("horizontal_separator.getHalignment()");
            }
            layout.apply(horizontal_separator);
            root.getChildren().add(horizontal_separator);
            return root;
        }
    }

    private class VerticalSeparatorPage extends SeparatorPageBase {

        @Override
        protected Separator getSeparator() {
            Separator separator = new Separator(Orientation.VERTICAL);
            layout.apply(separator);
            return separator;
        }
    }

    private class HorizontalSeparatorPage extends SeparatorPageBase {

        @Override
        protected Separator getSeparator() {
            Separator separator = new Separator(Orientation.HORIZONTAL);
            layout.apply(separator);
            return separator;
        }
    }

    private abstract class SeparatorPageBase extends TestNode {

        protected abstract Separator getSeparator();

        public TestNode getPage(SeparatorsPages page) {
            if (page == SeparatorsPages.isVertical) {
                return new IsVerticalPage();
            }
            if (page == SeparatorsPages.setHalignment) {
                return new SetHalignmentPage();
            }
            if (page == SeparatorsPages.setValignment) {
                return new SetValignmentPage();
            }
            return null;
        }

        public SeparatorPageBase() {
            super();
        }

        private class IsVerticalPage extends TestNode {

            private IsVerticalPage() {
                super();
            }

            @Override
            public Node drawNode() {
                HBox root = new HBox();
                Separator separator = getSeparator();
                separator.setOrientation(Orientation.VERTICAL);
                if (separator.getOrientation() != Orientation.VERTICAL) {
                    reportGetterFailure("separator.setOrientation()");
                }
                VBox box = new VBox();
                box.getChildren().addAll(new Label("[isVertical]"), separator);
                box.setAlignment(Pos.CENTER);
                root.getChildren().add(box);
                return root;
            }
        }

        private class SetHalignmentPage extends TestNode {

            private SetHalignmentPage() {
                super();
            }

            @Override
            public Node drawNode() {
                HBox root = new HBox();
                root.setSpacing(spacing);
                for (HPos pos : HPos.values()) {
                    Separator separator = getSeparator();
                    separator.setHalignment(pos);
                    if (separator.getHalignment() != pos) {
                        reportGetterFailure("separator.setHalignment()");
                    }
                    VBox box = new VBox();
                    box.getChildren().addAll(new Label("[" + pos.name() + "]"), separator);
                    root.getChildren().add(box);
                }
                return root;
            }
        }

        private class SetValignmentPage extends TestNode {

            private SetValignmentPage() {
                super();
            }

            @Override
            public Node drawNode() {
                HBox root = new HBox();
                root.setSpacing(spacing);
                for (VPos pos : VPos.values()) {
                    Separator separator = getSeparator();
                    separator.setValignment(pos);
                    if (separator.getValignment() != pos) {
                        reportGetterFailure("separator.setHalignment()");
                    }
                    VBox box = new VBox();
                    box.getChildren().addAll(new Label("[" + pos.name() + "]"), separator);
                    root.getChildren().add(box);
                }
                return root;
            }
        }
    }

    public static void main(String[] args) {
        Utils.launch(SeparatorApp.class, args);

    }
}