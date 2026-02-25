package ru.yandex.practicum.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import ru.yandex.practicum.dto.shoppingCart.request.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.shoppingCart.response.ShoppingCartDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CartService {

    ShoppingCartDto addProductToShoppingCart(@NotBlank String username, @NotEmpty Map<UUID, Long> products);

    ShoppingCartDto getShoppingCart(@NotBlank String username);

    ShoppingCartDto changeProductQuantity(@NotBlank String username, @Valid ChangeProductQuantityRequest request);

    ShoppingCartDto removeFromShoppingCart(@NotBlank String username, @NotEmpty List<UUID> products);

    void deactivateCurrentShoppingCart(@NotBlank String username);
}
