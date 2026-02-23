package ru.yandex.practicum.dto.warehouse.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DimensionDto(

        @NotNull(message = "Ширина не может быть null")
        @Min(1)
        Double width,

        @NotNull(message = "Высота не может быть null")
        @Min(1)
        Double height,

        @NotNull(message = "Глубина не может быть null")
        @Min(1)
        Double depth
) {
}
