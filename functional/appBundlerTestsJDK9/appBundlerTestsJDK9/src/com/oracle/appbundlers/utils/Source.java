/*
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.utils;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static java.nio.file.Files.write;
import static java.util.stream.Collectors.joining;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Source {

    private String packageName;
    private String simpleName;
    private String fileContent;
    private String jarName;
    private Map<String, String> replacementsInSourceCode;
    /*
     * JDK 9 Parameters
     */

    private String moduleName;
    private String moduleInfoContent;
    private Map<String, String> templateToClassNameMap = new HashMap<>();
    private boolean isModule;
    private boolean mainModule;

    /*
     * Jar Source
     */
    public Source(String fullName, String templateFileName, String jarName,
            Map<String, String> replacements) throws IOException {
        init(fullName, jarName);
        this.fileContent = readFileAsString(templateFileName, replacements);
        this.isModule = false;
    }

    /*
     * Jar Source
     */

    public Source(String fullName, String jar,
            Map<String, String> templateToClassNameMap,
            Map<String, Map<String, String>> templateToSourceCodeReplacementsMap)
                    throws IOException {
        init(fullName, jar);
        this.isModule = false;
        this.templateToClassNameMap = templateToClassNameMap;
    }

    /*
     * Module Source
     */

    public Source(String moduleName, String moduleInfoFileName,
            Map<String, String> templateToClassNameMap, String fullName,
            String jar, Map<String, String> replacementsInSourceCode)
                    throws IOException {
        init(fullName, jar);
        if (moduleName == null) {
            throw new NullPointerException("Module Name cannot be null");
        }
        this.moduleName = moduleName;
        if (moduleInfoFileName == null) {
            throw new NullPointerException(
                    "Module Info File Name cannot be null");
        }
        this.isModule = true;
        this.replacementsInSourceCode = replacementsInSourceCode;
        this.moduleInfoContent = readFileAsString(moduleInfoFileName,
                this.replacementsInSourceCode);
        this.templateToClassNameMap = templateToClassNameMap;
    }

    /*
     * Module Source
     */

    public Source(String moduleName, String moduleInfoFileName,
            Map<String, String> templateToClassNameMap, String fullName,
            String jar, Map<String, String> replacementsInSourceCode,
            boolean mainModule) throws IOException {
        this(moduleName, moduleInfoFileName, templateToClassNameMap, fullName,
                jar, replacementsInSourceCode);
        this.mainModule = mainModule;
    }

    private void init(String fullName, String jarName) {
        int lastDot = fullName.lastIndexOf(".");
        this.packageName = fullName.substring(0, lastDot);
        this.simpleName = fullName.substring(lastDot + 1);
        this.jarName = jarName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getSource() {
        return this.fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getJarName() {
        return this.jarName;
    }

    public void setJarName(String jarName) {
        this.jarName = jarName;
    }

    public String getFullName() {
        return this.packageName + "." + this.simpleName;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public boolean isModule() {
        return this.isModule;
    }

    void generateSourceForJar(Path srcDir) throws IOException {
        Path dir = srcDir
                .resolve(this.packageName.replace('.', File.separatorChar));
        Utils.createDir(dir);
        Path sourceFilePath = dir.resolve(this.simpleName + ".java");
        write(sourceFilePath, this.fileContent.getBytes());
    }

    void generateSourceForModule(Path srcDir) throws IOException {
        Path moduleDir = srcDir.resolve(moduleName);
        Utils.createDir(moduleDir);
        Path moduleInfoPath = moduleDir.resolve("module-info.java");
        write(moduleInfoPath, moduleInfoContent.getBytes());
        writeJavaSourceFilesToDir(moduleDir);
    }

    private String readFileAsString(String templateFileName,
            Map<String, String> replacements) throws IOException {
        String content = Files
                .lines(CONFIG_INSTANCE.getResourceFilePath(templateFileName))
                .collect(joining(System.lineSeparator()));
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            content = content.replace(entry.getKey(), entry.getValue());
        }
        return content;
    }

    private void writeJavaSourceFilesToDir(Path moduleDir) throws IOException {
        Set<Entry<String, String>> entrySet = this.templateToClassNameMap
                .entrySet();
        Iterator<Entry<String, String>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<java.lang.String, java.lang.String> entry = (Map.Entry<java.lang.String, java.lang.String>) iterator
                    .next();
            String fileNameInFullPath = entry.getValue();
            int lastIndexOf = fileNameInFullPath.lastIndexOf('.');
            String appNameDir = fileNameInFullPath.substring(0, lastIndexOf);
            String replaceAll = appNameDir.replaceAll(Pattern.quote("."),
                    Matcher.quoteReplacement(File.separator));
            File file = new File(
                    moduleDir.toString() + File.separator + replaceAll);
            file.mkdirs();
            String fileContent = readFileAsString(entry.getKey(),
                    replacementsInSourceCode);
            String fileName = fileNameInFullPath.substring(
                    fileNameInFullPath.lastIndexOf('.') + 1) + ".java";
            write(Paths.get(moduleDir.toString() + File.separator + replaceAll)
                    .resolve(fileName), fileContent.getBytes());
        }
    }

    public Map<String, String> getReplacementsInSourceCode() {
        return this.replacementsInSourceCode;
    }

    public boolean isMainModule() {
        return this.mainModule;
    }
}
