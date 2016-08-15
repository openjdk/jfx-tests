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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.test.Change;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author Andrey Glushchenko
 */
public class CheckBoxApp extends ButtonBaseApp {

    public static final String DEFAULT_BUTTON = "default button";
    /**
     * public ELLIPSING_STRING because we need to get result string from outside
     * class by static context.
     */
    public static final String ELLIPSING_STRING = "gggggggggggggWWW";
    public static final String ELLIPSING_STRING_MODENA = "gggggggggggggWWW";
    protected Utils.LayoutSize defaultLayout = new Utils.LayoutSize(100, 15, 100, 15, 100, 15);

    @Override
    protected Labeled getListenable() {
        CheckBox button = new FireListenableButton(DEFAULT);
        defaultLayout.apply(button);
        return button;
    }

    @Override
    protected Labeled getButton() {
        CheckBox button = new CheckBox(DEFAULT);
        defaultLayout.apply(button);
        return button;
    }

    public static enum Pages {

        CheckBoxes
    }

    public CheckBoxApp() {
        super("checkboxes");
    }

    @Override
    protected List<Labeled> getConstructorPage() {
        List<Labeled> list = new ArrayList<Labeled>();

        CheckBox default_button = new CheckBox(DEFAULT_BUTTON);
        defaultLayout.apply(default_button);
        if (!default_button.getText().equals(DEFAULT_BUTTON)) {
            reportGetterFailure("new CheckBox(DEFAULT_LABEL)");
        } else {
            list.add(default_button);
        }
        return list;
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
        final boolean bools[] = {false, true};
        for (final boolean i : bools) {
            for (final boolean j : bools) {
                for (final boolean k : bools) {

                    list.add(new Change<Labeled>(i + "," + j + "," + k) {
                        public void apply(Labeled labeled) {
                            CheckBox button = (CheckBox) labeled;
                            button.setAllowIndeterminate(i);
                            if (button.isAllowIndeterminate() != i) {
                                reportGetterFailure(getMarker());
                            }
                            button.setSelected(j);
                            if (button.isSelected() != j) {
                                reportGetterFailure(getMarker());
                            }
                            button.setIndeterminate(k);
                            if (button.isIndeterminate() != k) {
                                reportGetterFailure(getMarker());
                            }
                        }
                    });
                }
            }
        }
        root.add(new Page(list), Pages.CheckBoxes.name());
        return root;
    }

    public static class FireListenableButton extends CheckBox implements Listenable {

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

    public static void main(String[] args) {
        Utils.launch(CheckBoxApp.class, args);
    }
}
