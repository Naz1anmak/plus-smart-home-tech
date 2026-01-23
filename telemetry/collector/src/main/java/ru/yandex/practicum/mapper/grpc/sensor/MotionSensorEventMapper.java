package ru.yandex.practicum.mapper.grpc.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.MotionSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.model.sensor.MotionSensorEvent;

import java.time.Instant;

@Component
public class MotionSensorEventMapper {

    public MotionSensorEvent fromProto(SensorEventProto proto) {
        MotionSensorEvent event = new MotionSensorEvent();
        MotionSensorProto motionSensor = proto.getMotionSensor();

        event.setId(proto.getId());
        event.setHubId(proto.getHubId());
        event.setTimestamp(Instant.ofEpochSecond(
                proto.getTimestamp().getSeconds(),
                proto.getTimestamp().getNanos()
        ));
        event.setLinkQuality(motionSensor.getLinkQuality());
        event.setMotion(motionSensor.getMotion());
        event.setVoltage(motionSensor.getVoltage());

        return event;
    }
}
