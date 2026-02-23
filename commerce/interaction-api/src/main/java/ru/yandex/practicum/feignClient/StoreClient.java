package ru.yandex.practicum.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.api.StoreOperations;

@FeignClient(name = "store-client", path = "/api/v1/shopping-store")
public interface StoreClient extends StoreOperations {
}
