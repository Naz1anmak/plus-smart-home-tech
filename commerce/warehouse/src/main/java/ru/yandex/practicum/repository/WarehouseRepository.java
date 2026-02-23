package ru.yandex.practicum.repository;

import jakarta.persistence.LockModeType;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.model.WarehouseProduct;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface WarehouseRepository extends JpaRepository<WarehouseProduct, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @NonNull
    Optional<WarehouseProduct> findById(@NonNull UUID id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
                SELECT p FROM WarehouseProduct p
                JOIN FETCH p.stock
                WHERE p.productId IN :ids
            """)
    List<WarehouseProduct> findAllWithStock(Set<UUID> ids);
}
