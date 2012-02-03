/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package org.jemmy.fx;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.text.Text;
import org.jemmy.action.GetAction;
import org.jemmy.env.Environment;
import org.jemmy.lookup.ByStringLookup;
import org.jemmy.resources.StringComparePolicy;

/**
 *
 * @param <T>
 * @author Shura
 */
public class ByText<T extends Node> extends ByStringLookup<T> {

    /**
     *
     * @param text
     */
    public ByText(String text) {
        super(text);
    }

    /**
     *
     * @param text
     * @param policy
     */
    public ByText(String text, StringComparePolicy policy) {
        super(text, policy);
    }

    @Override
    public String getText(T arg0) {
        return getNodeText(arg0);
    }

    /**
     *
     * @param nd
     * @return
     */
    public static String getNodeText(final Node nd) {
        return new GetAction<String>() {

            @Override
            public void run(Object... parameters) {
                if(nd instanceof Text) {
                    setResult(Text.class.cast(nd).getText());
                } else if(nd instanceof Labeled) {
                    setResult(Labeled.class.cast(nd).getText());
                } else if(nd instanceof TextInputControl) {
                    setResult(TextInputControl.class.cast(nd).getText());
                } else if(nd instanceof ChoiceBox) {
                    Object selectedItem = ChoiceBox.class.cast(nd).getSelectionModel().getSelectedItem();
                    if (selectedItem != null)
                    setResult(selectedItem.toString());
                } else /*
                // the rest is not yet implemented in javafx.scene.control

                 if(nd instanceof TextBox) {
                    setResult(TextBox.class.cast(nd).get$text());
                } else if(nd instanceof ListView) {
                    setResult(getText((ListView)nd));
                } else if(nd instanceof ChoiceBox) {
                    setResult(((ChoiceBox)nd).get$selectedItem().toString());
                } else
                 */ {
                    setResult("");
                }
            }

            @Override
            public String toString() {
                return "Getting text of " + nd;
            }

        }.dispatch(Environment.getEnvironment());
    }
}
