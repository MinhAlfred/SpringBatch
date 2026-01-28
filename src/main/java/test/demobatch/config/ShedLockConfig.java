package test.demobatch.config;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableSchedulerLock(defaultLockAtMostFor = "PT5M")
@RequiredArgsConstructor
public class ShedLockConfig {
    private final DataSource dataSource;

    @Bean
    public LockProvider lockProvider() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("SELECT DB_NAME()");
        System.out.println(">>> SHEDLOCK DS = " + dataSource);

        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withJdbcTemplate(jdbcTemplate)
                        .withTableName("dbo.shedlock")
                        .usingDbTime()
                        .build()
        );
    }
}
