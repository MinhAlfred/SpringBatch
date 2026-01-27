package test.demobatch.batch.writer;

import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import test.demobatch.model.OutputData;
import test.demobatch.model.User;
import test.demobatch.model.UserOutput;

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
    @Bean(name = "userWriter")
    public JdbcBatchItemWriter<UserOutput> userWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<UserOutput>()
                .dataSource(dataSource)
                .sql("""
                 INSERT INTO USER_OUTPUT(user_id,id,title,body, processed_at)
                 VALUES (:userId, :id, :title, :body, :processedAt)
                 """)
                .beanMapped()
                .build();
    }
}
