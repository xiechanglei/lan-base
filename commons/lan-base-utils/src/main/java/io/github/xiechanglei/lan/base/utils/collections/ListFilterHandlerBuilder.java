package io.github.xiechanglei.lan.base.utils.collections;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * List 数据过滤处理器生成器
 */
@Getter
@RequiredArgsConstructor
public class ListFilterHandlerBuilder<T> {
    private final List<T> originList;

    /**
     * 构建生成器的入口，必须要传入原始List
     * @param originList 原始List
     * @return ListFilterHandlerBuilder
     * @param <T> 泛型
     */
    public static <T> ListFilterHandlerBuilder<T> from(List<T> originList) {
        return new ListFilterHandlerBuilder<>(originList);
    }

    private Function<T, ?> keyFunction = t -> t;

    private BiFunction<T, T, T> updateFunction = (t1, t2) -> t1;

    public ListFilterHandlerBuilder<T> keyCompareFunction(Function<T, ?> keyFunction) {
        this.keyFunction = keyFunction;
        return this;
    }

    /**
     * 对保留下来的数据于原始数据进行比较，可以获取需要更新的数据
     * @param updateFunction 更新函数,如果不需要更新，就返回null,需要更新，则返回需要更新的数据，第一个参数是原始数据，第二个参数是新数据
     * @return ListFilterHandlerBuilder
     */
    public ListFilterHandlerBuilder<T> updateCompareFunction(BiFunction<T, T, T> updateFunction) {
        this.updateFunction = updateFunction;
        return this;
    }

    public ListFilterHandler<T> build() {
        return new ListFilterHandler<>(originList, keyFunction, updateFunction);
    }

    public ListFilterHandler<T> filter(Collection<T> list2) {
        return build().filter(list2);
    }

    public ListFilterHandler<T> filter(T t) {
        return build().filter(t);
    }


}
