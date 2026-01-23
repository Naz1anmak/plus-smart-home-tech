package ru.yandex.practicum.mapper.grpc.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.ClimateSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.model.sensor.ClimateSensorEvent;

import java.time.Instant;

@Component
public class ClimateSensorEventMapper {

    public ClimateSensorEvent fromProto(SensorEventProto proto) {
        ClimateSensorEvent event = new ClimateSensorEvent();
        ClimateSensorProto climateSensor = proto.getClimateSensor();

        event.setId(proto.getId());
        event.setHubId(proto.getHubId());
        event.setTimestamp(Instant.ofEpochSecond(
                proto.getTimestamp().getSeconds(),
                proto.getTimestamp().getNanos()));
        event.setTemperatureC(climateSensor.getTemperatureC());
        event.setHumidity(climateSensor.getHumidity());
        event.setCo2Level(climateSensor.getCo2Level());

        return event;
    }
}
