package io.github.xiechanglei.lan.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestFeignClient testFeignClient;

    @RequestMapping("/test")
    public List<Animal> test() {
        return testFeignClient.call(new Date());
    }

    @RequestMapping("/call")
    public List<Animal> call(Date date) {
        return List.of(new Animal("dog", 11));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Animal {
        private String name;
        private Integer age;
    }
}
