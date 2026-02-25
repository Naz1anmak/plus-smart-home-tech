package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.repository.CartRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartReadService {
    private final CartRepository cartRepository;

    @Transactional(readOnly = true)
    public Optional<ShoppingCart> getCartByName(String username) {
        return cartRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public ShoppingCart getCartByNameOrThrow(String username) {
        return cartRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Корзина для пользователя {} не найдена", username);
                    return new NotFoundException("Корзина для пользователя " + username + " не найдена");
                });
    }
}
