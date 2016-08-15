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
import javafx.scene.control.MenuButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import test.javaclient.shared.Utils;

/**
 *
 * @author Andrey Glushchenko
 */
public class MenuButtonsApp extends ButtonSimpleApp {

    public static final String DEFAULT_BUTTON = "default button";
    public static final String GRAPHICS_BUTTON = "default button with graphics";
    /**
     * public ELLIPSING_STRING because we need to get result string from outside
     * class by static context.
     */
    public static final String ELLIPSING_STRING = "gggggggggggWWW";
    public static final String ELLIPSING_STRING_MODENA = "ggggggggggggWWW";
    protected Utils.LayoutSize defaultLayout = new Utils.LayoutSize(100, 20, 100, 20, 100, 20);

    public MenuButtonsApp() {
        super("menubuttons");
    }

    @Override
    protected List<Labeled> getConstructorPage() {
        List<Labeled> list = new ArrayList<Labeled>();

        MenuButton default_button = new MenuButton(DEFAULT_BUTTON);
        defaultLayout.apply(default_button);
        if (!default_button.getText().equals(DEFAULT_BUTTON)) {
            reportGetterFailure("new MenuButton(DEFAULT_LABEL)");
        } else {
            list.add(default_button);
        }

        Rectangle rectangle = new Rectangle(10, 10, Color.rgb(200, 100, 100));
        MenuButton graphic_button = new MenuButton(GRAPHICS_BUTTON, rectangle);
        defaultLayout.apply(graphic_button);
        if (!graphic_button.getText().equals(GRAPHICS_BUTTON) || graphic_button.getGraphic() != rectangle) {
            reportGetterFailure("new MenuButton(GRAPHICS_LABEL, rectangle)");
        } else {
            list.add(graphic_button);
        }
        return list;
    }

    @Override
    public String getEllipsingString() {
        final String oldLookAndFeelName = "caspian";
        final String lfProp = System.getProperty("javafx.userAgentStylesheetUrl");
        if (null != lfProp) {
            if (0 == oldLookAndFeelName.compareTo(lfProp)) {
                return ELLIPSING_STRING;
            }

        }
        return ELLIPSING_STRING_MODENA;
    }

    public static void main(String[] args) {
        Utils.launch(MenuButtonsApp.class, args);
    }

    @Override
    protected Labeled getListenable() {
        MenuButton button = new FireListenableButton(DEFAULT);
        defaultLayout.apply(button);
        return button;
    }

    @Override
    protected Labeled getButton() {
        MenuButton button = new MenuButton(DEFAULT);
        defaultLayout.apply(button);
        return button;
    }

    public static class FireListenableButton extends MenuButton implements Listenable {

        EventHandler handler;

        public FireListenableButton(String text) {
            super(text);
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
}
