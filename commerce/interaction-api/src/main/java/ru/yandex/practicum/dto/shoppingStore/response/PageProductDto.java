package ru.yandex.practicum.dto.shoppingStore.response;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record PageProductDto<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last,
        int size,
        int number,
        List<SortObject> sort,
        int numberOfElements,
        PageableObject pageable,
        boolean empty
) {

    public static <S, T> PageProductDto<T> from(Page<S> page, Function<S, T> mapper) {

        List<SortObject> sortObjects = page.getSort().stream()
                .map(order -> new SortObject(
                        order.getDirection().name(),
                        order.getNullHandling().name(),
                        order.isAscending(),
                        order.getProperty(),
                        order.isIgnoreCase()
                ))
                .toList();

        PageableObject pageableObject = new PageableObject(
                page.getPageable().getOffset(),
                sortObjects,
                page.getPageable().isUnpaged(),
                page.getPageable().isPaged(),
                page.getNumber(),
                page.getSize()
        );

        return new PageProductDto<>(
                page.getContent().stream().map(mapper).toList(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.getSize(),
                page.getNumber(),
                sortObjects,
                page.getNumberOfElements(),
                pageableObject,
                page.isEmpty()
        );
    }
}
