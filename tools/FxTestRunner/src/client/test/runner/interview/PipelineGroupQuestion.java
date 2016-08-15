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

package client.test.runner.interview;

import com.sun.interview.*;
import java.util.Map;

/**
 *
 * @author Dmitry Ginzburg
 */

public class PipelineGroupQuestion extends ChoiceQuestion {

    /**
     *
     */
    public final static String HARDWARE_SETTINGS = "Hardware(Default)";

    /**
     *
     */
    public final static String SOFTWARE_SETTINGS = "Software";

    /**
     *
     */
    public final static String CUSTOM_SETTINGS = "Custom";

    /**
     *
     */
    public final static String PIPELINE_GROUP_PARAM_NAME = "pipelineGroup";

    private Question nextQuestion;
    private Interview interview;
    private PipelineQuestion pipeline = null;

    /**
     *
     * @param interview
     * @param nextQuestion
     */
    public PipelineGroupQuestion (Interview interview, Question nextQuestion) {
        super (interview, PIPELINE_GROUP_PARAM_NAME);
        setChoices(new String[]{HARDWARE_SETTINGS, SOFTWARE_SETTINGS, CUSTOM_SETTINGS}, false);
        clear();
        this.interview = interview;
        this.nextQuestion = nextQuestion;
    }

    /**
     *
     * @return
     */
    @Override
    public Question getNext () {
        if (getValue().toString().equals(CUSTOM_SETTINGS)) {
            if (pipeline == null)
                pipeline = new PipelineQuestion (interview, nextQuestion);
            return pipeline;
        } else
            return nextQuestion;
    }

    /**
     *
     * @return
     */
    @Override
    public String getText() {
        return "Software=\"sw\". Hardware is default.";
    }

    /**
     *
     * @return
     */
    @Override
    public String getSummary() {
        return "Settings to use";
    }

    /**
     *
     */
    @Override
    public void clear() {
        setValue(HARDWARE_SETTINGS);
    }

    /**
     *
     * @param map
     */
    @Override
    public void export(Map map) {
        if (value.equals(HARDWARE_SETTINGS)) {
            map.put(PipelineQuestion.PIPELINE_PARAM_NAME, "");
        }
        if (value.equals(SOFTWARE_SETTINGS)) {
            map.put(PipelineQuestion.PIPELINE_PARAM_NAME, "sw");
        }
    }

    /**
     *
     * @param data
     */
    @Override
    public void load(Map data) {
        setValue((String) data.get(PIPELINE_GROUP_PARAM_NAME));
    }
}

