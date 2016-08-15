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
 * questions.
 */
package org.jemmy.fx.control;

import javafx.scene.web.WebView;
import org.jemmy.control.As;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.fx.NodeWrap;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.AbstractParent;
import org.jemmy.lookup.LookupCriteria;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

@ControlType({WebView.class})
@ControlInterfaces( value = {Parent.class},
                    encapsulates = {org.w3c.dom.Node.class},
                    name= {"asWebNodeParent"})
public class WebViewWrap<CONTROL extends WebView> extends NodeWrap<CONTROL> {

    public static final Timeout PAGE_LOADING_TIMEOUT = new Timeout("webview.page.loading.timeout", 5000);

    static {
        Environment.getEnvironment().initTimeout(PAGE_LOADING_TIMEOUT);
    }

    /**
     *
     * @param env
     * @param nd
     */
    @SuppressWarnings("unchecked")
    public WebViewWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    @As(org.w3c.dom.Node.class)
    public <T extends org.w3c.dom.Node> AbstractParent<T> asWebNodeParent(Class<T> type) {
        return new WebNodeParent(this, type);
    }

    public static class ByName implements LookupCriteria<org.w3c.dom.Node> {
        protected String name;
        public ByName(String name) {
            this.name = name;
        }
        public boolean check(org.w3c.dom.Node cntrl) {
            if (cntrl.getNodeName().equals(name)) {
                return true;
            }
            return false;
        }
    }

    public static class ByAttribute implements LookupCriteria<org.w3c.dom.Node> {
        protected String name;
        protected String value;
        public ByAttribute(String name, String value) {
            this.name = name;
            this.value = value;
        }
        public boolean check(org.w3c.dom.Node cntrl) {
            final NamedNodeMap attributes = cntrl.getAttributes();
            if (attributes == null) {
                return false;
            }
            Node item = attributes.getNamedItem(name);
            if (item == null) {
                return false;
            }
            if (item.getNodeValue().equals(value)) {
                return true;
            }
            return false;
        }
    }
}