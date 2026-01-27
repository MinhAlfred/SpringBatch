package test.demobatch.batch.writer;

import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import test.demobatch.model.OutputData;

import javax.sql.DataSource;
@Configuration
public class JdbcOutputWriterConfig {
    @Bean(name = "jdbcBatchItemWriter")
    public JdbcBatchItemWriter<OutputData> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<OutputData>()
                .dataSource(dataSource)
                .sql("""
                 INSERT INTO output_data(name, processed_at)
                 VALUES (:name, :processedAt)
                 """)
                .beanMapped()
                .build();
    }
}
