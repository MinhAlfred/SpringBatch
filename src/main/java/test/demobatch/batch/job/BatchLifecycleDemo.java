package test.demobatch.batch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import test.demobatch.batch.tasklet.TaskletEnd;
import test.demobatch.batch.tasklet.TaskletStart;

@Configuration
//@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchLifecycleDemo {
    private final TaskletEnd taskletEnd;
    private final TaskletStart taskletStart;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Step stepStart() {
        return new StepBuilder("stepStart", jobRepository)
                .tasklet(taskletStart,transactionManager)
                .build();
    }

    @Bean
    public Step stepEnd() {
        return new StepBuilder("stepEnd", jobRepository)
                .tasklet(taskletEnd,transactionManager)
                .build();
    }
    @Bean
    public Job demonstrationJob() {
        return new JobBuilder("demonstrationJob", jobRepository)
                .start(stepStart())
                .next(stepEnd())
                .build();
    }

}
