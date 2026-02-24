package ru.yandex.practicum.service;

import jakarta.validation.Valid;
import ru.yandex.practicum.dto.shoppingCart.response.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.response.AddressDto;
import ru.yandex.practicum.dto.warehouse.response.BookedProductsDto;

public interface WarehouseService {
    void newProductInWarehouse(@Valid NewProductInWarehouseRequest request);

    void addProductToWarehouse(@Valid AddProductToWarehouseRequest request);

    BookedProductsDto checkProductQuantityEnoughForShoppingCart(@Valid ShoppingCartDto shoppingCart);

    AddressDto getWarehouseAddress();
}
