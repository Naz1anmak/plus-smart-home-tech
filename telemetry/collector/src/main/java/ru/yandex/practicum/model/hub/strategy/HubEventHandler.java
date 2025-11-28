package ru.yandex.practicum.model.hub.strategy;

import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.HubEventType;

public interface HubEventHandler {

    HubEventType getType();

    void handle(HubEvent event);
}
