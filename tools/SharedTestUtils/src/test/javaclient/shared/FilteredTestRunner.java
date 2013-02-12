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
package test.javaclient.shared;

import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class FilteredTestRunner extends BlockJUnit4ClassRunner {

    public FilteredTestRunner(Class testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
        String annotation_expr = System.getProperty("javatest.testset.annotations");
        if ((annotation_expr != null) && !annotation_expr.isEmpty()) {
            String[] arguments  = annotation_expr.split("&|!()");
            for (int i = 0; i < arguments.length; i++) {
                if (!arguments[i].isEmpty()) {
                    try {
                        Class<? extends Annotation> annotation = (Class<? extends Annotation>) Class.forName("client.test." + arguments[i]);
                        annotation_expr = annotation_expr.replace(arguments[i], String.valueOf(getTestClass().getAnnotatedMethods(annotation).contains(method)));
                    } catch (ClassNotFoundException ex) {
                        annotation_expr = annotation_expr.replace(arguments[i], "false");
                        Logger.getLogger(FilteredTestRunner.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            annotation_expr = annotation_expr.replace("&", "&&");
            annotation_expr = annotation_expr.replace("|", "||");

            try {
                Object script_result = SwingAWTUtils.evalScript(annotation_expr);
                if (!Boolean.class.cast(script_result)) {
                    Description description = describeChild(method);
                    new EachTestNotifier(notifier, description).fireTestIgnored();
                    return;
            }
            } catch (Exception ex) {
                Logger.getLogger(FilteredTestRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        super.runChild(method, notifier);
    }
}
