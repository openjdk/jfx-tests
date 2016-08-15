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
import java.util.LinkedList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Labeled;
import javafx.scene.control.test.Change;
import javafx.scene.shape.Rectangle;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author Andrey Glushchenko
 */
public class HyperlinkApp extends LabeledsAbstactApp {

    protected Utils.LayoutSize defaultLayout = new Utils.LayoutSize(100, 20, 100, 20, 100, 20);
    private Hyperlink target;
    public static String HYPER_LINK_IS_FIRED = "Hyperlink is fired";
    /**
     * public ELLIPSING_STRING because we need to get result string from outside
     * class by static context.
     */
    public static final String ELLIPSING_STRING = "ggggggggggggggggWWW";
    public static final String ELLIPSING_STRING_MODENA = "gggggggggggggggWWW";

    public static enum IDs {

        Hyperlink, Target
    }

    @Override
    protected List<Labeled> getConstructorPage() {
        List<Labeled> list = new ArrayList<Labeled>();

        Hyperlink hyperlink = new Hyperlink();
        defaultLayout.apply(hyperlink);
        if (hyperlink.getText() != null) {
            if (!hyperlink.getText().isEmpty()) {
                reportGetterFailure("hyperlink.getText()");
            }
        }
        if (hyperlink.isVisited() != false) {
            reportGetterFailure("hyperlink.isVisited");
        }
        list.add(hyperlink);

        Hyperlink text_hyperlink = new Hyperlink("Hyperlink");
        defaultLayout.apply(text_hyperlink);
        if (text_hyperlink.getText() == null || !text_hyperlink.getText().contentEquals("Hyperlink")) {
            reportGetterFailure("hyperlink.getText()");
        }
        list.add(text_hyperlink);

        Rectangle rectangle = new Rectangle(10, 10);
        Hyperlink node_hyperlink = new Hyperlink("Hyperlink with Node", rectangle);
        defaultLayout.apply(node_hyperlink);
        if (text_hyperlink.getText() == null || !node_hyperlink.getText().contentEquals("Hyperlink with Node")) {
            reportGetterFailure("hyperlink.getText()");
        }
        if (node_hyperlink.getGraphic() != rectangle) {
            reportGetterFailure("hyperlink.getGraphic()");
        }
        list.add(node_hyperlink);
        return list;
    }

    public static enum Pages {

        isVisited, Action
    }

    public HyperlinkApp() {
        super("hyperlinks");
    }

    @Override
    protected Labeled getTestingControl() {
        Hyperlink hyperlink = new Hyperlink("Default");
        defaultLayout.apply(hyperlink);
        return hyperlink;
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
        Utils.launch(HyperlinkApp.class, args);
    }

    private Hyperlink getTarget() {
        return target;
    }

    private void setTarget(Hyperlink target) {
        this.target = target;
    }

    @Override
    protected TestNode setup() {
        TestNode root = new TestNode();
        defFill(root);
        List<Change<Labeled>> list = new LinkedList<Change<Labeled>>();

        list.add(new Change<Labeled>(Pages.isVisited.name()) {
            public void apply(Labeled labeled) {
                Hyperlink hyperlink = (Hyperlink) labeled;
                hyperlink.setVisited(false);
                if (hyperlink.isVisited() != false) {
                    reportGetterFailure(getMarker());
                }
                hyperlink.setVisited(true);
                if (hyperlink.isVisited() != true) {
                    reportGetterFailure(getMarker());
                }
            }
        });
        root.add(new Page(list), Pages.isVisited.name());


        list.add(new Change<Labeled>("control") {
            public void apply(Labeled labeled) {
                Hyperlink link = (Hyperlink) labeled;
                link.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        getTarget().setText(HYPER_LINK_IS_FIRED);
                    }
                });
                link.setId(IDs.Hyperlink.name());

            }
        });
        list.add(new Change<Labeled>("target") {
            public void apply(Labeled labeled) {
                setTarget((Hyperlink) labeled);
                labeled.setText("");
                labeled.setId(IDs.Target.name());
            }
        });

        root.add(new Page(list), Pages.Action.name());


        return root;
    }
}
