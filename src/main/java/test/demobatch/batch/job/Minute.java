package test.demobatch.batch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@RequiredArgsConstructor
public class Minute {
    private final JobRepository jobRepository;
    private final Step stepA;
    private final Step stepB;

    @Bean(name = "daily9amJob")
    public Job daily9amJob() {
        return new JobBuilder("daily9amJob", jobRepository)
                .start(stepA)
                .next(stepB)
                .build();
    }
}
