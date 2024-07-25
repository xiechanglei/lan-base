package io.github.xiechanglei.lan.jpa.dsl;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlazeJPAQueryProvider {
    private final EntityManager entityManager;
    private final CriteriaBuilderFactory criteriaBuilderFactory;

    public BlazeJPAQuery<Object> build(){
        return new BlazeJPAQuery<>(entityManager, criteriaBuilderFactory);
    }
}
