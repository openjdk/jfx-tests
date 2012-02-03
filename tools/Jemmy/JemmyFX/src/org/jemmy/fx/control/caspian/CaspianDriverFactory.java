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
 * questions.
 */
package org.jemmy.fx.control.caspian;

import com.sun.javafx.scene.control.skin.ScrollBarSkin;
import com.sun.javafx.scene.control.skin.SliderSkin;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import org.jemmy.control.Wrap;
import org.jemmy.fx.control.ThemeDriverFactory;
import org.jemmy.fx.control.TreeItemWrap;
import org.jemmy.fx.control.TreeNodeWrap;
import org.jemmy.interfaces.Scroll;
import org.jemmy.interfaces.Scroller;
import org.jemmy.interfaces.Shifter;

/**
 * Defines control behaviour for Caspian theme.
 * 
 * Ported from the project JemmyFX, original class 
 * is   org.jemmy.fx.control.caspian.CaspianDriverFactory
 * 
 * @author shura
 */
public class CaspianDriverFactory extends ThemeDriverFactory {

    private float dragDelta;

    /**
     *
     * @param dragDelta
     */
    public CaspianDriverFactory(float dragDelta) {
        this.dragDelta = dragDelta;
    }

    /**
     *
     */
    public CaspianDriverFactory() {
        this(1);
    }

    /**
     *
     * @return
     */
    public float getDragDelta() {
        return dragDelta;
    }

    /**
     *
     * @param dragDelta
     */
    public void setDragDelta(float dragDelta) {
        this.dragDelta = dragDelta;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Scroller caret(Wrap<? extends Control> wrap, Scroll scroll) {
        KnobTrackScrollerImpl res = createScrollerImpl(wrap, scroll);
        res.setDragDelta(dragDelta);
        return res;
    }

    @Override
    public Shifter track(final Wrap<? extends Control> wrap, final Scroll scroll) {
        KnobTrackScrollerImpl sls = createScrollerImpl(wrap, scroll);
        return sls.getTrack();
    }

    private KnobTrackScrollerImpl createScrollerImpl(Wrap<? extends Control> wrap, Scroll scroll) {

        if (wrap.getControl() instanceof ScrollBar) {
            return new ScrollBarScroller((Wrap<? extends ScrollBar>) wrap, scroll, ScrollBarSkin.class);
        }
        if (wrap.getControl() instanceof Slider) {
            return new SliderScroller((Wrap<? extends Slider>) wrap, scroll, SliderSkin.class);
        }
        return null;
    }

    @Override
    public <T> org.jemmy.fx.control.caspian.TreeItem treeItem(Wrap<T> wrap) {
        if(wrap instanceof TreeNodeWrap) {
            TreeNodeWrap itemWrap = (TreeNodeWrap) wrap;
            return new org.jemmy.fx.control.caspian.TreeItem(itemWrap);
        }
        return null;
    }
}
