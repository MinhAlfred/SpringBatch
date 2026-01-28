package test.demobatch.batch.tasklet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import test.demobatch.feign.ExternalApiClient;
@RequiredArgsConstructor
@Component
@Slf4j
public class ApiSyncTasklet implements Tasklet {
    private final ExternalApiClient apiClient;

    @Override
    public @Nullable RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        var user = apiClient.getDataById(1L);
        log.info("user={}", user);
        return RepeatStatus.FINISHED;
    }
}
