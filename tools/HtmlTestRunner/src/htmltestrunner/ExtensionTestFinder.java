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
 */

package htmltestrunner;

import com.sun.javatest.Status;
import com.sun.javatest.TestFinder;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author shura
 */
public abstract class ExtensionTestFinder extends TestFinder {

    /**
     *
     */
    public static final String FILE_PATH_PROP = "file.path";

    /**
     *
     */
    public static final String INSTR_FILE_PATH_PROP = "instructions.file.path";

    private String extension;

    /**
     *
     * @param extension
     */
    public ExtensionTestFinder(String extension) {
        this.extension = extension;
    }

    /**
     *
     * @return
     */
    protected String getExtension() {
        return extension;
    }

    /**
     *
     * @param file
     */
    public void scan(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                if (f.isDirectory() || f.getName().toLowerCase().endsWith(extension)) {
                    foundFile(f);
                }
            }
        } else {
            if (acceptFile(file)) {
                HashMap tagValues = new HashMap();
                if (tagValues != null) {
                    String rootDir = getRootDir().getAbsolutePath();
                    String filePath = file.getAbsolutePath().substring(rootDir.length() + 1, file.getAbsolutePath().length());
                    tagValues.put("testName", filePath);
                    tagValues.put(FILE_PATH_PROP, file.getPath());
                    addAttributes(tagValues);
                    foundTestDescription(tagValues, new File(filePath), 0);
                }
            }
        }
    }

    /**
     *
     * @param tagValues
     */
    protected abstract void addAttributes(HashMap tagValues);

    /**
     *
     * @param f
     * @return
     */
    protected boolean acceptFile(File f) {
        return true;
    }
}
