package test.demobatch.mock;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbCheck {

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void checkDb() {
        String db = jdbcTemplate.queryForObject("SELECT DB_NAME()", String.class);
        String schema = jdbcTemplate.queryForObject(
                "SELECT SCHEMA_NAME()", String.class);

        System.out.println(">>> CONNECTED DB = " + db);
        System.out.println(">>> DEFAULT SCHEMA = " + schema);
    }
}
