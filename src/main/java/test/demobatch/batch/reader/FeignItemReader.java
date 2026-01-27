package test.demobatch.batch.reader;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.stereotype.Component;
import test.demobatch.feign.ExternalApiClient;
import test.demobatch.model.User;

import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@StepScope
@Component("feignItemReader")
public class FeignItemReader implements ItemReader<User> {
    private final ExternalApiClient apiClient;
    private Iterator<User> dataIterator;
    @Override
    public User read() {
        // Chỉ gọi API 1 lần cho mỗi StepExecution
        if (dataIterator == null) {
            List<User> data = apiClient.getData();
            dataIterator = data.iterator();
        }

        if (dataIterator.hasNext()) {
            return dataIterator.next();
        }
        return null; // kết thúc step
    }
}
