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

import javafx.scene.control.Slider;
import org.jemmy.control.Wrap;
import org.jemmy.input.KnobDragScrollerImpl;
import org.jemmy.interfaces.Scroll;

/**
 * Scroller implementation which uses d'n'd of the knob of Slider.
 *
 * Ported from the project JemmyFX, original class
 * is   org.jemmy.fx.control.caspian.SliderScroller
 *
 * @see KnobDragScrollerImpl
 * @author shura
 */
public class SliderScroller extends KnobTrackScrollerImpl {

    /**
     *
     * @param wrap
     * @param scroll
     * @param skinClass
     */
    public SliderScroller(Wrap<? extends Slider> wrap, Scroll scroll, final Class skinClass) {
        super(wrap, scroll, wrap.getProperty(Boolean.class, Scroll.VERTICAL_PROP_NAME).booleanValue());
        setWraps(wrap, skinClass);
    }
}
