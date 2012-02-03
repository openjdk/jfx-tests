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
package org.jemmy.fx.control;

import javafx.scene.control.Control;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import org.jemmy.control.Wrap;
import org.jemmy.fx.control.caspian.CaspianDriverFactory;
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
    
    public abstract <T> TreeItem treeItem(Wrap<T> itemWrap);
 }
