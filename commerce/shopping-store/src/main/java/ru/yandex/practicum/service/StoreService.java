package ru.yandex.practicum.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.request.CreateProductDto;
import ru.yandex.practicum.dto.shoppingStore.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.dto.shoppingStore.request.UpdateProductDto;
import ru.yandex.practicum.dto.shoppingStore.response.PageProductDto;
import ru.yandex.practicum.dto.shoppingStore.response.ProductDto;

import java.util.UUID;

public interface StoreService {
    ProductDto createNewProduct(@Valid CreateProductDto productDto);

    ProductDto getProductById(UUID productId);

    PageProductDto<ProductDto> getProductsByCategory(ProductCategory category, Pageable pageable);

    ProductDto updateProduct(@Valid UpdateProductDto productDto);

    boolean setProductQuantityState(@Valid SetProductQuantityStateRequest stateRequest);

    boolean removeProductFromStore(UUID productId);
}
