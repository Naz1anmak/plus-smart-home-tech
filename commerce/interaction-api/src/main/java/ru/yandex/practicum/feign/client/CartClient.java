package ru.yandex.practicum.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.api.CartOperations;

@FeignClient(name = "shopping-cart", path = "/api/v1/shopping-cart")
public interface CartClient extends CartOperations {
}
