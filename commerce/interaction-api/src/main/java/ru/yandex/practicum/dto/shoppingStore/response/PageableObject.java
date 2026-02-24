package ru.yandex.practicum.dto.shoppingStore.response;

import java.util.List;

public record PageableObject(
        long offset,
        List<SortObject> sort,
        boolean unpaged,
        boolean paged,
        int pageNumber,
        int pageSize
) {
}
