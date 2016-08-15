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

/**
 *
 * @author vshubov
 */
public class TestFXSettings {

    /**
     *
     * @param args
     */
    public static void main(String[] args){
       if(args.length<1) {
            System.out.println("usage: this.class <toolkit-short-name> [pipeline]");
            System.exit(1);
       }
       String toolkit = args[0];
       if (("DefaultToolkit".equals(toolkit))) {
            System.exit(0);
       }
       String pipeline = "";
       if(args.length>1) pipeline = args[1];
       String tk = com.sun.javafx.tk.Toolkit.getToolkit().getClass().getSimpleName();
       if(!toolkit.equals(tk)) System.exit(2);
       if ("GlassToolkit".equals(tk) || "PrismToolkit".equals(tk)) {
           String ppl = com.sun.prism.GraphicsPipeline.getPipeline().getClass().getSimpleName();
           if(pipeline.equals("")){
               if(!(ppl.trim().equals("D3DPipeline")
                       ||ppl.trim().equals("ES1Pipeline")
                       ||ppl.trim().equals("ES2Pipeline"))){
                   System.exit(3);
               }
           } else {
               if(!pipeline.trim().equals(ppl.trim())) System.exit(4);
           }
       }
       System.exit(0);
    }
}
