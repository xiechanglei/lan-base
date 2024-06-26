package io.github.xiechanglei.lan.base.beans.mutiple;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 三个参数的返回值
 * @param <T>
 * @param <E>
 * @param <F>
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