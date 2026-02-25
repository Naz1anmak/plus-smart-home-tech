package ru.yandex.practicum.api;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.enums.QuantityState;
import ru.yandex.practicum.dto.shoppingStore.request.CreateProductDto;
import ru.yandex.practicum.dto.shoppingStore.request.UpdateProductDto;
import ru.yandex.practicum.dto.shoppingStore.response.PageProductDto;
import ru.yandex.practicum.dto.shoppingStore.response.ProductDto;

import java.util.UUID;

/**
 * Интерфейс операций магазина.
 * Определяет все операции с HTTP-маппингами и валидациями.
 */
public interface StoreOperations {

    /**
     * Создать новый продукт в магазине
     */
    @PutMapping
    ProductDto createNewProduct(@RequestBody @Valid CreateProductDto productDto);

    /**
     * Получить продукт по ID
     */
    @GetMapping("/{productId}")
    ProductDto getProductById(@PathVariable UUID productId);

    /**
     * Получить продукты по категории с пагинацией
     */
    @GetMapping
    PageProductDto<ProductDto> getProductsByCategory(@RequestParam ProductCategory category,
                                                     @PageableDefault(size = 20) Pageable pageable);

    /**
     * Обновить информацию о продукте
     */
    @PostMapping
    ProductDto updateProduct(@RequestBody @Valid UpdateProductDto productDto);

    /**
     * Установить состояние количества продукта
     */
    @PostMapping("/quantityState")
    boolean setProductQuantityState(@RequestParam UUID productId,
                                    @RequestParam QuantityState quantityState);

    /**
     * Удалить продукт из ассортимента магазина
     */
    @PostMapping("/removeProductFromStore")
    boolean removeProductFromStore(@RequestBody UUID productId);
}
