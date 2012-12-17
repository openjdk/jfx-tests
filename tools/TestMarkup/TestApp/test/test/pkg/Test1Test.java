/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.pkg;

import com.oracle.jdk.sqe.cc.markup.Covers;
import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import org.junit.*;

/**
 *
 * @author shura
 */
@Covers({"0", "5"})
public class Test1Test {

    public Test1Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    @Covers(value={"1", "3"}, level=Level.LOW)
    public void test1() {}

    @Test
    @Covers(value="3", level=Level.MEDIUM)
    public void test3() {}

}