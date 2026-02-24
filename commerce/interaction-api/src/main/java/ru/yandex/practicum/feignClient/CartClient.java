package ru.yandex.practicum.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.api.CartOperations;

@FeignClient(name = "cart-service", path = "/api/v1/shopping-cart")
public interface CartClient extends CartOperations {
}
