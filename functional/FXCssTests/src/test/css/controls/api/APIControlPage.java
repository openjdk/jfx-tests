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
package test.css.controls.api;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.BorderRepeat;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import test.css.controls.styles.StyleSetter;

/**
 *
 * @author sergey.lugovoy@oracle.com
 */
public enum APIControlPage {

    BORDER_COLOR(new StyleSetter() {
        @Override
        public void setStyle(Node control) {
            BorderStroke bs = new BorderStroke(Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY);
            Border border = new Border(bs);
            if (control instanceof Region) {
                ((Region) control).setBorder(border);
            }
        }
    }), BORDER_WIDTH(new StyleSetter() {
        @Override
        public void setStyle(Node control) {
            BorderStroke bs = new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 2, 1, 2), Insets.EMPTY);
            Border border = new Border(bs);
            if (control instanceof Region) {
                ((Region) control).setBorder(border);
            }
        }
    }), BORDER_WIDTH_dotted(new StyleSetter() {
        @Override
        public void setStyle(Node control) {
            BorderStroke bs = new BorderStroke(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
                    BorderStrokeStyle.DOTTED, BorderStrokeStyle.DOTTED, BorderStrokeStyle.DOTTED, BorderStrokeStyle.DOTTED,
                    CornerRadii.EMPTY, new BorderWidths(1, 3, 5, 1), Insets.EMPTY);
            Border border = new Border(bs);
            if (control instanceof Region) {
                ((Region) control).setBorder(border);
            }
        }
    }), BORDER_WIDTH_dashed(new StyleSetter() {
        @Override
        public void setStyle(Node control) {
            BorderStroke bs = new BorderStroke(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
                    BorderStrokeStyle.DASHED, BorderStrokeStyle.DASHED, BorderStrokeStyle.DASHED, BorderStrokeStyle.DASHED,
                    CornerRadii.EMPTY, new BorderWidths(1, 3, 5, 1), Insets.EMPTY);
            Border border = new Border(bs);
            if (control instanceof Region) {
                ((Region) control).setBorder(border);
            }
        }
    }), BORDER_INSET(new StyleSetter() {
        @Override
        public void setStyle(Node control) {
            BorderStroke bs = new BorderStroke(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, BorderStrokeStyle.SOLID,
                    BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                    new CornerRadii(5), new BorderWidths(1), new Insets(5));
            Border border = new Border(bs);
            if (control instanceof Region) {
                ((Region) control).setBorder(border);
            }
        }
    }),
    BORDER_STYLE_DASHED(new StyleSetter() {
        @Override
        public void setStyle(Node control) {
            BorderStroke bs = new BorderStroke(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
                    BorderStrokeStyle.DASHED, BorderStrokeStyle.DASHED, BorderStrokeStyle.DASHED, BorderStrokeStyle.DASHED,
                    new CornerRadii(5), new BorderWidths(1), Insets.EMPTY);
            Border border = new Border(bs);
            if (control instanceof Region) {
                ((Region) control).setBorder(border);
            }
        }
    }),
    BORDER_STYLE_DOTTED(new StyleSetter() {
        @Override
        public void setStyle(Node control) {
            BorderStroke bs = new BorderStroke(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
                    BorderStrokeStyle.DOTTED, BorderStrokeStyle.DOTTED, BorderStrokeStyle.DOTTED, BorderStrokeStyle.DOTTED,
                    new CornerRadii(5), new BorderWidths(1), Insets.EMPTY);
            Border border = new Border(bs);
            if (control instanceof Region) {
                ((Region) control).setBorder(border);
            }
        }
    }),
    IMAGE_BORDER(new StyleSetter() {
        @Override
        public void setStyle(Node control) {
            BorderImage bi = new BorderImage(new Image(CSS_BORDER), new BorderWidths(9), Insets.EMPTY, new BorderWidths(28), false, null, null);
            Border border = new Border(bi);
            if (control instanceof Region) {
                ((Region) control).setBorder(border);
            }
        }
    }),
    IMAGE_BORDER_INSETS(new StyleSetter() {
        @Override
        public void setStyle(Node control) {
            BorderImage bi = new BorderImage(new Image(CSS_RED_RECTANGLE), new BorderWidths(5), new Insets(1, 5, 10, 15), null, false, BorderRepeat.ROUND, BorderRepeat.ROUND);
            Border border = new Border(bi);
            if (control instanceof Region) {
                ((Region) control).setBorder(border);
            }
        }
    }),
    IMAGE_BORDER_NO_REPEAT(new StyleSetter() {
        @Override
        public void setStyle(Node control) {
            BorderImage bi = new BorderImage(new Image(CSS_BORDER), new BorderWidths(9), Insets.EMPTY, new BorderWidths(28), false, BorderRepeat.STRETCH, BorderRepeat.STRETCH);
            Border border = new Border(bi);
            if (control instanceof Region) {
                ((Region) control).setBorder(border);
            }
        }
    }),
    IMAGE_BORDER_REPEAT_X(new StyleSetter() {
        @Override
        public void setStyle(Node control) {
            BorderImage bi = new BorderImage(new Image(CSS_BORDER), new BorderWidths(9), Insets.EMPTY, new BorderWidths(28), false, BorderRepeat.REPEAT, BorderRepeat.STRETCH);
            Border border = new Border(bi);
            if (control instanceof Region) {
                ((Region) control).setBorder(border);
            }
        }
    }),
    IMAGE_BORDER_REPEAT_Y(new StyleSetter() {
        @Override
        public void setStyle(Node control) {
            BorderImage bi = new BorderImage(new Image(CSS_BORDER), new BorderWidths(9), Insets.EMPTY, new BorderWidths(28), false, BorderRepeat.STRETCH, BorderRepeat.REPEAT);
            Border border = new Border(bi);
            if (control instanceof Region) {
                ((Region) control).setBorder(border);
            }
        }
    }),
    IMAGE_BORDER_ROUND(new StyleSetter() {
        @Override
        public void setStyle(Node control) {
            BorderImage bi = new BorderImage(new Image(CSS_BORDER), new BorderWidths(9), Insets.EMPTY, new BorderWidths(28), false, BorderRepeat.ROUND, BorderRepeat.ROUND);
            Border border = new Border(bi);
            if (control instanceof Region) {
                ((Region) control).setBorder(border);
            }
        }
    }),
    IMAGE_BORDER_SPACE(new StyleSetter() {
        @Override
        public void setStyle(Node control) {
            BorderImage bi = new BorderImage(new Image(CSS_BORDER), new BorderWidths(9), Insets.EMPTY, new BorderWidths(28), false, BorderRepeat.SPACE, BorderRepeat.SPACE);
            Border border = new Border(bi);
            if (control instanceof Region) {
                ((Region) control).setBorder(border);
            }
        }
    }),
    ;
    private StyleSetter setter;
    final private static String CSS_BORDER = APIControlPage.class.getResource("/test/css/resources/border.png").toExternalForm();
    final private static String CSS_RED_RECTANGLE = APIControlPage.class.getResource("/test/css/resources/red-rectangle.png").toExternalForm();
    public static final String packageName = "api";

    APIControlPage(StyleSetter setter) {
        this.setter = setter;
    }

    public void setStyle(Node control) {
        setter.setStyle(control);
    }
}
