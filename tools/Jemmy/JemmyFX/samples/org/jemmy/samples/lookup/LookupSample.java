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
package org.jemmy.samples.lookup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.jemmy.TimeoutExpiredException;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.lookup.AbstractLookup;
import org.jemmy.lookup.LookupCriteria;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This sample demonstrates different lookup approaches which could be applied
 * for lookup of any Node type. Use component specific samples to learn
 * component specific lookup approaches.
 *
 * @author shura
 */
public class LookupSample extends LookupSampleBase {

    /**
     * How to find ... whatever
     */
    @Test
    public void any() {

        //find just any scene
        //in a one-scene app this is just about what needed to be done
        SceneDock scene = new SceneDock();

        //find any node
        //this will heve very little use in a real app
        NodeDock node = new NodeDock(scene.asParent());

        //let's just move a mouse there
        node.mouse().move();

    }

    /**
     * How to find a Node of certain type
     */
    @Test
    public void type() {
        //find the scene
        SceneDock scene = new SceneDock();

        //now find a Label
        //this could be used directly in case there is only one node of a certain
        //type in a parent
        NodeDock label = new NodeDock(scene.asParent(), Label.class);
        label.mouse().move();
    }

    /**
     * How to find an index'th Node of certain type
     */
    @Test
    public void typeIndex() {
        //find the scene
        SceneDock scene = new SceneDock();

        //now find a second Label
        //this could be used if there are indistinquishable nodes
        //it is better not to use indexes whenever possible 'cause
        //indexes change from build to build
        NodeDock label = new NodeDock(scene.asParent(), Label.class, 1);
        label.mouse().move();

        assureMouseOver("(0,1)");

    }

    /**
     * How to find a Node by id
     */
    @Test
    public void id() {
        //find the scene
        SceneDock scene = new SceneDock();

        //now find a node which has a certain id
        NodeDock label = new NodeDock(scene.asParent(), Node.class, "lbl_11");
        label.mouse().move();

        assureMouseOver("(1,1)");
    }

    /**
     * How to find a Node by a custom criteria
     */
    @Test
    public void criteria() {
        //find the scene
        SceneDock scene = new SceneDock();

        //in more complicated cases id or type may not be enouhg
        //please consult control specific samples for additional lookup
        //approaches before doing this. There are also other predevined lookup
        //criteria shown in *LookupSampleTest.java files

        //let's find a label which has "0" in text
        new NodeDock(scene.asParent(), Label.class, new LookupCriteria<Label>() {

            public boolean check(Label cntrl) {
                return cntrl.getText().contains("0");
            }

            @Override
            public String toString() {
                //it is a good idea to override toString()
                //this value will be in the output if waiting for a node fails
                return "a label with \"0\" in the text";
            }
        }).mouse().move();

        assureMouseOver("(0,0)");
    }

    /**
     * Unsuccessful lookup is throwing TimeoutExpiredException. Timeout for
     * waiting is controlled by
     * <code>AbstractLookup.WAIT_CONTROL_TIMEOUT</code>
     *
     * @see AbstractLookup#WAIT_CONTROL_TIMEOUT
     */
    @Test
    public void negative() {
        //find the scene
        SceneDock scene = new SceneDock();

        try {
            NodeDock node = new NodeDock(scene.asParent(), cntrl -> false);
            System.out.println("This node could never be found " + node);
            fail("Should not get here!");
        } catch (TimeoutExpiredException e) {
            //expected
        }
    }

    @Test
    public void dump() throws FileNotFoundException {
        new SceneDock().asParent().lookup().dump(new PrintStream(new FileOutputStream(System.getProperty("user.dir") + File.separator + "scene.dump")));
    }
}
