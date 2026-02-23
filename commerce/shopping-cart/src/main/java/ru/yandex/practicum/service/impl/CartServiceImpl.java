package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.shoppingCart.request.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.shoppingCart.response.ShoppingCartDto;
import ru.yandex.practicum.mapper.CartItemMapper;
import ru.yandex.practicum.mapper.CartMapper;
import ru.yandex.practicum.model.CartItem;
import ru.yandex.practicum.model.CartState;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.repository.CartItemRepository;
import ru.yandex.practicum.repository.CartRepository;
import ru.yandex.practicum.service.CartService;
import ru.yandex.practicum.validation.CartValidationService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final CartReadService cartReadService;
    private final CartValidationService cartValidationService;
    private final CartItemReadService cartItemReadService;
    private final CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public ShoppingCartDto addProductToShoppingCart(String username, Map<UUID, Long> products) {
        ShoppingCart cart = cartReadService.getCartByName(username)
                .orElseGet(() -> createNewCart(username));

        cartValidationService.validateCartIsActive(cart.getUsername(), cart.getState());
        cartValidationService.validateProductsQuantities(cart.getId(), products);

        Map<UUID, CartItem> existingItems = cartItemReadService.findExistingItemsByProductIds(cart.getId(), products.keySet());

        products.forEach((productId, quantity) -> {
            CartItem cartItem = existingItems.get(productId);

            if (cartItem != null) {
                log.info("Обновляем количество продукта с id {} в корзине. Было: {}, добавляем: {}",
                        productId, cartItem.getQuantity(), quantity);
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItemRepository.save(cartItem);
            } else {
                CartItem newItem = cartItemMapper.toCartItem(productId, quantity, cart);
                log.info("Добавляем в корзину {} шт. продукта с id {}", quantity, productId);
                cartItemRepository.save(newItem);
            }
        });

        Map<UUID, Long> allProducts = cartItemReadService.findAllByCartId(cart.getId());
        return cartMapper.toShoppingCartDto(cart, allProducts);
    }

    @Override
    @Transactional
    public ShoppingCartDto getShoppingCart(String username) {
        return cartReadService.getCartByName(username)
                .map(cart -> {
                    Map<UUID, Long> products = cartItemReadService.findAllByCartId(cart.getId());
                    log.info("Корзина для пользователя {} найдена, возвращаем данные", username);
                    return cartMapper.toShoppingCartDto(cart, products);
                })
                .orElseGet(() -> {
                    log.info("Корзина для пользователя {} не найдена, создаём новую", username);
                    ShoppingCart newCart = createNewCart(username);
                    return cartMapper.toShoppingCartDto(newCart, Map.of());
                });
    }

    @Override
    @Transactional
    public ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest request) {
        ShoppingCart cart = cartReadService.getCartByNameOrThrow(username);

        cartValidationService.validateCartIsActive(cart.getUsername(), cart.getState());

        CartItem cartItem = cartItemReadService.findByCartIdAndProductId(cart.getId(), request.productId());

        log.info("Изменяем количество продукта с id {} в корзине пользователя {} на {}",
                request.productId(), username, request.newQuantity());
        cartItem.setQuantity(request.newQuantity());
        cartItemRepository.save(cartItem);

        Map<UUID, Long> allProducts = cartItemReadService.findAllByCartId(cart.getId());
        return cartMapper.toShoppingCartDto(cart, allProducts);
    }

    @Override
    @Transactional
    public ShoppingCartDto removeFromShoppingCart(String username, List<UUID> products) {
        ShoppingCart cart = cartReadService.getCartByNameOrThrow(username);

        cartValidationService.validateCartIsActive(cart.getUsername(), cart.getState());

        products.forEach(productId -> {
            CartItem cartItem = cartItemReadService.findByCartIdAndProductId(cart.getId(), productId);

            log.info("Удаляем продукт с id {} из корзины пользователя {}", productId, username);
            cartItemRepository.delete(cartItem);
        });

        Map<UUID, Long> allProducts = cartItemReadService.findAllByCartId(cart.getId());
        return cartMapper.toShoppingCartDto(cart, allProducts);
    }

    @Override
    @Transactional
    public void deactivateCurrentShoppingCart(String username) {
        cartReadService.getCartByName(username)
                .ifPresent(cart -> {
                    log.info("Деактивируем корзину пользователя {}", username);
                    cart.setState(CartState.DEACTIVATE);
                    cartRepository.save(cart);
                });
    }

    @Transactional
    public ShoppingCart createNewCart(String username) {
        ShoppingCart newCart = new ShoppingCart();
        newCart.setUsername(username);
        newCart.setState(CartState.ACTIVE);

        log.info("Создаём новую корзину для пользователя: {}", username);
        return cartRepository.save(newCart);
    }
}
