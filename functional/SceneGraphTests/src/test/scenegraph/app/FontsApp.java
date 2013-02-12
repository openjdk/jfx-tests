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
package test.scenegraph.app;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;

/**
 *
 * @author Sergey Grinev
 */
public class FontsApp extends BasicButtonChooserApp {

    public static final String FONT_RESOURCE = "/test/scenegraph/resources/Courgette-Regular.woff";
    public static final String FONT_URL = "http://jcsqe.ru.oracle.com/font/Courgette-Regular.woff";
    public static final double FONT_SIZE = 24d;
    public static final String TEST_PHRASE = "The quick brown fox jumps over the lazy dog 0123456789";
    private Pane pageContent;
    private TestNode root;

    protected Pane getPageContent() {
        return pageContent;
    }

    private class RunTestNode extends TestNode {

        protected Runnable runnable;

        public RunTestNode(Runnable runnable) {
            this.runnable = runnable;
        }
    }

    private void addPage(String name, Runnable runnable) {
        TestNode page = new RunTestNode(runnable) {
            @Override
            public Node drawNode() {
                pageContent = new Pane();
                runnable.run();
                return getPageContent();
            }
        };
        root.add(page, name);
    }

    public static enum Pages {

        monospace, serif, sansserif, cursive, fantasy,
        fontGetters, enums, alignment, undelineAndOrigin,
        woff, woffByUrl, woffByInputStream
    }

    public FontsApp() {
        super(710, 510, "Fonts", false);
        InputStream is = getClass().getResourceAsStream(FONT_RESOURCE);
        fontByInputStream = Font.loadFont(is, FONT_SIZE);
        fontByUrl = Font.loadFont(FONT_URL, FONT_SIZE);
    }
    private static final Map<String, String> fontFamilies = new HashMap<String, String>() {
        {
            put("monospace", "Courier New");
            put("serif", "Times New Roman");
            put("sansserif", "Arial");
            put("cursive", "Comic Sans MS");
            put("fantasy", "Impact");
        }
    };

    @Override
    protected void initPredefinedFont() {
    }

