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
package com.oracle.jdk.sqe.cc.markup;

import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shura
 */
public class CoverageUnit {

    String feature;
    ArrayList<String> sourceUnits = new ArrayList<String>();
    Level level;

    /**
     *
     * @param feature covered feature
     * @param sourceUnit covering source unit (such as test class or test method)
     * @param level
     */
    private CoverageUnit(String feature, Level level) {
        this.feature = feature;
        this.level = level;
    }

    public CoverageUnit(String feature, String sourceUnit, Level level) {
        this(feature, level);
        this.sourceUnits.add(sourceUnit);
    }

    public CoverageUnit(String feature) {
        this(feature, Level.TEMPLATE);
    }

    public String getFeature() {
        return feature;
    }

    public Level getLevel() {
        return level;
    }

    public List<String> getSourceUnits() {
        return sourceUnits;
    }

    public void addSourceUnit(String unit) {
        if (!sourceUnits.contains(unit)) {
            sourceUnits.add(unit);
        }
    }

    public void merge(CoverageUnit another) {
        if (!feature.equals(another.getFeature())) {
            throw new IllegalArgumentException("Wrong feature " + another.getFeature()
                    + " while expected " + feature);
        }
        level = level.max(another.level);
        for (String source : another.sourceUnits) {
            if (!sourceUnits.contains(source)) {
                sourceUnits.add(source);
            }
        }
    }

}
