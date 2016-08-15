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
package javafx.scene.control.test.focus;

import java.io.IOException;
import java.util.Set;
import javafx.factory.ControlsFactory;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Andrey Glushchenko
 */
@RunWith(FilteredTestRunner.class)
public class FocusTestGenerator extends ControlsTestGeneratorBase {

    public enum TestCase {

        Uniqueness, Traversable, Style, Owner, Disabled, DisabledDrop
    }

    public enum TestInput {

        Click, Keyboard, Request
    }
    private static String TEMPLATE = "Template";
    private Set<ControlsFactory> excludeSet = null;

    protected void generateTests(TestCase c, TestInput input, String javadoc) throws IOException {
        String className = "Focus" + c.name() + input.name() + "Test";
        String template = read(TEMPLATE);
        template += "public class " + className + " extends Focus" + input.name() + "TestBase {\n";
        template += getTestFunction(c);
        initExcludeSet(c, input);
        generateTests(template, className + ".java", javadoc);
    }

    @Override
    protected void generateTest(ControlsFactory cf, StringBuilder sb, String javadoc) {
        if (!excludeSet.contains(cf)) {
            sb.append("\n    /**\n     * ").append(javadoc).append(" for ").append(cf.name()).append("\n    **/\n");
            sb.append("    @Test(timeout=20000)\n");
            if(cf == ControlsFactory.MediaView) {
                sb.append("    @Keywords(keywords = \"media\")\n");
            }
            sb.append("    public void test_").append(cf.name()).append("() throws Exception {\n");
            sb.append("        test(ControlsFactory.").append(cf.name()).append(".name());\n");
            sb.append("    }\n");
        }
    }

    private String getTestFunction(TestCase c) {
        String function = "    private void test(String name) throws Exception{\n"
                + "        openPage(name);\n";
        switch (c) {
            case Owner:
                function += "        focusOwnerTest();\n";
                break;
            case Style:
                function += "        focusStyleTest();\n";
                break;
            case Traversable:
                function += "        focusTraversableTest();\n";
                break;
            case Uniqueness:
                function += "        focusUniquenessTest();\n";
                break;
            case Disabled:
                function += "        focusDisabledTest();\n";
                break;
            case DisabledDrop:
                function += "        focusDisabledDropTest();\n";
                break;
        }
        function += "    }\n";
        return function;
    }

    private void initExcludeSet(TestCase c, TestInput input) {
        switch (c) {
            case Owner:
                excludeSet = FocusTestBase.getOwnerExcludeSet();
                break;
            case Style:
                excludeSet = FocusTestBase.getStyleExcludeSet();
                break;
            case Traversable:
                excludeSet = FocusTestBase.getFTExcludeSet();
                break;
            case Uniqueness:
                excludeSet = FocusTestBase.getUniquenessExcludeSet();
                break;
        }
        switch (input) {
            case Click:
                excludeSet.addAll(FocusClickTestBase.getExcludeSet());
                break;
            case Keyboard:
                excludeSet.addAll(FocusKeyboardTestBase.getExcludeSet());
                break;
            case Request:
                excludeSet.addAll(FocusRequestTestBase.getExcludeSet());
                break;
        }

        excludeSet.add(ControlsFactory.Menubars);
    }

    public static void main(String[] args) throws IOException {
        FocusTestGenerator ftg = new FocusTestGenerator();
        for (TestCase tc : TestCase.values()) {
            for (TestInput input : TestInput.values()) {
                ftg.generateTests(tc, input, "");
            }
        }

    }
}
