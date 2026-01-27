package test.demobatch.batch.reader;

import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import test.demobatch.model.InputData;

import javax.sql.DataSource;
@Component
public class JdbcReaderConfig {
    @Bean(name = "jdbcCursorItemReader")
    public JdbcCursorItemReader<InputData> reader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<InputData>()
                .name("inputReader")
                .dataSource(dataSource)
                .sql("SELECT id, name, status FROM input_data WHERE status = 'NEW'")
                .rowMapper((rs, rowNum) ->
                        new InputData(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("status")
                        ))
                .build();
    }
}
