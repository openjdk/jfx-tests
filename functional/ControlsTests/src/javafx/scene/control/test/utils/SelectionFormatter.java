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
package javafx.scene.control.test.utils;

import java.util.Arrays;
import java.util.Collection;
import org.jemmy.Point;

/**
 *
 * Dmitry Zinkevich <dmitry.zinkevich@oracle.com>
 */
public class SelectionFormatter {

    static class EmptyStringCreator {
        public static String make(int n) {
            StringBuilder sb = new StringBuilder();
            while (n-- > 0) {
                sb.append(" ");
            }
            return sb.toString();
        }
    }

    public static String format(String xsTitle, Collection<Point> xs, String ysTitle, Collection<Point> ys) {
        return format(xsTitle, xs, ysTitle, ys, ",");
    }

    public static String format(String xsTitle, Collection<Point> xs, String ysTitle, Collection<Point> ys, String separator) {
        int[] helperIndices = new int[xs.size()];
        int i = 0;
        for (Point pt : xs) {
            helperIndices[i++] = pt.y;
        }

        Arrays.sort(helperIndices);

        int[] actualIndices = new int[ys.size()];
        i = 0;
        for (Point pt : ys) {
            actualIndices[i++] = pt.y;
        }

        Arrays.sort(actualIndices);

        int maxLen = Math.max(xsTitle.length(), ysTitle.length());

        StringBuilder firstResult = new StringBuilder(String.format("%" + maxLen + "s", xsTitle)).append("[");
        StringBuilder secondResult = new StringBuilder(String.format("%" + maxLen + "s", ysTitle)).append("[");

        int x = 0, y = 0;
        while (x < helperIndices.length && y < actualIndices.length) {
            if (helperIndices[x] < actualIndices[y]) {
                firstResult.append(helperIndices[x]).append(separator);
                secondResult.append(EmptyStringCreator.make(String.valueOf(helperIndices[x]).length())).append(separator);
                ++x;
                continue;
            } else if (helperIndices[x] > actualIndices[y]) {
                firstResult.append(EmptyStringCreator.make(String.valueOf(actualIndices[y]).length())).append(separator);
                secondResult.append(actualIndices[y]).append(separator);
                ++y;
                continue;
            } else {
                firstResult.append(helperIndices[x++]).append(separator);
                secondResult.append(actualIndices[y++]).append(separator);
            }
        }

        while (x < helperIndices.length) {
            firstResult.append(helperIndices[x]).append(separator);
            secondResult.append(EmptyStringCreator.make(String.valueOf(helperIndices[x]).length() + separator.length()));
            ++x;
        }

        while (y < actualIndices.length) {
            secondResult.append(actualIndices[y]).append(separator);
            firstResult.append(EmptyStringCreator.make(String.valueOf(actualIndices[y]).length() + separator.length()));
            ++y;
        }

        firstResult.append("]");
        secondResult.append("]");

        return firstResult.append("\n").append(secondResult).toString();
    }
}
