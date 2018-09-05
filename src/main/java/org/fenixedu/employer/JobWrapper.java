package org.fenixedu.employer;

import org.fenixedu.employer.job.Job;

public class JobWrapper {

    private Job job;
    private String id;
    private long waitTime;

    public JobWrapper(Job job, String id) {
        this.job = job;
        this.id = id;
        waitTime = 0;
    }

    public Job getJob() {
        return job;
    }

    public String getId() {
        return id;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }
}
