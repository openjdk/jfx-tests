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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.JTextComponent;

import com.sun.javatest.Status;

/***** TCK BackDoor insert begin ****/
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import javax.swing.JTextPane;

/***** TCK BackDoor insert end ****/
public class ManualTest {

    private Status status;
    private JPanel panel = null; // Panel for test
    private JPanel infoPanel = null; // User info panel
    private JTextPane infoPane = null; // Text component that displays infos
    private List infoList = null; // A list of instructions presented to user
    private JPanel controlPanel = null; // User interface
    private JPanel buttonPanel; // Yes/No buttons
    private JPanel failureInfoPanel = null; // Test failure info
    private TimePanel timePanel; // Panel for time display and pause button
    private JPanel userPanel; // Panel for buttonPanel and failureInfoPanel
    private JLabel userLabel; // Label for user question/instr. text
    private JTextField failureInfoField; // Textfield for failure info
    private JButton yesButton,  noButton,  doneButton,  interruptButton;
    private GridBagLayout gridbag = new GridBagLayout();
    private GridBagConstraints c = new GridBagConstraints();
    private int timeout = 90; // Set by invoking setTimeout()
    private int timeRemaining; // Remaining time for test execution
    private boolean testExecutionPaused = true; // Set to true if user pauses test execution
    private Listener listener = null;

    /**
     *
     */
    public String mode = null; // Test mode; Yes/No or Done
    private boolean testdirurlRequired = false; // True if test requires -TestDirURL parameter
//    private String testdir = null; // Directory indicated by TestDirURL parameter
    private Toolkit toolkit;
    private Dimension frameSize; // Size of test frame
    private Dimension screenSize; // Size of screen
    private JScrollPane scrollPane = new JScrollPane(); // Scrollpane for test
    private static final Pattern BAD_BRACKETS_PATTERN = Pattern.compile(
            "<(Blanket|Session|Oneshot|No)>");
    /***** TCK BackDoor insert begin ****/
    // private -> protected
    protected String name; // Test case name

    /**
     *
     */
    protected String testdir = null; // Directory indicated by TestDirURL parameter
    /***** TCK BackDoor insert end ****/
    /***** TCK BackDoor insert begin ****/
    protected String TCKBackDoorLogPrefix = "Debug output: ";

    /**
     *
     */
    public TCKBackDoor m_BackDoor = null;

    /**
     *
     */
    public void CreateBackDoor() {
        // Creating BackDoor
        if (m_BackDoor == null) {
            m_BackDoor = new TCKBackDoor(this);
        } else {
            m_BackDoor.DebugOutput("Warning! m_BackDoor in runTestCase is not null.");
            m_BackDoor.setTCKInterface(this);
            m_BackDoor.Run();
        }
    }

    /**
     *
     */
    public void CloseBackDoor() {
        // Erasing BackDoor
        if (m_BackDoor != null) {
            m_BackDoor.DebugOutput("go::m_BackDoor.TryToStopThread();");

            if (m_BackDoor.TryToStopThread()) {
                m_BackDoor = null;
            }
        }
    }

    /***** TCK BackDoor insert end ****/
    // 508-friendly text selection behavior
    private static final class SelectionPolicy
            extends MouseAdapter
            implements FocusListener {

        private boolean dragActive = false;

        public void focusGained(FocusEvent e) {
            if (!dragActive && !e.isTemporary()) {
                JTextComponent source = (JTextComponent) e.getComponent();
                if (source.getSelectedText() == null) {
                    source.selectAll();
                }
            }
        }

        public void focusLost(FocusEvent e) {
            JTextComponent source = (JTextComponent) e.getComponent();
            if (!e.isTemporary()) {
                source.select(0, 0);
            }
        }

        public void mousePressed(MouseEvent e) {
            dragActive = true;
        }

        public void mouseReleased(MouseEvent e) {
            dragActive = false;
        }
    }
    private String from; // Sender of incoming messages
//    private String name; // Test case name
    private boolean end = false; // End test-run flag
    private boolean arrived = false;

    /**
     *
     */
    public Font XXXLrgDialogFont = new Font("Dialog", Font.PLAIN, 26);

    /**
     *
     */
    public Font XXLrgDialogFont = new Font("Dialog", Font.PLAIN, 24);

    /**
     *
     */
    public Font XLrgDialogFont = new Font("Dialog", Font.PLAIN, 22);

    /**
     *
     */
    public Font lrgDialogFont = new Font("Dialog", Font.PLAIN, 20);

    /**
     *
     */
    public Font medDialogFont = new Font("Dialog", Font.PLAIN, 16);

