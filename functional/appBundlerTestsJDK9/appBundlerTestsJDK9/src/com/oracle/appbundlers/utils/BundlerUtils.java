/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils;

import static com.oracle.appbundlers.utils.Utils.isDpkgPresent;
import static com.oracle.appbundlers.utils.Utils.isLinux;
import static com.oracle.appbundlers.utils.Utils.isMacOS;
import static com.oracle.appbundlers.utils.Utils.isWindows;

import com.oracle.appbundlers.utils.installers.AbstractBundlerUtils;
import com.oracle.appbundlers.utils.installers.LinuxAppBundlerUtils;
import com.oracle.appbundlers.utils.installers.LinuxDebBundlerUtils;
import com.oracle.appbundlers.utils.installers.LinuxRPMBundlerUtils;
import com.oracle.appbundlers.utils.installers.MacAppBundlerUtils;
import com.oracle.appbundlers.utils.installers.MacDMGBundlerUtils;
import com.oracle.appbundlers.utils.installers.MacPKGBundlerUtils;
import com.oracle.appbundlers.utils.installers.WinAppBundlerUtils;
import com.oracle.appbundlers.utils.installers.WinExeBundlerUtils;
import com.oracle.appbundlers.utils.installers.WinMsiBundlerUtils;

/**
 *
 * @author Andrei Eremeev &lt;andrei.eremeev@oracle.com&gt;
 */
public enum BundlerUtils {
    LINUX_APP("linux.app") {
        @Override
        public AbstractBundlerUtils getBundlerUtils() {
            return new LinuxAppBundlerUtils();
        }

        @Override
        public boolean isSupported() {
            return isLinux();
        }
    },
    DEB("deb") {
        @Override
        public AbstractBundlerUtils getBundlerUtils() {
            return new LinuxDebBundlerUtils();
        }

        @Override
        public boolean isSupported() {
            return isDpkgPresent();
        }
    },
    RPM("rpm") {
        @Override
        public AbstractBundlerUtils getBundlerUtils() {
            return new LinuxRPMBundlerUtils();
        }

        @Override
        public boolean isSupported() {
            return isLinux();
        }
    },
    MAC_APP("mac.app") {
        @Override
        public AbstractBundlerUtils getBundlerUtils() {
            return new MacAppBundlerUtils();
        }

        @Override
        public boolean isSupported() {
            return isMacOS();
        }
    },
    DMG("dmg") {
        @Override
        public AbstractBundlerUtils getBundlerUtils() {
            return new MacDMGBundlerUtils();
        }

        @Override
        public boolean isSupported() {
            return isMacOS();
        }
    },
    PKG("pkg") {
        @Override
        public AbstractBundlerUtils getBundlerUtils() {
            return new MacPKGBundlerUtils();
        }

        @Override
        public boolean isSupported() {
            return isMacOS();
        }
    },
    WIN_APP("windows.app") {
        @Override
        public AbstractBundlerUtils getBundlerUtils() {
            return new WinAppBundlerUtils();
        }

        @Override
        public boolean isSupported() {
            return isWindows();
        }
    },
    EXE("exe") {
        @Override
        public AbstractBundlerUtils getBundlerUtils() {
            return new WinExeBundlerUtils();
        }

        @Override
        public boolean isSupported() {
            return isWindows();
        }
    },
    MSI("msi") {
        @Override
        public AbstractBundlerUtils getBundlerUtils() {
            return new WinMsiBundlerUtils();
        }

        @Override
        public boolean isSupported() {
            return isWindows();
        }
    };

    private final String id;

    private BundlerUtils(String id) {
        this.id = id;
    }

    public abstract AbstractBundlerUtils getBundlerUtils();

    public abstract boolean isSupported();

    public String getBundlerId() {
        return id;
    }
}
