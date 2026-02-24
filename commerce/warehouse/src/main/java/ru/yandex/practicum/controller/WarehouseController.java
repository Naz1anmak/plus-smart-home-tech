package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.api.WarehouseOperations;
import ru.yandex.practicum.dto.shoppingCart.response.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.response.AddressDto;
import ru.yandex.practicum.dto.warehouse.response.BookedProductsDto;
import ru.yandex.practicum.service.WarehouseService;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController implements WarehouseOperations {
    private final WarehouseService warehouseService;

    @Override
    public void newProductInWarehouse(@RequestBody @Valid NewProductInWarehouseRequest request) {
        warehouseService.newProductInWarehouse(request);
    }

    @Override
    public void addProductToWarehouse(@RequestBody @Valid AddProductToWarehouseRequest request) {
        warehouseService.addProductToWarehouse(request);
    }

    @Override
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCart) {
        return warehouseService.checkProductQuantityEnoughForShoppingCart(shoppingCart);
    }

    @Override
    public AddressDto getWarehouseAddress() {
        return warehouseService.getWarehouseAddress();
    }
}
