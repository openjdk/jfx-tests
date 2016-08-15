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
package javafx.scene.control.test.labeleds;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.Labeled;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.test.Change;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author Andrey Glushchenko
 */
public class ToggleButtonsApp extends ButtonBaseApp {

    public static final String DEFAULT_BUTTON = "default button";
    public static final String GRAPHICS_BUTTON = "default button with graphics";
    /**
     * public ELLIPSING_STRING because we need to get result string from outside
     * class by static context.
     */
    public static final String ELLIPSING_STRING = "ggggggggggggggWWW";
        public static final String ELLIPSING_STRING_MODENA = "ggggggggggggggWWW";

    protected Utils.LayoutSize defaultLayout = new Utils.LayoutSize(100, 20, 100, 20, 100, 20);

    public static enum Pages {

        setSelected
    }

    public ToggleButtonsApp(String title) {
        super(title);
    }

    public ToggleButtonsApp() {
        super("togglebuttons");
        enableBorder(false);
    }

    @Override
    protected List<Labeled> getConstructorPage() {
        List<Labeled> list = new ArrayList<Labeled>();

        ToggleButton default_button = new ToggleButton(DEFAULT_BUTTON);
        defaultLayout.apply(default_button);
        if (!default_button.getText().equals(DEFAULT_BUTTON)) {
            reportGetterFailure("new ToggleButton(DEFAULT_LABEL)");
        } else {
            list.add(default_button);
        }

        Rectangle rectangle = new Rectangle(10, 10, Color.rgb(200, 100, 100));
        ToggleButton graphic_button = new ToggleButton(GRAPHICS_BUTTON, rectangle);
        defaultLayout.apply(graphic_button);
        if (!graphic_button.getText().equals(GRAPHICS_BUTTON) || graphic_button.getGraphic() != rectangle) {
            reportGetterFailure("new ToggleButton(GRAPHICS_LABEL, rectangle)");
        } else {
            list.add(graphic_button);
        }

        return list;
    }

    @Override
    protected Labeled getListenable() {
        ToggleButton button = new FireListenableButton();
        button.setText(DEFAULT);
        defaultLayout.apply(button);
        return button;
    }

    @Override
    protected Labeled getButton() {
        ToggleButton button = new ToggleButton();
        button.setText(DEFAULT);
        defaultLayout.apply(button);
        return button;
    }

    public static class FireListenableButton extends ToggleButton implements Listenable {

        EventHandler handler;

        public FireListenableButton() {
            super();
        }

        public void setFireListener(EventHandler handler) {
            this.handler = handler;
        }

        @Override
        public void fire() {
            super.fire();
            if (handler != null) {
                handler.handle(null);
            }
        }
    }

    @Override
    public String getEllipsingString() {
        return ELLIPSING_STRING;
    }

    @Override
    protected TestNode setup() {
        TestNode root = new TestNode();
        defFill(root);
        ArrayList<Change<Labeled>> list = new ArrayList<Change<Labeled>>();
        list.add(new Change<Labeled>(Pages.setSelected.name()) {
            public void apply(Labeled labeled) {
                ToggleButton button = (ToggleButton) labeled;
                button.setSelected(true);
                if (button.isSelected() != true) {
                    reportGetterFailure(getMarker());
                }
            }
        });
        root.add(new Page(list), Pages.setSelected.name());
        return root;
    }

    public static void main(String[] args) {
        Utils.launch(ToggleButtonsApp.class, args);
    }
}
