/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.actions;

import javafx.scene.text.Text;
import test.scenegraph.lcd.TestAction;

/**
 *
 * @author Alexander Petrov
 */
public class SizeScaleTestAction implements TestAction {

    public enum ScaleAxis {

        X(true, false),
        Y(false, true),
        All(true, true);
        private boolean x;
        private boolean y;

        private ScaleAxis(boolean x, boolean y) {
            this.x = x;
            this.y = y;
        }
    }
    private double size;
    private double scale;
    private ScaleAxis scaleAxis;

    public SizeScaleTestAction(double size, double scale, ScaleAxis scaleAxis) {
        this.size = size;
        this.scale = scale;
        this.scaleAxis = scaleAxis;
    }

    public void updateNode(Text text) {
        //text.setFont(new Font(text.getFont().getName(), size));
        text.setStyle((text.getStyle() != null ? text.getStyle() : "")
                + "-fx-font-size: " + size + ";");
        
        if (scaleAxis.x) {
            text.setScaleX(scale);
        }
        if (scaleAxis.y) {
            text.setScaleY(scale);
        }
    }

    public boolean isLCDWork() {
        return size * scale <= 80;
    }

    public static TestAction[] generateSizes(double[] sizes) {
        if (sizes == null) {
            return null;
        }

        TestAction[] value = new TestAction[sizes.length];

        for (int i = 0; i < value.length; i++) {
            value[i] = new SizeScaleTestAction(sizes[i], 1, ScaleAxis.All);
        }

        return value;
    }

    public static TestAction[] generateScales(double[] scales, ScaleAxis scaleAxis) {
        if (scales == null) {
            return null;
        }

        TestAction[] value = new TestAction[scales.length];

        for (int i = 0; i < value.length; i++) {
            value[i] = new SizeScaleTestAction(16, scales[i], scaleAxis);
        }

        return value;
    }

    public static TestAction[] generate(double[] sizes, double[] scales, ScaleAxis scaleAxis) {
        if ((sizes == null) || (scales == null)) {
            return null;
        }

        TestAction[] value = new TestAction[sizes.length * scales.length];

        for (int i = 0; i < sizes.length; i++) {
            for (int j = 0; j < scales.length; j++) {
                value[i * scales.length + j] =
                        new SizeScaleTestAction(sizes[i], scales[j], scaleAxis);
            }
        }

        return value;
    }

    @Override
    public String toString() {
        return "size=" + size + ", scale=" + scale + ", scaleAxis=" + scaleAxis;
    }
    
    
}
