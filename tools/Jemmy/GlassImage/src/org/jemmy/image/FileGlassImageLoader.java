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
package org.jemmy.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jemmy.JemmyException;

/**
 *
 * @author shura
 */
public class FileGlassImageLoader extends GlassImageLoader {

    private File imageRoot = null;

    public FileGlassImageLoader(File imageRoot) {
        this.imageRoot = imageRoot;
    }

    public FileGlassImageLoader() {
    }

    @Override
    protected InputStream getInputStream(String name) {
        try {
            String fullPath = (imageRoot == null) ? name : (imageRoot.getAbsolutePath() + File.separator + name);
            return new FileInputStream(fullPath);
        } catch (FileNotFoundException ex) {
            throw new JemmyException("Unable to load image \"" + name + "\"", ex);
        }
    }
}
