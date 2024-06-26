package io.github.xiechanglei.lan.base.beans.func;

import java.util.Objects;

public interface ThreeConsumer<T, U, R> {


    void accept(T t, U u, R r);


    default ThreeConsumer<T, U, R> andThen(ThreeConsumer<? super T, ? super U, ? super R> after) {
        Objects.requireNonNull(after);

        return (t, u, r) -> {
            accept(t, u, r);
            after.accept(t, u, r);
        };
    }
}