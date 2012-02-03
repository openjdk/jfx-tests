/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this License Header
 * Notice in each file.
 *
 * If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
 */

package org.jemmy.fx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import javafx.application.Application;
import org.jemmy.JemmyException;
import org.jemmy.env.Environment;

/**
 * Runs an FX application
 * @author shura
 */
public class AppExecutor {
    private Class mainClass;

    /**
     * @deprecated Use static void execute(Class<? extends Application> program, String... parameters)
     * @param mainClassName Name of the application main class
     */
    public AppExecutor(String mainClassName) throws ClassNotFoundException {
        this(Class.forName(mainClassName));
    }

    /**
     * @deprecated Use static void execute(Class<? extends Application> program, String... parameters)
     * @param mainClass the application main class
     */
    public AppExecutor(Class mainClass) {
        this.mainClass = mainClass;
    }

    /**
     * Runs the app with parameters
     * @deprecated Use static void execute(Class<? extends Application> program, String... parameters)
     * @param parameters
     */
    public void execute(final String... parameters) {
        execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Class[] argTypes = new Class[] { String[].class };
                    Method main = mainClass.getMethod("main", argTypes);
                    //String[] mainArgs = Arrays.copyOfRange(parameters, 1, parameters.length);
                    main.invoke(null, (Object)parameters);
                } catch (IllegalAccessException ex) {
                    throw new JemmyException("Unable to execute " + mainClass.getName(), ex);
                } catch (IllegalArgumentException ex) {
                    throw new JemmyException("Unable to execute " + mainClass.getName(), ex);
                } catch (InvocationTargetException ex) {
                    throw new JemmyException("Unable to execute " + mainClass.getName(), ex);
                } catch (NoSuchMethodException ex) {
                    throw new JemmyException("Unable to execute " + mainClass.getName(), ex);
                } catch (SecurityException ex) {
                    throw new JemmyException("Unable to execute " + mainClass.getName(), ex);
                }
            }
        });
    }

    /**
     * @deprecated Use static void execute(Class<? extends Application> program, String... parameters)
     * @param program
     */
    public static void execute(Runnable program) {
        Environment.getEnvironment().getOutput().printerr("It is recommended not to use " +
                AppExecutor.class.getName() + ".execute(Runnable)");
        Environment.getEnvironment().getOutput().printerr("use " +
                AppExecutor.class.getName() + ".execute(Class<? extends " + Application.class.getName() + ">) instead");
        // com.sun.javafx.application.PlatformImpl.startup(program); JJCBrowser is for work with FX app
        //                                                           which starts FX platform in the main()
        new Thread(program).start();
    }

    public static void execute(Class<? extends Application> program) {
        execute(program, new String[0]);
    }

    public static void execute(Class<? extends Application> program, String... parameters) {
        Application.launch(program, parameters);
    }

    public static void executeNoBlock(Class<? extends Application> program) {
        executeNoBlock(program, new String[0]);
    }

    public static void executeNoBlock(final Class<? extends Application> program, final String... parameters) {
        new Thread(new Runnable() {

            public void run() {
                execute(program, parameters);
            }
        }).start();
    }

    public static void executeReflect(String... argv) throws ClassNotFoundException {
        if (argv.length == 0) {
            throw new ClassNotFoundException("No class name to execute - empty parameters");
        }
        Class mainClass = Class.forName(argv[0]);

        String[] parameters = Arrays.copyOfRange(argv, 1, argv.length);

        if (Application.class.isAssignableFrom(mainClass)) {
            Class<? extends Application> app = mainClass;
            AppExecutor.execute(app, parameters);
            return;
        }
        // throw new ClassNotFoundException("Class doesn't extends " + 
        //                                   Application.class.getName() + ". Old way to start FX app isn't supported");
        // then use old deprecated method to start.
        new AppExecutor(mainClass).execute(parameters);
    }
}
