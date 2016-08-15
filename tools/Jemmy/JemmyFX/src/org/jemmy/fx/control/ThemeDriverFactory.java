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
package org.jemmy.fx.control;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.caspian.CaspianDriverFactory;
import org.jemmy.interfaces.Editor;
import org.jemmy.interfaces.Focus;
import org.jemmy.interfaces.Scroll;
import org.jemmy.interfaces.Scroller;
import org.jemmy.interfaces.Shifter;
import org.jemmy.interfaces.TreeItem;

/**
 * Defines how FX controls are operated. This implementation defines "generic"
 * behavior which comes from JemmyCore. Theme-specific implementations are
 * to be provided for every theme.
 *
 * Ported from the project JemmyFX, original class
 * is   org.jemmy.fx.control.DriverFactory
 *
 * @see CaspianDriverFactory
 * @author shura
 */
public abstract class ThemeDriverFactory {

    static {
        Root.ROOT.getEnvironment().setPropertyIfNotSet(ThemeDriverFactory.class, ThemeDriverFactory.newInstance());
    }

    final static String NOT_SUPPORTED = "Not supported in the ThemeDriverFactory";
    /**
     * NOTE Probably param Environment env should be passed to the factory.
     * @return theme-specific implementation of the ThemeDriverFactory. Only
     * CaspianDriverFactor is provided to the moment.
     */
    public static ThemeDriverFactory newInstance() {
        return new CaspianDriverFactory();
    }

    /**
     * @param factory
     * @return
     */
    public static ThemeDriverFactory setThemeFactory(ThemeDriverFactory factory) {
        return (ThemeDriverFactory) Root.ROOT.getEnvironment().setProperty(ThemeDriverFactory.class, factory);
    }

    /**
     *
     * @return
     */
    public static ThemeDriverFactory getThemeFactory() {
        return (ThemeDriverFactory) Root.ROOT.getEnvironment().getProperty(ThemeDriverFactory.class);
    }
    /**
     * Returns scroller for Slider and ScrollBar. Default implementation is yet disabled.
     * @see Slider
     * @see ScrollBar
     * @see SliderOperator
     * @see ScrollBarOperator
     * @see Scroll
     * @param wrap - the operator instance
     * @param scroll - Scroll instance. Any method of it could be used but the
     * caret() method.
     * @return
     */
    public abstract Scroller caret(final Wrap<? extends Control> wrap, final Scroll scroll);

    public abstract Shifter track(final Wrap<? extends Control> wrap, final Scroll scroll);

    public abstract <T> TreeItem treeItem(Wrap<T> itemWrap, Wrap parentControlWrap);

    public abstract Focus menuBarFocuser(final MenuBarWrap<? extends MenuBar> menuBarWrap);

    public abstract Focus comboBoxFocuser(final ComboBoxWrap<? extends ComboBox> comboBoxWrap);

    public abstract void splitMenuButtonExpandCollapseAction(final SplitMenuButtonWrap<? extends SplitMenuButton> wrap);

    public abstract Editor colorEditor(final ColorPickerWrap<? extends ColorPicker> colorPickerWrap);
 }
