package test.demobatch.batch.writer;

import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.demobatch.model.OutputData;
import test.demobatch.model.UserOutput;

import javax.sql.DataSource;
@Configuration
public class JdbcOutputWriterImplement {
    @Bean(name = "outputWriter")
    public JdbcBatchItemWriter<OutputData> outputWriter(
            DataSource ds,
            JdbcWriterConfig factory
    ) {
        return factory.build(ds, """
        INSERT INTO output_data(name, processed_at)
        VALUES (:name, :processedAt)
    """);
    }
    @Bean(name = "userWriter")
    public JdbcBatchItemWriter<UserOutput> userWriter(
            DataSource ds,
            JdbcWriterConfig factory
    ) {
        return factory.build(ds, """
        INSERT INTO USER_OUTPUT(user_id, id, title, body, processed_at)
        VALUES (:userId, :id, :title, :body, :processedAt)
    """);
    }
}
