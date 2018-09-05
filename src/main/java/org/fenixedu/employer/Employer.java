package org.fenixedu.employer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.fenixedu.employer.backoff.BackoffStrategy;
import org.fenixedu.employer.exception.JobFailedException;
import org.fenixedu.employer.job.Job;
import org.fenixedu.employer.repository.MockRepository;
import org.fenixedu.employer.repository.Repository;
import org.fenixedu.employer.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Employer {
    private static final Logger logger = LoggerFactory.getLogger(Employer.class);

    private Workflow workflow;
    private BackoffStrategy backoffStrategy;
    private Repository<Job> jobRepository;

    private volatile BlockingQueue<JobWrapper> queue;
    private ExecutorService workerpool;
    private ScheduledExecutorService scheduler;

    public Employer(Workflow workflow, BackoffStrategy backoffStrategy, int maxWorkers) {
        this(workflow, backoffStrategy, maxWorkers, new MockRepository<Job>());
    }

    public Employer(Workflow workflow, BackoffStrategy backoffStrategy, int maxWorkers, Repository<Job> jobRepository) {
        this.workflow = workflow;
        this.backoffStrategy = backoffStrategy;
        this.jobRepository = jobRepository;
        queue = new LinkedBlockingQueue<>();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        workerpool = Executors.newFixedThreadPool(maxWorkers);
        start(maxWorkers);
        logger.debug("Employer initialized.");
    }

    public void start(int maxWorkers) {
        for (int i = 0; i < maxWorkers; i++) {
            workerpool.execute(new Worker());
            logger.debug("New Worker thread added to Employer pool.", workerpool);
        }
    }

    public void offer(Job job) {
        offer(new JobWrapper(job, jobRepository.store(job)));
    }

    protected void offer(JobWrapper jobWrapper) {
        try {
            logger.debug("Adding job {}:{} to queue.", jobWrapper.getJob().getClass().getSimpleName(), jobWrapper.getId());
            queue.put(jobWrapper);
        } catch (InterruptedException e) {
            logger.error("Could not add job {}:{} to queue.", jobWrapper.getJob().getClass().getSimpleName(), jobWrapper.getId());
        }
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            try {
                final JobWrapper job[] = new JobWrapper[1];
                while (true) {
                    job[0] = queue.take();
                    logger.debug("Executing new job {}:{} on Worker {}", job[0].getJob().getClass().getSimpleName(),
                            job[0].getId(), Thread.currentThread().getName());
                    try {
                        workflow.run(job[0].getJob());
                        jobRepository.remove(job[0].getId());
                        logger.debug("Successful job execution {}:{} on Worker {}", job[0].getJob().getClass().getSimpleName(),
                                job[0].getId(), Thread.currentThread().getName());
                    } catch (JobFailedException e) {
                        long waitTime = job[0].getWaitTime();
                        logger.debug("Failed job execution {}:{} on Worker {}. Retrying in {}ms",
                                job[0].getJob().getClass().getSimpleName(), job[0].getId(), Thread.currentThread().getName(),
                                waitTime);
                        job[0].setWaitTime(backoffStrategy.getWaitTime(waitTime));
                        scheduler.schedule(() -> offer(job[0]), waitTime, TimeUnit.MILLISECONDS);
                    }
                }
            } catch (InterruptedException e) {
                logger.warn("Employer's worker '{}' killed!", Thread.currentThread().getName());
            }
        }
    }
}
