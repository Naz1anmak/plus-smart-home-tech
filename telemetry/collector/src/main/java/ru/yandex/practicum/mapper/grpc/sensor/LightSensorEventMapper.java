package ru.yandex.practicum.mapper.grpc.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.LightSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.model.sensor.LightSensorEvent;

import java.time.Instant;

@Component
public class LightSensorEventMapper {

    public LightSensorEvent fromProto(SensorEventProto proto) {
        LightSensorEvent event = new LightSensorEvent();
        LightSensorProto lightSensor = proto.getLightSensor();

        event.setId(proto.getId());
        event.setHubId(proto.getHubId());
        event.setTimestamp(Instant.ofEpochSecond(
                proto.getTimestamp().getSeconds(),
                proto.getTimestamp().getNanos()
        ));
        event.setLinkQuality(lightSensor.getLinkQuality());
        event.setLuminosity(lightSensor.getLuminosity());

        return event;
    }
}
