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
 */
package test.scenegraph.app;

import java.util.Arrays;
import java.util.List;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.web.WebEvent;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import test.javaclient.shared.InteroperabilityApp;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class ControlEventsApp extends InteroperabilityApp {
    

    public static void main(String[] args) {
        test.javaclient.shared.Utils.launch(ControlEventsApp.class, args);
    }

    @Override
    protected Scene getScene() {
        TabPane tabPane = new TabPane();
        for (Controls c : Controls.values()) {
            ControlEventsTab tab = new ControlEventsTab(c.name(), c.getControl(), c.getProcessedEvents());
            tab.setId(c.toString());
            tabPane.getTabs().add(tab);
        }
        return new Scene(tabPane, 800, 600);
    }

    public abstract static class AbstractControlFactory<T extends Control> {

        public abstract T create();
    }
    
    public static final String CONTROL_ID = "control";
    public static final String DRAG_TARGET_ID = "drag_target";
    public static final String DRAG_FIELD_ID = "drag_field";
    public static final String RESET_FOCUS_AREA = "reset_focus";
    public static final double INSETS = 50;

    public static enum EventTypes {

        ACTION(ActionEvent.ACTION),
        CONTEXT_MENU_REQUESTED(ContextMenuEvent.CONTEXT_MENU_REQUESTED),
        DRAG_DONE(DragEvent.DRAG_DONE),
        DRAG_DROPPED(DragEvent.DRAG_DROPPED),
        DRAG_ENTERED(DragEvent.DRAG_ENTERED),
        DRAG_ENTERED_TARGET(DragEvent.DRAG_ENTERED_TARGET),
        DRAG_EXITED(DragEvent.DRAG_EXITED),
        DRAG_EXITED_TARGET(DragEvent.DRAG_EXITED_TARGET),
        DRAG_OVER(DragEvent.DRAG_OVER),

//         temporary removed while test method
//         for automated test suite is not provided
//         https://javafx-jira.kenai.com/browse/RT-31875
       // INPUT_METHOD_TEXT_CHANGED(InputMethodEvent.INPUT_METHOD_TEXT_CHANGED),
        
        KEY_PRESSED(KeyEvent.KEY_PRESSED),
        KEY_RELEASED(KeyEvent.KEY_RELEASED),
        KEY_TYPED(KeyEvent.KEY_TYPED),
        DRAG_DETECTED(MouseEvent.DRAG_DETECTED),
        MOUSE_CLICKED(MouseEvent.MOUSE_CLICKED),
        MOUSE_DRAGGED(MouseEvent.MOUSE_DRAGGED),
        MOUSE_ENTERED(MouseEvent.MOUSE_ENTERED),
//        MOUSE_ENTERED_TARGET(MouseEvent.MOUSE_ENTERED_TARGET),
        MOUSE_EXITED(MouseEvent.MOUSE_EXITED),
//        MOUSE_EXITED_TARGET(MouseEvent.MOUSE_EXITED_TARGET),
        MOUSE_MOVED(MouseEvent.MOUSE_MOVED),
        MOUSE_PRESSED(MouseEvent.MOUSE_PRESSED),
        MOUSE_RELEASED(MouseEvent.MOUSE_RELEASED),
        MOUSE_DRAG_ENTERED(MouseDragEvent.MOUSE_DRAG_ENTERED),
//        MOUSE_DRAG_ENTERED_TARGET(MouseDragEvent.MOUSE_DRAG_ENTERED_TARGET),
        MOUSE_DRAG_EXITED(MouseDragEvent.MOUSE_DRAG_EXITED),
//        MOUSE_DRAG_EXITED_TARGET(MouseDragEvent.MOUSE_DRAG_EXITED_TARGET),
        MOUSE_DRAG_OVER(MouseDragEvent.MOUSE_DRAG_OVER),
        MOUSE_DRAG_RELEASED(MouseDragEvent.MOUSE_DRAG_RELEASED),
        SCROLL(ScrollEvent.SCROLL),
        MEDIA_ERROR(MediaErrorEvent.MEDIA_ERROR),
        ALERT(WebEvent.ALERT),
        RESIZED(WebEvent.RESIZED),
        STATUS_CHANGED(WebEvent.STATUS_CHANGED),
        VISIBILITY_CHANGED(WebEvent.VISIBILITY_CHANGED),
        WINDOW_CLOSE_REQUEST(WindowEvent.WINDOW_CLOSE_REQUEST),
        WINDOW_HIDDEN(WindowEvent.WINDOW_HIDDEN),
        WINDOW_HIDING(WindowEvent.WINDOW_HIDING),
        WINDOW_SHOWING(WindowEvent.WINDOW_SHOWING),
        WINDOW_SHOWN(WindowEvent.WINDOW_SHOWN),
        WORKER_STATE_CANCELLED(WorkerStateEvent.WORKER_STATE_CANCELLED),
        WORKER_STATE_FAILED(WorkerStateEvent.WORKER_STATE_FAILED),
        WORKER_STATE_READY(WorkerStateEvent.WORKER_STATE_READY),
        WORKER_STATE_RUNNING(WorkerStateEvent.WORKER_STATE_RUNNING),
        WORKER_STATE_SCHEDULED(WorkerStateEvent.WORKER_STATE_SCHEDULED),
        WORKER_STATE_SUCCEEDED(WorkerStateEvent.WORKER_STATE_SUCCEEDED),
        LIST_VIEW_EDIT_START_EVENT(ListView.editStartEvent()),
        LIST_VIEW_EDIT_COMMIT_EVENT(ListView.editCommitEvent()),
        LIST_VIEW_EDIT_CANCEL_EVENT(ListView.editCancelEvent()),
        TREE_VIEW_EDIT_START_EVENT(TreeView.editStartEvent()),
        TREE_VIEW_EDIT_COMMIT_EVENT(TreeView.editCommitEvent()),
        TREE_VIEW_EDIT_CANCEL_EVENT(TreeView.editCancelEvent())
        ;

        private EventTypes(EventType<? extends Event> type) {
            this.type = type;
        }

        public EventType<? extends Event> getType() {
            return type;
        }

        public static EventTypes get(EventType<? extends Event> type) {
            for (EventTypes t : EventTypes.values()) {
                if (t.getType().equals(type)) {
                    return t;
                }
            }
            return null;
        }
        private EventType<? extends Event> type;
    }

    public static enum Controls 
    {
        
        ACCORDION(new AbstractControlFactory<Accordion>() {

            @Override
            public Accordion create() {
                TitledPane t1 = new TitledPane("Label", new Label("Label"));
                TitledPane t2 = new TitledPane("TextField", new TextField());
                TitledPane t3 = new TitledPane("Button", new Button("Button"));
                Accordion accordion = new Accordion();
                accordion.getPanes().addAll(t1, t2, t3);
                return accordion;
            }
        }, ContextMenuEvent.class, DragEvent.class, 
                MouseEvent.class, MouseDragEvent.class, KeyEvent.class, 
                ScrollEvent.class),
        
        CHOICE_BOX(new AbstractControlFactory<ChoiceBox>() {

            @Override
            public ChoiceBox create() {
                ChoiceBox<String> cb = new ChoiceBox<String>();
                cb.getItems().addAll("one", "two", "three");
                cb.setValue("one");
                return cb;
            }
        }, ActionEvent.class, ContextMenuEvent.class, DragEvent.class, 
                MouseEvent.class, MouseDragEvent.class, KeyEvent.class, 
                ScrollEvent.class),

        LABEL(new AbstractControlFactory<Label>() {
            @Override
            public Label create() {
                return new Label("Label");
            }
        }, ContextMenuEvent.class, DragEvent.class, MouseEvent.class, 
                MouseDragEvent.class),
        
        BUTTON(new AbstractControlFactory<Button>() {

            @Override
            public Button create() {
                return new Button("Button");
            }
        }, ActionEvent.class, ContextMenuEvent.class, DragEvent.class, 
                MouseEvent.class, MouseDragEvent.class, KeyEvent.class, 
                ScrollEvent.class),
        
        CHECK_BOX(new AbstractControlFactory<CheckBox>() {

            @Override
            public CheckBox create() {
                return new CheckBox("CheckBox");
            }
        }, ActionEvent.class, ContextMenuEvent.class, DragEvent.class, 
                MouseEvent.class, MouseDragEvent.class, KeyEvent.class, 
                ScrollEvent.class),
        
        HYPERLINK(new AbstractControlFactory<Hyperlink>() {

            @Override
            public Hyperlink create() {
                return new Hyperlink("www.oracle.com");
            }
        }, ActionEvent.class, ContextMenuEvent.class, DragEvent.class, 
                MouseEvent.class, MouseDragEvent.class, KeyEvent.class, 
                ScrollEvent.class),
        
        MENU_BUTTON(new AbstractControlFactory<MenuButton>() {

            @Override
            public MenuButton create() {
                MenuButton m = new MenuButton("Eats");
                m.getItems().addAll(new MenuItem("Burger"), new MenuItem("Hot Dog"));
                return m;
            }
        }, ContextMenuEvent.class, DragEvent.class, MouseEvent.class, 
                MouseDragEvent.class, KeyEvent.class, ScrollEvent.class),
        
        TOGGLE_BUTTON(new AbstractControlFactory<ToggleButton>() {

            @Override
            public ToggleButton create() {
                return new ToggleButton("Toggle button");
            }
        }, ActionEvent.class, ContextMenuEvent.class, DragEvent.class, 
                MouseEvent.class, MouseDragEvent.class, KeyEvent.class, 
                ScrollEvent.class),
        
        TITLED_PANE(new AbstractControlFactory<TitledPane>() {

            @Override
            public TitledPane create() {
                Label l = new Label("Label");
                TitledPane tp = new TitledPane("Label", l);
                tp.setMaxWidth(l.getWidth());
                return tp;
            }
        }, ContextMenuEvent.class, DragEvent.class, MouseEvent.class, 
                MouseDragEvent.class, KeyEvent.class, ScrollEvent.class),
        
        COLOR_PICKER(new AbstractControlFactory<ColorPicker>() {

            @Override
            public ColorPicker create() {
                return new ColorPicker();
            }
        }, ActionEvent.class, ContextMenuEvent.class, DragEvent.class, 
                MouseEvent.class, MouseDragEvent.class, KeyEvent.class, 
                ScrollEvent.class),
        
        COMBO_BOX(new AbstractControlFactory<ComboBox>() {

            @Override
            public ComboBox create() {
                ComboBox<String> cb = new ComboBox<String>();
                cb.getItems().addAll("one", "two", "three");
                cb.setValue("one");
                return cb;
            }
        }, ActionEvent.class, ContextMenuEvent.class, DragEvent.class, 
                MouseEvent.class, MouseDragEvent.class, KeyEvent.class, 
                ScrollEvent.class),
        
        PAGINATION(new AbstractControlFactory<Pagination>() {

            @Override
            public Pagination create() {
                Pagination p = new Pagination(3);
                p.setPageFactory(new Callback<Integer, Node>() {

                    public Node call(Integer p) {
                        return new Label(p.toString());
                    }
                });
                return p;
            }
        }, ActionEvent.class, ContextMenuEvent.class, DragEvent.class, 
                MouseEvent.class, MouseDragEvent.class, KeyEvent.class, 
                ScrollEvent.class),
        
        LIST_VIEW(new AbstractControlFactory<ListView>() {

            @Override
            public ListView create() {
                ListView<String> lv = new ListView<String>();
                lv.getItems().addAll("one", "two", "three");
                lv.setEditable(true);
                lv.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

                    public ListCell<String> call(ListView<String> p) {
                        return new TextFieldListCell<String>(new StringConverter<String>() {

                            @Override
                            public String toString(String t) {
                                return t;
                            }

                            @Override
                            public String fromString(String string) {
                                return string;
                            }
                        });
                    }
                });
                return lv;
            }
        }, ContextMenuEvent.class, DragEvent.class, MouseEvent.class, 
                MouseDragEvent.class, KeyEvent.class, ScrollEvent.class, 
                ListView.class),
        
        TEXT_FIELD(new AbstractControlFactory<TextField>() {

            @Override
            public TextField create() {
                return new TextField("Text field");
            }
        }, ActionEvent.class, ContextMenuEvent.class, DragEvent.class, 
                MouseEvent.class, MouseDragEvent.class, KeyEvent.class, 
                ScrollEvent.class, InputMethodEvent.class),
        
        PASSWORD_FIELD(new AbstractControlFactory<PasswordField>() {

            @Override
            public PasswordField create() {
                return new PasswordField();
            }
        }, ActionEvent.class, ContextMenuEvent.class, DragEvent.class, 
                MouseEvent.class, MouseDragEvent.class, KeyEvent.class, 
                ScrollEvent.class, InputMethodEvent.class),
        
        TEXT_AREA(new AbstractControlFactory<TextArea>() {

            @Override
            public TextArea create() {
                return new TextArea("Text area");
            }
        }, ContextMenuEvent.class, DragEvent.class, MouseEvent.class, 
                MouseDragEvent.class, KeyEvent.class, ScrollEvent.class, 
                InputMethodEvent.class),
        
        TREE_VIEW(new AbstractControlFactory<TreeView>() {

            @Override
            public TreeView create() {
                TreeItem<String> root = new CheckBoxTreeItem<String>("Root node");
                root.setExpanded(true);
                root.getChildren().addAll(
                        new CheckBoxTreeItem<String>("Item 1"), 
                        new CheckBoxTreeItem<String>("Item 2"), 
                        new CheckBoxTreeItem<String>("Item 3")
                );
                TreeView<String> tw = new TreeView<String>(root);
                tw.setCellFactory(TextFieldTreeCell.<String>forTreeView());
                tw.setEditable(true);
                return tw;
            }
        }, ContextMenuEvent.class, DragEvent.class, MouseEvent.class, 
                MouseDragEvent.class, KeyEvent.class, ScrollEvent.class, 
                TreeView.class)
        ;

        private Controls(AbstractControlFactory<? extends Control> f, 
                Class<?>... eventsDeclaringClasses) 
        {
            this.f = f;
            this.eventsDeclaringClasses = Arrays.asList(eventsDeclaringClasses);
        }

        public Control getControl() 
        {
            return f.create();
        }
        
        public List<Class<?>> getProcessedEvents()
        {
            return eventsDeclaringClasses;
        }
        
        private AbstractControlFactory<? extends Control> f;
        private List<Class<?>> eventsDeclaringClasses;
        
    }
}
