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

package client.test.runner;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;

/**
 *
 * @author shura
 */
public class LessSequrityManager extends SecurityManager {

    @Override
    public void checkExit(int status) {
    }

    @Override
    public void checkAwtEventQueueAccess() {
    }

    @Override
    public void checkAccept(String string, int i) {
    }

    @Override
    public void checkAccess(ThreadGroup tg) {
    }

    @Override
    public void checkAccess(Thread thread) {
    }

    @Override
    public void checkConnect(String string, int i) {
    }

    @Override
    public void checkConnect(String string, int i, Object o) {
    }

    @Override
    public void checkCreateClassLoader() {
    }

    @Override
    public void checkDelete(String string) {
    }

    @Override
    public void checkExec(String string) {
    }

    @Override
    public void checkLink(String string) {
    }

    @Override
    public void checkListen(int i) {
    }

    @Override
    public void checkMemberAccess(Class<?> type, int i) {
    }

    @Override
    public void checkMulticast(InetAddress ia) {
    }

    @Override
    public void checkMulticast(InetAddress ia, byte b) {
    }

    @Override
    public void checkPackageAccess(String string) {
    }

    @Override
    public void checkPackageDefinition(String string) {
    }

    @Override
    public void checkPermission(Permission prmsn) {
    }

    @Override
    public void checkPermission(Permission prmsn, Object o) {
    }

    @Override
    public void checkPrintJobAccess() {
    }

    @Override
    public void checkPropertiesAccess() {
    }

    @Override
    public void checkPropertyAccess(String string) {
    }

    @Override
    public void checkRead(FileDescriptor fd) {
    }

    @Override
    public void checkRead(String string) {
    }

    @Override
    public void checkRead(String string, Object o) {
    }

    @Override
    public void checkSecurityAccess(String string) {
    }

    @Override
    public void checkSetFactory() {
    }

    @Override
    public void checkSystemClipboardAccess() {
    }

    @Override
    public void checkWrite(FileDescriptor fd) {
    }

    @Override
    public void checkWrite(String string) {
    }

}
