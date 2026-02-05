package ru.yandex.practicum.handler.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.mapper.grpc.hub.DeviceAddedEventMapper;
import ru.yandex.practicum.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.service.DeviceAddedService;

@Component
@RequiredArgsConstructor
public class DeviceAddedEventHandler implements HubEventHandler {
    private final DeviceAddedService deviceAddedService;
    private final DeviceAddedEventMapper mapper;

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_ADDED;
    }

    @Override
    public void handle(HubEventProto proto) {
        DeviceAddedEvent deviceAddedEvent = mapper.fromProto(proto);
        deviceAddedService.save(deviceAddedEvent);
    }
}
