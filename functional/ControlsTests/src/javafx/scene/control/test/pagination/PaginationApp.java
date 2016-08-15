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
package javafx.scene.control.test.pagination;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.test.utils.*;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class PaginationApp extends InteroperabilityApp {

    public final static String TESTED_PAGINATION_ID = "TESTED_PAGINATION_ID";
    public final static String HARD_RESET_BUTTON_ID = "HARD_RESET_PAGINATION_BUTTON_ID";
    public final static String SOFT_RESET_BUTTON_ID = "SOFT_RESET_PAGINATION_BUTTON_ID";
    public final static String SET_NEW_PAGE_FACTORY_BUTTON_ID = "SET_NEW_PAGE_FACTORY_BUTTON_ID";
    public final static String SET_OLD_PAGE_FACTORY_BUTTON_ID = "SET_OLD_PAGE_FACTORY_BUTTON_ID";
    public final static String SET_BULLET_PAGE_INDICATOR_BUTTON_ID = "SET_BULLET_PAGE_INDICATOR_BUTTON_ID";
    public final static String SET_PAGE_COUNT_TO_INDETERMINATE_BUTTON_ID = "SET_PAGE_COUNT_TO_INDETERMINATE_BUTTON_ID";
    public final static String OLD_FACTORY_MARKER = "factory : old";
    public final static String NEW_FACTORY_MARKER = "factory : new";
    public final static String PAGE_INDEX_PREFIX = "PAGE : ";
    public final static int LABELS_PER_PAGE = 10;

    public static void main(String[] args) {
        Utils.launch(PaginationApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "PaginationTestApp");
        return new PaginationApp.PaginationScene();
    }

    class PaginationScene extends CommonPropertiesScene {

        PropertiesTable tb;
        Pagination testedPagination;
        final int i = 10;
        private String[] content;
        private static final int MAX_CONTENT = 2500;

        public PaginationScene() {
            super("Pagination", 800, 600);

            prepareScene();
        }

        @Override
        final protected void prepareScene() {
            Utils.addBrowser(this);

            content = new String[MAX_CONTENT];
            for (int i = 0; i < MAX_CONTENT; i++) {
                content[i] = "some text " + i;
            }

            testedPagination = new Pagination(i);
            testedPagination.setId(TESTED_PAGINATION_ID);

            testedPagination.setPageFactory(new Callback<Integer, Node>() {
                @Override
                public Node call(Integer pageIndex) {
                    return createPage(pageIndex);
                }
            });

            tb = new PropertiesTable(testedPagination);
            PropertyTablesFactory.explorePropertiesList(testedPagination, tb);
            SpecialTablePropertiesProvider.provideForControl(testedPagination, tb);
            tb.addDoublePropertyLine(testedPagination.minHeightProperty(), -100, 300, 50, testedPagination);
            tb.addDoublePropertyLine(testedPagination.minWidthProperty(), -100, 300, 50, testedPagination);

            Button hardResetButton = ButtonBuilder.create().id(HARD_RESET_BUTTON_ID).text("Hard reset").build();
            hardResetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareMainSceneStructure();
                    prepareScene();
                }
            });

            Button setBulletPageIndicatorButton = ButtonBuilder.create().id(SET_BULLET_PAGE_INDICATOR_BUTTON_ID).text("Set bullet").build();
            setBulletPageIndicatorButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    //testedPagination.setStyle(Pagination.STYLE_CLASS_BULLET);
                    testedPagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
                }
            });

            Button setPageCountToIndeterminateButton = ButtonBuilder.create().id(SET_PAGE_COUNT_TO_INDETERMINATE_BUTTON_ID).text("Set indeterminate\n page count").build();
            setPageCountToIndeterminateButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedPagination.setPageCount(Pagination.INDETERMINATE);
                }
            });

            Button softResetButton = ButtonBuilder.create().id(SOFT_RESET_BUTTON_ID).text("Soft reset").build();
            softResetButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    tb.refresh();
                    Pagination newOne = new Pagination(i);
                    testedPagination.setCurrentPageIndex(newOne.getCurrentPageIndex());
                    testedPagination.setPageCount(newOne.getPageCount());
                    testedPagination.setPageFactory(newOne.getPageFactory());
                    testedPagination.setMaxPageIndicatorCount(newOne.maxPageIndicatorCountProperty().get());
                }
            });

            Button setNewPageFactory = ButtonBuilder.create().id(SET_NEW_PAGE_FACTORY_BUTTON_ID).text("Set new page factory").build();
            setNewPageFactory.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    Callback<Integer, Node> newOne = new Callback<Integer, Node>() {
                        @Override
                        public Node call(Integer pageIndex) {
                            return createPage2(pageIndex);
                        }
                    };

                    Callback<Integer, Node> oldOne = testedPagination.getPageFactory();

                    testedPagination.setPageFactory(newOne);

                    assertTrue(testedPagination.pageFactoryProperty().getValue().equals(newOne));
                    assertTrue(testedPagination.getPageFactory().equals(newOne));
                    assertFalse(testedPagination.pageFactoryProperty().getValue().equals(oldOne));
                    assertFalse(testedPagination.getPageFactory().equals(oldOne));
                }
            });

            Button setOldPageFactory = ButtonBuilder.create().id(SET_OLD_PAGE_FACTORY_BUTTON_ID).text("Set old page factory").build();
            setOldPageFactory.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    Callback<Integer, Node> newOne = new Callback<Integer, Node>() {
                        @Override
                        public Node call(Integer pageIndex) {
                            return createPage(pageIndex);
                        }
                    };

                    Callback<Integer, Node> oldOne = testedPagination.getPageFactory();

                    testedPagination.setPageFactory(newOne);

                    assertTrue(testedPagination.pageFactoryProperty().getValue().equals(newOne));
                    assertTrue(testedPagination.getPageFactory().equals(newOne));
                    assertFalse(testedPagination.pageFactoryProperty().getValue().equals(oldOne));
                    assertFalse(testedPagination.getPageFactory().equals(oldOne));
                }
            });

            HBox resetButtonsHBox = new HBox();
            resetButtonsHBox.getChildren().addAll(hardResetButton, softResetButton);

            VBox vb = new VBox();
            vb.setSpacing(5);
            vb.getChildren().addAll(resetButtonsHBox, setNewPageFactory,
                    setOldPageFactory, setBulletPageIndicatorButton,
                    setPageCountToIndeterminateButton);
            setControllersContent(vb);
            setTestedControl(testedPagination);
            setPropertiesContent(tb);
        }

        public VBox createPage(int pageIndex) {
            checkIndexCorrectness(pageIndex);

            VBox box = new VBox(5);
            int page = pageIndex * 10;
            for (int i = 0; i < 10; i++) {
                Label l = new Label(PAGE_INDEX_PREFIX + pageIndex + "; " + OLD_FACTORY_MARKER + "; " + content[page + i % MAX_CONTENT]);
                box.getChildren().add(l);
            }
            box.getChildren().addAll(ComponentsFactory.createFormComponent(), ComponentsFactory.createCustomContent(100, 100));
            return box;
        }

        public VBox createPage2(int pageIndex) {
            checkIndexCorrectness(pageIndex);

            VBox box = new VBox(5);
            int page = pageIndex * 10;
            for (int i = 0; i < LABELS_PER_PAGE; i++) {
                Label l = new Label(PAGE_INDEX_PREFIX + pageIndex + "; " + NEW_FACTORY_MARKER + "; " + content[page + i % MAX_CONTENT]);
                box.getChildren().add(l);
            }
            box.getChildren().addAll(ComponentsFactory.createCustomContent(100, 100));
            return box;
        }

        private void checkIndexCorrectness(int index) {
            if (testedPagination.getPageCount() != Pagination.INDETERMINATE) {
                if ((index < 0) || (index > Math.max(testedPagination.getPageCount() - 1, 0))) {
                    throw new IllegalArgumentException("Incorrect index : " + index);
                }
            }
        }
    }
}
