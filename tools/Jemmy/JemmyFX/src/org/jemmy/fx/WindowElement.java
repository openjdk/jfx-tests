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

package org.jemmy.fx;

import org.jemmy.interfaces.TypeControlInterface;

/**
 * The interface specifies how to get window (a.k.a. root element) where control
 * resides. For different control libraries this could be
 * different things: <code>java.awt.Window</code> for AWT,
 * <code>javafx.scene.Scene</code> for FX, etc.
 * @param <WINDOW> type of the root element.
 * @author shura
 */
public interface WindowElement<WINDOW> extends TypeControlInterface<WINDOW>{
    /**
     * Returns root hierarchy element.
     * @return
     */
    public WINDOW getWindow();
}
