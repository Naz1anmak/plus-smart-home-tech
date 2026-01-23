package ru.yandex.practicum.mapper.grpc.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SwitchSensorProto;
import ru.yandex.practicum.model.sensor.SwitchSensorEvent;

import java.time.Instant;

@Component
public class SwitchSensorEventMapper {

    public SwitchSensorEvent fromProto(SensorEventProto proto) {
        SwitchSensorEvent event = new SwitchSensorEvent();
        SwitchSensorProto switchSensor = proto.getSwitchSensor();

        event.setId(proto.getId());
        event.setHubId(proto.getHubId());
        event.setTimestamp(Instant.ofEpochSecond(
                proto.getTimestamp().getSeconds(),
                proto.getTimestamp().getNanos()
        ));
        event.setState(switchSensor.getState());

        return event;
    }
}
