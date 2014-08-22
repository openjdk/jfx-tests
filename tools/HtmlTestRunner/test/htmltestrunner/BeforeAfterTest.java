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

package htmltestrunner;

import com.sun.javatest.Status;
import java.io.File;
import com.sun.javatest.TestDescription;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author shura
 */
public class BeforeAfterTest {

    /**
     *
     */
    public BeforeAfterTest() {
    }


    static TestDescription description;
    static File workdir;

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        workdir = File.createTempFile("HttmlTestRunner", "test");
        HashMap params = new HashMap();
        params.put("testName", "test.name");
        params.put("testInstructions", "test.instructions");
        description = new TestDescription(new File("root"),
            new File("file"), params);
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     *
     */
    @Test
    public void beforeFailed() {
        final String message = "before failed";
        TestScript script = new TestScript() {
            {
                workdirPath = workdir.getPath();
            }

            @Override
            protected void before(TestDescription description, String resDir) throws Throwable {
                throw new RuntimeException(message);
            }

        };
        Status stat = script.run(null, description, null);
        if(!stat.isError()) {
            fail("should be error");
        }
        assertTrue(stat.getReason().contains(message));
    }

    /**
     *
     */
    @Test
    public void afterFailed() {
        final String message = "after failed";
        TestScript script = new TestScript() {
            {
                workdirPath = workdir.getPath();
            }

            @Override
            protected void after(TestDescription description, String resDir) throws Throwable {
                throw new RuntimeException(message);
            }

        };
        Status stat = script.run(null, description, null);
        if(!stat.isError()) {
            fail("should be error");
        }
        System.out.println("Reason: " + stat.getReason());
        assertTrue(stat.getReason().contains(message));
    }

}