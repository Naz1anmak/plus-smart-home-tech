package ru.yandex.practicum.mapper.grpc.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.TemperatureSensorProto;
import ru.yandex.practicum.model.sensor.TemperatureSensorEvent;

import java.time.Instant;

@Component
public class TemperatureSensorEventMapper {

    public TemperatureSensorEvent fromProto(SensorEventProto proto) {
        TemperatureSensorEvent event = new TemperatureSensorEvent();
        TemperatureSensorProto temperatureSensor = proto.getTemperatureSensor();

        event.setId(proto.getId());
        event.setHubId(proto.getHubId());
        event.setTimestamp(Instant.ofEpochSecond(
                proto.getTimestamp().getSeconds(),
                proto.getTimestamp().getNanos()
        ));
        event.setTemperatureC(temperatureSensor.getTemperatureC());
        event.setTemperatureF(temperatureSensor.getTemperatureF());

        return event;
    }
}
