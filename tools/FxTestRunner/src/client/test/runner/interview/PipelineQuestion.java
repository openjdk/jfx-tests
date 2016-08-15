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


public class PipelineQuestion extends StringQuestion {

    /**
     *
     */
    public final static String PIPELINE_PARAM_NAME = "pipeline";

    private Question nextQuestion;

    /**
     *
     * @param interview
     * @param nextQuestion
     */
    public PipelineQuestion (Interview interview, Question nextQuestion) {
        super (interview, PIPELINE_PARAM_NAME);
        this.nextQuestion = nextQuestion;
    }

    /**
     *
     */
    @Override
    public void clear() {
        setValue("");
    }

    /**
     *
     * @return
     */
    @Override
    public String getText() {
        return "Pipeline option. Can be \"es1\", \"es2\", \"j2d\" and \"d3d\" or "
                + "a sequence of these values, e.g. \"es2,j2d\"";
    }

    /**
     *
     * @return
     */
    @Override
    public String getSummary() {
        return "Pipeline option";
    }

    /**
     *
     * @return
     */
    @Override
    public Question getNext() {
        return nextQuestion;
    }

    /**
     *
     * @param data
     */
    @Override
    public void export(Map data) {
        data.put(PIPELINE_PARAM_NAME, value);
    }

    /**
     *
     * @param data
     */
    @Override
    public void load(Map data) {
        String newValue = (String) data.get(PIPELINE_PARAM_NAME);
        setValue(newValue != null ? newValue : "");
    }
}

