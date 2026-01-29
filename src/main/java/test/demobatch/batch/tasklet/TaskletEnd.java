package test.demobatch.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskletEnd implements Tasklet {
    @Override
    public @Nullable RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("=== STEP 2 EXECUTING ===");

        // Đọc data từ Job Context (đã lưu ở step 1)
        ExecutionContext jobContext = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();
        String dataFromStep1 = jobContext.getString("dataFromStep1");
        System.out.println("Received from Step 1: " + dataFromStep1);

        return RepeatStatus.FINISHED;
    }
}
