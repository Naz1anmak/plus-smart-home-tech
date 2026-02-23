package ru.yandex.practicum.dto.shoppingCart.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ChangeProductQuantityRequest(

        @NotNull(message = "ID продукта не может быть null")
        UUID productId,

        @NotNull(message = "Количество продукта не может быть null")
        Long newQuantity
) {
}
