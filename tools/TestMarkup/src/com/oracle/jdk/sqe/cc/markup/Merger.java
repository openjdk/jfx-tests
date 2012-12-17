/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
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
