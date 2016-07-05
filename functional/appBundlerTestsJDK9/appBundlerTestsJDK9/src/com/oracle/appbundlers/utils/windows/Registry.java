/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils.windows;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.oracle.appbundlers.utils.ProcessOutput;
import com.oracle.appbundlers.utils.Utils;

/**
 *
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt
 *
 *         The main purpose of this class is to provide methods for querying
 *         registry on Windows.
 *
 *         see {@link http://technet.microsoft.com/en-us/library/cc742028.aspx}
 *         for details about {@code reg query} syntax
 */
public class Registry {

    /**
     * Registry query command abstraction.
     */
    public interface Query {
        Optional<List<String>> execute();
    }

    /**
     * Builder to construct query with necessary options;
     */
    public interface QueryBuilder {
        QueryBuilder key(String keyName);

        QueryBuilder useDataPattern(String pattern);

        QueryBuilder searchSubkeys();

        QueryBuilder serarchInKeyNamesOnly();

        Query build();
    }

    private static final String SYSTEM_DIR = "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall";
    private static final String USER_DIR = "HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall";

    private Registry() {
    }

    /**
     * Method searches for the application {@code appName} entry. Method checks
     * first the HKLM. If entry not found than it is searched in HKCU.
     *
     * @param appName
     * @return Optional string which is the name of registry folder
     */
    public static Optional<String> findAppRegistryKey(String appName) {

        Query userDirQuery = getQueryBuilder().key(USER_DIR)
                .useDataPattern(appName).searchSubkeys().build();

        Query sysDirQuery = getQueryBuilder().key(SYSTEM_DIR)
                .useDataPattern(appName).searchSubkeys().build();

        List<String> output = sysDirQuery.execute().orElseGet(() -> {
            return userDirQuery.execute().orElse(Collections.emptyList());
        });

        return output.stream().map(String::trim)
                .filter(s -> s.startsWith(SYSTEM_DIR) || s.startsWith(USER_DIR))
                .findFirst();
    }

    /**
     * Method returns the content of registry folder.
     *
     * @param registryFolder
     * @return Optional collection of registry folder content
     */
    public static Optional<List<String>> queryKey(String registryFolder) {
        Query query = getQueryBuilder().key(registryFolder).build();
        return query.execute();
    }

    private static class QueryImpl implements Query {

        String[] command;

        @Override
        public Optional<List<String>> execute() {
            ProcessOutput out;
            try {
                out = Utils.runCommand(command, true,
                        CONFIG_INSTANCE.getRunTimeout());
                return Optional.of(out.getOutputStream());
            } catch (IOException | ExecutionException ex) {
                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null,
                        ex);
            }

            return Optional.empty();
        }

        @Override
        public String toString() {
            return Arrays.toString(command);
        }

    }

    private static class QueryBuilderImpl implements QueryBuilder {

        private String key;
        private String pattern;
        private boolean seachSubKeys;
        private boolean searchInKeyNamesOnly;

        @Override
        public QueryBuilder key(String key) {
            this.key = key;
            return this;
        }

        @Override
        public QueryBuilder useDataPattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        @Override
        public QueryBuilder searchSubkeys() {
            seachSubKeys = true;
            return this;
        }

        @Override
        public QueryBuilder serarchInKeyNamesOnly() {
            searchInKeyNamesOnly = true;
            return this;
        }

        @Override
        public Query build() {
            QueryImpl query = new QueryImpl();

            List<String> command = new ArrayList<>();

            command.add("REG");
            command.add("QUERY");

            require(key, "[Registry key must be provided]");
            command.add(key);

            if (seachSubKeys) {
                command.add("/s");
            }
            if (searchInKeyNamesOnly) {
                command.add("/k");
            }

            if (pattern != null) {
                command.add("/f");
                command.add(pattern);
            }
            query.command = command.toArray(new String[0]);
            return query;
        }

        private void require(Object obj, String explanation) {
            if (obj == null) {
                throw new IllegalStateException(explanation);
            }
        }

    }

    public static QueryBuilder getQueryBuilder() {
        return new QueryBuilderImpl();
    }
}
