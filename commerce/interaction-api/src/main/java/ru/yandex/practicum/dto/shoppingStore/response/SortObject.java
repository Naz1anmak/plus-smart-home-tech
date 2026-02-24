package ru.yandex.practicum.dto.shoppingStore.response;

public record SortObject(
        String direction,
        String nullHandling,
        boolean ascending,
        String property,
        boolean ignoreCase
) {
}
