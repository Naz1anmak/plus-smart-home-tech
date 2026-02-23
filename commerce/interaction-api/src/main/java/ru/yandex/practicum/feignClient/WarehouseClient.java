package ru.yandex.practicum.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.api.WarehouseOperations;
import ru.yandex.practicum.config.WarehouseFeignConfig;

@FeignClient(name = "warehouse",
        path = "/api/v1/warehouse",
        configuration = WarehouseFeignConfig.class)
public interface WarehouseClient extends WarehouseOperations {
}
