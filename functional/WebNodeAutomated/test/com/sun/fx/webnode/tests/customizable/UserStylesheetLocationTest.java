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

package com.sun.fx.webnode.tests.customizable;

import com.sun.fx.webnode.tests.commonUtils.GenericTestClass;
import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for managing user stylesheet location.
 * @author Irina Grineva
 */
public class UserStylesheetLocationTest extends GenericTestClass {

    private String userStylesheetLocation;
    private String userStylesheetLocationPropertyValue;
    private StringProperty userStylesheetLocationProperty;
    private IllegalArgumentException e;
    private Tester exceptionCaught = new Tester() {
        public boolean isPassed() {
            return e != null;
        }
    };

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for getting default user stylesheet location.
     */
    @Test(timeout=10000)
    public void testGetDefaultUserCSS() {
        initWebEngineOnFXThread();
        userStylesheetLocation = "not this";
        Platform.runLater(new Runnable() {
            public void run() {
                userStylesheetLocation = engine.getUserStyleSheetLocation();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return userStylesheetLocation == null;
            }
        });
    }

    /**
     * Test for setting a valid local user stylesheet location.
     */
    @Test(timeout=10000)
    public void testSetUserCSS() {
        initWebEngineOnFXThread();
        final String cssUrl = UserStylesheetLocationTest.class.getResource("resources/user.css").toExternalForm();
        userStylesheetLocation = null;
        Platform.runLater(new Runnable() {
            public void run() {
                engine.setUserStyleSheetLocation(cssUrl);
                userStylesheetLocation = engine.getUserStyleSheetLocation();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return userStylesheetLocation != null;
            }
        });
        Assert.assertEquals(cssUrl, userStylesheetLocation);
    }

    /**
     * Test for setting a null to user stylesheet location.
     */
    @Test(timeout=10000)
    public void testSetUserCSSNull() {
        initWebEngineOnFXThread();
        userStylesheetLocation = "not this";
        Platform.runLater(new Runnable() {
            public void run() {
                engine.setUserStyleSheetLocation(null);
                userStylesheetLocation = engine.getUserStyleSheetLocation();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return userStylesheetLocation == null;
            }
        });
    }

    /**
     * Test for setting a null to user stylesheet location.
     */
    @Test(timeout=10000)
    public void testSetUserCSSNull2() {
        initWebEngineOnFXThread();
        final String cssUrl = UserStylesheetLocationTest.class.getResource("resources/user.css").toExternalForm();
        userStylesheetLocation = null;
        Platform.runLater(new Runnable() {
            public void run() {
                engine.setUserStyleSheetLocation(cssUrl);
                userStylesheetLocation = engine.getUserStyleSheetLocation();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return userStylesheetLocation != null;
            }
        });
        Assert.assertEquals(cssUrl, userStylesheetLocation);
        userStylesheetLocation = "not this";
        Platform.runLater(new Runnable() {
            public void run() {
                engine.setUserStyleSheetLocation(null);
                userStylesheetLocation = engine.getUserStyleSheetLocation();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return userStylesheetLocation == null;
            }
        });
    }

    /**
     * Test for setting an external URL to user stylesheet location.
     * http://javafx-jira.kenai.com/browse/RT-22579
     */
    @Test(timeout=10000)
    public void testSetUserCSSExternal() {
        initWebEngineOnFXThread();
        e = null;
        userStylesheetLocation = "not this";
        Platform.runLater(new Runnable() {
            public void run() {
                String cssUrl = "http://google.com";
                try {
                    engine.setUserStyleSheetLocation(cssUrl);
                } catch (IllegalArgumentException ex) {e = ex;}
                userStylesheetLocation = engine.getUserStyleSheetLocation();
            }
        });
        doWait(exceptionCaught);
    }

    /**
     * Test for setting an invalid URL to user stylesheet location.
     * http://javafx-jira.kenai.com/browse/RT-22579
     */
    @Test(timeout=10000)
    public void testSetUserCSSInvalid() {
        initWebEngineOnFXThread();
        e = null;
        userStylesheetLocation = "not this";
        Platform.runLater(new Runnable() {
            public void run() {
                String cssUrl = "ololo";
                try {
                    engine.setUserStyleSheetLocation(cssUrl);
                } catch (IllegalArgumentException ex) {e = ex;}
                userStylesheetLocation = engine.getUserStyleSheetLocation();
            }
        });
        doWait(exceptionCaught);
    }

    /**
     * Test for getting default user stylesheet location using a property.
     */
    @Test(timeout=10000)
    public void testGetDefaultUserCSSProperty() {
        initWebEngineOnFXThread();
        userStylesheetLocationProperty = null;
        userStylesheetLocationPropertyValue = "not this";
        Platform.runLater(new Runnable() {
            public void run() {
                userStylesheetLocationProperty = engine.userStyleSheetLocationProperty();
                userStylesheetLocationPropertyValue = userStylesheetLocationProperty.get();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return userStylesheetLocationProperty != null && userStylesheetLocationPropertyValue == null;
            }
        });
    }

