package org.fenixedu.employer.backoff;

public interface BackoffStrategy {
    /**
     * Returns the number of miliseconds to wait before
     * retrying a failed operation again
     * 
     * @return
     */
    public long getWaitTime(long previousWait);
}
