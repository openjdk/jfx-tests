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

import java.util.EnumMap;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author Sergey Grinev
 */
public class EventsApp extends BasicButtonChooserApp {

    public static void main(String args[]) {
        Utils.launch(EventsApp.class, args);
    }

    public EventsApp() {
        super(600, 400, "Events",false);
    }

    private static class Markers extends Parent {
        final Text txtNormal = getText("Normal ");
        final Text txtShift = getText("Shift ");
        final Text txtAlt = getText("Alt ");
        final Text txtControl = getText("Control ");

        public Markers(String name) {
            this(name, true);
        }

        public Markers(String name, boolean includeAlt) {
            HBox box = new HBox();
            setId(name);
            Text txt = new Text(name);
            txt.setWrappingWidth(150);
            box.getChildren().add(txt);
            box.getChildren().add(txtNormal);
            box.getChildren().add(txtShift);
            if (includeAlt) {
                box.getChildren().add(txtAlt);
            }
            box.getChildren().add(txtControl);
            getChildren().add(box);
        }

        private static Text getText(String value) {
            Text txt = new Text(value);
            txt.setFill(Color.RED);
            txt.setId("shouldbegreen");
            return txt;
        }

        private void mark(Text txt) {
            txt.setFill(Color.GREEN);
        }

        public void markNormal() {
            mark(txtNormal);
        }

        public void markShift() {
            mark(txtShift);
        }

        public void markAlt() {
            mark(txtAlt);
        }

        public void markControl() {
            mark(txtControl);
        }
    }

    private void setupMarkers(MouseEventTypes met) {
        Map<MouseButton, Markers> mmap = new EnumMap<MouseButton, EventsApp.Markers>(MouseButton.class);
        Markers m;
        String name = met.name();
        boolean includeAlt = !Utils.isLinux(); //RT-19395
        if (met.buttonized) {
            mmap.put(MouseButton.PRIMARY, m = new EventsApp.Markers("mouse primary " + name, includeAlt));
            labels.getChildren().add(m);
            mmap.put(MouseButton.SECONDARY, m = new EventsApp.Markers("mouse secondary " + name, includeAlt));
            labels.getChildren().add(m);
        } else {
            mmap.put(MouseButton.NONE, m = new EventsApp.Markers("mouse " + name, includeAlt));
            labels.getChildren().add(m);
        }
        mouseMap.put(met, mmap);
    }
    private VBox labels;
    private final Map<MouseEventTypes, Map<MouseButton, Markers>> mouseMap =
            new EnumMap<MouseEventTypes, Map<MouseButton, EventsApp.Markers>>(MouseEventTypes.class);
    private final Map<KeyEventTypes, Markers> keyMap =
            new EnumMap<KeyEventTypes, EventsApp.Markers>(KeyEventTypes.class);

    public enum MouseEventTypes {
        click, released, pressed, entered(false), exited(false), moved(false), drag //TODO: add jemmy support for wheel first
        //, wheel(false)
        ;
        public boolean buttonized;

        private MouseEventTypes(boolean buttonized) {
            this.buttonized = buttonized;
        }

        private MouseEventTypes() {
            this(true);
        }
    }

    private class RegMouseListener implements EventHandler<MouseEvent> {
        private final MouseEventTypes type;

        public RegMouseListener(MouseEventTypes type) {
            this.type = type;
        }

        public void handle(MouseEvent me) {
            Markers markers = mouseMap.get(type).get(me.getButton());
            if (markers == null) {
                System.err.println("unknown event " + me);
                return;
            }
            System.err.println("onevent " + type + " " + me.getButton() + " shift: " + me.isShiftDown() + " alt: " + me.isAltDown() + " ctrl: " + me.isControlDown() + " (" + markers.getId() + ")");
            if (me.isAltDown()) {
                markers.markAlt();
            }
            if (me.isShiftDown()) {
                markers.markShift();
            }
            if (me.isControlDown()) {
                markers.markControl();
            }
            if (!me.isControlDown() && !me.isAltDown() && !me.isShiftDown()) {
                markers.markNormal();
            }

        }
    }

    public enum KeyEventTypes {
        typed, released, pressed;
    }
    public static int eventCount = 0;

    private class RegKeyListener implements EventHandler<KeyEvent> {
        private final KeyEventTypes type;

        public RegKeyListener(KeyEventTypes type) {
            this.type = type;
        }

