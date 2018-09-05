package org.fenixedu.employer.workflow;

import org.fenixedu.employer.exception.JobFailedException;
import org.fenixedu.employer.job.Job;

public class SafeWorkflow implements Workflow {

    @Override
    public void run(Job job) throws JobFailedException {
        try {
            job.start();
            job.execute();
            job.finish();
        } catch (Throwable e) {
            try {
                job.fail();
            } catch (Throwable e1) {
            }
            throw new JobFailedException(e.getMessage());
        }

    }

}
