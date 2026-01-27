package test.demobatch.batch.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.demobatch.model.InputData;
import test.demobatch.model.OutputData;

import java.time.LocalDateTime;

@Configuration
@Slf4j
public class ProcessorConfig {
    @Bean(name = "itemProcessor")
    public ItemProcessor<InputData, OutputData> processor() {
        return item -> {
            log.info("Processing: {}", item.name());
            return new OutputData(
                    item.name().toUpperCase(),
                    LocalDateTime.now()
            );
        };
    }
}
