package io.github.xiechanglei.lan.test;

import io.github.xiechanglei.lan.beans.exception.BusinessException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Service
@FeignClient(name = "service-test-client")
public interface TestFeignClient {
    @RequestMapping("/call")
    List<TestController.Animal> call(@RequestParam Date date) throws BusinessException;
}
