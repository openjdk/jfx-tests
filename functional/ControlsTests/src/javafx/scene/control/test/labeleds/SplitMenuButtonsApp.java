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
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import test.javaclient.shared.Utils;

/**
 * @author Andrey Glushchenko
 */
public class SplitMenuButtonsApp extends ButtonSimpleApp {

    public static final String DEFAULT_BUTTON = "default button";
    public static final String GRAPHICS_BUTTON = "default button with graphics";
    /**
     * public ELLIPSING_STRING because we need to get result string from outside
     * class by static context.
     */
    public static final String ELLIPSING_STRING = "ggggggggggWWW";
    public static final String ELLIPSING_STRING_MODENA = "gggggggggggWWW";
    protected Utils.LayoutSize defaultLayout = new Utils.LayoutSize(150, 30, 150, 30, 150, 30);

    public SplitMenuButtonsApp() {
        super("splitmenubuttons");
    }

    @Override
    protected List<Labeled> getConstructorPage() {
        List<Labeled> list = new ArrayList<Labeled>();

        SplitMenuButton defaultButton = new SplitMenuButton();
        defaultLayout.apply(defaultButton);
        if (!defaultButton.getText().equals("")) {
            reportGetterFailure("new SplitMenuButton()");
        } else {
            list.add(defaultButton);
        }

        SplitMenuButton itemsButton = new SplitMenuButton(new MenuItem("Item 1"), new MenuItem("Item 2"), new MenuItem("Item 3"));
        defaultLayout.apply(itemsButton);
        if (!itemsButton.getItems().get(0).getText().equals("Item 1")
                || !itemsButton.getItems().get(1).getText().equals("Item 2")
                || !itemsButton.getItems().get(2).getText().equals("Item 3")
                || !itemsButton.getText().equals("")
                || itemsButton.getGraphic() != null) {
            reportGetterFailure("new SplitMenuButton(MenuItem...)");
        } else {
            list.add(itemsButton);
        }

        return list;
    }

    public static class FireListenableButton extends SplitMenuButton implements Listenable {

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

    public static void main(String[] args) {
        Utils.launch(SplitMenuButtonsApp.class, args);
    }

    @Override
    protected Labeled getListenable() {
        SplitMenuButton button = new FireListenableButton();
        button.setText(DEFAULT);
        defaultLayout.apply(button);
        return button;
    }

    @Override
    protected Labeled getButton() {
        SplitMenuButton button = new SplitMenuButton();
        button.setText(DEFAULT);
        defaultLayout.apply(button);
        return button;
    }
}
