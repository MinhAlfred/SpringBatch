package test.demobatch.batch.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import test.demobatch.batch.reader.ApiItemReader;
import test.demobatch.batch.tasklet.ApiSyncTasklet;
import test.demobatch.feign.ExternalApiClient;
import test.demobatch.model.InputData;
import test.demobatch.model.OutputData;
import test.demobatch.model.User;
import test.demobatch.model.UserOutput;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class StepConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ApiSyncTasklet apiSyncTasklet;

    @Bean
    @StepScope
    public ItemReader<User> userApiReader(ExternalApiClient apiClient) {
        return new ApiItemReader<>(apiClient::getData);
    }


    @Bean(name = "stepA")
    public Step stepA(@Qualifier("jdbcCursorItemReader") ItemReader<InputData> readerA,
                      @Qualifier("itemProcessor")ItemProcessor<InputData, OutputData> processorA,
                      @Qualifier("outputWriter") ItemWriter<OutputData> writerA) {

        return new StepBuilder("stepA", jobRepository)
                .<InputData, OutputData>chunk(10)
                .reader(readerA)
                .transactionManager(transactionManager)
                .processor(processorA)
                .writer(writerA)
                .build();
    }

    @Bean(name = "stepB")
    public Step stepB(@Qualifier("userApiReader") ItemReader<User> readerB,
                      @Qualifier("apiItemProcessor") ItemProcessor<User, UserOutput> processorB,
                      @Qualifier("userWriter") ItemWriter<UserOutput> writerB) {

        return new StepBuilder("stepB", jobRepository)
                .<User, UserOutput>chunk(10)
                .transactionManager(transactionManager)
                .reader(readerB)
                .processor(processorB)
                .writer(writerB)
                .build();
    }

    @Bean(name = "stepC")
    public Step stepC(){
        return new StepBuilder("stepC", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("Step C executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    @Bean
    public Step stepD() {
        return new StepBuilder("stepD", jobRepository)
                .tasklet(apiSyncTasklet, transactionManager)
                .build();
    }
}
