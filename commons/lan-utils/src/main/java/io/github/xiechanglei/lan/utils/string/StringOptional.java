package io.github.xiechanglei.lan.utils.string;

import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class StringOptional {
    private final String value;

    public static StringOptional of(String value) {
        return new StringOptional(value);
    }

    /**
     * 如果值不为空，则执行
     */
    public void ifPresent(Consumer<String> consumer) {
        if (StringHelper.isNotBlank(value)) {
            consumer.accept(value);
        }
    }

    /**
     * 如果值为空，则执行
     */
    public void ifNotPresent(Consumer<String> consumer) {
        if (!StringHelper.isNotBlank(value)) {
            consumer.accept(value);
        }
    }


    /**
     * ifNotPresentThrow
     */
    public <X extends Throwable> StringOptional ifNotPresentThrow(X e) throws X {
        if (StringHelper.isNotBlank(value)) {
            return this;
        } else {
            throw e;
        }
    }

    /**
     * 如果值为空，使用新值构建StringOptional对象
     */
    public StringOptional orFill(String defaultValue) {
        if (!StringHelper.isNotBlank(value)) {
            return new StringOptional(defaultValue);
        }
        return this;
    }

    /**
     * 如果值为空，使用新的supplier构建StringOptional对象
     */
    public StringOptional orFillProduce(Supplier<String> supplier) {
        if (!StringHelper.isNotBlank(value)) {
            return new StringOptional(supplier.get());
        }
        return this;
    }


    /**
     * get
     */
    public String get() {
        return value;
    }

}
