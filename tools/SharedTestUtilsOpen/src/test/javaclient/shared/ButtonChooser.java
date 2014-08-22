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

package test.javaclient.shared;

import java.util.Collection;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author shubov
 */
public class ButtonChooser implements TestSceneChooser {

    protected final int width;
    private Pane buttons;
    private static final int TABS_SPACE = 270;
    public static final String AdditionalActionButtonTxt = "AdditionalAction";
    private Utils.TextButton additionalActionButton;
    private TestNode selectedTestNode;
    private SelectActionProvider selectActionProvider;

    public ButtonChooser(int a_width, boolean showAdditionalActionButton, SelectActionProvider a_selectActionProvider) {
        width = a_width;
        selectActionProvider = a_selectActionProvider;

        buttons = new FlowPane();
        buttons.setMaxWidth(width);
        buttons.setMinWidth(width);

        if (showAdditionalActionButton) {
            additionalActionButton = new Utils.TextButton(AdditionalActionButtonTxt, null);
            buttons.getChildren().add(additionalActionButton);
            additionalActionButton.setTextHandler(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent t) {
                    if (null != selectedTestNode) {
                        selectedTestNode.additionalAction();
                    }
                }
            });
        }
    }

    public Pane getButtonsPane() {
        return buttons;
    }

    public void addTestNodesToChooser(final Collection<TestNode> tns) {
        for (TestNode tn : tns) {
            addTestNodeToChooser(tn);
        }
    }

    public void addTestNodeToChooser(final TestNode tn) {
        addTestNodeToChooser(tn, tn.getName());
    }

    public void addTestNodeToChooser(final TestNode tn, String nodeName) {
        tn.setName(nodeName);
        Utils.deferAction(new Runnable() {
            public void run() {
                // add content to the scene
                buttons.getChildren().add(new Utils.TextButton(tn.getName(), new Runnable() {
                    public void run() {
                        selectedTestNode = tn;
                        highlightItem(tn.getName());
                        selectActionProvider.selectNode(tn);
                    }
                }));
            }
        });
    }

    public boolean highlightItem(String item) {
        boolean found = false;
        for (Node node : buttons.getChildren()) {
            if (node instanceof Utils.TextButton) {
                Utils.TextButton textButton = (Utils.TextButton) node;
                if (item.equals(textButton.getName())) {
                    textButton.setFillWithActiveColor();
                    found = true;
                } else {
                    textButton.setFillWithPassiveColor();
                }
            }
        }
        return found;
    }
}