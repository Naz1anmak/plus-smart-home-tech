package ru.yandex.practicum.kafka.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.model.HubEventType;
import ru.yandex.practicum.service.SensorService;

@Component
@RequiredArgsConstructor
public class DeviceAddedEventHandler implements HubEventHandler {
    private final SensorService sensorService;

    @Override
    public HubEventType getEventType() {
        return HubEventType.DEVICE_ADDED;
    }

    @Override
    public void handle(HubEventAvro event) {
        DeviceAddedEventAvro payload = (DeviceAddedEventAvro) event.getPayload();
        sensorService.addSensor(event.getHubId(), payload.getId());
    }
}
