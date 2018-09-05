package org.fenixedu.employer.workflow;

import org.fenixedu.employer.exception.JobFailedException;
import org.fenixedu.employer.job.Job;

/**
 * Workflow defines the set of steps of the JobInterface that should be executed.
 * The alternative to having this middle interface would be for the Workflow to
 * always call all the steps (in a predefined sequential order) and, if a step
 * (method) did not make sense for a particular job, the JobInterface implemented
 * method would just be empty.
 * 
 * This way, the Workflow stays more generic since other workflows can be designed
 * by simply implementing this interface, and calling the JobInterface methods as
 * intended.
 * 
 */
public interface Workflow {

    /**
     * Method to run
     * 
     * @param job
     * @throws JobFailedException
     */
    public void run(Job job) throws JobFailedException;

    /**
     * Equal to run but handles exceptions
     * 
     * @param job
     * @return Whether job was successfully executed
     */
    public default boolean runCatch(Job job) {
        try {
            this.run(job);
            return true;
        } catch (JobFailedException e) {
            return false;
        }
    }

    /**
     * Equal to run but ignores exceptions
     * 
     * @param job
     * @return Whether job was successfully executed
     */
    public default void runIgnoreFailed(Job job) {
        try {
            this.run(job);
        } catch (JobFailedException e) {
        }
    }
}
