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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobOperator jobLauncher;
    private final Job daily9amJob;
    private final Job demoJob;
    private final Job taskletJob;
    private final Job demonstrationJob;
    private final Job demoRestartJob;

    @Scheduled(cron = "*/30 * * * * *") // mỗi phút
//    @SchedulerLock(
//            name = "daily9amJobScheduler",
//            lockAtMostFor = "PT3M",
//            lockAtLeastFor = "PT20S"
//    )
    public void runJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("demoRestartJob", 123123L) // bắt buộc unique
                .toJobParameters();

        jobLauncher.start(demoRestartJob, params);
    }
//
//    @Scheduled(cron = "0 */2 * * * *")
//    @SchedulerLock(
//            name = "demoJobLock",
//            lockAtMostFor = "PT2M",
//            lockAtLeastFor = "PT10S"
//    )
//    public void runDemoJob() throws Exception {
//        JobParameters params = new JobParametersBuilder()
//                .addLong("demoJob", System.currentTimeMillis()) // bắt buộc unique
//                .toJobParameters();
//
//        jobLauncher.start(demoJob, params);
//    }
//
//    @Scheduled(cron = "0 * * * * *")
////    @SchedulerLock(
////            name = "taskletJobLock",
////            lockAtMostFor = "PT2M",
////            lockAtLeastFor = "PT1M"
////    )
//    public void runTaskletJob() throws Exception {
//        LocalDateTime now = LocalDateTime.now();
//        String key = now.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmm"));
//        JobParameters params = new JobParametersBuilder()
////                .addString("taskletJob", String.valueOf(UUID.randomUUID())) // bắt buộc unique
//                .addString("demobatch", key)
//                .toJobParameters();
//
//        jobLauncher.start(taskletJob, params);
//    }

//    @Scheduled(cron = "0 */2 * * * *")
//    @SchedulerLock(
//            name = "demonstrationJobLock",
//            lockAtMostFor = "PT6M",
//            lockAtLeastFor = "PT2M"
//    )
//    public void runDemonstrationJob() throws Exception {
//        JobParameters params = new JobParametersBuilder()
//                .addLong("demonstrationJob", System.currentTimeMillis())
//                .toJobParameters();
//
//        jobLauncher.start(demonstrationJob, params);
//    }

}
