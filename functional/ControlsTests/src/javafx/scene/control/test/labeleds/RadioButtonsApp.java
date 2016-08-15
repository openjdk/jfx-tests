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
import javafx.scene.control.RadioButton;
import test.javaclient.shared.Utils;

/**
 *
 * @author Andrey Glushchenko
 */
public class RadioButtonsApp extends ToggleButtonsApp {

    /**
     * public ELLIPSING_STRING because we need to get result string from outside
     * class by static context.
     */
    public static final String ELLIPSING_STRING = "gggggggggggggWWW";
    public static final String ELLIPSING_STRING_MODENA = "gggggggggggggWWW";

    public RadioButtonsApp() {
        super("RadioButtons");
    }

    @Override
    protected List<Labeled> getConstructorPage() {
        List<Labeled> list = new ArrayList<Labeled>();

        RadioButton default_button = new RadioButton(DEFAULT_BUTTON);
        defaultLayout.apply(default_button);
        if (!default_button.getText().equals(DEFAULT_BUTTON)) {
            reportGetterFailure("new ToggleButton(DEFAULT_LABEL)");
        } else {
            list.add(default_button);
        }

        return list;
    }

    @Override
    protected Labeled getListenable() {
        RadioButton button = new FireListenableButton(DEFAULT);
        defaultLayout.apply(button);
        return button;
    }

    public static class FireListenableButton extends RadioButton implements Listenable {

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

    @Override
    protected Labeled getButton() {
        RadioButton button = new RadioButton(DEFAULT);
        defaultLayout.apply(button);
        return button;
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
        Utils.launch(RadioButtonsApp.class, args);
    }
}
