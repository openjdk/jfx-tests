/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this License Header
 * Notice in each file.
 *
 * If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
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
