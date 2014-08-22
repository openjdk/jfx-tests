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
 * @author vshubov
 */
public class LookAndFeelQuestion extends ChoiceQuestion {

    /**
     *
     */
    public final static String UNDEFINED_SETTINGS = "Undefined(Default/now is Modena)";

    /**
     *
     */
    public final static String CASPIAN_SETTINGS = "Caspian(old)";

    /**
     *
     */
    public final static String MODENA_SETTINGS = "Modena(new)";

    /**
     *
     */
    public final static String LOOKANDFEEL_PARAM_NAME = "lookandfeelGroup";
    private Question nextQuestion;

    /**
     *
     * @param interview
     * @param nextQuestion
     */
    public LookAndFeelQuestion(Interview interview, Question nextQuestion) {
        super(interview, LOOKANDFEEL_PARAM_NAME);
        setChoices(new String[]{UNDEFINED_SETTINGS, CASPIAN_SETTINGS, MODENA_SETTINGS}, false);
        clear();
        this.interview = interview;
        this.nextQuestion = nextQuestion;
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
     * @return
     */
    @Override
    public String getText() {
        return "look and feel.";
    }

    /**
     *
     * @return
     */
    @Override
    public String getSummary() {
        return "look and feel";
    }

    /**
     *
     */
    @Override
    final public void clear() {
        setValue(UNDEFINED_SETTINGS);
    }

    /**
     *
     * @param map
     */
    @Override
    public void export(Map map) {
        if (value.equals(UNDEFINED_SETTINGS)) {
            map.put(LookAndFeelQuestion.LOOKANDFEEL_PARAM_NAME, "");
        }
        if (value.equals(MODENA_SETTINGS)) {
            map.put(LookAndFeelQuestion.LOOKANDFEEL_PARAM_NAME, "modena");
        }
        if (value.equals(CASPIAN_SETTINGS)) {
            map.put(LookAndFeelQuestion.LOOKANDFEEL_PARAM_NAME, "caspian");
        }
    }

    /**
     *
     * @param data
     */
    @Override
    public void load(Map data) {
        String newValue = (String) data.get(LOOKANDFEEL_PARAM_NAME);
        setValue(newValue != null ? newValue : UNDEFINED_SETTINGS);
    }
}
