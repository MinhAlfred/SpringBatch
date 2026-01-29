package test.demobatch.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RestartJobDemo {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    // Biến static để giả lập trạng thái: True là sẽ ném lỗi, False là chạy ngon
    // Trong thực tế, đây có thể là lỗi mạng hoặc data bẩn
    public static boolean SIMULATE_ERROR = true;

    @Bean
    public Step step1_Success() {
        return new StepBuilder("step1_Success", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> STEP 1: Đã chạy xong và lưu vào DB.");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step step2_FailFirstTime() {
        return new StepBuilder("step2_FailFirstTime", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> STEP 2: Đang bắt đầu xử lý...");

                    if (SIMULATE_ERROR) {
                        log.error("!!!! STEP 2: GẶP LỖI GIẢ LẬP !!!!");
                        // Gán lại false để lần sau chạy lại sẽ thành công
                        SIMULATE_ERROR = false;
                        throw new RuntimeException("Đây là lỗi giả lập để test Restart!");
                    }

                    log.info(">>>> STEP 2: Xử lý thành công (Lần chạy lại).");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Job demoRestartJob() {
        return new JobBuilder("demoRestartJob", jobRepository)
                // Mặc định restartable là true, nhưng khai báo rõ cho dễ hiểu
                // Nếu false thì job fail sẽ không cho chạy lại

                .start(step1_Success())
                .next(step2_FailFirstTime())
                .build();
    }
}