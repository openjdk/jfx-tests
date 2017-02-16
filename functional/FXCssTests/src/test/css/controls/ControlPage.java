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

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.scene.control.skin.CellSkinBase;
import javafx.scene.control.skin.ColorPickerSkin;
import javafx.scene.control.skin.PaginationSkin;
import javafx.scene.control.skin.ProgressIndicatorSkin;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.scene.control.skin.TextInputControlSkin;
import javafx.scene.control.skin.TreeCellSkin;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author sergey.lugovoy@oracle.com
 */
public enum ControlPage {
    Accordions(Accordion.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            TitledPane pane1 = new TitledPane();
            pane1.setText("title 1\nLong text long text");
            pane1.setContent(new Rectangle(100, 40, Color.SKYBLUE));
            pane1.setFocusTraversable(false);
            TitledPane pane2 = new TitledPane();
            pane2.setText("title 2\nLong text long text");
            pane2.setContent(new Rectangle(100, 40, Color.BLUEVIOLET));
            Accordion acc = new Accordion();
            acc.getPanes().addAll(pane1, pane2);
            acc.setExpandedPane(pane2);
            pane2.setAnimated(false);
            pane2.setFocusTraversable(false);
            acc.setFocusTraversable(false);
            return acc;
        }
    }, 200), Buttons(Button.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Control createControl() {
            Button temp = new Button("Button the first line" + "\nthe sec long line" + "\nthe third line");
            temp.setFocusTraversable(false);
            temp.setGraphic(new Rectangle(10, 10, Color.RED));
            return temp;
        }
    }, 200, 100), ChoiceBoxes(ChoiceBox.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Control createControl() {
            return new ChoiceBox() {
                {
                    getItems().addAll(Arrays.asList("one", "two", "three"));
                    this.getSelectionModel().select(0);
                    setFocusTraversable(false);
                }
            };
        }
    }), CheckBoxes(CheckBox.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Control createControl() {
            CheckBox temp = new CheckBox("Check box the first line" + "\nthe sec long line" + "\nthe third line");
            temp.setFocusTraversable(false);
            temp.setGraphic(new Rectangle(20, 20, Color.web("lightblue")));
            return temp;
        }
    }, 200, 100), ComboBoxes(ComboBox.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        @Override
        public Control createControl() {
            return new ComboBox() {
                {
                    getItems().addAll(Arrays.asList("one", "two", "three"));
                    getSelectionModel().select(0);
                    setFocusTraversable(false);
                }
            };
        }
    }), Hyperlinks(Hyperlink.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Control createControl() {
            Hyperlink temp = new Hyperlink("Hyperlink the first line" + "\nthe sec long line" + "\nthe third line");
            temp.setFocusTraversable(false);
            temp.setGraphic(new Circle(10, Color.BLUE));
            return temp;
        }
    }, 200, 100), Labels(new ArrayList<CssMetaData<? extends Styleable, ?>>(Label.getClassCssMetaData()) {
        {
            addAll(Text.getClassCssMetaData());
        }
    }, new ControlsCSSApp.ControlFactory() {
        public Control createControl() {
            Label temp = new Label("Label the first line" + "\nthe sec longlong line" + "\nthe third line");
            temp.setFocusTraversable(false);
            temp.setGraphic(new Rectangle(20, 20, Color.web("lightblue")));
            return temp;
        }
    }, 200), ListViews(ListView.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            ListView list = new ListView();
            ObservableListWrapper<String> items = new ObservableListWrapper<String>(Arrays.asList("One", "Two", "Three", "Four", "Five", "Six", "long line long line long line long line"));
            list.setItems(items);
            list.setPrefWidth(100);
            list.setPrefHeight(100);
            list.setFocusTraversable(false);
            return list;
        }
    }, 150), PasswordFields(PasswordField.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Control createControl() {
            PasswordField temp = new PasswordField();
            temp.setPromptText("Password box the first line" + "\nthe sec long line" + "\nthe third line");
            temp.setFocusTraversable(false);
            return temp;
        }
    }), PressedToggleButtons(ToggleButton.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Control createControl() {
            ToggleButton temp = new ToggleButton("Button the first line" + "\nthe sec long line" + "\nthe third line");
            temp.setSelected(true);
            temp.setFocusTraversable(false);
            temp.setGraphic(new Rectangle(10, 10, Color.RED));
            return temp;
        }
    }, 200, 120), ProgressIndicators(new ArrayList<CssMetaData<? extends Styleable, ?>>(ProgressIndicator.getClassCssMetaData()) {
        {
            addAll(ProgressIndicatorSkin.getClassCssMetaData());
        }
    }, new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            ProgressIndicator temp = new ProgressIndicator(0.85);
            temp.setPrefSize(100, 100);
            temp.setFocusTraversable(false);
            return temp;
        }
    }), ProgressBars(ProgressBar.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            ProgressBar temp = new ProgressBar(0.25);
            temp.setFocusTraversable(false);
            return temp;
        }
    }, 200, 100), RadioButtons(RadioButton.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Control createControl() {
            RadioButton temp = new RadioButton("Radio the first line" + "\nthe sec long line" + "\nthe third line");
            temp.setFocusTraversable(false);
            temp.setGraphic(new Rectangle(20, 20, Color.web("lightblue")));
            return temp;
        }
    }, 200, 100), TextFields(new ArrayList<CssMetaData<? extends Styleable, ?>>(TextField.getClassCssMetaData()) {
        {
            addAll(TextInputControlSkin.getClassCssMetaData());
        }
    }, new ControlsCSSApp.ControlFactory() {
        public Control createControl() {
            TextField temp = new TextField("Text box the first line" + "\nthe sec long line" + "\nthe third line");
            temp.setFocusTraversable(false);
            return temp;
        }
    }), Sliders(Slider.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Control createControl() {
            Slider temp = new Slider();
            temp.setMin(0);
            temp.setMax(100);
            temp.setValue(20);
            temp.setFocusTraversable(false);
            return temp;
        }
    }, 200), Separators(Separator.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Control createControl() {
            Separator sep = new Separator();
            sep.setHalignment(HPos.CENTER);
            sep.setPrefWidth(80);
            return sep;
        }
    }, 150), ScrollBars(ScrollBar.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Control createControl() {
            ScrollBar temp = new ScrollBar();
            temp.setValue(45);
            temp.setMin(0);
            temp.setMax(100);
            temp.setFocusTraversable(false);
            return temp;
        }
    }, 150), ScrollPanes(ScrollPane.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            ScrollPane pane = new ScrollPane();
            HBox hbox = new HBox(30);
            VBox vbox1 = new VBox(10);
            vbox1.getChildren().addAll(new Label("one"), new Button("two"), new CheckBox("three"), new RadioButton("four"), new Label("five"));
            VBox vbox2 = new VBox(10);
            vbox2.getChildren().addAll(new Label("one"), new Button("two"), new CheckBox("three"), new RadioButton("four"), new Label("five"));
            hbox.getChildren().addAll(vbox1, vbox2);
            pane.setContent(hbox);
            pane.setFocusTraversable(false);
            return pane;
        }
    }, 150), UnPressedToggleButtons(ToggleButton.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Control createControl() {
            ToggleButton temp = new ToggleButton("Button the first line" + "\nthe sec long line" + "\nthe third line");
            temp.setGraphic(new Rectangle(10, 10, Color.RED));
            temp.setSelected(false);
            temp.setFocusTraversable(false);
            return temp;
        }
    }, 200, 120), Toolbars(ToolBar.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            ToolBar toolbar = new ToolBar();
            SplitMenuButton temp = new SplitMenuButton();
            temp.setText("three");
            toolbar.getItems().addAll(new Button("One"), new Button("Two"), new Separator(), temp);
            toolbar.setFocusTraversable(false);
            return toolbar;
        }
    }, 250, 150), Menubars(MenuBar.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            MenuBar menubar = new MenuBar();
            Menu firstMenu = new Menu("File");
            MenuItem firstMenuFirstItem = new MenuItem("Save");
            MenuItem firstMenuSecondItem = new MenuItem("Open");
            MenuItem firstMenuThirdItem = new MenuItem("Exit");
            firstMenu.getItems().addAll(firstMenuFirstItem, firstMenuSecondItem, firstMenuThirdItem);
            Menu secondMenu = new Menu("Edit");
            MenuItem secondMenuFirstItem = new MenuItem("Undo");
            MenuItem secondMenuSecondItem = new MenuItem("Cut");
            MenuItem secondMenuThirdItem = new MenuItem("Copy");
            secondMenu.getItems().addAll(secondMenuFirstItem, secondMenuSecondItem, secondMenuThirdItem);
            Menu thirdMenu = new Menu("Help");
            MenuItem thirdMenuFirstItem = new MenuItem("Help Contents");
            MenuItem thirdMenuSecondItem = new MenuItem("About");
            thirdMenu.getItems().addAll(thirdMenuFirstItem, thirdMenuSecondItem);
            menubar.getMenus().addAll(firstMenu, secondMenu, thirdMenu);
            menubar.setFocusTraversable(false);
            return menubar;
        }
    }, 250, 150), SplitMenuButtons(SplitMenuButton.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            SplitMenuButton smb = new SplitMenuButton(new MenuItem("Split box the first line" + "\nthe sec long line" + "\nthe third line", new Rectangle(10, 10, Color.BLUE)));
            smb.setText("Split box the first line" + "\nthe sec long line" + "\nthe third line");
            smb.setGraphic(new Rectangle(10, 10, Color.RED));
            smb.setFocusTraversable(false);
            return smb;
        }
    }, 200, 120), TabPanes(TabPane.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            TabPane pane = new TabPane();
            Tab tab = new Tab();
            tab.setText("tab 1");
            HBox content1 = new HBox(10);
            content1.getChildren().addAll(new Button("Button"), new Label("Label"), new Rectangle(40, 40, Color.TOMATO));
            tab.setContent(content1);
            Tab tab2 = new Tab();
            tab2.setText("tab 2");
            tab2.setContent(new Circle(40, Color.RED));
            pane.getTabs().addAll(tab, tab2);
            pane.setFocusTraversable(false);
            return pane;
        }
    }, 200), TitledPanes(TitledPane.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            TitledPane tpane = new TitledPane();
            tpane.setText("Title");
            VBox content = new VBox(5);
            content.getChildren().addAll(new Label("Label"), new Button("Button"), new CheckBox("Check box"));
            tpane.setContent(content);
            tpane.setAnimated(false);
            tpane.setFocusTraversable(false);
            return tpane;
        }
    }, 150), TableViews(new ArrayList<CssMetaData<? extends Styleable, ?>>(TableView.getClassCssMetaData()) {
        {
            addAll(CellSkinBase.getClassCssMetaData());
            addAll(TableColumnHeader.getClassCssMetaData());
        }
    }, new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            ObservableList<ControlsCSSApp.Person> items = FXCollections.observableArrayList();
            for (int i = 0; i < 10; i++) {
                items.add(new ControlsCSSApp.Person("name " + i, "surname " + i));
            }
            TableColumn<ControlsCSSApp.Person, Node> column1 = new TableColumn<ControlsCSSApp.Person, Node>("First Name");
            column1.setCellValueFactory(new Callback<CellDataFeatures<ControlsCSSApp.Person, Node>, ObservableValue<Node>>() {
                @Override
                public ObservableValue<Node> call(final CellDataFeatures<ControlsCSSApp.Person, Node> p) {
                    SimpleObjectProperty<Node> text = new SimpleObjectProperty<Node>();
                    text.setValue(new Label(p.getValue().getFirstName()));
                    return text;
                }
            });
            TableColumn<ControlsCSSApp.Person, Node> column2 = new TableColumn<ControlsCSSApp.Person, Node>("Last Name");
            column2.setCellValueFactory(new Callback<CellDataFeatures<ControlsCSSApp.Person, Node>, ObservableValue<Node>>() {
                @Override
                public ObservableValue<Node> call(final CellDataFeatures<ControlsCSSApp.Person, Node> p) {
                    SimpleObjectProperty<Node> text = new SimpleObjectProperty<Node>();
                    text.setValue(new Label(p.getValue().getLastName()));
                    return text;
                }
            });
            TableView<ControlsCSSApp.Person> table = new TableView<ControlsCSSApp.Person>(items);
            table.getColumns().setAll(column1, column2);
            table.setPrefHeight(200);
            table.setFocusTraversable(false);
            return table;
        }
    }, 200, 250), TreeViews(new ArrayList<CssMetaData<? extends Styleable, ?>>(TableView.getClassCssMetaData()) {
        {
            addAll(TreeCellSkin.getClassCssMetaData());
        }
    }, new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            TreeItem<String> root = new TreeItem<String>("ROOT", new Rectangle(20, 20, Color.CHOCOLATE));
            root.setExpanded(true);
            TreeItem<String> firstBrunch = new TreeItem<String>("brunch 1");
            firstBrunch.setExpanded(true);
            firstBrunch.getChildren().addAll(new TreeItem<String>("first item"), new TreeItem<String>("second item", new Rectangle(20, 20, Color.DARKGREY)));
            root.getChildren().addAll(firstBrunch);
            TreeItem<String> secondBrunch = new TreeItem<String>("brunch 2");
            secondBrunch.getChildren().addAll(new TreeItem<String>("first item"), new TreeItem<String>("second item", new Rectangle(20, 20, Color.DARKGREY)));
            root.getChildren().addAll(secondBrunch);
            TreeView tree = new TreeView(root);
            tree.setFocusTraversable(false);
            tree.setMinSize(147, 127);
            tree.setPrefSize(160, 140);
            return tree;
        }
    }, 200, 300), SplitPanes(SplitPane.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            StackPane sp1 = new StackPane(new Rectangle(40, 40, Color.WHITESMOKE));
            StackPane sp2 = new StackPane(new Rectangle(40, 40, Color.BLUE));
            StackPane sp3 = new StackPane(new Rectangle(40, 40, Color.RED));
            SplitPane pane = new SplitPane(sp1, sp2, sp3);
            pane.setPrefSize(150, 150);
            pane.setDividerPositions(0.33, 0.67);
            pane.setFocusTraversable(false);
            return pane;
        }
    }, 250), Paginations(new ArrayList<CssMetaData<? extends Styleable, ?>>(Pagination.getClassCssMetaData()) {
        {
            addAll(PaginationSkin.getClassCssMetaData());
        }
    }, new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            Pagination node = new Pagination(47);
            node.setMaxPageIndicatorCount(5);
            node.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
            return node;
        }
    }, 350, 150), XYChars(new ArrayList<CssMetaData<? extends Styleable, ?>>(LineChart.getClassCssMetaData()) {
        {
            addAll(NumberAxis.getClassCssMetaData());
        }
    }, new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            XYChart node = new LineChart(new NumberAxis(0, 4, 1), new NumberAxis(0, 4, 1));
            node.setPrefSize(200, 200);
            XYChart.Series series = new XYChart.Series();
            series.getData().add(new XYChart.Data(0d, 0d));
            series.getData().add(new XYChart.Data(4d, 4d));
            XYChart.Series series2 = new XYChart.Series();
            series2.getData().add(new XYChart.Data(0d, 0d));
            series2.getData().add(new XYChart.Data(1d, 3d));
            XYChart.Series series3 = new XYChart.Series();
            series3.getData().add(new XYChart.Data(0d, 0d));
            series3.getData().add(new XYChart.Data(2d, 4d));
            node.getData().addAll(series, series2, series3);
            return node;
        }
    }, 300, 300), TilePanes(TilePane.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            TilePane tile = new TilePane();
            tile.setPrefSize(150, 150);
            tile.setTileAlignment(Pos.CENTER_LEFT);
            for (int i = 0; i < 15; i++) {
                tile.getChildren().add(new Rectangle(20, 20, (i % 2 == 0) ? Color.RED : Color.BLUE));
            }
            return tile;
        }
    }, 200, 200), VBoxs(VBox.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        @Override
        public Node createControl() {
            VBox node = new VBox(10);
            node.setMinSize(150, 150);
            for (int i = 0; i < 4; i++) {
                node.getChildren().add(new Rectangle(20, 20, (i % 2 == 0) ? Color.RED : Color.BLUE));
            }
            return node;
        }
    }, 200, 200), BarCharts(new ArrayList<CssMetaData<? extends Styleable, ?>>(BarChart.getClassCssMetaData()) {
        {
            addAll(CategoryAxis.getClassCssMetaData());
        }
    }, new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            CategoryAxis cAxis = new CategoryAxis();
            NumberAxis nAxis = new NumberAxis(0, 5, 1);
            BarChart node = new BarChart(cAxis, nAxis);
            XYChart.Series series = new XYChart.Series();
            series.getData().add(new XYChart.Data("1", 2));
            XYChart.Series series1 = new XYChart.Series();
            series1.getData().add(new XYChart.Data("2", 3));
            XYChart.Series series2 = new XYChart.Series();
            series2.getData().add(new XYChart.Data("2", 5));
            node.getData().addAll(series, series1, series2);
            node.setMaxSize(180, 180);
            return node;
        }
    }, 200, 200), ColorPickers(ColorPickerSkin.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            ColorPicker node = new ColorPicker();
            node.setPrefSize(100, 100);
            node.setStyle("-fx-color-label-visible:false;");
            return node;
        }
    }, 200, 200), FlowPanes(FlowPane.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            FlowPane node = new FlowPane();
            for (int i = 0; i < 6; i++) {
                node.getChildren().add(new Rectangle(20, 20, (i % 2 == 0) ? Color.RED : Color.BLUE));
            }
            node.setColumnHalignment(HPos.CENTER);
            node.setPrefSize(100, 100);
            return node;
        }
    }, 200, 200), PieCharts(PieChart.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("", 13), new PieChart.Data("", 25), new PieChart.Data("", 10), new PieChart.Data("", 22), new PieChart.Data("", 30));
            PieChart chart = new PieChart(pieChartData);
            chart.setMaxSize(150, 150);
            return chart;
        }
    }, 300, 300), WebViews(getWebViewClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            Node node = null;
            try {
                Class<?> webViewCl = Class.forName("javafx.scene.web.WebView");
                Constructor<?> constructor = webViewCl.getConstructor();
                constructor.setAccessible(true);
                Object args = null;
                node = (Node) constructor.newInstance();
                Method setViewport = webViewCl.getDeclaredMethod("setPrefSize", double.class, double.class);
                setViewport.invoke(node, 150, 150);
                Method getEngine = webViewCl.getDeclaredMethod("getEngine");
                Object engine = getEngine.invoke(node);
                Method load = engine.getClass().getDeclaredMethod("load", String.class);
                load.invoke(engine, ControlsCSSApp.class.getResource("/test/css/resources/index.html").toExternalForm());
            } catch (Exception ignored) {
            }
            return node;
        }
    }, 300, 300), HBoxs(HBox.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            HBox node = new HBox(10);
            node.setMinSize(150, 150);
            for (int i = 0; i < 4; i++) {
                node.getChildren().add(new Rectangle(20, 20, (i % 2 == 0) ? Color.RED : Color.BLUE));
            }
            return node;
        }
    }, 300, 300), ImageViews(ImageView.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            ImageView view = new ImageView(new Image(ControlsCSSApp.class.getResource("/test/css/resources/placeholder.png").toExternalForm()));
            return view;
        }
    }, 300, 300), DatePickers(DatePicker.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            DatePicker picker = new DatePicker();
            picker.setValue(LocalDate.of(2042, Month.MAY, 27));
            picker.setConverter(new StringConverter<LocalDate>() {

                DateTimeFormatter df = new DateTimeFormatterBuilder().parseLenient()
                        .appendPattern("M/dd/YYYY")
                        .toFormatter(Locale.ENGLISH)
                        .withLocale(Locale.ENGLISH);

                @Override
                public String toString(LocalDate localDate) {
                    return df.format(localDate);
                }

                @Override
                public LocalDate fromString(String s) {
                    return LocalDate.parse(s, df);
                }
            });
            //picker.setMinSize(100, 30);
            return picker;
        }
    }, 300, 300),
    Texts(Text.getClassCssMetaData(), new ControlsCSSApp.ControlFactory() {
        public Node createControl() {
            Text node = new Text("1234567890");
            return node;
        }
    }, 150, 150);
    public static final int DEFAULT_SLOTSIZE = 150;
    public static final int INNER_PANE_SHIFT = 20;
    public List<CssMetaData<? extends Styleable, ?>> keys;
    public ControlsCSSApp.ControlFactory factory;
    public int slotHeight;
    public int slotWidth;

    public static final String packageName = "functional";

    ControlPage(List<CssMetaData<? extends Styleable, ?>> keys, ControlsCSSApp.ControlFactory factory) {
        this(keys, factory, DEFAULT_SLOTSIZE);
    }

    ControlPage(List<CssMetaData<? extends Styleable, ?>> keys, ControlsCSSApp.ControlFactory factory, int slotSize) {
        this(keys, factory, slotSize, slotSize);
    }

    /**
     * Returns filtered enumeration values, this is required to support embedded
     * execution, cause currently JavaFX Embedded does not contain Web component
     * @return filtered enumeration values
     */
    public static ControlPage[] filteredValues(){
        Set<ControlPage> controls = EnumSet.allOf(ControlPage.class);
        if(!Platform.isSupported(ConditionalFeature.WEB))
            controls.remove(ControlPage.WebViews);
        return controls.toArray(new ControlPage[]{});
    }

    private ControlPage(List<CssMetaData<? extends Styleable, ?>> keys, ControlsCSSApp.ControlFactory factory, int slotWidth, int slotHeight) {
        this.keys = keys;
        this.factory = factory;
        this.slotHeight = slotHeight;
        this.slotWidth = slotWidth;
    }

    private static List<CssMetaData<? extends Styleable, ?>> getWebViewClassCssMetaData() {
        List<CssMetaData<? extends Styleable, ?>> arrayList = null;
        try {
            Class<?> webViewCl = Class.forName("javafx.scene.web.WebView");
            Method staticMethod = webViewCl.getDeclaredMethod("getClassCssMetaData");
            arrayList = (List<CssMetaData<? extends Styleable, ?>>) staticMethod.invoke(null);
        } catch (Exception ex) {
            arrayList = new ArrayList<CssMetaData<? extends Styleable,?>>();
        }
        return arrayList;
    }
}
