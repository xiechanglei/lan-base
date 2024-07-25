package io.github.xiechanglei.lan.test;

import io.github.xiechanglei.lan.jpa.dsl.BlazeJPAQueryProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestController {
    private final BlazeJPAQueryProvider blazeJPAQueryProvider;

    @PostConstruct
    public void test() {
        List<String> fetch = blazeJPAQueryProvider.build().from(QCat.cat).select(QCat.cat.name).fetch();
        System.out.println(fetch);
    }
}