    /**
     * Test for setting a valid local user stylesheet location using a property.
     */
    @Test(timeout=10000)
    public void testSetUserCSSProperty() {
        initWebEngineOnFXThread();
        final String cssUrl = UserStylesheetLocationTest.class.getResource("resources/user.css").toExternalForm();
        userStylesheetLocation = null;
        userStylesheetLocationPropertyValue = null;
        Platform.runLater(new Runnable() {
            public void run() {
                userStylesheetLocationProperty = engine.userStyleSheetLocationProperty();
                userStylesheetLocationProperty.set(cssUrl);
                userStylesheetLocation = engine.getUserStyleSheetLocation();
                userStylesheetLocationPropertyValue = userStylesheetLocationProperty.get();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return userStylesheetLocation != null && userStylesheetLocationPropertyValue != null;
            }
        });
        Assert.assertNotNull(userStylesheetLocation);
        Assert.assertEquals(cssUrl, userStylesheetLocation);
        Assert.assertEquals(cssUrl, userStylesheetLocationPropertyValue);
    }

    /**
     * Test for setting a null to user stylesheet location using a property.
     */
    @Test(timeout=10000)
    public void testSetUserCSSPropertyNull() {
        initWebEngineOnFXThread();
        userStylesheetLocation = "not this";
        userStylesheetLocationPropertyValue = "not this";
        Platform.runLater(new Runnable() {
            public void run() {
                userStylesheetLocationProperty = engine.userStyleSheetLocationProperty();
                userStylesheetLocationProperty.set(null);
                userStylesheetLocation = engine.getUserStyleSheetLocation();
                userStylesheetLocationPropertyValue = userStylesheetLocationProperty.get();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return userStylesheetLocation == null && userStylesheetLocationPropertyValue == null;
            }
        });
    }

    /**
     * Test for setting a null to user stylesheet location using a property.
     */
    @Test(timeout=10000)
    public void testSetUserCSSPropertyNull2() {
        initWebEngineOnFXThread();
        final String cssUrl = UserStylesheetLocationTest.class.getResource("resources/user.css").toExternalForm();
        userStylesheetLocation = null;
        userStylesheetLocationPropertyValue = null;
        Platform.runLater(new Runnable() {
            public void run() {
                userStylesheetLocationProperty = engine.userStyleSheetLocationProperty();
                userStylesheetLocationProperty.set(cssUrl);
                userStylesheetLocation = engine.getUserStyleSheetLocation();
                userStylesheetLocationPropertyValue = userStylesheetLocationProperty.get();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return userStylesheetLocation != null && userStylesheetLocationPropertyValue != null;
            }
        });
        Assert.assertNotNull(userStylesheetLocation);
        Assert.assertEquals(cssUrl, userStylesheetLocation);
        Assert.assertEquals(cssUrl, userStylesheetLocationPropertyValue);
        userStylesheetLocation = "not this";
        userStylesheetLocationPropertyValue = "not this";
        Platform.runLater(new Runnable() {
            public void run() {
                userStylesheetLocationProperty = engine.userStyleSheetLocationProperty();
                userStylesheetLocationProperty.set(null);
                userStylesheetLocation = engine.getUserStyleSheetLocation();
                userStylesheetLocationPropertyValue = userStylesheetLocationProperty.get();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return userStylesheetLocation == null && userStylesheetLocationPropertyValue == null;
            }
        });
    }

    /**
     * Test for setting an external URL to user stylesheet location using a property.
     * http://javafx-jira.kenai.com/browse/RT-22579
     * http://javafx-jira.kenai.com/browse/RT-22580
     */
    @Test(timeout=10000)
    public void testSetUserCSSPropertyExternal() {
        initWebEngineOnFXThread();
        e = null;
        Platform.runLater(new Runnable() {
            public void run() {
                userStylesheetLocationProperty = engine.userStyleSheetLocationProperty();
                String cssUrl = "http://google.com";
                try {
                    userStylesheetLocationProperty.set(cssUrl);
                } catch (IllegalArgumentException ex) {e = ex;}
                userStylesheetLocation = engine.getUserStyleSheetLocation();
                userStylesheetLocationPropertyValue = userStylesheetLocationProperty.get();
            }
        });
        doWait(exceptionCaught);
    }

    /**
     * Test for setting an invalid URL to user stylesheet location using a property.
     * http://javafx-jira.kenai.com/browse/RT-22579
     * http://javafx-jira.kenai.com/browse/RT-22580
     */
    @Test(timeout=10000)
    public void testSetUserCSSPropertyInvalid() {
        initWebEngineOnFXThread();
        e = null;
        Platform.runLater(new Runnable() {
            public void run() {
                userStylesheetLocationProperty = engine.userStyleSheetLocationProperty();
                String cssUrl = "ololo";
                try {
                    userStylesheetLocationProperty.set(cssUrl);
                } catch (IllegalArgumentException ex) {e = ex;}
                userStylesheetLocation = engine.getUserStyleSheetLocation();
                userStylesheetLocationPropertyValue = userStylesheetLocationProperty.get();
            }
        });
        doWait(exceptionCaught);
    }
}