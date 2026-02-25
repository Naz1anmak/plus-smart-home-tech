package ru.yandex.practicum.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.api.WarehouseOperations;
import ru.yandex.practicum.feign.config.WarehouseFeignConfig;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse", configuration = WarehouseFeignConfig.class)
public interface WarehouseClient extends WarehouseOperations {
}
