package ru.yandex.practicum.dto.warehouse.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddProductToWarehouseRequest(

        @NotNull(message = "Идентификатор продукта не может быть null")
        UUID productId,

        @NotNull(message = "Количество продукта не может быть null")
        @Min(1)
        Long quantity
) {
}