    public TestNode setup() {
        root = new TestNode();
        for (final String name : fontFamilies.keySet()) {
            final String family = fontFamilies.get(name);

            addPage(name, new Runnable() {
                public void run() {
                    // prepare fonts
                    final Map<String, Font> fonts = new TreeMap<String, Font>();

                    for (int size : new int[]{12, 24, 36}) {
                        fonts.put(family + " " + size, Font.font(family, size));
                        fonts.put(family + " " + size + " BOLD", Font.font(family, FontWeight.BOLD, size));
                        fonts.put(family + " " + size + " ITALIC", Font.font(family, FontPosture.ITALIC, size));
                        fonts.put(family + " " + size + " BOLD ITALIC", Font.font(family, FontWeight.BOLD, FontPosture.ITALIC, size));
                    }

                    VBox lines = new VBox();
                    for (final String name : fonts.keySet()) {
                        Text tmpTxt = new Text(name);
                        tmpTxt.setId(name);
                        tmpTxt.setFont(fonts.get(name));
                        lines.getChildren().add(tmpTxt);
                    }
                    getPageContent().getChildren().add(lines);
                }
            });
        }
        addPage(Pages.fontGetters.name(), new Runnable() {
            public void run() {

                VBox lines = new VBox();
                getPageContent().getChildren().add(lines);

                List<String> fontFamilies = Font.getFamilies();
                lines.getChildren().add(new Text("last font family: " + fontFamilies.get(fontFamilies.size() - 1)));

                List<String> fontF = Font.getFontNames(fontFamilies.get(fontFamilies.size() - 1));
                lines.getChildren().add(new Text("first font in last family: " + fontF.get(0)));

                List<String> fontNames = Font.getFontNames();
                String lastfontname = fontNames.get(fontNames.size() - 1);
                lines.getChildren().add(new Text("last font: " + lastfontname));

                Font font1 = new Font(30);
                Text text1 = new Text("Test Font(30) Ctor");
                text1.setFont(font1);
                lines.getChildren().add(text1);

                //lines.getChildren().add(new Text("LetterSpacing of default font: " + font1.getLetterSpacing()));

                //lines.getChildren().add(new Text("getPosition of default font: " + font1.getPosition().toString()));

                lines.getChildren().add(new Text("getStyle of default font: " + font1.getStyle().toString()));

                Font lastFont = new Font(lastfontname, 14);
                /*
                 lines.getChildren().add(new Text("isAutoKern: " + lastFont.isAutoKern() ));
                 lines.getChildren().add(new Text("isEmbolden: " + lastFont.isEmbolden() ));
                 lines.getChildren().add(new Text("isLigatures: " + lastFont.isLigatures() ));
                 lines.getChildren().add(new Text("isOblique: " + lastFont.isOblique() ));
                 */
                lines.getChildren().add(new Text("toString: " + lastFont.toString()));


            }
        });
        addPage(Pages.enums.name(), new Runnable() {
            public void run() {

                VBox lines = new VBox();
                getPageContent().getChildren().add(lines);

                // VPos
                {
                    VPos testValue;
                    for (VPos c : VPos.values()) {
                        testValue = VPos.valueOf(c.toString());
                        lines.getChildren().add(new Text("VPos: " + c.toString()));
                    }

                    boolean exTest = false;
                    try {
                        testValue = VPos.valueOf(null);
                    } catch (NullPointerException e) {
                        exTest = true;
                    }
                    lines.getChildren().add(new Text("VPos exception test1: " + (exTest ? "passed" : "failed")));

                    exTest = false;
                    try {
                        testValue = VPos.valueOf("blah-blah-blah");
                    } catch (IllegalArgumentException e) {
                        exTest = true;
                    }
                    lines.getChildren().add(new Text("VPos exception test2: " + (exTest ? "passed" : "failed")));
                }

                // TextBoundsType
                {
                    TextBoundsType testValue;
                    for (TextBoundsType c : TextBoundsType.values()) {
                        testValue = TextBoundsType.valueOf(c.toString());
                        lines.getChildren().add(new Text("TextBoundsType: " + c.toString()));
                    }

                    boolean exTest = false;
                    try {
                        testValue = TextBoundsType.valueOf(null);
                    } catch (NullPointerException e) {
                        exTest = true;
                    }
                    lines.getChildren().add(new Text("TextBoundsType exception test1: " + (exTest ? "passed" : "failed")));

                    exTest = false;
                    try {
                        testValue = TextBoundsType.valueOf("blah-blah-blah");
                    } catch (IllegalArgumentException e) {
                        exTest = true;
                    }
                    lines.getChildren().add(new Text("TextBoundsType exception test2: " + (exTest ? "passed" : "failed")));
                }
                // TextAlignment
                {
                    TextAlignment testValue;
                    for (TextAlignment c : TextAlignment.values()) {
                        testValue = TextAlignment.valueOf(c.toString());
                        lines.getChildren().add(new Text("TextAlignment: " + c.toString()));
                    }

                    boolean exTest = false;
                    try {
                        testValue = TextAlignment.valueOf(null);
                    } catch (NullPointerException e) {
                        exTest = true;
                    }
                    lines.getChildren().add(new Text("TextAlignment exception test1: " + (exTest ? "passed" : "failed")));

                    exTest = false;
                    try {
                        testValue = TextAlignment.valueOf("blah-blah-blah");
                    } catch (IllegalArgumentException e) {
                        exTest = true;
                    }
                    lines.getChildren().add(new Text("TextAlignment exception test2: " + (exTest ? "passed" : "failed")));
                }
                // FontPosture
                {
                    FontPosture testValue;
                    for (FontPosture c : FontPosture.values()) {
                        testValue = FontPosture.valueOf(c.toString());
                        lines.getChildren().add(new Text("FontPosture: " + c.toString()));
                    }

                    boolean exTest = false;
                    try {
                        testValue = FontPosture.valueOf(null);
                    } catch (NullPointerException e) {
                        exTest = true;
                    }
                    lines.getChildren().add(new Text("FontPosture exception test1: " + (exTest ? "passed" : "failed")));

                    exTest = false;
                    try {
                        testValue = FontPosture.valueOf("blah-blah-blah");
                    } catch (IllegalArgumentException e) {
                        exTest = true;
                    }
                    lines.getChildren().add(new Text("FontPosture exception test2: " + (exTest ? "passed" : "failed")));
                }
                // FontPosition
                /*
                 {
                 FontPosition testValue;
                 for (FontPosition c : FontPosition.values()) {
                 testValue = FontPosition.valueOf(c.toString());
                 lines.getChildren().add(new Text("FontPosition: " + c.toString()));
                 }

                 boolean exTest = false;
                 try {
                 testValue = FontPosition.valueOf(null);
                 }
                 catch (NullPointerException e) {
                 exTest = true;
                 }
                 lines.getChildren().add(new Text("FontPosition exception test1: " + (exTest?"passed":"failed")));

                 exTest = false;
                 try {
                 testValue = FontPosition.valueOf("blah-blah-blah");
                 }
                 catch (IllegalArgumentException e) {
                 exTest = true;
                 }
                 lines.getChildren().add(new Text("FontPosition exception test2: " + (exTest?"passed":"failed")));
                 }
                 *
                 */
                // FontWeight
                {
                    FontWeight testValue;
                    for (FontWeight c : FontWeight.values()) {
                        testValue = FontWeight.valueOf(c.toString());
                        //lines.getChildren().add(new Text("FontPosition: " + c.toString()));
                    }

                    boolean exTest = false;
                    try {
                        testValue = FontWeight.valueOf(null);
                    } catch (NullPointerException e) {
                        exTest = true;
                    }
                    lines.getChildren().add(new Text("FontWeight exception test1: " + (exTest ? "passed" : "failed")));

                    exTest = false;
                    try {
                        testValue = FontWeight.valueOf("blah-blah-blah");
                    } catch (IllegalArgumentException e) {
                        exTest = true;
                    }
                    lines.getChildren().add(new Text("FontWeight exception test2: " + (exTest ? "passed" : "failed")));
                }


            }
        });
        addPage(Pages.alignment.name(), new Runnable() {
            public void run() {

                VBox lines = new VBox();
                getPageContent().getChildren().add(lines);

                List<String> fontFamilies = Font.getFamilies();
                lines.getChildren().add(new Text("last font family: " + fontFamilies.get(fontFamilies.size() - 1)));

                List<String> fontF = Font.getFontNames(fontFamilies.get(fontFamilies.size() - 1));
                lines.getChildren().add(new Text("first font in last family: " + fontF.get(0)));

                Font w = new Font(fontF.get(0), 12);
                Text text1 = new Text("Test Font");
                text1.setFont(w);
                lines.getChildren().add(text1);

                Text t = new Text(20, 50, "This is a test X=20 Y=50"); // what does these digits mean?
                if (20 != t.getX()) {
                    reportGetterFailure("Text.getX()");
                }
                if (50 != t.getY()) {
                    reportGetterFailure("Text.getY()");
                }
                lines.getChildren().add(t);

                Text t2 = new Text();
                final String strTxt = "The quick brown fox jumps over the lazy dog";
                t2.setFont(new Font(20));
                t2.setWrappingWidth(200);
                if (200 != t2.getWrappingWidth()) {
                    reportGetterFailure("Text.getWrappingWidth()");
                }
                t2.setText(strTxt);
                if (!strTxt.equals(t2.getText())) {
                    reportGetterFailure("Text.getText()");
                }
                t2.setTextAlignment(TextAlignment.JUSTIFY);
                if (TextAlignment.JUSTIFY != t2.getTextAlignment()) {
                    reportGetterFailure("Text.getTextAlignment()");
                }
                lines.getChildren().add(t2);

                Text t3 = new Text();
                t3.setFont(new Font(20));
                t3.setWrappingWidth(200);
                t3.setText(strTxt);
                t3.setTextAlignment(TextAlignment.CENTER);
                lines.getChildren().add(t3);

                Text t4 = new Text();
                t4.setFont(new Font(20));
                t4.setWrappingWidth(200);
                t4.setText(strTxt);
                t4.setTextAlignment(TextAlignment.LEFT);
                lines.getChildren().add(t4);

                Text t5 = new Text();
                t5.setFont(new Font(20));
                t5.setWrappingWidth(200);
                t5.setText(strTxt);
                t5.setTextAlignment(TextAlignment.RIGHT);
                lines.getChildren().add(t5);


            }
        });
        addPage(Pages.undelineAndOrigin.name(), new Runnable() {
            public void run() {

                VBox lines = new VBox();
                getPageContent().getChildren().add(lines);

                List<String> fontFamilies = Font.getFamilies();
                lines.getChildren().add(new Text("last font family: " + fontFamilies.get(fontFamilies.size() - 1)));

                List<String> fontF = Font.getFontNames(fontFamilies.get(fontFamilies.size() - 1));
                lines.getChildren().add(new Text("first font in last family: " + fontF.get(0)));

                Font w = new Font(fontF.get(0), 12);
                Text text1 = new Text("Test Font and baseline offset ");
                text1.setFont(w);
                if (w != text1.getFont()) {
                    reportGetterFailure("Text.getFont()");
                }
                double baselineOffset = text1.getBaselineOffset();
                text1.setBoundsType(TextBoundsType.VISUAL);
                if (TextBoundsType.VISUAL != text1.getBoundsType()) {
                    reportGetterFailure("Text.getBoundsType()");
                }
                lines.getChildren().add(text1);

                HBox hbox = new HBox();
                lines.getChildren().add(hbox);

                Text t2 = new Text();
                t2.setFont(new Font(20));
                t2.setText("[" + baselineOffset + "] ABC");
                t2.setTextOrigin(VPos.TOP);
                if (VPos.TOP != t2.getTextOrigin()) {
                    reportGetterFailure("Text.getTextOrigin()");
                }
                if (true == t2.isStrikethrough()) {
                    reportGetterFailure("Text.isStrikethrough()");
                }
                if (true == t2.isUnderline()) {
                    reportGetterFailure("Text.isUnderline()");
                }
                hbox.getChildren().add(t2);

                Text t3 = new Text();
                t3.setFont(new Font(20));
                t3.setText("DEF");
                t3.setStrikethrough(true);
                if (false == t3.isStrikethrough()) {
                    reportGetterFailure("Text.isStrikethrough()");
                }
                t3.setTextOrigin(VPos.BASELINE);
                hbox.getChildren().add(t3);

                Text t4 = new Text();
                t4.setFont(new Font(20));
                t4.setText("GHI");
                t4.setTextOrigin(VPos.BOTTOM);
                t4.setUnderline(true);
                if (false == t4.isUnderline()) {
                    reportGetterFailure("Text.isUnderline()");
                }
                hbox.getChildren().add(t4);


            }
        });
        

        addPage( Pages.woffByUrl.name(), new Runnable() {
            public void run() {
                VBox vBox = new VBox();
                getPageContent().getChildren().add(vBox);

                Text text = new Text(TEST_PHRASE);
                text.setFont(fontByUrl);

                vBox.getChildren().add(text);
            }
        });

        addPage( Pages.woffByInputStream.name(), new Runnable() {
            public void run() {
                VBox vBox = new VBox();
                getPageContent().getChildren().add(vBox);

                Text text = new Text(TEST_PHRASE);
                text.setFont(fontByInputStream);

                vBox.getChildren().add(text);
            }
        });
        return root;

    }

    public static void main(String args[]) {
        test.javaclient.shared.Utils.launch(FontsApp.class, args);
    }
    private Font fontByInputStream;
    private Font fontByUrl;
}
