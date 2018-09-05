package org.fenixedu.employer.job;

import org.fenixedu.employer.exception.JobFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintJob extends Job {

    private static Logger logger = LoggerFactory.getLogger("PrintJob");

    private String message;

    public PrintJob(int i) {
        message = Integer.toString(i);
    }

    public PrintJob(String message) {
        this.message = message;
    }

    @Override
    public void start() {

    }

    @Override
    public void execute() throws JobFailedException {
        logger.info("PrintJob executing: " + message);
    }

    @Override
    public void finish() {
    }

    @Override
    public void fail() {
    }
}
