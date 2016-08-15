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
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Dmitry Ginzburg &lt;dmitry.ginzburg@oracle.com&gt;
 */
public class LocalStorageTest extends GenericTestClass {

    public static final String key = "xxx", value = "yyy", secondValue = "zzz";
    public static final String uriDomain1 = "http://shaman.ru.oracle.com/",uriDomain2 = "http://spb-ebola.ru.oracle.com/";
    public static final String veryLongString = "abacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabahabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabaiabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabahabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabajabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabahabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabaiabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabahabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabakabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabahabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabaiabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabahabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabajabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabahabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabaiabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabahabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacabagabacabadabacabaeabacabadabacabafabacabadabacabaeabacabadabacaba";
    // Length: 2047 /\

    //Domains are available from inner/outer network

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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                result = engine.executeScript("window.localStorage.clear();");
            }
        });
        doWait(resultReady);
        result = null;
    }

    @Test(timeout = 10000)
    public void testCheckStorageTypeExists() {
        result = null;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initWebEngine();
                result = engine.executeScript("if (typeof (Storage) !== \"undefined\") 1; else 2;");
            }
        });
        doWait(resultReady);
        assertTrue("Javascript type \"storage\" does not exist", result.equals(Integer.valueOf(1)));
    }

    @Test(timeout = 10000)
    public void testCheckLocalStorageVariableExists() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                result = engine.executeScript("window.localStorage;");
            }
        });
        doWait(resultReady);
        assertTrue("Javascript variable \"localStorage\" does not exist", result != null);
    }

    @Test(timeout = 10000)
    public void testCheckLocalStorageBasicReadWrite() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.localStorage.setItem(\"" + key + "\", \"" + value + "\");");
                result = engine.executeScript("window.localStorage.getItem(\"" + key + "\");");
            }
        });
        doWait(resultReady);
        assertTrue("LocalStorage doesn't support basic read/write operations", result.equals(value));
    }

    @Test(timeout = 10000)
    public void testCheckLocalStorageBasicReadWriteAccessThroughInnerVariable() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.localStorage." + key + " = \"" + value + "\";");
                result = engine.executeScript("window.localStorage." + key + ";");
            }
        });
        doWait(resultReady);
        assertTrue("LocalStorage doesn't support basic read/write operations through dot", result.equals(value));
    }

    @Test(timeout = 10000)
    public void testCheckLocalStorageBasicReadWriteMixedAccessSetThroughInnerVariable() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.localStorage." + key + " = \"" + value + "\";");
                result = engine.executeScript("window.localStorage.getItem(\"" + key + "\");");
            }
        });
        doWait(resultReady);
        assertTrue("LocalStorage doesn't support basic mixed read/write operations through dot", result.equals(value));
    }

    @Test(timeout = 10000)
    public void testCheckLocalStorageBasicReadWriteMixedAccessSetThroughMethod() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.localStorage.setItem(\"" + key + "\", \"" + value + "\");");
                result = engine.executeScript("window.localStorage." + key + ";");
            }
        });
        doWait(resultReady);
        assertTrue("LocalStorage doesn't support basic mixed read/write operations through dot", result.equals(value));
    }

    @Test(timeout = 10000)
    public void testCheckLocalStorageWithLongString() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.localStorage.setItem(\"" + key + "\", \"" + veryLongString + "\");");
                result = engine.executeScript("window.localStorage.getItem(\"" + key + "\");");
            }
        });
        doWait(resultReady);
        assertTrue("LocalStorage doesn't support basic read/write operations with long strings", result.equals(veryLongString));
    }

    @Test(timeout = 10000)
    public void testCheckLocalStorageWithEmptyString() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.localStorage.setItem(\"" + key + "\", \"\");");
                result = engine.executeScript("window.localStorage.getItem(\"" + key + "\");");
            }
        });
        doWait(resultReady);
        assertTrue("LocalStorage doesn't support basic read/write operations with empty strings", result.toString().isEmpty());
    }

    @Test(timeout = 10000)
    public void testCheckLocalStorageDoubleWrite() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                result = engine.executeScript("window.localStorage.setItem(\"" + key + "\", \"" + value + "\");");
            }
        });
        doWait(resultReady);
        result = null;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.localStorage.setItem(\"" + key + "\", \"" + secondValue + "\");");
                result = engine.executeScript("window.localStorage.getItem(\"" + key + "\");");
            }
        });
        doWait(resultReady);
        assertTrue("LocalStorage doesn't support basic read/write operations double writing", result.equals (secondValue));
    }

    @Test(timeout = 10000)
    public void testCheckLocalStorageSaveBetweenRuns() {
        initPage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                engine.executeScript("window.localStorage.setItem(\"" + key + "\", \"" + value + "\");");
                engine = null;
                initWebEngine();
                engine.load(getPath(this.getClass(), "resources/empty.html"));
            }
        });
        doWaitPageLoading();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                result = engine.executeScript("window.localStorage.getItem(\"" + key + "\");");
            }
        });
        doWait(resultReady);
        Assert.assertTrue("localStorage content is changing between runs", result.equals(value));
    }

    @Test(timeout = 20000)
    public void testCheckLocalStorageCrossDomainVariables() {
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
                            result = engine.executeScript("window.localStorage.clear();");
                        }
                    }
                });
            }
        });
        doWait(resultReady);
        result = null;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                result = engine.executeScript("window.localStorage.setItem(\"" + key + "\", \"" + value + "\");");
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
                            result = engine.executeScript ("window.localStorage.getItem(\"" + key + "\");");
                        }
                    }
                });
            }
        });
        doWait (new Tester() {
            @Override
            public boolean isPassed() {
                return result == null;
            }
        });
    }
}
