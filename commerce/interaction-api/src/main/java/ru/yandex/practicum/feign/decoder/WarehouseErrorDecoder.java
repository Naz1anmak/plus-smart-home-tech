package ru.yandex.practicum.feign.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import ru.yandex.practicum.exception.BadRequestException;
import ru.yandex.practicum.exception.NotFoundException;

public class WarehouseErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();

        if (status >= 400 && status < 500) {
            if (status == 404) {
                return new NotFoundException("Ресурс не найден при вызове: " + methodKey);
            }
            return new BadRequestException("Ошибка клиента при вызове: " + methodKey + " со статусом: " + status);
        }

        return defaultDecoder.decode(methodKey, response);
    }
}
