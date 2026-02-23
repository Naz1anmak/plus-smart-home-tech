package ru.yandex.practicum.dto.warehouse.response;

import jakarta.validation.constraints.NotNull;

public record BookedProductsDto(

        @NotNull(message = "Общий вес доставки не может быть null")
        Double deliveryWeight,

        @NotNull(message = "Общий объём доставки не может быть null")
        Double deliveryVolume,

        @NotNull(message = "Поле 'Есть ли хрупкие вещи в доставке' не может быть null")
        Boolean fragile
) {
}