        public void handle(KeyEvent me) {
            System.err.println("onevent " + type + " " + me.getCharacter() + "event: " + me.toString());
            Markers markers = keyMap.get(type);
            if (markers == null) {
                System.err.println("unknown event " + me);
                return;
            }
            if (me.isAltDown()) {
                markers.markAlt();
            }
            if (me.isShiftDown()) {
                markers.markShift();
            }
            if (me.isControlDown()) {
                markers.markControl();
            }
            if (!me.isControlDown() && !me.isAltDown() && !me.isShiftDown()) {
                markers.markNormal();
            }
            eventCount = eventCount +1;

        }
    }
    private int SLOTSIZEX;
    private int SLOTSIZEY;
 /**
     * Next free slot horizontal position
     */
    protected int shiftX = 0;
    /**
     * Next free slot vertical position
     */
    protected int shiftY = 0;

    private void addSlot(String name, Node field) {
        VBox slot = new VBox();
        slot.getChildren().addAll(new Text(name), field);
        slot.setTranslateX(shiftX);
        slot.setTranslateY(shiftY);
        int stepX = SLOTSIZEX + 1;
        int stepY = SLOTSIZEY + 20;
        shiftX += stepX;     // SLOTSIZE + 1;
        if ((shiftX + SLOTSIZEX) > width) {
            shiftX = 0;
            shiftY += stepY; //SLOTSIZE + 20;
        }
        getPageContent().getChildren().add(slot);
    }
    private Pane pageContent;
    private TestNode root;

    protected Pane getPageContent() {
        return pageContent;
    }

    private class RunTestNode extends TestNode {

        protected Runnable runnable;

        public RunTestNode(Runnable runnable) {
            this.runnable = runnable;
        }
    }

    private void addPage(String name, Runnable runnable) {
        TestNode page = new RunTestNode(runnable) {
            @Override
            public Node drawNode() {
                pageContent = new Pane();
                runnable.run();
                shiftX = 0;
                shiftY = 0;
                return getPageContent();
            }
        };
        root.add(page, name);
    }

    @Override
    protected TestNode setup() {
        root = new TestNode();
        addPage("mouse", new Runnable() {
            public void run() {
                HBox cont = new HBox();

                labels = new VBox();
                labels.setTranslateX(50);

                for (EventsApp.MouseEventTypes met : MouseEventTypes.values()) {
                    setupMarkers(met);
                }


                Rectangle rect = new Rectangle(10, 10, 100, 100);
                cont.getChildren().add(rect);
                cont.getChildren().add(labels);
                rect.setId("rect");
                rect.setFill(Color.RED);
                rect.setStroke(Color.BLACK);

                rect.setOnMouseClicked(new RegMouseListener(MouseEventTypes.click));
                rect.setOnMousePressed(new RegMouseListener(MouseEventTypes.pressed));
                rect.setOnMouseReleased(new RegMouseListener(MouseEventTypes.released));
                rect.setOnMouseEntered(new RegMouseListener(MouseEventTypes.entered));
                rect.setOnMouseExited(new RegMouseListener(MouseEventTypes.exited));
                rect.setOnMouseMoved(new RegMouseListener(MouseEventTypes.moved));
                rect.setOnMouseDragged(new RegMouseListener(MouseEventTypes.drag));
                //rect.setOnMouseWheelMoved(new RegMouseListener(MouseEventTypes.wheel));

                addSlot("click in square", cont);
            }
        });

        addPage("keyboard", new Runnable() {
            public void run() {
                eventCount = 0;
                HBox cont = new HBox();

                labels = new VBox();
                labels.setTranslateX(50);

                for (EventsApp.KeyEventTypes met : KeyEventTypes.values()) {
                    boolean includeAlt = met != EventsApp.KeyEventTypes.typed; //RT-10089
                    Markers m = new EventsApp.Markers("keyboard " + met.name(), includeAlt);
                    labels.getChildren().add(m);
                    keyMap.put(met, m);
                }

                final Rectangle rect = new Rectangle(10, 10, 100, 100);
                cont.getChildren().add(rect);
                cont.getChildren().add(labels);
                rect.setId("rect");
                rect.setFill(Color.YELLOW);
                rect.setStroke(Color.BLACK);

                rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        rect.requestFocus();
                    }
                });
                rect.setOnKeyPressed(new RegKeyListener(KeyEventTypes.pressed));
                rect.setOnKeyReleased(new RegKeyListener(KeyEventTypes.released));
                rect.setOnKeyTyped(new RegKeyListener(KeyEventTypes.typed));

                rect.setFocusTraversable(true);
                rect.requestFocus();

                addSlot("press button B", cont);
            }
        });
        return root;
    }
}
