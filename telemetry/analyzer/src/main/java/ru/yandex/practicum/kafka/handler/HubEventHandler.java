package ru.yandex.practicum.kafka.handler;

import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.model.HubEventType;

public interface HubEventHandler {

    HubEventType getEventType();

    void handle(HubEventAvro event);
}
