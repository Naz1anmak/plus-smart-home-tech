package ru.yandex.practicum.dto.shoppingCart.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;

public record ShoppingCartDto(

        @NotNull(message = "Идентификатор корзины не может быть null")
        UUID shoppingCartId,

        @NotEmpty(message = "Список продуктов не может быть пустым")
        Map<UUID, Long> products
) {
}
