package org.fenixedu.employer.workflow;

import org.fenixedu.employer.exception.JobFailedException;
import org.fenixedu.employer.job.Job;

public class SimpleWorkflow implements Workflow {

    @Override
    public void run(Job job) throws JobFailedException {
        job.start();
        job.execute();
        job.finish();
    }
}
