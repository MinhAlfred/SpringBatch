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
    private final Job daily9amJob;
    private final Job demoJob;
    private final Job taskletJob;

    @Scheduled(cron = "0 */2 * * * *") // mỗi phút
    @SchedulerLock(
            name = "daily9amJobScheduler",
            lockAtMostFor = "PT3M",
            lockAtLeastFor = "PT20S"
    )
    public void runJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("dailyJob", System.currentTimeMillis()) // bắt buộc unique
                .toJobParameters();

        jobLauncher.start(daily9amJob, params);
    }

    @Scheduled(cron = "0 */1 * * * *")
    @SchedulerLock(
            name = "demoJobLock",
            lockAtMostFor = "PT2M",
            lockAtLeastFor = "PT10S"
    )
    public void runDemoJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("demoJob", System.currentTimeMillis()) // bắt buộc unique
                .toJobParameters();

        jobLauncher.start(demoJob, params);
    }

    @Scheduled(cron = "*/30 * * * * *")
    @SchedulerLock(
            name = "taskletJobLock",
            lockAtMostFor = "PT40S",
            lockAtLeastFor = "PT5S"
    )
    public void runTaskletJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("taskletJob", System.currentTimeMillis()) // bắt buộc unique
                .toJobParameters();

        jobLauncher.start(taskletJob, params);
    }

}
