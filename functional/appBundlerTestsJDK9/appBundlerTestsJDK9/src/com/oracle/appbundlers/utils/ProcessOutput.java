
package com.oracle.appbundlers.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrei-eremeev
 */
public class ProcessOutput {

    private static final Logger LOG = Logger
            .getLogger(ProcessOutput.class.getName());

    private final Process process;
    private final boolean verbose;
    private final Future<List<String>> outputFuture;
    private List<String> outputStream;
    private final Future<List<String>> errorFuture;
    private List<String> errorStream;
    private final ExecutorService executor;
    private boolean timeoutExceeded;

    public ProcessOutput(Process process) {
        this(process, false);
    }

    ProcessOutput(Process p, boolean verbose) {
        this.process = p;
        this.verbose = verbose;
        executor = Executors.newFixedThreadPool(2);
        outputFuture = executor.submit(
                () -> getOutput(p.getInputStream(), System.out, "STDOUT"));
        errorFuture = executor.submit(
                () -> getOutput(p.getErrorStream(), System.err, "STDERR"));
    }

    public void waitFor(long timeout, TimeUnit timeUnit)
            throws InterruptedException, ExecutionException {
        boolean processFinishedInTime = process.waitFor(timeout, timeUnit);
        timeoutExceeded = !processFinishedInTime;

        outputStream = outputFuture.get();
        errorStream = errorFuture.get();
    }

    private List<String> getOutput(InputStream is, PrintStream os,
            String streamName) throws IOException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(is))) {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = in.readLine()) != null) {
                if (verbose) {
                    // TODO: Possible 32 bit Out of memory comes here.
                    os.printf("[%s]: %s\n", streamName, line);
                    LOG.log(Level.FINE, "[%s]: %s.",
                            new Object[] { streamName, line });
                }
                lines.add(line);
            }
            return lines;
        }
    }

    public int exitCode() {
        return process.exitValue();
    }

    public List<String> getOutputStream() {
        checkState(outputStream);
        return outputStream;
    }

    public List<String> getErrorStream() {
        checkState(errorStream);
        return errorStream;
    }

    private void checkState(List<String> list) throws IllegalStateException {
        if (list == null) {
            throw new IllegalStateException("Process has not finished yet");
        }
    }

    public void shutdown() {
        executor.shutdown();
        process.destroy();
    }

    boolean isTimeoutExceeded() {
        return timeoutExceeded;
    }
}
