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
package com.sun.fx.webnode.tests.html5.webstorage;

import com.sun.fx.webnode.tests.commonUtils.GenericTestClass;
import static com.sun.fx.webnode.tests.commonUtils.GenericTestClass.getPath;
import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import static com.sun.fx.webnode.tests.html5.webstorage.LocalStorageTest.key;
import static com.sun.fx.webnode.tests.html5.webstorage.LocalStorageTest.secondValue;
import static com.sun.fx.webnode.tests.html5.webstorage.LocalStorageTest.uriDomain1;
import static com.sun.fx.webnode.tests.html5.webstorage.LocalStorageTest.uriDomain2;
import static com.sun.fx.webnode.tests.html5.webstorage.LocalStorageTest.value;
import static com.sun.fx.webnode.tests.html5.webstorage.LocalStorageTest.veryLongString;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Dmitry Ginzburg &lt;dmitry.ginzburg@oracle.com&gt;
 */
public class SessionStorageTest extends GenericTestClass {

    protected Object result;
    protected Tester resultReady = new Tester() {
        public boolean isPassed() {
            return result != null;
        }
    };

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    public void initPage() {
        result = null;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initWebEngine();
                engine.load(getPath(this.getClass(), "resources/empty.html"));

            }
        });
        doWaitPageLoading();
    }

    @Test(timeout = 10000)
    public void testCheckSessionStorageVariableExists() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                result = engine.executeScript("window.sessionStorage;");
            }
        });
        doWait(resultReady);
        assertTrue("Javascript variable \"sessionStorage\" does not exist", result != null);
    }

    @Test(timeout = 10000)
    public void testCheckSessionStorageBasicReadWrite() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.sessionStorage.setItem(\"" + key + "\", \"" + value + "\");");
                result = engine.executeScript("window.sessionStorage.getItem(\"" + key + "\");");
            }
        });
        doWait(resultReady);
        assertTrue("SessionStorage doesn't support basic read/write operations", result.equals(value));
    }

    @Test(timeout = 10000)
    public void testCheckSessionStorageBasicReadWriteAccessThroughInnerVariable() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.sessionStorage." + key + " = \"" + value + "\";");
                result = engine.executeScript("window.sessionStorage." + key + ";");
            }
        });
        doWait(resultReady);
        assertTrue("SessionStorage doesn't support basic read/write operations through dot", result.equals(value));
    }

    @Test(timeout = 10000)
    public void testCheckSessionStorageBasicReadWriteMixedAccessSetThroughInnerVariable() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.sessionStorage." + key + " = \"" + value + "\";");
                result = engine.executeScript("window.sessionStorage.getItem(\"" + key + "\");");
            }
        });
        doWait(resultReady);
        assertTrue("SessionStorage doesn't support basic mixed read/write operations through dot", result.equals(value));
    }

    @Test(timeout = 10000)
    public void testCheckSessionStorageBasicReadWriteMixedAccessSetThroughMethod() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.sessionStorage.setItem(\"" + key + "\", \"" + value + "\");");
                result = engine.executeScript("window.sessionStorage." + key + ";");
            }
        });
        doWait(resultReady);
        assertTrue("SessionStorage doesn't support basic mixed read/write operations through dot", result.equals(value));
    }

    @Test(timeout = 10000)
    public void testCheckSessionStorageWithLongString() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.sessionStorage.setItem(\"" + key + "\", \"" + veryLongString + "\");");
                result = engine.executeScript("window.sessionStorage.getItem(\"" + key + "\");");
            }
        });
        doWait(resultReady);
        assertTrue("SessionStorage doesn't support basic read/write operations with long strings", result.equals(veryLongString));
    }

    @Test(timeout = 10000)
    public void testCheckSessionStorageWithEmptyString() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.sessionStorage.setItem(\"" + key + "\", \"\");");
                result = engine.executeScript("window.sessionStorage.getItem(\"" + key + "\");");
            }
        });
        doWait(resultReady);
        assertTrue("SessionStorage doesn't support basic read/write operations with empty strings", result.toString().isEmpty());
    }

    @Test(timeout = 10000)
    public void testCheckSessionStorageDoubleWrite() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                result = engine.executeScript("window.sessionStorage.setItem(\"" + key + "\", \"" + value + "\");");
            }
        });
        doWait(resultReady);
        result = null;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.sessionStorage.setItem(\"" + key + "\", \"" + secondValue + "\");");
                result = engine.executeScript("window.sessionStorage.getItem(\"" + key + "\");");
            }
        });
        doWait(resultReady);
        assertTrue("SessionStorage doesn't support basic read/write operations double writing", result.equals (secondValue));
    }

    @Test(timeout = 10000)
    public void testCheckSessionStorageNotSaveBetweenRuns() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.sessionStorage.setItem(\"" + key + "\", \"" + value + "\");");
                engine = null;
                initWebEngine();
                engine.load(getPath(this.getClass(), "resources/empty.html"));
            }
        });
        doWaitPageLoading();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                result = engine.executeScript("window.sessionStorage.getItem(\"" + key + "\");");
            }
        });
        try {
            doWait(resultReady);
            throw new RuntimeException("sessionStorage content is saved between runs: bad behavior");
        } catch (org.jemmy.TimeoutExpiredException ex) {

        }
    }

    @Test(timeout = 10000)
    public void testCheckSessionStorageCrossDomainVariables() {
        result = null;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine = null;
                initWebEngine();
                engine.load(uriDomain1);
                engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> ov, Worker.State t, Worker.State t1) {
                        if (t1.equals(Worker.State.SUCCEEDED)) {
                            result = engine.executeScript("window.sessionStorage.setItem(\"" + key + "\", \"" + value + "\");");
                        }
                    }
                });
            }
        });
        doWait(resultReady);
        result = new Object();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine = null;
                initWebEngine();
                engine.load(uriDomain2);
                engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> ov, Worker.State t, Worker.State t1) {
                        if (t1.equals(Worker.State.SUCCEEDED)) {
                            result = engine.executeScript("window.sessionStorage.getItem(\"" + key + "\");");
                        }
                    }
                });
            }
        });
        doWait(new Tester() {
            @Override
            public boolean isPassed() {
                return result == null;
            }
        });
    }
}
