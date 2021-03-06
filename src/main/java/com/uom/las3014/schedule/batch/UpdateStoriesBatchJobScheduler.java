package com.uom.las3014.schedule.batch;

import com.uom.las3014.batching.jobs.UpdateStoriesJob;
import com.uom.las3014.cache.MyCacheManager;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * A {@link Scheduled} task which runs the {@link UpdateStoriesJob}
 */
@Configuration
public class UpdateStoriesBatchJobScheduler {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("UpdateStoriesJobBean")
    private Job updateStoriesJob;

    @Scheduled(cron = "0 15 3 ? * *")
    @CacheEvict(value = {MyCacheManager.TOPIC_CACHE, MyCacheManager.TOP_STORY_CACHE}, allEntries = true)
    public void performUpdateWeeklyStoriesJob() throws Exception {
        final JobParameters param = new JobParametersBuilder()
                                        .addString("JobID", String.valueOf(System.currentTimeMillis()))
                                        .toJobParameters();

        jobLauncher.run(updateStoriesJob, param);
    }
}
