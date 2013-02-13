/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.richtext;

import java.lang.reflect.Array;
import java.util.Collection;
import javafx.scene.text.TextAlignment;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.richtext.RichTextPropertiesApp.Pages;

/**
 *
 * @author Andrey Glushchenko
 */
public class RichTextMixedTest extends RichTextPropertiesFunctional {

    @BeforeClass
    public static void setUp() {
        RichTextPropertiesApp.main(null);
    }

    @Before
    public void prepare() {
        lookup();
    }

    @After
    public void errase() {
        clear();
        throwErrors();
    }

    /**
     * Test for adding differents nodes in TextFlow.
     */
    @Test(timeout = 40000)
    public void mixedAddTest() {


        addAllFlowCombinations("line");
        Pages[] list = new Pages[2];

        addFlow();
        list[0] = Pages.RectanglePage;
        list[1] = Pages.TextPage;
        fillFlow(list, "line7");
        addFlow();
        list[0] = Pages.RectanglePage;
        list[1] = Pages.ButtonPage;
        fillFlow(list, "line8");
        addFlow();
        list[0] = Pages.ButtonPage;
        list[1] = Pages.RectanglePage;
        fillFlow(list, "line9");
        addFlow();
        list[0] = Pages.TextPage;
        list[1] = Pages.RectanglePage;
        fillFlow(list, "line10");
        addFlow();
        list[0] = Pages.ButtonPage;
        list[1] = Pages.TextPage;
        fillFlow(list, "line11");
        addFlow();
        list[0] = Pages.TextPage;
        list[1] = Pages.ButtonPage;
        fillFlow(list, "line12");
        check("mixedAddTest");
    }

    /**
     * Test for rotation differents nodes in TextFlow.
     */
    @Test(timeout = 60000)
    public void miltipleRotationTest() {
        setFlowBorder(true);
        for (int i = 0; i < 4; i++) {
            setFlowWidth(RichTextPropertiesApp.paneWidth - 70 - 30 * i);
            fillFlowWithRoatation("line" + i);
            setFlowRotation((3 - i) * 30D);
            if (i < 3) {
                addFlow();
            }
        }
        check("miltipleRotationTest");
    }

    /**
     * Test for alignment of differents nodes in TextFlow.
     */
    @Test(timeout = 30000)
    public void alignmentTest() {
        setFlowBorder(true);
        Pages[] list = new Pages[3];
        list[0] = Pages.TextPage;
        list[1] = Pages.RectanglePage;
        list[2] = Pages.ButtonPage;
        for (TextAlignment alig : TextAlignment.values()) {
            setFlowWidth(RichTextPropertiesApp.paneWidth);
            fillFlow(list, alig.name() + MULTILINE_TEXT);
            setAlignment(alig);
            addFlow();
        }
        check("alignmentTest");
    }

    /**
     * Test for deleting nodes form TextFlow.
     */
    @Test(timeout = 30000)
    public void deleteItemTest() {
        addAllFlowCombinations("line");
        Pages[][] list = getListPagesCombinations();
        for (int i = 0; i < 6; i++) {
            selectFlow(i);
            changePage(list[i][1]);
            selectItem("line" + i);
            delete();
        }
        check("deleteItemTest");



    }

    private Pages[][] getListPagesCombinations() {
        Pages[][] list = new Pages[6][3];
        list[0][0] = Pages.TextPage;
        list[0][1] = Pages.RectanglePage;
        list[0][2] = Pages.ButtonPage;
        list[1][0] = Pages.TextPage;
        list[1][1] = Pages.ButtonPage;
        list[1][2] = Pages.RectanglePage;
        list[2][0] = Pages.RectanglePage;
        list[2][1] = Pages.TextPage;
        list[2][2] = Pages.ButtonPage;
        list[3][0] = Pages.RectanglePage;
        list[3][1] = Pages.ButtonPage;
        list[3][2] = Pages.TextPage;
        list[4][0] = Pages.ButtonPage;
        list[4][1] = Pages.RectanglePage;
        list[4][2] = Pages.TextPage;
        list[5][0] = Pages.ButtonPage;
        list[5][1] = Pages.TextPage;
        list[5][2] = Pages.RectanglePage;
        return list;
    }

    private void addAllFlowCombinations(String name) {
        Pages[][] list = getListPagesCombinations();
        for (int i = 0; i < list.length; i++) {
            fillFlow(list[i], name + i);
            if (i < list.length - 1) {
                addFlow();
            }
        }
    }

    private void fillFlowWithRoatation(String name) {
        Pages[] list = new Pages[3];
        list[0] = Pages.TextPage;
        list[1] = Pages.RectanglePage;
        list[2] = Pages.ButtonPage;
        fillFlow(list, name);
        changePage(Pages.TextPage);
        selectItem(name);
        setRotation(-45D);
        changePage(Pages.RectanglePage);
        selectItem(name);
        setRotation(90D);
        changePage(Pages.ButtonPage);
        selectItem(name);
        setRotation(45D);

    }

    private void fillFlow(Pages pages[], String name) {
        for (Pages p : pages) {
            changePage(p);
            addItem(name);
        }
    }

    protected void check(String testName) {
        checkScreenshot("RichTextMixedTest-" + testName);
    }
}
