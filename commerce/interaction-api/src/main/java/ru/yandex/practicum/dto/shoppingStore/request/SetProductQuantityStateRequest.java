package ru.yandex.practicum.dto.shoppingStore.request;

import jakarta.validation.constraints.NotNull;
import ru.yandex.practicum.dto.shoppingStore.enums.QuantityState;

import java.util.UUID;

public record SetProductQuantityStateRequest(

        @NotNull(message = "Идентификатор продукта не может быть null")
        UUID productId,

        @NotNull(message = "Статус количества не может быть null")
        QuantityState quantityState
) {
}
