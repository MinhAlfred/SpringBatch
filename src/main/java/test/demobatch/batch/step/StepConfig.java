package test.demobatch.batch.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import test.demobatch.model.InputData;
import test.demobatch.model.OutputData;

@Configuration
@RequiredArgsConstructor
public class StepConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean(name = "stepA")
    public Step stepA(@Qualifier("jdbcCursorItemReader") ItemReader<InputData> readerA,
                      @Qualifier("itemProcessor")ItemProcessor<InputData, OutputData> processorA,
                      @Qualifier("jdbcBatchItemWriter") ItemWriter<OutputData> writerA) {

        return new StepBuilder("stepA", jobRepository)
                .<InputData, OutputData>chunk(100)
                .reader(readerA)
                .transactionManager(transactionManager)
                .processor(processorA)
                .writer(writerA)
                .build();
    }

    @Bean(name = "stepB")
    public Step stepB(@Qualifier("restInputReader") ItemReader<InputData> readerB,
                      @Qualifier("itemProcessor") ItemProcessor<InputData, OutputData> processorB,
                      @Qualifier("jdbcBatchItemWriter") ItemWriter<OutputData> writerB) {

        return new StepBuilder("stepB", jobRepository)
                .<InputData, OutputData>chunk(100)
                .transactionManager(transactionManager)
                .reader(readerB)
                .processor(processorB)
                .writer(writerB)
                .build();
    }
}
