package io.github.xiechanglei.lan.base.rbac.util;

import io.github.xiechanglei.lan.base.utils.collections.ListFilterHandler;
import io.github.xiechanglei.lan.base.utils.collections.ListFilterHandlerBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class DataUpdaterUtil {
    public static <T extends ComparedEntity, U> void update(JpaRepository<T, U> repository, List<T> newData) {
        ListFilterHandler<T> filter = ListFilterHandlerBuilder.from(repository.findAll()).
                keyCompareFunction(T::buildUniqueId).
                updateCompareFunction((originData, nw) -> originData.changeIfNotSame(nw) ? originData : null).
                filter(newData);
        repository.deleteAll(filter.getDeleteList());
        repository.saveAll(filter.getAddList());
        repository.saveAll(filter.getUpdateList());
    }
}
