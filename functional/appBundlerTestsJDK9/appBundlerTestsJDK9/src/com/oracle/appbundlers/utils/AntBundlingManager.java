/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.oracle.appbundlers.utils.installers.AbstractBundlerUtils;
import com.oracle.tools.packager.ConfigException;
import com.oracle.tools.packager.RelativeFileSet;
import com.oracle.tools.packager.UnsupportedPlatformException;
import com.sun.javafx.tools.packager.bundlers.BundleParams;

/**
 * @author Andrei Eremeev &lt;andrei.eremeev@oracle.com&gt;
 */
public class AntBundlingManager extends BundlingManager {

    private static final Logger LOG = Logger
            .getLogger(AntBundlingManager.class.getName());

    @SuppressWarnings("serial")
    private final static Map<String, Location> toAntEntry = new HashMap<String, Location>() {
        {
            put(APP_RESOURCES, new Location("fx:resources", ""));
            put(LICENSE_FILE, new Location("fx:resources", ""));
            put(APPLICATION_CLASS, new Location("fx:application", "mainClass"));
            put(IDENTIFIER, new Location("fx:application", "id"));
            put(VERSION, new Location("fx:application", "version"));
            put(APP_NAME,
                    new Location("fx:application", "name"));
            put(VENDOR, new Location("fx:info", "vendor"));
            put(TITLE, new Location("fx:info", "title"));
            put(DESCRIPTION, new Location("fx:info", "description"));
            put(ICON, new Location("fx:info", ""));
            put(EMAIL, new Location("fx:info", "email"));
            put(COPYRIGHT, new Location("fx:info", "copyright"));
            put(LICENSE_TYPE, new Location("fx:info", "license"));
            put(CATEGORY, new Location("fx:info", "category"));
            put(SHORTCUT_HINT, new Location("fx:preferences", "shortcut"));
            put(MENU_HINT, new Location("fx:preferences", "menu"));
            put(SYSTEM_WIDE, new Location("fx:preferences", "install"));
            put(BundleParams.PARAM_RUNTIME,
                    new Location("fx:platform", "baseDir"));
            put(JVM_OPTIONS, new Location("fx:platform", ""));
            put(JVM_PROPERTIES, new Location("fx:platform", ""));
            put(USER_JVM_OPTIONS, new Location("fx:platform", ""));
            put(ARGUMENTS, new Location("fx:application", ""));
            put(FILE_ASSOCIATIONS, new Location("fx:info", ""));
            put(SECONDARY_LAUNCHERS, Location.DUMMY);
            put(STRIP_NATIVE_COMMANDS, new Location("fx:runtime", STRIP_NATIVE_COMMANDS));
            put(ADD_MODS, new Location("fx:runtime", ""));
            put(LIMIT_MODS, new Location("fx:runtime",""));
            put(MODULEPATH, new Location("fx:runtime",""));
            put(MAIN_MODULE, new Location("fx:application", MAIN_MODULE));
        }
    };

    public AntBundlingManager(AbstractBundlerUtils bundler) {
        super(bundler);
    }

    @Override
    public boolean validate(Map<String, Object> params)
            throws UnsupportedPlatformException, ConfigException {
        return true;
    }

    private Data createDocument()
            throws DOMException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element project = document.createElement("project");
        project.setAttribute("default", "fx-deploy");
        project.setAttribute("xmlns:fx", "javafx:com.sun.javafx.tools.ant");
        document.appendChild(project);

        Element taskDef = document.createElement("taskdef");
        taskDef.setAttribute("resource", "com/sun/javafx/tools/ant/antlib.xml");
        taskDef.setAttribute("uri", "javafx:com.sun.javafx.tools.ant");
        taskDef.setAttribute("classpath", CONFIG_INSTANCE.getAntJavaFx());
        project.appendChild(taskDef);

