package test.demobatch.batch.reader;

import org.springframework.batch.infrastructure.item.ItemReader;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class ApiItemReader<T> implements ItemReader<T> {
    private final Supplier<List<T>> supplier;
    private Iterator<T> iterator;

    public ApiItemReader(Supplier<List<T>> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T read() {
        if (iterator == null) {
            List<T> data = supplier.get(); // gọi API 1 lần
            iterator = data.iterator();
        }
        return iterator.hasNext() ? iterator.next() : null;
    }
}
