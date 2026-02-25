package ru.yandex.practicum.dto.shoppingStore.response;


import ru.yandex.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.enums.ProductState;
import ru.yandex.practicum.dto.shoppingStore.enums.QuantityState;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDto(
        UUID productId,
        String productName,
        String description,
        String imageSrc,
        QuantityState quantityState,
        ProductState productState,
        ProductCategory productCategory,
        BigDecimal price
) {
}
