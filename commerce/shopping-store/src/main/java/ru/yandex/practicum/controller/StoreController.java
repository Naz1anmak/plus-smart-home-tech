package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.api.StoreOperations;
import ru.yandex.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.enums.QuantityState;
import ru.yandex.practicum.dto.shoppingStore.request.CreateProductDto;
import ru.yandex.practicum.dto.shoppingStore.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.dto.shoppingStore.request.UpdateProductDto;
import ru.yandex.practicum.dto.shoppingStore.response.PageProductDto;
import ru.yandex.practicum.dto.shoppingStore.response.ProductDto;
import ru.yandex.practicum.service.StoreService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor
public class StoreController implements StoreOperations {
    private final StoreService storeService;

    @Override
    public ProductDto createNewProduct(@RequestBody @Valid CreateProductDto productDto) {
        return storeService.createNewProduct(productDto);
    }

    @Override
    public ProductDto getProductById(@PathVariable UUID productId) {
        return storeService.getProductById(productId);
    }

    @Override
    public PageProductDto<ProductDto> getProductsByCategory(@RequestParam ProductCategory category,
                                                            @PageableDefault(size = 20) Pageable pageable) {
        return storeService.getProductsByCategory(category, pageable);
    }

    @Override
    public ProductDto updateProduct(@RequestBody @Valid UpdateProductDto productDto) {
        return storeService.updateProduct(productDto);
    }

    @Override
    public boolean setProductQuantityState(@RequestParam UUID productId,
                                           @RequestParam QuantityState quantityState) {
        SetProductQuantityStateRequest stateRequest = new SetProductQuantityStateRequest(productId, quantityState);
        return storeService.setProductQuantityState(stateRequest);
    }

    @Override
    public boolean removeProductFromStore(@RequestBody UUID productId) {
        return storeService.removeProductFromStore(productId);
    }
}
