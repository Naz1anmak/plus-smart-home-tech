package ru.yandex.practicum.mapper.grpc.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.TypeNotFoundException;
import ru.yandex.practicum.grpc.telemetry.event.DeviceAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.model.hub.DeviceType;

import java.time.Instant;

@Component
public class DeviceAddedEventMapper {

    public DeviceAddedEvent fromProto(HubEventProto proto) {
        DeviceAddedEvent event = new DeviceAddedEvent();
        DeviceAddedEventProto deviceAdded = proto.getDeviceAdded();

        event.setHubId(proto.getHubId());
        event.setTimestamp(Instant.ofEpochSecond(
                proto.getTimestamp().getSeconds(),
                proto.getTimestamp().getNanos()));
        event.setId(deviceAdded.getId());
        event.setDeviceType(mapDeviceType(deviceAdded.getType()));

        return event;
    }

    private DeviceType mapDeviceType(DeviceTypeProto protoType) {
        return switch (protoType) {
            case MOTION_SENSOR -> DeviceType.MOTION_SENSOR;
            case TEMPERATURE_SENSOR -> DeviceType.TEMPERATURE_SENSOR;
            case LIGHT_SENSOR -> DeviceType.LIGHT_SENSOR;
            case CLIMATE_SENSOR -> DeviceType.CLIMATE_SENSOR;
            case SWITCH_SENSOR -> DeviceType.SWITCH_SENSOR;
            case UNRECOGNIZED -> throw new TypeNotFoundException("Неизвестный тип устройства " + protoType);
        };
    }
}
