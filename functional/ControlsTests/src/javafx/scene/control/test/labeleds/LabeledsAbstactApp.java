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

import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.test.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 * @author Andrey Glushchenko
 */
public abstract class LabeledsAbstactApp extends BasicButtonChooserApp {

    protected static int WIDTH = 800;
    protected static int HEIGHT = 400;
    public static final String DEFAULT = "default";
    public static final String CONTROL = "control";
    public static final String GOLDEN = "goldentext";
    public static final String ELLIPSIS_TEXT = "ggggggggggggggggggggggggggggggggggggggg";
    public static final String ELLIPSIS_ELLIPSIS = "WWW";
    final static String MULTILINE_TEXT = "first line first line first line first line first line"
            + "\nsecond line second line second line second line second line"
            + "\nthird line third line third line third line third line"
            + "\nfourth line fourth line fourth line fourth line fourth line";
    final static String WRAPPED_TEXT = "wrapped wrappped wrapppped wrappppped wrapppppped wrappppped wrapppped wrappped wrapped";
    public static final String WRAPPED = "wrapped wrapped wrapped wrapped wrapped wrapped";
    private boolean enableBorder = true;

    public static enum LabeledsPages {

        Constructors,
        setText,
        setContentDisplay,
        setUnderline,
        setWrapText,
        setFont,
        setGraphicTextGap,
        setAlignment,
        setTextAlignment,
        setTextOverrunSingleLine,
        setTextOverrunMultiLine,
        setTextOverrunSingleLineWrapped,
        setTextOverrunMultiLineWrapped,
        setEllipsisString,
    }

    public LabeledsAbstactApp(String title) {
        super(WIDTH, HEIGHT, title, false);
    }

    protected void enableBorder(boolean enableBorder) {
        this.enableBorder = enableBorder;
    }

    protected void defFill(TestNode root) {
        List<Change<Labeled>> list = new LinkedList<Change<Labeled>>();


        root.add(new ConstructorPage(getConstructorPage()), LabeledsPages.Constructors.name());

        list.add(new Change<Labeled>(LabeledsPages.setText.name()) {
            public void apply(Labeled labeled) {
                labeled.setText(DEFAULT);
                if (!labeled.getText().equals(DEFAULT)) {
                    reportGetterFailure(getMarker());
                }
            }
        });
        root.add(new Page(list), LabeledsPages.setText.name());

        for (final ContentDisplay content_display : ContentDisplay.values()) {
            list.add(new Change<Labeled>(content_display.toString()) {
                public void apply(Labeled labeled) {
                    Utils.LayoutSize layout = new Utils.LayoutSize(100, 40, 100, 40, 100, 40);
                    layout.apply(labeled);
                    Rectangle rectangle = new Rectangle(10, 10, Color.rgb(200, 100, 100));
                    labeled.setGraphic(rectangle);
                    if (labeled.getGraphic() != rectangle) {
                        reportGetterFailure("labeled.getGraphic()");
                    }
                    labeled.setContentDisplay(content_display);
                    if (labeled.getContentDisplay() != content_display) {
                        reportGetterFailure("labeled.getContentDisplay()");
                    }
                }
            });
        }
        root.add(new Page(list), LabeledsPages.setContentDisplay.name());

        list.add(new Change<Labeled>(LabeledsPages.setUnderline.name()) {
            public void apply(Labeled labeled) {
                labeled.setUnderline(true);
                if (labeled.isUnderline() != true) {
                    reportGetterFailure("labeled.isUnderline()");
                }
            }
        });
        root.add(new Page(list), LabeledsPages.setUnderline.name());

        list.add(new Change<Labeled>(LabeledsPages.setWrapText.name()) {
            public void apply(Labeled labeled) {
                labeled.setText(WRAPPED);
                Utils.LayoutSize layout = new Utils.LayoutSize(0, 0, 100, 100, 100, 200);
                layout.apply(labeled);
                labeled.setWrapText(true);
                if (labeled.isWrapText() != true) {
                    reportGetterFailure("labeled.isWrapText()");
                }
            }
        });
        root.add(new Page(list), LabeledsPages.setWrapText.name());

        list.add(new Change<Labeled>(LabeledsPages.setFont.name()) {
            public void apply(Labeled labeled) {
                Utils.LayoutSize layout = new Utils.LayoutSize(150, 25, 150, 25, 150, 25);
                layout.apply(labeled);
                labeled.setText("changed font");
                Font font = new Font(16);
                labeled.setFont(font);
                if (labeled.getFont() != font) {
                    reportGetterFailure(getMarker());
                }
            }
        });
        root.add(new Page(list), LabeledsPages.setFont.name());

        list.add(new Change<Labeled>(CONTROL) {
            public void apply(Labeled labeled) {
                Utils.LayoutSize layout = new Utils.LayoutSize(150, 25, 150, 25, 150, 25);
                layout.apply(labeled);
                labeled.setText(ELLIPSIS_TEXT);
                labeled.setId(LabeledsPages.setEllipsisString.name() + CONTROL);
                labeled.setEllipsisString(ELLIPSIS_ELLIPSIS);
                if (!labeled.getEllipsisString().equals(ELLIPSIS_ELLIPSIS)) {
                    reportGetterFailure(getMarker());
                }
            }
        });
        list.add(new Change<Labeled>(GOLDEN) {
            public void apply(Labeled labeled) {
                Utils.LayoutSize layout = new Utils.LayoutSize(150, 25, 150, 25, 150, 25);
                layout.apply(labeled);
                labeled.setId(LabeledsPages.setEllipsisString.name() + GOLDEN);
                labeled.setText(getEllipsingString());
            }
        });
        root.add(new Page(list), LabeledsPages.setEllipsisString.name());

        list.add(new Change<Labeled>("10") {
            public void apply(Labeled labeled) {
                Rectangle rectangle = new Rectangle(10, 10, Color.rgb(200, 100, 100));
                labeled.setGraphic(rectangle);
                labeled.setGraphicTextGap(10);
                if (labeled.getGraphicTextGap() != 10) {
                    reportGetterFailure(getMarker());
                }
            }
        });
        root.add(new Page(list), LabeledsPages.setGraphicTextGap.name());

        for (final Pos pos : Pos.values()) {
            list.add(new Change<Labeled>(pos.toString()) {
                public void apply(Labeled labeled) {
                    Rectangle rectangle = new Rectangle(10, 10, Color.rgb(200, 100, 100));
                    labeled.setGraphic(rectangle);
                    Utils.LayoutSize layout = new Utils.LayoutSize(150, 60, 150, 60, 150, 60);
                    layout.apply(labeled);
                    labeled.setAlignment(pos);
                    if (labeled.getAlignment() != pos) {
                        reportGetterFailure(getMarker());
                    }
                }
            });
        }
        root.add(new Page(list), LabeledsPages.setAlignment.name());

        addAlignment(list, false, "\nsecond line\nthird line", "");
        addAlignment(list, true, "\n" + WRAPPED_TEXT, "");
        addAlignment(list, false, "\nsecond line\nthird line\nfourth line", "_overrun");
        addAlignment(list, true, "\n" + WRAPPED_TEXT, "_overrun");
        root.add(new Page(list, 3), LabeledsPages.setTextAlignment.name());

        Utils.LayoutSize single_line_layout = new Utils.LayoutSize(150, 20, 150, 20, 150, 20);
        Utils.LayoutSize multi_line_layout = new Utils.LayoutSize(150, 40, 150, 40, 150, 40);

        addTextAlignment(list, single_line_layout, false, "one two three four five six seven eight nine");
        root.add(new Page(list), LabeledsPages.setTextOverrunSingleLine.name());

        addTextAlignment(list, multi_line_layout, false, MULTILINE_TEXT);
        root.add(new Page(list), LabeledsPages.setTextOverrunMultiLine.name());

        addTextAlignment(list, multi_line_layout, true, WRAPPED_TEXT);
        root.add(new Page(list), LabeledsPages.setTextOverrunSingleLineWrapped.name());

        addTextAlignment(list, multi_line_layout, true, MULTILINE_TEXT);
        root.add(new Page(list), LabeledsPages.setTextOverrunMultiLineWrapped.name());


    }

