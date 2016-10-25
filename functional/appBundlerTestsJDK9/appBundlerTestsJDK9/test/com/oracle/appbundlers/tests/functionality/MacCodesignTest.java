/*
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.MAC_APP;
import static com.oracle.appbundlers.utils.BundlerUtils.PKG;
import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.Utils;

/**
 *
 * @author dmitriy.ermashov@oracle.com
 */

/**
 * Tests {@code mac.signing-keychain} option on Mac only.
 */

public class MacCodesignTest extends TestBase {

    File keychain;

    @Override
    public void customBeforeClassHook() throws Exception {
        // creating fake certificates
        if (Utils.isMacOS()) {
            Map<String, Object> basicParams = this.currentParameter
                    .createNewBasicParams();
            File certsDir = new File(
                    CONFIG_INSTANCE.getResourceDir() + "/certs");
            certsDir.mkdir();

            try {
                keychain = new File(System.getenv("HOME")
                        + "/Library/Keychains/pkg.keychain");
                if (keychain.exists())
                    keychain.delete();

                // create the SSL keys for app
                Utils.runCommand(
                        Arrays.asList("openssl", "req", "-newkey", "rsa:2048",
                                "-nodes", "-out", certsDir + "/app.csr",
                                "-keyout", certsDir + "/app.key", "-subj",
                                "/CN=Developer ID Application: Insecure Test Cert/OU=JavaFX SQE/O=Oracle/C=US"),
                        true, CONFIG_INSTANCE.getRunTimeout());

                // first, for the app
                // create the cert
                Utils.runCommand(Arrays.asList("openssl", "x509", "-req",
                        "-days", "10", "-in", certsDir + "/app.csr", "-signkey",
                        certsDir + "/app.key", "-out", certsDir + "/app.crt",
                        "-extfile", CONFIG_INSTANCE.getResource("cert.cfg"),
                        "-extensions", "codesign"), true,
                        CONFIG_INSTANCE.getRunTimeout());

                // create and add it to the keychain
                Utils.runCommand(
                        Arrays.asList("certtool", "i", certsDir + "/app.crt",
                                "k=" + "pkg.keychain",
                                "r=" + certsDir + "/app.key", "c", "v", "p="),
                        true, CONFIG_INSTANCE.getRunTimeout());

                // create the SSL keys for pkg
                Utils.runCommand(
                        Arrays.asList("openssl", "req", "-newkey", "rsa:2048",
                                "-nodes", "-out", certsDir + "/pkg.csr",
                                "-keyout", certsDir + "/pkg.key", "-subj",
                                "/CN=Developer ID Installer: Insecure Test Cert/OU=JavaFX SQE/O=Oracle/C=US"),
                        true, CONFIG_INSTANCE.getRunTimeout());

                // now for the pkg cert
                Utils.runCommand(Arrays.asList("openssl", "x509", "-req",
                        "-days", "10", "-in", certsDir + "/pkg.csr", "-signkey",
                        certsDir + "/pkg.key", "-out", certsDir + "/pkg.crt",
                        "-extfile", CONFIG_INSTANCE.getResource("cert.cfg"),
                        "-extensions", "productbuild"), true,
                        CONFIG_INSTANCE.getRunTimeout());

                // create and add it to the keychain
                Utils.runCommand(
                        Arrays.asList("certtool", "i", certsDir + "/pkg.crt",
                                "k=" + "pkg.keychain",
                                "r=" + certsDir + "/pkg.key", "v"),
                        true, CONFIG_INSTANCE.getRunTimeout());
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    Utils.removeRecursive(certsDir.toPath(), true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] { MAC_APP, PKG };
    }

    protected AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(SIGNING_KEYCHAIN, keychain.toString());
            return additionalParams;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
    }
}
