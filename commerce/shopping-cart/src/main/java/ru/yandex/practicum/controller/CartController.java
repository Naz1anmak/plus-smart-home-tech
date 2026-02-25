package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.annotation.Loggable;
import ru.yandex.practicum.api.CartOperations;
import ru.yandex.practicum.dto.shoppingCart.request.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.shoppingCart.response.ShoppingCartDto;
import ru.yandex.practicum.service.CartService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
public class CartController implements CartOperations {
    private final CartService cartService;

    @Loggable
    @Override
    public ShoppingCartDto addProductToShoppingCart(@RequestParam @NotBlank String username,
                                                    @RequestBody @NotEmpty Map<UUID, Long> products) {
        return cartService.addProductToShoppingCart(username, products);
    }

    @Loggable
    @Override
    public ShoppingCartDto getShoppingCart(@RequestParam @NotBlank String username) {
        return cartService.getShoppingCart(username);
    }

    @Loggable
    @Override
    public ShoppingCartDto changeProductQuantity(@RequestParam @NotBlank String username,
                                                 @RequestBody @Valid ChangeProductQuantityRequest request) {
        return cartService.changeProductQuantity(username, request);
    }

    @Loggable
    @Override
    public ShoppingCartDto removeFromShoppingCart(@RequestParam @NotBlank String username,
                                                  @RequestBody @NotEmpty List<UUID> products) {
        return cartService.removeFromShoppingCart(username, products);
    }

    @Loggable
    @Override
    public void deactivateCurrentShoppingCart(@RequestParam @NotBlank String username) {
        cartService.deactivateCurrentShoppingCart(username);
    }
}