    protected void addAlignment(List<Change<Labeled>> list, final boolean wrapped, final String text, String mark) {
        for (final TextAlignment alignment : TextAlignment.values()) {
            list.add(new Change<Labeled>(alignment.toString() + (wrapped ? "_wrapped" : "") + mark) {
                public void apply(Labeled labeled) {
                    labeled.setText("TextAlignment = " + alignment.toString() + text);
                    labeled.setWrapText(wrapped);
                    Utils.LayoutSize layout = new Utils.LayoutSize(250, 40, 250, 40, 250, 40);
                    layout.apply(labeled);
                    labeled.setTextAlignment(alignment);
                    if (labeled.getTextAlignment() != alignment) {
                        reportGetterFailure(getMarker());
                    }
                }
            });
        }
    }

    protected void addTextAlignment(List<Change<Labeled>> list, final Utils.LayoutSize layout, final boolean wrapped, final String text) {
        for (final OverrunStyle overrun : OverrunStyle.values()) {
            list.add(new Change<Labeled>(overrun.toString()) {
                public void apply(Labeled labeled) {
                    layout.apply(labeled);
                    labeled.setText(text);
                    labeled.setTextOverrun(overrun);
                    labeled.setWrapText(wrapped);
                    if (labeled.getTextOverrun() != overrun) {
                        reportGetterFailure("labeled.overrun()");
                    }
                }
            });
        }
    }

    protected abstract List<Labeled> getConstructorPage();

    protected abstract Labeled getTestingControl();

    /**
     * This method need override in every test because every control have
     * different default size, border etc. Therefore every control have
     * different string.
     *
     * @return expected string
     */
    public abstract String getEllipsingString();

    protected class PageBase extends TestNode {

        protected GridPane root;
        private int counter = 0;
        private int line;

        public PageBase(int line) {
            this.line = line;
            root = new GridPane();
        }

        protected void addNode(Node node) {
            root.add(node, counter % line, counter / line);
            counter++;
        }

        @Override
        public Node drawNode() {
            return root;
        }
    }

    protected class Page extends PageBase {

        public Page(List<Change<Labeled>> changes) {
            this(changes, 5);
        }

        public Page(List<Change<Labeled>> changes, int line) {
            super(line);
            init(changes);
        }

        private void init(List<Change<Labeled>> changes) {
            for (Change<Labeled> change : changes) {
                VBox box = new VBox();
                if (change.getName() != null) {
                    box.getChildren().add(new Label("[" + change.getName() + "]"));
                }
                Labeled control = getTestingControl();
                change.apply(control);
                if (enableBorder) {
                    control.setStyle("-fx-border-color: darkgray");
                }
                box.getChildren().add(control);
                addNode(box);
            }
            changes.clear();
        }
    }

    protected class ConstructorPage extends PageBase {

        public ConstructorPage(List<Labeled> list) {
            this(list, 5);
        }

        public ConstructorPage(List<Labeled> list, int line) {
            super(line);
            init(list);
        }

        private void init(List<Labeled> list) {
            for (Labeled item : list) {
                if (enableBorder) {
                    item.setStyle("-fx-border-color: darkgray");
                }
                addNode(item);
            }
            list.clear();
        }
    }
}
