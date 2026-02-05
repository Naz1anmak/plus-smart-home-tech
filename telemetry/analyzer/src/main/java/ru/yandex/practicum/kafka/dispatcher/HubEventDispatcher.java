package ru.yandex.practicum.kafka.dispatcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.HandlerNotFoundException;
import ru.yandex.practicum.kafka.handler.HubEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.mapper.HubEventTypeMapper;
import ru.yandex.practicum.model.HubEventType;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HubEventDispatcher {
    private final Map<HubEventType, HubEventHandler> handlers;
    private final HubEventTypeMapper mapper;

    public HubEventDispatcher(Set<HubEventHandler> handlers, HubEventTypeMapper mapper) {
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(
                        HubEventHandler::getEventType,
                        Function.identity()
                ));
        this.mapper = mapper;
    }

    public void dispatch(HubEventAvro event) {
        HubEventHandler handler = handlers.get(mapper.fromAvro(event));
        if (handler == null) {
            log.error("Не найден обработчик для события {}", event.getPayload());
            throw new HandlerNotFoundException("Не найден обработчик для события " + event.getPayload());
        }
        handler.handle(event);
    }
}
