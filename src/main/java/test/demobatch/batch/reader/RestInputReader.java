package test.demobatch.batch.reader;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import test.demobatch.model.InputData;

import java.util.Arrays;
import java.util.Iterator;

@Component("restInputReader")
@StepScope
@RequiredArgsConstructor
public class RestInputReader implements ItemReader<InputData> {
    private final RestTemplate restTemplate;
    private Iterator<InputData> iterator;
    @Override
    public @Nullable InputData read() throws Exception {
        if (iterator == null) {
            InputData[] data = restTemplate.getForObject(
                    "http://localhost:8080/api/input-data",
                    InputData[].class
            );

            iterator = Arrays.asList(
                    data != null ? data : new InputData[0]
            ).iterator();
        }

        return iterator.hasNext() ? iterator.next() : null;
    }
}
