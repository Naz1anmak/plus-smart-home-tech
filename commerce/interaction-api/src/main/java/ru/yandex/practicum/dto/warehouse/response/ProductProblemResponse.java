package ru.yandex.practicum.dto.warehouse.response;

import java.util.List;

public record ProductProblemResponse(
        List<ProductProblem> problems
) {
}

