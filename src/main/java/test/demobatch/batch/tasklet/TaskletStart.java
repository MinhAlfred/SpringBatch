package test.demobatch.batch.tasklet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class TaskletStart implements Tasklet {
    @Override
    public @Nullable RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("=== STEP 1 EXECUTING ===");

        // Lấy Job Execution ID
        Long jobExecutionId = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecutionId();
        System.out.println("Job Execution ID: " + jobExecutionId);

        // Lấy Step Execution ID
        Long stepExecutionId = chunkContext.getStepContext()
                .getStepExecution()
                .getId();
        System.out.println("Step Execution ID: " + stepExecutionId);

        // Lưu vào Job Context (share giữa các steps)
        ExecutionContext jobContext = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();
        jobContext.putString("dataFromStep1", "Hello from Step 1");

        // Lưu vào Step Context (chỉ step này)
        ExecutionContext stepContext = chunkContext.getStepContext()
                .getStepExecution()
                .getExecutionContext();
        stepContext.putLong("recordsProcessed", 100);

        return RepeatStatus.FINISHED;
    }
}
