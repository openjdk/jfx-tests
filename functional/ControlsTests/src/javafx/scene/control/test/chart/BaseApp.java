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

package javafx.scene.control.test.chart;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.Property;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.test.chart.XYChartBaseApp.Pages;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.TestUtil;

public abstract class BaseApp extends BasicButtonChooserApp {
    protected TestNode rootTestNode = new TestNode();
    protected int SLOT_WIDTH = 350;
    protected int SLOT_HEIGHT = 250;

    protected BaseApp(int width, int height, String title, boolean showAdditionalActionButton) {
        super(width, height, title, showAdditionalActionButton);
    }

    /**
     * Method for embedded purpose, to get appropriate suffix from the array
     */
    public static String getSuffix(int index){
        return TestUtil.SUFFIXES[index];
    }

    /**
     * Checks whether it is an embedded mode or not.
     */
    public boolean isEmbedded(){
        return TestUtil.isEmbedded();
    }

    /*protected void pageSetup(String pageName, TestNode node) {
        pageSetup(pageName, pageName, node);
    }*/
    protected void pageSetup(String pageName, TestNode node) {
        if(isEmbedded()){
            pageSetup(pageName+"First", pageName, node, 0);
            pageSetup(pageName+"Second", pageName, node, 1);
        }else{
            pageSetup(pageName, pageName, node);
        }
    }

    protected void pageSetup(String pageName, TestNode node, Object var) {
        final PageWithSlots page = new PageWithSlots(pageName, height, width);
        page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        page.add(node, var.toString());
        rootTestNode.add(page);
    }

    protected void pageSetup(String pageName, Object... params) {
        final PageWithSlots page = new PageWithSlots(pageName, height, width);
        page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        for (int i = 0; i < params.length/2; i++) {
            page.add((TestNode)params[i], params[params.length/2 + i].toString());
        }
        rootTestNode.add(page);
    }

    protected void pageSetup(String pageName, TestNode nodes[], Object vars[]) {
        final PageWithSlots page = new PageWithSlots(pageName, height, width);
        page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        for (int i = 0; i < nodes.length; i++) {
            page.add(nodes[i], vars[i].toString());
        }
        rootTestNode.add(page);
    }

    protected void pageSetup(String pageName, TestNode nodes[], String names[]) {
        final PageWithSlots page = new PageWithSlots(pageName, height, width);
        page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        for (int i = 0; i < nodes.length; i++) {
            page.add(nodes[i], names[i]);
        }
        rootTestNode.add(page);
    }

    protected void pageSetup(String pageName, String node_name, TestNode node) {
        final PageWithSlots page = new PageWithSlots(pageName, height, width);
        page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        page.add(createEmptyNode(), "Nothing");
        page.add(node, node_name);
        rootTestNode.add(page);
    }

    protected void pageSetup(String pageName, String node_name, TestNode node, int index) {
        final PageWithSlots page = new PageWithSlots(pageName, height, width);
        page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        if(index == 0)
            page.add(createEmptyNode(), "Nothing");
        else if(index == 1)
            page.add(node, node_name);

        rootTestNode.add(page);
    }

