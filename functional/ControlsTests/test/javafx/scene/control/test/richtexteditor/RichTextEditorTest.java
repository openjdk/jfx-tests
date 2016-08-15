/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package javafx.scene.control.test.richtexteditor;

import client.test.Keywords;
import client.test.ScreenshotCheck;
import client.test.Smoke;
import java.awt.Toolkit;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.test.ControlsTestBase;
import javafx.scene.control.test.MenuApp;
import javafx.scene.control.test.RichTextEditorApp;
import javafx.scene.input.Clipboard;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.PopupWindow;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.TextControlWrap;
import org.jemmy.fx.control.ThemeDriverFactory;
import org.jemmy.fx.control.caspian.CaspianDriverFactory;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.JemmyUtils;
import test.javaclient.shared.Utils;
import test.javaclient.shared.screenshots.GoldenImageManager;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public class RichTextEditorTest extends ControlsTestBase {

    public RichTextEditorTest() {
    }
    static Wrap<? extends Scene> scene = null;
    static Parent<Node> parent = null;
    static Wrap resetBtn;
    static Wrap contentPane = null;
    Wrap<? extends WebView> webView = null;
    Wrap cut = null;
    Wrap copy = null;
    Wrap paste = null;
    Wrap undo = null;
    Wrap redo = null;
    Wrap separator = null;
    Wrap left = null;
    Wrap center = null;
    Wrap right = null;
    Wrap adjust = null;
    Wrap bullets = null;
    Wrap numbers = null;
    Wrap indent = null;
    Wrap removeIndent = null;
    Wrap<? extends ComboBox> paragraph = null;
    Wrap<? extends ComboBox> fontStyle = null;
    Wrap<? extends ComboBox> fontSize = null;
    Wrap<? extends ToggleButton> bold = null;
    Wrap<? extends ToggleButton> italic = null;
    Wrap<? extends ToggleButton> underline = null;
    Wrap<? extends ToggleButton> crossed = null;
    Wrap foreground = null;
    Wrap background = null;
    Wrap<? extends HTMLEditor> htmlEditor = null;
    Parent htmlEditorAsParent;
    protected static KeyboardModifiers CTRL_DOWN_MASK_OS;
    public static String DEFAULT_TEXT_STYLE = "ArialW7";
    public static String PLATFORM_TAG;

    static {
        if (Utils.isMacOS()) {
            CTRL_DOWN_MASK_OS = KeyboardModifiers.META_DOWN_MASK;
            PLATFORM_TAG = "mac_";
        } else {
            CTRL_DOWN_MASK_OS = KeyboardModifiers.CTRL_DOWN_MASK;
            if (Utils.isWindows8()) {
                PLATFORM_TAG = "win8_";
            } else if (Utils.isWindows()) {
                PLATFORM_TAG = "win_";
            } else {
                PLATFORM_TAG = "lin_";
            }
        }
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        RichTextEditorApp.main(null);
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        resetBtn = parent.lookup(new ByID(RichTextEditorApp.RESET_BUTTON_ID)).wrap();
        contentPane = parent.lookup(new ByID<Node>(RichTextEditorApp.TEST_PANE_ID)).wrap();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        JemmyUtils.comparatorDistance = 0.01f;
        reset();
        if (!Utils.isLinux() && !Utils.isMacOS()) //Temporary. Need to be explored, why it generates exception on linux.
        {
            Toolkit.getDefaultToolkit().setLockingKeyState(java.awt.event.KeyEvent.VK_NUM_LOCK, false);
        }
    }

    @After
    public void tearDown() {
    }

//     @ScreenshotCheck
//     @Test(timeout=300000) //platform specific
//     public void fontStylesTest() throws InterruptedException, IOException, Throwable {
//        final Parent<RadioMenuItem> attributes = fontStyle.as(Parent.class, RadioMenuItem.class);
//        for (int i = 0; i < attributes.lookup().size(); i++) {
//            Wrap<? extends RadioMenuItem> attribute = attributes.lookup().wrap(i);
//            attribute.mouse().click();
//            typeText(getAttributeText("paragraph", attribute.getControl().getText()) + "\n");
//        }
//        check("");
//    }
    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void textSelectionTest() throws InterruptedException, IOException, Throwable {
        int someRandomColor = 19;
        typeText(SOME_TEXT);
        webView.keyboard().pushKey(KeyboardButtons.ENTER);
        typeText(SOME_OTHER_TEXT);
        for (int i = 0; i < SOME_OTHER_TEXT.length() + 1; i++) {
            webView.keyboard().pushKey(KeyboardButtons.LEFT);
        }
        webView.keyboard().pressKey(KeyboardButtons.SHIFT);
        try {
            for (int i = 0; i < SOME_OTHER_TEXT.length() + 1; i++) {
                webView.keyboard().pushKey(KeyboardButtons.LEFT);
            }
        } finally {
            webView.keyboard().releaseKey(KeyboardButtons.SHIFT);
        }
        color(background, "background", someRandomColor);
        webView.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
        webView.keyboard().pushKey(KeyboardButtons.RIGHT);
        ScreenshotUtils.checkScreenshot("RichTextEditor-textSelection", webView);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void textStyleReflectionTest() throws InterruptedException, IOException, Throwable {
        typeText(HELLO_WORLD);
        webView.keyboard().pushKey(KeyboardButtons.ENTER);
        setParagraph(HEADING1);
        typeText(SOME_TEXT);
        ScreenshotUtils.checkScreenshot("RichTextEditor-textStyleReflection1", contentPane);
        webView.keyboard().pushKey(KeyboardButtons.ENTER);
        setParagraph(HEADING2);
        typeText(SOME_OTHER_TEXT);
        ScreenshotUtils.checkScreenshot("RichTextEditor-textStyleReflection2", contentPane);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void headingIssueTest() throws InterruptedException, IOException, Throwable {
        setParagraph(HEADING1);
        typeText(SOME_TEXT);
        webView.keyboard().pushKey(KeyboardButtons.ENTER);
        setParagraph(HEADING2);
        typeText(SOME_OTHER_TEXT);
        for (int i = 0; i < SOME_OTHER_TEXT.length(); i++) {
            webView.keyboard().pushKey(KeyboardButtons.LEFT);
        }
        webView.keyboard().pushKey(KeyboardButtons.BACK_SPACE);
        ScreenshotUtils.checkScreenshot("RichTextEditor-headingIssue", webView);
        check("heading_issue");
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void setHtmlTest() throws InterruptedException, IOException {
        SimpleStringProperty ssp = new SimpleStringProperty();
        ssp.set(LIST);
        setHTML(HTML_SAMPLE_TEXT);
        String res = getHTML();
        assertEquals(HTML_SAMPLE_TEXT, res);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void checkStylesTest() throws Throwable {
        setParagraph(HEADING1);
        typeText(SOME_TEXT);
        setFontSize(XX_LARGE_FONT_SIZE);
        typeText(SOME_OTHER_TEXT);
        setParagraph(HEADING6);
        ScreenshotUtils.checkScreenshot("RichTextEditor-styles", webView);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void initialStateTest() throws InterruptedException {
        ScreenshotUtils.checkScreenshot("RichTextEditor-initialState", contentPane);
    }

    @Smoke
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void deletingEmptyStringTest() throws InterruptedException, IOException, Throwable {
        webView.keyboard().pushKey(KeyboardButtons.ENTER);
        webView.keyboard().pushKey(KeyboardButtons.ENTER);
        webView.keyboard().pushKey(KeyboardButtons.UP);
        webView.keyboard().pushKey(KeyboardButtons.UP);
        webView.keyboard().pushKey(KeyboardButtons.DELETE);
        webView.keyboard().pushKey(KeyboardButtons.DOWN);
        webView.keyboard().pushKey(KeyboardButtons.DOWN);
        webView.keyboard().pushKey(KeyboardButtons.BACK_SPACE);
        assertTrue(getHTML().equals(readResource("deleting_empty_string_test.html")));
    }

    @ScreenshotCheck
    @Test(timeout = 300000) //rt-20500 -- html when possible
    @Keywords(keywords="webkit")
    public void unexpectedlyTextSelectionTest() throws InterruptedException, IOException, Throwable {
        final String hw = "Hello, World!";
        typeText(hw);
        for (int i = 0; i < hw.length(); i++) {
            webView.keyboard().pushKey(KeyboardButtons.LEFT);
        }
        webView.keyboard().pushKey(KeyboardButtons.Q);
        webView.keyboard().pushKey(KeyboardButtons.W);
        webView.keyboard().pushKey(KeyboardButtons.E);
        webView.keyboard().pushKey(KeyboardButtons.R);
        webView.keyboard().pushKey(KeyboardButtons.T);
        webView.keyboard().pushKey(KeyboardButtons.Y);
        checkScreenshot("RichTextEditor-unexpectedly-text-selection", webView);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void separatorTest() throws InterruptedException, IOException {
        webView.mouse().click();
        separator.mouse().click();
        check("separator");
        ScreenshotUtils.checkScreenshot("RichTextEditor-separator", webView);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void alignmentTest() throws InterruptedException, IOException, Throwable {
        typeText(ALIGNMENT_TEXT);
        webView.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
        alignment(left, "left");
        alignment(right, "right");
        alignment(center, "center");
        alignment(adjust, "adjust");

        kbdClear();

        Map<Wrap<? extends ToggleButton>, String> map = new LinkedHashMap<Wrap<? extends ToggleButton>, String>();
        map.put(left, "left");
        map.put(right, "right");
        map.put(center, "center");
        map.put(adjust, "adjust");

        for (int j = 0; j < map.size(); j++) {
            Entry<Wrap<? extends ToggleButton>, String> param = (Entry<Wrap<? extends ToggleButton>, String>) map.entrySet().toArray()[j];
            param.getKey().as(Selectable.class, Boolean.class).selector().select(true);
            typeText(param.getValue() + "\n");
        }

        moveToTextBegin();

        for (int j = 0; j < map.size(); j++) {
            Entry<Wrap<? extends ToggleButton>, String> param = (Entry<Wrap<? extends ToggleButton>, String>) map.entrySet().toArray()[j];
            param.getKey().waitProperty(TextControlWrap.SELECTED_PROP_NAME, true);
            webView.keyboard().pushKey(KeyboardButtons.END);
            param.getKey().waitProperty(TextControlWrap.SELECTED_PROP_NAME, true);
            pushKey(KeyboardButtons.DOWN, 1);
        }

        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords = "webkit")
    public void fontSizeTest() throws InterruptedException, IOException, Throwable {
        //We need to change step of scrolling, because list view behavior doesn't allow to select the last item.
        final int val = Math.round(((CaspianDriverFactory) ThemeDriverFactory.getThemeFactory()).getDragDelta());
        ((CaspianDriverFactory) ThemeDriverFactory.getThemeFactory()).setDragDelta(20);
        attributes(fontSize, "size");
        ((CaspianDriverFactory) ThemeDriverFactory.getThemeFactory()).setDragDelta(val);
        reset();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void fontStyleTest() throws InterruptedException, IOException, Throwable {
        attributes(fontStyle, "style", 4);
    }

    @ScreenshotCheck
    @Test(timeout = 300000) //rt -- 20500 update when possible
    @Keywords(keywords="webkit")
    public void paragraphTest() throws InterruptedException, IOException, Throwable {
        final Parent<RadioMenuItem> attributes = paragraph.as(Parent.class, RadioMenuItem.class);
        for (int i = 0; i < attributes.lookup().size(); i++) {
            Wrap<? extends RadioMenuItem> attribute = attributes.lookup().wrap(i);
            attribute.mouse().click();
            typeText(getAttributeText("paragraph", attribute.getControl().getText()) + "\n");
        }

        moveToTextBegin();

        paragraph.waitProperty(Wrap.TEXT_PROP_NAME, attributes.lookup().wrap(0).getControl().getText());
        for (int i = 0; i < attributes.lookup().size(); i++) {
            Wrap<? extends RadioMenuItem> attribute = attributes.lookup().wrap(i);
            paragraph.waitProperty(Wrap.TEXT_PROP_NAME, attribute.getControl().getText());
            pushKey(KeyboardButtons.DOWN, 1);
        }

        check("paragraph");

        checkScreenshot("RichTextEditor-font-paragraph", webView);

        webView.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
        checkScreenshot("RichTextEditor-font-paragraph-selected", webView);

        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void typefaceTest() throws InterruptedException, IOException, Throwable {
        Map<Wrap<? extends ToggleButton>, String> map = new LinkedHashMap<Wrap<? extends ToggleButton>, String>();
        map.put(bold, "bold");
        map.put(italic, "italic");
        map.put(underline, "underline");
        map.put(crossed, "crossed");

        for (Integer i = 0; i < (1 << map.size()); i++) {
            String text = new String();
            for (int j = 0; j < map.size(); j++) {
                Entry<Wrap<? extends ToggleButton>, String> param = (Entry<Wrap<? extends ToggleButton>, String>) map.entrySet().toArray()[j];
                Boolean selected = ((i >> j) & 1) > 0;
                param.getKey().as(Selectable.class, Boolean.class).selector().select(selected);
                text += (selected ? "" : "not ") + param.getValue() + " ";
            }
            typeText(text);
        }

        moveToTextBegin();

        for (int j = 0; j < map.size(); j++) {
            Wrap<? extends ToggleButton> wrap = (Wrap<? extends ToggleButton>) map.keySet().toArray()[j];
            wrap.waitProperty(TextControlWrap.SELECTED_PROP_NAME, false);
        }
        pushKey(KeyboardButtons.RIGHT, 1);
        for (Integer i = 0; i < (1 << map.size()); i++) {
            String text = new String();
            for (int j = 0; j < map.size(); j++) {
                Entry<Wrap<? extends ToggleButton>, String> param = (Entry<Wrap<? extends ToggleButton>, String>) map.entrySet().toArray()[j];
                Boolean selected = ((i >> j) & 1) > 0;
                param.getKey().waitProperty(TextControlWrap.SELECTED_PROP_NAME, selected);
                text += (selected ? "" : "not ") + param.getValue() + " ";
            }
            pushKey(KeyboardButtons.RIGHT, text.length());
        }

        checkScreenshot("RichTextEditor-typeface", webView);

        webView.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
        checkScreenshot("RichTextEditor-typeface-selected", webView);

        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void colorTest() throws InterruptedException, IOException, Throwable {
        for (int i = 0; i < 3; i++) {
            String fore = color(foreground, "foreground", i);
            for (int j = 3; j < 6; j++) {
                String back = color(background, "background", j);
                typeText("[" + fore + "; " + back + "] ");
            }
        }
        check("color");

        checkScreenshot("RichTextEditor-colors", webView);

        webView.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
        checkScreenshot("RichTextEditor-colors-selected", webView);

        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void resetTest() throws InterruptedException, IOException, Throwable {
        for (int i = 0; i < 30; ++i) {
            reset();
        }
        checkScreenshot("RichTextEditor-reset", scene);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void countTest() throws InterruptedException, IOException, Throwable {
        countSequential(numbers, "number");
        Thread.sleep(500);
        countSequential(bullets, "bullet");
        check("count");
        checkScreenshot("RichTextEditor-count-sequential", webView);
        kbdClear();
        check("empty-count");
        checkScreenshot("RichTextEditor-count-clear", webView);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void countChangeTypeTest() throws InterruptedException, IOException, Throwable {
        countSequential(numbers, "number");
        check("count_numbers");
        webView.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
        bullets.mouse().click();
        check("count_bullets");
        webView.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
        numbers.mouse().click();
        check("count_numbers2");
        checkScreenshot("RichTextEditor-count-change-type", webView);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void countRemoveIndentTest() throws InterruptedException, IOException, Throwable {
        countSimple(numbers, "number");
        moveToTextBegin();
        removeIndent.mouse().click();
        check("count_numbers_remove_indent");
        checkScreenshot("RichTextEditor-indent-remove", webView);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Keywords(keywords="webkit")
    public void countIndentTest() throws InterruptedException, IOException, Throwable {
        countSimple(numbers, "number");
        check("simple_numbers");
        moveToTextBegin();
        for (int i = 0; i < SHORT_LIST_LENTGTH; i++) {
            indent.mouse().click();
            check("count_numbers_indent_" + i + "");
            checkScreenshot("RichTextEditor-indent-" + i, webView);
            removeIndent.mouse().click();
            //check("simple_numbers");
            webView.keyboard().pushKey(KeyboardButtons.DOWN);
        }
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000) //platform specific OK except bug 20500
    @Keywords(keywords="webkit")
    public void compositeTest() throws InterruptedException, IOException, Throwable {
        HashMap<Wrap, Integer> map = getAttributesMap();
        typeText(LINE);

        final int SHIFT = 4;
        final int RANGE = SHIFT * (map.size() + 1);
        assertTrue(2 * map.size() * SHIFT < LINE.length());
        for (int i = 0; i < map.size(); i++) {
            int start = i * SHIFT;
            selectRange(start, start + RANGE);
            Entry<Wrap, Integer> param = (Entry<Wrap, Integer>) map.entrySet().toArray()[i];
            int index = param.getValue();
            Wrap wrap = param.getKey();
            if (index == -1) {
                wrap.mouse().click();
            } else {
                if (ColorPicker.class.isAssignableFrom(wrap.getControl().getClass())) {
                    color(wrap, "", index);
                } else {
                    selectAttribute(wrap, index);
                }
            }
        }
        selectRange(0, 0);
        check("composite");
        checkScreenshot("RichTextEditor-composite", webView);
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000) //http://javafx-jira.kenai.com/browse/RT-20554
    @Keywords(keywords="webkit")
    public void copyTest() throws InterruptedException, IOException, Throwable {
        clipboard(copy, "copy");
    }

    @Smoke
    @Test(timeout = 300000) //http://javafx-jira.kenai.com/browse/RT-20554
    @Keywords(keywords="webkit")
    public void cutTest() throws InterruptedException, IOException, Throwable {
        clipboard(cut, "cut");
    }

    @Smoke
    @Test(timeout = 300000)  //http://javafx-jira.kenai.com/browse/RT-20554
    @Keywords(keywords="webkit")
    public void pasteTest() throws InterruptedException, IOException, Throwable {
        typeText(ALIGNMENT_TEXT);
        webView.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
        copy.mouse().click();
        webView.keyboard().pushKey(KeyboardButtons.DELETE);
        paste.mouse().click();
        System.out.println(getHTML());
        check("paste");
    }

//    @ScreenshotCheck
//    @Test(timeout=300000)
//    public void undoRedoTest() throws InterruptedException, IOException, Throwable {
//        typeText(SHORT_LIST);
//        String html = htmlEditor.getControl().getHtmlText();
//        undo.mouse().click();
//        check("empty");
//        redo.mouse().click();
//        htmlEditor.waitState(new State<String>() {
//            public String reached() {
//                return htmlEditor.getControl().getHtmlText();
//            }
//        }, html);
//    }
    protected void setParagraph(int num) throws InterruptedException {
        paragraph.mouse().click();
        final Parent<Node> attributes = (Parent<Node>) getPopupWrap().as(Parent.class, Node.class).lookup(ListView.class).wrap(0).as(Parent.class, Node.class);
        if (num < 0 || num > attributes.lookup(ListCell.class).size()) {
            num = 0;
        }
        attributes.lookup(ListCell.class).wrap(num).mouse().click();
        setDefaultFont();
    }

    protected void setFontSize(int num) throws InterruptedException {
        fontSize.mouse().click();
        final Parent<Node> attributes = (Parent<Node>) getPopupWrap().as(Parent.class, Node.class).lookup(ListView.class).wrap(0).as(Parent.class, Node.class);
        if (num < 0 || num > attributes.lookup(ListCell.class).size()) {
            num = 0;
        }
        attributes.lookup(ListCell.class).wrap(num).mouse().click();
    }

    protected void setFont(int num) throws InterruptedException {
        fontStyle.mouse().click();
        final Parent<Node> attributes = (Parent<Node>) getPopupWrap().as(Parent.class, Node.class).lookup(ListView.class).wrap(0).as(Parent.class, Node.class);
        if (num < 0 || num > attributes.lookup(ListCell.class).size()) {
            num = 0;
        }
        attributes.lookup(ListCell.class).wrap(num).mouse().click();
    }

    protected HashMap<Wrap, Integer> getAttributesMap() {
        HashMap<Wrap, Integer> map = new LinkedHashMap<Wrap, Integer>();
        map.put(fontSize, 4);
        map.put(fontStyle, 3);
        //map.put(paragraph, 2);
        map.put(bold, -1);
        map.put(italic, -1);
        map.put(underline, -1);
        map.put(crossed, -1);
        map.put(foreground, 3);
        map.put(background, 4);
        return map;
    }

    protected void clipboard(final Wrap<? extends Node> wrap, String html) throws IOException {
        typeText(ALIGNMENT_TEXT);
        webView.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
        wrap.waitState(new State<Boolean>() {

            public Boolean reached() {
                return new GetAction<Boolean>() {

                    @Override
                    public void run(Object... parameters) {
                        setResult(wrap.getControl().isDisabled());
                    }
                }.dispatch(Root.ROOT.getEnvironment());
            }
        }, false);
        wrap.mouse().click();
        checkClipboard(html);
    }

    protected void selectAttribute(Wrap<? extends MenuButton> wrap, int index) {
        final Parent<RadioMenuItem> attributes = wrap.as(Parent.class, RadioMenuItem.class);
        Wrap<? extends RadioMenuItem> attribute = attributes.lookup().wrap(index);
        attribute.mouse().click();
    }

    protected void selectRange(int min, int max) {
        setPos(min);
        setPos(max, KeyboardModifiers.SHIFT_DOWN_MASK);
    }
    int current_pos = -1;

    protected void setPos(int pos, KeyboardModifiers... mdfs) {
        if (current_pos == -1 || mdfs.length == 0) {
            moveToTextBegin();
            current_pos = 0;
        }
        if (current_pos < pos) {
            pushKey(KeyboardButtons.RIGHT, pos - current_pos, mdfs);
        } else {
            pushKey(KeyboardButtons.LEFT, current_pos - pos, mdfs);
        }
        current_pos = pos;
    }

    public void countSequential(Wrap wrap, String name) throws InterruptedException, IOException, Throwable {
        wrap.mouse().click();
        typeText(SHORT_LIST);
        typeText("\n");
        wrap.mouse().click();
        typeText(SHORT_LIST);
        typeText("\n\n");
        wrap.mouse().click();
        typeText(SHORT_LIST);
    }

    public void countSimple(Wrap wrap, String name) throws InterruptedException, IOException, Throwable {
        wrap.mouse().click();
        setDefaultFont();
        typeText(SHORT_LIST);
        typeText("\n");
    }

    /**
     * Selects color from dropdown.
     * @param control - part of color picker visible on a panel.
     * @param name - just a name
     * @param num - index of the color
     * @return
     * @throws InterruptedException
     * @throws IOException
     * @throws Throwable
     */
    protected String color(Wrap control, String name, int num) throws InterruptedException, IOException, Throwable {
        control.mouse().click();
        Wrap<? extends Scene> drop_scene = Root.ROOT.lookup(new LookupCriteria<Scene>() {

            public boolean check(Scene sc) {
                return sc != scene.getControl() && sc.getWindow().isShowing();
            }
        }).wrap();
        Parent<Node> drop_scene_parent = drop_scene.as(Parent.class, Node.class);
        Lookup<Node> color_lookup = drop_scene_parent.lookup(new LookupCriteria<Node>() {

            public boolean check(Node cntrl) {
                return cntrl.getStyleClass().contains("color-square") || cntrl.getStyleClass().contains("color-square-selected");
            }
        });

        Wrap wrap = color_lookup.wrap(num);
        double[] colors = getColors(wrap.getScreenImage());
        wrap.mouse().click();
        return name + ": (" + colors[0] + ", " + colors[1] + ", " + colors[2] + ")";
    }

    public void attributes(Wrap<? extends ComboBox> wrap, String name, int... counter) throws InterruptedException, IOException, Throwable {
        int c = 0;
        for (int i = 0; i < wrap.as(Selectable.class).getStates().size(); i++) {
            if (counter.length > 0) {
                if (c++ >= counter[0]) {
                    break;
                }
            }
            wrap.as(Selectable.class).selector().select(wrap.as(Selectable.class).getStates().get(i));
            typeText(getAttributeText(name, wrap.as(Selectable.class).getStates().get(i).toString()));
        }

        moveToTextBegin();
        checkComboBoxValue(wrap, wrap.as(Selectable.class).getStates().get(0).toString());
        pushKey(KeyboardButtons.RIGHT, 1);
        c = 0;
        for (int i = 0; i < wrap.as(Selectable.class).getStates().size(); i++) {
            if (counter.length > 0) {
                if (c++ >= counter[0]) {
                    break;
                }
            }
            checkComboBoxValue(wrap, wrap.as(Selectable.class).getStates().get(i).toString());
            pushKey(KeyboardButtons.RIGHT, getAttributeText(name, wrap.as(Selectable.class).getStates().get(i).toString()).length());
        }

        check(name);

        checkScreenshot("RichTextEditor-font-" + name, webView);

        webView.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
        checkScreenshot("RichTextEditor-font-" + name + "-selected", webView);

        throwScreenshotError();
    }

    protected void checkComboBoxValue(final Wrap<? extends ComboBox> wrap, String expectedValue) {
        wrap.waitState(new State<String>() {
            public String reached() {
                return new GetAction<String>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(wrap.getControl().getValue().toString());
                    }
                }.dispatch(Root.ROOT.getEnvironment());
            }
        }, expectedValue);
    }

    protected void alignment(Wrap wrap, String name) throws InterruptedException, IOException, Throwable {
        wrap.mouse().click();
        check(name + "_alignment");
        checkScreenshot("RichTextEditor-alignment-" + name, webView);
    }

    private List<MenuItem> getFonts() {
        return new GetAction<List<MenuItem>>() {

            @Override
            public void run(Object... os) throws Exception {
                ArrayList res = new ArrayList();
                int size = fontStyle.as(Parent.class, MenuItem.class).lookup().size();
                for (int i = 0; i < size; i++) {
                    res.add(fontStyle.as(Parent.class, MenuItem.class).lookup().get(i));
                }
                setResult(res);

                //setResult(((MenuButton)fontStyle.getControl()).getItems().listIterator());
            }
        }.dispatch(htmlEditor.getEnvironment());
    }

    protected void setFont(String name) {
        fontStyle.as(Selectable.class).selector().select(name);
//       ListIterator<MenuItem> li = getFonts().listIterator();
//       MenuItem mi;
//       int i = 0;
//       do {
//        mi = li.next();
//        i++;
//       } while (!mi.getText().equals(name));
//       setFont(--i);
    }

    private void setDefaultFont() {
        setFont(DEFAULT_TEXT_STYLE);
    }

    void reset() {
        resetBtn.mouse().click();

        webView = parent.lookup(WebView.class).wrap();
        webView.mouse().click();

        htmlEditor = parent.lookup(HTMLEditor.class).wrap();
        htmlEditorAsParent = htmlEditor.as(Parent.class, Node.class);

        cut = parent.lookup(Button.class).wrap(0);
        copy = parent.lookup(Button.class).wrap(1);
        paste = parent.lookup(Button.class).wrap(2);
//        undo = parent.lookup(Button.class).wrap(3);
//        redo = parent.lookup(Button.class).wrap(4);
        left = parent.lookup(ToggleButton.class).wrap(0);
        center = parent.lookup(ToggleButton.class).wrap(1);
        right = parent.lookup(ToggleButton.class).wrap(2);
        adjust = parent.lookup(ToggleButton.class).wrap(3);
        bullets = parent.lookup(ToggleButton.class).wrap(4);
        numbers = parent.lookup(ToggleButton.class).wrap(5);
        indent = parent.lookup(Button.class).wrap(3);
        removeIndent = parent.lookup(Button.class).wrap(4);
        separator = parent.lookup(Button.class).wrap(5);

        paragraph = parent.lookup(ComboBox.class).wrap(0);
        fontStyle = parent.lookup(ComboBox.class).wrap(1);
        fontSize = parent.lookup(ComboBox.class).wrap(2);

        bold = parent.lookup(ToggleButton.class).wrap(6);
        italic = parent.lookup(ToggleButton.class).wrap(7);
        underline = parent.lookup(ToggleButton.class).wrap(8);
        crossed = parent.lookup(ToggleButton.class).wrap(9);

        foreground = parent.lookup(ColorPicker.class).wrap(0);
        background = parent.lookup(ColorPicker.class).wrap(1);
        setDefaultFont();
    }

    private String getHTMLName(String name) {
        String plsSpecName = PLATFORM_TAG + name;
        File plSpec;
        URL url = getClass().getResource(plsSpecName + ".html");
        if (url != null) {
            plSpec = new File(url.getFile());
            if (plSpec != null && plSpec.exists()) {
                return plsSpecName;
            }
        }
        //If no win8, use other win.
        if (PLATFORM_TAG.contains("win8_") && url == null) {
            plsSpecName = "win_" + name;
            url = getClass().getResource(plsSpecName + ".html");
            if (url != null) {
                plSpec = new File(url.getFile());
                if (plSpec != null && plSpec.exists()) {
                    return plsSpecName;
                }
            }
        }

        System.out.println("there is no platform specific html for name <" + name + ">.");
        return name;
    }

    protected void check(final String html) throws IOException, FileNotFoundException {
        System.out.println(readResource(getHTMLName(html) + ".html"));
        try {
            htmlEditor.waitState(new State<String>() {

                public String reached() {
                    System.out.println(htmlEditor.getControl().getHtmlText());
                    return htmlEditor.getControl().getHtmlText();
                }
            }, readResource(getHTMLName(html) + ".html"));
        } finally {
            Writer out = new OutputStreamWriter(new FileOutputStream(GoldenImageManager.getGoldenPath(html, ".html")));
            try {
                out.write("Found : " + htmlEditor.getControl().getHtmlText() + "\n");
                out.write("Expected : " + readResource(getHTMLName(html) + ".html") + "\n");
            } finally {
                out.close();
            }
        }
    }

    protected void checkClipboard(String html) throws IOException {
        try {
            htmlEditor.waitState(new State<String>() {

                public String reached() {
                    return getClipboardHTML();
                }
            }, readResource(getHTMLName(html) + ".html"));
        } finally {
            Writer out = new OutputStreamWriter(new FileOutputStream(GoldenImageManager.getGoldenPath(html, ".html")));
            try {
                out.write(getClipboardHTML());
            } finally {
                out.close();
            }
        }
    }

    protected void typeText(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                webView.keyboard().pushKey(KeyboardButtons.ENTER);
            } else {
                webView.keyboard().typeChar(text.charAt(i), ZERO_TIMEOUT);
            }
        }
    }

    protected String readResource(String name) throws IOException {
        URL url = RichTextEditorTest.class.getResource(name);
        assertNotNull(url);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder result = new StringBuilder();
        while (true) {
            int c = reader.read();
            if (c == -1) {
                break;
            }
            result.append((char) c);
        }
        return result.toString();
    }

    protected String getClipboardHTML() {
        return new GetAction<String>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(Clipboard.getSystemClipboard().getHtml());
            }
        }.dispatch(htmlEditor.getEnvironment());
    }

    protected String getHTML() {
        return new GetAction<String>() {

            @Override
            public void run(Object... parameters) {
                setResult(htmlEditor.getControl().getHtmlText());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void setHTML(final String html) {
        new GetAction() {

            @Override
            public void run(Object... parameters) {
                htmlEditor.getControl().setHtmlText(html);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected String getAttributeText(String attribute, String value) {
        return attribute + " " + value + " ";
    }

    protected void pushKey(KeyboardButton kb, int count, KeyboardModifiers... mods) {
        for (int k = 0; k < count; k++) {
            webView.keyboard().pushKey(ZERO_TIMEOUT, kb, mods);
        }
    }

    protected void kbdClear() {
        webView.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
        webView.keyboard().pushKey(KeyboardButtons.DELETE);
    }

    protected void moveToTextBegin() {
        if (Utils.isMacOS()) {
            webView.keyboard().pushKey(KeyboardButtons.UP, KeyboardModifiers.META_DOWN_MASK);
        } else {
            webView.keyboard().pushKey(KeyboardButtons.HOME, KeyboardModifiers.CTRL_DOWN_MASK);
        }
    }

    private Wrap<? extends Scene> getPopupWrap() throws InterruptedException {
        Wrap<? extends Scene> temp;
        try {
            temp = Root.ROOT.lookup(new ByWindowType(PopupWindow.class)).lookup(Scene.class).wrap(0);
        } catch (Exception e) {
            return null;
        }
        return temp;
    }
    final static String SOME_TEXT = "some text";
    final static String HELLO_WORLD = "Hello, World!";
    final static String SOME_OTHER_TEXT = "some more text";
    final static String HTML_SAMPLE_TEXT = "<html><head></head><body contenteditable=\"true\"><div style=\"text-align: left;\"><div style=\"text-align: left;\">\"<b>Sing, O goddess, the anger of<i> </i></b><i>Achilles <u>son </u></i><u>of Pele<strike>us, that brou</strike></u>ght&nbsp;</div><hr><h5>c<font class=\"Apple-style-span\" face=\"CordiaUPC\">ountless ills up</font>o<font class=\"Apple-style-span\" size=\"4\">n the Achaeans. Many<font class=\"Apple-style-span\" face=\"'Comic Sans MS'\"> </font></font><font class=\"Apple-style-span\" face=\"'Comic Sans MS'\">a brave sou</font><font class=\"Apple-style-span\" face=\"David\">l <font class=\"Apple-style-span\" size=\"4\">di</font></font><font class=\"Apple-style-span\" face=\"David\" style=\"font-size: large; \">d it send hurrying down to Hades, and many a hero did it yield a prey to dogs and </font><font class=\"Apple-style-span\" face=\"'DejaVu Sans Mono'\" style=\"font-size: large; \">vultures, for so</font><font class=\"Apple-style-span\" size=\"4\"> were the counsels of Jove fulfilled from the day on which the son of Atr</font><font class=\"Apple-style-span\" face=\"BrowalliaUPC\" size=\"3\">eus, king of men, and great A</font><font class=\"Apple-style-span\" size=\"4\">chilles, first fell out with one another.\";</font></h5><p><br></p></div></body></html>";
    final static Timeout ZERO_TIMEOUT = new Timeout("KbdType", 0);
    final static String ALIGNMENT_TEXT = "Line 1 line 1 line 1 line 1 line 1 line 1 line 1 line 1 line 1\n"
            + "Line 2 line 2 line 2 line 2 line 2 line 2 line 2 line 2\n"
            + "Line 3 line 3 line 3 line 3 line 3 line 3 line 3\n"
            + "Line 4 line 4 line 4 line 4 line 4 line 4\n"
            + "Line 5 line 5 line 5 line 5 line 5\n"
            + "Line 6 line 6 line 6 line 6\n"
            + "Sing, O goddess, the anger of Achilles son of Peleus, that brought countless ills upon the Achaeans. Many a brave soul did it send hurrying down to Hades, and many a hero did it yield a prey to dogs and vultures, for so were the counsels of Jove fulfilled from the day on which the son of Atreus, king of men, and great Achilles, first fell out with one another.";
    final static String LIST = "Line 1 line 1 line 1 line 1 line 1 line 1 line 1 line 1 line 1 line 1 line 1 line 1 line 1 line 1\n"
            + "Line 2 line 2 line 2 line 2 line 2 line 2 line 2 line 2 line 2 line 2 line 2 line 2 line 2 line 2\n"
            + "Line 3 line 3 line 3 line 3 line 3 line 3 line 3 line 3 line 3 line 3 line 3 line 3 line 3 line 3\n"
            + "Line 4 line 4 line 4 line 4 line 4 line 4 line 4 line 4 line 4 line 4 line 4 line 4 line 4 line 4\n"
            + "Line 5 line 5 line 5 line 5 line 5 line 5 line 5 line 5 line 5 line 5 line 5 line 5 line 5 line 5\n"
            + "Line 6 line 6 line 6 line 6 line 6 line 6 line 6 line 6 line 6 line 6 line 6 line 6 line 6 line 6\n";
    final static int SHORT_LIST_LENTGTH = 4;
    final static int HEADING1 = 1;
    final static int HEADING2 = 2;
    final static int HEADING6 = 6;
    final static int XX_LARGE_FONT_SIZE = 6;
    final static String SHORT_LIST = "Line 1\n"
            + "Line 2\n"
            + "Line 3\n"
            + "Line 4\n";
    final static String LINE = "Item 01 item 02 item 03 item 04 item 05 item 06 item 07 item 08 item 09 item 10 item 11";
}
