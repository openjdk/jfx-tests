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

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 * @author Andrey Glushchenko
 */
public class ProgressApp extends BasicButtonChooserApp {

    public static enum Pages {

        Constructors, IndeterminedConstructors, ProgressIndicator, ProgressBar
    }
    private static final int spacing = 100;
    protected static Utils.LayoutSize progressBarLayout = new Utils.LayoutSize(150, 10, 150, 10, 150, 10);
    protected static Utils.LayoutSize progressIndicatorLayout = new Utils.LayoutSize(50, 50, 50, 50, 50, 50);

    private static ProgressIndicator progressIndicatorCreate() {
        ProgressIndicator progress = new ProgressIndicator(0);
        progressIndicatorLayout.apply(progress);
        return progress;
    }

    private static ProgressBar progressBarCreate() {
        ProgressBar progress = new ProgressBar(0);
        progressBarLayout.apply(progress);
        return new ProgressBar(0);
    }

    public ProgressApp() {
        super(600, 200, "Progress", false);
    }

    @Override
    protected TestNode setup() {
        TestNode root = new TestNode();
        root.add(new ProgressApp.ProgressConstructorsPage(), ProgressApp.Pages.Constructors.name());
        root.add(new ProgressApp.ProgressIndeterminedConstructorsPage(), ProgressApp.Pages.IndeterminedConstructors.name());
        root.add(new ProgressApp.ProgressIndicatorPage(), ProgressApp.Pages.ProgressIndicator.name());
        root.add(new ProgressApp.ProgressBarPage(), ProgressApp.Pages.ProgressBar.name());
        return root;
    }

    private class ProgressConstructorsPage extends TestNode {

        @Override
        public Node drawNode() {
            HBox root = new HBox();
            root.setSpacing(spacing);
            ProgressIndicator determined_progress_indicator = new ProgressIndicator(0.25);
            progressIndicatorLayout.apply(determined_progress_indicator);
            ProgressBar determined_progress_bar = new ProgressBar(0.25);
            progressBarLayout.apply(determined_progress_bar);
            root.getChildren().addAll(determined_progress_indicator, determined_progress_bar);
            return root;
        }
    }

    private class ProgressIndeterminedConstructorsPage extends TestNode {

        @Override
        public Node drawNode() {
            HBox root = new HBox();
            root.setSpacing(spacing);
            ProgressIndicator indetermined_progress_indicator = new ProgressIndicator();
            progressIndicatorLayout.apply(indetermined_progress_indicator);
            ProgressBar indetermined_progress_bar = new ProgressBar();
            progressBarLayout.apply(indetermined_progress_bar);
            root.getChildren().addAll(indetermined_progress_indicator, indetermined_progress_bar);
            return root;
        }
    }

    private class ProgressIndicatorPage extends TestNode {

        @Override
        public Node drawNode() {
            GridPane root = new GridPane();
            root.setVgap(spacing / 2);
            root.setHgap(spacing);
            double d, _d = -0.25;
            for (int i = 0; _d < 1; i++) {
                d = _d + 0.25;
                _d = d;
                ProgressIndicator ind = progressIndicatorCreate();
                VBox box = new VBox();
                box.setAlignment(Pos.CENTER);
                box.getChildren().add(new Label("[" + d + "]"));
                ind.setProgress(d);
                if (ind.getProgress() != d) {
                    reportGetterFailure("progress_indicator.getProgress()");
                }
                box.getChildren().add(ind);
                root.add(box, i % 3, i / 3);
            }
            return root;
        }
    }

    private class ProgressBarPage extends TestNode {

        @Override
        public Node drawNode() {
            GridPane root = new GridPane();
            root.setVgap(spacing / 2);
            root.setHgap(spacing);
            double d, _d = -0.25;
            for (int i = 0; _d < 1; i++) {
                d = _d + 0.25;
                _d = d;
                ProgressBar bar = progressBarCreate();
                VBox box = new VBox();
                box.setAlignment(Pos.CENTER);
                box.getChildren().add(new Label("[" + d + "]"));
                bar.setProgress(d);
                if (bar.getProgress() != d) {
                    reportGetterFailure("progress_indicator.getProgress()");
                }
                box.getChildren().add(bar);
                root.add(box, i % 3, i / 3);
            }
            return root;
        }
    }

    public static void main(String[] args) {
        Utils.launch(ProgressApp.class, args);

    }
}
