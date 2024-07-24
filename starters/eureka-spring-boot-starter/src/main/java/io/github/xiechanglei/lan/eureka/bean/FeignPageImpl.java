package io.github.xiechanglei.lan.eureka.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.*;
import org.springframework.lang.NonNull;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @param <T>
 */
@Getter
@Setter
public class FeignPageImpl<T> implements Page<T> {
    private boolean last;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private int size;
    private int number;
    private int numberOfElements;
    private List<T> content;
    private boolean empty;


    @Override
    public boolean hasContent() {
        return content != null && !content.isEmpty();
    }

    @NonNull
    public List<T> getContent() {
        return content == null ? List.of() : content;
    }

    /**
     * 反序列化之后，sort被抛弃了，所以这里返回null
     *
     * @return null
     */
    @Override
    @NonNull
    public Sort getSort() {
        return Sort.unsorted();
    }


    @Override
    public boolean hasNext() {
        return totalPages > number + 1;
    }

    @Override
    public boolean hasPrevious() {
        return number > 0;
    }

    @Override
    @NonNull
    public Pageable nextPageable() {
        return PageRequest.of(number + 1, size);
    }

    @Override
    @NonNull
    public Pageable previousPageable() {
        return PageRequest.of(number - 1, size);
    }

    @Override
    @NonNull
    public <U> Page<U> map(@NonNull Function<? super T, ? extends U> converter) {
        return new PageImpl<>(getContent().stream().map(converter).collect(Collectors.toList()),
                PageRequest.of(number, size),
                totalElements);
    }

    @Override
    @NonNull
    public Iterator<T> iterator() {
        return getContent().iterator();
    }
}