    protected void setupTitle(String pageName, String node_name){
        pageSetup(pageName, new StandardStringSetterNode(pageName, node_name));
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupHorizontalZeroLineVisible(String name){
        if(isEmbedded()){
            pageSetup(name+"First",
                    new StandardBooleanSetterNode(name, true) {
                            @Override
                            public Object create() {
                                Object node = super.create();
                                ((XYChart)node).getXAxis().setSide(Side.TOP);
                                return node;
                            }
                        },
                    "true");
            pageSetup(name+"Second",
                    new StandardBooleanSetterNode(name, false) {
                            @Override
                            public Object create() {
                                Object node = super.create();
                                ((XYChart)node).getXAxis().setSide(Side.TOP);
                                return node;
                            }
                        },
                    "false");
        }else{
            pageSetup(Pages.HorizontalZeroLineVisible.name(),
                  new StandardBooleanSetterNode(Pages.HorizontalZeroLineVisible.name(), true) {
                        @Override
                        public Object create() {
                            Object node = super.create();
                            ((XYChart)node).getXAxis().setSide(Side.TOP);
                            return node;
                        }
                    },
                  new StandardBooleanSetterNode(Pages.HorizontalZeroLineVisible.name(), false) {
                        @Override
                        public Object create() {
                            Object node = super.create();
                            ((XYChart)node).getXAxis().setSide(Side.TOP);
                            return node;
                        }
                    },
                  "true",
                  "false");
        }
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupVerticalZeroLineVisible(String name){
        if(isEmbedded()){
            pageSetup(name+"First",
                    new StandardBooleanSetterNode(name, true) {
                            @Override
                            public Object create() {
                                Object node = super.create();
                                ((XYChart)node).getYAxis().setSide(Side.RIGHT);
                                return node;
                            }
                        },
                    "true");
            pageSetup(name+"Second",
                    new StandardBooleanSetterNode(name, false) {
                            @Override
                            public Object create() {
                                Object node = super.create();
                                ((XYChart)node).getYAxis().setSide(Side.RIGHT);
                                return node;
                            }
                        },
                    "false");
        }else{
            pageSetup(Pages.VerticalZeroLineVisible.name(),
                    new StandardBooleanSetterNode(Pages.VerticalZeroLineVisible.name(), true) {
                            @Override
                            public Object create() {
                                Object node = super.create();
                                ((XYChart)node).getYAxis().setSide(Side.RIGHT);
                                return node;
                            }
                        },
                    new StandardBooleanSetterNode(Pages.VerticalZeroLineVisible.name(), false) {
                            @Override
                            public Object create() {
                                Object node = super.create();
                                ((XYChart)node).getYAxis().setSide(Side.RIGHT);
                                return node;
                            }
                        },
                    "true",
                    "false");
        }
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupBoolean(String name) {
        if(isEmbedded()){
            pageSetup(name+"First",
                    new StandardBooleanSetterNode(name, true),
                    "true");
            pageSetup(name+"Second",
                    new StandardBooleanSetterNode(name, false),
                    "false");
        }else{
            pageSetup(name,
                    new StandardBooleanSetterNode(name, true),
                    new StandardBooleanSetterNode(name, false),
                    "true",
                    "false");
        }
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupDouble(String name, Double values[]) {
        if(isEmbedded()){
            for (int i = 0; i < values.length; i++) {
                TestNode node = new StandardDoubleSetterNode(name, values[i]);
                pageSetup(name+getSuffix(i), node, values[i]);
            }
        }else{
            TestNode nodes[] = new TestNode[values.length];
            for (int i = 0; i < values.length; i++) {
                nodes[i] = new StandardDoubleSetterNode(name, values[i]);
            }
            pageSetup(name, nodes, values);
        }
    }

    protected void setupInteger(String name, Integer values[]) {
        TestNode nodes[] = new TestNode[values.length];
        for (int i = 0; i < values.length; i++) {
            nodes[i] = new StandardIntegerSetterNode(name, values[i]);
        }
        pageSetup(name, nodes, values);
    }

    protected void setupString(String name, String values[]) {
        TestNode nodes[] = new TestNode[values.length];
        for (int i = 0; i < values.length; i++) {
            nodes[i] = new StandardStringSetterNode(name, values[i]);
        }
        pageSetup(name, nodes, values);
    }

    protected void setupObject(String name, Object values[]) {
        setupObject(name, name, values);
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupObject(String page_name, String property_name, Object values[]) {
        if(isEmbedded()){
            for (int i = 0; i < values.length; i++) {
                TestNode node = new StandardObjectSetterNode(property_name, values[i]);
                pageSetup(page_name+getSuffix(i), node, values[i]);
            }
        } else {
            TestNode nodes[] = new TestNode[values.length];
            for (int i = 0; i < values.length; i++) {
                nodes[i] = new StandardObjectSetterNode(property_name, values[i]);
            }
            pageSetup(page_name, nodes, values);
        }
    }

    protected class StandardObjectSetterNode extends StandardSetterNode<Object> {
        public StandardObjectSetterNode(String name, Object value) {
            super(name, value, Object.class);
        }
    }

    protected class StandardStringSetterNode extends StandardSetterNode<String> {
        public StandardStringSetterNode(String name, String value) {
            super(name, value, String.class);
        }
        @Override
        protected boolean compare(Object a, Object b) {
            return ((String)a).contentEquals((String)b);
        }
    }

    protected class StandardBooleanSetterNode extends StandardSetterNode<Boolean> {
        public StandardBooleanSetterNode(String name, Boolean value) {
            super(name, value, Boolean.class);
        }
    }

    protected class StandardDoubleSetterNode extends StandardSetterNode<Double> {
        public StandardDoubleSetterNode(String name, Double value) {
            super(name, value, Double.class);
        }
        @Override
        protected boolean compare(Object a, Object b) {
            if (Double.compare((Double)a, (Double)b) == 0) {
                return true;
            }
            return false;
        }
    }

    protected class StandardIntegerSetterNode extends StandardSetterNode<Integer> {
        public StandardIntegerSetterNode(String name, Integer value) {
            super(name, value, Integer.class);
        }
    }

    protected class StandardSetterNode<Type> extends TestNode {
        protected Type value;
        protected String name;
        protected Class cl;

        public StandardSetterNode(String name, Type value, Class cl) {
            this.value = value;
            this.name = name;
            this.cl = cl;
        }

        protected Object create() {
            return createObject();
        }

        @Override
        public Node drawNode() {
            Object node = create();
            Class type = node.getClass();
            try {
                Class property_class = Class.forName(Property.class.getPackage().getName() +  ".Simple"  + cl.getSimpleName() + "Property");
                Constructor property_constructor = property_class.getConstructor(new Class[0]);
                Object observable_value = property_constructor.newInstance(new Object[0]);

                Object objects_observable = type.getMethod(firstSymbolToLower(name) + "Property").invoke(node, new Object[0]);

                invoke("bind", objects_observable, observable_value);

                invoke("setValue", observable_value, value);

                if (!compare(value, (Type) invoke("get" + name, "is" + name, node,  new Object[0]))) {
                    reportGetterFailure(type.getSimpleName() + ".get" + name + "()");
                }
            } catch (Exception ex) {
                Logger.getLogger(ValueAxisApp.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                Object objects_observable = type.getMethod(firstSymbolToLower(name) + "Property").invoke(node, new Object[0]);
                invoke("unbind", objects_observable, new Object[0]);
            } catch (Exception ex) {
                Logger.getLogger(ValueAxisApp.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                invoke("set" + name, node,  value);

                if (!compare(value, (Type) invoke("get" + name, "is" + name, node,  new Object[0]))) {
                    reportGetterFailure(type.getSimpleName() + ".get" + name + "()");
                }
            } catch (Exception ex) {
                Logger.getLogger(ValueAxisApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            return createNode(node);
        }

        protected boolean compare(Object a, Object b) {
            return a.equals(b);
        }

        protected Object invoke(String name, Object object, Object... params) throws Exception {
            return invoke(name, "", object, params);
        }

        protected Object invoke(String name, String alternative_name, Object object, Object... params) throws Exception {
            Method methods[] = object.getClass().getMethods();
            for (int i = 0; i < methods.length; i++) {
               if ((methods[i].getName().contentEquals(name) || methods[i].getName().contentEquals(alternative_name)) && methods[i].getParameterTypes().length == params.length) {
                   try {
                       return methods[i].invoke(object, params);
                   } catch (Throwable th) {
                       return null;
                   }
               }
            }
            return null;
        }

        protected String firstSymbolToLower(final String arg) {
            String res = arg;
            res = (res.substring(0, 1).toLowerCase()) + res.substring(1);
            return res;
        }
    }

    public class CSSNode extends TestNode {
        protected String[] style;
        protected String[] key;

        public CSSNode(String[] style, String[] key) {
            this.style = style;
            this.key = key;
        }

        public Object create() {
            return createObject();
        }

        @Override
        public Node drawNode() {
            Node node = (Node)create();
            StringBuilder style_builder = new StringBuilder();
            for (int i = 0; i < style.length; i++) {
                style_builder.append(" " + style[i] + ": " + key[i] + ";");
            }
            if (node.getStyle().isEmpty()) {
                style_builder.delete(0, 1);
            }
            node.setStyle(node.getStyle() + style_builder.toString());
            return node;
        }
    }

    protected class EmptyNode extends TestNode {
        public EmptyNode() {
        }
        @Override
        public Node drawNode() {
            return createNode(createObject());
        }
    }

    protected TestNode createEmptyNode() {
        return new EmptyNode();
    }

    protected Node createNode(Object obj) {
        return (Node)obj;
    }

    protected abstract Object createObject();
}
