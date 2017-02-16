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
package test.css.utils;

import javafx.css.StyleConverter;
import javafx.css.CssMetaData;
import javafx.css.converter.*;
import com.sun.javafx.scene.layout.region.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Andrey Nazarov
 */
public class StyleGenerator {

    static Set<CssMetaData> uniqKeys = new HashSet<CssMetaData>();

    static public void addKey(CssMetaData key) {
        uniqKeys.add(key);
    }
    static StringBuffer unknownTypes = new StringBuffer();
    static StringBuffer genCode = new StringBuffer("--------------Generated code for unused css styles----------\n");

    public static void generate() {
        for (CssMetaData key : uniqKeys) {
            String value = getValueByType(key.getConverter());
            if (value == null) {
                unknownTypes.append("Don't know value of property ").append(key.getProperty()).append(" of type ").append(key.getConverter()).append("\n");
                continue;
            }
            genCode.append("props2Impls.put(\"");
            genCode.append(key.getProperty() + "\", new CssStyle[]{\n");
            genCode.append("new CssStyle(\"" + key.getProperty().substring(4).toUpperCase() + "\",\n");
            genCode.append("\"" + key.getProperty() + ": \"+\n");
            genCode.append("\"");
            genCode.append(value + ";\")});\n");
        }
        System.out.println(genCode.toString());
        System.out.println("----------------------Errors-------------------------");
        System.out.println(unknownTypes.toString());
    }

    static String getValueByType(StyleConverter type) {
        if (type instanceof BooleanConverter) {
            return "true";
        } else if (type instanceof ColorConverter) {
            return "red";
        } else if (type instanceof SizeConverter) {
            return "40";
        } else if (type instanceof StringConverter) {
            return "Just text";
        } else if (type instanceof InsetsConverter || type.toString().equals("InsetsSequenceConverter") || type.toString().equals("MarginsSequenceConverter")) {
            return "10 5 30 15";
        //} else if (type instanceof BackgroundFillConverter) {
        //    return "linear (0%,0%) to (100%,100%) stops (0.0,yellow) (1.0,red)";
        } else if (type instanceof PaintConverter) {
            return "green , linear (0%,0%) to (100%,100%) stops (0.0,yellow) (1.0,red)";
        //} else if (type instanceof StrokeBorderConverter) {
        //    return "round";
        //} else if (type instanceof LayeredBorderPaintConverter) {
        //    return "green blue yellow red";
        } else if (type instanceof FontConverter) {
            return "15 cursive bolder";
        } else if (type instanceof EnumConverter) {
            return "TODO type " + type.toString();
        } else if (type instanceof PaintConverter) {
            return "ladder black stops (0.49, white) (0.5, black)";
        //} else if (type instanceof URLConverter || type instanceof BackgroundImageConverter || type instanceof BorderImageConverter || type.toString().equals("URLSeqType")) {
        //    return "url(\\\"file:javafx/scene/control/test/css/resources/car.png\\\")";
        } else if (type instanceof CursorConverter) {
            return "crosshair";
        } else {
            return null;
        }
    }
}
