package ru.yandex.practicum.dto.shoppingStore.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.yandex.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.enums.ProductState;
import ru.yandex.practicum.dto.shoppingStore.enums.QuantityState;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductDto(

        UUID productId,

        @NotBlank(message = "Имя продукта не может быть пустым")
        String productName,

        @NotBlank(message = "Описание продукта не может быть пустым")
        String description,

        String imageSrc,

        @NotNull(message = "Статус количества продукта не может быть null")
        QuantityState quantityState,

        @NotNull(message = "Статус продукта не может быть null")
        ProductState productState,

        ProductCategory productCategory,

        @NotNull(message = "Цена продукта не может быть пустой")
        @DecimalMin(value = "1", message = "Цена продукта должна быть больше нуля")
        BigDecimal price
) {
}