    /**
     *
     */
    public Font smDialogFont = new Font("Dialog", Font.PLAIN, 14);

    /**
     *
     */
    public Font XsmDialogFont = new Font("Dialog", Font.PLAIN, 12);
    /**
     * Main test frame.
     */
    protected JFrame testFrame = new JFrame();


    {
        testFrame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                setStatus(Status.failed("Test Frame manually closed by user."));
            }
        });
    }

    // Non-lazy initialization on purpose so that this
    // class will be loaded by agent at the beginning of the test,
    // and can be used during forced shutdown of the agent.
    // Otherwise the agent will try to load this class over TCP connection
    // during the shutdown and will get an Exception.
    private Runnable cleanUpTestFrame = new Runnable() {

        public void run() {
            testFrame.setVisible(false);
            testFrame.getContentPane().removeAll();
        }
    };
    /**
     * Question presented to a user of the "YesNo" interface.
     * Set by invoking setQuestion().
     */
    protected String question = "";
    /**
     * Instruction presented to a user of the "Done" interface.
     * Set by invoking setDoneInstruction().
     */
    protected String doneInstruction = "Press Done when finished.";
    /**
     * Instruction presented to a user of the "InfoOnly" interface.
     * Set by invoking setInterruptInstruction().
     */
    protected String interruptInstruction =
            "Press Interrupt if you'd like to interrupt test execution.";

    /**
     *
     */
    protected String passMessage = "";

    /**
     *
     */
    protected String failMessage = "";

    /**
     *
     */
    public String testdirurl = null;

    /**
     *
     */
    public ManualTest() {
        this("YesNo");
    }

    /**
     *
     * @param mode
     */
    protected ManualTest(String mode) {
        this.mode = mode;
    }
    private String testInstructions = null;

    /**
     *
     * @param testName
     * @param instructions
     * @return
     */
    public Status run(String testName, String instructions) {
        name = testName;
        // Creating BackDoor
        CreateBackDoor();
        testInstructions = instructions;
        java.lang.reflect.Method method = null;
        Status s = null;
        try {
            method = getClass().getMethod("displayDialog", new Class[0]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return Status.error("Internal error in OTATest: " + "no displayDialog method");
        }
        try {
            s = invokeTestCase(method);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return Status.failed("Could not execute test case: " + method);
        } catch (java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
            //printStackTrace(e.getTargetException());
            return Status.failed("Test case throws exception: " + e.getTargetException().toString());
        }
        /* Erasing of m_BackDoor removed to cleanUp */
        CloseBackDoor();
        return s;
    }

    /**
     *
     * @return
     */
    public Status displayDialog() {
        String passMsg = "OKAY";
        String failMsg = "Failed";
        // prepare the main test frame
        infoPane.setText(testInstructions);
        setQuestion("Instructions passed?");
        setStatusMessages(passMsg, failMsg);
        setFrameTitle("user action requested");
        setTimeout(180);
        return Status.passed(""); // will be ignored
    }

    private static String quoteUnsafeBrackets(String line) {
        return BAD_BRACKETS_PATTERN.matcher(line).replaceAll("&lt;$1&gt;");
    }

    /**
     *
     * @param m
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    protected Status invokeTestCase(Method m)
            throws IllegalAccessException, InvocationTargetException {
        Object[] testArgs = {};
        toolkit = Toolkit.getDefaultToolkit();
        screenSize = toolkit.getScreenSize();
        status = null;
        testExecutionPaused = false;
        JButton focusedButton = null;

        setUp();
        m.invoke(this, testArgs);

        timeRemaining = timeout;

        //infoPane.setText(testInstructions);
        try {
            infoPane.read(new StringReader(testInstructions), "text/html");
//        infoPane.setText("dasdsadas");
        } catch (IOException ex) {
            Logger.getLogger(ManualTest.class.getName()).log(Level.SEVERE, null, ex);
        }
//        infoPane.setText("dasdsadas");

        // Create Yes/No or Done or InfoOnly user interface as required
        if (mode.equals("YesNo")) {
            createYNInterface();
            focusedButton = yesButton;
        } else if (mode.equals("Done")) {
            createDoneInterface();
            focusedButton = doneButton;
        } else if (mode.equals("InfoOnly")) {
            createInfoOnlyInterface();
            focusedButton = interruptButton;
        }

        // Set size of frame to screen size if frame is
        // larger than screen
        testFrame.setSize(500, 500);
        frameSize = testFrame.getSize();

        if ((frameSize.width > screenSize.width) || (frameSize.height > screenSize.height)) {
            testFrame.setSize(screenSize);
        }

        // Initial Focus
        if (focusedButton != null) {
            focusedButton.requestFocusInWindow();
        }

        testFrame.setVisible(true);
        startRecordingTime();

        try {
            waitForStatus();
        } catch (InterruptedException e) {
            return Status.failed(e.toString());
        } finally {
            cleanUp();
        }
        return (status); // status is an accumulation
    }

    /**
     * Sets pass/fail test status.
     * Called by listener when the user indicates that the test is done by
     * pressing the Yes button, the No button, or the Done button.
     *
     * @see com.sun.javatest.Status
     *
     * @param status Pass/Fail status.
     */
    protected synchronized void setStatus(Status status) {
        this.status = status;
        notifyAll();
    }

    /**
     * Starts the thread that updates the amount of time left for test
     * execution.
     */
    protected void startRecordingTime() {
        timePanel.start();
    }

    /**
     * Waits for the user to indicate test completion.
     * Times out if the user doesn't respond within the timeout limit.
     *
     * @exception InterruptedException If another thread has interrupted the
     *            current thread.
     */
    protected synchronized void waitForStatus() throws InterruptedException {
        try {
            while ((status == null) && (timeRemaining > 0)) {
                if (!testExecutionPaused) {
                    wait(1000);
                    timeRemaining--;
                } else {
                    wait(10000);
                }
            }
        } catch (InterruptedException e) {
            if (status == null) {
                status = Status.failed("Test execution is interrupted: " + e.toString());
            }
        }

        if (status == null) {
            status = Status.failed("No response from user.");
        }
    }

    /**
     * Pauses test execution.
     */
    protected synchronized void pauseTestExecution() {
        timePanel.stop();
        testExecutionPaused = true;
        notifyAll();
    }

    /**
     * Resumes test execution.
     */
    protected synchronized void resumeTestExecution() {
        testExecutionPaused = false;
        notifyAll();
        timePanel.start();
    }

    /**
     * Sets the question presented to a user of the Yes/No user interface.
     *
     * @param question Question presented to user using Yes/No interface.
     */
    protected void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Sets the messages that get printed upon test pass/fail.
     *
     * @param passMessage Message printed if test passes.
     *
     * @param failMessage Message printed if test fails.
     */
    protected void setStatusMessages(String passMessage, String failMessage) {
        this.passMessage = passMessage;
        this.failMessage = failMessage;
    }

    /**
     * Sets the instruction for pressing the Done button.
     *
     * @param doneInstruction Instruction presented to user using Done interface.
     */
    protected void setDoneInstruction(String doneInstruction) {
        this.doneInstruction = doneInstruction;
    }

    /**
     * Sets the instruction for pressing the "Interrupt" button.
     *
     * @param interruptInstruction Instruction presented to user using InfoOnly interface.
     */
    protected void setInterruptInstruction(String interruptInstruction) {
        this.interruptInstruction = interruptInstruction;
    }

    /**
     * Sets the test frame's title.
     *
     * @param title Title given to test frame.
     */
    protected void setFrameTitle(String title) {
        testFrame.setTitle(title);
    }

    /**
     * Sets the test frame's size.
     *
     * @param width
     *                Width of test frame.
     *
     * @param height
     *                Height of test frame.
     * @deprecated The method should not be used since the GUI tries to
     *                   determine its size automatically. Setting frame size
     *                   explicitly is against 508 requirements.
     */
    protected void setFrameSize(int width, int height) {
        testFrame.setSize(width, height);
    }

    /**
     * Sets the test's timeout.
     *
     * @param seconds Number of seconds that elapse before the test times out.
     */
    protected void setTimeout(int seconds) {
        timeout = seconds;
    }

    /**
     * Returns the time remaining for test execution.
     *
     * @return timeRemaining Time remaining for test execution
     */
    protected int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Checks the test's result and declares pass/fail.
     * Overridden by self-checking tests to determine the test result after
     * user presses the Done button.
     * This method should be overridden if the derived test uses the Done user
     * interface.  The checkResult method should invoke setStatus after
     * determining the test result.
     */
    protected void checkResult() {
        setStatus(Status.failed("No result checking was performed by test."));
    }

    /**
     * Performs cleanup that is specific to the derived test.
     * Test should override this method if the test needs to do cleanup before
     * exiting.
     * Tests which display Container objects (such as Frame and Dialog) should
     * override this method to dispose() the containers.
     */
    protected void doTestCleanup() {
        // empty by default.
    }

    /**
     * Creates a frame that contains a scrollpane with user info, the test
     * panel, and the user interface.
     * Creates a listener for pausing test execution and for receiving the
     * user's response.
     */
    protected void setUp() {
        // Frame to contain info, test panel, and user interface
        testFrame.setDefaultCloseOperation(
                WindowConstants.HIDE_ON_CLOSE);
        testFrame.getAccessibleContext().setAccessibleDescription(
                "This Frame provides test instructions for" + "Interactive and OTA tests");

        testFrame.getContentPane().setLayout(new BorderLayout());

        // Panel to contain info, test panel, and user interface
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel to display usage and expected result info
        String infoPanelDesc = "Test Instructions";
        infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        Border loweredetched = BorderFactory.createEtchedBorder(
                EtchedBorder.LOWERED);
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                loweredetched, infoPanelDesc));

        infoPane = new JTextPane();
        infoPane.setContentType("text/html");
        infoPane.setEditable(false);

        infoPane.getAccessibleContext().setAccessibleName(infoPanelDesc);
        infoPane.getAccessibleContext().setAccessibleDescription(
                "This pane contains test instructions");
        infoPane.setToolTipText(infoPanelDesc);

        infoPanel.add(new JScrollPane(infoPane), BorderLayout.CENTER);

        // Panel to display user interface
        controlPanel = new JPanel();
        controlPanel.setLayout(gridbag);

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);

        testFrame.getContentPane().add(panel, BorderLayout.CENTER);

        // Listener for user actions
        listener = new Listener();
    }

    /**
     * Removes the test frame.
     * Called when the test is done.
     */
    protected void cleanUp() {
        doTestCleanup();

        if (timePanel != null) {
            timePanel.stop();
        }

        if (testFrame != null) {
            try {
                SwingUtilities.invokeAndWait(cleanUpTestFrame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates the user interface for the Yes/No interactive mode.
     */
    protected void createYNInterface() {
        userPanel = new JPanel();
        userLabel = new JLabel(question, SwingConstants.CENTER);
        buttonPanel = new JPanel();
        userLabel.setLabelFor(buttonPanel);
        failureInfoPanel = new JPanel();

        yesButton = new JButton("Yes");
        yesButton.setBackground(Color.green);
        // add listener for user's "yes" response
        yesButton.addActionListener(listener);
        yesButton.setToolTipText("Respond \"Yes\" to the question");
        yesButton.setMnemonic('y');

        noButton = new JButton("No");
        noButton.setBackground(Color.red);
        // add listener for user's "no" response
        noButton.addActionListener(listener);
        noButton.setToolTipText("Respond \"No\" to the question");
        noButton.setMnemonic('n');

        failureInfoField = new JTextField();

        // Add panel containing pause button and remaining time info
        timePanel = new TimePanel();
        c = new GridBagConstraints();
        c.insets = new Insets(15, 0, 0, 0);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(timePanel, c);
        controlPanel.add(timePanel);

        // Add question
        c.insets = new Insets(30, 0, 10, 0);
        gridbag.setConstraints(userLabel, c);
        controlPanel.add(userLabel);

        // Add Yes/No buttons to buttonPanel
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        // Add textfield for test failure info to failureInfoPanel
        GridBagLayout gridbag2 = new GridBagLayout();
        GridBagConstraints c2 = new GridBagConstraints();
        failureInfoPanel.setLayout(gridbag2);
        c2.weightx = 1;
        c2.fill = GridBagConstraints.HORIZONTAL;
        gridbag2.setConstraints(failureInfoField, c2);
        failureInfoPanel.add(failureInfoField);
        failureInfoField.addActionListener(listener);

        // Add buttonPanel and failureInfoPanel as cards to userPanel
        userPanel.setLayout(new CardLayout());
        userPanel.add("buttons", buttonPanel);
        userPanel.add("textfield", failureInfoPanel);

        // Add userPanel to controlPanel
        c.insets = new Insets(0, 0, 10, 0);
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(userPanel, c);
        controlPanel.add(userPanel);
    }

    /**
     * Creates the user interface for the Done interactive mode.
     */
    protected void createDoneInterface() {
        JLabel dLabel = new JLabel(doneInstruction);
        doneButton = new JButton("Done");
        dLabel.setLabelFor(doneButton);
        doneButton.setBackground(Color.green);
        doneButton.setForeground(Color.black);
        doneButton.addActionListener(listener); // add listener for user's "done" response
        doneButton.setMnemonic('d');
        doneButton.setToolTipText("Finish the test");

        // Add panel containing pause button and remaining time info
        timePanel = new TimePanel();
        c.insets = new Insets(15, 0, 0, 0);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(timePanel, c);
        controlPanel.add(timePanel);

        // Add done instruction
        c.insets = new Insets(30, 0, 10, 0);
        gridbag.setConstraints(dLabel, c);
        controlPanel.add(dLabel);

        // Add Done button
        c.insets = new Insets(0, 0, 10, 0);
        gridbag.setConstraints(doneButton, c);
        controlPanel.add(doneButton);
    }

    /**
     *
     */
    protected void createInfoOnlyInterface() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JLabel dLabel = new JLabel(interruptInstruction, SwingConstants.CENTER);
        interruptButton = new JButton("Interrupt");
        dLabel.setLabelFor(interruptButton);
        interruptButton.setBackground(Color.red);
        interruptButton.setForeground(Color.black);
        // add listener for user's "interrupt" response
        interruptButton.addActionListener(listener);
        interruptButton.setMnemonic('i');
        interruptButton.setToolTipText("Interrupt the test");

        // Add panel containing pause button and remaining time info
        timePanel = new TimePanel();
        c.insets = new Insets(15, 0, 0, 0);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(timePanel, c);
        controlPanel.add(timePanel);

        // Add Interrupt instruction
        c.insets = new Insets(30, 0, 10, 0);
        gridbag.setConstraints(dLabel, c);
        controlPanel.add(dLabel);

        // Add Interrupt button
        c.insets = new Insets(0, 0, 10, 0);
        gridbag.setConstraints(interruptButton, c);
        buttonPanel.add(interruptButton);
        controlPanel.add(buttonPanel);
    }

    /**
     * Gets test failure info from user.
     * Presents textfield for user to provide test failure details.
     */
    protected void getFailureInfo() {
        // remove focus from infoPane so that info text is deselected
        // as soon as possible to reduce select/deselect flickering
        timePanel.pauseButton.requestFocusInWindow();

        // Display textfield
        ((CardLayout) userPanel.getLayout()).show(userPanel, "textfield");

        String failureFieldDesc = "Please provide test failure details here" + " and press <ENTER> or <RETURN>.";
        failureInfoField.requestFocusInWindow();
        // Update label to request test failure info
        userLabel.setText(failureFieldDesc);
        userLabel.setLabelFor(failureInfoField);
        controlPanel.validate();
        testFrame.validate();
        failureInfoField.setToolTipText(failureFieldDesc);
        failureInfoField.getAccessibleContext().setAccessibleName(
                "Failure Information");
        failureInfoField.getAccessibleContext().setAccessibleDescription(
                failureFieldDesc);
    }

    /**
     * Updates the label of the Pause/Resume button.
     *
     * @param o Pause/Resume button
     * @param newLabel New label of button
     */
    protected void setButtonText(Object o, String newLabel) {
        ((JButton) o).setText(newLabel);
    }

    /**
     * Returns the textfield that is used for getting test failure
     * info from user.
     *
     * @return textfield that is used for getting test failure info
     */
    protected Component getFailureInfoField() {
        return failureInfoField;
    }

    /**
     * Returns the text that user enters in test failure info textfield
     *
     * @return text that user enters in test failure info textfield
     */
    protected String getFailureInfoText() {
        return failureInfoField.getText();
    }

    /**
     * Listener for pausing test execution and for receiving the user's
     * response.
     */
    protected class Listener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String name = e.getActionCommand();
            Object source = e.getSource();

            if ("Yes".equals(name)) {
                setStatus(Status.passed(passMessage)); // pass
            } else if ("No".equals(name)) {
                pauseTestExecution(); // stop the timer
                getFailureInfo();
            } else if ("Done".equals(name)) {
                // test should override this if using Done interface
                checkResult();
            } else if (source == getFailureInfoField()) {
                String failureInfo = getFailureInfoText();
                setStatus(Status.failed(failMessage)); // fail
            } else if ("Interrupt".equals(name)) {
                setStatus(Status.failed("Test execution interrupted by user"));
            }
        }
    }

    /**
     * Panel to display remaining time and pause button.
     */
    protected class TimePanel extends JPanel {

        private JLabel timeRemainingLabel;
        private JTextField timeRemainingField;
        private JButton pauseButton;
        private final Timer timer = new Timer(1000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        private static final String PAUSE = "Pause";
        private static final String RESUME = "Resume";

        // Non-lazy initialization on purpose so that these
        // classes will be loaded by agent at the beginning of the test
        private final Runnable setButtonToPause = new Runnable() {

            public void run() {
                pauseButton.setText(PAUSE);
                pauseButton.setMnemonic('p');
            }
        };
        private final Runnable setButtontoResume = new Runnable() {

            public void run() {
                pauseButton.setText(RESUME);
                pauseButton.setMnemonic('r');
            }
        };
        private boolean stopUpdating = false;

        TimePanel() {
            setLayout(new FlowLayout());
            Border loweredetched = BorderFactory.createEtchedBorder(
                    EtchedBorder.LOWERED);
            setBorder(BorderFactory.createTitledBorder(
                    loweredetched, "Timer"));

            timeRemainingLabel = new JLabel("Time remaining:");
            timeRemainingLabel.setFont(
                    timeRemainingLabel.getFont().deriveFont(
                    Font.ITALIC + Font.BOLD));
            timeRemainingField = new JTextField(secondsToString(timeRemaining));
            timeRemainingField.setEditable(false);
            // 508 requirement. See also bug 4512626.
            timeRemainingField.addFocusListener(new SelectionPolicy());
            timeRemainingField.setName(timeRemainingLabel.getName());
            String timeRemFieldDesc = "Remaining time";
            timeRemainingField.getAccessibleContext().setAccessibleDescription(timeRemFieldDesc);
            timeRemainingField.setToolTipText(timeRemFieldDesc);
            timeRemainingLabel.setLabelFor(timeRemainingField);
            timeRemainingLabel.setDisplayedMnemonic('t');

            pauseButton = new JButton(PAUSE);
            pauseButton.setMnemonic('p');
            String pauseButtonDesc = "Pause or resume the timer";
            pauseButton.getAccessibleContext().setAccessibleDescription(
                    pauseButtonDesc);
            pauseButton.setToolTipText(pauseButtonDesc);
            pauseButton.setBackground(Color.yellow);
            pauseButton.setForeground(Color.black);
            add(timeRemainingLabel);
            add(timeRemainingField);
            add(new JLabel("           "));
            add(pauseButton);
            pauseButton.addActionListener(listener);
            pauseButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    JButton source = (JButton) e.getSource();
                    if (PAUSE.equals(source.getText())) {
                        pauseTestExecution();
                    } else {
                        resumeTestExecution();
                    }
                }
            });
        }

        synchronized void start() {
            stopUpdating = false;
            SwingUtilities.invokeLater(setButtonToPause);
            timer.start();
        }

        synchronized void stop() {
            // We must make sure that when
            // stop() completes there will be NO MORE updates.
            // Simply canceling the timer is not enough.
            stopUpdating = true;
            timer.stop();
            SwingUtilities.invokeLater(setButtontoResume);
        }

        private synchronized void update() {
            if (stopUpdating) {
                return;
            }

            if (timeRemainingField != null) {
                timeRemainingField.setText(secondsToString(timeRemaining));
                // 508 requirement. We need visual indication that
                // the field has focus.
                if (timeRemainingField.hasFocus()) {
                    timeRemainingField.selectAll();
                }
            }
        }

        private String secondsToString(int time) {
            StringBuffer sb = new StringBuffer(5);
            int seconds = time % 60;
            int minutes = time / 60;

            if (minutes < 10) {
                sb.append('0');
            }
            sb.append(minutes);
            sb.append(':');

            if (seconds < 10) {
                sb.append('0');
            }
            sb.append(seconds);
            return sb.toString();
        }
    }

    /**
     *
     * @return
     */
    public String getTestName() {
        String FullTestName = System.getProperty("java.sqe.BackDoor.TestName");

        if (m_BackDoor != null) {
            m_BackDoor.DebugOutput("Local test name: " + name);
            m_BackDoor.DebugOutput("Javatest test name: " + FullTestName);
        }

        if ((FullTestName != null) && (FullTestName.length() > 0)) {
            return FullTestName;
        } else {
            return name;
        }
    }

    /**
     *
     * @return
     */
    public String getTestDir() {
        String fResult = null;
        String FullTestName = System.getProperty("java.sqe.BackDoor.TestName");

        if (m_BackDoor != null) {
            m_BackDoor.DebugOutput("Local test dir: " + testdir);
        //m_BackDoor.DebugOutput("Javatest test name: " + FullTestName);
        }

        if ((FullTestName != null) && (FullTestName.length() > 0)) {
            fResult = "";
        } else {
            if ((testdir != null) && (testdir.length() > 0)) {
                fResult = testdir;
            } else {
                fResult = "empty_test_dir";
            }
        }

        return fResult;
    }

    /**
     *
     * @param Success
     */
    public void finishTest(boolean Success) {
        if (m_BackDoor != null) {
            m_BackDoor.DebugOutput("Finish test " + Success);
        }

        if (Success) {
            setStatus(Status.passed(passMessage)); // pass
        } //setStatus(Status.passed("passed"));
        else {
            setStatus(Status.failed(failMessage)); // fail
        }            //setStatus(Status.failed("failed"));
    }

    /**
     *
     * @param ButtonName
     */
    public void pressButton(String ButtonName) {
        Component component = findButton(testFrame, ButtonName);

        if (component != null) {
            if (m_BackDoor != null) {
                m_BackDoor.DebugOutput("Pressing button \"" + ButtonName + "\"");
            }

            try {
                Button button = (Button) component;

                if (button != null) {
                    ActionListener listeners[] = button.getActionListeners();
                    ActionEvent event = new ActionEvent(this, 0, ButtonName, 0);
                    listeners[0].actionPerformed(event);
                }
            } catch (Throwable t) {
            }

            try {
                JButton jbutton = (JButton) component;

                if (jbutton != null) {
                    jbutton.doClick();
                }
            } catch (Throwable t) {
            }
        } else if (m_BackDoor != null) {
            m_BackDoor.DebugOutput("Button \"" + ButtonName + "\" not found.");
        }
    }

    /**
     *
     * @param container
     * @param label
     * @return
     */
    protected Component findButton(Container container, String label) {
        Component fResult = null;

        if (panel != null) {
            Component comps[] = container.getComponents();

            for (int i = 0; i < container.getComponentCount(); i++) {
                try {
                    JButton tmpJButton = (JButton) comps[i];

                    //if (m_BackDoor != null)
                    //    m_BackDoor.DebugOutput("Button \"" + tmpJButton.getLabel() + "\"");

                    if (label.equals(tmpJButton.getLabel())) {
                        fResult = tmpJButton;
                        break;
                    }
                } catch (Throwable t) {
                }

                try {
                    Button tmpButton = (Button) comps[i];

                    //if (m_BackDoor != null)
                    //    m_BackDoor.DebugOutput("Button \"" + tmpButton.getLabel() + "\"");

                    if (label.equals(tmpButton.getLabel())) {
                        fResult = tmpButton;
                        break;
                    }
                } catch (Throwable t) {
                }

                try {
                    Container tmpComponetnt = (Container) comps[i];
                    fResult = findButton(tmpComponetnt, label);
                    if (fResult != null) {
                        break;
                    }
                } catch (Throwable t) {
                }
            }
        }

        return fResult;

    }

    class TCKBackDoor extends Thread {

        ManualTest m_TCKInterface;
        boolean m_FlagToStop = false;
        boolean m_FlagThreadStopped = false;
        ServerSocket m_ServerSocket;
        protected boolean m_DebugFlag = true;///*true;//*/Boolean.getBoolean("debug." + TCKBackDoor.class.getName());

        TCKBackDoor(ManualTest parent) {
            DebugOutput("TCKBackDoor::TCKBackDoor");

            m_TCKInterface = parent;
            m_FlagToStop = false;
            start();
        }

        public void DebugOutput(String LogString) {
            if (m_DebugFlag) {
                System.out.println(TCKBackDoorLogPrefix + LogString);
            }
        }

        public void setTCKInterface(ManualTest parent) {
            m_TCKInterface = parent;
        }

        public void Run() {
            DebugOutput("TCKBackDoor::Run");

            if (TryToStopThread()) {
                m_FlagToStop = false;

                try {
                    start();
                } catch (Exception e) {
                    DebugOutput("Can't start new thread: " + e);/**/ }
            }
        }

        public boolean TryToStopThread() {
            m_FlagToStop = true;
            m_FlagThreadStopped = false;

            try {
                m_ServerSocket.close();
            } catch (Exception e) { /*DebugOutput("ServerSocket close problem: "+e);*/ }

            if (isAlive()) {
                //DebugOutput("Thread alive. Waiting for exit");

                int Count = 20; // 2 sec to wait
                while ((!m_FlagThreadStopped) && (Count > 0)) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }

                    Count--;
                }
            } else {
                m_FlagThreadStopped = true;
            }

            DebugOutput("TryToStopThread() = true");
            return m_FlagThreadStopped;
        }

        public void run() {
            DebugOutput("Enter Back door run.");

            try {
                int TryCount = 0;

                while (!m_FlagToStop) {
                    try {
                        m_ServerSocket = new ServerSocket(22222);
                        DebugOutput("Back door socket opened");
                        break;
                    } catch (Exception e) {
                        DebugOutput("Can't open socket: " + e);

                        if (++TryCount > 10) {
                            DebugOutput("Back door fatal error. Close backdoor!");
                            return;
                        }

                        try {
                            Socket soket = new Socket("localhost", 22222);

                            OutputStream os = soket.getOutputStream();
                            DataOutputStream DataOutput = new DataOutputStream(os);

                            try {
                                DataOutput.writeUTF("freeSocket");
                                DataOutput.flush();
                            } catch (Exception e1) {
                                DebugOutput("Can't sent message to old BackDoor: " + e1);
                            }

                            os.close();
                            soket.close();
                        } catch (Exception e2) {
                            DebugOutput("Can't sent message to old BackDoor: " + e2);
                        }
                    }
                }

                while (!m_FlagToStop) {
                    DebugOutput("Back door waiting");

                    Socket l_Socket = m_ServerSocket.accept();

                    if (l_Socket != null) {
                        InputStream l_InputStream = l_Socket.getInputStream();
                        OutputStream l_OutputStream = l_Socket.getOutputStream();
                        DataInputStream DataInput = new DataInputStream(l_InputStream);
                        DataOutputStream DataOutput = new DataOutputStream(l_OutputStream);

                        String l_TextCommand = "";

                        try {
                            l_TextCommand = DataInput.readUTF();
                        } catch (Exception e) {
                            DebugOutput("Can't read from stream: " + e);
                        }

                        if (l_TextCommand.length() > 0) {
                            DebugOutput("BackDoor recieved command: " + l_TextCommand);

                            if (l_TextCommand.equals("getTestName")) {
                                String TestName = m_TCKInterface.getTestName();
                                String TestDirName = getTestSubDir();

                                if (TestDirName.length() > 0) {
                                    TestName = TestDirName + "\\" + TestName;
                                }

                                TestName = TestName.replace('\\', '.');
                                TestName = TestName.replace('/', '.');

                                DebugOutput("Test name is: " + TestName);

                                try {
                                    DataOutput.writeUTF(TestName);
                                    DataOutput.flush();
                                } catch (Exception e) {
                                    DebugOutput("Backdoor reply test name problem: " + e);
                                }
                            }

                            if (l_TextCommand.equals("getTestURL")) {
                            }

                            if (l_TextCommand.equals("passed")) {
                                m_TCKInterface.finishTest(true);
                            }
                            if (l_TextCommand.equals("failed")) {
                                m_TCKInterface.finishTest(false);
                            }

                            if (l_TextCommand.equals("closeBackDoor")) {
                                m_FlagToStop = true;
                            }
                            if (l_TextCommand.equals("freeSocket")) {
                                m_FlagToStop = true;
                            }

                            if (l_TextCommand.indexOf("press:") == 0) {
                                m_TCKInterface.pressButton(l_TextCommand.substring(6));
                            }
                        }

                        l_InputStream.close();
                        l_OutputStream.close();
                        l_Socket.close();
                    }
                }
            } catch (Exception e) { /*DebugOutput("Backdoor run problem: "+e); /**/

            }

            try {
                DebugOutput("Closing socket...");
                m_ServerSocket.close();
            } catch (Exception e) { /*DebugOutput("ServerSocket close problem: "+e);/**/ }

            DebugOutput("Thread end");

            m_FlagThreadStopped = true;
        }

        protected String getTestSubDir() {
            String fResult = "";

            File CurrentDir = new File(".");

            try {
                String CurrentDirString = CurrentDir.getCanonicalPath();

                String testdir = m_TCKInterface.getTestDir();

                if (testdir != null) {
                    int PathIndex = testdir.indexOf(CurrentDirString);
                    int PathLen = CurrentDirString.length();

                    if (PathIndex > 0) {
                        fResult = testdir.substring(PathIndex + PathLen);
                    } else {
                        fResult = testdir;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (fResult.length() > 0) {
                int Pos = fResult.indexOf("MIDP-TCK_20b");

                if (Pos > 0) {
                    fResult = fResult.substring(Pos + 12);
                }

                fResult = RemoveSlashes(fResult);

                if (fResult.indexOf("tests") == 0) {
                    fResult = fResult.substring(5);
                }

                fResult = RemoveSlashes(fResult);
            } else {
                fResult = "";
            }

            //DebugOutput("getTestSubDir = " + fResult);

            return fResult;
        }

        private String RemoveSlashes(String InputString) {
            while ((InputString.charAt(0) == '\\') || (InputString.charAt(0) == '/')) {
                InputString = InputString.substring(1);
            }

            while ((InputString.charAt(InputString.length() - 1) == '\\') || (InputString.charAt(InputString.length() - 1) == '/')) {
                InputString = InputString.substring(0, InputString.length() - 1);
            }

            return InputString;
        }
    }
}
