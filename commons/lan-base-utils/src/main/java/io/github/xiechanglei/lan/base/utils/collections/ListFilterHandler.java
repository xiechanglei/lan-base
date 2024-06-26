package io.github.xiechanglei.lan.base.utils.collections;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * List 数据过滤处理器，
 * 前置需求是将两个List中的数据进行比对，然后形成四个List的数据
 * 1. 保留数据 两个List中都存在的数据
 * 2. 删除数据 第一个List中存在，第二个List中不存在的数据
 * 3. 新增数据 第一个List中不存在，第二个List中存在的数据
 */
public class ListFilterHandler<T> {
    // 保留数据，里面存在的是新产生的数据
    private final Map<Object, T> retainMap = new HashMap<>();
    // 删除数据，数据库里面的数据
    private final Map<Object, T> deleteMap = new HashMap<>();
    // 原始数据，数据库里面原始的数据
    private final Map<Object, T> originMap = new HashMap<>();
    // 新增数据，里面存在的是新产生的数据
    private final Map<Object, T> addMap = new HashMap<>();

    // key的生成器，默认是对象本身，这对于复杂对象当然是不合理的
    private Function<T, ?> keyFunction = t -> t;

    // 是否需要更新的处理器
    private BiFunction<T, T, T> updateFunction = (t1, t2) -> null;

    @SuppressWarnings("DataFlowIssue")
    public ListFilterHandler(List<T> originList, Function<T, ?> keyFunction, BiFunction<T, T, T> updateFunction) {
        if (keyFunction != null) {
            this.keyFunction = keyFunction;
        }
        if (updateFunction != null) {
            this.updateFunction = updateFunction;
        }
        for (T t : originList) {
            Object key = keyFunction.apply(t);
            deleteMap.put(key, t);
            originMap.put(key, t);
        }
    }

    /**
     * 对后续的数据进行过滤，将数据拆分到保留、删除、新增三个List中，其实是循环调用filter方法
     *
     * @param list2 数据
     * @return ListFilterHandler
     */
    public ListFilterHandler<T> filter(Collection<T> list2) {
        list2.forEach(this::filter);
        return this;
    }

    /**
     * 对后续的数据进行过滤，将数据拆分到保留、删除、新增三个List中
     *
     * @param t 数据
     * @return ListFilterHandler
     */
    public ListFilterHandler<T> filter(T t) {
        Object key = keyFunction.apply(t);
        if (deleteMap.containsKey(key)) {
            // 保留数据,将新数据放入保留数据中，方便最后的更新判断
            retainMap.put(key, t);
            deleteMap.remove(key);
        } else {
            //新增数据
            addMap.put(key, t);
        }
        return this;
    }

    /**
     * 获取保留的数据
     *
     * @return 保留的数据
     */
    public List<T> getRetainList() {
        return new ArrayList<>(retainMap.values());
    }

    /**
     * 获取更新的数据
     */
    public List<T> getUpdateList() {
        List<T> updateList = new ArrayList<>();
        for (Map.Entry<Object, T> entry : retainMap.entrySet()) {
            Object key = entry.getKey();
            T origin = originMap.get(key);
            T retain = entry.getValue();
            T apply = updateFunction.apply(origin, retain);
            if (apply != null) {
                updateList.add(apply);
            }
        }
        return updateList;
    }

    /**
     * 获取删除的数据
     *
     * @return 删除的数据
     */
    public List<T> getDeleteList() {
        return new ArrayList<>(deleteMap.values());
    }

    /**
     * 获取新增的数据
     *
     * @return 新增的数据
     */
    public List<T> getAddList() {
        return new ArrayList<>(addMap.values());
    }

}
