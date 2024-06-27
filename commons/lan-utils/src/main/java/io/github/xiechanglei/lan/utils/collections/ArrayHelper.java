package io.github.xiechanglei.lan.utils.collections;

import java.lang.reflect.Array;
import java.util.*;

public class ArrayHelper {
    /**
     * 合并数组,
     */
    @SafeVarargs
    public static <T> T[] concat(T[]... arrays) {
        List<T> list = new ArrayList<>();
        for (T[] array : arrays) {
            if (array != null) {
                Collections.addAll(list, array);
            }
        }
        return list.toArray(arrays[0]);
    }

    // 去重数组
    @SuppressWarnings("unchecked")
    public static <T> T[] distinct(T[] arr) {
        TreeSet<T> set = new TreeSet<>(Arrays.asList(arr));
        T[] res = (T[]) Array.newInstance(arr.getClass().getComponentType(), 0);
        return set.toArray(res);
    }

}
