package test.demobatch.scheduler;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobOperator jobLauncher;
    @Qualifier("daily9amJob")
    private final Job daily9amJob;

    @Scheduled(cron = "0 */1 * * * *") // mỗi phút
    @SchedulerLock(
            name = "daily9amJobScheduler",
            lockAtMostFor = "PT30S",
            lockAtLeastFor = "PT5S"
    )
    public void runJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("jobA", System.currentTimeMillis()) // bắt buộc unique
                .toJobParameters();

        jobLauncher.start(daily9amJob, params);
    }

}