        Element target = document.createElement("target");
        target.setAttribute("name", "fx-deploy");
        project.appendChild(target);
        return new Data(document, target);
    }

    private String documentToXml(Document document)
            throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setAttribute("indent-number", 4);
        Transformer transformer = factory.newTransformer();
        DOMSource source = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(new PrintWriter(writer));
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(source, result);
        return writer.toString();
    }

    @SuppressWarnings("unchecked")
    private void appendToFXDeploy(Document document, Element fxDeploy,
            Map<String, Object> params) throws IOException {
        final Map<String, Element> ant = toAntEntry.values().stream()
                .filter(location -> location != Location.DUMMY)
                .map(location -> location.element).distinct().collect(Collectors
                        .toMap(Function.identity(), document::createElement));
        ant.forEach((str, element) -> fxDeploy.appendChild(element));
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key = " + key);
            System.out.println("value = " + value);
            Location location = toAntEntry.get(key);
            if (location != null) {
                Element parentEl = ant.get(location.element);
                switch (key) {
                case "appResources": {
                    if(ExtensionType.NormalJar == extensionType) {
                        RelativeFileSet relFileSet = (RelativeFileSet) value;
                        Element e = document.createElement("fx:fileset");
                        e.setAttribute("dir",
                                relFileSet.getBaseDirectory().getAbsolutePath());
                        e.setAttribute("includes", relFileSet.getIncludedFiles()
                                .stream().collect(joining(",")));
                        parentEl.appendChild(e);
                    }
                    break;
                }
                case "appResourcesList":
                    break;
                case "licenseFile": {
                    String file = (String) value;
                    Element e = document.createElement("fx:fileset");
                    RelativeFileSet relFileSet = null;
                    relFileSet = com.oracle.tools.packager.StandardBundlerParam.APP_RESOURCES.fetchFrom(params);
                    e.setAttribute("dir",
                            relFileSet.getBaseDirectory().getAbsolutePath());
                    e.setAttribute("includes", file);
                    e.setAttribute("type", "license");
                    parentEl.appendChild(e);
                    break;
                }
                case "icon": {
                    File icon = (File) value;
                    Element e = document.createElement("fx:icon");
                    e.setAttribute("href", icon.getAbsolutePath());
                    parentEl.appendChild(e);
                    break;
                }
                case "jvmOptions": {
                    createJvmOptionsEntries(document, parentEl, value);
                    break;
                }
                case "jvmProperties": {
                    createJvmPropertiesEntries(document, parentEl, value);
                    break;
                }
                case "userJvmOptions": {
                    createUserJvmOptionsEntries(document, parentEl, value);
                    break;
                }
                case "secondaryLaunchers": {
                    List<Map<String, Object>> launchers = (List<Map<String, Object>>) value;
                    for (Map<String, Object> eachLauncher : launchers) {
                        Element launcherEl = document
                                .createElement("fx:secondaryLauncher");

                        for (Map.Entry<String, Object> keyVal : eachLauncher
                                .entrySet()) {

                            switch (keyVal.getKey()) {
                            case "jvmOptions": {
                                createJvmOptionsEntries(document, launcherEl,
                                        keyVal.getValue());
                                break;
                            }
                            case "jvmProperties": {
                                createJvmPropertiesEntries(document, launcherEl,
                                        keyVal.getValue());
                                break;
                            }
                            case "userJvmOptions": {
                                createUserJvmOptionsEntries(document,
                                        launcherEl, keyVal.getValue());
                                break;
                            }
                            case "arguments": {
                                createArgumentEntries(document, launcherEl,
                                        keyVal.getValue());
                                break;
                            }

                            case MAIN_MODULE:
                                launcherEl.setAttribute(MAIN_MODULE, (String) keyVal.getValue());
                            break;

                            case APPLICATION_CLASS:
                                launcherEl.setAttribute("mainClass", (String) keyVal.getValue());
                            break;

                            default:
                                Element bundlerArgumentEntry = createBundleArgumentEntry(document,
                                        keyVal.getKey(),
                                        keyVal.getValue());
                                if(bundlerArgumentEntry != null) {
                                    launcherEl.appendChild(
                                            bundlerArgumentEntry);
                                }
                            }
                        }

                        fxDeploy.appendChild(launcherEl);
                    }
                    break;
                }
                case "runtime": {
                    RelativeFileSet relFileSet = (RelativeFileSet) value;
                    parentEl.setAttribute(location.attribute,
                            relFileSet.getBaseDirectory().getAbsolutePath());
                    break;
                }
                case "arguments": {
                    createArgumentEntries(document, parentEl, value);
                    break;
                }
                case "fileAssociations": {
                    List<Map<String, Object>> associations = (List<Map<String, Object>>) value;
                    for (Map<String, Object> association : associations) {
                        Element el = document.createElement("fx:association");
                        List<String> extensions = (List<String>) association
                                .get(FA_EXTENSIONS);
                        List<String> contentTypes = (List<String>) association
                                .get(FA_CONTENT_TYPE);
                        String description = (String) association
                                .get(FA_DESCRIPTION);
                        el.setAttribute("extension", extensions.stream()
                                .collect(Collectors.joining(" ")));
                        el.setAttribute("mimetype", contentTypes.stream()
                                .collect(Collectors.joining(" ")));
                        if (description != null) {
                            el.setAttribute("description", description);
                        }
                        File icon = (File) association.get(FA_ICON);
                        if (icon != null) {
                            el.setAttribute("icon", icon.getAbsolutePath());
                        }
                        parentEl.appendChild(el);
                    }
                    break;
                }

                case STRIP_NATIVE_COMMANDS:
                    parentEl.setAttribute(STRIP_NATIVE_COMMANDS,
                            value.toString());
                    break;
                case ADD_MODS:
                    Element addModsElement = document
                            .createElement("fx:" + ADD_MODS);
                    addModsElement.setAttribute("value", getValueAsString(value));
                    parentEl.appendChild(addModsElement);
                    break;
                case LIMIT_MODS:
                    Element limitModsElement = document
                            .createElement("fx:" + LIMIT_MODS);
                    limitModsElement.setAttribute("value", getValueAsString(value));
                    parentEl.appendChild(limitModsElement);
                    break;
                case MODULEPATH:
                    Element modulePath = document
                            .createElement("fx:" + MODULEPATH);
                    modulePath.setAttribute("value", getValueAsString(value));
                    parentEl.appendChild(modulePath);
                    break;
                case MAIN_MODULE:
                    if(value instanceof String) {
                        parentEl.setAttribute(MAIN_MODULE, ((String) value).split("/")[0]);
                        parentEl.setAttribute("mainClass", ((String) value).split("/")[1]);
                    }
                    break;
                default:
                    checkValue(value);
                    parentEl.setAttribute(location.attribute, value.toString());
                    break;
                }
            } else {
                Element bundleArgumentEntry = createBundleArgumentEntry(document, key, value);
                if(bundleArgumentEntry != null) {
                    fxDeploy.appendChild(
                            bundleArgumentEntry);
                }
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private String getValueAsString(Object value) {
        String actualValue = null;
        if (value instanceof String) {
            actualValue = value.toString();
        } else if (value instanceof List) {
            actualValue = String.join(",", (List) value);
        } else if (value instanceof Set) {
            actualValue = String.join(",", (Set) value);
        } else if (value instanceof Object) {
            actualValue = value.toString();
        }
        return actualValue;
    }

    private Element createBundleArgumentEntry(Document document, String argName,
            Object value) throws IOException {
        if ("appResourcesList".equals(argName)) {
            return null;
        }
        Element bundleArgument = document.createElement("fx:bundleArgument");
        String argValue = null;
        if ("mainJar".equals(argName)) {
//            RelativeFileSet fileSet = (RelativeFileSet) value;
//            argValue = fileSet.getIncludedFiles().iterator().next();
            argValue = (String) value;
        } else {
            checkValue(value);
            argValue = value.toString();
        }
        bundleArgument.setAttribute("arg", argName);
        bundleArgument.setAttribute("value", argValue);
        return bundleArgument;
    }

    @SuppressWarnings("unchecked")
    private void createJvmOptionsEntries(Document document, Element parentEl,
            Object value) {
        Collection<String> col = (Collection<String>) value;
        col.forEach(arg -> {
            Element e = document.createElement("fx:jvmarg");
            e.setAttribute("value", arg);
            parentEl.appendChild(e);
        });
    }

    @SuppressWarnings("unchecked")
    private void createJvmPropertiesEntries(Document document, Element parentEl,
            Object value) {
        Map<String, String> properties = (Map<String, String>) value;
        properties.forEach((k, v) -> {
            Element e = document.createElement("fx:property");
            e.setAttribute("name", k);
            e.setAttribute("value", v);
            parentEl.appendChild(e);
        });
    }

    @SuppressWarnings("unchecked")
    private void createUserJvmOptionsEntries(Document document,
            Element parentEl, Object value) {
        Map<String, String> properties = (Map<String, String>) value;
        properties.forEach((k, v) -> {
            Element e = document.createElement("fx:jvmuserarg");
            e.setAttribute("name", k);
            e.setAttribute("value", v);
            parentEl.appendChild(e);
        });
    }

    @SuppressWarnings("unchecked")
    private void createArgumentEntries(Document document, Element parentEl,
            Object value) {
        List<String> arguments = (List<String>) value;
        for (String arg : arguments) {
            Element e = document.createElement("fx:argument");
            e.setTextContent(arg);
            parentEl.appendChild(e);
        }
    }

    private void checkValue(Object value) throws IOException {
        if (!(value instanceof String) && !(value instanceof Boolean)
                && !(value instanceof File)) {
            throw new IOException(format("Value is not mapped : %s, %s",
                    value.getClass(), value.toString()));
        }
    }

    private Element fxDeploy(Document document, Map<String, Object> params,
            File file) throws IOException {
        Element fxDeploy = document.createElement("fx:deploy");
        String bundleType = getBundler().getBundleType();
        fxDeploy.setAttribute("nativeBundles",
                "image".equalsIgnoreCase(bundleType) ? "image"
                        : getBundler().getID());
        if (!file.getName().equals("bundles")) {
            throw new IllegalArgumentException(
                    "Invalid bundle directory : " + file);
        }
        fxDeploy.setAttribute("outdir", file.getAbsolutePath());
        fxDeploy.setAttribute("outfile", "test");
        fxDeploy.setAttribute("verbose", "true");
        appendToFXDeploy(document, fxDeploy, params);
        return fxDeploy;
    }

    private void writeToFile(Path file, String buildXml) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file,
                StandardCharsets.UTF_8)) {
            writer.append(buildXml);
        }
    }

    @Override
    public File execute(Map<String, Object> params, File file)
            throws IOException {
       return execute(params, file, false);
    }

    @Override
    public File execute(Map<String, Object> params, File file,
            boolean isSrcDirRequired) throws IOException {
        try {
            Data data = createDocument();
            Element fxDeploy = fxDeploy(data.document, params, file);
            data.fxDeployTarget.appendChild(fxDeploy);
            Path buildXmlFile = Utils.getTempDir().resolve("build.xml");
            String buildXml = documentToXml(data.document);
            // TODO: WTF?
            LOG.log(Level.INFO, "build.xml:\n{0}\n", buildXml);
            writeToFile(buildXmlFile, buildXml);

            final List<String> command = asList(CONFIG_INSTANCE.antExec(), "-f",
                    buildXmlFile.toString());

            @SuppressWarnings("serial")
            ProcessOutput process = Utils.runCommand(command,
                    /* verbose = */ true,
                    /* timeout = */ CONFIG_INSTANCE.getInstallTimeout(),
                    new HashMap<String, String>() {
                        {
                            put("JAVA_HOME", CONFIG_INSTANCE.getJavaHome());
                        }
                    });
            if (process.isTimeoutExceeded()) {
                throw new IOException(
                        "The command " + command + " hasn't finished in "
                                + CONFIG_INSTANCE.getInstallTimeout()
                                + " milliseconds");
            }

            if (process.exitCode() != 0) {
                throw new IOException(
                        "Process finished with not zero exit code");
            }
            // TODO: Proper exception handle?
            return file;
        } catch (DOMException | ParserConfigurationException
                | TransformerException | ExecutionException e) {
            throw new IOException(e);
        }
    }

    @Override
    public String getShortName() {
        return "ANT";
    }

    private static class Location {

        public static final Location DUMMY = new Location("dummy", "dummy");

        public final String element;
        public final String attribute;

        public Location(String element, String attribute) {
            this.element = element;
            this.attribute = attribute;
        }
    }

    private static class Data {

        public final Document document;
        public final Element fxDeployTarget;

        public Data(Document document, Element fxDeploy) {
            this.document = document;
            this.fxDeployTarget = fxDeploy;
        }
    }
}
