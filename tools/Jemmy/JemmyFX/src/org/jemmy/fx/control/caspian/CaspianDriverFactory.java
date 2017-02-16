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

import javafx.scene.control.skin.ScrollBarSkin;
import javafx.scene.control.skin.SliderSkin;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.jemmy.action.FutureAction;
import org.jemmy.JemmyException;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.NodeWrap;
import org.jemmy.fx.control.ColorPickerWrap;
import org.jemmy.fx.control.ComboBoxWrap;
import org.jemmy.fx.control.MenuBarWrap;
import org.jemmy.fx.control.SplitMenuButtonWrap;
import org.jemmy.fx.control.ThemeDriverFactory;
import org.jemmy.fx.control.TreeNodeWrap;
import org.jemmy.fx.control.TreeTableItemWrap;
import org.jemmy.interfaces.*;
import org.jemmy.interfaces.Editor;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;

/**
 * Defines control behaviour for Caspian theme.
 * <p/>
 * Ported from the project JemmyFX, original class
 * is   org.jemmy.fx.control.caspian.CaspianDriverFactory
 *
 * @author shura
 */
public class CaspianDriverFactory extends ThemeDriverFactory {

    private float dragDelta;

    /**
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
     * @return
     */
    public float getDragDelta() {
        return dragDelta;
    }

    /**
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
    public <T> org.jemmy.interfaces.TreeItem treeItem(Wrap<T> wrap, Wrap parentControlWrap) {
        if (wrap instanceof TreeNodeWrap) {
            return new org.jemmy.fx.control.caspian.TreeItem((TreeNodeWrap) wrap, parentControlWrap);
        }
        if (wrap instanceof TreeTableItemWrap) {
            return new org.jemmy.fx.control.caspian.TreeTableItem((TreeTableItemWrap) wrap, parentControlWrap);
        }
        throw new JemmyException("Unknown type of parameter.");
    }

    @Override
    public Focus menuBarFocuser(final MenuBarWrap<? extends MenuBar> menuBarWrap) {
        if (isMacOS()) {
            return new NodeFocus(menuBarWrap) {
                @Override
                protected void activate() {
                    // temporary solution due to bug in MacOS implementation of MenuBar
                    new FutureAction(menuBarWrap.getEnvironment(), () -> menuBarWrap.getControl().requestFocus());
                }
            };
        } else {
            return new NodeFocus(menuBarWrap) {
                @Override
                protected void activate() {
                    // pressKey()/releaseKey() are used to prevent an attempt to get focus in pushKey()
                    menuBarWrap.keyboard().pressKey(KeyboardButtons.F10);
                    menuBarWrap.getEnvironment().getTimeout(menuBarWrap.keyboard().PUSH.getName());
                    menuBarWrap.keyboard().releaseKey(KeyboardButtons.F10);
                }
            };
        }
    }

    @Override
    public Focus comboBoxFocuser(final ComboBoxWrap<? extends ComboBox> comboBoxWrap) {
        return new NodeFocus(comboBoxWrap) {
            @Override
            protected void activate() {
                comboBoxWrap.as(Parent.class, Node.class).lookup(new ByStyleClass<>("arrow-button")).wrap().mouse().click(comboBoxWrap.isShowing() ? 1 : 2);
            }
        };
    }


    @Override
    public void splitMenuButtonExpandCollapseAction(SplitMenuButtonWrap<? extends SplitMenuButton> wrap) {
        wrap.asParent().lookup(Node.class, new ByStyleClass<>("arrow-button")).wrap().mouse().click();
    }

    @Override
    public Editor colorEditor(final ColorPickerWrap<? extends ColorPicker> colorPickerWrap) {
        return new ColorEditorImpl(colorPickerWrap);
    }

    abstract class NodeFocus implements Focus {

        NodeWrap nodeWrap;

        public NodeFocus(NodeWrap nodeWrap) {
            this.nodeWrap = nodeWrap;
        }

        @Override
        public void focus() {
            if (!nodeWrap.isFocused()) {
                activate();
            }
            nodeWrap.waitState(()->nodeWrap.isFocused(), true);
        }

        abstract protected void activate();
    }

    private static boolean isMacOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("mac") >= 0) {
            return true;
        }
        return false;
    }
}
