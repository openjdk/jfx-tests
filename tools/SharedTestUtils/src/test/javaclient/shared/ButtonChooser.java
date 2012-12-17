/*
 * Copyright (c) 2010-2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.javaclient.shared;

import java.util.Collection;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.LayoutInfo;
import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author shubov
 */
public class ButtonChooser implements TestSceneChooser {

    protected final int width;
    private Pane buttons;
    private static final int TABS_SPACE = 270;
    public static final String AdditionalActionButtonTxt = "AdditionalAction";
    private Utils.TextButton additionalActionButton = new Utils.TextButton(AdditionalActionButtonTxt, null);
    private TestNode selectedTestNode = null;
    private SelectActionProvider selectActionProvider;

    public ButtonChooser(int a_width, boolean showAdditionalActionButton, SelectActionProvider a_selectActionProvider) {
        width = a_width;
        selectActionProvider = a_selectActionProvider;

        buttons = new FlowPane();
        buttons.setMaxWidth(width);
        buttons.setMinWidth(width);

        if (showAdditionalActionButton) {
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
