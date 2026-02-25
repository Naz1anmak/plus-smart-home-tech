package ru.yandex.practicum.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.api.StoreOperations;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store")
public interface StoreClient extends StoreOperations {
}
