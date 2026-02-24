package ru.yandex.practicum.dto.warehouse.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewProductInWarehouseRequest(

        @NotNull(message = "Идентификатор товара не может быть null")
        UUID productId,

        Boolean fragile,

        @NotNull(message = "Размеры товара не могут быть null")
        DimensionDto dimension,

        @NotNull(message = "Вес товара не может быть null")
        @Min(1)
        Double weight
) {
}
