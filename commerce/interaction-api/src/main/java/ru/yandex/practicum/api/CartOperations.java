package ru.yandex.practicum.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.shoppingCart.request.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.shoppingCart.response.ShoppingCartDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Интерфейс операций корзины.
 * Определяет все операции с HTTP-маппингами и валидациями.
 */
public interface CartOperations {

    /**
     * Добавить товары в корзину пользователя
     */
    @PutMapping
    ShoppingCartDto addProductToShoppingCart(@RequestParam @NotBlank String username,
                                             @RequestBody @NotEmpty Map<UUID, Long> products);

    /**
     * Получить корзину пользователя
     */
    @GetMapping
    ShoppingCartDto getShoppingCart(@RequestParam @NotBlank String username);

    /**
     * Изменить количество товара в корзине
     */
    @PostMapping("/change-quantity")
    ShoppingCartDto changeProductQuantity(@RequestParam @NotBlank String username,
                                          @RequestBody @Valid ChangeProductQuantityRequest request);

    /**
     * Удалить товары из корзины
     */
    @PostMapping("/remove")
    ShoppingCartDto removeFromShoppingCart(@RequestParam @NotBlank String username,
                                           @RequestBody @NotEmpty List<UUID> products);

    /**
     * Деактивировать текущую корзину пользователя
     */
    @DeleteMapping
    void deactivateCurrentShoppingCart(@RequestParam @NotBlank String username);
}
