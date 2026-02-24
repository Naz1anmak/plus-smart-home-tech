package ru.yandex.practicum.dto.warehouse.response;

import java.util.UUID;

public record ProductProblem(
        UUID productId,
        String reason,
        Long availableQuantity,
        Long requestedQuantity
) {
}
