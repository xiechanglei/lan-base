package io.github.xiechanglei.lan.lang.collections;

import java.lang.reflect.Array;
import java.util.*;

public class ArrayHelper {
    /**
     * 合并数组,当所有的数组都为null时返回null，否则返回合并后的数组
     */
    @SafeVarargs
    public static <T> T[] concat(T[]... arrays) {
        List<T> list = new ArrayList<>();
        T[] res_type = null;
        for (T[] array : arrays) {
            if (array != null) {
                res_type = array;
                Collections.addAll(list, array);
            }
        }
        if (res_type == null) {
            return null;
        }
        return list.toArray(res_type);
    }

    /**
     * 数组元素去重
     *
     * @param arr 数组
     * @param <T> 数组元素类型
     * @return 去重后的数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] distinct(T[] arr) {
        TreeSet<T> set = new TreeSet<>(Arrays.asList(arr));
        T[] res = (T[]) Array.newInstance(arr.getClass().getComponentType(), 0);
        return set.toArray(res);
    }

}
