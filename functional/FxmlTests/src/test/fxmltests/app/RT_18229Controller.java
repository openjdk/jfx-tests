/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
 * questions.
 */

package test.fxmltests.app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class RT_18229Controller implements Initializable {
    @FXML private LoadableEntity_18229 root;

    private String rootName1 = null;
    private String rootName2 = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateRootName1();
        updateRootName2();
    }

    public LoadableEntity_18229 getRoot() {
        return root;
    }

    public String getRootName1() {
        return rootName1;
    }
    public String getRootName2() {
        return rootName2;
    }

    @FXML
    protected void handleRootName1Change() {
        updateRootName1();
    }

    @FXML
    protected void handleRootName2Change() {
        System.out.println("-----------------handleRootName2Change() -------------------------");
        throw new RuntimeException();
    }

    @FXML
    protected void handleRootName2Change(Object event) {
        System.out.println("-----------------handleRootName2Change(Object event)-------------------------");
        throw new RuntimeException();
    }

    @FXML
    protected void handleRootName2Change(javafx.event.Event event) {
        System.out.println("----------------- (event) -------------------------");
        throw new RuntimeException();
    }

    @FXML
    protected void handleRootName2Change(ObservableValue<? extends Object> o, Object o1, Object o2) {
        System.out.println("----------------- ObservableValue,  Object o1, Object event -------------------------");
        updateRootName2();
    }

    private void updateRootName1() {
        rootName1 = root.getName1();
    }
    private void updateRootName2() {
        rootName2 = root.getName2();
    }

}
