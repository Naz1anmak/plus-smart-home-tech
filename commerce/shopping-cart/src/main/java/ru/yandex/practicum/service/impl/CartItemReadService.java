package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.CartItem;
import ru.yandex.practicum.repository.CartItemRepository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemReadService {
    private final CartItemRepository cartItemRepository;

    @Transactional(readOnly = true)
    public Map<UUID, Long> findAllByCartId(UUID id) {
        return cartItemRepository.findAllByCartId(id).stream()
                .collect(Collectors.toMap(
                        CartItem::getProductId,
                        CartItem::getQuantity
                ));
    }

    @Transactional(readOnly = true)
    public CartItem findByCartIdAndProductId(UUID cartId, UUID productId) {
        return cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .orElseThrow(() -> {
                    log.error("Продукт с id {} не найден в корзине с id {}", productId, cartId);
                    return new NotFoundException("Продукт с id " + productId + " не найден в корзине с id " + cartId);
                });
    }

    @Transactional(readOnly = true)
    public Map<UUID, CartItem> findExistingItemsByProductIds(UUID cartId, Collection<UUID> productIds) {
        return cartItemRepository.findAllByCartIdAndProductIdIn(cartId, productIds).stream()
                .collect(Collectors.toMap(
                        CartItem::getProductId,
                        Function.identity()
                ));
    }
}
