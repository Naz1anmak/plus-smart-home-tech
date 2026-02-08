package ru.yandex.practicum.kafka.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.model.HubEventType;
import ru.yandex.practicum.service.SensorService;

@Component
@RequiredArgsConstructor
public class DeviceRemovedEventHandler implements HubEventHandler {
    private final SensorService sensorService;

    @Override
    public HubEventType getEventType() {
        return HubEventType.DEVICE_REMOVED;
    }

    @Override
    public void handle(HubEventAvro event) {
        DeviceRemovedEventAvro payload = (DeviceRemovedEventAvro) event.getPayload();
        sensorService.removeSensor(event.getHubId(), payload.getId());
    }
}
