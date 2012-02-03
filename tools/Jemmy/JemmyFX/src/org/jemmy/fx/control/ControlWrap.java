/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the "License"). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this License Header Notice in each
 * file.
 *
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library. The Initial Developer of the
 * Original Software is Alexandre Iline. All Rights Reserved.
 *
 */
package org.jemmy.fx.control;

import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import org.jemmy.fx.NodeWrap;
import org.jemmy.control.ControlType;
import org.jemmy.control.MethodProperties;
import org.jemmy.env.Environment;

/**
 *
 * @param <T>
 * @author shura
 */
@ControlType(javafx.scene.control.Control.class)
@MethodProperties(value = "getTooltip", types = Tooltip.class)
public class ControlWrap<T extends javafx.scene.control.Control> extends NodeWrap<T> {

    /**
     *
     * @param scene
     * @param nd
     */
    public ControlWrap(Environment env, T node) {
        super(env, node);
    }
}
