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
/**
 * @author Andrey Nazarov
 */
package javafx.factory;

import com.sun.javafx.collections.ObservableListWrapper;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

public enum ControlsFactory implements NodeFactory {

    Buttons(new ControlFactory() {
        public Control createControl() {
            Button b = new Button("Button the first line" + "\nthe sec long line" + "\nthe third line");
            b.setFocusTraversable(false);
            b.setGraphic(new Rectangle(10, 10, Color.RED));
            return b;
        }

        public Class getControlClass() {
            return Button.class;
        }
    }),
    ChoiceBoxes(new ControlFactory() {
        public Control createControl() {
            return new ChoiceBox() {
                {
                    getItems().addAll(Arrays.asList("one", "two", "three"));
                    setFocusTraversable(false);
                    getSelectionModel().select(0);
                }
            };
        }

        public Class getControlClass() {
            return ChoiceBox.class;
        }
    }),
    ComboBoxes(new ControlFactory() {
        public Control createControl() {
            ComboBox box = new ComboBox();
            box.setEditable(false);
            box.getItems().addAll("one", "two", "three");
            return box;
        }

        public Class getControlClass() {
            return ComboBox.class;
        }
    }),
    EditableComboBoxes(new ControlFactory() {
        public Control createControl() {
            ComboBox box = new ComboBox();
            box.setEditable(true);
            box.getItems().addAll("one", "two", "three");
            return box;
        }

        public Class getControlClass() {
            return ComboBox.class;
        }
    }),
    Paginations(new ControlFactory() {
        public Control createControl() {
            Pagination ps = new Pagination();
            ps.maxPageIndicatorCountProperty().set(3);
            return ps;
        }

        public Class getControlClass() {
            return Pagination.class;
        }
    }),
    ColorPickers(new ControlFactory() {
        public Control createControl() {
            ColorPicker cp = new ColorPicker();
            return cp;
        }

        public Class getControlClass() {
            return ColorPicker.class;
        }
    }),
    CheckBoxes(new ControlFactory() {
        public Control createControl() {
            CheckBox cb = new CheckBox("Check box the first line" + "\nthe sec long line" + "\nthe third line");
            cb.setFocusTraversable(false);
            cb.setGraphic(new Rectangle(20, 20, Color.web("lightblue")));
            return cb;
        }

        public Class getControlClass() {
            return CheckBox.class;
        }
    }),
    RadioButtons(new ControlFactory() {
        public Control createControl() {
            RadioButton rb = new RadioButton("Radio the first line" + "\nthe sec long line" + "\nthe third line");
            rb.setFocusTraversable(false);
            rb.setGraphic(new Rectangle(20, 20, Color.web("lightblue")));
            rb.setTextAlignment(TextAlignment.RIGHT);
            return rb;
        }

        public Class getControlClass() {
            return RadioButton.class;
        }
    }),
    TextFields(new ControlFactory() {
        public Control createControl() {
            TextField tf = new TextField("Text box the first line" + "\nthe sec long line" + "\nthe third line");
            tf.setFocusTraversable(false);
            return tf;
        }

        public Class getControlClass() {
            return TextField.class;
        }
    }),
    TextAreas(new ControlFactory() {
        public Control createControl() {
            TextArea ta = new TextArea("Text area the first line" + "\nthe sec long line" + "\nthe third line");
            ta.setFocusTraversable(false);
            ta.setMaxSize(100, 50);
            return ta;
        }

        public Class getControlClass() {
            return TextArea.class;
        }
    }),
    PasswordFields(new ControlFactory() {
        public Control createControl() {
            PasswordField pf = new PasswordField();
            pf.setPromptText("Password box the first line" + "\nthe sec long line" + "\nthe third line");
            pf.setFocusTraversable(false);
            return pf;
        }

        public Class getControlClass() {
            return PasswordField.class;
        }
    }),
    Sliders(new ControlFactory() {
        public Control createControl() {
            Slider s = new Slider();
            s.setMin(0);
            s.setMax(100);
            s.setValue(20);
            s.setFocusTraversable(false);
            return s;
        }

        public Class getControlClass() {
            return Slider.class;
        }
    }),
    Labels(new ControlFactory() {
        public Control createControl() {
            Label l = new Label("Label the first line" + "\nthe sec longlong line" + "\nthe third line");
            l.setFocusTraversable(false);
            l.setGraphic(new Rectangle(20, 20, Color.web("lightblue")));
            return l;
        }

        public Class getControlClass() {
            return Label.class;
        }
    }),
    Hyperlinks(new ControlFactory() {
        public Control createControl() {
            Hyperlink hl = new Hyperlink("Hyperlink the first line" + "\nthe sec long line" + "\nthe third line");
            hl.setFocusTraversable(false);
            hl.setGraphic(new Circle(10, Color.BLUE));
            return hl;
        }

        public Class getControlClass() {
            return Hyperlink.class;
        }
    }),
    ImageView(new ControlFactory() {
        public Node createControl() {
            return new ImageView(new Image(ControlsFactory.class.getResourceAsStream("big_tiger_e0.gif")));
        }

        public Class getControlClass() {
            return ImageView.class;
        }
    }),
    MediaView(new ControlFactory() {
        public Node createControl() {
//    Lines are disabled due to impossibleness of stream using.
//    String path = System.getProperty("user.dir");
//    while (path.indexOf("\\") != -1) {
//        path = path.replace("\\", "/");
//    }
//    MediaPlayer mediaPlayer = new MediaPlayer(new Media("file:///" + path + "/content/somefile.flv"));
//    mediaPlayer.play();
            Node node = null;
            try {
                Class<?> mediaViewCl = Class.forName("javafx.scene.media.MediaView");
                Class<?> mediaPlayerCl = Class.forName("javafx.scene.media.MediaPlayer");
                Constructor<?> constructor = mediaViewCl.getConstructor(mediaPlayerCl);
                constructor.setAccessible(true);
                Object args = null;
                node = (Node) constructor.newInstance(args);
                Method setViewport = mediaViewCl.getDeclaredMethod("setViewport", Rectangle2D.class);
                setViewport.invoke(node, new Rectangle2D(0, 0, 100, 100));
                node.setStyle("-fx-stroke: black;");
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
            return node;
        }

        public Class getControlClass() {
            Class controlClass = null;
            try {
                controlClass = Class.forName("javafx.scene.media.MediaView");
            } catch (Exception ignored) {
            }
            return controlClass;
        }
    }),
    Separators(new ControlFactory() {
        public Control createControl() {
            Separator sep = new Separator();
            sep.setHalignment(HPos.CENTER);
            sep.setPrefWidth(80);
            return sep;
        }

        public Class getControlClass() {
            return Separator.class;
        }
    }),
    ScrollBars(new ControlFactory() {
        public Control createControl() {
            ScrollBar sb = new ScrollBar();
            sb.setValue(45);
            sb.setMin(0);
            sb.setMax(100);
            sb.setFocusTraversable(false);
            return sb;
        }

        public Class getControlClass() {
            return ScrollBar.class;
        }
    }),
    ScrollPanes(new ControlFactory() {
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

        public Class getControlClass() {
            return ScrollPane.class;
        }
    }),
    ProgressIndicators(new ControlFactory() {
        public Node createControl() {
            ProgressIndicator pi = new ProgressIndicator(0.85);
            pi.setFocusTraversable(false);
            return pi;
        }

        public Class getControlClass() {
            return ProgressIndicator.class;
        }
    }),
    ProgressBars(new ControlFactory() {
        public Node createControl() {
            ProgressBar pb = new ProgressBar(0.25);
            pb.setFocusTraversable(false);
            return pb;
        }

        public Class getControlClass() {
            return ProgressBar.class;
        }
    }),
    ListViews(new ControlFactory() {
        public Node createControl() {
            ListView list = new ListView();

            // The following requires --add-exports javafx.base/com.sun.javafx.collections=ALL-UNNAMED
            ObservableListWrapper<String> items = new ObservableListWrapper<String>(Arrays.asList("One", "Two", "Three", "Four", "Five", "Six", "long line long line long line long line"));
            list.setItems(items);
            list.setPrefWidth(100);
            list.setPrefHeight(100);
            list.setFocusTraversable(false);
            return list;
        }

        public Class getControlClass() {
            return ListView.class;
        }
    }),
    PressedToggleButtons(new ControlFactory() {
        public Control createControl() {
            ToggleButton tb = new ToggleButton("Button the first line" + "\nthe sec long line" + "\nthe third line");
            tb.setFocusTraversable(false);
            tb.setGraphic(new Rectangle(10, 10, Color.RED));
            tb.setSelected(true);
            return tb;
        }

        public Class getControlClass() {
            return ToggleButton.class;
        }
    }),
    UnPressedToggleButtons(new ControlFactory() {
        public Control createControl() {
            ToggleButton tb = new ToggleButton("Button the first line" + "\nthe sec long line" + "\nthe third line");
            tb.setFocusTraversable(false);
            tb.setGraphic(new Rectangle(10, 10, Color.RED));
            tb.setSelected(false);
            return tb;
        }

        public Class getControlClass() {
            return ToggleButton.class;
        }
    }),
    Toolbars(new ControlFactory() {
        public Node createControl() {
            ToolBar toolbar = new ToolBar();
            SplitMenuButton smb = new SplitMenuButton();
            smb.setText("three");
            toolbar.getItems().addAll(new Button("One"), new Button("Two"), new Separator(), smb);
            toolbar.setFocusTraversable(false);
            return toolbar;
        }

        public Class getControlClass() {
            return ToolBar.class;
        }
    }),
    Menubars(new ControlFactory() {
        public Node createControl() {
            MenuBar menubar = new MenuBar();
            menubar.getMenus().addAll(new Menu("File"));
            menubar.setFocusTraversable(false);
            return menubar;
        }

        public Class getControlClass() {
            return MenuBar.class;
        }
    }),
    SplitMenuButtons(new ControlFactory() {
        public Node createControl() {
            SplitMenuButton smb = new SplitMenuButton(new MenuItem("Split box the first line" + "\nthe sec long line" + "\nthe third line",
                    new Rectangle(10, 10, Color.BLUE)));
            smb.setText("Split box the first line" + "\nthe sec long line" + "\nthe third line");
            smb.setGraphic(new Rectangle(10, 10, Color.RED));
            smb.setMinWidth(100);
            smb.setFocusTraversable(false);
            return smb;
        }

        public Class getControlClass() {
            return SplitMenuButton.class;
        }
    }),
    TabPanes(new ControlFactory() {
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

        public Class getControlClass() {
            return TabPane.class;
        }
    }),
    TitledPanes(new ControlFactory() {
        public Node createControl() {
            TitledPane tpane = new TitledPane();
            tpane.setGraphic(new Label("Title"));
            VBox content = new VBox(5);
            content.getChildren().addAll(new Label("Label"), new Button("Button"), new CheckBox("Check box"));
            tpane.setContent(content);
            tpane.setAnimated(false);
            tpane.setMinWidth(40);
            return tpane;
        }

        public Class getControlClass() {
            return TabPane.class;
        }
    }),
    TableViews(new ControlFactory() {
        public Node createControl() {
            ObservableList<Person> items = FXCollections.observableArrayList();
            for (int i = 0; i < 10; i++) {
                items.add(new Person("name " + i, "surname " + i));
            }
            TableColumn<Person, Node> column1 = new TableColumn<Person, Node>("First Name");
            column1.setCellValueFactory(new Callback<CellDataFeatures<Person, Node>, ObservableValue<Node>>() {
                @Override
                public ObservableValue<Node> call(final CellDataFeatures<Person, Node> p) {
                    SimpleObjectProperty<Node> text = new SimpleObjectProperty<Node>();
                    text.setValue(new Label(p.getValue().getFirstName()));
                    return text;
                }
            });
            TableColumn<Person, Node> column2 = new TableColumn<Person, Node>("Last Name");
            column2.setCellValueFactory(new Callback<CellDataFeatures<Person, Node>, ObservableValue<Node>>() {
                @Override
                public ObservableValue<Node> call(final CellDataFeatures<Person, Node> p) {
                    SimpleObjectProperty<Node> text = new SimpleObjectProperty<Node>();
                    text.setValue(new Label(p.getValue().getLastName()));
                    return text;
                }
            });
            TableView table = new TableView(items);
            table.getColumns().setAll(column1, column2);
            table.setPrefHeight(100);
            table.setPrefWidth(100);
            table.setFocusTraversable(false);
            return table;
        }

        public Class getControlClass() {
            return TableView.class;
        }
    }),
    TreeTableViews(new ControlFactory() {
        public Node createControl() {
            ObservableList<TreeItem> items = FXCollections.observableArrayList();
            for (int i = 0; i < 10; i++) {
                items.add(new TreeItem(new Person("name " + i, "surname " + i)));
            }
            TreeTableColumn<Person, Node> column1 = new TreeTableColumn<Person, Node>("First Name");
            column1.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person, Node>, ObservableValue<Node>>() {
                @Override
                public ObservableValue<Node> call(final TreeTableColumn.CellDataFeatures<Person, Node> p) {
                    SimpleObjectProperty<Node> text = new SimpleObjectProperty<Node>();
                    text.setValue(new Label(p.getValue().getValue().getFirstName()));
                    return text;
                }
            });
            TreeTableColumn<Person, Node> column2 = new TreeTableColumn<Person, Node>("Last Name");
            column2.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person, Node>, ObservableValue<Node>>() {
                @Override
                public ObservableValue<Node> call(final TreeTableColumn.CellDataFeatures<Person, Node> p) {
                    SimpleObjectProperty<Node> text = new SimpleObjectProperty<Node>();
                    text.setValue(new Label(p.getValue().getValue().getLastName()));
                    return text;
                }
            });
            TreeTableView treeTable = new TreeTableView();
            treeTable.setRoot(new TreeItem(new Person("root", "root")));
            treeTable.getRoot().getChildren().addAll(items);
            treeTable.getRoot().setExpanded(true);
            treeTable.getColumns().setAll(column1, column2);
            treeTable.setPrefHeight(100);
            treeTable.setPrefWidth(100);
            treeTable.setFocusTraversable(false);
            return treeTable;
        }

        public Class getControlClass() {
            return TreeTableView.class;
        }
    }),
    TreeViews(new ControlFactory() {
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
            tree.setPrefSize(100, 100);
            return tree;
        }

        public Class getControlClass() {
            return TreeView.class;
        }
    }),
    Accordions(new ControlFactory() {
        public Node createControl() {
            TitledPane pane1 = new TitledPane();
            pane1.setGraphic(new Label("title 1\nLong text long text"));
            pane1.setContent(new Rectangle(100, 40, Color.SKYBLUE));
            TitledPane pane2 = new TitledPane();
            pane2.setGraphic(new Label("title 2\nLong text long text"));
            pane2.setContent(new Rectangle(100, 40, Color.BLUEVIOLET));
            Accordion acc = new Accordion();
            acc.getPanes().addAll(pane1, pane2);
            acc.setExpandedPane(pane2);
            pane2.setAnimated(false);
            acc.setFocusTraversable(false);
            acc.setMinWidth(100);
            return acc;
        }

        public Class getControlClass() {
            return Accordion.class;
        }
    }),
    SplitPanes(new ControlFactory() {
        public Node createControl() {
            StackPane sp1 = new StackPane(new Rectangle(40, 40, Color.WHITESMOKE));
            StackPane sp2 = new StackPane(new Rectangle(40, 40, Color.BLUE));
            StackPane sp3 = new StackPane(new Rectangle(40, 40, Color.RED));
            SplitPane pane = new SplitPane(sp1, sp2, sp3);
            pane.setPrefSize(150, 150);
            pane.setMinWidth(100);
            pane.setDividerPositions(0.33, 0.67);
            pane.setFocusTraversable(false);
            return pane;
        }

        public Class getControlClass() {
            return SplitPane.class;
        }
    }),
    DatePickers(new ControlFactory() {
        public Node createControl() {
            final DatePicker datePicker = new DatePicker();
            datePicker.setFocusTraversable(false);
            datePicker.setValue(LocalDate.of(2042, Month.MAY, 9));
            return datePicker;
        }

        public Class getControlClass() {
            return DatePicker.class;
        }
    }),;

    /**
     * Returns true/false depending on class presence
     *
     * @return true - if class is presented, otherwise - false
     */
    static boolean checkClassOnPresence(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    /**
     * Returns filtered enumeration values, this is required to support embedded
     * execution, cause currently JavaFX Embedded does not contain Media
     * component.
     * <p>
     * Currently Class.forName is being used, however as soon as
     * ConditionalFeature enumeration will be updated to support MEDIA,
     * Class.forName will be substituted with Platform.isSupported method
     *
     * @return filtered enumeration values
     */
    public static ControlsFactory[] filteredValues() {
        Set<ControlsFactory> controlsSet = EnumSet.allOf(ControlsFactory.class);
        if (!checkClassOnPresence("javafx.scene.media.MediaView")) {
            controlsSet.remove(ControlsFactory.MediaView);
        }
        ControlsFactory[] controlsArray = new ControlsFactory[controlsSet.toArray().length];
        Arrays.asList(controlsSet.toArray()).toArray(controlsArray);
        return controlsArray;
    }
    private ControlFactory factory;

    ControlsFactory(ControlFactory factory) {
        this.factory = factory;
    }

    public Node createNode() {
        Node node = null;
        try {
            node = factory.createControl();
        } catch (Throwable ex) {
            ex.printStackTrace();
            node = new Label("Error on control instantiation : " + ex.getMessage());
        }
        return node;
    }

    public Class getControlClass() {
        return factory.getControlClass();
    }

    private interface ControlFactory {

        /**
         * @return current control instance
         */
        public abstract Node createControl();

        public abstract Class getControlClass();
    }

    public static class Person {

        Person(String firstName, String lastName) {
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
        }
        public StringProperty firstName;

        public void setFirstName(String value) {
            firstName.set(value);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public StringProperty firstNameProperty() {
            if (firstName == null) {
                firstName = new SimpleStringProperty();
            }
            return firstName;
        }
        public StringProperty lastName;

        public void setLastName(String value) {
            lastName.set(value);
        }

        public String getLastName() {
            return lastName.get();
        }

        public StringProperty lastNameProperty() {
            if (lastName == null) {
                lastName = new SimpleStringProperty();
            }
            return lastName;
        }
    }
}
