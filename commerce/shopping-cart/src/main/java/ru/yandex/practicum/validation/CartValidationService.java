package ru.yandex.practicum.validation;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.shoppingCart.response.ShoppingCartDto;
import ru.yandex.practicum.exception.StateCartsException;
import ru.yandex.practicum.exception.WarehouseUnavailableException;
import ru.yandex.practicum.feignClient.WarehouseClient;
import ru.yandex.practicum.model.CartState;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartValidationService {
    private final WarehouseClient warehouseClient;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public void validateCartIsActive(String username, CartState state) {
        if (state != CartState.ACTIVE) {
            log.error("Корзина пользователя {} деактивирована", username);
            throw new StateCartsException("Корзина пользователя " + username + " деактивирована");
        }
    }

    @Transactional(readOnly = true)
    @CircuitBreaker(name = "warehouse", fallbackMethod = "warehouseUnavailable")
    @Retry(name = "warehouse")
    public void validateProductsQuantities(UUID shoppingCartId, Map<UUID, Long> products) {
        ShoppingCartDto checkDto = new ShoppingCartDto(shoppingCartId, products);
        log.info("Проверяем наличие достаточного количества товаров в складе для корзины с id {}", shoppingCartId);
        warehouseClient.checkProductQuantityEnoughForShoppingCart(checkDto);
    }

    @SuppressWarnings("unused")
    public void warehouseUnavailable(UUID shoppingCartId, Map<UUID, Long> products, Exception ex) {
        log.error("Сервис склада недоступен: {}", ex.getMessage());
        throw new WarehouseUnavailableException("Сервис склада временно недоступен. Попробуйте позже.", ex);
    }
}
