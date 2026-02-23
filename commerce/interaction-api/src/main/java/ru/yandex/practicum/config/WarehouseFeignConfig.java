package ru.yandex.practicum.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import ru.yandex.practicum.feign.WarehouseErrorDecoder;

public class WarehouseFeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new WarehouseErrorDecoder();
    }
}
