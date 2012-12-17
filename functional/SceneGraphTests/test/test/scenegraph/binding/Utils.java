/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.binding;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Utility class to support binding tests. Don't use it.
 *
 * @author Sergey Grinev
 */
public class Utils {
    
    public static void main(String[] args) throws Exception {
        //generateTests(Factories.buttonGraphic, false);
        generateAllTests(false);
        //generateFactories();
        //constraintsCheck();
       // BindingApp.populateList(Factories.Circle, false);
    }

    private static void constraintsCheck() {
        for (Factories factory : Factories.values()) for (Object walle : BindingApp.populateList(factory, false));
    }

    private static void generateAllTests(boolean onlyNew) {
        for (Factories factory : Factories.values()) {
            generateTests(factory, onlyNew);
        }
    }

    private static void generateTests(Factories factory, boolean onlyNew) {
        File dir = new File(prefix + factory.packageName.name());
        File f = new File(dir, factory.name() + "Test.java");
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            if (f.exists()) {
                if (onlyNew) {
                    return;
                }
                File backupFile = new File(f.getAbsolutePath() + ".orig");
                if (backupFile.exists()) {
                    backupFile.delete();
                }
                f.renameTo(backupFile);
            }
        }
        try {
            FileWriter fstream = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(String.format(fileTemplate, factory.name(), factory.packageName.name()));
            for (Constraint c : BindingApp.populateList(factory, false)) {
                out.write(String.format(testTemplate, c.toString(), factory.packageName.name(),
                        factory.name(), c.getClass().getSimpleName() +"."+c.toString(), factory.packageName.fullName));
            }
            out.write("}\n");
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static final String packagePrefix = "test.scenegraph.binding";
    private static final String prefix = "test/" + packagePrefix.replace(".", "/") + "/";
    private static final String testTemplate =
            "    /**\n"
            + "    * This test verifies %1$s property for %2$s.%3$s\n"
            + "    */\n"
            + "    @Test\n"
            // TODO: biderectional binding
            //+ "    @Covers(value=\"%5$s.%3$s.%1$s.BIND\", level=Level.FULL)\n"
            + "    public void %1$s() {\n"
            + "        commonTest(%4$s);\n"
            + "    }\n\n";
    
    private static final String fileTemplate =
            "package test.scenegraph.binding.%2$s;\n"
            + "\n"
            + "/*\n"
            + " * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.\n"
            + " * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.\n"
            + " */\n"
            + "\n"
            + "\n"
            + "import org.junit.BeforeClass;\n"
            + "import org.junit.Test;\n"
            + "import test.scenegraph.binding.*;\n"
            + "import com.oracle.jdk.sqe.cc.markup.Covers;\n"
            + "import com.oracle.jdk.sqe.cc.markup.Covers.Level;\n"
            + "\n"
            + "public class %1$sTest extends BindingTestBase {\n"
            + "    @BeforeClass\n"
            + "    public static void runUI() {\n"
            + "        BindingApp.factory = Factories.%1$s;\n"
            + "        BindingApp.main(null);\n"
            + "    }\n"
            + "\n"
            + "    public static void main(String[] args) {\n"
            + "        runUI();\n"
            + "    }\n\n";

    private static void generateFactories() throws Exception {
        final String packageName = "javafx.scene.control";
        final String testPackageName = "controls";

        String jarPath = "..\\lib\\jfxrt.jar";
        JarFile jarFile = new JarFile(jarPath);
        String relPath = packageName.replace('.', '/');
        Enumeration<JarEntry> entries = jarFile.entries();
        List<Class> list = new ArrayList<Class>();
        while(entries.hasMoreElements()) {
            String name = entries.nextElement().getName();
            if(name.startsWith(relPath) && name.length() > (relPath.length() + 1) && !name.contains("$")) {
                if (name.indexOf("$") > -1) continue;
                String className = name.replace('/', '.').replace('\\', '.').replace(".class", "");
                try {
                    Class clazz = Class.forName(className);
                    boolean bindarable = false;
                    for (Method method : clazz.getMethods()) {
                        if (method.getName().endsWith("Property")) {
                            bindarable = true;
                            break;
                        }
                    }
                    if (bindarable) {
                        list.add(clazz);
                    }
                } 
                catch (ClassNotFoundException e) {
                    System.err.println("Class Not Found: " + className);
                }
            }
        }

        System.out.println("Factories for " + packageName);
        for (Class clazz : list) {

            System.out.print(String.format(factoryTemplate, clazz.getSimpleName(), testPackageName));
        }
    }

    private static final String factoryTemplate =
            "    %1$s(%2$s, new DefaultFactory() {\n\n"
            + "        public NodeAndBindee create() {\n"
            + "            %1$s node = new %1$s();\n"
            + "            addStroke(node);\n"
            + "            return new BindingApp.NodeAndBindee(node, node);\n"
            + "        }\n"
            + "    }),\n";
}
