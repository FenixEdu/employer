package org.fenixedu.employer.backoff;

public class ExponentialBackoff implements BackoffStrategy {

    private static final long INITIAL_WAIT = 1000;
    private static final double EXPONENT = 1.12;
    private static final long MAX_WAIT = 86400000;

    private long initialWait;
    private double exponent;
    private long maxWait;

    public ExponentialBackoff() {
        initialWait = INITIAL_WAIT;
        exponent = EXPONENT;
        maxWait = MAX_WAIT;
    }

    public ExponentialBackoff(long initialWait, double exponent, long maxWait) {
        super();
        this.initialWait = initialWait;
        this.exponent = exponent;
        this.maxWait = maxWait;
    }

    /**
     * Returns the number of seconds to wait before
     * trying to add the job to the queue again
     * 
     * @return
     */
    @Override
    public long getWaitTime(long previousWait) {
        if (previousWait <= 0) {
            return initialWait;
        }
        return (long) Math.min(Math.ceil(Math.pow(previousWait, exponent)), maxWait);
    }
}
