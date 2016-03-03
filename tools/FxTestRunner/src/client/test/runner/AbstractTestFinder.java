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

package client.test.runner;

import client.test.ExcludePlatformMethod;
import client.test.ExcludePlatformType;
import client.test.OnlyPlatformMethod;
import client.test.OnlyPlatformType;
import client.test.Platforms;
import com.sun.javafx.util.Utils;
import htmltestrunner.ExtensionTestFinder;
import java.lang.reflect.Method;

/**
 * @author Andrey Glushchenko
 */
public abstract class AbstractTestFinder extends ExtensionTestFinder {

    /**
     *
     */
    protected static final String JAVA_EXT = ".java";

    /**
     *
     * @param extension
     */
    public AbstractTestFinder(String extension) {
        super(extension);
    }

    /**
     *
     * @param testClass
     * @return
     */
    protected boolean excludeTestClass(Class testClass) {
        ExcludePlatformType expt = (ExcludePlatformType) testClass.getAnnotation(ExcludePlatformType.class);
        if (expt != null) {
            if (isCurrentPlatform(expt.value())) {
                return true;
            }
        }
        OnlyPlatformType opt = (OnlyPlatformType) testClass.getAnnotation(OnlyPlatformType.class);
        if (opt != null) {
            if (!isCurrentPlatform(opt.value())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param testMethod
     * @return
     */
    protected boolean excludeTestMethod(Method testMethod) {
        ExcludePlatformMethod epm = (ExcludePlatformMethod) testMethod.getAnnotation(ExcludePlatformMethod.class);
        if (epm != null) {
            if (isCurrentPlatform(epm.value())) {
                return true;
            }
        }
        OnlyPlatformMethod opm = (OnlyPlatformMethod) testMethod.getAnnotation(OnlyPlatformMethod.class);
        if (opm != null) {
            if (!isCurrentPlatform(opm.value())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param p
     * @return
     */
    protected boolean isCurrentPlatform(Platforms p) {
        if (Utils.isMac() && p.equals(Platforms.MAC)) {
            return true;
        }
        if (Utils.isWindows() && p.equals(Platforms.WINDOWS)) {
            return true;
        }
        if (Utils.isUnix() && p.equals(Platforms.UNIX)) {
            return true;
        }
        return false;
    }

    /**
     * This function removes ".java" from the end of the file path. Nothing
     * happens, if there is no ".java" in the end.
     *
     * @param path Path to *.java file.
     * @return String without ".java" on the end.
     */
    protected String removeDotJavaExtensionFromFilePath(String path) {
        if (path.toLowerCase().endsWith(JAVA_EXT)) {
            return path.substring(0, path.length() - JAVA_EXT.length());
        } else {
            return path;
        }
    }
}
