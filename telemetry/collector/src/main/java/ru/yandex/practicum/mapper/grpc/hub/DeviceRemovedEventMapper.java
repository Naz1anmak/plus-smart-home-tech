package ru.yandex.practicum.mapper.grpc.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceRemovedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.model.hub.DeviceRemovedEvent;

import java.time.Instant;

@Component
public class DeviceRemovedEventMapper {

    public DeviceRemovedEvent fromProto(HubEventProto proto) {
        DeviceRemovedEvent event = new DeviceRemovedEvent();
        DeviceRemovedEventProto deviceRemoved = proto.getDeviceRemoved();

        event.setHubId(proto.getHubId());
        event.setTimestamp(Instant.ofEpochSecond(
                proto.getTimestamp().getSeconds(),
                proto.getTimestamp().getNanos()));
        event.setId(deviceRemoved.getId());

        return event;
    }
}
