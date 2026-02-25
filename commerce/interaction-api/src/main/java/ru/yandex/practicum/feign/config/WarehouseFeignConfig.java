package ru.yandex.practicum.feign.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.feign.decoder.WarehouseErrorDecoder;

@Configuration
public class WarehouseFeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new WarehouseErrorDecoder();
    }
}
