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

package test.javaclient.shared;

import javafx.application.Application;
import javafx.stage.Stage;
import com.sun.javafx.runtime.VersionInfo;
import java.io.FileOutputStream;

//JAXP 1.1
import javafx.application.Platform;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.*;
//import javax.xml.transform.sax.*;
//import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class appendXMLWithFxInfo extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // retrieve info
        final String tk = com.sun.javafx.tk.Toolkit.getToolkit().getClass().getSimpleName();
        System.err.println("USING TOOLKIT:" + tk);

        final String pipelineName = com.sun.prism.GraphicsPipeline.getPipeline().getClass().getName();
        System.err.println("pipeline:" + pipelineName);

        System.err.println("using java.version:" + System.getProperty("java.version"));
        System.err.println("using java.vm.name:" + System.getProperty("java.vm.name"));
        System.err.println("using prism.cacheshapes: " + System.getProperty("prism.cacheshapes"));

        System.err.println("fx runtime version: " + VersionInfo.getRuntimeVersion());
        System.err.println("fx version: " + VersionInfo.getVersion());
        System.err.println("fx milestone: " + VersionInfo.getReleaseMilestone());
        System.err.println("fx hudson build number: " + VersionInfo.getHudsonBuildNumber());

        //tmp solution for 8 b97 +
        String xxx = System.getProperty("java.runtime.version");
        //  got:       1.8.0-ea-b97
        //  should be: 8.0.0-ea-b97
        // VersionInfo.getRuntimeVersion() == 8.0.0-ea

        System.err.println("java.runtime.version: " + xxx);
        xxx = xxx.substring(xxx.lastIndexOf("-"),xxx.length());
        System.err.println("xxx: " + xxx);
        String zzz = VersionInfo.getVersion();
        zzz = zzz + xxx;
        System.err.println("tmp fx build number: " + zzz);


        String filepath = "report.xml";
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        DOMImplementation impl = docBuilder.getDOMImplementation();
        //Document xmldoc = docBuilder.parse(filepath);
        Document xmldoc = impl.createDocument(null, "entity", null);

// Root element.
        Element root = xmldoc.getDocumentElement();
        //Element root = xmldoc.createElement("entity");
        root.setAttributeNS(null, "type", "RUN");
        root.setAttributeNS(null, "name", "default");

        Element e = xmldoc.createElementNS(null, "entity");
        e.setAttributeNS(null, "type", "PRODUCT_RUNTIME");
        e.setAttributeNS(null, "name", "JFX");
        root.appendChild(e);

        Element e1 = xmldoc.createElementNS(null, "entity");
        e1.setAttributeNS(null, "type", "PRODUCT");
        e1.setAttributeNS(null, "name", "JFX");
        e.appendChild(e1);

        Element e1a = xmldoc.createElementNS(null, "attribute");
        e1a.setAttributeNS(null, "name", "name");
        Node n1a = xmldoc.createTextNode("JavaFX");
        e1a.appendChild(n1a);
        e1.appendChild(e1a);

        Element e1b = xmldoc.createElementNS(null, "attribute");
        e1b.setAttributeNS(null, "name", "release");
        Node n1b = xmldoc.createTextNode("2.0");
        e1b.appendChild(n1b);
        e1.appendChild(e1b);

        Element e1c = xmldoc.createElementNS(null, "attribute");
        e1c.setAttributeNS(null, "name", "version");


//        Node n1c = xmldoc.createTextNode(VersionInfo.getRuntimeVersion());
        Node n1c = xmldoc.createTextNode(zzz);
        e1c.appendChild(n1c);
        e1.appendChild(e1c);

        Element e1d = xmldoc.createElementNS(null, "attribute");
        e1d.setAttributeNS(null, "name", "additionalInfo");
//        Node n1d = xmldoc.createTextNode(VersionInfo.getHudsonBuildNumber());
        Node n1d = xmldoc.createTextNode(zzz);
        e1d.appendChild(n1d);
        e1.appendChild(e1d);

        Element e2 = xmldoc.createElementNS(null, "entity");
        e2.setAttributeNS(null, "type", "PRODUCT_CONFIG");
        e2.setAttributeNS(null, "name", "default");
        e.appendChild(e2);

        Element e2a = xmldoc.createElementNS(null, "attribute");
        e2a.setAttributeNS(null, "name", "arch");
        e2.appendChild(e2a);

        String strParam = System.getProperty("prism.order");//"javafx.toolkit");
        if (null == strParam) {
            strParam = "";
        }
        Element e2b = xmldoc.createElementNS(null, "attribute");
        e2b.setAttributeNS(null, "name", "settings");
        Node n2b = xmldoc.createTextNode(strParam);
        e2b.appendChild(n2b);
        e2.appendChild(e2b);

        Element e2c = xmldoc.createElementNS(null, "attribute");
        e2c.setAttributeNS(null, "name", "info");
        e2.appendChild(e2c);

        DOMSource domSource = new DOMSource(xmldoc);
        FileOutputStream fos = new FileOutputStream("report.xml");
        StreamResult streamResult = new StreamResult(fos);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.transform(domSource, streamResult);


        fos.flush(); // yes, I know. no need to flash() before close().
        fos.close();
        stage.close();
        Platform.exit();
        //System.exit(0);
    }

    public static void main(String args[]) {
        Application.launch(appendXMLWithFxInfo.class, args);
    }
}
