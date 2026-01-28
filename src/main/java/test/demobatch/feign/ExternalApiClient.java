package test.demobatch.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import test.demobatch.model.User;

import java.util.List;

@FeignClient(name = "external-api", url = "https://jsonplaceholder.typicode.com")
public interface ExternalApiClient {

    @GetMapping("/posts")
    List<User> getData();

    @GetMapping("/posts/{id}")
    User getDataById(@PathVariable("id") Long id);
}
