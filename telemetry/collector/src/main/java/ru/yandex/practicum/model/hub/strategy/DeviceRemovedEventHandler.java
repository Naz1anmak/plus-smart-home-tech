package ru.yandex.practicum.model.hub.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.mapper.grpc.hub.DeviceRemovedEventMapper;
import ru.yandex.practicum.model.hub.DeviceRemovedEvent;
import ru.yandex.practicum.service.DeviceRemovedService;

@Component
@RequiredArgsConstructor
public class DeviceRemovedEventHandler implements HubEventHandler {
    private final DeviceRemovedService deviceRemovedService;
    private final DeviceRemovedEventMapper mapper;

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_REMOVED;
    }

    @Override
    public void handle(HubEventProto proto) {
        DeviceRemovedEvent deviceRemovedEvent = mapper.fromProto(proto);
        deviceRemovedService.save(deviceRemovedEvent);
    }
}
