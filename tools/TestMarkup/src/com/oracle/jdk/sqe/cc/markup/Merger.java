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
package com.oracle.jdk.sqe.cc.markup;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author shura
 */
public class Merger {

    public static void main(String[] args) {
        if (args.length >= 3 && args[0].equals("-o")) {
            try {
                CoverageStorage res = new CoverageStorage(true);
                res.read(new File(args[2]));
                CoverageStorage next = new CoverageStorage(false);
                for (int i = 3; i < args.length; i++) {
                    next.read(new File(args[i]));
                    res.merge(next);
                }
                res.write(new File(args[1]));
            } catch (IOException ex) {
                ex.printStackTrace();
                usage();
            }
        } else {
            usage();
        }
    }

    public static void usage() {
        System.out.println("java " + Merger.class.getName()
                + " -o <output file> <template file> [<input file> ...]");
        System.exit(1);
    }
}
