package ru.yandex.practicum.api;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.shoppingCart.response.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.response.AddressDto;
import ru.yandex.practicum.dto.warehouse.response.BookedProductsDto;

/**
 * Интерфейс операций склада.
 * Определяет все операции с HTTP-маппингами и валидациями.
 */
public interface WarehouseOperations {

    /**
     * Добавить новый продукт на склад
     */
    @PutMapping
    void newProductInWarehouse(@RequestBody @Valid NewProductInWarehouseRequest request);

    /**
     * Увеличить количество существующего продукта на складе
     */
    @PostMapping("/add")
    void addProductToWarehouse(@RequestBody @Valid AddProductToWarehouseRequest request);

    /**
     * Проверить наличие достаточного количества товаров для корзины
     */
    @PostMapping("/check")
    BookedProductsDto checkProductQuantityEnoughForShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCart);

    /**
     * Получить адрес склада
     */
    @GetMapping("/address")
    AddressDto getWarehouseAddress();
}
