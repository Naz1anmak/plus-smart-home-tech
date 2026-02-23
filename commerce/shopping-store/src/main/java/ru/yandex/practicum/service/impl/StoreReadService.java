package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.StoreRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreReadService {
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public Product getProductById(UUID productId) {
        return storeRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Продукт с ID {} не найден", productId);
                    return new NotFoundException("Продукт не найден");
                });
    }

    @Transactional(readOnly = true)
    public Page<Product> getProductsByCategory(ProductCategory category, Pageable pageable) {
        return storeRepository.findByProductCategory(category, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProductById(UUID productId) {
        return storeRepository.findById(productId);
    }
}
