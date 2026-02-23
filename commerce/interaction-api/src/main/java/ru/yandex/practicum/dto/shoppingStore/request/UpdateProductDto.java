package ru.yandex.practicum.dto.shoppingStore.request;

import jakarta.validation.constraints.DecimalMin;
import ru.yandex.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.enums.ProductState;
import ru.yandex.practicum.dto.shoppingStore.enums.QuantityState;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProductDto(

        UUID productId,

        String productName,

        String description,

        String imageSrc,

        QuantityState quantityState,

        ProductState productState,

        ProductCategory productCategory,

        @DecimalMin(value = "1", message = "Цена продукта должна быть больше нуля")
        BigDecimal price
) {
}
