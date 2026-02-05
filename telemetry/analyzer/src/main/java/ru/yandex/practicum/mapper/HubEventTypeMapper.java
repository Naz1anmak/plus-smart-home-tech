package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.TypeNotFoundException;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.model.HubEventType;

@Component
public class HubEventTypeMapper {

    public HubEventType fromAvro(HubEventAvro event) {
        Object payload = event.getPayload();

        if (payload instanceof DeviceAddedEventAvro) {
            return HubEventType.DEVICE_ADDED;
        }
        if (payload instanceof DeviceRemovedEventAvro) {
            return HubEventType.DEVICE_REMOVED;
        }
        if (payload instanceof ScenarioAddedEventAvro) {
            return HubEventType.SCENARIO_ADDED;
        }
        if (payload instanceof ScenarioRemovedEventAvro) {
            return HubEventType.SCENARIO_REMOVED;
        }

        throw new TypeNotFoundException(
                "Неизвестный тип HubEvent payload: " + payload.getClass()
        );
    }
}
