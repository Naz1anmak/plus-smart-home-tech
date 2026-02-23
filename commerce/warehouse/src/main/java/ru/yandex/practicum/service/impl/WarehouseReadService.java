package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.model.WarehouseProduct;
import ru.yandex.practicum.repository.WarehouseRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WarehouseReadService {
    private final WarehouseRepository warehouseRepository;

    @Transactional(readOnly = true)
    public WarehouseProduct getProductById(UUID productId) {
        return warehouseRepository.findById(productId).orElseThrow(() -> {
            log.error("Продукт с id {} не найден в складе", productId);
            return new NoSpecifiedProductInWarehouseException("Продукт с id " + productId + " не найден в складе");
        });
    }
}
