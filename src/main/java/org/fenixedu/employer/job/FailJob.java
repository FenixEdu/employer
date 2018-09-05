package org.fenixedu.employer.job;

import org.fenixedu.employer.exception.JobFailedException;

public class FailJob extends Job {

    @Override
    public void start() {
    }

    @Override
    public void execute() throws JobFailedException {
        System.out.println("Job  failed");
        throw new JobFailedException();

    }

    @Override
    public void finish() {
    }

    @Override
    public void fail() {
    }

}
