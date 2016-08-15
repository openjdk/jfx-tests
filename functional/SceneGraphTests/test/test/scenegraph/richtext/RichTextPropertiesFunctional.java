/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.richtext;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.TextAlignment;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.Assert;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 *
 * @author Andrey Glushchenko
 */
public abstract class RichTextPropertiesFunctional extends TestBase {

    protected final static String TEXT1 = " first string";
    protected final static String TEXT2 = " second string";
    protected final static String TEXT3 = " third string";
    protected final static String MULTILINE_TEXT = "(first line)\n"
            + "(second line)\n";

    protected final static String STYLE1 = " -fx-fill: linear-gradient(from 0% 0% to 100% 100%, repeat, orange 0%, red 50%);"
            + "-fx-stroke: black;"
            + "-fx-stroke-width: 1;";
    protected final static String STYLE2 = "-fx-fill: green;"
            + "-fx-font-style: italic;";
    protected final static String STYLE3 = "-fx-underline: true;"
            + "-fx-fill: transparent;"
            + "-fx-stroke: linear-gradient(from 0% 0% to 100% 100%, repeat, black 0%, blue 50%);"
            + "-fx-stroke-width: 1;";
    protected final static Font font1 = Font.font("Kalinga", 50);
    protected final static Font font2 = Font.font("Lucida Bright Italic", 8);
    protected final static Font font3 = Font.font("Smudger LET Plain:1.0", 16);
    private Wrap<? extends Pane> screenshotArea = null;
    private RichTextPropertiesApp application = null;

    protected void lookup() {
        Parent p = getScene().as(Parent.class, Node.class);
        screenshotArea = p.lookup(Pane.class, new ByID<Pane>(RichTextPropertiesApp.PANE_ID)).wrap();
        application = RichTextPropertiesApp.getApplication();
    }

    protected void delete() {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.delete();
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void selectItem(final String text) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.select(text);
            }
        }).dispatch(Root.ROOT.getEnvironment());

    }

    protected void addItem(final String text) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.addItem(text);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void setRotation(final Double d) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setRotation(d);

            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void setText(final String text) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setText(text);
            }
        }).dispatch(Root.ROOT.getEnvironment());

    }

    protected void setStyle(final String style) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setStyle(style);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }
    protected void setLineSpacing(final double d){
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setLineSpacing(d);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void setFont(final Font font) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setFont(font);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void setFst(final FontSmoothingType fst) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setFst(fst);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void setUnderline(final Boolean underline) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setUnderline(underline);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void setStrike(final Boolean strike) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setStrike(strike);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void setRectHeight(final Double d) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setRectHeight(d);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void setRectWidth(final Double d) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setRectWidth(d);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void changePage(final RichTextPropertiesApp.Pages page) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.changePage(page);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void setAlignment(final TextAlignment alignment) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setAlignment(alignment);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void setFlowBorder(final boolean border) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setFlowBorder(border);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void setFlowWidth(final Integer d) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setFlowWidth(d);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void setFlowRotation(final Double d) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setFlowRotation(d);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void selectFlow(final int num) {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.selectFlow(num);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void addFlow() {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.addFlow();
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void clear() {
        (new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.clear();
            }
        }).dispatch(Root.ROOT.getEnvironment());

    }
    private List<Throwable> errorList = null;

    private void error(Throwable t) {
        if (errorList == null) {
            errorList = new LinkedList<Throwable>();
        }
        errorList.add(t);
    }

    protected void checkScreenshot(final String testName) {
        application.requestDefaultFocus();//Because we don't need  focus on screenshot
        try {
            new Waiter(Wrap.WAIT_STATE_TIMEOUT).ensureState(new State() {
                public Object reached() {
                    try {
                        ScreenshotUtils.checkScreenshot(testName, screenshotArea);
                        return true;
                    } catch (Throwable t) {
                        return null;
                    }
                }
            });
        } catch (Throwable t) {
            error(t);
        }

    }

    protected void throwErrors() {
        if (errorList != null) {
            String errorMessage = "";
            for (Throwable t : errorList) {
                errorMessage += t.getMessage();
            }
            if (!errorMessage.equals("")) {
                Assert.fail(errorMessage);
            }
        }
    }

    protected abstract void check(String testName);
}
