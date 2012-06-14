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
 * questions.
 */
package org.jemmy.samples.lookup;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.lookup.LookupCriteria;
import org.junit.Test;

/**
 * Not in all cases a straightforward lookup could give an answer right away.
 * Here are a few examples.
 *
 * @author shura
 */
public class CompoundLookupSample extends LookupSampleBase {

    /**
     * Nested lookup.
     */
    @Test
    public void nestedLookup() {
        //find the scene
        SceneDock scene = new SceneDock();

        //look for a VBox
        NodeDock vBox = new NodeDock(scene.asParent(), VBox.class);
        //look for a GridPane inside
        NodeDock gridPane = new NodeDock(vBox.asParent(), GridPane.class);

        //finally, find the label
        new NodeDock(gridPane.asParent(), Label.class, new LookupCriteria<Label>() {

            public boolean check(Label cntrl) {
                return cntrl.getText().equals("(0,0)");
            }

            @Override
            public String toString() {
                return "a label";
            }
        }).mouse().move();

        assureMouseOver("(0,0)");
    }

    /**
     * How to search by a few criteria
     */
    @Test
    public void fewCriteria() {
        //find the scene
        SceneDock scene = new SceneDock();

        //let's find a label which has "1,1" in text by two criteria
        NodeDock label11 = new NodeDock(scene.asParent(), Label.class,
                new LookupCriteria<Label>() {

                    public boolean check(Label cntrl) {
                        return cntrl.getText().contains("1,");
                    }

                    @Override
                    public String toString() {
                        return "a label with leading \"1\" in the text";
                    }
                },
                new LookupCriteria<Label>() {

                    public boolean check(Label cntrl) {
                        return cntrl.getText().contains(",1");
                    }

                    @Override
                    public String toString() {
                        return "a label with trailing \"1\" in the text";
                    }
                });
        label11.mouse().move();

        assureMouseOver("(1,1)");
    }
}
