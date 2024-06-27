package io.github.xiechanglei.lan.beans.mutiple;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 三个参数的返回值
 * @param <T> 第一个参数类型
 * @param <E> 第二个参数类型
 * @param <F> 第三个参数类型
 */

@Getter
@AllArgsConstructor
public class ThreeResponse<T,E,F> {
    private T one;
    private E two;
    private F three;


    public static <T,E,F> ThreeResponse<T,E,F> of(T one,E two,F three){
        return new ThreeResponse<>(one,two,three);
    }
}