package ru.yandex.practicum.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.repository.WarehouseRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WarehouseValidationService {
    private final WarehouseRepository warehouseRepository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public void validateProductIdNotExist(UUID productId) {
        if (warehouseRepository.existsById(productId)) {
            log.error("Продукт с ID {} уже существует на складе", productId);
            throw new SpecifiedProductAlreadyInWarehouseException("Продукт с ID " + productId + " уже существует на складе");
        }
    }
}
