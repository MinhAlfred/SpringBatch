package test.demobatch.batch.writer;

import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcWriterConfig {
    public <T> JdbcBatchItemWriter<T> build(
            DataSource dataSource,
            String sql
    ) {
        return new JdbcBatchItemWriterBuilder<T>()
                .dataSource(dataSource)
                .sql(sql)
                .beanMapped()
                .build();
    }
}
