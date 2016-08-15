/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
package javafx.scene.control.test.util.perfomance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Control;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.junit.Assert;

/**
 * @author Alexander Kirov
 */
public class NodeCounterChecker {

    private Wrap testedControl;
    private Runnable action;
    private int loops;
    private int warmingLoops;
    private List<Measurement> telemetry = new ArrayList<Measurement>();
    private StateVerificator verificators[];
    private Mode runMode;

    public NodeCounterChecker(Wrap testedControl, int numberOfLoops, int numberOfWarmingLoops, Runnable actionMaker, Mode runMode, StateVerificator... verifiers) {
        this.testedControl = testedControl;
        this.loops = numberOfLoops;
        this.warmingLoops = numberOfWarmingLoops;
        this.action = actionMaker;
        this.verificators = verifiers;
        this.runMode = runMode;
    }

    public void start() {
        collectTelemetryInfo();
        for (int i = 0; i < loops; i++) {
            action.run();
            collectTelemetryInfo();

            if (i >= warmingLoops) {
                for (int j = 0; j < verificators.length; j++) {
                    StateVerificator verifier = verificators[j];
                    if (!verifier.verify(telemetry, i)) {
                        Assert.fail("On iteration " + i + " telemetry verifier number " + j + " failed with results " + telemetry.get(i));
                    }
                }
            }

            if (runMode.equals(Mode.DEBUG)) {
                System.out.println("On iteration " + i + " telemetery : <" + telemetry.get(i) + ">");
            }
        }

        if (runMode.equals(Mode.DEBUG)) {
            Assert.fail("Runned in debug mode.");
        }
    }

    private void collectTelemetryInfo() {
        Parent<Control> parent1 = (Parent<Control>) testedControl.as(Parent.class, Control.class);
        int controls = parent1.lookup().size();
        Parent<Node> parent2 = (Parent<Node>) testedControl.as(Parent.class, Node.class);
        int nodes = parent2.lookup().size();
        Parent<javafx.scene.Parent> parent3 = (Parent<javafx.scene.Parent>) testedControl.as(Parent.class, javafx.scene.Parent.class);
        int containers = parent3.lookup().size();
        telemetry.add(Math.max(0, telemetry.size() - 1), new Measurement(containers, nodes, controls));
    }

    public static class Measurement {

        public int containersCount;
        public int nodesCount;
        public int controlsCount;

        public Measurement(int containers, int nodes, int controls) {
            this.containersCount = containers;
            this.controlsCount = controls;
            this.nodesCount = nodes;
        }

        @Override
        public String toString() {
            return "Containers : " + containersCount + "; Nodes : " + nodesCount + "; Controls : " + controlsCount + ".";
        }
    }

    public enum Mode {

        TEST, DEBUG
    };
}