package io.github.xiechanglei.lan.utils.string;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * test case for {@link StringOptional}
 */
public class StringOptionalTest {

    /**
     * test case for {@link StringOptional#of(java.lang.String)}
     */
    @Test
    public void testOf(){
        // if the value is not null, return the value
        assert Objects.equals(StringOptional.of("foo").get(), "foo");
        // if the value is null, return null
        assert StringOptional.of(null).get() == null;
        // if the value is empty, return empty
        assert StringOptional.of("").get().isEmpty();
    }

    /**
     * test case for {@link StringOptional#or(java.lang.String)}
     */
    @Test
    public void testOfElse(){
        // if the value is not null, return the value
        assert Objects.equals(StringOptional.of("foo").or("").get(), "foo");
        // if the value is null, return the value with or method provide
        assert Objects.equals(StringOptional.of(null).or("foo").get(), "foo");
        // if the value is empty, return the value with or method provide
        assert Objects.equals(StringOptional.of("").or("foo").get(), "foo");
    }

    /**
     * test case for {@link StringOptional#ifPresent(java.util.function.Consumer)}
     */
    @Test
    public void testIfPresent() {
        AtomicReference<String> value = new AtomicReference<>();
        StringOptional.of("foo").ifPresent(value::set);
        // if the value is not null, the consumer will be executed
        assert "foo".equals(value.get());

        value.set(null);
        StringOptional.of("").ifPresent(value::set);
        // if the value is empty, the consumer will not be executed
        assert value.get() == null;
    }

    /**
     * test case for {@link StringOptional#ifNotPresent(java.util.function.Consumer)}
     */
    @Test
    public void testIfNotPresent() {
        AtomicReference<String> value = new AtomicReference<>();
        StringOptional.of(null).ifNotPresent(v -> value.set("foo"));
        // if the value is null, the consumer will be executed
        assert "foo".equals(value.get());
        StringOptional.of("").ifNotPresent(v -> value.set("foo"));
        // if the value is empty, the consumer will be executed
        assert "foo".equals(value.get());
        value.set(null);
        StringOptional.of("foo").ifNotPresent(v -> value.set("foo"));
        // if the value is not empty, the consumer will not be executed
        assert value.get() == null;
    }

    /**
     * test case for {@link StringOptional#ifNotPresentThrow(java.lang.Throwable)}
     */
    @Test
    public void testIfNotPresentThrow() {
        // if the value is not null, throw exception
        assertThrows(IllegalArgumentException.class, () -> StringOptional.of(null).ifNotPresentThrow(new IllegalArgumentException()));
        // if the value is empty, throw exception
        assertThrows(IllegalArgumentException.class, () -> StringOptional.of("").ifNotPresentThrow(new IllegalArgumentException()));
        // if the value is not empty, the method will not throw exception
        StringOptional.of("foo").ifNotPresentThrow(new IllegalArgumentException());
    }

}
