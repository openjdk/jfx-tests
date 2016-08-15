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
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.test.Change;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author Andrey Glushchenko
 */
public class LabelsApp extends LabeledsAbstactApp {

    protected Utils.LayoutSize defaultLayout = new Utils.LayoutSize(100, 15, 100, 15, 100, 15);
    public static final String DEFAULT_LABEL = "default label";
    public static final String GRAPHICS_LABEL = "default label with graphics";
    /**
     * public ELLIPSING_STRING because we need to get result string from outside
     * class by static context.
     */
    public static final String ELLIPSING_STRING = "ggggggggggggggWWW";

    @Override
    protected List<Labeled> getConstructorPage() {
        List<Labeled> list = new ArrayList<Labeled>();

        Label default_label = new Label(DEFAULT_LABEL);
        defaultLayout.apply(default_label);
        if (!default_label.getText().equals(DEFAULT_LABEL)) {
            reportGetterFailure("new Label(DEFAULT_LABEL)");
        } else {
            list.add(default_label);
        }

        Rectangle rectangle = new Rectangle(10, 10, Color.rgb(200, 100, 100));
        Label graphic_label = new Label(GRAPHICS_LABEL, rectangle);
        defaultLayout.apply(graphic_label);
        if (!graphic_label.getText().equals(GRAPHICS_LABEL) || graphic_label.getGraphic() != rectangle) {
            reportGetterFailure("new Label(GRAPHICS_LABEL, rectangle)");
        } else {
            list.add(graphic_label);
        }
        return list;
    }

    public static enum Pages {

        setTextFill,
    }

    public LabelsApp() {
        super("labels");
    }

    @Override
    protected Labeled getTestingControl() {
        Label label = new Label(DEFAULT);
        label.setGraphic(new Rectangle(10, 10));
        defaultLayout.apply(label);
        return label;
    }

    @Override
    public String getEllipsingString() {
        return ELLIPSING_STRING;
    }

    public static void main(String[] args) {
        Utils.launch(LabelsApp.class, args);
    }

    @Override
    protected TestNode setup() {
        TestNode root = new TestNode();
        defFill(root);
        List<Change<Labeled>> list = new LinkedList<Change<Labeled>>();

        list.add(new Change<Labeled>(Pages.setTextFill.name()) {
            public void apply(Labeled labeled) {
                labeled.setText("filled");
                Paint paint = Color.rgb(128, 200, 200);
                labeled.setTextFill(paint);
                if (labeled.getTextFill() != paint) {
                    reportGetterFailure(getMarker());
                }
            }
        });
        root.add(new LabeledsAbstactApp.Page(list), Pages.setTextFill.name());


        return root;
    }
}
