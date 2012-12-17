/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
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
