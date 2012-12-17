/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
