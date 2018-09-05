package org.fenixedu.employer.job;

import org.fenixedu.employer.exception.JobFailedException;

public abstract class Job {
    public abstract void start();

    public abstract void execute() throws JobFailedException;

    public abstract void finish();

    public abstract void fail();
}
